package com.example.trackermvc.arrivals.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.trackermvc.R;
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
    private static final String STOP_NAME = "stopName";
    public static Intent launch(Context context, String stopId, String stopName) {
        return new Intent(context, ArrivalsActivity.class)
                .putExtra(STOP_ID, stopId)
                .putExtra(STOP_NAME, stopName);
    }

    @Inject
    ArrivalsView mView;
    @Inject
    ArrivalsRepository mRepository;

    private String stopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stopId = getIntent().getStringExtra(STOP_ID);
        mRepository.requestArrivals(stopId);
        mView.setToolbarTitle(getIntent().getStringExtra(STOP_NAME));
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
        App.getApp().getComponent()
                .add(new ArrivalsModule(this))
                .inject(this);
    }

    @Override
    public void onArrivalsLoaded(List<Arrival> arrivals) {
        Collections.sort(arrivals);
        mView.displayArrivals(arrivals);
    }

    @Override
    public void refresh() {
        mRepository.requestArrivals(stopId);
    }

    @Override
    public void onLoadError() {
        mView.displayLoadingError();
    }
}
