package com.weatherapp.umbrella.events;

import android.support.v4.app.Fragment;

import com.weatherapp.umbrella.models.common.BaseModel;

/**
 * Created by CodeWord on 9/11/2017.
 */

public class ScreenSwitcherEvent {
    private final boolean addToBackstack;
    private Fragment fragment;
    private BaseModel model;

    public ScreenSwitcherEvent(Fragment fragment, boolean addToBackstack, BaseModel model) {
        this.fragment = fragment;
        this.addToBackstack = addToBackstack;
        this.model = model;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public BaseModel getModel() {
        return model;
    }

    public boolean isAddToBackstack() {
        return addToBackstack;
    }
}
