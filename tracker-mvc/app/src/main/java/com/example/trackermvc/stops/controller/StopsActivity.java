package com.example.trackermvc.stops.controller;

import android.location.Location;
import android.os.Bundle;

import com.example.trackermvc.app.App;
import com.example.trackermvc.app.controllers.BaseActivity;
import com.example.trackermvc.app.manager.permissions.PermissionManager;
import com.example.trackermvc.arrivals.controller.ArrivalsActivity;
import com.example.trackermvc.stops.injection.LocationModule;
import com.example.trackermvc.stops.injection.StopsModule;
import com.example.trackermvc.stops.manager.LocationManager;
import com.example.trackermvc.stops.manager.LocationManagerListener;
import com.example.trackermvc.stops.model.StopData;
import com.example.trackermvc.stops.repository.StopsRepository;
import com.example.trackermvc.stops.ui.StopsView;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class StopsActivity extends BaseActivity implements StopsController, LocationManagerListener {

    @Inject
    StopsView mView;
    @Inject
    LocationManager mLocationManager;
    @Inject
    PermissionManager mPermissionManager;
    @Inject
    StopsRepository mStopsRepository;

    private Location mCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView.onCreate(savedInstanceState);
        mCurrentLocation = mLocationManager.getLastKnownLocation();
        if (mPermissionManager.hasPermissions(BaseActivity.MANDATORY_PERMISSIONS)) {
            if (mCurrentLocation != null) {
                mView.displayLocation(mCurrentLocation);
                mStopsRepository.requestStops(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
            }
        }
        mLocationManager.requestUpdates(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mView.onLowMemory();
    }

    @Override
    public void onBackPressed() {
        if (mView.stopDetailsExpanded()) {
            mView.collapseStopDetails();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void initComponent() {
        App.getApp()
                .getComponent()
                .add(new StopsModule(this), new LocationModule())
                .inject(this);
    }

    @Override
    public void onLocationUpdate(Location location) {
        mView.displayLocation(location);
        mStopsRepository.requestStops(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onStopsLoaded(List<StopData> stops) {
        mView.displayStops(stops);
    }

    @Override
    public void onStopsLoadingError() {

    }

    @Override
    public void onStopSelected(String stopId) {
        startActivity(ArrivalsActivity.launch(this, stopId));
    }
}
