package com.example.trackermvc.app.injection;

import com.example.trackermvc.app.injection.scopes.PerApplication;
import com.example.trackermvc.app.persistence.DbManager;
import com.example.trackermvc.app.persistence.DbManagerImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

    @Provides
    @PerApplication
    DbManager provideFavouritesDbManager(DbManagerImpl manager) {
        return manager;
    }
}
