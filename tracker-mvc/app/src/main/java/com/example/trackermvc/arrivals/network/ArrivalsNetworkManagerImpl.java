package com.example.trackermvc.arrivals.network;


import com.example.trackermvc.app.injection.qualifiers.ForIoThread;
import com.example.trackermvc.app.injection.qualifiers.ForMainThread;
import com.example.trackermvc.app.network.TflApi;
import com.example.trackermvc.arrivals.model.Arrival;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;

public class ArrivalsNetworkManagerImpl implements ArrivalsNetworkManager {

    private final TflApi mApi;
    private final Scheduler mIoScheduler;
    private final Scheduler mMainThreadScheduler;

    @Inject
    public ArrivalsNetworkManagerImpl(TflApi api,
                                      @ForIoThread Scheduler ioScheduler,
                                      @ForMainThread Scheduler mainThreadScheduler) {
        mApi = api;
        mIoScheduler = ioScheduler;
        mMainThreadScheduler = mainThreadScheduler;
    }

    @Override
    public Observable<ArrayList<Arrival>> getArrivals(String stopId) {
        return mApi.getArrivals(stopId)
                .flatMap(arrivalsResponses ->
                        Observable.from(arrivalsResponses)
                                .map(arrivalsResponse -> new Arrival(arrivalsResponse.getVehicleId(),
                                        arrivalsResponse.getLineName(),
                                        arrivalsResponse.getDestinationName(),
                                        arrivalsResponse.getTimeToStation(),
                                        arrivalsResponse.getExpectedArrival(),
                                        arrivalsResponse.getModeName()))
                )
                .collect(ArrayList<Arrival>::new, List::add)
                .subscribeOn(mIoScheduler)
                .observeOn(mMainThreadScheduler);
    }
}
