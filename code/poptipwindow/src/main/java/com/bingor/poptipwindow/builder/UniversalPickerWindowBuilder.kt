package com.bingor.poptipwindow.builder

import android.content.Context
import android.support.annotation.ColorInt
import android.support.annotation.FloatRange
import android.support.annotation.IntRange
import com.bingor.poptipwindow.impl.OnDataSelectedListener
import com.bingor.poptipwindow.impl.OnWindowStateChangedListener
import com.bingor.poptipwindow.view.universalpicker.UniversalPickerView
import com.bingor.poptipwindow.view.wheel.WheelItem

/**
 * Created by HXB on 2018/10/8.
 */
class UniversalPickerWindowBuilder : TipWindowBuilder<UniversalPickerWindowBuilder> {

    constructor(context: Context) : super(context) {
        tipWindow.contentView = UniversalPickerView(context)
    }

    fun setOK(textOK: String): UniversalPickerWindowBuilder {
        tipWindow.textOK = textOK
        return this
    }

    fun setCancel(textCancel: String): UniversalPickerWindowBuilder {
        tipWindow.textCancel = textCancel
        return this
    }

    fun setOnDataSelectedListener(onDataSelectedListener: OnDataSelectedListener): UniversalPickerWindowBuilder {
        tipWindow.onWindowStateChangedListener = object : OnWindowStateChangedListener {
            override fun onOKClicked() {
                var picker = tipWindow.contentView as UniversalPickerView
                onDataSelectedListener.onOKClicked(picker.currentItems, picker.currentPosition)
            }

            override fun onCancelClicked() {
                onDataSelectedListener.onCancelClicked()
            }

            override fun onOutsideClicked() {
                onDataSelectedListener.onOutsideClicked()
            }
        }
        return this
    }

    fun setLineSpaceMultiplier(@IntRange(from = 2, to = 4) lineSpaceMultiplier: Int): UniversalPickerWindowBuilder {
        (tipWindow.contentView as UniversalPickerView).setLineSpaceMultiplier(lineSpaceMultiplier)
        return this
    }

    fun setTextSize(textSizePX: Int): UniversalPickerWindowBuilder {
        (tipWindow.contentView as UniversalPickerView).setTextSize(textSizePX)
        return this
    }

    fun setTextColorNormal(@ColorInt textColorNormal: Int): UniversalPickerWindowBuilder {
        (tipWindow.contentView as UniversalPickerView).setTextColorNormal(textColorNormal)
        return this
    }

    fun setTextColorFocus(@ColorInt textColorFocus: Int): UniversalPickerWindowBuilder {
        (tipWindow.contentView as UniversalPickerView).setTextColorFocus(textColorFocus)
        return this
    }

    fun setDividerWidthRatio(@FloatRange(from = 0.0, to = 1.0) dividerWidthRatio: Float): UniversalPickerWindowBuilder {
        (tipWindow.contentView as UniversalPickerView).setDividerWidthRatio(dividerWidthRatio)
        return this
    }

    fun setDividerColor(@ColorInt dividerColor: Int): UniversalPickerWindowBuilder {
        (tipWindow.contentView as UniversalPickerView).setDividerColor(dividerColor)
        return this
    }

    fun setVisibleItemCount(visibleItemCount: Int): UniversalPickerWindowBuilder {
        (tipWindow.contentView as UniversalPickerView).setVisibleItemCount(visibleItemCount)
        return this
    }

    fun setCycleable(cycleable: Boolean): UniversalPickerWindowBuilder {
        (tipWindow.contentView as UniversalPickerView).setCycleable(cycleable)
        return this
    }


    fun setDatas(datas: List<WheelItem>): UniversalPickerWindowBuilder {
        (tipWindow.contentView as UniversalPickerView).setDatas(datas)
        return this
    }
}
