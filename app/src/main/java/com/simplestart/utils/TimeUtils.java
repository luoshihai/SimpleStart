package com.simplestart.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author:lsh
 * Version: 1.0
 * Description:
 * Date: 2017/6/25
 */

public class TimeUtils {

    public static Date getTime(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date d = null;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

}
