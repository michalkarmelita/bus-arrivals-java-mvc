package com.example.trackermvc.stops.injection;

import com.example.trackermvc.app.injection.scopes.PerActivity;
import com.example.trackermvc.stops.controller.StopsActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(
        modules = {
                StopsModule.class,
                LocationModule.class
        }
)
public interface StopsComponent {
    void inject(StopsActivity activity);
}
