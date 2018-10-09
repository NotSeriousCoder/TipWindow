package com.bingor.poptipwindow.builder

import android.content.Context
import android.view.View
import com.bingor.poptipwindow.TipWindow
import com.bingor.poptipwindow.adapter.GeneralAdapter
import com.bingor.poptipwindow.impl.OnItemClickListener
import com.bingor.poptipwindow.impl.OnWindowStateChangedListener

/**
 * Created by HXB on 2018/10/8.
 */
class CustomTipWindowBuilder(context: Context) : TipWindowBuilder<CustomTipWindowBuilder>(context) {

    fun setContentView(contentView: View): CustomTipWindowBuilder {
        tipWindow.contentView = contentView
        return this
    }

    fun setTextContent(textContent: String): CustomTipWindowBuilder {
        tipWindow.textContent = textContent
        return this
    }


    fun setOK(textOK: String): CustomTipWindowBuilder {
        tipWindow.textOK = textOK
        return this
    }

    fun setCancel(textCancel: String): CustomTipWindowBuilder {
        tipWindow.textCancel = textCancel
        return this
    }

    fun setOnWindowStateChangedListener(onWindowStateChangedListener: OnWindowStateChangedListener): CustomTipWindowBuilder {
        tipWindow.onWindowStateChangedListener = onWindowStateChangedListener
        return this
    }
}
