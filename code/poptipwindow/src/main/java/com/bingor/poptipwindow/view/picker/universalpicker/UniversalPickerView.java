package com.bingor.poptipwindow.view.picker.universalpicker;

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
import com.bingor.poptipwindow.view.picker.Picker;
import com.bingor.poptipwindow.view.wheel.UniversalWheelView;
import com.bingor.poptipwindow.view.wheel.WheelView;
import com.bingor.poptipwindow.view.OnItemSelectListener;
import com.bingor.poptipwindow.view.wheel.WheelItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HXB on 2018/10/12.
 */
public class UniversalPickerView extends Picker {
    //数据
    private List<? extends WheelItem> datas;
    //定位
    private int[] positions = {0, 0, 0, 0};
    //深度
    private int deep;
    Map<Integer, Integer> needChangeWheels = new HashMap<>();


    public UniversalPickerView(@NonNull Context context) {
        super(context, null);
    }

    public UniversalPickerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UniversalPickerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected UniversalWheelView createWheelView() {
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
                //Log.d("HXB", "wheelView==" + wheelView.getTag() + "  isRolling==" + wheelView.isRolling());
                //滚轮组别
                int group = (int) wheelView.getTag();

                for (int i = 0; i < getChildCount(); i++) {
                    WheelView child = (WheelView) getChildAt(i);
                    if (child.isRolling()) {
                        Log.d("HXB", "group==" + group + "  index==" + index);
                        needChangeWheels.put(group, index);
                        return;
                    }
                }
                if (!needChangeWheels.isEmpty()) {
                    Log.d("HXB", "needChangeWheels==" + needChangeWheels.size());
                    int tempGroup = 10000;
                    for (int key : needChangeWheels.keySet()) {
                        Log.d("HXB", "key==" + key + "  value==" + needChangeWheels.get(key));
                        if (needChangeWheels.get(key).intValue() < tempGroup) {
                            tempGroup = key;
                        }
                    }

                    if (group > tempGroup) {
                        group = tempGroup;
                        index = needChangeWheels.get(group);
                    }
                    needChangeWheels.clear();
                }


                positions[group] = index;
                //Log.d("HXB", "最终移动的组==" + group);


                //某组当前项
                WheelItem wheelItem;
                //该项的子列表
                List<? extends WheelItem> children = datas;
                if (group < 3) {
                    for (int i = 0; i <= group; i++) {
                        //对应组的位置
                        int tempIndex = positions[i];
                        if (children != null && children.size() > tempIndex) {
                            wheelItem = children.get(tempIndex);
                            children = wheelItem.getChildren();
                        }
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
                        nextABCUniversalPickerView = createWheelView();
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


//                new Handler() {
//                    @Override
//                    public void handleMessage(Message msg) {
//                        super.handleMessage(msg);
//                        Log.d("HXB", "Handler run");
//                        //某组当前项
//                        WheelItem wheelItem;
//                        //该项的子列表
//                        List<? extends WheelItem> children = datas;
//                        if (msg.what < 3) {
//                            for (int i = 0; i <= msg.what; i++) {
//                                //对应组的位置
//                                int tempIndex = positions[i];
//                                if (children != null && children.size() > tempIndex) {
//                                    wheelItem = children.get(tempIndex);
//                                    children = wheelItem.getChildren();
//                                }
//                            }
//                        } else {
//                            children = null;
//                        }
//                        if (children != null) {
//                            UniversalWheelView nextABCUniversalPickerView;
//                            if (getChildCount() > msg.what + 1) {
//                                nextABCUniversalPickerView = (UniversalWheelView) getChildAt(msg.what + 1);
//                                nextABCUniversalPickerView.setVisibility(VISIBLE);
//                            } else {
//                                nextABCUniversalPickerView = createWheelView();
//                                addView(nextABCUniversalPickerView);
//                            }
//                            if (!cycleable || children.size() < 4) {
//                                nextABCUniversalPickerView.setCycleable(false);
//                            } else {
//                                nextABCUniversalPickerView.setCycleable(true);
//                            }
//                            nextABCUniversalPickerView.setTag(msg.what + 1);
//                            nextABCUniversalPickerView.setItems(children);
//                            nextABCUniversalPickerView.setSelectedIndex(0);
//                        } else {
//                            for (int i = msg.what + 1; i < getChildCount(); i++) {
//                                getChildAt(i).setVisibility(GONE);
//                            }
//                        }
//                    }
//                }.sendEmptyMessageDelayed(group, 300);
            }

        });
//        wheelView.setItems(data);

        return wheelView;
    }

    protected void initWheel() {
        UniversalWheelView wheelView = createWheelView();
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
