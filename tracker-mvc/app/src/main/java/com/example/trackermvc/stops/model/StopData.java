package com.example.trackermvc.stops.model;

public class StopData {

    private final String id;
    private final Double lat;
    private final Double lon;
    private final String indicator;
    private final String commonName;

    public StopData(String id, Double lat, Double lon, String indicator, String commonName) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.indicator = indicator;
        this.commonName = commonName;
    }

    public String getId() {
        return id;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public String getIndicator() {
        return indicator;
    }

    public String getCommonName() {
        return commonName;
    }

}
