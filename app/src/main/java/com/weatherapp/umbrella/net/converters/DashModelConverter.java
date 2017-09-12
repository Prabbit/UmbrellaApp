package com.weatherapp.umbrella.net.converters;

import com.weatherapp.umbrella.R;
import com.weatherapp.umbrella.UmbrellaApplication;
import com.weatherapp.umbrella.models.common.BaseModel;
import com.weatherapp.umbrella.models.common.ErrorModel;
import com.weatherapp.umbrella.models.common.ToolBarModel;
import com.weatherapp.umbrella.models.dashboard.DashScreenModel;
import com.weatherapp.umbrella.models.dashboard.HourlyData;
import com.weatherapp.umbrella.net.response.dashboard.HourlyConditionsResponse;
import com.weatherapp.umbrella.net.tos.CurrentObservation;
import com.weatherapp.umbrella.net.tos.FCTTIME;
import com.weatherapp.umbrella.net.tos.HourlyForecast;
import com.weatherapp.umbrella.utils.JsonSerializationHelper;
import com.weatherapp.umbrella.utils.Utility;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by CodeWord on 9/11/2017.
 */

public class DashModelConverter implements Convertible {

    DashScreenModel screenModel;

    @Override
    public BaseModel convert(String response) {
        if (StringUtils.isEmpty(response))
            return new ErrorModel();
        return toScreenModel(response);
    }

    private BaseModel toScreenModel(String response) {

        HourlyConditionsResponse hourlyResponse = JsonSerializationHelper
                .deserializeObject(HourlyConditionsResponse.class, response);

        if (hourlyResponse == null)
            return new ErrorModel();

        CurrentObservation currentObservation = hourlyResponse.getCurrentObservation();
        if (currentObservation == null)
            return new ErrorModel();

        if (currentObservation.getDisplayLocation() == null)
            return new ErrorModel();
        screenModel = new DashScreenModel();
        addDashToolBar(currentObservation);
        toCardData(hourlyResponse.getHourlyForecast());
        return screenModel;
    }

    private void toCardData(List<HourlyForecast> hourlyForecast) {
        if (CollectionUtils.isEmpty(hourlyForecast))
            return;
        for (HourlyForecast forecast : hourlyForecast) {
            FCTTIME fctTime = forecast.getFCTTIME();
            HourlyData hourlyData = new HourlyData();
            hourlyData.setDay(Utility.getDayHeader(fctTime));
            hourlyData.setLogo(forecast.getIcon());
            hourlyData.setTime(fctTime.getCivil());
            hourlyData.setTempF(forecast.getTemp().getEnglish());
            hourlyData.setTempC(forecast.getTemp().getMetric());
            hourlyData.setKey(fctTime.getYday());
            screenModel.setDailyDataMap(fctTime.getYday(), hourlyData);
        }
    }

    private void addDashToolBar(CurrentObservation currentObservation) {
        ToolBarModel toolBar = new ToolBarModel();
        toolBar.setCityName(currentObservation.getDisplayLocation().getFull());
        if (currentObservation.getTempF() >= 60)
            toolBar.setColorCode(UmbrellaApplication.getInstance().getResources().getColor(R
                    .color.warm));
        else
            toolBar.setColorCode(UmbrellaApplication.getInstance().getResources().getColor(R
                    .color.cool));
        toolBar.setDegreeC(String.valueOf(currentObservation.getTempC()));
        toolBar.setDegreeF(String.valueOf(currentObservation.getTempF()));
        toolBar.setCondition(currentObservation.getWeather());
        toolBar.setDashBoardView(true);
        screenModel.setDashToolBarModel(toolBar);
    }
}
