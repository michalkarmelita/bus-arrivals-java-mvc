package com.example.trackermvc.app.persistence;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.trackermvc.app.injection.qualifiers.ForApplication;
import com.example.trackermvc.stops.model.StopData;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class DbManagerImpl implements DbManager {

    private static final String TRACKER_APP = "tracker_db";
    private static final String FAVORITES_STOPS = "favorites_stops";

    private final SharedPreferences mSharedPreferences;
    private final Gson mGson;

    @Inject
    public DbManagerImpl(@ForApplication Context context, Gson gson) {
        mSharedPreferences = context.getSharedPreferences(TRACKER_APP, 0);
        mGson = gson;
    }

    @Override
    public void saveFavoriteStop(StopData stopData) {
        List<StopData> stops = getFavoriteStops();
        stops.add(stopData);
        saveStops(stops);
    }

    @Override
    public List<StopData> removeFavoriteStop(StopData stopData) {
        List<StopData> stops = getFavoriteStops();
        stops.remove(stopData);
        saveStops(stops);
        return stops;
    }

    private void saveStops(List<StopData> stops) {
        Data data = new Data(stops);
        String stopsJson = mGson.toJson(data);
        mSharedPreferences.edit().putString(FAVORITES_STOPS, stopsJson).apply();
    }

    @Override
    public List<StopData> getFavoriteStops() {
        String stopsJson = mSharedPreferences.getString(FAVORITES_STOPS, mGson.toJson(new Data(new ArrayList<>())));
        Data data = mGson.fromJson(stopsJson, Data.class);
        return data.getStops();
    }

    @Override
    public boolean isStopFavorite(StopData stopData) {
        return getFavoriteStops().contains(stopData);
    }

    private static class Data {
        private List<StopData> stops;

        Data(List<StopData> stops) {
            this.stops = stops;
        }

        public List<StopData> getStops() {
            return stops;
        }
    }

}
