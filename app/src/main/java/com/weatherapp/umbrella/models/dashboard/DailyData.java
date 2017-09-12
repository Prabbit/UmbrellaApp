package com.weatherapp.umbrella.models.dashboard;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by CodeWord on 9/11/2017.
 */

public class DailyData implements Parcelable {
    private String dayName;
    private ArrayList<HourlyData> hourlyDataList = new ArrayList<>();


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dayName);
        dest.writeTypedList(this.hourlyDataList);
    }

    public DailyData() {
    }

    protected DailyData(Parcel in) {
        this.dayName = in.readString();
        this.hourlyDataList = in.createTypedArrayList(HourlyData.CREATOR);
    }

    public static final Creator<DailyData> CREATOR = new Creator<DailyData>() {
        @Override
        public DailyData createFromParcel(Parcel source) {
            return new DailyData(source);
        }

        @Override
        public DailyData[] newArray(int size) {
            return new DailyData[size];
        }
    };
}
