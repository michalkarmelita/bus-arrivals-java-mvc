package com.example.trackermvc.arrivals.network;


import com.example.trackermvc.arrivals.model.Arrival;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public interface ArrivalsNetworkManager {

    Observable<ArrayList<Arrival>> getArrivals(String stopId);

}
