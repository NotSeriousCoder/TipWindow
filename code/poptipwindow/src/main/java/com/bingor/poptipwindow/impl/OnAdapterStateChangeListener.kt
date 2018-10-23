package com.bingor.poptipwindow.impl

/**
 * 列表模式中，Item状态发生变化，则调用本接口
 * [com.bingor.poptipwindow.adapter.GeneralAdapter.setOnAdapterStateChangeListener]
 * Created by HXB on 2018/9/26.
 */
interface OnAdapterStateChangeListener<T> {
    /**
     * 如有删除按钮，点击则调用本方法
     *
     * @param position item位置
     * @param data     position对应的数据
     */
    fun onItemDeleteClick(position: Int, data: T)
}