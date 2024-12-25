package com.example.batteryusageapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AppUsageAdapter extends BaseAdapter {
    private Context context;
    private List<AppUsageInfo> appUsageInfoList;

    public AppUsageAdapter(Context context, List<AppUsageInfo> appUsageInfoList) {
        this.context = context;
        this.appUsageInfoList = appUsageInfoList;
    }

    @Override
    public int getCount() {
        return appUsageInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return appUsageInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        TextView packageNameTextView = convertView.findViewById(R.id.packageName);
        TextView usageTimeTextView = convertView.findViewById(R.id.usageTime);

        AppUsageInfo appUsageInfo = appUsageInfoList.get(position);

        packageNameTextView.setText(appUsageInfo.getPackageName());
        usageTimeTextView.setText("Time: " + (appUsageInfo.getUsageTime() / 1000) + " sec");

        return convertView;
    }
}
