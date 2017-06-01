package ru.a89uchet.roznica89.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Босс
 * Date: 26.08.12
 * Time: 6:38
 * To change this template use File | Settings | File Templates.
 */
public class DateHelper {
    public static Date getNowDate(){
        final Calendar c = Calendar.getInstance();
        return c.getTime();
    }

    public static String getNowFormatedDate(){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(year-1900,month,day);
        return dateFormat.format(date);
    }

    public static String formatFromPicker(int year, int month, int day){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(year-1900,month,day);
        return dateFormat.format(date);
    }

    public static String getDateBeforeNow(int days){
        if(days<0){
            days=0;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final Calendar gCal = Calendar.getInstance();
        gCal.add(Calendar.DAY_OF_MONTH,-days);
        return dateFormat.format(gCal.getTime());
    }

    public static String getMonthBeforeNow(int mont){
        if(mont<0){
                mont=0;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        final Calendar gCal = Calendar.getInstance();
        int deltaDay=gCal.get(Calendar.DATE)-1;
        gCal.add(Calendar.DAY_OF_MONTH,-deltaDay);//начало месяца
        gCal.add(Calendar.MONTH,-mont);
        return dateFormat.format(gCal.getTime());
    }

    public static long getTimeMsFromDayNow(){
        final Calendar gCal = Calendar.getInstance();
        int h=gCal.get(Calendar.HOUR_OF_DAY);
        int m=gCal.get(Calendar.MINUTE);
        int s=gCal.get(Calendar.SECOND);
        int ms=gCal.get(Calendar.MILLISECOND);

        return h*60*60*1000 +m*60*1000+s*1000+ms;

    }

    public static  long getMsFromHourMin(int hour,int min){
        final Calendar gCal = Calendar.getInstance();
        gCal.set(Calendar.HOUR_OF_DAY,hour);
        gCal.set(Calendar.MINUTE,min);
        return gCal.getTimeInMillis();

    }

    public static String getDateOurFormat(String oldDate){
        return oldDate.substring(8,10)+"."+oldDate.substring(5,7)+"."+oldDate.substring(0,4);
    }
}
