package com.nwhacks2020.rebuild;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = MapsActivity.class.getName();

    @SuppressWarnings("FieldCanBeLocal")
    private GoogleMap mMap;

    private LatLng startLocation = new LatLng(49.262599, -123.244944);

    @SuppressWarnings("FieldCanBeLocal")
    private float startZoom = 17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPinMenu();
            }
        });


        requestPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION);

        startAdvertising();
        startDiscovering();
    }

    public void openPinMenu(){
        Intent intent = new Intent(this, PinMenu.class);
        startActivity(intent);
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

        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

        LatLng myLocation = new LatLng(latitude, longitude);

        System.out.println(longitude);
        System.out.println(latitude);

        // Add a marker and move the camera
        mMap.addMarker(new MarkerOptions().position(myLocation).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(startZoom));
    }

    // For permission, use Manifest.permission
    private static void requestPermissions(
            Activity thisActivity,
            @SuppressWarnings("SameParameterValue") String permission) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(thisActivity, permission)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            //noinspection StatementWithEmptyBody
            if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
                    Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(thisActivity, new String[]{permission},0);
            }

        }
    }

    // FUNCTIONALITY FOR NearbyConnections

    private void startAdvertising() {
        final Context context = this;

        final ConnectionLifecycleCallback advertiserCallback =
                new ConnectionLifecycleCallback() {
                    @Override
                    public void onConnectionInitiated(
                            @NonNull String endpointId,
                            @NonNull ConnectionInfo connectionInfo) {

                        // Automatically accept the connection on both sides.
                        Nearby.getConnectionsClient(context)
                                .acceptConnection(endpointId, new ReceiveMarkersPayloadListener());
                    }

                    @Override
                    public void onConnectionResult(@NonNull String endpointId,
                                                   ConnectionResolution result) {
                        switch (result.getStatus().getStatusCode()) {
                            case ConnectionsStatusCodes.STATUS_OK:
                                // We're connected! Can now start sending and receiving data.
                                break;
                            case ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED:
                                // The connection was rejected by one or both sides.
                                break;
                            case ConnectionsStatusCodes.STATUS_ERROR:
                                // The connection broke before it was able to be accepted.
                                break;
                            default:
                                // Unknown status code
                        }
                    }

                    @Override
                    public void onDisconnected(@NonNull String endpointId) {
                        // No action taken
                    }
                };


        NearbyConnections.startAdvertising(this, advertiserCallback);
    }

    private void startDiscovering() {
        final Context context = this;

        final ConnectionLifecycleCallback discovererCallback =
                new ConnectionLifecycleCallback() {
                    @Override
                    public void onConnectionInitiated(
                            @NonNull String endpointId,
                            @NonNull ConnectionInfo connectionInfo) {

                        // Automatically accept the connection on both sides.
                        Nearby.getConnectionsClient(context)
                                .acceptConnection(endpointId, new ReceiveMarkersPayloadListener());
                    }

                    @Override
                    public void onConnectionResult(@NonNull String endpointId,
                                                   ConnectionResolution result) {
                        switch (result.getStatus().getStatusCode()) {
                            case ConnectionsStatusCodes.STATUS_OK:
                                // We're connected! Can now start sending and receiving data.
                                break;
                            case ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED:
                                // The connection was rejected by one or both sides.
                                break;
                            case ConnectionsStatusCodes.STATUS_ERROR:
                                // The connection broke before it was able to be accepted.
                                break;
                            default:
                                // Unknown status code
                        }
                    }

                    @Override
                    public void onDisconnected(@NonNull String endpointId) {
                        // No action taken
                    }
                };


        NearbyConnections.startDiscovering(this, discovererCallback);
    }


    private static class ReceiveMarkersPayloadListener extends PayloadCallback {

        @Override
        public void onPayloadReceived(@NonNull String endpointId, Payload payload) {
            // This always gets the full data of the payload. Will be null if it's not a BYTES
            // payload.
            // Check the payload type with payload.getType().
            byte[] receivedBytes = payload.asBytes();
            if (receivedBytes != null) {
                Log.d(TAG, "Received data: " + Arrays.toString(receivedBytes));
            }
            else {
                Log.d(TAG, "Empty data received.");
            }

            //TODO: Do something with the data.
        }

        @Override
        public void onPayloadTransferUpdate(@NonNull String endpointId,
                                            @NonNull PayloadTransferUpdate update) {
            // Action after the completed call to onPayloadReceived
        }
    }

}
