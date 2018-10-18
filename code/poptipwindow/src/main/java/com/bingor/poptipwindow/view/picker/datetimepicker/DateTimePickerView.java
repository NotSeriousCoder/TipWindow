package com.bingor.poptipwindow.view.picker.datetimepicker;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bingor.poptipwindow.R;
import com.bingor.poptipwindow.util.DateTimeUtil;
import com.bingor.poptipwindow.view.OnItemSelectListener;
import com.bingor.poptipwindow.view.picker.Picker;
import com.bingor.poptipwindow.view.wheel.NumberWheelView;
import com.bingor.poptipwindow.view.wheel.WheelView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HXB on 2018/10/16.
 */
public class DateTimePickerView extends Picker {
    private long dateTimeStart;
    private long dateTimeEnd;
    private long dateTimeInit;
    private long dateTimeSelect;

    private View rootView, pageDate, pageTime;
    private TabLayout tab;
    private ViewPager pages;
    private NumberWheelView npYear, npMonth, npDay, npHour, npMinute;
    private List<View> pagesList;
    private Map<Integer, Integer> needChangeWheels = new HashMap<>();


    public DateTimePickerView(Context context) {
        this(context, null);
    }

    public DateTimePickerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DateTimePickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initData();
        initListener();
    }

    private void initView() {
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

        initWheelView(npYear);
        initWheelView(npMonth);
        initWheelView(npDay);
        initWheelView(npHour);
        initWheelView(npMinute);
    }

    protected void initWheelView(NumberWheelView numberWheelView) {
        LayoutParams lp = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.weight = 1;
        lp.gravity = Gravity.CENTER;
        numberWheelView.setLayoutParams(lp);
        numberWheelView.setTextPadding(0);
        numberWheelView.setUseWeight(true);
        numberWheelView.setTextSizeAutoFit(true);
        //竖向间距
        numberWheelView.setLineSpaceMultiplier(lineSpaceMultiplier);
        //文字大小
        numberWheelView.setTextSizePX(textSize);
        //旁边文字颜色 焦点文字颜色
        numberWheelView.setTextColor(textColorNormal, textColorFocus);
        //分割线
        numberWheelView.setDividerConfig(new WheelView.DividerConfig(dividerWidthRatio).setColor(dividerColor));
        //可见项数量
        numberWheelView.setVisibleItemCount(visibleItemCount);
        //能否循环
        numberWheelView.setCycleable(cycleable);
    }


    private void initData() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1000, 0, 1, 0, 0);
        dateTimeStart = calendar.getTimeInMillis();
        calendar.set(5000, 11, 31, 23, 59);
        dateTimeEnd = calendar.getTimeInMillis();
        dateTimeInit = System.currentTimeMillis();

        pagesList = new ArrayList<>();
        pagesList.add(pageDate);
        pagesList.add(pageTime);
        pages.setAdapter(new DateTimePageAdapter());


        calendar.setTimeInMillis(dateTimeInit);
        npYear.setItems(0, 9999, calendar.get(Calendar.YEAR));
        npMonth.setItems(1, 12, calendar.get(Calendar.MONTH) + 1);
        npDay.setItems(1, 31, calendar.get(Calendar.DAY_OF_MONTH));
        npHour.setItems(0, 23, calendar.get(Calendar.HOUR_OF_DAY));
        npMinute.setItems(0, 59, calendar.get(Calendar.MINUTE));
    }

    private void initListener() {
        npYear.setOnItemSelectListener(new OnNumberchanged());
        npMonth.setOnItemSelectListener(new OnNumberchanged());
        npDay.setOnItemSelectListener(new OnNumberchanged());
        npHour.setOnItemSelectListener(new OnNumberchanged());
        npMinute.setOnItemSelectListener(new OnNumberchanged());
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

    private void initDateTime(long start, long end, long now) {
        dateTimeStart = start;
        dateTimeEnd = end;
        dateTimeInit = now;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateTimeStart);
        int yearStart = calendar.get(Calendar.YEAR);
        int monthStart = calendar.get(Calendar.MONTH) + 1;
        int dayStart = calendar.get(Calendar.DAY_OF_MONTH);
        int hourStart = calendar.get(Calendar.HOUR_OF_DAY);
        int minuteStart = calendar.get(Calendar.MINUTE);

        calendar.setTimeInMillis(dateTimeEnd);
        int yearEnd = calendar.get(Calendar.YEAR);
        int monthEnd = calendar.get(Calendar.MONTH) + 1;
        int dayEnd = calendar.get(Calendar.DAY_OF_MONTH);
        int hourEnd = calendar.get(Calendar.HOUR_OF_DAY);
        int minuteEnd = calendar.get(Calendar.MINUTE);

        calendar.setTimeInMillis(dateTimeSelect);
        int yearNow = calendar.get(Calendar.YEAR);
        int monthNow = calendar.get(Calendar.MONTH) + 1;
        int dayNow = calendar.get(Calendar.DAY_OF_MONTH);
        int hourNow = calendar.get(Calendar.HOUR_OF_DAY);
        int minuteNow = calendar.get(Calendar.MINUTE);

        int rightDays = DateTimeUtil.getDaysOfMonth(yearNow,monthNow);

        npMinute.setItems(0, 59, minuteNow);
        npHour.setItems(0, 23, hourNow);
        npDay.setItems(1, rightDays, dayNow);
        npMonth.setItems(1, 12, monthNow);
        npYear.setItems(yearStart, yearEnd, yearNow);
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

    private class OnNumberchanged implements OnItemSelectListener<Integer> {
        boolean noLinkage = false;

        @Override
        public <T extends WheelView> void onSelected(T wheelView, int index, Integer item) {
            if (noLinkage) {
                noLinkage = false;
                return;
            }
            int group = Integer.parseInt((String) wheelView.getTag());

            if (npYear.isRolling() || npMonth.isRolling() || npDay.isRolling() || npHour.isRolling() || npMinute.isRolling()) {
                return;
            }
//            for (int i = 0; i < getChildCount(); i++) {
//                WheelView child = (WheelView) getChildAt(i);
//                if (child.isRolling()) {
//                    Log.d("HXB", "group==" + group + "  index==" + index);
//                    needChangeWheels.put(group, index);
//                    return;
//                }
//            }

            //该月正确天数
            int rightDays = DateTimeUtil.getDaysOfMonth(npYear.getCurrentItem(), npMonth.getCurrentItem());
            //当前几号
            int currentDay = npDay.getCurrentItem();
            if (currentDay > rightDays) {
                noLinkage = true;
                npDay.setItems(1, rightDays, rightDays);
            }

            Calendar calendar = Calendar.getInstance();
            calendar.set(npYear.getCurrentItem(), npMonth.getCurrentItem() - 1, npDay.getCurrentItem(), npHour.getCurrentItem(), npMinute.getCurrentItem());
            if (calendar.getTimeInMillis() > dateTimeEnd) {
                calendar.setTimeInMillis(dateTimeEnd);
            } else if (calendar.getTimeInMillis() < dateTimeStart) {
                calendar.setTimeInMillis(dateTimeStart);
            } else {

            }

//            if (!needChangeWheels.isEmpty()) {
//                Log.d("HXB", "needChangeWheels==" + needChangeWheels.size());
//                int tempGroup = 10000;
//                for (int key : needChangeWheels.keySet()) {
//                    Log.d("HXB", "key==" + key + "  value==" + needChangeWheels.get(key));
//                    if (needChangeWheels.get(key).intValue() < tempGroup) {
//                        tempGroup = key;
//                    }
//                }
//
//                if (group > tempGroup) {
//                    group = tempGroup;
//                    index = needChangeWheels.get(group);
//                }
//                needChangeWheels.clear();
//            }


        }
    }


    /////////////////////////////////////////////////////////////////////////////

    public long getDateTimeStart() {
        return dateTimeStart;
    }

    public DateTimePickerView setDateTimeStart(int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
        setDateTimeStart(calendar.getTimeInMillis());
        return this;
    }

    public DateTimePickerView setDateTimeStart(long dateTimeStart) {
        initDateTime(dateTimeStart, dateTimeEnd, dateTimeInit);
        return this;
    }

    public long getDateTimeEnd() {
        return dateTimeEnd;
    }

    public DateTimePickerView setDateTimeEnd(int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
        setDateTimeEnd(calendar.getTimeInMillis());
        return this;
    }

    public DateTimePickerView setDateTimeEnd(long dateTimeEnd) {
        initDateTime(dateTimeStart, dateTimeEnd, dateTimeInit);
        return this;
    }

    public long getDateTimeInit() {
        return dateTimeInit;
    }

    public DateTimePickerView setDateTimeInit(int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
        setDateTimeInit(calendar.getTimeInMillis());
        return this;
    }

    public DateTimePickerView setDateTimeInit(long dateTimeInit) {
        initDateTime(dateTimeStart, dateTimeEnd, dateTimeInit);
        return this;
    }

    public long getDateTimeSelect() {
        return dateTimeSelect;
    }

    public DateTimePickerView setDateTimeSelect(int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
        setDateTimeSelect(calendar.getTimeInMillis());
        return this;
    }

    public DateTimePickerView setDateTimeSelect(long dateTimeSelect) {
        this.dateTimeSelect = dateTimeSelect;
        return this;
    }
}
