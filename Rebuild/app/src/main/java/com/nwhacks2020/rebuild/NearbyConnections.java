package com.nwhacks2020.rebuild;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;
import com.google.android.gms.nearby.connection.Strategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Arrays;
import java.util.UUID;

import static com.google.android.gms.nearby.connection.Strategy.P2P_CLUSTER;

public class NearbyConnections {

    private static final String TAG = NearbyConnections.class.getName();

    private static final String DEVICE_ID = UUID.randomUUID().toString();

    private static Strategy STRATEGY = P2P_CLUSTER;

    public static String getDeviceId() {
        return DEVICE_ID;
    }

    /*
     * P2P_CLUSTER by default.
     */
    public static void setStrategy(Strategy s) {
        STRATEGY = s;
    }


    public static void startAdvertising(
            final Context context,
            final ConnectionLifecycleCallback connectionLifecycleCallback) {
        final String serviceId =  context.getString(R.string.package_name);

        AdvertisingOptions advertisingOptions =
                new AdvertisingOptions.Builder().setStrategy(STRATEGY).build();
        Nearby.getConnectionsClient(context)
                .startAdvertising(
                        DEVICE_ID,
                        serviceId,
                        connectionLifecycleCallback,
                        advertisingOptions)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG, "Mesh network activated.");
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "Unable to activate mesh network.");
                            }
                        });
    }

    private void startDiscovery(
            final Context context,
            final ConnectionLifecycleCallback connectionLifecycleCallback) {
        DiscoveryOptions discoveryOptions =
                new DiscoveryOptions.Builder().setStrategy(STRATEGY).build();

        final EndpointDiscoveryCallback endpointDiscoveryCallback =
                new EndpointDiscoveryCallback() {

                    @Override
                    public void onEndpointFound(
                            @NonNull String endpointId, @NonNull DiscoveredEndpointInfo info) {

                        Nearby.getConnectionsClient(context)
                                .requestConnection(DEVICE_ID, endpointId, connectionLifecycleCallback)
                                .addOnSuccessListener(
                                        new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                // We successfully requested a connection. Now both sides
                                                // must accept before the connection is established.
                                            }
                                        })
                                .addOnFailureListener(
                                        new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Nearby Connections failed to request the connection.
                                            }
                                        });
                    }

                    @Override
                    public void onEndpointLost(@NonNull String endpointId) {
                        // A previously discovered endpoint has gone away.
                    }
                };

        Nearby.getConnectionsClient(context)
                .startDiscovery(
                        DEVICE_ID,
                        endpointDiscoveryCallback,
                        discoveryOptions)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                // We're discovering!
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // We're unable to start discovering.
                            }
                        });
    }





}
