package com.lixiang.applist_0905.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lixiang.applist_0905.R;
import com.lixiang.applist_0905.model.AppInfo;

import java.util.List;

public class AppInfoAdapter extends RecyclerView.Adapter<AppInfoAdapter.ViewHolder>{
    private static final String TAG = AppInfoAdapter.class.getName();
    private final List<AppInfo> mAppInfoList;
    private final Context mContext;
    private OnUninstallClickListener mOnUninstallClickListener;

    public AppInfoAdapter(List<AppInfo> appInfoList, Context context) {
        mContext = context;
        mAppInfoList = appInfoList;
    }

    public void setOnUninstallClickListener(OnUninstallClickListener onUninstallClickListener) {
        mOnUninstallClickListener = onUninstallClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_info_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppInfo appInfo = mAppInfoList.get(position);
        holder.label.setText(appInfo.label);
        holder.icon.setImageDrawable(appInfo.icon);
        holder.isSystem.setText(appInfo.isSystem ? "Y" : "N");

        holder.uninstallBtn.setOnClickListener( (view) -> {
            if (mOnUninstallClickListener != null) {
                mOnUninstallClickListener.onUninstallClick(appInfo);
            }

        });
    }

    @Override
    public int getItemCount() {
        return mAppInfoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView label;
        TextView isSystem;
        ImageView icon;
        Button uninstallBtn;
        public ViewHolder(View view) {
            super(view);
            label = view.findViewById(R.id.label);
            isSystem = view.findViewById(R.id.is_system);
            icon = view.findViewById(R.id.icon);
            uninstallBtn = view.findViewById(R.id.uninstall);
        }

    }

    @FunctionalInterface
    public interface OnUninstallClickListener{
        void onUninstallClick(AppInfo appInfo);
    }


}
