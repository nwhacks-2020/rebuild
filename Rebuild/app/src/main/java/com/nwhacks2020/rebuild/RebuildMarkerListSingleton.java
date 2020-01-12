package com.nwhacks2020.rebuild;

public class RebuildMarkerListSingleton {

    private static RebuildMarkerList rebuildMarkerList;

    public static RebuildMarkerList getInstance() {
        if (rebuildMarkerList == null) {
            rebuildMarkerList = new RebuildMarkerList();
        }
        return rebuildMarkerList;

    }

}
