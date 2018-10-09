package com.bingor.poptipwindow.builder

import android.content.Context
import com.bingor.poptipwindow.TipWindow
import com.bingor.poptipwindow.adapter.GeneralAdapter
import com.bingor.poptipwindow.impl.OnItemClickListener

/**
 * Created by HXB on 2018/10/8.
 */
class ListTipWindowBuilder(context: Context) : TipWindowBuilder<ListTipWindowBuilder>(context) {

    fun setAdapter(adapter: GeneralAdapter<*>): ListTipWindowBuilder {
        tipWindow.adapter = adapter
        return this
    }

    fun <T> setOnItemClickListener(onItemClickListener: OnItemClickListener<T>): ListTipWindowBuilder {
        tipWindow.onItemClickListener = onItemClickListener
        return this
    }

    fun setMaxHeight(maxHeight: Int): ListTipWindowBuilder {
        tipWindow.maxHeight = maxHeight
        return this
    }

}
