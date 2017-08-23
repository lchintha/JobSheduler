package com.example.chint.servicesjs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by chint on 8/23/2017.
 */

public class myBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
            Intent i = new Intent(context, Fservice.class);
            context.startService(i);
            Toast.makeText(context, "Service Started", Toast.LENGTH_SHORT).show();
        }
    }
}
