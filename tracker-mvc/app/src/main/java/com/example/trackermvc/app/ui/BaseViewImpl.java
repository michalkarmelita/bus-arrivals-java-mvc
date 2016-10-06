package com.example.trackermvc.app.ui;

import android.view.View;

import butterknife.ButterKnife;

public abstract class BaseViewImpl implements BaseView {

    @Override
    public void init(View view) {
        ButterKnife.bind(this, view);
    }
}
