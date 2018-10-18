package com.bingor.poptipwindow.view.wheel;

import android.content.Context;
import android.util.AttributeSet;

import com.bingor.poptipwindow.view.wheel.WheelView;
import com.bingor.poptipwindow.view.wheel.WheelItem;

import java.util.List;

/**
 * Created by HXB on 2018/10/16.
 */
public class UniversalWheelView<T extends WheelItem> extends WheelView<T> {
    public UniversalWheelView(Context context) {
        super(context);
    }

    public UniversalWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setItems(List<T> items, int defaultPosition) {
        this.items.clear();
        this.items.addAll(items);
        remeasure();
        invalidate();
        setSelectedIndex(defaultPosition);
    }

    @Override
    public void setItems(List<T> items) {
        setItems(items, 0);
    }

    @Override
    public String getContent(int position) {
        return items.get(position).getName();
    }

    @Override
    public int getPositionByItemContent(String name) {
        for (int i = 0; i < items.size(); i++) {
            WheelItem item = items.get(i);
            if (item.getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public T getCurrentItem() {
        return getItem(selectedIndex);
    }

    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getPositionByItem(WheelItem item) {
        return getPositionByItemContent(item.getName());
    }
}
