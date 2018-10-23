package com.bingor.poptipwindow.view.tip;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.bingor.poptipwindow.R;

/**
 * Created by HXB on 2018/9/19.
 */
public class TipWindow extends Tip {
    private PopupWindow window;

    @Override
    protected void initTip() {
        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
        window = new PopupWindow(rootView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        // 设置PopupWindow的背景
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#000000"));
        colorDrawable.setAlpha(alpha);
        window.setBackgroundDrawable(colorDrawable);
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);
        /*window.setFocusable(true);
        window.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(context, "onTouch", Toast.LENGTH_SHORT).show();
                return true;
            }
        });*/
        window.setAnimationStyle(R.style.animNull);
    }

    @Override
    protected void showTip(View anchor) {
        window.showAtLocation(anchor.getRootView(), Gravity.START | Gravity.BOTTOM, 0, 0);
    }

    @Override
    protected void dismissTip() {
        window.dismiss();
    }
}
