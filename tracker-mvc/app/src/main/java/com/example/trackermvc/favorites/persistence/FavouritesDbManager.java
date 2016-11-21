package com.example.trackermvc.favorites.persistence;


import com.example.trackermvc.stops.model.StopData;

import java.util.List;

public interface FavouritesDbManager {

    void saveFavoriteStop(StopData stopData);

    List<StopData> removeFavoriteStop(StopData stopData);

    List<StopData> getFavoriteStops();

    boolean isStopFavorite(StopData stopData);
}
