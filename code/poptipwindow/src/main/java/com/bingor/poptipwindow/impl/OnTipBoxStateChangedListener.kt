package com.bingor.poptipwindow.impl

/**
 * 当提示框状态改变（出现、消失）的回调
 * @sample com.bingor.poptipwindow.view.tip.Tip.show
 * @sample com.bingor.poptipwindow.view.tip.Tip.dismiss
 * Created by HXB on 2018/10/23.
 */
interface OnTipBoxStateChangedListener {
    fun onTipBoxShown()

    fun onTipBoxDismissed()
}