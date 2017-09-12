package com.weatherapp.umbrella.presenters.dashboard;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.weatherapp.umbrella.R;
import com.weatherapp.umbrella.UmbrellaApplication;
import com.weatherapp.umbrella.models.common.BaseModel;
import com.weatherapp.umbrella.models.common.ErrorModel;
import com.weatherapp.umbrella.models.common.ToolBarModel;
import com.weatherapp.umbrella.models.dashboard.SettingsScreenModel;
import com.weatherapp.umbrella.net.converters.DashModelConverter;
import com.weatherapp.umbrella.presenters.common.BasePresenter;
import com.weatherapp.umbrella.utils.Utility;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.weatherapp.umbrella.utils.Constants.API;
import static com.weatherapp.umbrella.utils.Constants.JSON_SUFFIX;
import static com.weatherapp.umbrella.utils.Constants.ZIPCODE;

/**
 * Created by CodeWord on 9/11/2017.
 */

public class FetchHourlyInfoPresenter extends BasePresenter {

    private static final String URL = "/conditions/hourly/q/";
    private SharedPreferences sharedPreferences;

    public FetchHourlyInfoPresenter(EventBus eventBus, RequestQueue requestQueue,
                                    SharedPreferences sharedPreferences) {
        super(eventBus, requestQueue);
        this.sharedPreferences = sharedPreferences;
    }

    public static String readFakeResponseFromAssets(Context context) throws IOException {
        InputStream stream = context.getResources().openRawResource(R.raw.temp);
        InputStreamReader streamReader = new InputStreamReader(stream, Charsets.UTF_8);
        String string = CharStreams.toString(streamReader);
        streamReader.close();
        stream.close();
        return string;
    }

    @Override
    public void executeRequest() {

//        String response = null;
//        try {
//            response = readFakeResponseFromAssets(UmbrellaApplication.getInstance()
//                    .getApplicationContext());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, buildResourceUrl(URL),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DashModelConverter converter = new DashModelConverter();
                        BaseModel screenModel = converter.convert(response);
                        broadcastResponse(screenModel);
                        hideProgress();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgress();
                broadcastResponse(new ErrorModel());
            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    protected String buildResourceUrl(String resource) {
        StringBuilder url = new StringBuilder();
        CharSequence baseUrl = getBaseURL();
        url.append(baseUrl);
        url.append(API);
        url.append(getAPIKey());
        url.append(resource);
        url.append(sharedPreferences.getString(ZIPCODE, ""));
        url.append(JSON_SUFFIX);
        return url.toString();
    }

    public void showSettingsPage(Context context) {
        SettingsScreenModel screenModel = new SettingsScreenModel();
        ToolBarModel toolBar = new ToolBarModel();
        toolBar.setCityName(Utility.getAppName(context));
        toolBar.setColorCode(UmbrellaApplication.getInstance().getResources().getColor(R
                .color.settings));
        toolBar.setDashBoardView(false);
        screenModel.setDashToolBarModel(toolBar);
        broadcastResponse(screenModel);
    }
}
