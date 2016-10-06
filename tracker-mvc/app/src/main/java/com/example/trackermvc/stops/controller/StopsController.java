package com.example.trackermvc.stops.controller;


import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.example.trackermvc.app.controllers.ToolbarActivityController;
import com.example.trackermvc.stops.model.StopData;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface StopsController extends ToolbarActivityController {

    void onStopsLoaded(List<StopData> stops);

    void onStopsLoadingError();

    @Override
    ActionBar setUpToolbar(Toolbar toolbar);

    void onStopSelected(String stopId);
}
