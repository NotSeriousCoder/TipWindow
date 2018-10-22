package com.bingor.poptipwindow.builder

import android.content.Context
import android.support.annotation.ColorInt
import android.support.annotation.FloatRange
import android.support.annotation.IntRange
import com.bingor.poptipwindow.TipWindow
import com.bingor.poptipwindow.impl.OnDataTimeDialogListener
import com.bingor.poptipwindow.impl.OnWindowStateChangedListener
import com.bingor.poptipwindow.view.picker.datetimepicker.DateTimePickerView

/**
 * Created by HXB on 2018/10/8.
 */
class DateTimePickerWindowBuilder : TipWindowBuilder<DateTimePickerWindowBuilder> {

    constructor(context: Context) : super(context) {
        tipWindow.contentView = DateTimePickerView(context)
    }

    fun setOK(textOK: String): DateTimePickerWindowBuilder {
        tipWindow.textOK = textOK
        return this
    }

    fun setCancel(textCancel: String): DateTimePickerWindowBuilder {
        tipWindow.textCancel = textCancel
        return this
    }

    fun setOnDataTimeDialogListener(onDataTimeDialogListener: OnDataTimeDialogListener): DateTimePickerWindowBuilder {
        tipWindow.onWindowStateChangedListener = object : OnWindowStateChangedListener {
            override fun onOKClicked() {
                var picker = tipWindow.contentView as DateTimePickerView
                onDataTimeDialogListener.onOKClicked(picker.getDateTimeSelect("yyyy-MM-dd HH:mm"), picker.dateTimeSelect)
            }

            override fun onCancelClicked() {
                onDataTimeDialogListener.onCancelClicked()
            }

            override fun onOutsideClicked() {
                onDataTimeDialogListener.onOutsideClicked()
            }
        }
        return this
    }

    fun setLineSpaceMultiplier(@IntRange(from = 2, to = 4) lineSpaceMultiplier: Int): DateTimePickerWindowBuilder {
        (tipWindow.contentView as DateTimePickerView).setLineSpaceMultiplier(lineSpaceMultiplier)
        return this
    }

    fun setTextSize(textSizePX: Int): DateTimePickerWindowBuilder {
        (tipWindow.contentView as DateTimePickerView).setTextSize(textSizePX)
        return this
    }

    fun setTextColorNormal(@ColorInt textColorNormal: Int): DateTimePickerWindowBuilder {
        (tipWindow.contentView as DateTimePickerView).setTextColorNormal(textColorNormal)
        return this
    }

    fun setTextColorFocus(@ColorInt textColorFocus: Int): DateTimePickerWindowBuilder {
        (tipWindow.contentView as DateTimePickerView).setTextColorFocus(textColorFocus)
        return this
    }

    fun setDividerWidthRatio(@FloatRange(from = 0.0, to = 1.0) dividerWidthRatio: Float): DateTimePickerWindowBuilder {
        (tipWindow.contentView as DateTimePickerView).setDividerWidthRatio(dividerWidthRatio)
        return this
    }

    fun setDividerColor(@ColorInt dividerColor: Int): DateTimePickerWindowBuilder {
        (tipWindow.contentView as DateTimePickerView).setDividerColor(dividerColor)
        return this
    }

    fun setVisibleItemCount(visibleItemCount: Int): DateTimePickerWindowBuilder {
        (tipWindow.contentView as DateTimePickerView).setVisibleItemCount(visibleItemCount)
        return this
    }

    fun setCycleable(cycleable: Boolean): DateTimePickerWindowBuilder {
        (tipWindow.contentView as DateTimePickerView).setCycleable(cycleable)
        return this
    }

    fun setDateTimeStart(year: Int, month: Int, day: Int, hour: Int, minute: Int): DateTimePickerWindowBuilder {
        (tipWindow.contentView as DateTimePickerView).setDateTimeStart(year, month, day, hour, minute)
        return this
    }

    fun setDateTimeStart(dateTimeStartMillis: Long): DateTimePickerWindowBuilder {
        (tipWindow.contentView as DateTimePickerView).setDateTimeStart(dateTimeStartMillis)
        return this
    }

    fun setDateTimeEnd(year: Int, month: Int, day: Int, hour: Int, minute: Int): DateTimePickerWindowBuilder {
        (tipWindow.contentView as DateTimePickerView).setDateTimeEnd(year, month, day, hour, minute)
        return this
    }

    fun setDateTimeEnd(dateTimeEndMillis: Long): DateTimePickerWindowBuilder {
        (tipWindow.contentView as DateTimePickerView).setDateTimeEnd(dateTimeEndMillis)
        return this
    }

    fun setDateTimeInit(year: Int, month: Int, day: Int, hour: Int, minute: Int): DateTimePickerWindowBuilder {
        (tipWindow.contentView as DateTimePickerView).setDateTimeInit(year, month, day, hour, minute)
        return this
    }

    fun setDateTimeInit(dateTimeInitMillis: Long): DateTimePickerWindowBuilder {
        (tipWindow.contentView as DateTimePickerView).setDateTimeInit(dateTimeInitMillis)
        return this
    }

    fun setTabIndicatorColor(tabIndicatorColor: Int): DateTimePickerWindowBuilder {
        (tipWindow.contentView as DateTimePickerView).setTabIndicatorColor(tabIndicatorColor)
        return this
    }

    override fun create(): TipWindow {
        (tipWindow.contentView as DateTimePickerView).init()
        tipWindow.setContentNeedPaddingTop(false)
        return super.create()
    }

}
