package com.nwhacks2020.rebuild.device_services;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.nwhacks2020.rebuild.R;

import java.util.Objects;

@SuppressWarnings("unused")
public class LocationRequestDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity thisActivity = Objects.requireNonNull(
                getActivity(), "Could not retrieve the current activity."
        );

        AlertDialog.Builder builder = new AlertDialog.Builder(thisActivity);

        builder.setTitle(R.string.dialog_location_request_title)
                .setMessage(R.string.dialog_location_request_message)

                .setPositiveButton(R.string.go_to_settings, (dialog, id) ->
                        Navigation.goToLocationSettings(thisActivity)
                )

                .setNegativeButton(R.string.exit, (dialog, id) ->
                        Navigation.exitApplication(thisActivity)
                );

        return builder.create();
    }

}
