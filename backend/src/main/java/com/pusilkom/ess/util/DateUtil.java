package com.pusilkom.ess.util;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by pusilkom on 3/24/2017.
 */
@Component
public class DateUtil {
    public static Date stringToDate(String date, String format) {
        DateFormat thisFormat = new SimpleDateFormat(format);
        Date thisDate = null;
        try {
            thisDate = thisFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return thisDate;
    }

    public static String dateToString (Date d) {
        String result = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        String[] month = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
        result += cal.get(Calendar.DAY_OF_MONTH) + " " + month[cal.get(Calendar.MONTH)] + " " + cal.get(Calendar.YEAR);
        return result;
    }

    public static String dateToStringDefault (Date d) {
        String result = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int tempBulan = cal.get(Calendar.MONTH) + 1;
        String bulan = "";
        if (tempBulan < 10) {
            bulan = "0"+ tempBulan;
        }
        else {
            bulan = "" + tempBulan;
        }
        result += cal.get(Calendar.YEAR)+ "-"+bulan + "-" +cal.get(Calendar.DAY_OF_MONTH) ;
        return result;
    }
    
    public static String dateToStringReal (Date d) {
        String result = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        String month = cal.get(Calendar.MONTH)+1 < 10 ? "0"+(cal.get(Calendar.MONTH)+1) : ""+ (cal.get(Calendar.MONTH)+1);
        result += cal.get(Calendar.YEAR) + "-" + month + "-" + cal.get(Calendar.DAY_OF_MONTH);
        return result;
    }

    public static String dateTimeToStringReal (Timestamp d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        String result = "";
        result = sdf.format(d);
        /*Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        String month = cal.get(Calendar.MONTH)+1 < 10 ? "0"+(cal.get(Calendar.MONTH)+1) : ""+ (cal.get(Calendar.MONTH)+1);
        result += cal.get(Calendar.YEAR) + "-" + month + "-" + cal.get(Calendar.DAY_OF_MONTH);*/
        return result;
    }

}
