package com.nwhacks2020.rebuild;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class PinMenu extends FragmentActivity {
    String items[] = new String[] {" DANGER", "SHELTER","FOOD","WATER","NEED_HELP"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_menu);
    }

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

        Log.d(PinMenu.class.getName(), "Creating " + type.toString() + " at location " +
                        CurrentLocationSingleton.getLatitude() + "," +
                CurrentLocationSingleton.getLongitude());

        if (type != MarkerTitles.NONE) {
            RebuildMarkerListSingleton.getInstance().addMarkerOverride(new RebuildMarker(
                    CurrentLocationSingleton.getLatitude(),
                    CurrentLocationSingleton.getLongitude(),
                    type
            ));
        }

        Vibrate.trigger(this, 250);
        Toast.makeText(this, "Created a new marker.", Toast.LENGTH_LONG).show();

        finish();
    }

    public void backPressed(View view) {
        finish();
    }
}
