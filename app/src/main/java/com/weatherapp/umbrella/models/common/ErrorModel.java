package com.weatherapp.umbrella.models.common;

import com.weatherapp.umbrella.events.ScreenSwitcherEvent;
import com.weatherapp.umbrella.ui.common.ErrorFragment;

/**
 * Created by CodeWord on 9/11/2017.
 */

public class ErrorModel extends BaseModel {

    @Override
    public ScreenSwitcherEvent createScreenSwitcherEvent() {
        return new ScreenSwitcherEvent(ErrorFragment.newInstance(),true,this);
    }
}
