package com.bingor.poptipwindow.impl

import android.view.View
import android.widget.AdapterView

/**
 * Created by HXB on 2018/9/20.
 */
interface OnItemClickListener<T> {
    fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long, data: T)

    fun onItemDeleteClick(position: Int, data: T)
}