package com.example.trackermvc.arrivals.ui;


import com.example.trackermvc.app.ui.BaseView;
import com.example.trackermvc.arrivals.model.Arrival;

import java.util.List;

public interface ArrivalsView extends BaseView {

    void setToolbarTitle(String title);

    void displayArrivals(List<Arrival> arrivals);

    void displayLoadingError();
}
