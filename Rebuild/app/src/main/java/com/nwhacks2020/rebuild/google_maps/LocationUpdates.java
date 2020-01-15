package com.nwhacks2020.rebuild.google_maps;

import com.google.android.gms.location.LocationRequest;

@SuppressWarnings("unused")
public class LocationUpdates {

    public static LocationRequest createLocationRequest() {
        LocationRequest lr = LocationRequest.create();
        lr.setInterval(10000);
        lr.setFastestInterval(5000);
        lr.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return lr;
    }

}
