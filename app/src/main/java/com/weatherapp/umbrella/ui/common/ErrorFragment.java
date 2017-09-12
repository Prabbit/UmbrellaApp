package com.weatherapp.umbrella.ui.common;

import com.weatherapp.umbrella.R;
import com.weatherapp.umbrella.models.common.ToolBarModel;

/**
 * Created by CodeWord on 9/11/2017.
 */

public class ErrorFragment extends BaseFragment {

    public static ErrorFragment newInstance() {
        ErrorFragment fragment = new ErrorFragment();
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.error_fragment;
    }


    @Override
    protected ToolBarModel getToolbarData() {
        return null;
    }

}
