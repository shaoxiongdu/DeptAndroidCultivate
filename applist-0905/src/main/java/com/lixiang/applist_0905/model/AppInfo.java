package com.lixiang.applist_0905.model;

import android.graphics.drawable.Drawable;

public class AppInfo {

    public String label;
    public String packageName;
    public Drawable icon;
    public boolean isSystem;
    public OnUninstallClickListener mOnUninstallClickListener;

    public AppInfo(String label, String packageName, Drawable icon, boolean isSystem) {
        this.label = label;
        this.packageName = packageName;
        this.icon = icon;
        this.isSystem = isSystem;
    }

    public interface OnUninstallClickListener{
        void onUninstallClick(AppInfo appInfo);
    }
}