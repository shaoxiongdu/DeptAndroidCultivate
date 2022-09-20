package com.lixiang.systemstability0920;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class OOMJavaHeapActivity extends AppCompatActivity {

    private static final String TAG = OOMSofActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oomjava_heap);
        int count = 0;
        ArrayList<BigObj> bigObjArrayList = new ArrayList<>();
        for (int i = 0; i < 150; i++) {
            count++;
            bigObjArrayList.add(new BigObj());
            Log.i(TAG, "count:" + count);
        }
    }

    private static class BigObj{
        final byte[] bytes = new byte[1024 * 1024];
    }
}