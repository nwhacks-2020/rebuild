package com.nwhacks2020.rebuild;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.nwhacks2020.rebuild.data.DemoModeSingleton;
import com.nwhacks2020.rebuild.device_services.DeviceServices;

public class OnboardingActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
    }

    @Override
    protected void onResume() {
        super.onResume();

        DeviceServices.requestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        DeviceServices.requireLocationEnabled(this);
    }

    public void nextActivity(View view) {
        startActivity(new Intent(this, MapsActivity.class));
        finish();
    }

    public void nextActivityWithMarkers(View view) {
        DemoModeSingleton.setHasDemoMarkers(true);
        nextActivity(view);
    }

    public void nextActivityWithoutMarkers(View view) {
        DemoModeSingleton.setHasDemoMarkers(false);
        nextActivity(view);
    }


}
