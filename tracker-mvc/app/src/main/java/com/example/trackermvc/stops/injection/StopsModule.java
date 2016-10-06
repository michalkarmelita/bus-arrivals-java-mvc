package com.example.trackermvc.stops.injection;

import android.support.v7.app.AppCompatActivity;

import com.example.trackermvc.app.injection.qualifiers.ForActivity;
import com.example.trackermvc.app.injection.scopes.PerActivity;
import com.example.trackermvc.app.manager.permissions.PermissionManager;
import com.example.trackermvc.app.manager.permissions.PermissionManagerImpl;
import com.example.trackermvc.app.ui.BaseView;
import com.example.trackermvc.stops.controller.StopsActivity;
import com.example.trackermvc.stops.controller.StopsController;
import com.example.trackermvc.stops.repository.StopsRepository;
import com.example.trackermvc.stops.repository.StopsRepositoryImpl;
import com.example.trackermvc.stops.ui.StopsView;
import com.example.trackermvc.stops.ui.StopsViewImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class StopsModule {

    private final StopsActivity mActivity;

    public StopsModule(StopsActivity activity) {
        mActivity = activity;
    }

    @Provides
    @ForActivity
    StopsActivity provideStopsActivity() {
        return mActivity;
    }

    @Provides
    @PerActivity
    StopsController provideStopsController() {
        return mActivity;
    }

    @Provides
    @PerActivity
    StopsView provideStopsView(StopsViewImpl view) {
        return view;
    }

    @Provides
    @PerActivity
    @ForActivity
    BaseView provideBaseView(StopsView view) {
        return view;
    }

    @Provides
    @PerActivity
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    @PerActivity
    PermissionManager providePermissionManager(PermissionManagerImpl permissionManager) {
        return permissionManager;
    }

    @Provides
    @PerActivity
    StopsRepository provideStopsRepository(StopsRepositoryImpl repository) {
        return repository;
    }
}
