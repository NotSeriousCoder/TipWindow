package com.bingor.poptipwindow.builder

import android.app.Activity
import android.content.Context
import com.bingor.poptipwindow.TipWindow

/**
 * Created by HXB on 2018/10/8.
 */
abstract class TipWindowBuilder<T> {
    var tipWindow: TipWindow

    constructor(context: Context) {
        tipWindow = TipWindow(context as Activity?)
        tipWindow.context = context
    }

    /**
     * 设置背景透明度
     *
     * @param alpha 0~1
     * @return
     */
    open fun setAlpha(alpha: Float): T {
        if (alpha >= 1.0f) {
            tipWindow.alpha = 255
        } else if (alpha <= 0f) {
            tipWindow.alpha = 0
        } else {
            tipWindow.alpha = (255 * alpha).toInt()
        }
        return this as T
    }


    fun setCancelable(cancelable: Boolean): T {
        tipWindow.setCancelable(cancelable)
        return this as T
    }

    open fun create(): TipWindow {
        tipWindow.init()
        return tipWindow
    }

}