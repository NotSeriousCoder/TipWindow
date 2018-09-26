package com.bingor.poptipwindow.impl;

/**
 * Created by HXB on 2018/9/26.
 */
public interface OnAdapterStateChangeListener<T> {
    void onItemDeleteClick(int position, T data);
}
