package com.bingor.poptipwindow.view.tip;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.view.Window;

import com.bingor.poptipwindow.R;


/**
 * 全屏弹窗
 * Created by HXB on 2017-03-20.
 */

public class CustomDialog extends Dialog {
    public CustomDialog(Context context) {
        this(context, R.style.customDialogStyle);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //这里设置的透明貌似不起作用，可能跟targetSdkVersion 28有关
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

}
