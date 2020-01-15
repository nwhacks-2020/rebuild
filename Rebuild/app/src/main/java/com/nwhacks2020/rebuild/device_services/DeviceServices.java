package com.nwhacks2020.rebuild.device_services;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

@SuppressWarnings({"unused", "WeakerAccess"})
public class DeviceServices {

    private static final String TAG = DeviceServices.class.getName();

    private static final int REQUEST_CODE = 884721;

    public static boolean locationDisabled(Context context) {
        LocationManager locationManager =
                (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            Log.e(TAG, "Failed to access LocationManager System Service.");
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

    private static boolean deniedByUser(Activity thisActivity, String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(thisActivity, permission);
    }

    public static void requireLocationEnabled(FragmentActivity context) {
        if (DeviceServices.locationDisabled(context)) {
            new LocationRequestDialogFragment().show(
                    context.getSupportFragmentManager(),
                    LocationRequestDialogFragment.class.getName()
            );
        }
    }


    public static void vibrate(Context context, long milliseconds) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (v == null) {
            Log.e(TAG, "Failed to access Vibrator System Service.");
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(
                    milliseconds, VibrationEffect.DEFAULT_AMPLITUDE
            ));
        } else {
            // Deprecated in API 26
            v.vibrate(milliseconds);
        }
    }

}
