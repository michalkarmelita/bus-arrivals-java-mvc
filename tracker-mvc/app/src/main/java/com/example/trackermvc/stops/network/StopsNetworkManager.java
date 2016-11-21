package com.example.trackermvc.stops.network;

import com.example.trackermvc.stops.model.StopData;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public interface StopsNetworkManager {

    Observable<ArrayList<StopData>> getStops(double lat, double lon, String stopType, int radius);

}
