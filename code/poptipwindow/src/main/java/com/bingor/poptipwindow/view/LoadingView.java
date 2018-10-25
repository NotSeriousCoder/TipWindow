package com.bingor.poptipwindow.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bingor.poptipwindow.R;

/**
 * Created by HXB on 2018/10/23.
 */
public class LoadingView extends FrameLayout {
    private View rootView;
    private ImageView ivLoading;
    private TextView tvMsg;

    private RotateAnimation ra;

    public LoadingView(@NonNull Context context) {
        this(context, null);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        rootView = LayoutInflater.from(context).inflate(R.layout.view_app_wait_dialog, this);
        ivLoading = rootView.findViewById(R.id.iv_m_view_app_wait_dialog_p_loading);
        tvMsg = rootView.findViewById(R.id.tv_m_view_app_wait_dialog_p_msg);
    }

    public void setMsg(String msg) {
        tvMsg.setVisibility(VISIBLE);
        tvMsg.setText(msg);
    }

    public void showAnim() {
        ra = new RotateAnimation(0, 359, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(1000);
        ra.setInterpolator(new LinearInterpolator());
        ra.setRepeatCount(-1);
        ra.setRepeatMode(Animation.RESTART);
        ivLoading.startAnimation(ra);
    }

    public void stopAnim() {
        ivLoading.clearAnimation();
    }

    public void setImageDrawable(Drawable drawable) {
        ivLoading.setImageDrawable(drawable);
    }

    public void setImageResource(@DrawableRes int resId) {
        ivLoading.setImageResource(resId);
    }

    public void setImageBitmap(Bitmap bitmap) {
        ivLoading.setImageBitmap(bitmap);
    }

    public void setTextSize(int textSizePX) {
        tvMsg.setTextSize(textSizePX);
    }
}
