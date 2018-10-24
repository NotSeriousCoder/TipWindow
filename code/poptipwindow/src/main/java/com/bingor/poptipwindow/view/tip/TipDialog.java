package com.bingor.poptipwindow.view.tip;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import com.bingor.poptipwindow.view.LoadingView;

/**
 * 以Dialog为载体的提示窗
 * Created by HXB on 2018/10/22.
 */
public class TipDialog extends Tip {
    private CustomDialog dialog;

    @Override
    protected void initTip() {
        dialog = new CustomDialog(context);
        dialog.setContentView(rootView);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                //LoadingView的情况下停止动画
                if (contentView != null && contentView instanceof LoadingView) {
                    LoadingView loadingView = (LoadingView) contentView;
                    loadingView.stopAnim();
                }
            }
        });
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                //LoadingView的情况下开始动画
                if (contentView != null && contentView instanceof LoadingView) {
                    LoadingView loadingView = (LoadingView) contentView;
                    loadingView.showAnim();
                }
            }
        });
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#000000"));
        colorDrawable.setAlpha(alpha);
        dialog.getWindow().setBackgroundDrawable(colorDrawable);
    }

    @Override
    protected void showTip(View anchor) {
        dialog.show();
    }

    @Override
    protected void dismissTip() {
        dialog.dismiss();
    }
}
