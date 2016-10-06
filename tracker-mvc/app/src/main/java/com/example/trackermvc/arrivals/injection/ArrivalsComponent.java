package com.example.trackermvc.arrivals.injection;

import com.example.trackermvc.app.injection.scopes.PerActivity;
import com.example.trackermvc.arrivals.controller.ArrivalsActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(
        modules = {
                ArrivalsModule.class
        }
)
public interface ArrivalsComponent {

    void inject(ArrivalsActivity activity);

}
