package com.example.trackermvc.arrivals.ui.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.trackermvc.R;
import com.example.trackermvc.arrivals.model.Arrival;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArrivalViewHolder extends RecyclerView.ViewHolder {

    public static final int MINUTES = 60;
    @BindView(R.id.line_name)
    TextView mLineName;
    @BindView(R.id.destination)
    TextView mDestination;
    @BindView(R.id.expected)
    TextView mExpected;

    public ArrivalViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Arrival arrival) {
        mLineName.setText(arrival.getLineName());
        mDestination.setText(arrival.getDestinationName());
        mExpected.setText(formatArrivalTime(arrival.getTimeToStation()));
    }

    private String formatArrivalTime(Integer timeToStation) {
        int minutes = timeToStation / MINUTES;
        int seconds = timeToStation % MINUTES;

        if (seconds < 10) {
            return String.format("%s'0%s", String.valueOf(minutes), String.valueOf(seconds));
        }

        return String.format("%s'%s", String.valueOf(minutes), String.valueOf(seconds));
    }
}
