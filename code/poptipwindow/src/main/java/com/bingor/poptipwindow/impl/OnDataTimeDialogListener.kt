package com.bingor.poptipwindow.impl

import com.bingor.poptipwindow.view.wheel.WheelItem


/**
 * Created by HXB on 2018/10/15.
 */
interface OnDataTimeDialogListener {
    fun onOKClicked(dateTimeFormat: String, dateTime: Long)

    fun onCancelClicked()

    fun onOutsideClicked()
}