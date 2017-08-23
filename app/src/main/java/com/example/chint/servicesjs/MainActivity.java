package com.example.chint.servicesjs;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    Button start, stop;
    Intent i;
    BroadcastReceiver b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button) findViewById(R.id.startService);
        stop = (Button) findViewById(R.id.stopService);
        b = new myBroadCastReceiver();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, Fservice.class);
                startService(i);
                Toast.makeText(MainActivity.this, "Service Started", Toast.LENGTH_LONG).show();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i!=null) {
                    stopService(i);
                }
                Toast.makeText(MainActivity.this, "Service Stopped", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter i = new IntentFilter();
        i.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(b,i);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        i = new Intent(MainActivity.this, Fservice.class);
        startService(i);
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(b);
    }
}
