package com.example.trackermvc.app.injection;

import com.example.trackermvc.app.controllers.BaseActivity;
import com.example.trackermvc.app.injection.scopes.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final BaseActivity mActivity;

    public ActivityModule(BaseActivity activity) {
        mActivity = activity;
    }


}
