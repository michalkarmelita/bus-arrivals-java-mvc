package com.example.trackermvc.stops.model;

import com.google.android.gms.maps.model.MarkerOptions;

public class MarkerDetails {

    private final StopData stopData;
    private final MarkerOptions markerOptions;

    public MarkerDetails(StopData stopData, MarkerOptions markerOptions) {
        this.stopData = stopData;
        this.markerOptions = markerOptions;
    }

    public StopData getStopData() {
        return stopData;
    }

    public MarkerOptions getMarkerOptions() {
        return markerOptions;
    }
}
