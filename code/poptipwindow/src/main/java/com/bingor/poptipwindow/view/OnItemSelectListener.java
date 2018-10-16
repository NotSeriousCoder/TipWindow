package com.bingor.poptipwindow.view;

import com.bingor.poptipwindow.view.wheel.WheelView;

/**
 * Created by HXB on 2018/10/16.
 */
public interface OnItemSelectListener<DataType> {
    /**
     * 滑动选择回调
     *
     * @param index 当前选择项的索引
     */
    <View extends WheelView> void onSelected(View wheelView, int index, DataType item);
}
