package com.mantistech.busreservation.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static Date today()
    {
        return new Date();
    }

    public static String todayStr()
    {
        return simpleDateFormat.format(today());
    }

    public static String formattedDate(Date date)
    {
        return date!=null ? simpleDateFormat.format(date) : todayStr();
    }

}
