package com.example.trackermvc.stops.persistence;


import com.example.trackermvc.stops.model.StopData;

import java.util.List;

public interface StopsDbManager {

    void saveFavoriteStop(StopData stopData);

    List<StopData> removeFavoriteStop(StopData stopData);

    boolean isStopFavorite(StopData stopData);
}
