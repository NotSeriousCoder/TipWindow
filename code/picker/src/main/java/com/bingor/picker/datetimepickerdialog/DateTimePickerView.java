package com.bingor.picker.datetimepickerdialog;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.bingor.picker.R;
import com.bingor.picker.datetimepickerdialog.wheel.WheelView;
import com.bingor.picker.datetimepickerdialog.wheel.WheelView2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HXB on 2018/10/9.
 */
public class DateTimePickerView extends FrameLayout {
    private View rootView, pageDate, pageTime;
    private TabLayout tab;
    private ViewPager pages;
    private WheelView npYear, npMonth, npDay, npHour, npMinute;

    private List<View> pagesList;

    public DateTimePickerView(@NonNull Context context) {
        this(context, null);
    }

    public DateTimePickerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DateTimePickerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.view_date_time_picker, this);
        tab = rootView.findViewById(R.id.tl_m_view_date_time_picker_p_tab);
        pages = rootView.findViewById(R.id.vp_m_view_date_time_picker_p_pages);
        tab.setupWithViewPager(pages);

        pageDate = LayoutInflater.from(getContext()).inflate(R.layout.view_date_time_picker_page_date, null);
        npYear = pageDate.findViewById(R.id.np_m_view_date_time_picker_page_date_p_year);
        npMonth = pageDate.findViewById(R.id.np_m_view_date_time_picker_page_date_p_month);
        npDay = pageDate.findViewById(R.id.np_m_view_date_time_picker_page_date_p_day);

        pagesList = new ArrayList<>();

        pagesList.add(pageDate);

        pages.setAdapter(new DateTimePageAdapter());

    }



    protected WheelView initWheelView(WheelView wheelView) {
        wheelView.setTextPadding(0);
        wheelView.setUseWeight(true);
        wheelView.setTextSizeAutoFit(true);

        //竖向间距
        wheelView.setLineSpaceMultiplier(2);
        //文字大小
        wheelView.setTextSizeDP(14);
        //旁边文字颜色 焦点文字颜色
        wheelView.setTextColor(Color.parseColor("#55ff44"), Color.parseColor("#11a5a5"));
        //分割线
        wheelView.setDividerConfig(new WheelView.DividerConfig(0.6f).setColor(Color.parseColor("#000000")));
        //可见项数量
        wheelView.setVisibleItemCount(13);
        //能否循环
        wheelView.setCycleable(true);


        wheelView.setOnItemSelectListener(new WheelView.OnItemSelectListener() {
            @Override
            public void onSelected(WheelView wheelView, int index) {

            }
        });

        List<String> data = new ArrayList<>();
        data.add("广州");
        data.add("深圳");
        data.add("乌鲁木齐乌鲁木齐");
        data.add("杭州");
        data.add("成都");
        data.add("珠海");
        data.add("中山");
        data.add("揭阳");
        wheelView.setItems(data);

        return wheelView;
    }

    protected void initWheelView2(WheelView2 wheelView) {
        List<String> data = new ArrayList<>();
        data.add("广州");
        data.add("深圳");
        data.add("乌鲁木齐");
        data.add("杭州");
        data.add("成都");
        data.add("珠海");
        data.add("中山");
        data.add("揭阳");

        wheelView.setOffset(2);
        wheelView.setItems(data);
        wheelView.setSeletion(3);
//        wheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
//            @Override
//            public void onSelected(int selectedIndex, String item) {
//                Log.d(TAG, "[Dialog]selectedIndex: " + selectedIndex + ", item: " + item);
//            }
//        });

    }

    private class DateTimePageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return pagesList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(pagesList.get(position));
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            SimpleDateFormat format = new SimpleDateFormat();
//            if (position == 0 && needDate) {
//                format.applyPattern(formatDate);
//                return format.format(dateTimeNow.getDateTime().getTime());
//            } else {
//                format.applyPattern(formatTime);
//                return format.format(dateTimeNow.getDateTime().getTime());
//            }
            return position + "";
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pagesList.get(position));
            return pagesList.get(position);
        }
    }
}
