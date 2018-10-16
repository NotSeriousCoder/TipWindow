package com.bingor.poptipwindow.view.datetimepicker;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bingor.poptipwindow.R;
import com.bingor.poptipwindow.view.OnItemSelectListener;
import com.bingor.poptipwindow.view.wheel.NumberWheelView;
import com.bingor.poptipwindow.view.wheel.WheelView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by HXB on 2018/10/16.
 */
public class DateTimePickerView extends LinearLayout {
    private long dateTimeStart;
    private long dateTimeEnd;
    private long dateTimeInit;
    private long dateTimeSelect;

    private View rootView, pageDate, pageTime;
    private TabLayout tab;
    private ViewPager pages;
    private NumberWheelView npYear, npMonth, npDay, npHour, npMinute;
    private List<View> pagesList;


    public DateTimePickerView(Context context) {
        this(context, null);
    }

    public DateTimePickerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DateTimePickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
        initData();
        initListener();
    }

    private void initView(AttributeSet attrs) {
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.view_date_time_picker, this);
        tab = rootView.findViewById(R.id.tl_m_view_date_time_picker_p_tab);
        pages = rootView.findViewById(R.id.vp_m_view_date_time_picker_p_pages);
        tab.setupWithViewPager(pages);

        pageDate = LayoutInflater.from(getContext()).inflate(R.layout.view_date_time_picker_page_date, null);
        npYear = pageDate.findViewById(R.id.np_m_view_date_time_picker_page_date_p_year);
        npMonth = pageDate.findViewById(R.id.np_m_view_date_time_picker_page_date_p_month);
        npDay = pageDate.findViewById(R.id.np_m_view_date_time_picker_page_date_p_day);

        pageTime = LayoutInflater.from(getContext()).inflate(R.layout.view_date_time_picker_page_time, null);
        npHour = pageTime.findViewById(R.id.np_m_view_date_time_picker_page_time_p_hour);
        npMinute = pageTime.findViewById(R.id.np_m_view_date_time_picker_page_time_p_minute);
    }

    private void initData() {
        pagesList = new ArrayList<>();
        pagesList.add(pageDate);
        pagesList.add(pageTime);
        pages.setAdapter(new DateTimePageAdapter());


        npYear.setItems(0, 9999);
        npMonth.setItems(1, 12);
        npDay.setItems(1, 31);
        npHour.setItems(0, 23);
        npMinute.setItems(0, 59);
    }

    private void initListener() {
        OnItemSelectListener<Integer> onItemSelectListener = new OnItemSelectListener<Integer>() {
            @Override
            public void onSelected(WheelView wheelView, int index, Integer item) {
                long currentDT = getCurrentDateTimeLong();
                if (currentDT < dateTimeStart) {

                } else if (currentDT > dateTimeEnd) {

                }
            }
        };
        npYear.setOnItemSelectListener(onItemSelectListener);
        npMonth.setOnItemSelectListener(onItemSelectListener);
        npDay.setOnItemSelectListener(onItemSelectListener);
        npHour.setOnItemSelectListener(onItemSelectListener);
        npMinute.setOnItemSelectListener(onItemSelectListener);
    }

    private long getCurrentDateTimeLong() {
        Calendar calendar = Calendar.getInstance();
//        calendar.set((Integer) npYear.getCurrentItem(),
//                (Integer) npMonth.getCurrentItem(),
//                (Integer) npDay.getCurrentItem(),
//                (Integer) npHour.getCurrentItem(),
//                (Integer) npMinute.getCurrentItem());
        return calendar.getTimeInMillis();
    }

//    public final void goStart() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(dateTimeStart);
//        npYear.setSelectedIndex();
//        npMonth.setOnItemSelectListener(onItemSelectListener);
//        npDay.setOnItemSelectListener(onItemSelectListener);
//        npHour.setOnItemSelectListener(onItemSelectListener);
//        npMinute.setOnItemSelectListener(onItemSelectListener);
//    }

    public final void goPrevious() {
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
