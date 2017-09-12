package com.weatherapp.umbrella.models.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.weatherapp.umbrella.events.ScreenSwitcherEvent;

/**
 * Created by CodeWord on 9/11/2017.
 */

public class BaseModel implements Parcelable {


    public BaseModel() {
    }

    public ScreenSwitcherEvent createScreenSwitcherEvent() {
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    protected BaseModel(Parcel in) {
    }

    public static final Creator<BaseModel> CREATOR = new Creator<BaseModel>() {
        @Override
        public BaseModel createFromParcel(Parcel source) {
            return new BaseModel(source);
        }

        @Override
        public BaseModel[] newArray(int size) {
            return new BaseModel[size];
        }
    };
}
