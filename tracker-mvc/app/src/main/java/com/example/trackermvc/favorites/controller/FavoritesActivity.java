package com.example.trackermvc.favorites.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.trackermvc.app.App;
import com.example.trackermvc.app.controllers.BaseActivity;
import com.example.trackermvc.arrivals.controller.ArrivalsActivity;
import com.example.trackermvc.arrivals.model.Arrival;
import com.example.trackermvc.favorites.injection.FavoritesModule;
import com.example.trackermvc.favorites.persistence.FavouritesDbManager;
import com.example.trackermvc.favorites.ui.FavoritesView;
import com.example.trackermvc.stops.model.StopData;

import java.util.List;

import javax.inject.Inject;

public class FavoritesActivity extends BaseActivity implements FavoritesController {

    public static Intent launch(Context context) {
        return new Intent(context, FavoritesActivity.class);
    }

    @Inject
    FavouritesDbManager mDbManager;
    @Inject
    FavoritesView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<StopData> favStops = mDbManager.getFavoriteStops();

        if (favStops != null && favStops.size() > 0) {
            mView.showStops(favStops);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void initComponent() {
        App.getApp()
                .getComponent()
                .add(new FavoritesModule(this))
                .inject(this);
    }

    @Override
    public void removeStop(StopData stopData) {
        List<StopData> newStops = mDbManager.removeFavoriteStop(stopData);
        mView.showStops(newStops);
    }

    @Override
    public void getArrivals(StopData stopData) {
        startActivity(ArrivalsActivity.launch(this, stopData.getId(), stopData.getCommonName()));
    }
}
