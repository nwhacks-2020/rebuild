package com.nwhacks2020.rebuild.nearby_connections;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.nwhacks2020.rebuild.data.RebuildMarkerListSingleton;

public class MeshNetworkService extends Service {

    private static final String TAG = MeshNetworkService.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "Initialized.");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        repeatOperation();

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    private void repeatOperation() {
        final Context context = this;
        Handler handler = new Handler();
        handler.postDelayed(() -> {

            NearbyConnections.sendStringToAllEndpoints(
                    context, RebuildMarkerListSingleton.getInstance().toJson()
            );

            repeatOperation();

        }, 2000);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
