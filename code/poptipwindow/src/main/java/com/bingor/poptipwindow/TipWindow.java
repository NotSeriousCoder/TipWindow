package com.bingor.poptipwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bingor.poptipwindow.adapter.GeneralAdapter;
import com.bingor.poptipwindow.impl.OnAdapterStateChangeListener;
import com.bingor.poptipwindow.impl.OnItemClickListener;
import com.bingor.poptipwindow.impl.OnWindowStateChangedListener;
import com.bingor.poptipwindow.util.UnitConverter;

/**
 * Created by HXB on 2018/9/19.
 */
public class TipWindow implements KeyEvent.Callback {
    //通用
    private Context context;
    private View rootView;
    private PopupWindow window;
    private int alpha = 50;
    private View parent;
    private boolean cancelable = true;

    //列表模式
    private ListView lvList;
    private GeneralAdapter adapter;
    private int maxHeight;
    private OnItemClickListener onItemClickListener;

    //普通模式
    private LinearLayout contentParent;
    private View contentView, okCancelPadding;
    private TextView tvContent;
    private TextView tvOK, tvCancel;
    private String textOK, textCancel, textContent;
    private OnWindowStateChangedListener onWindowStateChangedListener;
    private boolean contentNeedPaddingTop = true;

    public TipWindow(final Activity activity) {
//        Window w = activity.getWindow();
//        w.setCallback(new Window.Callback() {
//            @Override
//            public boolean dispatchKeyEvent(KeyEvent event) {
//                Log.d("HXB", "dispatchKeyEvent");
////                Toast.makeText(activity, "dispatchKeyEvent==" + event.getAction(), Toast.LENGTH_SHORT).show();
//                return true;
//            }
//
//            @Override
//            public boolean dispatchKeyShortcutEvent(KeyEvent event) {
//                Log.d("HXB", "dispatchKeyShortcutEvent");
//                return true;
//            }
//
//            @Override
//            public boolean dispatchTouchEvent(MotionEvent event) {
//                Log.d("HXB", "dispatchTouchEvent");
//                return true;
//            }
//
//            @Override
//            public boolean dispatchTrackballEvent(MotionEvent event) {
//                Log.d("HXB", "dispatchTrackballEvent");
//                return true;
//            }
//
//            @Override
//            public boolean dispatchGenericMotionEvent(MotionEvent event) {
//                Log.d("HXB", "dispatchGenericMotionEvent");
//                return true;
//            }
//
//            @Override
//            public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
//                Log.d("HXB", "dispatchPopulateAccessibilityEvent");
//                return true;
//            }
//
//            @Override
//            public View onCreatePanelView(int featureId) {
//                return null;
//            }
//
//            @Override
//            public boolean onCreatePanelMenu(int featureId, Menu menu) {
//                Log.d("HXB", "onCreatePanelMenu");
//                return true;
//            }
//
//            @Override
//            public boolean onPreparePanel(int featureId, View view, Menu menu) {
//                Log.d("HXB", "onPreparePanel");
//                return true;
//            }
//
//            @Override
//            public boolean onMenuOpened(int featureId, Menu menu) {
//                Log.d("HXB", "onMenuOpened");
//                return true;
//            }
//
//            @Override
//            public boolean onMenuItemSelected(int featureId, MenuItem item) {
//                Log.d("HXB", "onMenuItemSelected");
//                return true;
//            }
//
//            @Override
//            public void onWindowAttributesChanged(WindowManager.LayoutParams attrs) {
//
//            }
//
//            @Override
//            public void onContentChanged() {
//
//            }
//
//            @Override
//            public void onWindowFocusChanged(boolean hasFocus) {
//
//            }
//
//            @Override
//            public void onAttachedToWindow() {
//
//            }
//
//            @Override
//            public void onDetachedFromWindow() {
//
//            }
//
//            @Override
//            public void onPanelClosed(int featureId, Menu menu) {
//
//            }
//
//            @Override
//            public boolean onSearchRequested() {
//                Log.d("HXB", "onSearchRequested");
//                return true;
//            }
//
//            @Override
//            public boolean onSearchRequested(SearchEvent searchEvent) {
//                Log.d("HXB", "onSearchRequested");
//                return true;
//            }
//
//            @Override
//            public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
//                return null;
//            }
//
//            @Override
//            public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int type) {
//                return null;
//            }
//
//            @Override
//            public void onActionModeStarted(ActionMode mode) {
//
//            }
//
//            @Override
//            public void onActionModeFinished(ActionMode mode) {
//
//            }
//        });
    }

    public void init() throws Exception {
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

            if (!contentNeedPaddingTop) {
                parent.setPadding(parent.getPaddingLeft(), 0, parent.getPaddingRight(), parent.getPaddingBottom());
            }
            initContent();
        } else if (adapter != null) {
            if (maxHeight == 0) {
                maxHeight = UnitConverter.dip2px(context, 30);
            }
            // 用于PopupWindow的View
            rootView = LayoutInflater.from(context).inflate(R.layout.view_tip_list, null, false);
            parent = rootView.findViewById(R.id.fl_m_view_tip_list_p_parent);
            lvList = rootView.findViewById(R.id.lv_m_view_tip_list_p_list);
            initList();
        }


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
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancelable) {
                    dismiss();
                    if (onWindowStateChangedListener != null) {
                        onWindowStateChangedListener.onOutsideClicked();
                    }
                }
            }
        });
    }

    private void initList() {
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

    private void initContent() {
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

        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void show(View anchor) {
//        window.showAsDropDown(ViewUtil.findRootView(anchor), 0, 0);
//        window.showAsDropDown(anchor, 0, 0);
        window.showAtLocation(anchor.getRootView(), Gravity.START | Gravity.BOTTOM, 0, 0);
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

    public void setAlpha(int alpha) {
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(context, "KEYCODE_BACK", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.KEYCODE_BACK) {

            return true;
        }
        return false;
    }

    @Override
    public boolean onKeyMultiple(int keyCode, int count, KeyEvent event) {
        return false;
    }
    //////////////////////////////getset////////////////////////////////

}
