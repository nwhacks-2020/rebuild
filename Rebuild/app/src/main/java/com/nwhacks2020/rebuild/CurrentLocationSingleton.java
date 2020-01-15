package com.nwhacks2020.rebuild;

import android.location.Location;

@SuppressWarnings("WeakerAccess")
public class CurrentLocationSingleton {

    private static double latitude = 0.0;
    private static double longitude = 0.0;

    public static void setCurrentLocation(Location location) {
        CurrentLocationSingleton.latitude = location.getLatitude();
        CurrentLocationSingleton.longitude = location.getLongitude();
    }

    public static double getLatitude() {
        return latitude;
    }

    public static double getLongitude() {
        return longitude;
    }



}
