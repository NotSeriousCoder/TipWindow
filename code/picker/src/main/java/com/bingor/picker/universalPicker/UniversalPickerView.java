package com.bingor.picker.universalPicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bingor.picker.R;
import com.bingor.picker.datetimepickerdialog.DateTimePickerView;
import com.bingor.picker.datetimepickerdialog.util.FindDeepUtil;
import com.bingor.picker.datetimepickerdialog.wheel.WheelItem;
import com.bingor.picker.datetimepickerdialog.wheel.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HXB on 2018/10/12.
 */
public class UniversalPickerView extends LinearLayout {
    //竖向间距(2-4)
    private int lineSpaceMultiplier;
    //文字大小
    private int textSize;
    //旁边文字颜色 焦点文字颜色
    private int textColorNormal, textColorFocus;
    //分割线
    private float dividerWidthRatio;
    private int dividerColor;
    //可见项数量
    private int visibleItemCount;
    //能否循环
    private boolean cycleable;

    //数据
    private List<? extends WheelItem> datas;
    //定位
    private int[] positions = {0, 0, 0, 0};

    public UniversalPickerView(@NonNull Context context) {
        this(context, null);
    }

    public UniversalPickerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UniversalPickerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Picker);
        lineSpaceMultiplier = ta.getInteger(R.styleable.Picker_lineSpaceMultiplier, 2);
        textSize = ta.getDimensionPixelSize(R.styleable.Picker_textSize, (int) (getContext().getResources().getDisplayMetrics().density * 14));
        textColorNormal = ta.getColor(R.styleable.Picker_textColorNormal, Color.parseColor("#999999"));
        textColorFocus = ta.getColor(R.styleable.Picker_textColorFocus, Color.parseColor("#000000"));
        dividerWidthRatio = ta.getFloat(R.styleable.Picker_dividerWidthRatio, 1.0f);
        dividerColor = ta.getColor(R.styleable.Picker_dividerColor, Color.parseColor("#000000"));
        visibleItemCount = ta.getInteger(R.styleable.Picker_visibleItemCount, 3);
        //能否循环
        cycleable = ta.getBoolean(R.styleable.Picker_cycleable, true);

        init();
    }

    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }


    protected WheelView createWheelView() {
        WheelView wheelView = new WheelView(getContext());
        wheelView.setTextPadding(0);
        wheelView.setUseWeight(true);
        wheelView.setTextSizeAutoFit(true);

        //竖向间距
        wheelView.setLineSpaceMultiplier(lineSpaceMultiplier);
        //文字大小
        wheelView.setTextSizePX(textSize);
        //旁边文字颜色 焦点文字颜色
        wheelView.setTextColor(textColorNormal, textColorFocus);
        //分割线
        wheelView.setDividerConfig(new WheelView.DividerConfig(dividerWidthRatio).setColor(dividerColor));
        //可见项数量
        wheelView.setVisibleItemCount(visibleItemCount);
        //能否循环
        wheelView.setCycleable(cycleable);


        wheelView.setOnItemSelectListener(new WheelView.OnItemSelectListener() {
            @Override
            public void onSelected(WheelView wheelView, int index) {
                Log.d("HXB", "index==" + index);
            }
        });
//        wheelView.setItems(data);

        return wheelView;
    }

    protected void initWheel(int num) {
        for (int i = 0; i < num; i++) {
            WheelView wheelView = createWheelView();
            LayoutParams lp = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.weight = 1;
            lp.gravity = Gravity.CENTER;
            wheelView.setLayoutParams(lp);
            wheelView.setTag(i);
            addView(wheelView);
            wheelView.setSelectedIndex(2);
        }
        WheelView wheelView = (WheelView) getChildAt(0);
        wheelView.setItems(datas);
        wheelView.setSelectedIndex(1);
    }

    public void setDatas(final List<? extends WheelItem> datas) {
        this.datas = datas;
        new Thread() {
            @Override
            public void run() {
                super.run();
                //查找数据树的深度，深度=滚轮数目
                int deep = new FindDeepUtil().find(datas);
                //舍弃超过3的层级，屏幕放不下
                deep = deep > 3 ? 3 : deep;
                new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        initWheel(msg.what);
                    }
                }.sendEmptyMessage(deep);
            }
        }.run();

    }
}