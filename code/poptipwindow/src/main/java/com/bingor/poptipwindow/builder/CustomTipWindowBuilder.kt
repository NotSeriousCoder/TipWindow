package com.bingor.poptipwindow.builder

import android.content.Context
import android.view.View
import com.bingor.poptipwindow.impl.OnWindowStateChangedListener

/**
 * 自定义内容提示窗
 * Created by HXB on 2018/10/8.
 */
class CustomTipWindowBuilder(context: Context, tipType: Int) : TipWindowBuilder<CustomTipWindowBuilder>(context, tipType) {
    /**
     * 设置视图内容
     */
    fun setContentView(contentView: View): CustomTipWindowBuilder {
        tip.contentView = contentView
        return this
    }

    /**
     * 设置文字内容，如果有文字了，contentView就不会生效
     */
    fun setTextContent(textContent: String): CustomTipWindowBuilder {
        tip.textContent = textContent
        return this
    }


    fun setOK(textOK: String): CustomTipWindowBuilder {
        tip.textOK = textOK
        return this
    }

    fun setCancel(textCancel: String): CustomTipWindowBuilder {
        tip.textCancel = textCancel
        return this
    }

    fun setOnWindowStateChangedListener(onWindowStateChangedListener: OnWindowStateChangedListener): CustomTipWindowBuilder {
        tip.onWindowStateChangedListener = onWindowStateChangedListener
        return this
    }
}
