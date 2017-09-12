package com.weatherapp.umbrella.utils;

import android.content.Context;

import com.google.common.collect.ImmutableMap;
import com.weatherapp.umbrella.R;
import com.weatherapp.umbrella.net.tos.FCTTIME;

import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;


/**
 * Created by CodeWord on 9/11/2017.
 */

public class Utility {

    public static String getDayHeader(FCTTIME fctTime) {
        Calendar calendar = Calendar.getInstance();
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR) - 1;
        if (StringUtils.equalsIgnoreCase(String.valueOf(dayOfYear), fctTime.getYday()))
            return "Today";
        else if (StringUtils.equalsIgnoreCase(String.valueOf(dayOfYear + 1), fctTime.getYday()))
            return "Tomorrow";
        else
            return fctTime.getWeekdayName();
    }

    private static ImmutableMap<String, Integer> iconMap;

    static {
        iconMap = ImmutableMap.<String, Integer>builder()
                .put("clear", R.drawable.weather_sunny)
                .put("partlycloudy", R.drawable.weather_partlycloudy)
                .put("mostlycloudy", R.drawable.weather_cloudy)
                .put("fog", R.drawable.weather_fog)
                .put("rain", R.drawable.weather_rainy)
                .build();
    }

    public static int getDrawableId(String icon) {
        if (iconMap.containsKey(icon)) {
            return iconMap.get(icon);
        }
        return R.drawable.weather_sunny;
    }

    public static int dpToPx(int dp, Context context) {
        return (int) (dp * context.getResources().getSystem().getDisplayMetrics().density);
    }

    public static String getAppName(Context app) {
        return "Umbrella";
    }

}
