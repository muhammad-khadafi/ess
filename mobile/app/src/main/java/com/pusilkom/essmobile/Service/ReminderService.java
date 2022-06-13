package com.pusilkom.essmobile.Service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.pusilkom.essmobile.R;
import com.pusilkom.essmobile.ReminderActivity;

import java.util.Date;

public class ReminderService extends Service {
    MediaPlayer reminderRingtone;
    boolean isPlaying;

    @Override
    public void onCreate(){
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void startMyOwnForeground(){
        String NOTIFICATION_CHANNEL_ID = "com.example.simpleapp";
        String channelName = "My Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("App is running in background")
                .build();
        startForeground(2, notification);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        Log.e("Check active svc:", ""+intent.getExtras().getBoolean("activeClockInIsChecked"));
        Log.e("Check sound alert svc:", ""+intent.getExtras().getBoolean("soundAlertClockInIsChecked"));

        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

        boolean activeClockInIsChecked = intent.getExtras().getBoolean("activeClockInIsChecked");
        boolean soundAlertClockInIsChecked = intent.getExtras().getBoolean("soundAlertClockInIsChecked");
        boolean activeClockOutIsChecked = intent.getExtras().getBoolean("activeClockOutIsChecked");
        boolean soundAlertClockOutIsChecked = intent.getExtras().getBoolean("soundAlertClockOutIsChecked");

        //Alert dialog tidak muncul di foreground service (apps di close/minimize)
        //untuk sementara menggunakan notifikasi
        /*Intent popUpIntent = new Intent("android.intent.action.MAIN");
        popUpIntent.setClass(this, DialogPopupActivity.class);
        popUpIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        popUpIntent.putExtra("AlarmID", intent.getIntExtra("AlarmID", -1));*/

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent reminderActivityIntent_ci = new Intent(this.getApplicationContext(), ReminderActivity.class);
        Intent reminderActivityIntent_co = new Intent(this.getApplicationContext(), ReminderActivity.class);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "com.example.simpleapp");
        builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
        builder.setContentTitle("Alert");
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.drawable.notification);

        if(activeClockInIsChecked){
            PendingIntent pendingIntentReminderActivity = PendingIntent.getActivity(this, 11, reminderActivityIntent_ci, PendingIntent.FLAG_ONE_SHOT);
            builder.setContentIntent(pendingIntentReminderActivity);
            //this.startActivity(popUpIntent);
            builder.setContentText("Waktunya Clock In");
            Notification notification = builder.build();
            if(soundAlertClockInIsChecked){
              reminderRingtone = MediaPlayer.create(this, Settings.System.DEFAULT_ALARM_ALERT_URI);
              reminderRingtone.start();
              isPlaying = true;
                /*notification.sound = Uri.parse("android.resource://"
                        + this.getPackageName() + "/" + R.raw.reminder);*/
            }
            nm.notify(11, notification);
        } else {
            isPlaying = false;
        }

        if(activeClockOutIsChecked){
            PendingIntent pendingIntentReminderActivity = PendingIntent.getActivity(this, 13, reminderActivityIntent_co, PendingIntent.FLAG_ONE_SHOT);
            builder.setContentIntent(pendingIntentReminderActivity);
            builder.setContentText("Waktunya Clock Out");
            Notification notification = builder.build();
            //this.startActivity(popUpIntent);
            if(soundAlertClockOutIsChecked){
              reminderRingtone = MediaPlayer.create(this, Settings.System.DEFAULT_ALARM_ALERT_URI);
              reminderRingtone.start();
              isPlaying = true;
                /*notification.sound = Uri.parse("android.resource://"
                        + this.getPackageName() + "/" + R.raw.reminder);*/
            }
            nm.notify(13, notification);
        } else {
            isPlaying = false;
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        // Tell the user we stopped.
        Toast.makeText(this, "on destroy called", Toast.LENGTH_SHORT).show();
        super.onDestroy();
        this.isPlaying = false;
    }
}
