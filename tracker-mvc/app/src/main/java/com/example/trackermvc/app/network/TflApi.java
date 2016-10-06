package com.example.trackermvc.app.network;


import com.example.trackermvc.app.network.model.ArrivalsResponse;
import com.example.trackermvc.app.network.model.StopsResponse;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * REST Api interface for Transport for London.
 */
public interface TflApi {

    @GET("/StopPoint")
    Observable<StopsResponse> getStopPoints(@Query("lat") double lat, @Query("lon") double lon, @Query("stopTypes") String stopType, @Query("radius") int radius);

    @GET("/StopPoint/{id}/Arrivals")
    Observable<List<ArrivalsResponse>> getArrivals(@Path("id") String stopId);
}
