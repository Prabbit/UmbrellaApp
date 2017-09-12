package com.weatherapp.umbrella;

import android.app.Application;

import com.weatherapp.umbrella.di.ApplicationComponent;
import com.weatherapp.umbrella.di.ApplicationModule;
import com.weatherapp.umbrella.di.DaggerApplicationComponent;

/**
 * Created by CodeWord on 9/11/2017.
 */
public class UmbrellaApplication extends Application {

    private ApplicationComponent applicationComponent;

    private static UmbrellaApplication sInstance;

    public static UmbrellaApplication getInstance() {
        return sInstance;
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(sInstance))
                .build();
    }
}
