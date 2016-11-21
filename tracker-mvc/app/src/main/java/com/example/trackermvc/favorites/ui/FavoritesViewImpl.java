package com.example.trackermvc.favorites.ui;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ViewSwitcher;

import com.example.trackermvc.R;
import com.example.trackermvc.app.ui.BaseActionBarView;
import com.example.trackermvc.favorites.controller.FavoritesController;
import com.example.trackermvc.favorites.ui.adapters.FavoritesAdapter;
import com.example.trackermvc.stops.model.StopData;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class FavoritesViewImpl extends BaseActionBarView implements FavoritesView, FavoriteStopListener {

    private static final int STOP_LIST = 1;

    @BindView(R.id.favorites_view_switcher)
    ViewSwitcher mViewSwitcher;
    @BindView(R.id.favorites_stops)
    RecyclerView mRecyclerView;

    private final FavoritesController mController;
    private final FavoritesAdapter mAdapter;

    @Inject
    public FavoritesViewImpl(FavoritesController controller, FavoritesAdapter adapter) {
        super(controller);
        mController = controller;
        mAdapter = adapter;
    }

    @Override
    public void init(View view) {
        super.init(view);
        showNavigationIcon();
        mToolbar.setTitle(R.string.favourites_activity_title);
        mAdapter.setRemoveStopListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected int getToolbarId() {
        return R.id.toolbar;
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_favorites;
    }

    @Override
    public void showStops(List<StopData> stops) {
        mViewSwitcher.setDisplayedChild(STOP_LIST);
        mAdapter.setStops(stops);
    }

    @Override
    public void onStopClicked(StopData stopData) {
        mController.getArrivals(stopData);
    }

    @Override
    public void onRemoveClicked(StopData stopData) {
        mController.removeStop(stopData);
    }
}
