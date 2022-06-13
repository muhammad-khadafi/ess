package com.pusilkom.essmobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class DialogPopupActivity extends Activity {
    private int m_alarmId;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.e("Goes to popup class:", "got in");
        super.onCreate(savedInstanceState);

        // Get the alarm ID from the intent extra data
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            m_alarmId = extras.getInt("AlarmID", -1);
        } else {
            m_alarmId = -1;
        }

        // Show the popup dialog
        showDialog(0);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        super.onCreateDialog(id);
        // Build the dialog
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Alert");
        alert.setMessage("Waktunya Check In !");
        alert.setCancelable(false);

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                DialogPopupActivity.this.finish();
            }
        });

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                DialogPopupActivity.this.finish();
            }
        });

        // Create and return the dialog
        AlertDialog dlg = alert.create();
//        dlg.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

        return dlg;
    }
}
