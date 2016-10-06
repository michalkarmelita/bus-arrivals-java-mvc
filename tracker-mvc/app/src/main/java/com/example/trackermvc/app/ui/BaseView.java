package com.example.trackermvc.app.ui;

import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * Base interface to be implemented by all views
 **/
public interface BaseView {
    /**
     * Returns the layout resource id to be inflated in the view
     *
     * @return Layout resource id to be inflated
     **/
    @LayoutRes
    int getLayoutResourceId();

    /**
     * Initialises the view (setting default values, setting up needed objects, etc)
     *
     * @param view Android view containing the displayed view hierarchy
     **/
    void init(View view);
}
