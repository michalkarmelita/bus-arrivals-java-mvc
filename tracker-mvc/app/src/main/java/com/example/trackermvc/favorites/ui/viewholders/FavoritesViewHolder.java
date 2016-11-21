package com.example.trackermvc.favorites.ui.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.trackermvc.R;
import com.example.trackermvc.favorites.ui.FavoriteStopListener;
import com.example.trackermvc.stops.model.StopData;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.stop_container)
    View mRootView;
    @BindView(R.id.stop_letter)
    TextView stopLetter;
    @BindView(R.id.stop_name)
    TextView stopName;
    @BindView(R.id.btn_remove)
    View btnRemove;

    private final FavoriteStopListener mListener;

    public FavoritesViewHolder(View itemView, FavoriteStopListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mListener = listener;
    }

    public void bind(StopData stopData) {
        mRootView.setOnClickListener(v -> mListener.onStopClicked(stopData));
        stopLetter.setText(stopData.getStopLetter());
        stopName.setText(stopData.getCommonName());
        btnRemove.setOnClickListener(v -> mListener.onRemoveClicked(stopData));
    }
}
