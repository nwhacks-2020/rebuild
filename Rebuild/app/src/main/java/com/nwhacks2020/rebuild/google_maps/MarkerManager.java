package com.nwhacks2020.rebuild.google_maps;

import android.location.Location;

import com.nwhacks2020.rebuild.data.RebuildMarkerListSingleton;
import com.nwhacks2020.rebuild.rebuild_markers.MarkerTitles;
import com.nwhacks2020.rebuild.rebuild_markers.RebuildMarker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("unused")
public class MarkerManager {

    private static Random rand = new Random();

    public static void addSampleLocalMarkers(Location currentLocation) {

        List<RebuildMarker> markers = new ArrayList<>();

        Integer num = 12;

        // Top-left quadrant
        for (int i = 0; i < num/4; i++) {
            RebuildMarker m = new RebuildMarker(
                    currentLocation.getLatitude() - randomRadius(),
                    currentLocation.getLongitude() + randomRadius(),
                    randomMarkerType()
            );
            RebuildMarkerListSingleton.getInstance().addMarkerIfNew(m);
        }

        // Top-right quadrant
        for (int i = 0; i < num/4; i++) {
            RebuildMarker m = new RebuildMarker(
                    currentLocation.getLatitude() + randomRadius(),
                    currentLocation.getLongitude() + randomRadius(),
                    randomMarkerType()
            );
            RebuildMarkerListSingleton.getInstance().addMarkerIfNew(m);
        }

        // Bottom-left quadrant
        for (int i = 0; i < num/4; i++) {
            RebuildMarker m = new RebuildMarker(
                    currentLocation.getLatitude() - randomRadius(),
                    currentLocation.getLongitude() - randomRadius(),
                    randomMarkerType()
            );
            RebuildMarkerListSingleton.getInstance().addMarkerIfNew(m);
        }

        // Bottom-right quadrant
        for (int i = 0; i < num/4; i++) {
            RebuildMarker m = new RebuildMarker(
                    currentLocation.getLatitude() + randomRadius(),
                    currentLocation.getLongitude() - randomRadius(),
                    randomMarkerType()
            );
            RebuildMarkerListSingleton.getInstance().addMarkerIfNew(m);
        }

    }

    private static float randomRadius() {
        return rand.nextInt(8) * 0.0002f;
    }

    private static MarkerTitles randomMarkerType() {
        return MarkerTitles.values()[
                rand.nextInt(MarkerTitles.values().length)
        ];
    }

    public static void addSampleUbcMarkers() {

        // Centre for Drug Research
        RebuildMarkerListSingleton.getInstance().addMarkerIfNew(new RebuildMarker(
                49.262201, -123.243708, MarkerTitles.DANGER
        ));

        // UBC Chem and Biological Engineering
        RebuildMarkerListSingleton.getInstance().addMarkerIfNew(new RebuildMarker(
                49.262555, -123.247261, MarkerTitles.DANGER
        ));

        // Djavad Mowafaghian
        RebuildMarkerListSingleton.getInstance().addMarkerIfNew(new RebuildMarker(
                49.264580, -123.244279, MarkerTitles.DANGER
        ));

        // Centre for Blood Research
        RebuildMarkerListSingleton.getInstance().addMarkerIfNew(new RebuildMarker(
                49.262569, -123.245156, MarkerTitles.SHELTER
        ));

        // UBC Skate Park
        RebuildMarkerListSingleton.getInstance().addMarkerIfNew(new RebuildMarker(
                49.260938, -123.244514, MarkerTitles.SHELTER
        ));

        // Starbucks
        RebuildMarkerListSingleton.getInstance().addMarkerIfNew(new RebuildMarker(
                49.261307, -123.246550, MarkerTitles.FOOD
        ));

        // Purdy Pavilion
        RebuildMarkerListSingleton.getInstance().addMarkerIfNew(new RebuildMarker(
                49.263368, -123.245978, MarkerTitles.FOOD
        ));

        // BC Ambulance Station (South)
        RebuildMarkerListSingleton.getInstance().addMarkerIfNew(new RebuildMarker(
                49.263034, -123.243475, MarkerTitles.WATER
        ));

        // Walkway
        RebuildMarkerListSingleton.getInstance().addMarkerIfNew(new RebuildMarker(
                49.262718, -123.244174, MarkerTitles.NEED_HELP
        ));

        // Campus Energy Centre
        RebuildMarkerListSingleton.getInstance().addMarkerIfNew(new RebuildMarker(
                49.261745, -123.245134, MarkerTitles.POWER
        ));

        // The UBC Department of Psychiatry
        RebuildMarkerListSingleton.getInstance().addMarkerIfNew(new RebuildMarker(
                49.263819, -123.244550, MarkerTitles.POWER
        ));

    }

}
