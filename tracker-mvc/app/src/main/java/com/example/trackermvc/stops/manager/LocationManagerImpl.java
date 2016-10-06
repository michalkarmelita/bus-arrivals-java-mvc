package com.example.trackermvc.stops.manager;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;


import com.example.trackermvc.app.injection.qualifiers.ForApplication;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import javax.inject.Inject;

import timber.log.Timber;

@MainThread
public class LocationManagerImpl implements LocationManager, LocationListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private android.location.LocationManager mLocationManager;
    private LocationManagerListener mLocationListener;

    @Inject
    public LocationManagerImpl(@ForApplication Context context, LocationRequest locationRequest) {
        mLocationRequest = locationRequest;
        mLocationManager = (android.location.LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void requestUpdates(LocationManagerListener listener) {
        mLocationListener = listener;

        mGoogleApiClient.connect();
    }

    @Override
    public void removeUpdates() {
        mLocationListener = null;
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public boolean isLocationEnabled() {

        boolean gps_enabled;
        boolean network_enabled;

        try {
            gps_enabled = mLocationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
        } catch (IllegalArgumentException ex) {
            return false;
        }

        try {
            network_enabled = mLocationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER);
        } catch (IllegalArgumentException ex) {
            return false;
        }

        return gps_enabled && network_enabled;
    }

    @Override
    public Location getLastKnownLocation() {
        return LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
    }

    @Override
    public void onConnected(Bundle bundle) {
        // request location updates
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        // send last available location so that we get a quick idea where we are
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (lastLocation != null) {
            onLocationChanged(lastLocation);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Timber.e("Location suspended: %s", i);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Timber.e("Location connection failed: %s", connectionResult.getErrorMessage());
    }

    @Override
    public void onLocationChanged(Location location) {
        if (mLocationListener == null) {
            Timber.e("Receiving locations but there is no listener bound");
            removeUpdates();
            return;
        }

        mLocationListener.onLocationUpdate(location);
    }
}
