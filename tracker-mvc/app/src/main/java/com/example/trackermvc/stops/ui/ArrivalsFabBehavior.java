package com.example.trackermvc.stops.ui;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;

public class ArrivalsFabBehavior extends CoordinatorLayout.Behavior<FloatingActionButton> {


    public ArrivalsFabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        int[] dependencyLocation = new int[2];

        dependency.getLocationInWindow(dependencyLocation);

        int dy = dependencyLocation[1];

        if (parent.getBottom() - child.getPaddingBottom() < dy) {
            child.hide();
        } else {
            child.show();
        }

        return false;
    }
}
