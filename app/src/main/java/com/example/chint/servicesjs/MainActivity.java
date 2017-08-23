package com.example.chint.servicesjs;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
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

    Button start, stop, sjob;
    Intent i;
    ComponentName myServiceComponent;
    Sservice myService;
    Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            myService = (Sservice) msg.obj;
            myService.setUICallback(MainActivity.this);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myServiceComponent = new ComponentName(this, Sservice.class);

        Intent myServiceIntent = new Intent(this, Sservice.class);
        myServiceIntent.putExtra("messenger", new Messenger(myHandler));
        startService(myServiceIntent);

        start = (Button) findViewById(R.id.startService);
        stop = (Button) findViewById(R.id.stopService);
        sjob = (Button) findViewById(R.id.schedule);

        sjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myService = new Sservice(getBaseContext());
                JobInfo.Builder builder = new JobInfo.Builder(0, myServiceComponent);
                builder.setRequiresCharging(true);
                builder.setRequiresDeviceIdle(false);
                builder.setPeriodic(2000, 100);
                myService.scheduleJob(builder.build());
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, Fservice.class);
                startService(i);
                Toast.makeText(MainActivity.this, "Check Log", Toast.LENGTH_SHORT).show();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i!=null) {
                    stopService(i);
                }
                Toast.makeText(MainActivity.this, "Service Stopped", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
