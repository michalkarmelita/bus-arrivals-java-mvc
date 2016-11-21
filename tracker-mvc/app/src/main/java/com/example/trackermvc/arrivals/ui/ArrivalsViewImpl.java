package com.example.trackermvc.arrivals.ui;

import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.trackermvc.R;
import com.example.trackermvc.app.ui.BaseActionBarView;
import com.example.trackermvc.arrivals.controller.ArrivalsController;
import com.example.trackermvc.arrivals.model.Arrival;
import com.example.trackermvc.arrivals.ui.adapters.ArrivalsListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class ArrivalsViewImpl extends BaseActionBarView implements ArrivalsView {

    @BindView(R.id.root_view)
    View mRootView;
    @BindView(R.id.refresh_view)
    SwipeRefreshLayout mRefreshView;
    @BindView(R.id.arrivals_recycler_view)
    RecyclerView mRecyclerView;

    private final ArrivalsController mController;
    private final ArrivalsListAdapter mAdapter;

    @Inject
    public ArrivalsViewImpl(ArrivalsController controller, ArrivalsListAdapter adapter) {
        super(controller);
        mController = controller;
        mAdapter = adapter;
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_arrivals;
    }

    @Override
    public void init(View view) {
        super.init(view);
        showNavigationIcon();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRefreshView.setOnRefreshListener(mController::refresh);
    }

    @Override
    protected int getToolbarId() {
        return R.id.toolbar;
    }

    @Override
    public void setToolbarTitle(String title) {
        mToolbar.setTitle(title);
    }

    @Override
    public void displayArrivals(List<Arrival> arrivals) {
        mAdapter.setData(arrivals);
        stopRefresh();
    }

    @Override
    public void displayLoadingError() {
        stopRefresh();
        Snackbar.make(mRootView, "Loading error. Please check internet connection", Snackbar.LENGTH_LONG).show();
    }

    private void stopRefresh() {
        if (mRefreshView.isRefreshing()) {
            mRefreshView.setRefreshing(false);
        }
    }
}
