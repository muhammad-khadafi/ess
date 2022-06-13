package com.pusilkom.ess.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

/**
 * Created by muhammad.khadafi on 11/2/2018.
 */
public class CommonUtil {

    private static String userName;

    private static Integer userId;

    public static Date getCurrentDate(){
        return new Date();
    }

    public static Integer getUserId(){
        return userId;
    }

    public static void setUserId(Integer userId) {
        CommonUtil.userId = userId;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        CommonUtil.userName = userName;
    }
}
