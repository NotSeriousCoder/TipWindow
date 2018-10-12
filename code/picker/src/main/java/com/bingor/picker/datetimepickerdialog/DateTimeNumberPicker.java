package com.bingor.picker.datetimepickerdialog;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.NumberPicker;


/**
 * Created by HXB on 2017-05-22.
 */

public class DateTimeNumberPicker extends NumberPicker {
    private boolean isSet = false;
    private OnPickerScrollListener onPickerScrollListener;

    public DateTimeNumberPicker(Context context) {
        super(context);
        setListener();
    }

    public DateTimeNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        setListener();
    }

    public DateTimeNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setListener();
    }

    @Override
    public void setValue(int value) {
        isSet = true;
//        setValueInternal();
        super.setValue(value);
    }

    private void setListener() {
        this.setOnValueChangedListener(new OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.d("HXB", "值改变");
                if (onPickerScrollListener != null) {
                    onPickerScrollListener.onValueChange(picker, oldVal, newVal, isSet);
                    isSet = false;
                }
            }
        });
    }

    public OnPickerScrollListener getOnPickerScrollListener() {
        return onPickerScrollListener;
    }

    public void setOnPickerScrollListener(OnPickerScrollListener onPickerScrollListener) {
        this.onPickerScrollListener = onPickerScrollListener;
    }

    public interface OnPickerScrollListener {
        public void onValueChange(NumberPicker picker, int oldVal, int newVal, boolean isSet);
    }

}
