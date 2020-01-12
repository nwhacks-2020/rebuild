package com.nwhacks2020.rebuild;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

public class OnboardingActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
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
