package com.example.trackermvc.favorites.injection;


import com.example.trackermvc.app.injection.qualifiers.ForActivity;
import com.example.trackermvc.app.injection.scopes.PerActivity;
import com.example.trackermvc.app.ui.BaseView;
import com.example.trackermvc.favorites.controller.FavoritesActivity;
import com.example.trackermvc.favorites.controller.FavoritesController;
import com.example.trackermvc.favorites.persistence.FavouritesDbManager;
import com.example.trackermvc.favorites.persistence.FavouritesDbManagerImpl;
import com.example.trackermvc.favorites.ui.FavoritesView;
import com.example.trackermvc.favorites.ui.FavoritesViewImpl;
import com.example.trackermvc.favorites.ui.adapters.FavoritesAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class FavoritesModule {

    private final FavoritesActivity mActivity;

    public FavoritesModule(FavoritesActivity activity) {
        mActivity = activity;
    }

    @Provides
    @PerActivity
    FavoritesController provideFavoritesController() {
        return mActivity;
    }

    @Provides
    @PerActivity
    FavoritesAdapter provideFavoritesAdapter() {
        return new FavoritesAdapter();
    }

    @Provides
    @PerActivity
    FavoritesView provideStopsView(FavoritesViewImpl view) {
        return view;
    }

    @Provides
    @ForActivity
    @PerActivity
    BaseView provideBaseView(FavoritesView view) {
        return view;
    }

    @Provides
    @PerActivity
    FavouritesDbManager providesFavouritesDbManager(FavouritesDbManagerImpl dbManager) {
        return dbManager;
    }

}
