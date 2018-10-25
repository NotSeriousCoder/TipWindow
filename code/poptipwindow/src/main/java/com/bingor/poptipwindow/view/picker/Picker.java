package com.bingor.poptipwindow.view.picker;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bingor.poptipwindow.R;

/**
 * 选择器基类
 * Created by HXB on 2018/10/17.
 */
public abstract class Picker extends LinearLayout {
    //竖向间距(2-4)
    protected int lineSpaceMultiplier;
    //文字大小
    protected int textSize;
    //旁边文字颜色 焦点文字颜色
    protected int textColorNormal, textColorFocus;
    //分割线
    protected float dividerWidthRatio;
    protected int dividerColor;
    //可见项数量
    protected int visibleItemCount;
    //能否循环
    protected boolean cycleable;


    public Picker(Context context) {
        super(context);
        lineSpaceMultiplier = 2;
        textSize = (int) (getContext().getResources().getDisplayMetrics().density * 14);
        textColorNormal = getResources().getColor(R.color.text_no_focus);
        textColorFocus = getResources().getColor(R.color.text_focus);
        dividerWidthRatio = 1.0f;
        dividerColor = getResources().getColor(R.color.divider);
        visibleItemCount = 3;
        //能否循环
        cycleable = true;
        init();
    }

    public Picker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Picker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Picker);
        lineSpaceMultiplier = ta.getInteger(R.styleable.Picker_lineSpaceMultiplier, 2);
        textSize = ta.getDimensionPixelSize(R.styleable.Picker_textSize, (int) (getContext().getResources().getDisplayMetrics().density * 14));
        textColorNormal = ta.getColor(R.styleable.Picker_textColorNormal, getResources().getColor(R.color.text_no_focus));
        textColorFocus = ta.getColor(R.styleable.Picker_textColorFocus, getResources().getColor(R.color.text_focus));
        dividerWidthRatio = ta.getFloat(R.styleable.Picker_dividerWidthRatio, 1.0f);
        dividerColor = ta.getColor(R.styleable.Picker_dividerColor, getResources().getColor(R.color.divider));
        visibleItemCount = ta.getInteger(R.styleable.Picker_visibleItemCount, 3);
        //能否循环
        cycleable = ta.getBoolean(R.styleable.Picker_cycleable, true);

        init();
    }

    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    public int getLineSpaceMultiplier() {
        return lineSpaceMultiplier;
    }

    /**
     * 滚轮竖向间距
     * @param lineSpaceMultiplier
     * @return
     */
    public Picker setLineSpaceMultiplier(@IntRange(from = 2, to = 4) int lineSpaceMultiplier) {
        this.lineSpaceMultiplier = lineSpaceMultiplier;
        return this;
    }

    public int getTextSize() {
        return textSize;
    }

    public Picker setTextSize(int textSizePX) {
        this.textSize = textSizePX;
        return this;
    }

    public int getTextColorNormal() {
        return textColorNormal;
    }

    public Picker setTextColorNormal(@ColorInt int textColorNormal) {
        this.textColorNormal = textColorNormal;
        return this;
    }

    public int getTextColorFocus() {
        return textColorFocus;
    }

    public Picker setTextColorFocus(@ColorInt int textColorFocus) {
        this.textColorFocus = textColorFocus;
        return this;
    }

    public float getDividerWidthRatio() {
        return dividerWidthRatio;
    }

    public Picker setDividerWidthRatio(@FloatRange(from = 0, to = 1) float dividerWidthRatio) {
        this.dividerWidthRatio = dividerWidthRatio;
        return this;
    }

    public int getDividerColor() {
        return dividerColor;
    }

    public Picker setDividerColor(@ColorInt int dividerColor) {
        this.dividerColor = dividerColor;
        return this;
    }

    public int getVisibleItemCount() {
        return visibleItemCount;
    }

    public Picker setVisibleItemCount(int visibleItemCount) {
        this.visibleItemCount = visibleItemCount;
        return this;
    }

    public boolean isCycleable() {
        return cycleable;
    }

    public Picker setCycleable(boolean cycleable) {
        this.cycleable = cycleable;
        return this;
    }
}
