package com.weatherapp.umbrella.ui.dashboard.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weatherapp.umbrella.R;
import com.weatherapp.umbrella.models.dashboard.DashScreenModel;
import com.weatherapp.umbrella.models.dashboard.HourlyData;
import com.weatherapp.umbrella.ui.custom_components.GridDividerItemDecorator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by CodeWord on 9/12/2017.
 */

public class DailyForecastAdapter extends RecyclerView.Adapter {

    private final Context context;
    private final SharedPreferences sharedPref;
    private List<ArrayList<HourlyData>> dailyData;
    private HashMap<String, HourlyData[]> tempTupleMap;

    public DailyForecastAdapter(DashScreenModel model, Context context,
                                SharedPreferences sharedPreferences) {
        this.dailyData = model.getDailyDataList();
        this.tempTupleMap = model.getHighestLowestTemp();
        this.context = context;
        this.sharedPref= sharedPreferences;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        vh.dayHeader.setText(dailyData.get(position).get(0).getDay());
        GridLayoutManager layoutManager = new GridLayoutManager(context, 4,
                LinearLayoutManager.VERTICAL, false);
        vh.hourlyGrid.setLayoutManager(layoutManager);
        vh.hourlyGrid.addItemDecoration(new GridDividerItemDecorator());
        ArrayList<HourlyData> hourlyData = dailyData.get(position);
        HourlyForecastAdapter adapter = new HourlyForecastAdapter(hourlyData,sharedPref,
                tempTupleMap.get(hourlyData.get(0).getKey()));
        vh.hourlyGrid.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return dailyData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.dayHeader)
        public TextView dayHeader;
        @BindView(R.id.hourlyGrid)
        public RecyclerView hourlyGrid;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}