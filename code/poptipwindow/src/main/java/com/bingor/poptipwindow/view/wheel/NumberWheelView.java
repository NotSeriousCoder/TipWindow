package com.bingor.poptipwindow.view.wheel;

import android.content.Context;
import android.util.AttributeSet;

import java.util.List;

/**
 * Created by HXB on 2018/10/16.
 */
public class NumberWheelView extends WheelView<Integer> {
    public NumberWheelView(Context context) {
        super(context);
    }

    public NumberWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setItems(List<Integer> items) {
        setItems(items, 0);
    }

    @Override
    public void setItems(List<Integer> items, int defaultPosition) {
        this.items.clear();
        this.items.addAll(items);
        remeasure();
        invalidate();
        setSelectedIndex(defaultPosition);
    }

    public void setItems(int startNum, int endNum, int defaultNum) {
        for (int i = startNum; i <= endNum; i++) {
            items.add(i);
        }
        remeasure();
        invalidate();
        setSelectedIndex(defaultNum - startNum);
    }

    public void setItems(int startNum, int endNum) {
        setItems(startNum, endNum, startNum);
    }


    @Override
    public String getContent(int position) {
        return items.get(position) + "";
    }

    @Override
    public int getPositionByItemContent(String name) {
        return getPositionByItem(Integer.parseInt(name));
    }

    @Override
    public int getPositionByItem(Integer item) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) == item) {
                return i;
            }
        }
        return -1;
    }
}
