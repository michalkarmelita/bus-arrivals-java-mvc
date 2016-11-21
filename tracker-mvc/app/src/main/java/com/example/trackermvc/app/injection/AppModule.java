package com.example.trackermvc.app.injection;

import android.content.Context;
import android.content.res.Resources;

import com.example.trackermvc.app.App;
import com.example.trackermvc.app.injection.qualifiers.ForApplication;
import com.example.trackermvc.app.injection.qualifiers.ForIoThread;
import com.example.trackermvc.app.injection.qualifiers.ForMainThread;
import com.example.trackermvc.app.injection.scopes.PerApplication;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

@Module
public class AppModule {

    private final App mApp;

    public AppModule(App app) {
        mApp = app;
    }

    @Provides
    @PerApplication
    @ForApplication
    Context provideApplicationContext() {
        return mApp;
    }

    @Provides
    @PerApplication
    Resources provideResources(@ForApplication Context context) {
        return context.getResources();
    }

    @Provides
    @PerApplication
    Timber.Tree provideLogger() {
        return new Timber.DebugTree();
    }

    @Provides
    @PerApplication
    @ForIoThread
    Scheduler provideIoScheduler() {
        return Schedulers.io();
    }

    @Provides
    @PerApplication
    @ForMainThread
    Scheduler provideMainScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @PerApplication
    Gson provideGson() {
        return new Gson();
    }
}
