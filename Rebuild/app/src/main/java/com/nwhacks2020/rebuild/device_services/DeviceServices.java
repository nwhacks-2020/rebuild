package com.nwhacks2020.rebuild.device_services;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

@SuppressWarnings("unused")
public class DeviceServices {

    private static final String TAG = DeviceServices.class.getName();

    private static final int REQUEST_CODE = 884721;

    public static boolean locationDisabled(Context context) {
        LocationManager locationManager =
                (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            Log.e(TAG, "Failed to access LocationManager.");
            return true;
        }

        return !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
                !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    /*
     * @param   permission  Value from Manifest.permission
     */
    public static void requestPermission(
            Activity thisActivity,
            String permission) {

        if (ContextCompat.checkSelfPermission(thisActivity, permission)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission already granted
            return;
        }

        // Permission is not granted
        if (deniedByUser(thisActivity, permission)) {

            // TODO
            // Display an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to requestPermission the permission.

        }
        else {
            ActivityCompat.requestPermissions(
                    thisActivity, new String[]{permission}, REQUEST_CODE
            );
        }

    }

    public static void requireLocationEnabled(FragmentActivity context) {
        if (DeviceServices.locationDisabled(context)) {
            new LocationRequestDialogFragment().show(
                    context.getSupportFragmentManager(),
                    LocationRequestDialogFragment.class.getName()
            );
        }
    }

    private static boolean deniedByUser(Activity thisActivity, String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(thisActivity, permission);
    }

}
