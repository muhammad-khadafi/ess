package com.pusilkom.essmobile;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.Clock;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ClockIn extends AppCompatActivity {

    String URL;

    LocationManager locationManager;

    LocationListener locationListener;

    String userLocation = "";

    TextView lblLocation;

    SessionHandler session;

    private int FASTEST_INTERVAL = 10000; // use whatever suits you
    private Location currentLocation = null;
    private long locationUpdatedAt = Long.MIN_VALUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_in);

        try {
            URL = Util.getProperty("API_URL",getApplicationContext()) + "/clockin";
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ********** Location Handling ****************

        lblLocation = findViewById(R.id.lblLocation);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                boolean updateLocationandReport = false;
                if(currentLocation == null){
                    currentLocation = location;
                    locationUpdatedAt = System.currentTimeMillis();
                    updateLocationandReport = true;
                } else {
                    long secondsElapsed = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - locationUpdatedAt);
                    if (secondsElapsed >= TimeUnit.MILLISECONDS.toSeconds(FASTEST_INTERVAL)){
                        // check location accuracy here
                        currentLocation = location;
                        locationUpdatedAt = System.currentTimeMillis();
                        updateLocationandReport = true;
                    }
                }
                if(updateLocationandReport){
                    //  Do process business
                    RequestQueue queue = Volley.newRequestQueue(ClockIn.this);
                    String url = null;
                    try {
                        url = "https://maps.googleapis.com/maps/api/geocode/json?latlng="+
                                location.getLatitude()+","+location.getLongitude()+"&key="+
                                Util.getProperty("API_KEY", getApplicationContext());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.i("URL : ", url);
                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Display the first 500 characters of the response string.
                                    //textView.setText("Response is: "+ response.substring(0,500));
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        userLocation = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getString("formatted_address");
                                        lblLocation.setText(userLocation);
                                        //Log.i("EXCEPTION", response);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //textView.setText("That didn't work!");
                            Log.i("EXCEPTION", "Failed to put json value");
                        }
                    });

                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        }

        // **************************************************

        // ************ Clock In Process ********************

        final Button btnClockIn = findViewById(R.id.btnClockIn);

        session = new SessionHandler(getApplicationContext());

        btnClockIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btnClockIn.setEnabled(false);
                if (userLocation.equals(""))
                    Toast.makeText(ClockIn.this, "Please wait the location..", Toast.LENGTH_SHORT).show();
                else
                    clockInProcess();
            }
        });

        // ****************************************************
    }

    private void clockInProcess() {
        RequestQueue rQueue = Volley.newRequestQueue(ClockIn.this);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("employeeId", session.getEmployeeId());
            jsonBody.put("clockInLocation", userLocation);
        } catch (JSONException e) {
            Log.i("EXCEPTION", "Failed to put json value");
            e.printStackTrace();
        }
        final String requestBody = jsonBody.toString();
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(ClockIn.this, "Clock In Succes", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(ClockIn.this, Menu.class);
//                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ERROR", error.toString());
                Toast.makeText(ClockIn.this, "Clock In Failed", Toast.LENGTH_LONG).show();
            }
        }){
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
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rQueue.add(request);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        System.out.println("Permission Result");
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            }

        }
    }

}
