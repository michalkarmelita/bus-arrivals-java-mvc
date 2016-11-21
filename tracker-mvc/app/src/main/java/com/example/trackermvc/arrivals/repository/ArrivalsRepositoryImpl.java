package com.example.trackermvc.arrivals.repository;


import com.example.trackermvc.arrivals.controller.ArrivalsController;
import com.example.trackermvc.arrivals.network.ArrivalsNetworkManager;

import javax.inject.Inject;

import rx.Subscription;

public class ArrivalsRepositoryImpl implements ArrivalsRepository {

    private final ArrivalsController mController;
    private final ArrivalsNetworkManager mNetworkManager;
    private Subscription mSubscription;

    @Inject
    public ArrivalsRepositoryImpl(ArrivalsController controller,
                                  ArrivalsNetworkManager networkManager) {
        mController = controller;
        mNetworkManager = networkManager;
    }

    @Override
    public void requestArrivals(String stopId) {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        mSubscription = mNetworkManager.getArrivals(stopId)
                .subscribe(mController::onArrivalsLoaded);
    }
}
