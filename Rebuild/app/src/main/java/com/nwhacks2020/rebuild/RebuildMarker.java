package com.nwhacks2020.rebuild;

public class RebuildMarker {
    private double latitude;
    private double longitude;
    private MarkerTitles markerType;

    public RebuildMarker(double latitude, double longitude, MarkerTitles markerType) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.markerType = markerType;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setMarkerType(MarkerTitles markerType) {
        this.markerType = markerType;
    }
}
