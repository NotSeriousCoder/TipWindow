package com.bingor.poptipwindow.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bingor.poptipwindow.R;
import com.bingor.poptipwindow.util.ShapeSelectorUtil;

import java.util.List;

/**
 * 简易的列表适配器
 * {@link com.bingor.poptipwindow.builder.ListTipWindowBuilder#setAdapter(GeneralAdapter)}
 * Created by HXB on 2018/9/20.
 */
public class SimpleListAdapter extends GeneralAdapter<String> {
    //选中标签的颜色
    private int color;
    //是否需要删除按钮
    private boolean needDelete;
    //是否需要选中标签
    private boolean needTag;

    public SimpleListAdapter(Context context, List<String> datas, @ColorInt int color) {
        super(context, datas);
        needDelete = true;
        needTag = true;
        this.color = color;
    }

    public SimpleListAdapter(Context context, @ColorInt int color) {
        super(context);
        needDelete = true;
        needTag = true;
        this.color = color;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.view_list_item, null);

        FrameLayout flCheck = convertView.findViewById(R.id.fl_m_view_list_item_p_check);
        View viewCheck = convertView.findViewById(R.id.view_m_view_list_item_p_check);
        TextView tvContent = convertView.findViewById(R.id.tv_m_view_list_item_p_content);
        ImageView ivClose = convertView.findViewById(R.id.iv_m_view_list_item_p_close);

        if (needTag) {
            flCheck.setVisibility(View.VISIBLE);
            if (position == positionChecked) {
                flCheck.setBackground(ShapeSelectorUtil.getDrawable(null, new ShapeSelectorUtil.CornersWrapper().setRadius(200), color, 0));
//                        viewCheck.setVisibility(View.GONE);
            } else {
                flCheck.setBackground(ShapeSelectorUtil.getDrawable(null, new ShapeSelectorUtil.CornersWrapper().setRadius(200), Color.parseColor("#cccccc"), 0));
//                        viewCheck.setVisibility(View.VISIBLE);
            }
        } else {
            flCheck.setVisibility(View.GONE);
        }

        tvContent.setText(datas.get(position));

        if (needDelete) {
            ivClose.setVisibility(View.VISIBLE);
            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onAdapterStateChangeListener != null) {
                        onAdapterStateChangeListener.onItemDeleteClick(position, datas.get(position));
                    }
                }
            });
        } else {
            ivClose.setVisibility(View.GONE);
        }
        return convertView;
    }

    public boolean isNeedDelete() {
        return needDelete;
    }

    public void setNeedDelete(boolean needDelete) {
        this.needDelete = needDelete;
    }

    public boolean isNeedTag() {
        return needTag;
    }

    public void setNeedTag(boolean needTag) {
        this.needTag = needTag;
    }
}
