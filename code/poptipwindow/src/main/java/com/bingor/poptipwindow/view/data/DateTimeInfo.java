package com.bingor.poptipwindow.view.data;

import com.bingor.poptipwindow.util.DateTimeUtil;

import java.util.Calendar;

/**
 * DateTimePickerView数据的Bean
 * Created by HXB on 2018/10/19.
 */
public class DateTimeInfo {
    public long timeMillis;
    public int year;
    public int month;
    public int day;
    public int hour;
    public int minute;

    public DateTimeInfo(long timeMillis) {
        this.timeMillis = timeMillis;
        millis2Date();
    }

    public DateTimeInfo(int year, int month, int day, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        date2Millis();
    }

    public long date2Millis() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, hour, minute);
        timeMillis = calendar.getTimeInMillis();
        return timeMillis;
    }

    public void millis2Date() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
    }

    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
        millis2Date();
    }

    public void setDateTime(int year, int month, int day, int hour, int minute) {
        this.year = year;
        this.month = month;
        int rightDays = DateTimeUtil.getDaysOfMonth(year, month);
        if (rightDays < day) {
            day = rightDays;
        }
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        date2Millis();
    }

    public void setYear(int year) {
        this.year = year;
        int rightDays = DateTimeUtil.getDaysOfMonth(year, month);
        if (day > rightDays) {
            day = rightDays;
        }
        date2Millis();
    }

    public void setMonth(int month) {
        this.month = month;
        int rightDays = DateTimeUtil.getDaysOfMonth(year, month);
        if (day > rightDays) {
            day = rightDays;
        }
        date2Millis();
    }

    public void setDay(int day) {
        this.day = day;
        int rightDays = DateTimeUtil.getDaysOfMonth(year, month);
        if (day > rightDays) {
            this.day = rightDays;
        }
        date2Millis();
    }

    public void setHour(int hour) {
        this.hour = hour;
        date2Millis();
    }

    public void setMinute(int minute) {
        this.minute = minute;
        date2Millis();
    }
}
