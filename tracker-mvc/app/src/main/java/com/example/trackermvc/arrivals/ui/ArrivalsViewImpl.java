package com.example.trackermvc.arrivals.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    @BindView(R.id.arrivals_recycler_view)
    RecyclerView mRecyclerView;

    private final ArrivalsListAdapter mAdapter;

    @Inject
    public ArrivalsViewImpl(ArrivalsController controller, ArrivalsListAdapter adapter) {
        super(controller);
        mAdapter = adapter;
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_arrivals;
    }

    @Override
    public void init(View view) {
        super.init(view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected int getToolbarId() {
        return 0;
    }

    @Override
    public void displayArrivals(List<Arrival> arrivals) {
        mAdapter.setData(arrivals);
    }
}
