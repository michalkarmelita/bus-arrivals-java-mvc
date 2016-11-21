package com.example.trackermvc.app.ui;

import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.trackermvc.app.controllers.ToolbarActivityController;

import butterknife.ButterKnife;

public abstract class BaseActionBarView extends BaseViewImpl implements BaseView {
    private final ToolbarActivityController mController;

    protected ActionBar mActionBar;
    protected Toolbar mToolbar;

    public BaseActionBarView(ToolbarActivityController controller) {
        mController = controller;
    }

    @Override
    public void init(View view) {
        super.init(view);
        mToolbar = ButterKnife.findById(view, getToolbarId());
        mActionBar = mController.setUpToolbar(mToolbar);
        mActionBar.setDisplayShowTitleEnabled(false);
    }

    @IdRes
    protected abstract int getToolbarId();

    public void showNavigationIcon() {
        mActionBar.setDisplayHomeAsUpEnabled(true);
    }
}
