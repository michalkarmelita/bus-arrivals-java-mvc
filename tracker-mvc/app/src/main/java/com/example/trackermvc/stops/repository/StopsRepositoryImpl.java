package com.example.trackermvc.stops.repository;


import com.example.trackermvc.app.network.NetworkManager;
import com.example.trackermvc.stops.controller.StopsController;
import com.example.trackermvc.stops.model.StopData;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;

public class StopsRepositoryImpl implements StopsRepository {
    private static final String STOP_TYPE =
            "NaptanBusCoachStation," +
                    "NaptanBusWayPoint," +
                    "NaptanCoachAccessArea," +
                    "NaptanCoachBay," +
                    "NaptanCoachEntrance," +
                    "NaptanCoachServiceCoverage," +
                    "NaptanCoachVariableBay," +
                    "NaptanPublicBusCoachTram," +
                    "NaptanOnstreetBusCoachStopPair";

    private final NetworkManager mNetworkManager;
    private final StopsController mController;

    @Inject
    public StopsRepositoryImpl(StopsController controller,
                               NetworkManager networkManager) {
        mNetworkManager = networkManager;
        mController = controller;
    }

    @Override
    public void requestStops(double lat, double lon) {
        mNetworkManager.getStops(lat, lon, STOP_TYPE, 500)
                .flatMap(stopsResponse -> Observable.from(stopsResponse.getStopPoints())
                        .flatMap(stopPoint ->
                                Observable.from(stopPoint.getChildren())
                                        .map(child -> new StopData(child.getId(), child.getLat(), child.getLon(), child.getIndicator(), child.getCommonName()))
                        )
                        .collect(ArrayList<StopData>::new, ArrayList::add)
                ).subscribe(mController::onStopsLoaded, throwable -> {
            mController.onStopsLoadingError();
        });
    }
}