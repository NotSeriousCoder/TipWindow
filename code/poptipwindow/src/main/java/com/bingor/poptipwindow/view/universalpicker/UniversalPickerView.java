package com.bingor.poptipwindow.view.universalpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.bingor.poptipwindow.R;
import com.bingor.poptipwindow.util.FindDeepUtil;
import com.bingor.poptipwindow.view.wheel.UniversalWheelView;
import com.bingor.poptipwindow.view.wheel.WheelView;
import com.bingor.poptipwindow.view.OnItemSelectListener;
import com.bingor.poptipwindow.view.wheel.WheelItem;

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
    //深度
    private int deep;

    public UniversalPickerView(@NonNull Context context) {
        super(context, null);
        lineSpaceMultiplier = 2;
        textSize = (int) (getContext().getResources().getDisplayMetrics().density * 14);
        textColorNormal = Color.parseColor("#999999");
        textColorFocus = Color.parseColor("#000000");
        dividerWidthRatio = 1.0f;
        dividerColor = Color.parseColor("#000000");
        visibleItemCount = 3;
        //能否循环
        cycleable = true;
        init();
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


    protected UniversalWheelView createABCUniversalPickerView() {
        UniversalWheelView wheelView = new UniversalWheelView(getContext());

        LayoutParams lp = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.weight = 1;
        lp.gravity = Gravity.CENTER;
        wheelView.setLayoutParams(lp);


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
        wheelView.setDividerConfig(new UniversalWheelView.DividerConfig(dividerWidthRatio).setColor(dividerColor));
        //可见项数量
        wheelView.setVisibleItemCount(visibleItemCount);
        //能否循环
        wheelView.setCycleable(cycleable);


        wheelView.setOnItemSelectListener(new OnItemSelectListener<WheelItem>() {
            @Override
            public <View extends WheelView> void onSelected(View wheelView, int index, WheelItem item) {
                Log.d("HXB", "index == " + index);
                //滚轮组别
                int group = (int) wheelView.getTag();
                positions[group] = index;
                //某组当前项
                WheelItem wheelItem;
                //该项的子列表
                List<? extends WheelItem> children = datas;
                if (group < 3) {
                    for (int i = 0; i <= group; i++) {
                        //对应组的位置
                        int tempIndex = positions[i];
                        wheelItem = children.get(tempIndex);
                        children = wheelItem.getChildren();
                    }
                } else {
                    children = null;
                }
                if (children != null) {
                    UniversalWheelView nextABCUniversalPickerView;
                    if (getChildCount() > group + 1) {
                        nextABCUniversalPickerView = (UniversalWheelView) getChildAt(group + 1);
                        nextABCUniversalPickerView.setVisibility(VISIBLE);
                    } else {
                        nextABCUniversalPickerView = createABCUniversalPickerView();
                        addView(nextABCUniversalPickerView);
                    }
                    if (!cycleable || children.size() < 4) {
                        nextABCUniversalPickerView.setCycleable(false);
                    } else {
                        nextABCUniversalPickerView.setCycleable(true);
                    }
                    nextABCUniversalPickerView.setTag(group + 1);
                    nextABCUniversalPickerView.setItems(children);
                    nextABCUniversalPickerView.setSelectedIndex(0);
                } else {
                    for (int i = group + 1; i < getChildCount(); i++) {
                        getChildAt(i).setVisibility(GONE);
                    }
                }
            }
        });
//        wheelView.setItems(data);

        return wheelView;
    }

    protected void initWheel() {
        UniversalWheelView wheelView = createABCUniversalPickerView();
        if (!cycleable || datas.size() < 4) {
            wheelView.setCycleable(false);
        } else {
            wheelView.setCycleable(true);
        }
        wheelView.setTag(0);
        addView(wheelView);
        wheelView.setItems(datas);
        wheelView.setSelectedIndex(0);
    }

    public void setDatas(final List<? extends WheelItem> datas) {
        this.datas = datas;
        new Thread() {
            @Override
            public void run() {
                super.run();
                //查找数据树的深度，深度=滚轮数目
                deep = new FindDeepUtil().find(datas);
                //舍弃超过3的层级，屏幕放不下
                deep = deep > 3 ? 3 : deep;
                new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        initWheel();
                    }
                }.sendEmptyMessage(deep);
            }
        }.run();

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////

    public int getLineSpaceMultiplier() {
        return lineSpaceMultiplier;
    }

    public UniversalPickerView setLineSpaceMultiplier(@IntRange(from = 2, to = 4) int lineSpaceMultiplier) {
        this.lineSpaceMultiplier = lineSpaceMultiplier;
        return this;
    }

    public int getTextSize() {
        return textSize;
    }

    public UniversalPickerView setTextSize(int textSizePX) {
        this.textSize = textSizePX;
        return this;
    }

    public int getTextColorNormal() {
        return textColorNormal;
    }

    public UniversalPickerView setTextColorNormal(@ColorInt int textColorNormal) {
        this.textColorNormal = textColorNormal;
        return this;
    }

    public int getTextColorFocus() {
        return textColorFocus;
    }

    public UniversalPickerView setTextColorFocus(@ColorInt int textColorFocus) {
        this.textColorFocus = textColorFocus;
        return this;
    }

    public float getDividerWidthRatio() {
        return dividerWidthRatio;
    }

    public UniversalPickerView setDividerWidthRatio(@FloatRange(from = 0, to = 1) float dividerWidthRatio) {
        this.dividerWidthRatio = dividerWidthRatio;
        return this;
    }

    public int getDividerColor() {
        return dividerColor;
    }

    public UniversalPickerView setDividerColor(@ColorInt int dividerColor) {
        this.dividerColor = dividerColor;
        return this;
    }

    public int getVisibleItemCount() {
        return visibleItemCount;
    }

    public UniversalPickerView setVisibleItemCount(int visibleItemCount) {
        this.visibleItemCount = visibleItemCount;
        return this;
    }

    public boolean isCycleable() {
        return cycleable;
    }

    public UniversalPickerView setCycleable(boolean cycleable) {
        this.cycleable = cycleable;
        return this;
    }

    public List<? extends WheelItem> getCurrentItems() {
        List<WheelItem> currentItems = new ArrayList<>();
        //某组当前项
        WheelItem wheelItem;
        //该项的子列表
        List<? extends WheelItem> children = datas;
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i).getVisibility() == VISIBLE) {
                wheelItem = children.get(positions[i]);
                currentItems.add(wheelItem);
                children = wheelItem.getChildren();
                if (children == null || children.isEmpty()) {
                    break;
                }
            }
        }
        return currentItems;
    }

    public int[] getCurrentPosition() {
        int count = 0;
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i).getVisibility() == VISIBLE) {
                count++;
            }
        }
        int[] currentPosition = new int[count];
        for (int i = 0; i < count; i++) {
            currentPosition[i] = positions[i];
        }
        return currentPosition;
    }
}
