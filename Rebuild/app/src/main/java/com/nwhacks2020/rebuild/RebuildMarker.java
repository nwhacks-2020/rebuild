package com.nwhacks2020.rebuild;

import com.google.gson.annotations.Expose;

import java.util.Random;

@SuppressWarnings("WeakerAccess")
public class RebuildMarker {

    private double latitude;

    @Expose(deserialize = false)
    private static final String jsonKeyLatitude = "latitude";

    private double longitude;

    @Expose(deserialize = false)
    private static final String jsonKeyLongitude = "longitude";

    private MarkerTitles markerType;

    @Expose(deserialize = false)
    private static final String jsonKeyMarkerType = "markerType";

    public RebuildMarker(double latitude, double longitude, MarkerTitles markerType) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.markerType = markerType;
    }

    public static String getJsonKeyLatitude() {
        return jsonKeyLatitude;
    }

    public static String getJsonKeyLongitude() {
        return jsonKeyLongitude;
    }

    public static String getJsonKeyMarkerType() {
        return jsonKeyMarkerType;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public MarkerTitles getMarkerType() { return markerType; }

    public String getLocation() {
        return "" + latitude + "," + longitude;
    }

    public void setMarkerType(MarkerTitles markerType) {
        this.markerType = markerType;
    }

    public static MarkerTitles toMarkerTitle(String s) {
        return MarkerTitles.valueOf(s.toUpperCase());
    }

    public void shiftToAvoidConflict() {
        latitude += (new Random().nextInt(20)-10) * 0.00002f;
        longitude += (new Random().nextInt(20)-10) * 0.00002f;
    }
}
