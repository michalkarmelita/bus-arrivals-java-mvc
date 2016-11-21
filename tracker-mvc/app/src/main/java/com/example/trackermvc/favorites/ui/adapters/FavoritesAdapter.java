package com.example.trackermvc.favorites.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.trackermvc.R;
import com.example.trackermvc.favorites.ui.FavoriteStopListener;
import com.example.trackermvc.favorites.ui.viewholders.FavoritesViewHolder;
import com.example.trackermvc.stops.model.StopData;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesViewHolder> {

    private List<StopData> favoritesList;
    private FavoriteStopListener mListener;

    @Override
    public FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FavoritesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.favorires_item, parent, false), mListener);
    }

    @Override
    public void onBindViewHolder(FavoritesViewHolder holder, int position) {
        holder.bind(favoritesList.get(position));
    }

    @Override
    public int getItemCount() {
        return favoritesList.size();
    }

    public void setStops(List<StopData> stops) {
        favoritesList = stops;
        notifyDataSetChanged();
    }

    public void setRemoveStopListener(FavoriteStopListener listener) {
        mListener = listener;
    }
}
