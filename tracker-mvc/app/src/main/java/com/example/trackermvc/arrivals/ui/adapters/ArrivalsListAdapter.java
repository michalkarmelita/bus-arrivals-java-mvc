package com.example.trackermvc.arrivals.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.trackermvc.R;
import com.example.trackermvc.arrivals.model.Arrival;
import com.example.trackermvc.arrivals.ui.viewholders.ArrivalViewHolder;
import com.example.trackermvc.stops.model.StopData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ArrivalsListAdapter extends RecyclerView.Adapter<ArrivalViewHolder> {

    private List<Arrival> mArrivals;

    @Inject
    public ArrivalsListAdapter() {
        mArrivals = new ArrayList<>();
    }

    @Override
    public ArrivalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ArrivalViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.arrival_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(ArrivalViewHolder holder, int position) {
        holder.bind(mArrivals.get(position));
    }

    @Override
    public int getItemCount() {
        return mArrivals.size();
    }

    public void setData(List<Arrival> arrivals) {
        mArrivals = arrivals;
        notifyDataSetChanged();
    }
}
