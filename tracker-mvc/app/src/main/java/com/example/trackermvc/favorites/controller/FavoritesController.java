package com.example.trackermvc.favorites.controller;

import com.example.trackermvc.app.controllers.ToolbarActivityController;
import com.example.trackermvc.stops.model.StopData;

public interface FavoritesController extends ToolbarActivityController {

    void removeStop(StopData stopData);

    void getArrivals(StopData stopData);
}
