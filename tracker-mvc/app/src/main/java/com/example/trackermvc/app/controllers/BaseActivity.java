package com.example.trackermvc.app.controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.trackermvc.app.injection.qualifiers.ForActivity;
import com.example.trackermvc.app.ui.BaseView;

import javax.inject.Inject;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public abstract class BaseActivity extends AppCompatActivity implements ToolbarActivityController {
    protected static final String[] MANDATORY_PERMISSIONS = {ACCESS_FINE_LOCATION};

    @Inject
    @ForActivity
    BaseView mView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initComponent();
        super.onCreate(savedInstanceState);
        setContentView(mView.getLayoutResourceId());
        mView.init(getWindow().getDecorView());
    }

    protected abstract void initComponent();

    @Override
    public ActionBar setUpToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        return getSupportActionBar();
    }
}
