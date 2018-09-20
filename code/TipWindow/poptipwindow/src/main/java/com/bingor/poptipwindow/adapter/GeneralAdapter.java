package com.bingor.poptipwindow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HXB on 2017-09-03.
 */

public abstract class GeneralAdapter<Data> extends BaseAdapter {
    protected Context context;
    protected List<Data> datas;
    protected LayoutInflater inflater;
    protected int positionChecked;

    public GeneralAdapter(Context context, List<Data> datas) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        if (datas != null) {
            this.datas = datas;
        } else {
            this.datas = new ArrayList<>();
        }
        positionChecked = -1;
    }

    public GeneralAdapter(Context context) {
        this(context, new ArrayList<Data>());
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<Data> getDatas() {
        return datas;
    }

    public void setDatas(List<Data> datas) {
        this.datas.clear();
        if (datas == null) {
            return;
        }
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public void addDatas(List<Data> datas, int position) {
        if (datas == null) {
            return;
        }
        if (position < 0) {
            this.datas.addAll(0, datas);
        } else if (position > datas.size()) {
            this.datas.addAll(datas);
        } else {
            this.datas.addAll(position, datas);
        }
        notifyDataSetChanged();
    }

    public void addDatas(List<Data> datas) {
        if (datas == null) {
            return;
        }
        addDatas(datas, this.datas.size());
    }

    public void addItem(Data data, int position) {
        if (data == null) {
            return;
        }
        if (position < 0) {
            datas.add(0, data);
        } else if (position > datas.size()) {
            datas.add(data);
        } else {
            datas.add(position, data);
        }
        notifyDataSetChanged();
    }

    public void addItem(Data data) {
        if (data == null) {
            return;
        }
        addItem(data, datas.size());
    }

    public void removeItem(int position) {
        if (position < 0 || position >= datas.size()) {
            return;
        } else {
            datas.remove(position);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        datas.clear();
        notifyDataSetChanged();
    }

    public int getPositionChecked() {
        return positionChecked;
    }

    public void setPositionChecked(int positionChecked) {
        this.positionChecked = positionChecked;
    }
}
