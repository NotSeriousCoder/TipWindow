package com.bingor.poptipwindow.util;

/**
 * Created by HXB on 2017-07-18.
 */

public class DateTimeUtil {
    public static boolean  isLeap(int year) {
        if (year < 0) {
            return false;
        }
        if ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0)) {
            return true;
        } else {
            return false;
        }
    }

    public static int getDaysOfMonth(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (isLeap(year)) {
                    return 29;
                } else {
                    return 28;
                }
        }
        return 0;
    }

}
