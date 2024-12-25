package com.example.batteryusageapp;

public class AppUsageInfo {
    private String packageName;
    private long usageTime;

    public AppUsageInfo(String packageName, long usageTime) {
        this.packageName = packageName;
        this.usageTime = usageTime;
    }

    public String getPackageName() {
        return packageName;
    }

    public long getUsageTime() {
        return usageTime;
    }
}
