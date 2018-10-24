package com.bingor.poptipwindow.builder

import android.content.Context
import android.support.annotation.ColorInt
import android.support.annotation.FloatRange
import android.support.annotation.IntRange
import com.bingor.poptipwindow.impl.OnDataSelectedListener
import com.bingor.poptipwindow.impl.OnWindowStateChangedListener
import com.bingor.poptipwindow.view.picker.universalpicker.UniversalPickerView
import com.bingor.poptipwindow.view.wheel.WheelItem

/**
 * 通用数据选择器提示窗构建器
 * Created by HXB on 2018/10/8.
 */
class UniversalPickerWindowBuilder : TipWindowBuilder<UniversalPickerWindowBuilder> {

    constructor(context: Context, tipType: Int) : super(context, tipType) {
        tip.contentView = UniversalPickerView(context)
    }

    fun setOK(textOK: String): UniversalPickerWindowBuilder {
        tip.textOK = textOK
        return this
    }

    fun setCancel(textCancel: String): UniversalPickerWindowBuilder {
        tip.textCancel = textCancel
        return this
    }

    fun setOnDataSelectedListener(onDataSelectedListener: OnDataSelectedListener): UniversalPickerWindowBuilder {
        tip.onWindowStateChangedListener = object : OnWindowStateChangedListener {
            override fun onOKClicked() {
                var picker = tip.contentView as UniversalPickerView
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
        (tip.contentView as UniversalPickerView).setLineSpaceMultiplier(lineSpaceMultiplier)
        return this
    }

    fun setTextSize(textSizePX: Int): UniversalPickerWindowBuilder {
        (tip.contentView as UniversalPickerView).setTextSize(textSizePX)
        return this
    }

    fun setTextColorNormal(@ColorInt textColorNormal: Int): UniversalPickerWindowBuilder {
        (tip.contentView as UniversalPickerView).setTextColorNormal(textColorNormal)
        return this
    }

    fun setTextColorFocus(@ColorInt textColorFocus: Int): UniversalPickerWindowBuilder {
        (tip.contentView as UniversalPickerView).setTextColorFocus(textColorFocus)
        return this
    }

    fun setDividerWidthRatio(@FloatRange(from = 0.0, to = 1.0) dividerWidthRatio: Float): UniversalPickerWindowBuilder {
        (tip.contentView as UniversalPickerView).setDividerWidthRatio(dividerWidthRatio)
        return this
    }

    fun setDividerColor(@ColorInt dividerColor: Int): UniversalPickerWindowBuilder {
        (tip.contentView as UniversalPickerView).setDividerColor(dividerColor)
        return this
    }

    fun setVisibleItemCount(visibleItemCount: Int): UniversalPickerWindowBuilder {
        (tip.contentView as UniversalPickerView).setVisibleItemCount(visibleItemCount)
        return this
    }

    fun setCycleable(cycleable: Boolean): UniversalPickerWindowBuilder {
        (tip.contentView as UniversalPickerView).setCycleable(cycleable)
        return this
    }


    fun setDatas(datas: List<WheelItem>): UniversalPickerWindowBuilder {
        (tip.contentView as UniversalPickerView).setDatas(datas)
        return this
    }
}
