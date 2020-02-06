package com.nwhacks2020.rebuild;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nwhacks2020.rebuild.data.CurrentLocationSingleton;
import com.nwhacks2020.rebuild.data.RebuildMarkerListSingleton;
import com.nwhacks2020.rebuild.device_services.DeviceServices;
import com.nwhacks2020.rebuild.rebuild_markers.MarkerTitles;
import com.nwhacks2020.rebuild.rebuild_markers.RebuildMarker;

/*
 * Menu to create a new marker on the map.
 */
public class PinMenuFragment extends FragmentActivity {
    public static final String TAG = "Pin Menu";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG,"created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_menu);
    }

    @SuppressWarnings("unused")  // Used in styles.xml, applied to the layout buttons
    public void clicked(View view) {

        MarkerTitles type = MarkerTitles.NONE;
        switch (view.getId()){
            case (R.id.buttonDanger):
                type = MarkerTitles.DANGER;
                break;
            case (R.id.buttonWater):
                type = MarkerTitles.WATER;
                break;
            case (R.id.buttonFood):
                type = MarkerTitles.FOOD;
                break;
            case (R.id.buttonShelter):
                type = MarkerTitles.SHELTER;
                break;
            case (R.id.buttonPower):
                type = MarkerTitles.POWER;
                break;
            case (R.id.buttonNeedsHelp):
                type = MarkerTitles.NEED_HELP;
                break;
        }

        Log.d(
                PinMenuFragment.class.getName(),
                "Creating " +
                        type.toString() +
                        " at location " +
                        CurrentLocationSingleton.getLatitude() +
                        "," +
                        CurrentLocationSingleton.getLongitude()
        );

        if (type != MarkerTitles.NONE) {
            RebuildMarkerListSingleton.getInstance().addMarkerOverride(new RebuildMarker(
                    CurrentLocationSingleton.getLatitude(),
                    CurrentLocationSingleton.getLongitude(),
                    type
            ));
        }

        DeviceServices.vibrate(this, 250);
        Toast.makeText(this, "Created a new marker.", Toast.LENGTH_LONG).show();

        finish();
    }

    public void backPressed(View view) {
        finish();
    }
}
