package com.example.trackermvc.app.network;


import com.example.trackermvc.app.injection.qualifiers.ForIoThread;
import com.example.trackermvc.app.injection.qualifiers.ForMainThread;
import com.example.trackermvc.app.network.model.StopsResponse;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;

public class NetworkManagerImpl implements NetworkManager {

    private final TflApi mApi;
    private final Scheduler mIoScheduler;
    private final Scheduler mMainThreadScheduler;

    @Inject
    public NetworkManagerImpl(TflApi api,
                              @ForIoThread Scheduler ioScheduler,
                              @ForMainThread Scheduler mainThreadScheduler) {
        mApi = api;
        mIoScheduler = ioScheduler;
        mMainThreadScheduler = mainThreadScheduler;
    }

    @Override
    public Observable<StopsResponse> getStops(double lat, double lon, String stopType, int radius) {
        return mApi.getStopPoints(lat, lon, stopType, radius)
                .subscribeOn(mIoScheduler)
                .observeOn(mMainThreadScheduler);
    }
}
