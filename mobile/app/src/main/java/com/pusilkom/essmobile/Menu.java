package com.pusilkom.essmobile;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Menu extends AppCompatActivity {
    String URL;
    String[] splitRespons;
    SessionHandler session;
    TextView textName;
    LocationManager locationManager;
    Boolean isGpsActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getName();
        checkingClockInOutStatus();
        locationManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

    }

    @Override
    protected void onResume() {
        super.onResume();
        getName();
        checkingClockInOutStatus();
    }

    public void toClockIn(View view) {
        isGpsActive = checkingGPS();
        if (isGpsActive){
            Intent intent = new Intent( this, ClockIn.class);
            if (splitRespons != null) {
                if (splitRespons[0].equals("null")) {
                    startActivity(intent);
                } else {
                    Toast.makeText(Menu.this, "You already clock-in for today", Toast.LENGTH_SHORT).show();
                }
            } else
                startActivity(intent);
        }
        else
            Toast.makeText(Menu.this, "Please turn on the GPS location", Toast.LENGTH_SHORT).show();

    }

    private Boolean checkingGPS() {
        Boolean result = false;
        if ( locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            result = true;
        }
        return result;
    }

    private void getName() {
        session = new SessionHandler(getApplicationContext());
        textName = findViewById(R.id.textMonthYear);
        textName.setText(session.getEmployeeName());
    }

    public void toClockOut(View view) {
        isGpsActive = checkingGPS();
        if (isGpsActive) {
            if(!checkingFakeGPS()){
                Intent intent = new Intent(this, ClockOut.class);
                if (splitRespons != null) {
                    if (splitRespons[1].equals("null")) {
                        startActivity(intent);
                    } else {
                        Toast.makeText(Menu.this, "You already clock-out for today", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Menu.this, "You must clock-in first", Toast.LENGTH_SHORT).show();
                }
            }
            else
                Toast.makeText(Menu.this, "Fake location detected, Please use real location", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(Menu.this, "Please turn on the GPS location", Toast.LENGTH_SHORT).show();
    }

    private Boolean checkingFakeGPS() {
        Location location = new Location("service Provider");
        Boolean isFake;

        if (android.os.Build.VERSION.SDK_INT >= 18) {
            isFake = location.isFromMockProvider();
        } else {
            String a = Settings.Secure.getString(getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION);
             isFake = Settings.Secure.getString(getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION).equals("0");
        }
        return isFake;
    }

    public void logOut(View view) {
        SessionHandler session = new SessionHandler(getApplicationContext());
        session.destroySession();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void toReminder(View view) {
        Toast.makeText(Menu.this, "Under Development..", Toast.LENGTH_LONG).show();
//        Intent intent = new Intent(this, ReminderActivity.class);
//        startActivity(intent);
    }

    public void toLog(View view) {
        Intent intent = new Intent(this, ViewLog.class);
        startActivity(intent);
    }

    private void checkingClockInOutStatus() {
        final ImageView clockInStatus = findViewById(R.id.clockInStatus);
        final ImageView clockOutStatus = findViewById(R.id.clockOutStatus);

        try {
            URL = Util.getProperty("API_URL", getApplicationContext()) + "/clock-in-out-status/";
        } catch (IOException e) {
            e.printStackTrace();
        }

        final SessionHandler session = new SessionHandler(getApplicationContext());

        RequestQueue rQueue = Volley.newRequestQueue(this);
        JSONObject jsonBody = new JSONObject();
        final String requestBody = jsonBody.toString();
        StringRequest request = new StringRequest(Request.Method.GET, URL + session.getEmployeeId(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                clockInStatus.setBackgroundResource(R.drawable.ic_close_black_24dp);
                clockOutStatus.setBackgroundResource(R.drawable.ic_close_black_24dp);

                if (!response.equals("")) {
                    splitRespons = response.split("\\|");

                    if (!splitRespons[0].equals("null"))
                        clockInStatus.setBackgroundResource(R.drawable.ic_check_black_24dp);

                    if (!splitRespons[1].equals("null"))
                        clockOutStatus.setBackgroundResource(R.drawable.ic_check_black_24dp);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ERROR", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", session.getIdToken());
                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };

        rQueue.add(request);
    }

}
