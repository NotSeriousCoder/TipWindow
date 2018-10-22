package com.bingor.poptipwindow.view.picker.datetimepicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.internal.ThemeEnforcement;
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
import com.bingor.poptipwindow.view.data.DateTimeInfo;
import com.bingor.poptipwindow.view.picker.Picker;
import com.bingor.poptipwindow.view.wheel.NumberWheelView;
import com.bingor.poptipwindow.view.wheel.WheelView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HXB on 2018/10/16.
 */
public class DateTimePickerView extends Picker {
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_JUST_DATE = 1;
    public static final int TYPE_JUST_TIME = 2;
    private DateTimeInfo dateTimeStart, dateTimeEnd, dateTimeInit, dateTimeSelect;

    private View rootView, pageDate, pageTime;
    private TabLayout tab;
    private ViewPager pages;
    private NumberWheelView npYear, npMonth, npDay, npHour, npMinute;
    private List<View> pagesList;
    private DateTimePageAdapter adapter;
    //下划线颜色
    private int tabIndicatorColor;
    private int type = TYPE_NORMAL;


    public DateTimePickerView(Context context) {
        super(context, null);
        tabIndicatorColor = getResources().getColor(R.color.main_color);
        initView();
        initData();
        initListener();
    }

    public DateTimePickerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DateTimePickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, android.support.design.R.styleable.TabLayout);
        tabIndicatorColor = ta.getColor(android.support.design.R.styleable.TabLayout_tabIndicatorColor, getResources().getColor(R.color.main_color));
        ta.recycle();

        initView();
        initData();
        initListener();
    }

    public void init() {
        tab.setSelectedTabIndicatorColor(tabIndicatorColor);
        tab.setTabTextColors(textColorNormal, textColorFocus);
        initWheelView(npYear);
        initWheelView(npMonth);
        initWheelView(npDay);
        initWheelView(npHour);
        initWheelView(npMinute);

        dateTimeSelect.setTimeMillis(dateTimeInit.timeMillis);
        npYear.setItems(dateTimeStart.year, dateTimeEnd.year, dateTimeInit.year);
        npMonth.setItems(1, 12, dateTimeInit.month);
        npDay.setItems(1, 30, dateTimeInit.day);
        npHour.setItems(0, 23, dateTimeInit.hour);
        npMinute.setItems(0, 59, dateTimeInit.minute);
    }

    private void initView() {
//        ViewGroup.LayoutParams lp = getLayoutParams();
//        if (lp == null) {
//            lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        } else {
//            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        }
//        setLayoutParams(lp);
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        Log.d("HXB", "npYear.getMeasuredHeight()==" + npYear.getMeasuredHeight());
//        Log.d("HXB", "pages.getMeasuredHeight()==" + pages.getMeasuredHeight());
        int height = Math.max(npYear.getMeasuredHeight(), npHour.getMeasuredHeight());
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec((height + tab.getMeasuredHeight()), MeasureSpec.getMode(heightMeasureSpec)));
    }

    private void initData() {
        dateTimeStart = new DateTimeInfo(1, 1, 1, 0, 0);
        dateTimeEnd = new DateTimeInfo(9999, 12, 31, 23, 59);
        dateTimeInit = new DateTimeInfo(System.currentTimeMillis());
        dateTimeSelect = new DateTimeInfo(dateTimeInit.timeMillis);

        pagesList = new ArrayList<>();
        pagesList.add(pageDate);
        pagesList.add(pageTime);
        adapter = new DateTimePageAdapter();
        pages.setAdapter(adapter);

        npYear.setItems(dateTimeStart.year, dateTimeEnd.year, dateTimeInit.year);
        npMonth.setItems(dateTimeStart.month, dateTimeEnd.month, dateTimeInit.month);
        npDay.setItems(dateTimeStart.day, dateTimeEnd.day, dateTimeInit.day);
        npHour.setItems(dateTimeStart.hour, dateTimeEnd.hour, dateTimeInit.hour);
        npMinute.setItems(dateTimeStart.minute, dateTimeEnd.minute, dateTimeInit.minute);

    }

    private void initListener() {
        npYear.setOnItemSelectListener(new OnItemSelectListener<Integer>() {
            @Override
            public void onSelected(WheelView wheelView, int index, Integer item) {
                dateTimeSelect.setYear(item);
                if (dateTimeSelect.timeMillis < dateTimeStart.timeMillis || dateTimeSelect.timeMillis > dateTimeEnd.timeMillis) {
                    if (dateTimeSelect.timeMillis < dateTimeStart.timeMillis) {
                        Log.d("HXB", "太小");
                        dateTimeSelect.setTimeMillis(dateTimeStart.timeMillis);
                    } else if (dateTimeSelect.timeMillis > dateTimeEnd.timeMillis) {
                        Log.d("HXB", "太大");
                        dateTimeSelect.setTimeMillis(dateTimeEnd.timeMillis);
                    }
                    npMonth.stop();
                    npDay.stop();
                    npHour.stop();
                    npMinute.stop();

                    Log.d("HXB", "结束日期==" + dateTimeEnd.year + "," + dateTimeEnd.month + "," + dateTimeEnd.day + "," + dateTimeEnd.hour + "," + dateTimeEnd.minute);
                    Log.d("HXB", "重置后的日期==" + dateTimeSelect.year + "," + dateTimeSelect.month + "," + dateTimeSelect.day + "," + dateTimeSelect.hour + "," + dateTimeSelect.minute);
                    npMonth.setSelectedIndex(npMonth.getPositionByItem(dateTimeSelect.month));
                    npDay.setSelectedIndex(npDay.getPositionByItem(dateTimeSelect.day));
                    npHour.setSelectedIndex(npHour.getPositionByItem(dateTimeSelect.hour));
                    npMinute.setSelectedIndex(npMinute.getPositionByItem(dateTimeSelect.minute));
                } else {
                    int rightDays = DateTimeUtil.getDaysOfMonth(dateTimeSelect.year, dateTimeSelect.month);
                    if (npDay.getItemCount() != rightDays) {
                        npDay.stop();
                        npDay.setItems(1, rightDays, dateTimeSelect.day);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        npMonth.setOnItemSelectListener(new OnItemSelectListener<Integer>() {
            @Override
            public void onSelected(WheelView wheelView, int index, Integer item) {
                dateTimeSelect.setMonth(item);
                if (dateTimeSelect.timeMillis < dateTimeStart.timeMillis || dateTimeSelect.timeMillis > dateTimeEnd.timeMillis) {
                    if (dateTimeSelect.timeMillis < dateTimeStart.timeMillis) {
                        dateTimeSelect.setTimeMillis(dateTimeStart.timeMillis);
                    } else if (dateTimeSelect.timeMillis > dateTimeEnd.timeMillis) {
                        dateTimeSelect.setTimeMillis(dateTimeEnd.timeMillis);
                    }
                    npDay.stop();
                    npHour.stop();
                    npMinute.stop();

                    npMonth.setSelectedIndex(npMonth.getPositionByItem(dateTimeSelect.month));
                    npDay.setSelectedIndex(npDay.getPositionByItem(dateTimeSelect.day));
                    npHour.setSelectedIndex(npHour.getPositionByItem(dateTimeSelect.hour));
                    npMinute.setSelectedIndex(npMinute.getPositionByItem(dateTimeSelect.minute));
                } else {
                    int rightDays = DateTimeUtil.getDaysOfMonth(dateTimeSelect.year, dateTimeSelect.month);
                    if (npDay.getItemCount() != rightDays) {
                        npDay.stop();
                        npDay.setItems(1, rightDays, dateTimeSelect.day);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        npDay.setOnItemSelectListener(new OnItemSelectListener<Integer>() {
            @Override
            public void onSelected(WheelView wheelView, int index, Integer item) {
                dateTimeSelect.setDay(item);
                if (dateTimeSelect.timeMillis < dateTimeStart.timeMillis || dateTimeSelect.timeMillis > dateTimeEnd.timeMillis) {
                    if (dateTimeSelect.timeMillis < dateTimeStart.timeMillis) {
                        dateTimeSelect.setTimeMillis(dateTimeStart.timeMillis);
                    } else if (dateTimeSelect.timeMillis > dateTimeEnd.timeMillis) {
                        dateTimeSelect.setTimeMillis(dateTimeEnd.timeMillis);
                    }
                    npHour.stop();
                    npMinute.stop();

                    npDay.setSelectedIndex(npDay.getPositionByItem(dateTimeSelect.day));
                    npHour.setSelectedIndex(npHour.getPositionByItem(dateTimeSelect.hour));
                    npMinute.setSelectedIndex(npMinute.getPositionByItem(dateTimeSelect.minute));
                }
                adapter.notifyDataSetChanged();
            }
        });
        npHour.setOnItemSelectListener(new OnItemSelectListener<Integer>() {
            @Override
            public void onSelected(WheelView wheelView, int index, Integer item) {
                dateTimeSelect.setHour(item);
                if (dateTimeSelect.timeMillis < dateTimeStart.timeMillis || dateTimeSelect.timeMillis > dateTimeEnd.timeMillis) {
                    if (dateTimeSelect.timeMillis < dateTimeStart.timeMillis) {
                        dateTimeSelect.setTimeMillis(dateTimeStart.timeMillis);
                    } else if (dateTimeSelect.timeMillis > dateTimeEnd.timeMillis) {
                        dateTimeSelect.setTimeMillis(dateTimeEnd.timeMillis);
                    }
                    npMinute.stop();

                    npHour.setSelectedIndex(npHour.getPositionByItem(dateTimeSelect.hour));
                    npMinute.setSelectedIndex(npMinute.getPositionByItem(dateTimeSelect.minute));
                }
                adapter.notifyDataSetChanged();
            }
        });
        npMinute.setOnItemSelectListener(new OnItemSelectListener<Integer>() {
            @Override
            public void onSelected(WheelView wheelView, int index, Integer item) {
                dateTimeSelect.setMinute(item);
                if (dateTimeSelect.timeMillis < dateTimeStart.timeMillis || dateTimeSelect.timeMillis > dateTimeEnd.timeMillis) {
                    if (dateTimeSelect.timeMillis < dateTimeStart.timeMillis) {
                        dateTimeSelect.setTimeMillis(dateTimeStart.timeMillis);
                    } else if (dateTimeSelect.timeMillis > dateTimeEnd.timeMillis) {
                        dateTimeSelect.setTimeMillis(dateTimeEnd.timeMillis);
                    }

                    npMinute.setSelectedIndex(npMinute.getPositionByItem(dateTimeSelect.minute));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private class DateTimePageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            switch (type) {
                case TYPE_NORMAL:
                    return pagesList.size();
                case TYPE_JUST_DATE:
                case TYPE_JUST_TIME:
                    return 1;
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = null;
            switch (type) {
                case TYPE_NORMAL:
                    view = pagesList.get(position);
                    break;
                case TYPE_JUST_DATE:
                    view = pagesList.get(0);
                    break;
                case TYPE_JUST_TIME:
                    view = pagesList.get(1);
                    break;
            }
            container.removeView(view);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = "";
            switch (type) {
                case TYPE_NORMAL:
                    if (position == 0) {
                        title = dateTimeSelect.year + "-" + dateTimeSelect.month + "-" + dateTimeSelect.day;
                    } else {
                        title = dateTimeSelect.hour + ":" + dateTimeSelect.minute;
                    }
                    break;
                case TYPE_JUST_DATE:
                    title = dateTimeSelect.year + "-" + dateTimeSelect.month + "-" + dateTimeSelect.day;
                    break;
                case TYPE_JUST_TIME:
                    title = dateTimeSelect.hour + ":" + dateTimeSelect.minute;
                    break;
            }

            return title;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = null;
            switch (type) {
                case TYPE_NORMAL:
                    view = pagesList.get(position);
                    break;
                case TYPE_JUST_DATE:
                    view = pagesList.get(0);
                    break;
                case TYPE_JUST_TIME:
                    view = pagesList.get(1);
                    break;
            }
            container.addView(view);
            return view;
        }
    }

    public DateTimePickerView setDateTimeStart(int year, int month, int day, int hour, int minute) {
        dateTimeStart.setDateTime(year, month, day, hour, minute);
        if (dateTimeStart.timeMillis > dateTimeEnd.timeMillis) {
            dateTimeStart.setTimeMillis(dateTimeEnd.timeMillis);
        }
        return this;
    }

    public DateTimePickerView setDateTimeStart(long dateTimeStartMillis) {
        dateTimeStart.setTimeMillis(dateTimeStartMillis);
        if (dateTimeStart.timeMillis > dateTimeEnd.timeMillis) {
            dateTimeStart.setTimeMillis(dateTimeEnd.timeMillis);
        }
        return this;
    }

    public DateTimePickerView setDateTimeEnd(int year, int month, int day, int hour, int minute) {
        dateTimeEnd.setDateTime(year, month, day, hour, minute);
        if (dateTimeEnd.timeMillis < dateTimeStart.timeMillis) {
            dateTimeEnd.setTimeMillis(dateTimeStart.timeMillis);
        }
        return this;
    }

    public DateTimePickerView setDateTimeEnd(long dateTimeEndMillis) {
        dateTimeEnd.setTimeMillis(dateTimeEndMillis);
        if (dateTimeEnd.timeMillis < dateTimeStart.timeMillis) {
            dateTimeEnd.setTimeMillis(dateTimeStart.timeMillis);
        }
        return this;
    }

    public DateTimePickerView setDateTimeInit(int year, int month, int day, int hour, int minute) {
        dateTimeInit.setDateTime(year, month, day, hour, minute);
        if (dateTimeInit.timeMillis < dateTimeStart.timeMillis) {
            dateTimeInit.setTimeMillis(dateTimeStart.timeMillis);
        } else if (dateTimeInit.timeMillis > dateTimeEnd.timeMillis) {
            dateTimeInit.setTimeMillis(dateTimeEnd.timeMillis);
        }
        return this;
    }

    public DateTimePickerView setDateTimeInit(long dateTimeInitMillis) {
        dateTimeInit.setTimeMillis(dateTimeInitMillis);
        return this;
    }

    public DateTimePickerView setTabIndicatorColor(int tabIndicatorColor) {
        this.tabIndicatorColor = tabIndicatorColor;
        return this;
    }

    @Override
    public DateTimePickerView setTextSize(int textSizePX) {
        super.setTextSize(textSizePX);
        return this;
    }

    @Override
    public DateTimePickerView setTextColorNormal(int textColorNormal) {
        super.setTextColorNormal(textColorNormal);
        return this;
    }

    @Override
    public DateTimePickerView setTextColorFocus(int textColorFocus) {
        super.setTextColorFocus(textColorFocus);
        return this;
    }

    public long getDateTimeSelect() {
        return dateTimeSelect.timeMillis;
    }

    public String getDateTimeSelect(String format) {
        return new SimpleDateFormat(format).format(new Date(dateTimeSelect.timeMillis));
    }

    public int getType() {
        return type;
    }

    /**
     * @param type {@link DateTimePickerView#TYPE_NORMAL}
     *             {@link DateTimePickerView#TYPE_JUST_DATE}
     *             {@link DateTimePickerView#TYPE_JUST_TIME}
     */
    public void setType(int type) {
        this.type = type;
        adapter.notifyDataSetChanged();
    }
}
