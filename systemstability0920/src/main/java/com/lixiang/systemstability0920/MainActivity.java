package com.lixiang.systemstability0920;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.oom_for_sof).setOnClickListener( view -> {
            startActivity(new Intent(this, OOMSofActivity.class));
        });
        findViewById(R.id.oom_for_heap).setOnClickListener( view -> {
            startActivity(new Intent(this, OOMJavaHeapActivity.class));
        });
        findViewById(R.id.anrInputDispatchingTimeout).setOnClickListener( view -> {
            startActivity(new Intent(this, AnrInputDispatchingTimeout.class));
        });
    }
}