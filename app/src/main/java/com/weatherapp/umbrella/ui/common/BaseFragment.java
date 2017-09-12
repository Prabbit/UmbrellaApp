package com.weatherapp.umbrella.ui.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weatherapp.umbrella.UmbrellaApplication;
import com.weatherapp.umbrella.callbacks.ToolbarDecorator;
import com.weatherapp.umbrella.di.ApplicationComponent;
import com.weatherapp.umbrella.models.common.ToolBarModel;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by CodeWord on 9/11/2017.
 */

public abstract class BaseFragment extends Fragment {
    protected static final String EXTRA_PARAMS = "extra_params";
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectForDI();
        loadFragmentArguments();
    }

    protected void injectForDI() {
    }

    protected void loadFragmentArguments() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = createRootView(inflater, container);
        unbinder = ButterKnife.bind(this, rootView);
        initialiseView(rootView);
        return rootView;
    }

    private View createRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(getLayout(), container, false);
    }

    protected abstract int getLayout();

    protected void initialiseView(View rootView) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onResume() {
        super.onResume();
        ToolBarModel toolbarData = getToolbarData();
        if (getActivity() instanceof ToolbarDecorator) {
            updateToolbar(toolbarData);
        }
    }

    protected void updateToolbar(ToolBarModel toolBarModel){
        ((ToolbarDecorator) getActivity()).decorateToolbar(toolBarModel);
    }

    protected ApplicationComponent getApplicationComponent() {
        return UmbrellaApplication.getInstance().getApplicationComponent();
    }

    protected abstract ToolBarModel getToolbarData();
}
