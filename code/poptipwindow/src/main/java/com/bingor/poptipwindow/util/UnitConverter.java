package com.bingor.poptipwindow.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;


public class UnitConverter {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    protected static DisplayMetrics displayMetrics;

    public static DisplayMetrics getDisplayMetrics(Context context) {
        if (displayMetrics == null) {
            displayMetrics = context.getResources().getDisplayMetrics();
        }
        return displayMetrics;
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = getDisplayMetrics(context).density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = getDisplayMetrics(context).density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(Context context, float dipValue, int orientation) {
        if (orientation == HORIZONTAL) {
            return (int) (dipValue * (getDisplayMetrics(context).widthPixels / 320.0));
        } else {
            return (int) (dipValue * (getDisplayMetrics(context).heightPixels / 480.0));
        }
    }

    public static int px2dip(Context context, float pxValue, int orientation) {
        if (orientation == HORIZONTAL) {
            return (int) (pxValue / (getDisplayMetrics(context).widthPixels / 320.0));
        } else {
            return (int) (pxValue / (getDisplayMetrics(context).heightPixels / 480.0));
        }
    }

    public static float applyDimension(Context context, int unit, float size) {
        Resources r = context.getResources();
        return TypedValue.applyDimension(unit, size, r.getDisplayMetrics());
    }
}
