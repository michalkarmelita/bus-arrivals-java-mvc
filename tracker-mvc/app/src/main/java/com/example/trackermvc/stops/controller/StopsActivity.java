package com.example.trackermvc.stops.controller;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import com.example.trackermvc.app.App;
import com.example.trackermvc.app.controllers.BaseActivity;
import com.example.trackermvc.app.manager.permissions.PermissionManager;
import com.example.trackermvc.arrivals.controller.ArrivalsActivity;
import com.example.trackermvc.favorites.controller.FavoritesActivity;
import com.example.trackermvc.stops.injection.LocationModule;
import com.example.trackermvc.stops.injection.StopsModule;
import com.example.trackermvc.stops.manager.LocationManager;
import com.example.trackermvc.stops.manager.LocationManagerListener;
import com.example.trackermvc.stops.model.StopData;
import com.example.trackermvc.stops.repository.StopsRepository;
import com.example.trackermvc.stops.ui.StopsView;

import java.util.List;

import javax.inject.Inject;

public class StopsActivity extends BaseActivity implements StopsController, LocationManagerListener {

    private static final int REQUEST_PERMISSIONS = 1;

    public static Intent launch(Context context) {
        return new Intent(context, StopsActivity.class);
    }

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
        if (mPermissionManager.hasPermissions(BaseActivity.MANDATORY_PERMISSIONS)) {
            initializeMapAndLocation();
        } else {
            mPermissionManager.requestPermissions(REQUEST_PERMISSIONS, BaseActivity.MANDATORY_PERMISSIONS);
        }
    }

    private void initializeMapAndLocation() {
        requestLocation();
        mView.initMap();
        mView.zoomToLocation(mCurrentLocation);
        requestStops();
        mLocationManager.requestUpdates(this);
    }

    private void requestLocation() {
        mCurrentLocation = mLocationManager.getLastKnownLocation();
    }

    private void requestStops() {
        if (mCurrentLocation != null) {
            mStopsRepository.requestStops(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS:
                if (resultCode == RESULT_OK) {
                    initializeMapAndLocation();
                }
            default:
                super.onActivityResult(requestCode, resultCode, data);
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
        mView.zoomToLocation(location);
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
    public void onStopSelected(String stopId, String stopName) {
        startActivity(ArrivalsActivity.launch(this, stopId, stopName));
    }

    @Override
    public void favoriteStopClicked(StopData stopData) {
        if (mStopsRepository.isStopFavorite(stopData)) {
            mStopsRepository.removeStop(stopData);
            mView.setFavouriteIcon(false);
            mView.showFavoritedSnackbar(false);
        } else {
            mStopsRepository.saveStop(stopData);
            mView.setFavouriteIcon(true);
            mView.showFavoritedSnackbar(true);
        }
    }

    @Override
    public void showFavStops() {
        startActivity(FavoritesActivity.launch(this));
    }

    @Override
    public boolean isStopFavorite(StopData stopData) {
        return mStopsRepository.isStopFavorite(stopData);
    }
}
