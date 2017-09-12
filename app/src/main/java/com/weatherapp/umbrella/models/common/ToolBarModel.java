package com.weatherapp.umbrella.models.common;

import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

import static com.weatherapp.umbrella.utils.Constants.UNIT;
import static com.weatherapp.umbrella.utils.Constants.US;

/**
 * Created by CodeWord on 9/11/2017.
 */

public class ToolBarModel implements Parcelable {
    private String title;
    private String degreeC;
    private String degreeF;
    private String condition;
    private int colorCode;
    private boolean isDashboardView;

    public String getTitle() {
        return title;
    }

    public void setCityName(String cityName) {
        this.title = cityName;
    }

    public void setDegreeC(String degreeC) {
        this.degreeC = degreeC;
    }

    public String getTemperature(SharedPreferences sharedPrefs) {
        int unitPref = sharedPrefs.getInt(UNIT,US);
        if (unitPref==US){
            return degreeF+ (char) 0x00B0;
        }
        return degreeC + (char) 0x00B0;
    }

    public void setDegreeF(String degreeF) {
        this.degreeF = degreeF;
    }

    public String getCondition() {
        return condition;
    }

    public void setDashBoardView(boolean isDashBoardView) {
        this.isDashboardView = isDashBoardView;
    }

    public boolean isDashboardView() {
        return isDashboardView;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getColorCode() {
        return colorCode;
    }

    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
    }

    public ToolBarModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.degreeC);
        dest.writeString(this.degreeF);
        dest.writeString(this.condition);
        dest.writeInt(this.colorCode);
        dest.writeByte(this.isDashboardView ? (byte) 1 : (byte) 0);
    }

    protected ToolBarModel(Parcel in) {
        this.title = in.readString();
        this.degreeC = in.readString();
        this.degreeF = in.readString();
        this.condition = in.readString();
        this.colorCode = in.readInt();
        this.isDashboardView = in.readByte() != 0;
    }

    public static final Creator<ToolBarModel> CREATOR = new Creator<ToolBarModel>() {
        @Override
        public ToolBarModel createFromParcel(Parcel source) {
            return new ToolBarModel(source);
        }

        @Override
        public ToolBarModel[] newArray(int size) {
            return new ToolBarModel[size];
        }
    };
}
