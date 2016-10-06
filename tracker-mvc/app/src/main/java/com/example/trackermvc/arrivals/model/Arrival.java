package com.example.trackermvc.arrivals.model;

import android.support.annotation.NonNull;

public class Arrival implements Comparable<Arrival> {

    private final String vehicleId;
    private final String lineName;
    private final String destinationName;
    private final Integer timeToStation;
    private final String expectedArrival;
    private final String modeName;

    public Arrival(String vehicleId, String lineName, String destinationName, Integer timeToStation, String expectedArrival, String modeName) {
        this.vehicleId = vehicleId;
        this.lineName = lineName;
        this.destinationName = destinationName;
        this.timeToStation = timeToStation;
        this.expectedArrival = expectedArrival;
        this.modeName = modeName;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getLineName() {
        return lineName;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public Integer getTimeToStation() {
        return timeToStation;
    }

    public String getExpectedArrival() {
        return expectedArrival;
    }

    public String getModeName() {
        return modeName;
    }

    @Override
    public int compareTo(@NonNull Arrival arrival) {
        return timeToStation.compareTo(arrival.timeToStation);
    }
}
