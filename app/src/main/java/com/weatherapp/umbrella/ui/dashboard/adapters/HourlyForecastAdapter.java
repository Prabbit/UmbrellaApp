package com.weatherapp.umbrella.ui.dashboard.adapters;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.weatherapp.umbrella.R;
import com.weatherapp.umbrella.UmbrellaApplication;
import com.weatherapp.umbrella.models.dashboard.HourlyData;
import com.weatherapp.umbrella.utils.Utility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by CodeWord on 9/12/2017.
 */

public class HourlyForecastAdapter extends RecyclerView.Adapter {

    private ArrayList<HourlyData> dataItem;
    private SharedPreferences sharedPreferences;
    private HourlyData[] tempTuple;
    private int warmColor;
    private int coolColor;

    public HourlyForecastAdapter(ArrayList<HourlyData> dataList, SharedPreferences
            sharedPreferences, HourlyData[] hourlyDatas) {
        this.dataItem = dataList;
        this.sharedPreferences = sharedPreferences;
        this.tempTuple = hourlyDatas;
        Resources resources = UmbrellaApplication.getInstance().getResources();
        warmColor = resources.getColor(R.color.warm);
        coolColor = resources.getColor(R.color.cool);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        HourlyData data = dataItem.get(position);
        vh.time.setText(data.getTime());
        vh.temp.setText(data.getTemp(sharedPreferences));
        vh.icon.setImageResource(Utility.getDrawableId(data.getLogo()));
        if (tempTuple==null || tempTuple.length < 2)
            return;
        HourlyData lowTemp = tempTuple[0];
        HourlyData highTemp = tempTuple[1];
        if (lowTemp == highTemp) {
            return;
        }
        if (data == lowTemp) {
            vh.time.setTextColor(coolColor);
            vh.temp.setTextColor(coolColor);
            vh.icon.getDrawable().mutate().setColorFilter(coolColor, PorterDuff.Mode.SRC_IN);
            return;
        }
        if (data == highTemp) {
            vh.time.setTextColor(warmColor);
            vh.temp.setTextColor(warmColor);
            vh.icon.getDrawable().mutate().setColorFilter(warmColor, PorterDuff.Mode.SRC_IN);
            return;
        }
    }

    @Override
    public int getItemCount() {
        return dataItem.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.time)
        public TextView time;
        @BindView(R.id.temp)
        public TextView temp;
        @BindView(R.id.icon)
        public ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}