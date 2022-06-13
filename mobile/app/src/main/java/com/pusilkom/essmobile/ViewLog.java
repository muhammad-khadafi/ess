package com.pusilkom.essmobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pusilkom.essmobile.model.EmployeeAttendance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ViewLog extends AppCompatActivity {

    String URL;
    SessionHandler session;
    List<EmployeeAttendance> listLog;
    Date dateOfLog;
    TextView textMonthYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_log);

        session = new SessionHandler(getApplicationContext());

        try {
            URL = Util.getProperty("API_URL", getApplicationContext()) + "/view-log/";
        } catch (IOException e) {
            e.printStackTrace();
        }

        loadMonthYear();

    }

    public void prevMonth(View view){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateOfLog);
        calendar.add(Calendar.MONTH, -1);
        dateOfLog = calendar.getTime();
        textMonthYear.setText(formatingMonthYear(dateOfLog));
        loadListView();
    }

    public void nextMonth(View view){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateOfLog);
        calendar.add(Calendar.MONTH, +1);
        dateOfLog = calendar.getTime();
        textMonthYear.setText(formatingMonthYear(dateOfLog));
        loadListView();
    }

    private void loadMonthYear() {

        RequestQueue rQueue = Volley.newRequestQueue(this);
        JSONObject jsonBody = new JSONObject();
        final String requestBody = jsonBody.toString();
        textMonthYear = findViewById(R.id.textMonthYear);

        try {
            URL = Util.getProperty("API_URL", getApplicationContext()) + "/view-log/get-month-year/";
        } catch (IOException e) {
            e.printStackTrace();
        }

        final SessionHandler session = new SessionHandler(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                     dateOfLog = new SimpleDateFormat("dd/MM/yyyy").parse(response);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                textMonthYear.setText(formatingMonthYear(dateOfLog));
                loadListView();

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

    private String formatingMonthYear(Date date) {
        DateFormat fmt = new SimpleDateFormat("MMMM-yyyy", Locale.US);
        return fmt.format(date);
    }

    private String formatingMonthYear2(Date date) {
        DateFormat fmt = new SimpleDateFormat("MM-yyyy", Locale.US);
        return fmt.format(date);
    }

    private void loadListView() {

        RequestQueue rQueue = Volley.newRequestQueue(this);
        JSONObject jsonBody = new JSONObject();
        final String requestBody = jsonBody.toString();

        listLog = new ArrayList<>();

        try {
            URL = Util.getProperty("API_URL", getApplicationContext()) + "/view-log/" + session.getEmployeeId()
                    + "/" + formatingMonthYear2(dateOfLog);
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject data = array.getJSONObject(i);
                        Long id = data.getLong("id");
                        String checkInString = data.getString("checkInString");
                        String checkOutString = data.getString("checkOutString");
                        String dateString = data.getString("dateString");
                        Integer totalDuration = data.getInt("totalDuration");
                        String activityDetail = data.getString("activityDetail");

                        listLog.add(new EmployeeAttendance(id, checkInString, checkOutString, dateString, totalDuration, activityDetail));
                    }
                    showList();
                } catch (JSONException e) {
                    e.printStackTrace();
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

    private void showList() {
        ListView attendanceList = findViewById(R.id.attendanceList);
        ViewLogArrayAdapter arrayAdapter = new ViewLogArrayAdapter(this, R.layout.custom_list_view, listLog);
        attendanceList.setAdapter(arrayAdapter);

    }
}
