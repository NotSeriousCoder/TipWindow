package com.bingor.poptipwindow.impl;

import android.view.View;
import android.widget.AdapterView;

import com.bingor.picker.datetimepickerdialog.wheel.WheelItem;

import java.util.List;

/**
 * Created by HXB on 2018/9/20.
 */
public interface OnItemClickListener<T> {
    void onItemClick(AdapterView<?> parent, View view, int position, long id, T data);

    void onItemDeleteClick(int position, T data);
}
