package com.nwhacks2020.rebuild;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

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

        requestPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION);
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

        // Add a marker and move the camera
        mMap.addMarker(new MarkerOptions().position(startLocation).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(startLocation));
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

}
