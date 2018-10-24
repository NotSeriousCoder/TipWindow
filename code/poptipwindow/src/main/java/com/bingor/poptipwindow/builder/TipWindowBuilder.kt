package com.bingor.poptipwindow.builder

import android.content.Context
import com.bingor.poptipwindow.view.tip.Tip
import com.bingor.poptipwindow.view.tip.TipDialog
import com.bingor.poptipwindow.view.tip.TipWindow

/**
 * 提示窗构建器
 * Created by HXB on 2018/10/8.
 */
abstract class TipWindowBuilder<T> {
    //提示框载体类型
    companion object {
        //PopUpWindow类型
        @JvmField
        val TIP_TYPE_WINDOW = 0
        //Dialog类型
        @JvmField
        val TIP_TYPE_DIALOG = 1
    }

    protected var tip: Tip

    constructor(context: Context, tipType: Int) {
        if (tipType == TIP_TYPE_WINDOW) {
            tip = TipWindow()
        } else {
            tip = TipDialog()
        }
        tip.context = context
    }

    /**
     * 设置背景透明度
     *
     * @param alpha 0~1
     * @return
     */
    open fun setAlpha(alpha: Float): T {
        if (alpha >= 1.0f) {
            tip.alpha = 255
        } else if (alpha <= 0f) {
            tip.alpha = 0
        } else {
            tip.alpha = (255 * alpha).toInt()
        }
        return this as T
    }

    /**
     * 设置点击空白处能不能关闭提示窗
     */
    fun setCancelable(cancelable: Boolean): T {
        tip.setCancelable(cancelable)
        return this as T
    }

    open fun create(): Tip {
        tip.init()
        return tip
    }

}