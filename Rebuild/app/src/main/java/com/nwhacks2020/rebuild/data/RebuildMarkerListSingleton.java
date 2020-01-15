package com.nwhacks2020.rebuild.data;

import com.nwhacks2020.rebuild.rebuild_markers.RebuildMarkerList;

public class RebuildMarkerListSingleton {

    private static RebuildMarkerList rebuildMarkerList;

    public static RebuildMarkerList getInstance() {
        if (rebuildMarkerList == null) {
            rebuildMarkerList = new RebuildMarkerList();
        }
        return rebuildMarkerList;

    }

}
