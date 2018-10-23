package com.bingor.poptipwindow.builder

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.FloatRange
import android.support.annotation.IntRange
import android.view.ViewGroup
import com.bingor.poptipwindow.impl.OnDataSelectedListener
import com.bingor.poptipwindow.impl.OnWindowStateChangedListener
import com.bingor.poptipwindow.view.LoadingView
import com.bingor.poptipwindow.view.picker.universalpicker.UniversalPickerView
import com.bingor.poptipwindow.view.tip.Tip
import com.bingor.poptipwindow.view.wheel.WheelItem

/**
 * Created by HXB on 2018/10/8.
 */
class WaitingWindowBuilder : TipWindowBuilder<WaitingWindowBuilder> {
    constructor(context: Context, tipType: Int) : super(context, tipType) {
        tip.contentView = LoadingView(context)
        tip.onTipStateChangedListener = object : Tip.OnTipStateChangedListener {
            override fun onTipShown() {
                (tip.contentView as LoadingView).showAnim()
            }

            override fun onTipDismissed() {
                (tip.contentView as LoadingView).stopAnim()
            }
        }
    }


    fun setMsg(msg: String): WaitingWindowBuilder {
        (tip.contentView as LoadingView).setMsg(msg)
        return this
    }


    fun setImageDrawable(drawable: Drawable): WaitingWindowBuilder {
        (tip.contentView as LoadingView).setImageDrawable(drawable)
        return this
    }

    fun setImageResource(@DrawableRes resId: Int): WaitingWindowBuilder {
        (tip.contentView as LoadingView).setImageResource(resId)
        return this
    }

    fun setImageBitmap(bitmap: Bitmap): WaitingWindowBuilder {
        (tip.contentView as LoadingView).setImageBitmap(bitmap)
        return this
    }

    override fun create(): Tip {
        tip.setWrapContent(true)
        tip.setContentCenter(true)
        return super.create()
    }
}
