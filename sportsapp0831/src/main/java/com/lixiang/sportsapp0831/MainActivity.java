package com.lixiang.sportsapp0831;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private TextView mDistanceTV;
    private Button mResetBtn;
    private IGPSAidlInterface mGpsService;
    private boolean isConnection = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initGPSService();
    }

    private void initGPSService() {
        Intent gpsServiceIntent = new Intent(this,GPSService.class);
        gpsServiceIntent.setPackage("com.lixiang.sportsapp0831");
        bindService(gpsServiceIntent, gpsServiceConnection, Context.BIND_AUTO_CREATE);
    }


    private final ServiceConnection gpsServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mGpsService = IGPSAidlInterface.Stub.asInterface(iBinder);
            isConnection = true;

            try {
                mDistanceTV.setText("" + mGpsService.getDistanceByToday());


                mResetBtn.setOnClickListener( view -> {
                    try {
                        mGpsService.resetDistance();
                        mDistanceTV.setText("" + mGpsService.getDistanceByToday());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected() called with: componentName = [" + componentName + "]");
            isConnection = false;
        }
    };

    private void initView() {
        mResetBtn = findViewById(R.id.reset_btn);
        mDistanceTV = findViewById(R.id.distance_tv);
    }


    private void unBindGpsService() {
        if (isConnection) {
            unbindService(gpsServiceConnection);
        }
        mGpsService = null;
    }

    protected void onDestroy() {
        super.onDestroy();
        unBindGpsService();
    }
}