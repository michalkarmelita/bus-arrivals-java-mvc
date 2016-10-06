package com.example.trackermvc.arrivals.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.trackermvc.app.App;
import com.example.trackermvc.app.controllers.BaseActivity;
import com.example.trackermvc.arrivals.injection.ArrivalsModule;
import com.example.trackermvc.arrivals.model.Arrival;
import com.example.trackermvc.arrivals.repository.ArrivalsRepository;
import com.example.trackermvc.arrivals.ui.ArrivalsView;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class ArrivalsActivity extends BaseActivity implements ArrivalsController {

    private static final String STOP_ID = "stopId";

    public static Intent launch(Context context, String stopId) {
        return new Intent(context, ArrivalsActivity.class)
                .putExtra(STOP_ID, stopId);
    }

    @Inject
    ArrivalsView mView;
    @Inject
    ArrivalsRepository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String stopId = getIntent().getStringExtra(STOP_ID);
        mRepository.requestArrivals(stopId);
    }

    @Override
    protected void initComponent() {
        App.getApp().getComponent()
                .add(new ArrivalsModule(this))
                .inject(this);
    }

    @Override
    public void onArrivalsLoaded(List<Arrival> arrivals) {
        Collections.sort(arrivals);
        mView.displayArrivals(arrivals);
    }
}
