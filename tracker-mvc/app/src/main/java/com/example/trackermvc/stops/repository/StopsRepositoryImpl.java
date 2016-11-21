package com.example.trackermvc.stops.repository;


import com.example.trackermvc.stops.network.StopsNetworkManager;
import com.example.trackermvc.stops.controller.StopsController;
import com.example.trackermvc.stops.model.StopData;
import com.example.trackermvc.stops.persistence.StopsDbManager;

import javax.inject.Inject;

public class StopsRepositoryImpl implements StopsRepository {


//    private static final String STOP_TYPE =
//            "NaptanBusCoachStation," +
//                    "NaptanBusWayPoint," +
//                    "NaptanCoachAccessArea," +
//                    "NaptanCoachBay," +
//                    "NaptanCoachEntrance," +
//                    "NaptanCoachServiceCoverage," +
//                    "NaptanCoachVariableBay," +
//                    "NaptanPublicBusCoachTram," +
//                    "NaptanOnstreetBusCoachStopPair" +
//                    "NaptanOnstreetBusCoachStopCluster";

    private static final String STOP_TYPE = "NaptanBusCoachStation,NaptanBusWayPoint,NaptanCoachAccessArea,NaptanCoachBay,NaptanCoachEntrance,NaptanCoachServiceCoverage,NaptanCoachVariableBay,NaptanOnstreetBusCoachStopCluster,NaptanOnstreetBusCoachStopPair,NaptanPrivateBusCoachTram,NaptanPublicBusCoachTram";

//    private static final String STOP_TYPE = "CarPickupSetDownArea,NaptanAirAccessArea,NaptanAirEntrance,NaptanAirportBuilding,NaptanBusCoachStation,NaptanBusWayPoint,NaptanCoachAccessArea,NaptanCoachBay,NaptanCoachEntrance,NaptanCoachServiceCoverage,NaptanCoachVariableBay,NaptanFerryAccessArea,NaptanFerryBerth,NaptanFerryEntrance,NaptanFerryPort,NaptanFlexibleZone,NaptanHailAndRideSection,NaptanLiftCableCarAccessArea,NaptanLiftCableCarEntrance,NaptanLiftCableCarStop,NaptanLiftCableCarStopArea,NaptanMarkedPoint,NaptanMetroAccessArea,NaptanMetroEntrance,NaptanMetroPlatform,NaptanMetroStation,NaptanOnstreetBusCoachStopCluster,NaptanOnstreetBusCoachStopPair,NaptanPrivateBusCoachTram,NaptanPublicBusCoachTram,NaptanRailAccessArea,NaptanRailEntrance,NaptanRailPlatform,NaptanRailStation,NaptanSharedTaxi,NaptanTaxiRank,NaptanUnmarkedPoint,TransportInterchange";

    private final StopsNetworkManager mStopsNetworkManager;
    private final StopsController mController;
    private final StopsDbManager mDbManager;

    @Inject
    public StopsRepositoryImpl(StopsController controller,
                               StopsNetworkManager stopsNetworkManager,
                               StopsDbManager dbManager) {
        mStopsNetworkManager = stopsNetworkManager;
        mController = controller;
        mDbManager = dbManager;
    }

    @Override
    public void requestStops(double lat, double lon) {
        mStopsNetworkManager.getStops(lat, lon, STOP_TYPE, 500)
                .subscribe(mController::onStopsLoaded, throwable -> {
            mController.onStopsLoadingError();
        });
    }

    @Override
    public void saveStop(StopData stopData) {
        mDbManager.saveFavoriteStop(stopData);
    }

    @Override
    public boolean isStopFavorite(StopData stopData) {
        return mDbManager.isStopFavorite(stopData);
    }

    @Override
    public void removeStop(StopData stopData) {
        mDbManager.removeFavoriteStop(stopData);
    }
}