package com.nwhacks2020.rebuild;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.Strategy;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.google.android.gms.nearby.connection.Strategy.P2P_CLUSTER;

@SuppressWarnings({"WeakerAccess", "unused"})
public class NearbyConnections {

    private static final String TAG = NearbyConnections.class.getName();

    private static final String DEVICE_ID = UUID.randomUUID().toString();

    private static Strategy STRATEGY = P2P_CLUSTER;

    private static Map<String, String> connectedDevices = new HashMap<>();

    /*
     * P2P_CLUSTER by default.
     */
    public static void setStrategy(Strategy s) {
        STRATEGY = s;
    }

    public static void startAdvertising(
            final Context context,
            final String connectionServiceId,
            PayloadCallback payloadCallback) {

        final ConnectionLifecycleCallback connectionLifecycleCallback =
                createConnectionLifecycleCallback(context, payloadCallback);

        AdvertisingOptions advertisingOptions =
                new AdvertisingOptions.Builder().setStrategy(STRATEGY).build();
        Nearby.getConnectionsClient(context)
                .startAdvertising(
                        DEVICE_ID,
                        connectionServiceId,
                        connectionLifecycleCallback,
                        advertisingOptions)
                .addOnSuccessListener(
                        unused -> Log.d(TAG, "Mesh network activated on [" + connectionServiceId + "]. Advertising."))
                .addOnFailureListener(
                        e -> Log.d(TAG, "Unable to activate mesh network advertising: " + e.getMessage()));
    }

    public static void startDiscovering(
            final Context context,
            final String connectionServiceId,
            PayloadCallback payloadCallback) {

        DiscoveryOptions discoveryOptions =
                new DiscoveryOptions.Builder().setStrategy(STRATEGY).build();

        final ConnectionLifecycleCallback connectionLifecycleCallback =
                createConnectionLifecycleCallback(context, payloadCallback);

        final EndpointDiscoveryCallback endpointDiscoveryCallback =
                new EndpointDiscoveryCallback() {

                    @Override
                    public void onEndpointFound(
                            @NonNull String endpointId, @NonNull DiscoveredEndpointInfo info) {

                        Log.d(TAG, "Discovered device [" + endpointId + "].");

                        Nearby.getConnectionsClient(context)
                                .requestConnection(
                                        DEVICE_ID,
                                        endpointId,
                                        connectionLifecycleCallback)
                                .addOnSuccessListener(
                                        unused -> Log.d(TAG, "Connected (as discoverer)."))
                                .addOnFailureListener(
                                        e -> Log.d(TAG, "Failed to connect as discoverer: " + e.getMessage()));
                    }

                    @Override
                    public void onEndpointLost(@NonNull String endpointId) {
                        // A previously discovered endpoint has gone away.
                    }
                };

        Nearby.getConnectionsClient(context)
                .startDiscovery(
                        connectionServiceId,
                        endpointDiscoveryCallback,
                        discoveryOptions)
                .addOnSuccessListener(
                        unused -> Log.d(TAG, "Mesh network activated on [" + connectionServiceId + "]. Discovering."))
                .addOnFailureListener(
                        e -> Log.d(TAG, "Unable to activate mesh network discovery: " + e.getMessage()));
    }

    public static void sendStringToAllEndpoints(Context context, String s) {
        Payload bytesPayload = Payload.fromBytes(s.getBytes());

        if (connectedDevices.isEmpty()) {
            Log.d(TAG, "No devices to send to.");
        }
        else {
            Log.d(TAG, "Sending data to " + connectedDevices.size() + " devices.");
            for (String device : connectedDevices.keySet()) {
                Nearby.getConnectionsClient(context).sendPayload(device, bytesPayload);
            }
        }
    }

    private static ConnectionLifecycleCallback createConnectionLifecycleCallback(
            final Context context,
            final PayloadCallback payloadCallback) {
        return new ConnectionLifecycleCallback() {
            @Override
            public void onConnectionInitiated(
                    @NonNull String endpointId,
                    @NonNull ConnectionInfo connectionInfo) {

                Log.d(TAG, "Found device [" + endpointId + "].");

                // Automatically accept the connection on both sides.
                Nearby.getConnectionsClient(context)
                        .acceptConnection(endpointId, payloadCallback);
            }

            @Override
            public void onConnectionResult(@NonNull String endpointId,
                                           @NonNull ConnectionResolution result) {
                switch (result.getStatus().getStatusCode()) {
                    case ConnectionsStatusCodes.STATUS_OK:
                        Log.i(TAG, "Connected to device [" + endpointId + "].");
                        connectedDevices.put(endpointId, endpointId);
                        break;
                    case ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED:
                        Log.e(TAG, "Connection rejected by x4device [" + endpointId + "].");
                        break;
                    case ConnectionsStatusCodes.STATUS_ERROR:
                        Log.e(TAG, "Unable to connect to device [" + endpointId + "].");
                    default:
                        Log.e(TAG, "Unable to connect to device [" + endpointId + "].");
                }
            }

            @Override
            public void onDisconnected(@NonNull String endpointId) {
                Log.d(TAG, "Disconnected from device [" + endpointId + "].");
                connectedDevices.remove(endpointId);
            }
        };
    }

}
