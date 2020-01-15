package com.nwhacks2020.rebuild;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;
import com.nwhacks2020.rebuild.data.CurrentLocationSingleton;
import com.nwhacks2020.rebuild.data.DemoModeSingleton;
import com.nwhacks2020.rebuild.data.RebuildMarkerListSingleton;
import com.nwhacks2020.rebuild.device_services.DeviceServices;
import com.nwhacks2020.rebuild.google_maps.LocationUpdates;
import com.nwhacks2020.rebuild.google_maps.MarkerManager;
import com.nwhacks2020.rebuild.nearby_connections.MeshNetworkService;
import com.nwhacks2020.rebuild.nearby_connections.NearbyConnections;
import com.nwhacks2020.rebuild.rebuild_markers.RebuildMarker;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = MapsActivity.class.getName();

    private static LocationManager locationManager;

    @SuppressWarnings("FieldCanBeLocal")
    private FusedLocationProviderClient fusedLocationClient;
    private GoogleMap mMap;
    private LocationCallback locationCallback;

//    private LatLng ubcStartLocation = new LatLng(49.262599, -123.244944);

    @SuppressWarnings("FieldCanBeLocal")
    private float startZoom = 17;
    private boolean movedToCurrentLocation = false;

    private Activity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager == null) {
            Toast.makeText(this, "Failed to access Location Service.", Toast.LENGTH_LONG).show();
            return;
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    // Got last known location. In some rare situations this can be null.
                    if (location == null) {
                        return;
                    }

                    setCurrentLocation(location);
                });

        // Set behaviour upon a new received location
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }

                Location mostAccurateLocation = null;
                for (Location l : locationResult.getLocations()) {
                    if (l == null) {
                        continue;
                    }
                    if (mostAccurateLocation == null) {
                        mostAccurateLocation = l;
                        continue;
                    }
                    if (l.getAccuracy() < mostAccurateLocation.getAccuracy()) {
                        mostAccurateLocation = l;
                    }
                }

                if (mostAccurateLocation == null) {
                    return;
                }

                setCurrentLocation(mostAccurateLocation);
            }
        };


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment == null) {
            Toast.makeText(this, "Could not instantiate map.", Toast.LENGTH_SHORT)
                    .show();
        }
        else {
            mapFragment.getMapAsync(this);
        }

        // Start NearbyConnections
        // Requires Fine Location
        final String connectionServiceId = getString(R.string.package_name);
        NearbyConnections.startAdvertising(this, connectionServiceId, new ReceivePayloadListener());
        NearbyConnections.startDiscovering(this, connectionServiceId, new ReceivePayloadListener());
        startService(new Intent(this, MeshNetworkService.class));
    }

    private void setCurrentLocation(Location current) {
        Log.i(
                TAG,
                "Setting current location to (" +
                current.getLatitude() +
                ", " +
                current.getLongitude() + ")"
        );

        CurrentLocationSingleton.setCurrentLocation(current);

        if (!movedToCurrentLocation) {

            if (DemoModeSingleton.demoMarkersActivated()) {
                MarkerManager.addSampleLocalMarkers(current);
            }

            LatLng mMapLocation = new LatLng(
                    current.getLatitude(), current.getLongitude()
            );
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mMapLocation));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(startZoom));
            movedToCurrentLocation = true;
        }
    }

    private void asyncRepeatUpdateMarkersAndDevicesCount() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {

            updateAllMarkers();
            int devicesConnected = NearbyConnections.getNumberOfConnectedDevices();
            if (devicesConnected == 0) {
                setDevicesCount(getString(R.string.maps_devices_status_none));
            }
            else if (devicesConnected == 1) {
                setDevicesCount(getString(R.string.maps_devices_status_one));
            }
            else {
                setDevicesCount(devicesConnected + getString(R.string.maps_devices_status_more));
            }

            Log.d(TAG, "Updated markers and devices count.");
            asyncRepeatUpdateMarkersAndDevicesCount();

        }, 4000);
    }

    private void setDevicesCount(String s) {
        TextView view = findViewById(R.id.maps_devices_status);
        view.setText(s);
    }

    private void updateAllMarkers() {
        List<RebuildMarker> markers = RebuildMarkerListSingleton.getInstance().getList();

        mMap.clear();

        Location current = updateLastKnownLocation();
        if (current != null) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(
                    current.getLatitude(),
                    current.getLongitude()
            ))).setZIndex(Float.MAX_VALUE);
        }

        for (RebuildMarker m : markers) {

            int iconDrawable = 0;
            switch(m.getMarkerType()) {
                case NONE:
                    break;
                case DANGER:
                    iconDrawable = R.drawable.danger;
                    break;
                case SHELTER:
                    iconDrawable = R.drawable.shelter;
                    break;
                case FOOD:
                    iconDrawable = R.drawable.food;
                    break;
                case WATER:
                    iconDrawable = R.drawable.water;
                    break;
                case NEED_HELP:
                    iconDrawable = R.drawable.needhelp;
                    break;
                case POWER:
                    iconDrawable = R.drawable.power;
                    break;
            }

            if (iconDrawable == 0) {
                LatLng location = new LatLng(m.getLatitude(), m.getLongitude());
                mMap.addMarker(new MarkerOptions().position(location));
            }
            else {
                int dim = 100;
                Bitmap b = BitmapFactory.decodeResource(getResources(), iconDrawable);
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, dim, dim, false);
                BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(smallMarker);

                LatLng location = new LatLng(m.getLatitude(), m.getLongitude());
                mMap.addMarker(new MarkerOptions()
                        .position(location)
                        .title(m.getMarkerType().toString())
                        .icon(icon)
                );
            }
        }

    }

    public void buttonOpenPinMenu() {
        Intent intent = new Intent(this, PinMenuFragment.class);
        startActivity(intent);
    }

    /*
     * @return null if the Location cannot be accessed
     */
    private Location getLastKnownLocation() throws SecurityException {
        DeviceServices.requestPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        DeviceServices.requestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        // Require location to be active
        DeviceServices.requireLocationEnabled(this);

        // Search all enabled providers for the most accurate location
        List<String> providers = locationManager.getProviders(true);
        Location mostAccurateLocation = null;

        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (mostAccurateLocation == null ||
                    l.getAccuracy() < mostAccurateLocation.getAccuracy()) {
                mostAccurateLocation = l;
            }
        }

        return mostAccurateLocation;
    }

    private Location updateLastKnownLocation() {
        Location l = getLastKnownLocation();
        if (l != null) {
            CurrentLocationSingleton.setCurrentLocation(l);
        }
        return l;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.google_maps_style));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        DeviceServices.requestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        updateAllMarkers();
        asyncRepeatUpdateMarkersAndDevicesCount();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            String strEditText = data.getStringExtra("editTextValue");
            if ("Danger".equals(strEditText)) {
                Location l = getLastKnownLocation();
                if (l != null) {
                    Bitmap danger = BitmapFactory.decodeResource(getResources(), R.drawable.danger);
                    danger = Bitmap.createScaledBitmap(danger, 40, 40, false);
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(l.getLatitude(), l.getLongitude()))
                            .title("Marker")
                            .icon(BitmapDescriptorFactory.fromBitmap(danger))
                    );
                }
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();

        // Start location updates
        fusedLocationClient.requestLocationUpdates(
                LocationUpdates.createLocationRequest(),
                locationCallback,
                Looper.getMainLooper()
        );


        updateLastKnownLocation();
    }

    private class ReceivePayloadListener extends PayloadCallback {

        @Override
        public void onPayloadReceived(@NonNull String endpointId, Payload payload) {
            // This always gets the full data of the payload. Will be null if it's not a BYTES
            // payload.
            // Check the payload type with payload.getType().
            byte[] receivedBytes = payload.asBytes();
            if (receivedBytes != null) {
                Log.d(TAG, "Received data: " + new String(receivedBytes));
            }
            else {
                Log.d(TAG, "Empty data received.");
            }

            if (receivedBytes != null) {
                RebuildMarkerListSingleton.getInstance().addMarkersFromJson(
                        context,
                        new String(receivedBytes)
                );
            }
        }

        @Override
        public void onPayloadTransferUpdate(@NonNull String endpointId,
                                            @NonNull PayloadTransferUpdate update) {
            // Action after the completed call to onPayloadReceived
        }
    }

}
