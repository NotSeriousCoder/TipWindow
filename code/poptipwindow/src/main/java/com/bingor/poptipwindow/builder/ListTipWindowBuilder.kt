package com.bingor.poptipwindow.builder

import android.content.Context
import com.bingor.poptipwindow.adapter.GeneralAdapter
import com.bingor.poptipwindow.impl.OnItemClickListener

/**
 * 列表提示窗构建器
 * Created by HXB on 2018/10/8.
 */
class ListTipWindowBuilder(context: Context, tipType: Int) : TipWindowBuilder<ListTipWindowBuilder>(context, tipType) {

    fun setAdapter(adapter: GeneralAdapter<*>): ListTipWindowBuilder {
        tip.adapter = adapter
        return this
    }

    fun <T> setOnItemClickListener(onItemClickListener: OnItemClickListener<T>): ListTipWindowBuilder {
        tip.onItemClickListener = onItemClickListener
        return this
    }

    fun setMaxHeight(maxHeight: Int): ListTipWindowBuilder {
        tip.maxHeight = maxHeight
        return this
    }

}
