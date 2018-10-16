package com.bingor.poptipwindow.impl

import com.bingor.poptipwindow.view.wheel.WheelItem


/**
 * Created by HXB on 2018/10/15.
 */
interface OnDataSelectedListener {
    fun onOKClicked(items: List<WheelItem>, positions: IntArray)

    fun onCancelClicked()

    fun onOutsideClicked()
}