package com.weatherapp.umbrella.models.dashboard;

import android.os.Parcel;

import com.weatherapp.umbrella.events.ScreenSwitcherEvent;
import com.weatherapp.umbrella.models.common.BaseModel;
import com.weatherapp.umbrella.models.common.ToolBarModel;
import com.weatherapp.umbrella.ui.dashboard.fragment.FragmentDashboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by CodeWord on 9/11/2017.
 */

public class DashScreenModel extends BaseModel {

    private ToolBarModel toolBarModel;
    private LinkedHashMap<String, ArrayList<HourlyData>> dailyDataMap = new LinkedHashMap<>();
    private HashMap<String, HourlyData[]> highestLowestTemp = new HashMap<>();

    public ToolBarModel getDashToolBarModel() {
        return toolBarModel;
    }

    public void setDashToolBarModel(ToolBarModel toolBarModel) {
        this.toolBarModel = toolBarModel;
    }

    public HashMap<String, HourlyData[]> getHighestLowestTemp() {
        return highestLowestTemp;
    }

    public List<ArrayList<HourlyData>> getDailyDataList() {
        return new ArrayList<>(dailyDataMap.values());
    }

    public void setDailyDataMap(String key, HourlyData data) {
        sortTemp(key, data);
        if (dailyDataMap.containsKey(key)) {
            dailyDataMap.get(key).add(data);
        } else {
            ArrayList<HourlyData> hourlyDatas = new ArrayList<>();
            hourlyDatas.add(data);
            dailyDataMap.put(key, hourlyDatas);
        }
    }

    private void sortTemp(String key, HourlyData data) {
        HourlyData[] tempTuple;
        if (highestLowestTemp.containsKey(key)) {
            tempTuple = highestLowestTemp.get(key);
        } else {
            tempTuple = new HourlyData[2];
            tempTuple[0] = data;
            tempTuple[1] = data;
            highestLowestTemp.put(key,tempTuple);
            return;
        }
        if (Float.parseFloat(tempTuple[0].getTempF()) > Float.parseFloat
                (data.getTempF())) {
            tempTuple[0] = data;
            return;
        }
        if (Float.parseFloat(tempTuple[1].getTempF()) < Float
                .parseFloat(data.getTempF())) {
            tempTuple[1] = data;
            return;
        }
    }

    public DashScreenModel() {
    }

    @Override
    public ScreenSwitcherEvent createScreenSwitcherEvent() {
        return new ScreenSwitcherEvent(FragmentDashboard.newInstance(this), false, this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.toolBarModel, flags);
    }

    protected DashScreenModel(Parcel in) {
        super(in);
        this.toolBarModel = in.readParcelable(ToolBarModel.class.getClassLoader());
    }

    public static final Creator<DashScreenModel> CREATOR = new Creator<DashScreenModel>() {
        @Override
        public DashScreenModel createFromParcel(Parcel source) {
            return new DashScreenModel(source);
        }

        @Override
        public DashScreenModel[] newArray(int size) {
            return new DashScreenModel[size];
        }
    };
}
