package com.liauto.androidview;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * @author deshui
 * @date 2022/8/6
 */
public class HomeWidgetProvider extends AppWidgetProvider {
    private static final String TAG = "HomeWidgetProvider";
    //定义一个action，这个action要在AndroidMainfest中去定义，不然识别不到，名字是自定义的
    private static final String CLICK_ACTION = "com.lixiang.android.widget.LIXIANG.CLICK";

    //onReceive不存在widget生命周期中，它是用来接收广播，通知全局的
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d(TAG, "onReceive: " + intent.getAction());
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        //当我们点击桌面上的widget按钮（这个按钮我们在onUpdate中已经为它设置了监听），widget就会发送广播
        //这个广播我们也在onUpdate中为它设置好了意图，设置了action，在这里我们接收到对应的action并做相应处理
        if (intent.getAction().equals(CLICK_ACTION)) {
            //因为点击按钮后要对布局中的文本进行更新，所以需要创建一个远程view
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
            //为对应的TextView设置文本
            remoteViews.setTextViewText(R.id.btn, "按钮已经点击");
            //更新widget
            appWidgetManager.updateAppWidget(new ComponentName(context, HomeWidgetProvider.class), remoteViews);
        }
    }

    //当widget第一次添加到桌面的时候回调，可添加多次widget，但该方法只回调一次
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Toast.makeText(context, "创建成功", Toast.LENGTH_SHORT).show();
    }

    //当widget被初次添加或大小被改变时回调
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    /**
     * 当widget更新时回调
     *
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds     这个数组使用用来存储已经创建的widget的id，因为可能创建了多个widget
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d(TAG, "onUpdate: ");
        //因为可能有多个widget，所以要对它们全部更新
        for (int appWidgetId : appWidgetIds) {
            //创建一个远程view，绑定我们要操控的widget布局文件
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
            Intent intentClick = new Intent();
            //这个必须要设置，不然点击效果会无效
            intentClick.setClass(context, HomeWidgetProvider.class);
            intentClick.setAction(CLICK_ACTION);
            //PendingIntent表示的是一种即将发生的意图，区别于Intent它不是立即会发生的
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentClick, 0);
            //为布局文件中的按钮设置点击监听
            remoteViews.setOnClickPendingIntent(R.id.btn, pendingIntent);
            //告诉AppWidgetManager对当前应用程序小部件执行更新
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    //当 widget 被删除时回调
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    //当最后一个widget实例被删除时回调.
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }
}
