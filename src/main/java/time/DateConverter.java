package time;

import model.description.DescriptionFactory;
import model.order.OrderFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class DateConverter {
    public static Calendar calendar = Calendar.getInstance();
    public static Calendar calendarAfterPeriod = Calendar.getInstance();

    public static void main(String[] args) {
//        long l1 = DateConverter.dateFromIntToLong(2019, 0, 10);
//        long l1 = DateConverter.dateFromStringToLong("26.04.2019");
//        System.out.println(l1);
//        System.out.println(DateConverter.dateToString(l1));
//        System.out.println(DateConverter.timeToString(l1));
//        System.out.println(DateConverter.dateFromIntToLong(2019, 4, 10));
    }

    public static long getNowDate(){
        return new Date().getTime();
    }

    public static int getYear(long millis) {
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.YEAR);
    }

    public static int getYearShort(long millis) {
        String year = String.valueOf(getYear(millis));
        return Integer.valueOf(year.substring(2));
    }

    public static long offset (long millisecond, int duration) {
        calendarAfterPeriod.setTimeInMillis(millisecond);
        calendarAfterPeriod.add(Calendar.DATE, Integer.valueOf(duration));
        return calendarAfterPeriod.getTimeInMillis();
    }

    public static long dateAfterThreeDay(OrderFactory order) {
        long startTimeInMillis = 0L;
        long endTimeInMillis = 0L;
        for (DescriptionFactory descr : order.getDescriptions()) {
            if (descr.getType().equalsIgnoreCase("КБ")) {
                startTimeInMillis = descr.getStatusTimeList()[2];
                break;
            }
        }
        LocalDateTime dateStart = new Timestamp(startTimeInMillis).toLocalDateTime();
        int dayForDevelopment = 3;
        int startDayOfWeek = dateStart.getDayOfWeek().getValue();
        if (startDayOfWeek > 2) {
            dayForDevelopment = 5;
        }
        endTimeInMillis = DateConverter.offset(startTimeInMillis, dayForDevelopment);
        return endTimeInMillis;
    }

    public static int getPeriod (LocalDate dateStart, LocalDate dateEnd) {
        int day1 = dateStart.getDayOfYear();
        int day2 = dateEnd.getDayOfYear();
        if  (dateStart.equals(dateEnd))
            return 1;
        else if (dateStart.getYear() == dateEnd.getYear())
            return day2 - day1 + 1;
        else {
            java.sql.Date date1 = java.sql.Date.valueOf(dateStart);
            java.sql.Date date2 = java.sql.Date.valueOf(dateEnd);
            return 1 + (int)Math.round( (date2.getTime() - date1.getTime()) / 86400000.0 );
        }
    }

    public static int getPeriodForDesigner (long endTime) {

        java.sql.Date date1 = new java.sql.Date(DateConverter.getNowDate());
        java.sql.Date date2 = new java.sql.Date(endTime);
        return (int)Math.round( (date2.getTime() - date1.getTime()) / 86400000.0 );

    }


    public static String dateToString(long millis) {
        if ( (Long)millis == null | millis == 0 ) {
            return "-";
        }
        String date = "";
        calendar.setTimeInMillis(millis);
        int day, month;
        if ((day = calendar.get(Calendar.DAY_OF_MONTH))<10) {
            date += "0" + day + ".";
        }
        else {
            date += "" + day + ".";
        }
        if ((month = calendar.get(Calendar.MONTH) + 1 )<10) {
            date += "0" + month;
        }
        else {
            date += month;
        }
        return date;
    }

    public static String dateWithYearToString(long millis) {
        if ( (Long)millis == null | millis == 0 ) {
            return "-";
        }
        else return dateToString(millis) + "." + (String.valueOf(calendar.get(Calendar.YEAR))).substring(2);
    }

    public static String dateForDatePicker(long millis) {
        String date = "";
        calendar.setTimeInMillis(millis);
        int day, month;
        if ((day = calendar.get(Calendar.DAY_OF_MONTH))<10) {
            date += "0" + day + ".";
        }
        else {
            date += "" + day + ".";
        }
        if ((month = calendar.get(Calendar.MONTH) + 1 )<10) {
            date += "0" + month + ".";
        }
        else {
            date += month + ".";
        }
        return date + calendar.get(Calendar.YEAR);
    }

    public static String timeToString(long millis) {
        calendar.setTimeInMillis(millis);
        String time = "";
        int hour, min;
        if ((hour = calendar.get(Calendar.HOUR_OF_DAY))<10) {
            time += "0" + hour + ":";
        }
        else {
            time += hour + ":";
        }

        if ((min = calendar.get(Calendar.MINUTE) )<10) {
            time += "0" + min;
        }
        else {
            time += min;
        }
        return time;
    }

    public static long dateFromIntToLong (int year, int mon, int date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, mon, date);
        return calendar.getTimeInMillis();
    }

    public static long dateFromStringToLong (String dateString) {
        CharSequence c1 = ".";
        CharSequence c2 = "/";
        String[] lines = null;
        if (dateString.contains(c1)) {
            lines = dateString.split("\\.");
        }
        else if (dateString.contains(c2)) {
            lines = dateString.split("/");
        }
       int year, mon, day;
        day = Integer.valueOf(lines[0]);
        mon = Integer.valueOf(lines[1]) - 1;
        year = Integer.valueOf(lines[2]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, mon, day, 0, 0, 0);
        return calendar.getTimeInMillis();
    }

    public static Date stringToDate(String s) {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);

        try {
            Date d = format.parse(s);
            return d;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String dateToString(Date d) {
        DateFormat format = new SimpleDateFormat("dd.MM.yy", Locale.ENGLISH);
        String s = format.format(d);
        return s;
    }

    public static LocalDate dateToLocalDate(java.util.Date d) {
        LocalDate ld = new java.sql.Date(d.getTime()).toLocalDate();
        return ld;
    }

    public static java.util.Date dateToLocalDate(LocalDate localDate) {
        java.util.Date date = java.sql.Date.valueOf(localDate);
        return date;
    }

    public static LocalDate getNowLocalDate() {
        java.sql.Date d = new java.sql.Date(getNowDate());
        return new java.sql.Date(d.getTime()).toLocalDate();
    }

    public static LocalDate getLocalDateOffset(LocalDate firstLocalDate, int period) {
        java.util.Date oldDate = java.sql.Date.valueOf(firstLocalDate);
        long l2 = offset(oldDate.getTime(), period);
        java.sql.Date d2 = new java.sql.Date(l2);
        return new java.sql.Date(d2.getTime()).toLocalDate();
    }

}
