package com.bingor.poptipwindow.view.tip;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

/**
 * Created by HXB on 2018/10/22.
 */
public class TipDialog extends Tip {
    private CustomDialog dialog;

    @Override
    protected void initTip() {
        dialog = new CustomDialog(context);
        rootView.setAlpha(alpha);
        dialog.setContentView(rootView);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
    }

    @Override
    protected void showTip(View anchor) {
        dialog.show();
//        window.showAtLocation(anchor.getRootView(), Gravity.START | Gravity.BOTTOM, 0, 0);
    }

    @Override
    protected void dismissTip() {
        dialog.dismiss();
//        window.dismiss();
    }
}
