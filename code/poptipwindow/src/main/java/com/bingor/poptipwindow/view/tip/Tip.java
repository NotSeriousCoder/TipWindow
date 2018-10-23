package com.bingor.poptipwindow.view.tip;

import android.content.Context;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bingor.poptipwindow.R;
import com.bingor.poptipwindow.adapter.GeneralAdapter;
import com.bingor.poptipwindow.impl.OnAdapterStateChangeListener;
import com.bingor.poptipwindow.impl.OnItemClickListener;
import com.bingor.poptipwindow.impl.OnTipBoxStateChangedListener;
import com.bingor.poptipwindow.impl.OnWindowStateChangedListener;
import com.bingor.poptipwindow.util.UnitConverter;

/**
 * 提示窗的抽象类
 * 1.这里定义了提示窗的类型（列表模式/自定义模式）
 * 2.这里只初始化提示窗的视图，但是未声明提示窗的载体，载体需要在子类中声明
 * Created by HXB on 2018/10/22.
 */
public abstract class Tip {
    //通用
    protected Context context;
    //视图最外层View
    protected View rootView;
    protected int alpha = 50;
    //执行动画的View
    protected View parent;
    protected boolean cancelable = true;
    protected OnTipBoxStateChangedListener onTipBoxStateChangedListener;

    //列表模式
    protected ListView lvList;
    protected GeneralAdapter adapter;
    protected int maxHeight;
    protected OnItemClickListener onItemClickListener;

    //自定义模式
    protected LinearLayout contentParent;
    protected View contentView, okCancelPadding;
    protected TextView tvContent;
    protected TextView tvOK, tvCancel;
    protected String textOK, textCancel, textContent;
    protected OnWindowStateChangedListener onWindowStateChangedListener;
    protected boolean contentNeedPaddingTop = true, wrapContent = false, center = false;


    //////////////////////////////public fun/////////////////////////////////

    /**
     * 初始化方法
     *
     * @throws Exception
     */
    public void init() throws Exception {
        findView();
        initViewContent();
        initTip();
    }


    //////////////////////////////public fun end/////////////////////////////////


    /**
     * 将提示窗的视图先初始化
     * 根据contentView、textContent和adapter会判断初始化哪种类型的视图
     *
     * @throws Exception
     */
    protected void findView() throws Exception {
        if ((contentView != null || !TextUtils.isEmpty(textContent)) && adapter != null) {
            throw new Exception("TipWindow can not be contentView mode and list mode contemporaneously");
        }

        if (contentView != null || !TextUtils.isEmpty(textContent)) {
            rootView = LayoutInflater.from(context).inflate(R.layout.view_tip_ok_cancel, null, false);
            tvContent = rootView.findViewById(R.id.tv_m_view_tip_ok_cancel_p_content);
            parent = rootView.findViewById(R.id.ll_m_view_tip_ok_cancel_p_parent);
            contentParent = rootView.findViewById(R.id.ll_m_view_tip_ok_cancel_p_content);
            tvOK = rootView.findViewById(R.id.tv_m_view_tip_ok_cancel_p_ok);
            tvCancel = rootView.findViewById(R.id.tv_m_view_tip_ok_cancel_p_cancel);
            okCancelPadding = rootView.findViewById(R.id.view_m_view_tip_ok_cancel_p_ok_cancel_padding);

            /**
             * 某些情况下不需要顶部有padding，即可通过contentNeedPaddingTop = false来设置
             * {@link Tip#setContentNeedPaddingTop(boolean)}
             */
            if (!contentNeedPaddingTop) {
                parent.setPadding(parent.getPaddingLeft(), 0, parent.getPaddingRight(), parent.getPaddingBottom());
            }
        } else if (adapter != null) {
            if (maxHeight == 0) {
                maxHeight = UnitConverter.dip2px(context, 30);
            }
            // 用于PopupWindow的View
            rootView = LayoutInflater.from(context).inflate(R.layout.view_tip_list, null, false);
            parent = rootView.findViewById(R.id.fl_m_view_tip_list_p_parent);
            lvList = rootView.findViewById(R.id.lv_m_view_tip_list_p_list);
        }
    }

    /**
     * 将参数设置到视图中
     */
    protected void initViewContent() {
        if (contentView != null || !TextUtils.isEmpty(textContent)) {
            initContent();
        } else if (adapter != null) {
            initList();
        }

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 这里控制是否能点击视图外区域关闭窗体
                 * {@link Tip#setCancelable(boolean)}
                 */
                if (cancelable) {
                    dismiss();
                    if (onWindowStateChangedListener != null) {
                        onWindowStateChangedListener.onOutsideClicked();
                    }
                }
            }
        });
    }

    /**
     * 初始化列表模式下的数据
     */
    protected void initList() {
        adapter.setOnAdapterStateChangeListener(new OnAdapterStateChangeListener() {
            @Override
            public void onItemDeleteClick(int position, Object data) {
                int pos = adapter.getPositionChecked();
                if (pos >= adapter.getCount() - 1 && adapter.getCount() > 1) {
                    pos--;
                    adapter.setPositionChecked(pos);
                }
                adapter.removeItem(position);
                dismiss();
                onItemClickListener.onItemDeleteClick(position, data);
            }
        });
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

    /**
     * 初始化自定义模式下的数据
     */
    protected void initContent() {
        if (!TextUtils.isEmpty(textOK)) {
            tvOK.setText(textOK);
        }
        if (!TextUtils.isEmpty(textCancel)) {
            tvCancel.setText(textCancel);
        }

        if (TextUtils.isEmpty(textOK)) {
            tvOK.setVisibility(View.GONE);
            okCancelPadding.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(textCancel)) {
            tvCancel.setVisibility(View.GONE);
            okCancelPadding.setVisibility(View.GONE);
        }

        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onWindowStateChangedListener != null) {
                    onWindowStateChangedListener.onOKClicked();
                }
                dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onWindowStateChangedListener != null) {
                    onWindowStateChangedListener.onCancelClicked();
                }
                dismiss();
            }
        });

        if (!TextUtils.isEmpty(textContent)) {
            tvContent.setVisibility(View.VISIBLE);
            tvContent.setText(textContent);
        } else {
            contentParent.addView(contentView);
        }

        /**
         * wrapContent决定整个提示框的窗体是否包裹内容
         * {@link Tip#setWrapContent(boolean)}
         */
        if (!wrapContent) {
            okCancelPadding.setVisibility(View.VISIBLE);
        } else {
            okCancelPadding.setVisibility(View.GONE);
        }

        if (center) {
//            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) rootView.getLayoutParams();
//            lp.gravity = Gravity.CENTER;
//            rootView.setLayoutParams(lp);

            LinearLayout linearLayout = (LinearLayout) rootView;
            linearLayout.setGravity(Gravity.CENTER);
        }

        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void show(View anchor) {
        showTip(anchor);
        if (center) {
            parent.startAnimation(AnimationUtils.loadAnimation(context, R.anim.translate_in_long));
        } else {
            parent.startAnimation(AnimationUtils.loadAnimation(context, R.anim.translate_in));
        }
        if (onTipBoxStateChangedListener != null) {
            onTipBoxStateChangedListener.onTipBoxShown();
        }
    }

    public void dismiss() {
        Animation animation = null;
        if (center) {
            animation = AnimationUtils.loadAnimation(context, R.anim.translate_out_long);
        } else {
            animation = AnimationUtils.loadAnimation(context, R.anim.translate_out);
        }
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dismissTip();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        parent.startAnimation(animation);
        if (onTipBoxStateChangedListener != null) {
            onTipBoxStateChangedListener.onTipBoxDismissed();
        }
        context = null;
    }


    protected abstract void initTip();

    protected abstract void showTip(View anchor);

    protected abstract void dismissTip();


    //////////////////////////////getset////////////////////////////////
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public GeneralAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(GeneralAdapter adapter) {
        this.adapter = adapter;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        this.alpha = alpha;
    }

    public View getContentView() {
        return contentView;
    }

    public void setContentView(View contentView) {
        this.contentView = contentView;
    }

    public String getTextOK() {
        return textOK;
    }

    public void setTextOK(String textOK) {
        this.textOK = textOK;
    }

    public String getTextCancel() {
        return textCancel;
    }

    public void setTextCancel(String textCancel) {
        this.textCancel = textCancel;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public OnWindowStateChangedListener getOnWindowStateChangedListener() {
        return onWindowStateChangedListener;
    }

    public void setOnWindowStateChangedListener(OnWindowStateChangedListener onWindowStateChangedListener) {
        this.onWindowStateChangedListener = onWindowStateChangedListener;
    }

    public boolean isCancelable() {
        return cancelable;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public void setContentNeedPaddingTop(boolean contentNeedPaddingTop) {
        this.contentNeedPaddingTop = contentNeedPaddingTop;
    }

    public void setWrapContent(boolean wrapContent) {
        this.wrapContent = wrapContent;
    }

    public void setContentCenter(boolean center) {
        this.center = center;
    }

    public OnTipBoxStateChangedListener getOnTipBoxStateChangedListener() {
        return onTipBoxStateChangedListener;
    }

    public void setOnTipBoxStateChangedListener(OnTipBoxStateChangedListener onTipBoxStateChangedListener) {
        this.onTipBoxStateChangedListener = onTipBoxStateChangedListener;
    }




}
