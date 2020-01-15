package com.nwhacks2020.rebuild.data;

public class DemoModeSingleton {

    private static boolean hasDemoMarkers = true;

    private DemoModeSingleton() {
    }

    public static boolean demoMarkersActivated() {
        return hasDemoMarkers;
    }

    public static void setHasDemoMarkers(boolean hasDemoMarkers) {
        DemoModeSingleton.hasDemoMarkers = hasDemoMarkers;
    }

}
