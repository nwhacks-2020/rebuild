package com.nwhacks2020.rebuild.data;

import android.location.Location;

@SuppressWarnings("WeakerAccess")
public class CurrentLocationSingleton {

    private static Location location = null;

    public static void setCurrentLocation(Location location) {
        CurrentLocationSingleton.location = location;
    }

    public static double getLatitude() {
        return location.getLatitude();
    }

    public static double getLongitude() {
        return location.getLongitude();
    }

    public static Location getLocation() {
        return location;
    }

}
