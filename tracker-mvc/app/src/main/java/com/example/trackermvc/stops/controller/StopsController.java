package com.example.trackermvc.stops.controller;


import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import com.example.trackermvc.app.controllers.ToolbarActivityController;
import com.example.trackermvc.stops.model.StopData;

import java.util.List;

public interface StopsController extends ToolbarActivityController {

    void onStopsLoaded(List<StopData> stops);

    void onStopsLoadingError();

    @Override
    ActionBar setUpToolbar(Toolbar toolbar);

    void onStopSelected(String stopId, String stopName);

    void favoriteStopClicked(StopData stopData);

    void showFavStops();

    boolean isStopFavorite(StopData stopData);

    Window getWindow();
}
