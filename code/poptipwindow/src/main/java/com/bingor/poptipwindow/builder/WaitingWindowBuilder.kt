package com.bingor.poptipwindow.builder

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import com.bingor.poptipwindow.impl.OnTipBoxStateChangedListener
import com.bingor.poptipwindow.view.LoadingView
import com.bingor.poptipwindow.view.tip.Tip

/**
 * 等待提示窗构建器
 * Created by HXB on 2018/10/8.
 */
class WaitingWindowBuilder : TipWindowBuilder<WaitingWindowBuilder> {
    constructor(context: Context, tipType: Int) : super(context, tipType) {
        tip.contentView = LoadingView(context)
        tip.onTipBoxStateChangedListener = object : OnTipBoxStateChangedListener {
            override fun onTipBoxShown() {
                (tip.contentView as LoadingView).showAnim()
            }

            override fun onTipBoxDismissed() {
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
