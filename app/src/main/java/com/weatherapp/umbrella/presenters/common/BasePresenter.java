package com.weatherapp.umbrella.presenters.common;

import com.android.volley.RequestQueue;
import com.weatherapp.umbrella.events.DisplayProgressEvent;
import com.weatherapp.umbrella.events.HideProgressEvent;
import com.weatherapp.umbrella.models.common.BaseModel;

import org.greenrobot.eventbus.EventBus;

import static com.weatherapp.umbrella.utils.Constants.API_KEY;
import static com.weatherapp.umbrella.utils.Constants.BASE_URL;

/**
 * Created by CodeWord on 9/11/2017.
 */

public abstract class BasePresenter {

    protected final RequestQueue requestQueue;
    protected EventBus eventBus;

    public BasePresenter(EventBus eventBus, RequestQueue requestQueue) {
        this.eventBus = eventBus;
        this.requestQueue = requestQueue;
    }

    protected void broadcastResponse(BaseModel baseModel) {
        if (baseModel.createScreenSwitcherEvent() != null) {
            eventBus.postSticky(baseModel.createScreenSwitcherEvent());
        }
    }

    protected void showProgress() {
        eventBus.postSticky(new DisplayProgressEvent());
    }

    protected void hideProgress() {
        eventBus.postSticky(new HideProgressEvent());
    }

    public void queueRequest() {
        showProgress();
        executeRequest();
    }

    public abstract void executeRequest();

    protected abstract String buildResourceUrl(String resource);

    protected CharSequence getBaseURL() {
        return BASE_URL;
    }

    protected CharSequence getAPIKey() {
        return API_KEY;
    }
}
