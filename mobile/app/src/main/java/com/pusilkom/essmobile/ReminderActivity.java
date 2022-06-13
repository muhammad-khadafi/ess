package com.pusilkom.essmobile;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TimePicker;

import com.pusilkom.essmobile.Receiver.ReminderReceiver;

import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity implements View.OnClickListener{

    AlarmManager alarm_manager_ci;
    AlarmManager alarm_manager_co;
    TimePicker alarm_timepicker_ci;
    TimePicker alarm_timepicker_co;

    CheckBox checkBoxClockInActive;
    CheckBox checkBoxClockInSoundAlert;
    CheckBox checkBoxClockOutActive;
    CheckBox checkBoxClockOutSoundAlert;

    Boolean checkBoxClockInActiveIsChecked;
    Boolean checkBoxClockInSoundAlertIsChecked;
    Boolean checkBoxClockOutActiveIsChecked;
    Boolean checkBoxClockOutSoundAlertIsChecked;

    Context context;
    PendingIntent pendingIntentClockIn;
    PendingIntent pendingIntentClockOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        checkBoxClockInActive = findViewById(R.id.checkBoxClockInActive);
        checkBoxClockInSoundAlert = findViewById(R.id.checkBoxClockInSoundAlert);
        checkBoxClockOutActive = findViewById(R.id.checkBoxClockOutActive);
        checkBoxClockOutSoundAlert = findViewById(R.id.checkBoxClockOutSoundAlert);
        alarm_timepicker_ci = findViewById(R.id.timePickerClockIn);
        alarm_timepicker_co = findViewById(R.id.timePickerClockIn);

        checkBoxClockInSoundAlert.setEnabled(false);
        checkBoxClockOutSoundAlert.setEnabled(false);

        checkBoxClockInActive.setOnClickListener(this);
        checkBoxClockInSoundAlert.setOnClickListener(this);
        checkBoxClockOutActive.setOnClickListener(this);
        checkBoxClockOutSoundAlert.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        this.context = this;
        if(view.getId() == R.id.checkBoxClockInActive || view.getId() == R.id.checkBoxClockInSoundAlert){
            Log.e("masuk ke:", "Clock In");
            alarm_manager_ci = (AlarmManager) getSystemService(ALARM_SERVICE);
            final Calendar calendar_ci = Calendar.getInstance();

            checkBoxClockInActiveIsChecked = checkBoxClockInActive.isChecked();
            checkBoxClockInSoundAlertIsChecked = checkBoxClockInSoundAlert.isChecked();

            if(checkBoxClockInActiveIsChecked){
                Intent reminderIntent = new Intent(this.context, ReminderReceiver.class);
                reminderIntent.putExtra("clockIn", "clockIn");;
                checkBoxClockInSoundAlert.setEnabled(true);
                calendar_ci.set(Calendar.HOUR_OF_DAY,alarm_timepicker_ci.getCurrentHour());
                calendar_ci.set(Calendar.MINUTE,alarm_timepicker_ci.getCurrentMinute());

                if(checkBoxClockInActiveIsChecked && checkBoxClockInSoundAlertIsChecked){
                    reminderIntent.putExtra("activeClockInIsChecked", true);
                    reminderIntent.putExtra("soundAlertClockInIsChecked", true);
                } else if (checkBoxClockInActiveIsChecked && !checkBoxClockInSoundAlertIsChecked) {
                    reminderIntent.putExtra("activeClockInIsChecked", true);
                    reminderIntent.putExtra("soundAlertClockInIsChecked", false);
                } else {
                    reminderIntent.putExtra("activeClockInIsChecked", false);
                    reminderIntent.putExtra("soundAlertClockInIsChecked", false);
                }

                pendingIntentClockIn = PendingIntent.getBroadcast(ReminderActivity.this, 3, reminderIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarm_manager_ci.set(AlarmManager.RTC_WAKEUP, calendar_ci.getTimeInMillis(), pendingIntentClockIn);
            } else {
                checkBoxClockInSoundAlert.setEnabled(false);
                checkBoxClockInSoundAlert.setChecked(false);
            }
        }

        if(view.getId() == R.id.checkBoxClockOutActive || view.getId() == R.id.checkBoxClockOutSoundAlert){
            Log.e("masuk ke:", "Clock Out");
            alarm_manager_co = (AlarmManager) getSystemService(ALARM_SERVICE);
            final Calendar calendar_co = Calendar.getInstance();
            checkBoxClockOutActiveIsChecked = checkBoxClockOutActive.isChecked();
            checkBoxClockOutSoundAlertIsChecked = checkBoxClockOutSoundAlert.isChecked();
            if (checkBoxClockOutActiveIsChecked){
                Intent reminderIntent = new Intent(this.context, ReminderReceiver.class);
                reminderIntent.putExtra("clockOut", "clockOut");
                checkBoxClockOutSoundAlert.setEnabled(true);
                calendar_co.set(Calendar.HOUR_OF_DAY,alarm_timepicker_co.getCurrentHour());
                calendar_co.set(Calendar.MINUTE,alarm_timepicker_co.getCurrentMinute());

                if(checkBoxClockOutActiveIsChecked && checkBoxClockOutSoundAlertIsChecked){
                    reminderIntent.putExtra("activeClockOutIsChecked", true);
                    reminderIntent.putExtra("soundAlertClockOutIsChecked", true);
                } else if (checkBoxClockOutActiveIsChecked && !checkBoxClockOutSoundAlertIsChecked) {
                    reminderIntent.putExtra("activeClockOutIsChecked", true);
                    reminderIntent.putExtra("soundAlertClockOutIsChecked", false);
                } else {
                    reminderIntent.putExtra("activeClockOutIsChecked", false);
                    reminderIntent.putExtra("soundAlertClockOutIsChecked", false);
                }
                pendingIntentClockOut = PendingIntent.getBroadcast(ReminderActivity.this, 4, reminderIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarm_manager_co.set(AlarmManager.RTC_WAKEUP, calendar_co.getTimeInMillis(), pendingIntentClockOut);
            } else {
                checkBoxClockOutSoundAlert.setEnabled(false);
                checkBoxClockOutSoundAlert.setChecked(false);
            }
        }
    }
}
