package com.example.trackermvc.favorites.ui;


import com.example.trackermvc.app.ui.BaseView;
import com.example.trackermvc.stops.model.StopData;

import java.util.List;

public interface FavoritesView extends BaseView {
    void showStops(List<StopData> stops);
}
