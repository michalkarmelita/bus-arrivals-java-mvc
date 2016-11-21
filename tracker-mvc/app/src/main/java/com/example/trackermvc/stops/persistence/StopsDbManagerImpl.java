package com.example.trackermvc.stops.persistence;

import com.example.trackermvc.app.persistence.DbManager;
import com.example.trackermvc.stops.model.StopData;

import java.util.List;

import javax.inject.Inject;

public class StopsDbManagerImpl implements StopsDbManager {

    private final DbManager mDbManager;

    @Inject
    public StopsDbManagerImpl(DbManager dbManager) {
        mDbManager = dbManager;
    }

    @Override
    public void saveFavoriteStop(StopData stopData) {
        mDbManager.saveFavoriteStop(stopData);
    }

    @Override
    public List<StopData> removeFavoriteStop(StopData stopData) {
        return mDbManager.removeFavoriteStop(stopData);
    }

    @Override
    public boolean isStopFavorite(StopData stopData) {
        return mDbManager.isStopFavorite(stopData);
    }

}
