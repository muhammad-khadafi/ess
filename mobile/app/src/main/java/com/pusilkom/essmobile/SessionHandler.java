package com.pusilkom.essmobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionHandler {
    private SharedPreferences pref;

    public SessionHandler(Context context) {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setUsername(String username) {
        pref.edit().putString("username", username).commit();
    }

    public String getUsername() {
        return pref.getString("username", "");
    }

    public void setEmployeeName(String employeeName) {
        pref.edit().putString("employeeName", employeeName).commit();
    }

    public String getEmployeeName() {
        return pref.getString("employeeName", "");
    }

    public void setIdToken(String idToken) {
        pref.edit().putString("idToken", idToken).commit();
    }

    public String getIdToken() {
        return pref.getString("idToken","");
    }

    public void setEmployeeId(Integer employeeId) {
        pref.edit().putInt("employeeId", employeeId).commit();
    }

    public Integer getEmployeeId() {
        return pref.getInt("employeeId", 0);
    }

    public void setIsLogin(Boolean loginStatus) {
        pref.edit().putBoolean("loginStatus", loginStatus).commit();
    }

    public Boolean isLogin() {
        return pref.getBoolean("loginStatus", false);
    }

    public void destroySession(){
        pref.edit().clear().commit();
    }
}
