package com.nwhacks2020.rebuild;

import com.google.gson.annotations.Expose;

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

    public String getLocation() {
        return "" + latitude + "," + longitude;
    }

    public void setMarkerType(MarkerTitles markerType) {
        this.markerType = markerType;
    }

    public static MarkerTitles toMarkerTitle(String s) {
        return MarkerTitles.valueOf(s.toUpperCase());
    }
}
