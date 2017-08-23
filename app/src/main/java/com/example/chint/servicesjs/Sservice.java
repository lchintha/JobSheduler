package com.example.chint.servicesjs;

import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by chint on 8/22/2017.
 */

public class Sservice extends JobService {

    Context context;

    public Sservice(){

    }

    public Sservice(Context context) {
        this.context = context;
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.i("MyService", "on start job");
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.i("MyService", "on stop job");
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("TAG", "Second Service Stopped");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Messenger callback = intent.getParcelableExtra("messenger");
        Message m = Message.obtain();
        m.what = 2;
        m.obj = this;

        try {
            callback.send(m);
            Log.i("TAG", "Second Service Started onstartcm");
        } catch (RemoteException e) {
            Log.i("MyService", "Error passing service object back to activity.");
        }
        //stopSelf();
        return START_NOT_STICKY;
    }

    public void scheduleJob(JobInfo build) {

        JobScheduler js = (JobScheduler) context.getSystemService(context.JOB_SCHEDULER_SERVICE);
        js.schedule(build);
    }

    MainActivity myMainActivity;

    public void setUICallback(MainActivity activity) {
        myMainActivity = activity;
    }
}
