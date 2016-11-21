package com.example.trackermvc.favorites.injection;

import com.example.trackermvc.app.injection.scopes.PerActivity;
import com.example.trackermvc.favorites.controller.FavoritesActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(
        modules = FavoritesModule.class
)
public interface FavoritesComponent {
    void inject(FavoritesActivity activity);
}
