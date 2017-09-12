package com.weatherapp.umbrella.ui.dashboard.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.weatherapp.umbrella.R;
import com.weatherapp.umbrella.models.common.BaseModel;
import com.weatherapp.umbrella.models.common.ToolBarModel;
import com.weatherapp.umbrella.models.dashboard.SettingsScreenModel;
import com.weatherapp.umbrella.ui.common.BaseFragment;
import com.weatherapp.umbrella.utils.UiUtility;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.OnClick;

/**
 * Created by CodeWord on 9/11/2017.
 */

public class FragmentSettings extends BaseFragment {

    private SettingsScreenModel screenModel;

    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    EventBus eventBus;

    public static FragmentSettings newInstance(BaseModel baseModel) {
        FragmentSettings fragment = new FragmentSettings();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_PARAMS, baseModel);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void injectForDI() {
        getApplicationComponent().inject(this);
    }

    @Override
    protected void loadFragmentArguments() {
        screenModel = getArguments().getParcelable(EXTRA_PARAMS);
    }

    @Override
    protected void initialiseView(View rootView) {

    }

    @Override
    protected int getLayout() {
        return R.layout.settings_fragment;
    }


    @Override
    protected ToolBarModel getToolbarData() {
        return screenModel.getDashToolBarModel();
    }

    @OnClick(R.id.units)
    public void onUnitClicked() {
        UiUtility.showUnitsDialog(getContext(), sharedPreferences);
    }

    @OnClick(R.id.zip)
    public void onZipClicked() {
        UiUtility.showChangeZipDialog(getContext(), sharedPreferences, eventBus);
    }

}
