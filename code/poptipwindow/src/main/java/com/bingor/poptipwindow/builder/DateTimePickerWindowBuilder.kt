package com.bingor.poptipwindow.builder

import android.content.Context
import android.support.annotation.ColorInt
import android.support.annotation.FloatRange
import android.support.annotation.IntRange
import android.view.View
import com.bingor.poptipwindow.impl.OnDataTimeDialogListener
import com.bingor.poptipwindow.impl.OnWindowStateChangedListener
import com.bingor.poptipwindow.view.picker.datetimepicker.DateTimePickerView
import com.bingor.poptipwindow.view.tip.Tip

/**
 * 日期选择题提示窗构建器
 * Created by HXB on 2018/10/8.
 */
class DateTimePickerWindowBuilder : TipWindowBuilder<DateTimePickerWindowBuilder> {

    constructor(context: Context, tipType: Int) : super(context, tipType) {
        tip.contentView = DateTimePickerView(context)
    }

    fun setOK(textOK: String): DateTimePickerWindowBuilder {
        tip.textOK = textOK
        return this
    }

    fun setCancel(textCancel: String): DateTimePickerWindowBuilder {
        tip.textCancel = textCancel
        return this
    }

    fun setOnDataTimeDialogListener(onDataTimeDialogListener: OnDataTimeDialogListener): DateTimePickerWindowBuilder {
        tip.onWindowStateChangedListener = object : OnWindowStateChangedListener {
            override fun onOKClicked() {
                var picker = tip.contentView as DateTimePickerView
                when (picker.type) {
                    DateTimePickerView.TYPE_NORMAL -> {
                        onDataTimeDialogListener.onOKClicked(picker.getDateTimeSelect("yyyy-MM-dd HH:mm"), picker.dateTimeSelect)
                    }
                    DateTimePickerView.TYPE_JUST_DATE -> {
                        onDataTimeDialogListener.onOKClicked(picker.getDateTimeSelect("yyyy-MM-dd"), picker.dateTimeSelect)
                    }
                    DateTimePickerView.TYPE_JUST_TIME -> {
                        onDataTimeDialogListener.onOKClicked(picker.getDateTimeSelect("HH:mm"), picker.dateTimeSelect)
                    }
                }
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

    /**
     * 滚轮竖向间距
     */
    fun setLineSpaceMultiplier(@IntRange(from = 2, to = 4) lineSpaceMultiplier: Int): DateTimePickerWindowBuilder {
        (tip.contentView as DateTimePickerView).setLineSpaceMultiplier(lineSpaceMultiplier)
        return this
    }

    fun setTextSize(textSizePX: Int): DateTimePickerWindowBuilder {
        (tip.contentView as DateTimePickerView).setTextSize(textSizePX)
        return this
    }

    fun setTextColorNormal(@ColorInt textColorNormal: Int): DateTimePickerWindowBuilder {
        (tip.contentView as DateTimePickerView).setTextColorNormal(textColorNormal)
        return this
    }

    fun setTextColorFocus(@ColorInt textColorFocus: Int): DateTimePickerWindowBuilder {
        (tip.contentView as DateTimePickerView).setTextColorFocus(textColorFocus)
        return this
    }

    fun setDividerWidthRatio(@FloatRange(from = 0.0, to = 1.0) dividerWidthRatio: Float): DateTimePickerWindowBuilder {
        (tip.contentView as DateTimePickerView).setDividerWidthRatio(dividerWidthRatio)
        return this
    }

    fun setDividerColor(@ColorInt dividerColor: Int): DateTimePickerWindowBuilder {
        (tip.contentView as DateTimePickerView).setDividerColor(dividerColor)
        return this
    }

    fun setVisibleItemCount(visibleItemCount: Int): DateTimePickerWindowBuilder {
        (tip.contentView as DateTimePickerView).setVisibleItemCount(visibleItemCount)
        return this
    }

    fun setCycleable(cycleable: Boolean): DateTimePickerWindowBuilder {
        (tip.contentView as DateTimePickerView).setCycleable(cycleable)
        return this
    }

    fun setDateTimeStart(year: Int, month: Int, day: Int, hour: Int, minute: Int): DateTimePickerWindowBuilder {
        (tip.contentView as DateTimePickerView).setDateTimeStart(year, month, day, hour, minute)
        return this
    }

    fun setDateTimeStart(dateTimeStartMillis: Long): DateTimePickerWindowBuilder {
        (tip.contentView as DateTimePickerView).setDateTimeStart(dateTimeStartMillis)
        return this
    }

    fun setDateTimeEnd(year: Int, month: Int, day: Int, hour: Int, minute: Int): DateTimePickerWindowBuilder {
        (tip.contentView as DateTimePickerView).setDateTimeEnd(year, month, day, hour, minute)
        return this
    }

    fun setDateTimeEnd(dateTimeEndMillis: Long): DateTimePickerWindowBuilder {
        (tip.contentView as DateTimePickerView).setDateTimeEnd(dateTimeEndMillis)
        return this
    }

    fun setDateTimeInit(year: Int, month: Int, day: Int, hour: Int, minute: Int): DateTimePickerWindowBuilder {
        (tip.contentView as DateTimePickerView).setDateTimeInit(year, month, day, hour, minute)
        return this
    }

    fun setDateTimeInit(dateTimeInitMillis: Long): DateTimePickerWindowBuilder {
        (tip.contentView as DateTimePickerView).setDateTimeInit(dateTimeInitMillis)
        return this
    }

//    fun setTabIndicatorColor(tabIndicatorColor: Int): DateTimePickerWindowBuilder {
//        (tip.contentView as DateTimePickerView).setTabIndicatorColor(tabIndicatorColor)
//        return this
//    }

    fun setType(type: Int): DateTimePickerWindowBuilder {
        (tip.contentView as DateTimePickerView).setType(type)
        return this
    }

    override fun create(): Tip {
        (tip.contentView as DateTimePickerView).init()
        tip.setContentNeedPaddingTop(false)
        return super.create()
    }

}
