package com.trd.oecms.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author tanruidong
 * @date 2020-04-14 16:04
 */
public class DateUtils {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

    public static Date parseStringDate(String date) {
        try{
            return dateFormat.parse(date);
        }catch(Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException("日期格式有误，请仔细检查！！");
        }
    }

    /**
     * 获取秒为单位的时间间隔
     * @param startMillsTime 起始时间戳
     * @param endMillsTime 结束时间戳
     * @return
     */
    public static long getSecondsDuration(long startMillsTime, long endMillsTime) {
        return TimeUnit.MILLISECONDS.toSeconds(endMillsTime - startMillsTime);
    }
}
