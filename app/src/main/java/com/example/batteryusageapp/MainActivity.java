package com.example.batteryusageapp;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        if (!hasUsageStatsPermission()) {
            requestUsageStatsPermission();
        } else {
            displayBatteryUsage();
        }
    }

    private boolean hasUsageStatsPermission() {
        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        long currentTime = System.currentTimeMillis();
        List<UsageStats> stats = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY,
                currentTime - 1000 * 3600,
                currentTime
        );
        return stats != null && !stats.isEmpty();
    }

    private void requestUsageStatsPermission() {
        Toast.makeText(this, "Please enable Usage Access for this app.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivity(intent);
    }

    private void displayBatteryUsage() {
        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        long currentTime = System.currentTimeMillis();
        List<UsageStats> usageStatsList = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY,
                currentTime - 1000 * 3600 * 24,
                currentTime
        );

        if (usageStatsList == null || usageStatsList.isEmpty()) {
            Toast.makeText(this, "No usage data available.", Toast.LENGTH_SHORT).show();
            return;
        }

        List<AppUsageInfo> appUsageInfoList = new ArrayList<>();
        for (UsageStats stats : usageStatsList) {
            long totalTimeInForeground = stats.getTotalTimeInForeground();
            if (totalTimeInForeground > 0) {
                appUsageInfoList.add(new AppUsageInfo(
                        stats.getPackageName(),
                        totalTimeInForeground
                ));
            }
        }

        Collections.sort(appUsageInfoList, new Comparator<AppUsageInfo>() {
            @Override
            public int compare(AppUsageInfo o1, AppUsageInfo o2) {
                return Long.compare(o1.getUsageTime(), o2.getUsageTime());
            }
        });



        AppUsageAdapter adapter = new AppUsageAdapter(this, appUsageInfoList);
        listView.setAdapter(adapter);
    }
}
