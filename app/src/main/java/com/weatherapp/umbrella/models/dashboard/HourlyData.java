package com.weatherapp.umbrella.models.dashboard;

import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

import static com.weatherapp.umbrella.utils.Constants.UNIT;
import static com.weatherapp.umbrella.utils.Constants.US;

/**
 * Created by CodeWord on 9/11/2017.
 */

public class HourlyData implements Parcelable {

    private String time;
    private String key;
    private String day;
    private String logo;
    private String tempC;
    private String tempF;

    public String getTempF() {
        return tempF;
    }

    public String getTime() {
        return time;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setTempC(String tempC) {
        this.tempC = tempC;
    }

    public void setTempF(String tempF) {
        this.tempF = tempF;
    }

    public String getTemp(SharedPreferences sharedPrefs){
        int unitPref = sharedPrefs.getInt(UNIT, US);
        if (unitPref==US) {
            return tempF+ (char) 0x00B0;
        }
        return tempC+ (char) 0x00B0;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.time);
        dest.writeString(this.day);
        dest.writeString(this.logo);
        dest.writeString(this.tempF);
        dest.writeString(this.tempC);
    }

    public HourlyData() {
    }

    protected HourlyData(Parcel in) {
        this.time = in.readString();
        this.day = in.readString();
        this.logo = in.readString();
        this.tempF = in.readString();
        this.tempC = in.readString();
    }

    public static final Creator<HourlyData> CREATOR = new Creator<HourlyData>() {
        @Override
        public HourlyData createFromParcel(Parcel source) {
            return new HourlyData(source);
        }

        @Override
        public HourlyData[] newArray(int size) {
            return new HourlyData[size];
        }
    };
}
