package com.example.trackermvc.stops.ui;

import android.location.Location;
import android.os.Bundle;

import com.example.trackermvc.app.ui.BaseView;
import com.example.trackermvc.stops.model.StopData;
import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface StopsView extends BaseView {

    void displayLocation(Location mCurrentLocation);

    void displayStops(List<StopData> stops);

    void onCreate(Bundle savedInstanceState);

    void onResume();

    void onPause();

    void onDestroy();

    void onSaveInstanceState(Bundle outState);

    void onLowMemory();

    boolean stopDetailsExpanded();

    void collapseStopDetails();
}

