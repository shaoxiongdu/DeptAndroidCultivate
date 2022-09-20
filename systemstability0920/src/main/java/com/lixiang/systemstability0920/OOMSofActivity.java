package com.lixiang.systemstability0920;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

/**
 * dushaoxiong@lixiang.com
 */
public class OOMSofActivity extends AppCompatActivity {

    private static final String TAG = OOMSofActivity.class.getName();
    private static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oomactivity);

        recursion();
    }

    private void recursion() {
        count++;
        // count = 27345   sof
        Log.i(TAG, "recursion: count = " + count);
        recursion();
    }


}