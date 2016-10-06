package com.example.trackermvc.app;

import android.app.Application;

import com.example.trackermvc.app.injection.AppComponent;
import com.example.trackermvc.app.injection.AppModule;
import com.example.trackermvc.app.injection.DaggerAppComponent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import timber.log.Timber;

public class App extends Application {

    @Inject
    Timber.Tree mLogger;

    private static App sApp;

    private final AppComponent mComponent;

    public App() {
        sApp = this;
        mComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
        mComponent.inject(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ButterKnife.setDebug(true);
        Timber.plant(mLogger);
    }

    public static App getApp() {
        return sApp;
    }

    public AppComponent getComponent() {
        return mComponent;
    }

}
