package com.example.trackermvc.app.network;

import com.example.trackermvc.app.network.model.StopsResponse;

import rx.Observable;

public interface NetworkManager {

    Observable<StopsResponse> getStops(double lat, double lon, String stopType, int radius);

}
