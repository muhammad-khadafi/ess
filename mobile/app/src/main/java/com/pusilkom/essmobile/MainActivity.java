package com.pusilkom.essmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    ProgressBar spinner;
    EditText emailBox, passwordBox;
    Button loginButton;
    String URL;
    SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionHandler(getApplicationContext());

        if (session.isLogin()){
            Intent intent = new Intent(MainActivity.this, Menu.class);
            startActivity(intent);
            finish();
        }

        try {
            URL = Util.getProperty("API_URL",getApplicationContext()) + "/auth/mlogin";
        } catch (IOException e) {
            e.printStackTrace();
        }

        emailBox = findViewById(R.id.emailBox);
        passwordBox = findViewById(R.id.passwordBox);
        loginButton = findViewById(R.id.loginButton);

        spinner = findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                Log.i("URL : ", URL);
                RequestQueue rQueue = Volley.newRequestQueue(MainActivity.this);
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("username", emailBox.getText().toString());
                    jsonBody.put("password", passwordBox.getText().toString());
                } catch (JSONException e) {
                    Log.i("EXCEPTION", "Failed to put json value");
                    e.printStackTrace();
                }
                final String requestBody = jsonBody.toString();
                StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String[] splitRespons = s.split("\\|");
                        session.setIsLogin(true);
                        session.setIdToken(splitRespons[0]);
                        session.setEmployeeId(Integer.parseInt(splitRespons[1]));
                        session.setEmployeeName(splitRespons[2]);
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, Menu.class);
                        startActivity(intent);
                        finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(MainActivity.this, "Accesss Denied " + volleyError, Toast.LENGTH_LONG).show();
                        spinner.setVisibility(View.GONE);
                    }
                }) {
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
                        return super.parseNetworkResponse(response);
                    }
                };
                request.setRetryPolicy(new DefaultRetryPolicy(
                        60000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                rQueue.add(request);

            }
        });

    }
}
