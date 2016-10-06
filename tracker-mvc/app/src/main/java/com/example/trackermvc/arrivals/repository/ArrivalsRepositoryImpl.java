package com.example.trackermvc.arrivals.repository;


import com.example.trackermvc.app.injection.qualifiers.ForIoThread;
import com.example.trackermvc.app.injection.qualifiers.ForMainThread;
import com.example.trackermvc.app.network.TflApi;
import com.example.trackermvc.arrivals.controller.ArrivalsController;
import com.example.trackermvc.arrivals.model.Arrival;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;

public class ArrivalsRepositoryImpl implements ArrivalsRepository {

    private final TflApi mApi;
    private final Scheduler mIoScheduler;
    private final Scheduler mMainThreadScheduler;
    private final ArrivalsController mController;

    @Inject
    public ArrivalsRepositoryImpl(ArrivalsController controller,
                                  TflApi api,
                                  @ForIoThread Scheduler ioScheduler,
                                  @ForMainThread Scheduler mainThreadScheduler) {
        mController = controller;
        mApi = api;
        mIoScheduler = ioScheduler;
        mMainThreadScheduler = mainThreadScheduler;
    }

    @Override
    public void requestArrivals(String stopId) {
        mApi.getArrivals(stopId)
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
                .observeOn(mMainThreadScheduler)
                .subscribe(mController::onArrivalsLoaded);
    }
}
