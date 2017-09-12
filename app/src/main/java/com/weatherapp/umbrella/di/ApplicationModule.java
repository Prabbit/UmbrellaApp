package com.weatherapp.umbrella.di;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.RequestQueue;
import com.weatherapp.umbrella.BuildConfig;
import com.weatherapp.umbrella.net.VolleySingleton;
import com.weatherapp.umbrella.presenters.dashboard.FetchHourlyInfoPresenter;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by CodeWord on 9/11/2017.
 */

@Module
public class ApplicationModule {

    Application app;

    public ApplicationModule(Application appContext) {
        app = appContext;
    }

    @Provides
    @Singleton
    public Application providesApplication() {
        return app;
    }

    @Provides
    @Singleton
    public SharedPreferences providesSharedPreferences(Application appContext) {
        return PreferenceManager.getDefaultSharedPreferences(appContext);
    }

    @Provides
    @Singleton
    public EventBus providesEventBus() {
        return EventBus.builder().throwSubscriberException(BuildConfig.DEBUG)
                .logNoSubscriberMessages(true).sendNoSubscriberEvent(true).build();
    }

    @Provides
    @Singleton
    public FetchHourlyInfoPresenter providesDataHubPresenter(EventBus eventBus, RequestQueue
            requestQueue, SharedPreferences sharedPreferences) {
        return new FetchHourlyInfoPresenter(eventBus,requestQueue, sharedPreferences);
    }

    @Provides
    public RequestQueue providesVolleyRequestQueue(Application app) {
        RequestQueue queue = VolleySingleton.getInstance(app).getRequestQueue();
        return queue;
    }

}
