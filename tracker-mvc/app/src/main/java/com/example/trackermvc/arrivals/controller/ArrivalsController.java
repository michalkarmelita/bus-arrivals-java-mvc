package com.example.trackermvc.arrivals.controller;

import com.example.trackermvc.app.controllers.ToolbarActivityController;
import com.example.trackermvc.arrivals.model.Arrival;

import java.util.List;

public interface ArrivalsController extends ToolbarActivityController {

    void onArrivalsLoaded(List<Arrival> arrivals);
}
