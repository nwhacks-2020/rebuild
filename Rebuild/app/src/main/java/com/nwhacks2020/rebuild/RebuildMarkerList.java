package com.nwhacks2020.rebuild;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
public class RebuildMarkerList {

    private static final String TAG = RebuildMarkerList.class.getName();

    private List<RebuildMarker> markerList;

    private Gson gson;

    public RebuildMarkerList() {
        markerList = new ArrayList<>();
        gson = new Gson();
    }

    // Assesses uniqueness by coordinates
    public void addMarkerIfNew(RebuildMarker marker) {
        String id = marker.getLocation();

        for (RebuildMarker m : markerList) {
            if (m.getLocation().equals(id)) {
                return;  // Already exists
            }
        }

        markerList.add(marker);
    }

    public List<RebuildMarker> getList() {
        return markerList;
    }

    public String toJson() {
        String json = gson.toJson(markerList);
        Log.d(TAG, "Converted markers to JSON: " + json);
        return json;
    }

    public void addMarkersFromJson(Context context, String jsonData) {
        JSONArray markersToAdd;
        try {
            markersToAdd = new JSONArray(jsonData);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(
                    context,
                    "Failed to convert JSON to object: " + e.getMessage(),
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        Log.d(TAG, "Received markers: " + markersToAdd.toString());

        for (int i = 0; i < markersToAdd.length(); i++) {

            RebuildMarker marker;
            try {
                JSONObject markerData = markersToAdd.getJSONObject(i);
                marker = jsonToMarker(markerData);
            } catch (JSONException e) {
                e.printStackTrace();
                String errorMessage = "Failed to read JSON from marker object: " + e.getMessage();
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                Log.e(TAG, errorMessage);
                continue;
            }

            addMarkerIfNew(marker);
        }

    }

    // Returns NULL if the JSON is invalid
    private RebuildMarker jsonToMarker(JSONObject obj) throws JSONException {
        MarkerTitles type = RebuildMarker.toMarkerTitle(
                obj.getString(RebuildMarker.getJsonKeyMarkerType())
        );
        return new RebuildMarker(
                obj.getDouble(RebuildMarker.getJsonKeyLatitude()),
                obj.getDouble(RebuildMarker.getJsonKeyLongitude()),
                type
        );
    }

}
