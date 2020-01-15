package com.nwhacks2020.rebuild;

import android.app.Activity;
import android.content.Intent;

@SuppressWarnings("unused")
public class Navigation {

    private static final int REQUEST_CODE = 23937;

    public static void goToWifiSettings(Activity thisActivity) {
        thisActivity.startActivityForResult(
                new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS), REQUEST_CODE
        );
    }

    public static void goToLocationSettings(Activity thisActivity) {
        thisActivity.startActivityForResult(
                new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), REQUEST_CODE
        );
    }

    public static void exitApplication(Activity thisActivity) {
        thisActivity.finish();
        System.exit(0);
    }

}
