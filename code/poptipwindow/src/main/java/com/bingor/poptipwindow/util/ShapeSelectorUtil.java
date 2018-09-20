package com.bingor.poptipwindow.util;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorInt;

/**
 * Created by HXB on 2017-07-03.
 */

public class ShapeSelectorUtil {
    /**
     * 定义一个shape资源
     *
     * @return
     */
    public static GradientDrawable getDrawable(StrokeWrapper strok, CornersWrapper corners, @ColorInt int soildColor, int gradientType) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        if (gradientType != 0 &&
                (gradientType == GradientDrawable.LINEAR_GRADIENT
                        || gradientType == GradientDrawable.RADIAL_GRADIENT
                        || gradientType == GradientDrawable.SWEEP_GRADIENT)) {
            gradientDrawable.setGradientType(gradientType);
        }

        if (strok != null) {
            gradientDrawable.setStroke(strok.getWidth(), strok.getColor(), strok.getDashWidth(), strok.getDashGap());
        }

        if (corners != null) {
            if (corners.getRadius() != 0) {
                gradientDrawable.setCornerRadius(corners.getRadius());
            } else if (corners.getRadiusArr() != null) {
                gradientDrawable.setCornerRadii(corners.getRadiusArr());
            }
        }
        if (soildColor != 0) {
            gradientDrawable.setColor(soildColor);
        }


        return gradientDrawable;
    }

    public static StateListDrawable getSelector(Drawable normalDrawable, Drawable pressDrawable) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        //给当前的颜色选择器添加选中图片指向状态，未选中图片指向状态
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed}, pressDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, normalDrawable);
        //设置默认状态
        stateListDrawable.addState(new int[]{}, normalDrawable);
        return stateListDrawable;
    }


    public static class StrokeWrapper {
        private int width;
        private int color;
        private float dashGap;
        private float dashWidth;

        public StrokeWrapper() {
            this.color = Color.TRANSPARENT;
        }

        public int getWidth() {
            return width;
        }

        public StrokeWrapper setWidth(int width) {
            this.width = width;
            return this;
        }

        public int getColor() {
            return color;
        }

        public StrokeWrapper setColor(int color) {
            this.color = color;
            return this;
        }

        public float getDashGap() {
            return dashGap;
        }

        public StrokeWrapper setDashGap(float dashGap) {
            this.dashGap = dashGap;
            return this;
        }

        public float getDashWidth() {
            return dashWidth;
        }

        public StrokeWrapper setDashWidth(float dashWidth) {
            this.dashWidth = dashWidth;
            return this;
        }
    }

    public static class CornersWrapper {
        private float radius;
        private float[] radiusArr;

        public float getRadius() {
            return radius;
        }

        public CornersWrapper setRadius(float radius) {
            this.radius = radius;
            return this;
        }

        public float[] getRadiusArr() {
            return radiusArr;
        }

        public CornersWrapper setRadiusArr(float[] radiusArr) {
            this.radiusArr = radiusArr;
            return this;
        }

    }
}
