package com.weatherapp.umbrella.net.response.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.weatherapp.umbrella.net.tos.CurrentObservation;
import com.weatherapp.umbrella.net.tos.HourlyForecast;

import java.util.List;

public class HourlyConditionsResponse {

    @SerializedName("current_observation")
    @Expose
    private CurrentObservation currentObservation;
    @SerializedName("hourly_forecast")
    @Expose
    private List<HourlyForecast> hourlyForecast = null;

    public CurrentObservation getCurrentObservation() {
        return currentObservation;
    }


    public List<HourlyForecast> getHourlyForecast() {
        return hourlyForecast;
    }

}