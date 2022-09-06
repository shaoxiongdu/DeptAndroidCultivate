package com.lixiang.applist_0905;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lixiang.applist_0905.adapter.AppInfoAdapter;
import com.lixiang.applist_0905.model.AppInfo;
import com.lixiang.applist_0905.utils.AppInfoUtil;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private RecyclerView mRecyclerView;
    private AppInfoUtil mAppInfoUtil;
    private List<AppInfo> mAllAppInfo;
    private AppInfoAdapter mAppInfoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initListener();
    }

    private void initListener() {
        mAppInfoAdapter.setOnUninstallClickListener(mOnUninstallClickListener);
    }

    private void initData() {
        mAppInfoUtil = AppInfoUtil.getInstance();
        mAllAppInfo = mAppInfoUtil.getAllAppInfo(this);
        mAppInfoAdapter = new AppInfoAdapter(mAllAppInfo,this);
        mRecyclerView.setAdapter(mAppInfoAdapter);
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.date_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private final AppInfoAdapter.OnUninstallClickListener mOnUninstallClickListener = (appInfo -> {
        Log.d(TAG, "try delete app :" + appInfo.packageName);
        try {
            Process exec = Runtime.getRuntime().exec("uninstall " + appInfo.packageName);
            Toast.makeText(this, exec.toString(), Toast.LENGTH_SHORT).show();
            System.out.println(exec);
        } catch (IOException e) {
            e.printStackTrace();
        }
    });
}