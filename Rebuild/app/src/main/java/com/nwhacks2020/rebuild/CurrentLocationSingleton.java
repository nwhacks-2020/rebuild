package com.nwhacks2020.rebuild;

public class CurrentLocationSingleton {

    private static double latitude = 0.0;
    private static double longitude = 0.0;

    public static void setCurrentLocation(double latitude, double longitude) {
        CurrentLocationSingleton.latitude = latitude;
        CurrentLocationSingleton.longitude = longitude;
    }

    public static double getLatitude() {
        return latitude;
    }

    public static double getLongitude() {
        return longitude;
    }



}
