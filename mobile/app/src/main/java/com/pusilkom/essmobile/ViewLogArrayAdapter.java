package com.pusilkom.essmobile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import com.pusilkom.essmobile.model.EmployeeAttendance;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewLogArrayAdapter extends ArrayAdapter<EmployeeAttendance> {
    private static final String TAG = "ViewLogArrayAdapter";
    private List<EmployeeAttendance> employeeAttendances = new ArrayList<EmployeeAttendance>();

    String URL;

    static class ViewLogHolder {
        TextView textDate;
        TextView textClockIn;
        TextView textClockOut;
        TextView textActivity;
        EditText editTextActivity;
        Button btnUpdateActivity;
    }

    public ViewLogArrayAdapter(Context context, int textViewResourceId, List<EmployeeAttendance> list) {
        super(context, textViewResourceId, list);
        employeeAttendances = list;
    }

    @Override
    public void add(EmployeeAttendance object) {
        employeeAttendances.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.employeeAttendances.size();
    }

    @Override
    public EmployeeAttendance getItem(int index) {
        return this.employeeAttendances.get(index);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final ViewLogHolder viewHolder ;
        final EmployeeAttendance employeeAttendance = getItem(position);

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.custom_list_view, parent, false);
            viewHolder = new ViewLogHolder();
            viewHolder.textDate = row.findViewById(R.id.textDate);
            viewHolder.textClockIn = row.findViewById(R.id.textClockIn);
            viewHolder.textClockOut = row.findViewById(R.id.textClockOut);
            viewHolder.editTextActivity = row.findViewById(R.id.editTextActivity);
            viewHolder.btnUpdateActivity = row.findViewById(R.id.btnUpdateActivity);
            row.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewLogHolder)row.getTag();
        }

        viewHolder.btnUpdateActivity.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                updateActivity(employeeAttendance.getId(), viewHolder.editTextActivity.getText().toString(), viewHolder);
            }
        });

        viewHolder.textDate.setText(employeeAttendance.getDateString());
        viewHolder.textClockIn.setText(employeeAttendance.getCheckInString());
        viewHolder.textClockOut.setText(employeeAttendance.getCheckOutString());
        viewHolder.editTextActivity.setText(employeeAttendance.getActivityDetail());

        return row;
    }

    private void updateActivity(Long id, String activity, final ViewLogHolder viewHolder) {
        RequestQueue rQueue = Volley.newRequestQueue(getContext());
        JSONObject jsonBody = new JSONObject();
        final SessionHandler session = new SessionHandler(getContext());
        try {
            //activity = activity.replaceAll(" ", "%20");
            URL = Util.getProperty("API_URL",getContext()) + "/employee-attendance/" + id;
            jsonBody.put("id", session.getEmployeeId());
            jsonBody.put("activityDetail", activity);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            Log.i("EXCEPTION", "Failed to put json value");
            e.printStackTrace();
        }

        final String requestBody = jsonBody.toString();
        StringRequest request = new StringRequest(Request.Method.PUT, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "Update Activity Success", Toast.LENGTH_SHORT).show();
                viewHolder.editTextActivity.clearFocus();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ERROR", error.toString());
                Toast.makeText(getContext(), "Update Activity Failed", Toast.LENGTH_SHORT).show();
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

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
