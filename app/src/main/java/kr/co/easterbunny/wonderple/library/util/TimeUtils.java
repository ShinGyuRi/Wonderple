package kr.co.easterbunny.wonderple.library.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by scona on 2017-01-13.
 */

public class TimeUtils {


    public static String unixTimeToDate(long unixSeconds) {
        Date date = new Date(unixSeconds*1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4")); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdf.format(date);

        return formattedDate;
    }

    public static String unixTimeToRead(Long unixSeconds) {
        Date date = new Date(unixSeconds*1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm"); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4")); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdf.format(date);

        return formattedDate;
    }

    public static long unixTimeLeft(Long unixSeconds) {
        Date date = new Date(unixSeconds*1000L); // *1000 is to convert seconds to milliseconds
        Date dat = new Date();
        long difference = date.getTime() - dat.getTime();

        return difference;
    }

    public static int dDay(int myear, int mmonth, int mday) {
        try {
            Calendar today = Calendar.getInstance(); //현재 오늘 날짜
            Calendar dday = Calendar.getInstance();


            dday.set(myear,mmonth,mday);// D-day의 날짜를 입력합니다.


            long day = dday.getTimeInMillis()/86400000;
            // 각각 날의 시간 값을 얻어온 다음
            //( 1일의 값(86400000 = 24시간 * 60분 * 60초 * 1000(1초값) ) )


            long tday = today.getTimeInMillis()/86400000;
            long count = tday - day; // 오늘 날짜에서 dday 날짜를 빼주게 됩니다.
            return (int) count+1; // 날짜는 하루 + 시켜줘야합니다.
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }
}
