package com.example.trackermvc.stops.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;

public class LocationSensorReceiver extends BroadcastReceiver {
    public static final String GPS_PROVIDER_CHANGED_ACTION = "android.location.PROVIDERS_CHANGED";
    private final LocationStateListener mLocationStateListener;

    @Inject
    public LocationSensorReceiver(LocationStateListener mLocationStateListener) {
        this.mLocationStateListener = mLocationStateListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (GPS_PROVIDER_CHANGED_ACTION.equals(intent.getAction())) {
            mLocationStateListener.onLocationSensorStateChanged();
        }
    }
}