package com.bingor.poptipwindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.bingor.poptipwindow.adapter.GeneralAdapter;
import com.bingor.poptipwindow.impl.OnItemClickListener;
import com.bingor.poptipwindow.util.UnitConverter;

/**
 * Created by HXB on 2018/9/19.
 */
public class ListTipWindow {
    private Context context;
    private View rootView;
    private PopupWindow window;

    private View parent;
    private ListView lvList;
    private GeneralAdapter adapter;

    private int maxHeight, alpha = 50;

    private OnItemClickListener onItemClickListener;

    private void init() {
        if (maxHeight == 0) {
            maxHeight = UnitConverter.dip2px(context, 30);
        }
        // 用于PopupWindow的View
        rootView = LayoutInflater.from(context).inflate(R.layout.view_tip_list, null, false);
        parent = rootView.findViewById(R.id.fl_m_view_tip_list_p_parent);
        lvList = rootView.findViewById(R.id.lv_m_view_tip_list_p_list);
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
        window.setAnimationStyle(R.style.animNull);
        // 显示PopupWindow，其中：
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
//        window.showAsDropDown(anchor.getRootView(), 0, 0);
        // 或者也可以调用此方法显示PopupWindow，其中：
        // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
        // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
        // window.showAtLocation(parent, gravity, x, y);

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        initList();
    }

    private void initList() {
        lvList.setAdapter(adapter);
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setPositionChecked(position);
                dismiss();
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(parent, view, position, id, adapter.getDatas().get(position));
                }
            }
        });

        if (lvList.getMeasuredHeight() > maxHeight) {
            ViewGroup.LayoutParams lp = parent.getLayoutParams();
            lp.height = maxHeight;
            parent.setLayoutParams(lp);
        }
    }

    public void show(View anchor) {
        window.showAsDropDown(anchor.getRootView(), 0, 0);
        parent.startAnimation(AnimationUtils.loadAnimation(context, R.anim.translate_in));
    }

    public void dismiss() {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.translate_out);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                window.dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        parent.startAnimation(animation);
    }

    public static class Builder {
        ListTipWindow listTipWindow;

        public Builder(Context context) {
            listTipWindow = new ListTipWindow();
            listTipWindow.context = context;
        }

        public Builder setAdapter(GeneralAdapter adapter) {
            listTipWindow.adapter = adapter;
            return this;
        }

        public <T> Builder setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
            listTipWindow.onItemClickListener = onItemClickListener;
            return this;
        }

        public Builder setMaxHeight(int maxHeight) {
            listTipWindow.maxHeight = maxHeight;
            return this;
        }

        /**
         * 设置透明度
         *
         * @param alpha 0~1
         * @return
         */
        public Builder setAlpha(float alpha) {
            if (alpha >= 1.0f) {
                listTipWindow.alpha = 255;
            } else if (alpha <= 0f) {
                listTipWindow.alpha = 0;
            } else {
                listTipWindow.alpha = (int) (255 * alpha);
            }
            return this;
        }

        public ListTipWindow create() {
            listTipWindow.init();
            return listTipWindow;
        }
    }
}
