package com.bingor.poptipwindow.impl

import com.bingor.poptipwindow.view.wheel.WheelView


/**
 * 滚轮回调
 * [WheelView.setOnItemSelectListener]
 * [WheelView.itemSelectedCallback]
 * Created by HXB on 2018/10/16.
 */
interface OnItemSelectListener<DataType> {
    /**
     * 滑动选择回调
     *
     * @param index 当前选择项的索引
     */
    fun <View : WheelView<*>> onSelected(wheelView: View, index: Int, item: DataType)
}