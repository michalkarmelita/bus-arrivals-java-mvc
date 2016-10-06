package com.example.trackermvc.stops.injection;

import com.example.trackermvc.app.injection.scopes.PerActivity;
import com.example.trackermvc.stops.manager.LocationManager;
import com.example.trackermvc.stops.manager.LocationManagerImpl;
import com.google.android.gms.location.LocationRequest;

import dagger.Module;
import dagger.Provides;

@Module
public class LocationModule {
    private static final long MAP_LOCATION_INTERVAL = 60000;
    private static final long MAP_LOCATION_FASTEST_INTERVAL = 5000;

    public LocationModule() {
    }

    @Provides
    @PerActivity
    LocationRequest provideLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(MAP_LOCATION_INTERVAL);
        locationRequest.setFastestInterval(MAP_LOCATION_FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        return locationRequest;
    }

    @Provides
    @PerActivity
    LocationManager provideLocationManager(LocationManagerImpl locationManager) {
        return locationManager;
    }
}
