package com.weatherapp.umbrella.ui.dashboard.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.weatherapp.umbrella.R;
import com.weatherapp.umbrella.callbacks.ScreenRefresher;
import com.weatherapp.umbrella.events.ParamsChangeEvent;
import com.weatherapp.umbrella.models.common.BaseModel;
import com.weatherapp.umbrella.models.common.ToolBarModel;
import com.weatherapp.umbrella.models.dashboard.DashScreenModel;
import com.weatherapp.umbrella.presenters.dashboard.FetchHourlyInfoPresenter;
import com.weatherapp.umbrella.ui.common.BaseFragment;
import com.weatherapp.umbrella.ui.custom_components.SpaceDividerItemDecorator;
import com.weatherapp.umbrella.ui.dashboard.adapters.DailyForecastAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by CodeWord on 9/11/2017.
 */

public class FragmentDashboard extends BaseFragment implements ScreenRefresher {

    private DashScreenModel screenModel;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    EventBus eventBus;
    @Inject
    FetchHourlyInfoPresenter presenter;

    public static FragmentDashboard newInstance(BaseModel baseModel) {
        FragmentDashboard fragment = new FragmentDashboard();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_PARAMS, baseModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!eventBus.isRegistered(this))
            eventBus.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (eventBus.isRegistered(this))
            eventBus.unregister(this);
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
        DailyForecastAdapter dailyForcastAdapter = new DailyForecastAdapter(screenModel, getContext(), sharedPreferences);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SpaceDividerItemDecorator());
        recyclerView.setAdapter(dailyForcastAdapter);
    }

    @Override
    protected int getLayout() {
        return R.layout.dash_fragment;
    }


    @Override
    protected ToolBarModel getToolbarData() {
        return screenModel.getDashToolBarModel();
    }

    @Override
    public void refreshScreen(BaseModel model) {
        screenModel = (DashScreenModel) model;
        initialiseView(getView());
        updateToolbar(screenModel.getDashToolBarModel());
    }

    @Subscribe(sticky = true)
    public void onParamsChange(ParamsChangeEvent event) {
        eventBus.removeStickyEvent(event);
        presenter.queueRequest();
    }
}
