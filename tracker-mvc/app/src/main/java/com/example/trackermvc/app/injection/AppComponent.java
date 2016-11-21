package com.example.trackermvc.app.injection;

import com.example.trackermvc.app.App;
import com.example.trackermvc.app.injection.scopes.PerApplication;
import com.example.trackermvc.arrivals.injection.ArrivalsComponent;
import com.example.trackermvc.arrivals.injection.ArrivalsModule;
import com.example.trackermvc.favorites.injection.FavoritesComponent;
import com.example.trackermvc.favorites.injection.FavoritesModule;
import com.example.trackermvc.stops.injection.LocationModule;
import com.example.trackermvc.stops.injection.StopsComponent;
import com.example.trackermvc.stops.injection.StopsModule;

import dagger.Component;

@PerApplication
@Component(
        modules = {
                AppModule.class,
                NetworkModule.class,
                DbModule.class
        }
)
public interface AppComponent {
    void inject(App app);

    StopsComponent add(StopsModule module, LocationModule locationModule);

    ArrivalsComponent add(ArrivalsModule module);

    FavoritesComponent add(FavoritesModule module);
}
