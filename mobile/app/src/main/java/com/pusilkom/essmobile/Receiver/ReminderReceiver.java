package com.pusilkom.essmobile.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.pusilkom.essmobile.Service.ReminderService;

public class ReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("activeClockInIsChecked:", ""+intent.getExtras().getBoolean("activeClockInIsChecked"));
        Log.e("soundAlertClockInIsChe:", ""+intent.getExtras().getBoolean("soundAlertClockInIsChecked"));

        Intent serviceIntent = new Intent(context, ReminderService.class);
        if(intent.getExtras().getString("clockIn") != null){
            serviceIntent.putExtra("activeClockInIsChecked", intent.getExtras().getBoolean("activeClockInIsChecked"));
            serviceIntent.putExtra("soundAlertClockInIsChecked", intent.getExtras().getBoolean("soundAlertClockInIsChecked"));
        }

        if(intent.getExtras().getString("clockOut") != null){
            serviceIntent.putExtra("activeClockOutIsChecked", intent.getExtras().getBoolean("activeClockOutIsChecked"));
            serviceIntent.putExtra("soundAlertClockOutIsChecked", intent.getExtras().getBoolean("soundAlertClockOutIsChecked"));
        }

        ContextCompat.startForegroundService(context, serviceIntent );
    }
}
