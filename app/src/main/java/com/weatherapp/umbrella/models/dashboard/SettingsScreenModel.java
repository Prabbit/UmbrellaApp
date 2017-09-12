package com.weatherapp.umbrella.models.dashboard;

import android.os.Parcel;

import com.weatherapp.umbrella.events.ScreenSwitcherEvent;
import com.weatherapp.umbrella.models.common.BaseModel;
import com.weatherapp.umbrella.models.common.ToolBarModel;
import com.weatherapp.umbrella.ui.dashboard.fragment.FragmentSettings;

/**
 * Created by CodeWord on 9/11/2017.
 */

public class SettingsScreenModel extends BaseModel {

    private ToolBarModel toolBarModel;

    public ToolBarModel getDashToolBarModel() {
        return toolBarModel;
    }

    public void setDashToolBarModel(ToolBarModel toolBarModel) {
        this.toolBarModel = toolBarModel;
    }


    public SettingsScreenModel() {
    }

    @Override
    public ScreenSwitcherEvent createScreenSwitcherEvent() {
        return new ScreenSwitcherEvent(FragmentSettings.newInstance(this),true,this);
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

    protected SettingsScreenModel(Parcel in) {
        super(in);
        this.toolBarModel = in.readParcelable(ToolBarModel.class.getClassLoader());
    }

    public static final Creator<SettingsScreenModel> CREATOR = new Creator<SettingsScreenModel>() {
        @Override
        public SettingsScreenModel createFromParcel(Parcel source) {
            return new SettingsScreenModel(source);
        }

        @Override
        public SettingsScreenModel[] newArray(int size) {
            return new SettingsScreenModel[size];
        }
    };
}
