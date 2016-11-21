package com.example.trackermvc.favorites.ui;

import com.example.trackermvc.stops.model.StopData;

public interface FavoriteStopListener {

    void onStopClicked(StopData stopData);

    void onRemoveClicked(StopData stopData);

}
