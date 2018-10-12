package com.bingor.picker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by HXB on 2017-07-17.
 */

public class CalendarBean implements Cloneable {
    private boolean isToday;
    private boolean isSigned;
    private boolean isThisMonth = true;

    private Date dateTime;

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    @Override
    public Object clone() {
        CalendarBean calendarBean = null;
        try {
            calendarBean = (CalendarBean) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return calendarBean;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
        setDateTime();
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
        setDateTime();
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
        setDateTime();
    }

    public boolean isToday() {
        return isToday;
    }

    public void setToday(boolean today) {
        isToday = today;
    }

    public boolean isSigned() {
        return isSigned;
    }

    public void setSigned(boolean signed) {
        isSigned = signed;
    }

    public boolean isThisMonth() {
        return isThisMonth;
    }

    public void setThisMonth(boolean thisMonth) {
        isThisMonth = thisMonth;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
        setDateTime();
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
        setDateTime();
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
        setDateTime();
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
        if (dateTime == null) {
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);
    }

    public void setDateTime() {
        if (year != 0 && month != 0 && day != 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month - 1, day, hour, minute, second);
            this.dateTime = calendar.getTime();
        }
    }
}
