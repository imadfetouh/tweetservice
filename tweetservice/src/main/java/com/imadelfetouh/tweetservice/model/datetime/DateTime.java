package com.imadelfetouh.tweetservice.model.datetime;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTime {

    private static final DateTime dateTime = new DateTime();
    private TimeZone timeZone;

    private DateTime() {
        timeZone = TimeZone.getTimeZone("Europe/Amsterdam");
    }

    public static DateTime getInstance() {
        return dateTime;
    }

    public Long getCurrentDate(){
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis() / 1000L;
    }

    public Long getCurrentDateMinusWeek(){
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        calendar.add(Calendar.DAY_OF_WEEK, -7);

        return calendar.getTimeInMillis() / 1000L;
    }

    public String getCurrentTime(){
        Calendar calendar = Calendar.getInstance(timeZone);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(calendar.getTime());
    }

    public String timeStampToString(Long tweetDate) {
        Timestamp timestamp = new Timestamp(tweetDate * 1000L);
        Date date = new Date(timestamp.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.YEAR);
    }

}
