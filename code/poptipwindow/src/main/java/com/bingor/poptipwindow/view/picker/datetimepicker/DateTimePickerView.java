package com.bingor.poptipwindow.view.picker.datetimepicker;

import android.content.Context;
import android.os.Build;
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
import com.bingor.poptipwindow.view.data.DateTimeInfo;
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
    private DateTimeInfo dateTimeStart, dateTimeEnd, dateTimeInit, dateTimeSelect;

    private View rootView, pageDate, pageTime;
    private TabLayout tab;
    private ViewPager pages;
    private NumberWheelView npYear, npMonth, npDay, npHour, npMinute;
    private List<View> pagesList;
    private DateTimePageAdapter adapter;

    public DateTimePickerView(Context context) {
        this(context, null);
    }

    public DateTimePickerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DateTimePickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        for (int i = 0, size = attrs.getAttributeCount(); i < size; i++) {
            String name = attrs.getAttributeName(i);
            String value = attrs.getAttributeValue(i);
            if ("tabIndicatorColor".equals(name) || "tabSelectedTextColor".equals(name) || "tabTextColor".equals(name)) {
                tab.setSelectedTabIndicatorColor();
                if (value.startsWith("@")) {
                    int bgResId = Integer.parseInt(value.substring(1));
                    setBackgroundResource(bgResId);
//                    rootView.findViewById(R.id.ll_main).setBackgroundResource(bgResId);
                } else if (value.startsWith("#")) {
                    String alphaStr = null;
                    String colorStr = null;
                    if (value.length() == 9) {
                        alphaStr = value.substring(1, 3);
                        colorStr = value.substring(3);
                    } else if (value.length() <= 7) {
                        colorStr = value.substring(1);
                    }
                    if (colorStr != null) {
                        try {
                            int color = Integer.parseInt(colorStr, 16);
                            setBackgroundColor(color);
//                            rootView.findViewById(R.id.ll_main).setBackgroundColor(color);
                        } catch (NumberFormatException e) {

                        }
                    }

                    if (alphaStr != null && Build.VERSION.SDK_INT >= 11) {
                        try {
                            int alpha = Integer.parseInt(alphaStr, 16);
                            setAlpha(alpha);
//                            rootView.findViewById(R.id.ll_main).setAlpha(alpha);
                        } catch (NumberFormatException e) {

                        }
                    }

                }
            }
        }

//        app:tabIndicatorColor="#ffff11"
//        app:tabSelectedTextColor="#3a9155"
//        app:tabTextColor="#000000"


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
            String title;
            if (position == 0) {
                title = dateTimeSelect.year + "-" + dateTimeSelect.month + "-" + dateTimeSelect.day;
            } else {
                title = dateTimeSelect.hour + ":" + dateTimeSelect.minute;
            }
            return title;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pagesList.get(position));
            return pagesList.get(position);
        }
    }

    public DateTimePickerView setDateTimeStart(int year, int month, int day, int hour, int minute) {
        dateTimeStart.setDateTime(year, month, day, hour, minute);
        return this;
    }

    public DateTimePickerView setDateTimeStart(int dateTimeStartMillis) {
        dateTimeStart.setTimeMillis(dateTimeStartMillis);
        return this;
    }

    public DateTimePickerView setDateTimeEnd(int year, int month, int day, int hour, int minute) {
        dateTimeEnd.setDateTime(year, month, day, hour, minute);
        return this;
    }

    public DateTimePickerView setDateTimeEnd(long dateTimeEndMillis) {
        dateTimeEnd.setTimeMillis(dateTimeEndMillis);
        return this;
    }

    public DateTimePickerView setDateTimeInit(int year, int month, int day, int hour, int minute) {
        dateTimeInit.setDateTime(year, month, day, hour, minute);
        return this;
    }

    public DateTimePickerView setDateTimeInit(long dateTimeInitMillis) {
        dateTimeInit.setTimeMillis(dateTimeInitMillis);
        return this;
    }

    public void initDateTime() {
        dateTimeSelect.setTimeMillis(dateTimeInit.timeMillis);
        npYear.setItems(dateTimeStart.year, dateTimeEnd.year, dateTimeInit.year);
        npMonth.setItems(1, 12, dateTimeInit.month);
        npDay.setItems(1, 30, dateTimeInit.day);
        npHour.setItems(0, 23, dateTimeInit.hour);
        npMinute.setItems(0, 59, dateTimeInit.minute);
    }

}
