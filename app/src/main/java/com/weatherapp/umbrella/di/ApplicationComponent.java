package com.weatherapp.umbrella.di;

import com.weatherapp.umbrella.ui.dashboard.activity.MainActivity;
import com.weatherapp.umbrella.ui.dashboard.fragment.FragmentDashboard;
import com.weatherapp.umbrella.ui.dashboard.fragment.FragmentSettings;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by CodeWord on 9/11/2017.
 */

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(FragmentDashboard fragmentDashboard);

    void inject(MainActivity mainActivity);

    void inject(FragmentSettings fragmentSettings);
}
