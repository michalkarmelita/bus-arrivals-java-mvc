package com.example.trackermvc.stops.network;


import com.example.trackermvc.app.injection.qualifiers.ForIoThread;
import com.example.trackermvc.app.injection.qualifiers.ForMainThread;
import com.example.trackermvc.app.network.TflApi;
import com.example.trackermvc.app.network.model.Line;
import com.example.trackermvc.stops.model.StopData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;

public class StopsNetworkManagerImpl implements StopsNetworkManager {

    private final TflApi mApi;
    private final Scheduler mIoScheduler;
    private final Scheduler mMainThreadScheduler;

    @Inject
    public StopsNetworkManagerImpl(TflApi api,
                                   @ForIoThread Scheduler ioScheduler,
                                   @ForMainThread Scheduler mainThreadScheduler) {
        mApi = api;
        mIoScheduler = ioScheduler;
        mMainThreadScheduler = mainThreadScheduler;
    }

    @Override
    public Observable<ArrayList<StopData>> getStops(double lat, double lon, String stopType, int radius) {
        return mApi.getStopPoints(lat, lon, stopType, radius)
                .subscribeOn(mIoScheduler)
                .flatMap(stopsResponse -> Observable.from(stopsResponse.getStopPoints())
                        .flatMap(stopPoint ->
                                Observable.from(stopPoint.getChildren())
//                                .map(child -> Observable.from(child.getLines()).map(new Func1<Line, String>() {
//                                    @Override
//                                    public String call(Line line) {
//                                        return line.getName();
//                                    }
//                                }))
                                        .map(child -> {
                                            return new StopData(child.getId(), child.getLat(), child.getLon(), child.getIndicator(), child.getStopLetter(), format(child.getCommonName()), new HashSet<Integer>());
                                        })
                        )
                        .collect(ArrayList<StopData>::new, List::add)
                )
                .observeOn(mMainThreadScheduler);
    }

    private String format(String name) {
        //// FIXME: 09/11/2016 remove spaces around " / " in name
        return name;
    }
}
