package com.example.trackermvc.stops.repository;

import com.example.trackermvc.stops.model.StopData;

public interface StopsRepository {

    void requestStops(double lat, double lon);

    void saveStop(StopData stopData);

    boolean isStopFavorite(StopData stopData);

    void removeStop(StopData stopData);
}
