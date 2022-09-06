package com.lixiang.applist_0905.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.lixiang.applist_0905.model.AppInfo;

import java.util.ArrayList;
import java.util.List;

public class AppInfoUtil {

    private static AppInfoUtil mInstance = new AppInfoUtil();

    public static AppInfoUtil getInstance() {
        return mInstance;
    }

    public List<AppInfo> getAllAppInfo(Context context) {
        ArrayList<AppInfo> apps = new ArrayList(1000);

        PackageManager pm = context.getPackageManager();

        pm.getInstalledApplications(0).forEach( applicationInfo -> {

            apps.add(new AppInfo(
                    (String) pm.getApplicationLabel(applicationInfo),
                    applicationInfo.packageName,
                    applicationInfo.loadIcon(pm),
                    (applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0
            ));

        } );

        return apps;
    }

}
