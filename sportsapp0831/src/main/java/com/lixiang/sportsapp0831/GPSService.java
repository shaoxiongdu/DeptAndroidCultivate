package com.lixiang.sportsapp0831;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Random;

public class GPSService extends Service {

    private final String TAG = GPSService.class.getName();

    private double distance = 0.0d;

    public GPSService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // TODO 通过GPS计算今日累计运动距离
        distance = new Random().nextDouble() * 10;
        return mStub;
    }

    private final IGPSAidlInterface.Stub mStub = new IGPSAidlInterface.Stub() {
        @Override
        public double getDistanceByToday() {
            return distance;
        }

        @Override
        public void resetDistance() {
            distance = 0;
            Log.d(TAG, "resetDistance() called");
        }
    };


}