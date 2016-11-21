package com.example.trackermvc.arrivals.injection;

import com.example.trackermvc.app.injection.qualifiers.ForActivity;
import com.example.trackermvc.app.injection.scopes.PerActivity;
import com.example.trackermvc.app.ui.BaseView;
import com.example.trackermvc.arrivals.controller.ArrivalsActivity;
import com.example.trackermvc.arrivals.controller.ArrivalsController;
import com.example.trackermvc.arrivals.network.ArrivalsNetworkManager;
import com.example.trackermvc.arrivals.network.ArrivalsNetworkManagerImpl;
import com.example.trackermvc.arrivals.repository.ArrivalsRepository;
import com.example.trackermvc.arrivals.repository.ArrivalsRepositoryImpl;
import com.example.trackermvc.arrivals.ui.ArrivalsView;
import com.example.trackermvc.arrivals.ui.ArrivalsViewImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class ArrivalsModule {

    private final ArrivalsActivity mActivity;

    public ArrivalsModule(ArrivalsActivity activity) {
        mActivity = activity;
    }

    @Provides
    @PerActivity
    ArrivalsController provideArrivalsController() {
        return mActivity;
    }

    @Provides
    @PerActivity
    ArrivalsView provideArrivalsView(ArrivalsViewImpl view) {
        return view;
    }

    @Provides
    @PerActivity
    @ForActivity
    BaseView provideBaseView(ArrivalsView view) {
        return view;
    }

    @Provides
    @PerActivity
    ArrivalsNetworkManager provideArrivalsNetworkManager(ArrivalsNetworkManagerImpl manager) {
        return manager;
    }

    @Provides
    @PerActivity
    ArrivalsRepository provideArrivalsRepository(ArrivalsRepositoryImpl repository) {
        return repository;
    }

}
