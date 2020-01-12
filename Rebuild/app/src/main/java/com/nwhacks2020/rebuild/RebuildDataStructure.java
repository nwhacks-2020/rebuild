package com.nwhacks2020.rebuild;

import java.util.ArrayList;
import java.util.List;

public class RebuildDataStructure {
    private List<RebuildMarker> markerList;

    public RebuildDataStructure() {
        this.markerList = new ArrayList<>();
    }

    public void addMarkerToList(RebuildMarker newMarker) {
        markerList.add(newMarker);
    }

    public List<RebuildMarker> returnList(List<RebuildMarker> markerList) {
        return markerList;
    }
}
