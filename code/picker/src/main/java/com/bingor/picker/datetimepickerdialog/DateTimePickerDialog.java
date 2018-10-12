package com.bingor.picker.datetimepickerdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.NumberPicker;
import android.widget.TextView;


import com.bingor.picker.CalendarBean;
import com.bingor.picker.R;
import com.bingor.picker.datetimepickerdialog.util.DateTimePickerUtil;
import com.bingor.picker.datetimepickerdialog.util.DateTimeUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 时间选择器
 * Created by HXB on 2017-03-20.
 */

public class DateTimePickerDialog extends Dialog {
    private Context context;

    //日期-时间tab
    private TabLayout tlTab;
    private ViewPager vpVp;
    //确定-取消
    private TextView tvNegative, tvPositive;
    //确定取消的分割线
    private View viewDividerVertical;
    private View rootView, pageDate, pageTime;
    private NumberPicker npYear, npMonth, npDay, npHour, npMinute;


    private boolean cancelable, needDate, needTime;
    private OnClickListener clickListener;


    private CalendarBean dateTimeNow, dateTimeMax, dateTimeMin;
    private int dividerColor;
    private String formatDate, formatTime, txtPositive, txtNegative;

    private DateTimePickerDialog(Context context) {
        super(context);
        this.context = context;
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dateTimeNow = new CalendarBean();
        dateTimeMax = new CalendarBean();
        dateTimeMin = new CalendarBean();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 初始化View
     *
     * @return
     */
    private void initView() {
        rootView = LayoutInflater.from(context).inflate(R.layout.view_date_time_picker_dialog, null);
//        tlTab = (TabLayout) rootView.findViewById(R.id.stl_m_view_date_time_picker_p_tab);
        vpVp = (ViewPager) rootView.findViewById(R.id.vp_m_view_date_time_picker_p_vp);
        tvNegative = (TextView) rootView.findViewById(R.id.tv_m_view_date_time_picker_p_negative);
        viewDividerVertical = rootView.findViewById(R.id.view_m_view_date_time_picker_p_divider_vertical);
        tvPositive = (TextView) rootView.findViewById(R.id.tv_m_view_date_time_picker_p_positive);

//        pageDate = LayoutInflater.from(context).inflate(R.layout.view_date_time_picker_dialog_page_date, null);
//        pageTime = LayoutInflater.from(context).inflate(R.layout.view_date_time_picker_dialog_page_time, null);
//
//        npYear = (NumberPicker) pageDate.findViewById(R.id.np_m_view_date_time_picker_page_date_p_year);
//        npMonth = (NumberPicker) pageDate.findViewById(R.id.np_m_view_date_time_picker_page_date_p_month);
//        npDay = (NumberPicker) pageDate.findViewById(R.id.np_m_view_date_time_picker_page_date_p_day);
//
//        npHour = (NumberPicker) pageTime.findViewById(R.id.np_m_view_date_time_picker_page_time_p_hour);
//        npMinute = (NumberPicker) pageTime.findViewById(R.id.np_m_view_date_time_picker_page_time_p_minute);
//
//        stlTab.setCustomTabView(R.layout.view_date_time_picker_dialog_tab, R.id.tabText);

        setContentView(rootView);
    }

    private void initListener() {
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (clickListener != null) {
                    clickListener.onDismiss();
                }
            }
        });
        npYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                dateTimeNow.setYear(newVal);
//                Log.d("最大日期===" + dateTimeMax.getYear() + " " + dateTimeMax.getMonth() + " " + dateTimeMax.getDay() + " " + dateTimeMax.getHour() + " " + dateTimeMax.getMinute());
//                Log.d("最小时间==="+dateTimeMax.getDateTime().getTime());
//                Log.d("现在时间==="+dateTimeNow.getDateTime().getTime());
                if (dateTimeNow.getDateTime().getTime() < dateTimeMin.getDateTime().getTime() || dateTimeNow.getDateTime().getTime() > dateTimeMax.getDateTime().getTime()) {
                    if (dateTimeNow.getDateTime().getTime() < dateTimeMin.getDateTime().getTime()) {
                        dateTimeNow.setDateTime(dateTimeMin.getDateTime());
                    } else if (dateTimeNow.getDateTime().getTime() > dateTimeMax.getDateTime().getTime()) {
                        dateTimeNow.setDateTime(dateTimeMax.getDateTime());
                    }
                    setNumPickerValue(
                            dateTimeNow.getYear(),
                            dateTimeNow.getMonth(),
                            dateTimeNow.getDay(),
                            dateTimeNow.getHour(),
                            dateTimeNow.getMinute());
                }
                setTitle();
                setPickerMinMax();
            }
        });
        npMonth.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                dateTimeNow.setMonth(newVal);
//                Log.d("最大日期===" + dateTimeMax.getYear() + " " + dateTimeMax.getMonth() + " " + dateTimeMax.getDay() + " " + dateTimeMax.getHour() + " " + dateTimeMax.getMinute());
                // Log.d("最小日期===" + dateTimeMin.getYear() + " " + dateTimeMin.getMonth() + " " + dateTimeMin.getDay() + " " + dateTimeMin.getHour() + " " + dateTimeMin.getMinute());
//                Log.d("当前日期===" + dateTimeNow.getYear() + " " + dateTimeNow.getMonth() + " " + dateTimeNow.getDay() + " " + dateTimeNow.getHour() + " " + dateTimeNow.getMinute());
                if (dateTimeNow.getDateTime().getTime() < dateTimeMin.getDateTime().getTime() || dateTimeNow.getDateTime().getTime() > dateTimeMax.getDateTime().getTime()) {
                    if (dateTimeNow.getDateTime().getTime() < dateTimeMin.getDateTime().getTime()) {
                        dateTimeNow.setDateTime(dateTimeMin.getDateTime());
                    } else if (dateTimeNow.getDateTime().getTime() > dateTimeMax.getDateTime().getTime()) {
                        dateTimeNow.setDateTime(dateTimeMax.getDateTime());
                    }
                    setNumPickerValue(
                            null,
                            dateTimeNow.getMonth(),
                            dateTimeNow.getDay(),
                            dateTimeNow.getHour(),
                            dateTimeNow.getMinute());
                }
                setTitle();
                setPickerMinMax();
            }
        });
        npDay.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                dateTimeNow.setDay(newVal);
                if (dateTimeNow.getDateTime().getTime() < dateTimeMin.getDateTime().getTime() || dateTimeNow.getDateTime().getTime() > dateTimeMax.getDateTime().getTime()) {
                    if (dateTimeNow.getDateTime().getTime() < dateTimeMin.getDateTime().getTime()) {
                        dateTimeNow.setDateTime(dateTimeMin.getDateTime());
                    } else if (dateTimeNow.getDateTime().getTime() > dateTimeMax.getDateTime().getTime()) {
                        dateTimeNow.setDateTime(dateTimeMax.getDateTime());
                    }
                    setNumPickerValue(
                            null,
                            null,
                            dateTimeNow.getDay(),
                            dateTimeNow.getHour(),
                            dateTimeNow.getMinute());
                }
                setTitle();
            }
        });
        npHour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                dateTimeNow.setHour(newVal);
                if (dateTimeNow.getDateTime().getTime() < dateTimeMin.getDateTime().getTime() || dateTimeNow.getDateTime().getTime() > dateTimeMax.getDateTime().getTime()) {
                    if (dateTimeNow.getDateTime().getTime() < dateTimeMin.getDateTime().getTime()) {
                        dateTimeNow.setDateTime(dateTimeMin.getDateTime());
                    } else if (dateTimeNow.getDateTime().getTime() > dateTimeMax.getDateTime().getTime()) {
                        dateTimeNow.setDateTime(dateTimeMax.getDateTime());
                    }
                    setNumPickerValue(
                            null,
                            null,
                            null,
                            dateTimeNow.getHour(),
                            dateTimeNow.getMinute());
                }
                setTitle();
            }
        });
        npMinute.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                dateTimeNow.setMinute(newVal);
                if (dateTimeNow.getDateTime().getTime() < dateTimeMin.getDateTime().getTime() || dateTimeNow.getDateTime().getTime() > dateTimeMax.getDateTime().getTime()) {
                    if (dateTimeNow.getDateTime().getTime() < dateTimeMin.getDateTime().getTime()) {
                        dateTimeNow.setDateTime(dateTimeMin.getDateTime());
                    } else if (dateTimeNow.getDateTime().getTime() > dateTimeMax.getDateTime().getTime()) {
                        dateTimeNow.setDateTime(dateTimeMax.getDateTime());
                    }
                    setNumPickerValue(
                            null,
                            null,
                            null,
                            null,
                            dateTimeNow.getMinute());
                }
                setTitle();
            }
        });

        tvPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    if (!needDate) {
                        clickListener.onOkClick(
                                new SimpleDateFormat(formatTime).format(dateTimeNow.getDateTime().getTime()),
                                dateTimeNow.getDateTime(), 0, 0, 0,
                                dateTimeNow.getHour(),
                                dateTimeNow.getMinute());
                    } else if (!needTime) {
                        clickListener.onOkClick(
                                new SimpleDateFormat(formatDate)
                                        .format(dateTimeNow.getDateTime().getTime()),
                                dateTimeNow.getDateTime(),
                                dateTimeNow.getYear(),
                                dateTimeNow.getMonth(),
                                dateTimeNow.getDay(),
                                0, 0);
                    } else {
                        clickListener.onOkClick(
                                new SimpleDateFormat(formatDate + " " + formatTime)
                                        .format(dateTimeNow.getDateTime().getTime()),
                                dateTimeNow.getDateTime(),
                                dateTimeNow.getYear(),
                                dateTimeNow.getMonth(),
                                dateTimeNow.getDay(),
                                dateTimeNow.getHour(),
                                dateTimeNow.getMinute());
                    }
                    dismiss();
                }
            }
        });

        tvNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onCancelClick();
                }
                dismiss();
            }
        });

    }

    private void initData() {
        if (!needDate && !needTime) {
            needDate = true;
        }
//        tlTab.setSelectedIndicatorColors(dividerColor);
        if (!TextUtils.isEmpty(txtPositive)) {
            tvPositive.setText(txtPositive);
        }
        if (!TextUtils.isEmpty(txtNegative)) {
            tvNegative.setText(txtNegative);
        }
        if (!cancelable) {
            tvNegative.setVisibility(View.GONE);
            viewDividerVertical.setVisibility(View.GONE);
        }

        final List<View> data = new ArrayList();
        if (needDate) {
            data.add(pageDate);
        }
        if (needTime) {
            data.add(pageTime);
        }

        vpVp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(data.get(position));

            }

            @Override
            public int getItemPosition(Object object) {

                return super.getItemPosition(object);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                SimpleDateFormat format = new SimpleDateFormat();
                if (position == 0 && needDate) {
                    format.applyPattern(formatDate);
                    return format.format(dateTimeNow.getDateTime().getTime());
                } else {
                    format.applyPattern(formatTime);
                    return format.format(dateTimeNow.getDateTime().getTime());
                }
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(data.get(position));
                return data.get(position);
            }
        });
//        tlTab.setViewPager(vpVp);


        DateTimePickerUtil.setNumberPickerEditable(npYear, false);
        DateTimePickerUtil.setNumberPickerEditable(npMonth, false);
        DateTimePickerUtil.setNumberPickerEditable(npDay, false);
        DateTimePickerUtil.setNumberPickerEditable(npHour, false);
        DateTimePickerUtil.setNumberPickerEditable(npMinute, false);

        DateTimePickerUtil.setNumberPickerDividerColor(npYear, dividerColor);
        DateTimePickerUtil.setNumberPickerDividerColor(npMonth, dividerColor);
        DateTimePickerUtil.setNumberPickerDividerColor(npDay, dividerColor);
        DateTimePickerUtil.setNumberPickerDividerColor(npHour, dividerColor);
        DateTimePickerUtil.setNumberPickerDividerColor(npMinute, dividerColor);
        initDateTime();
    }

    private void initDateTime() {

        npYear.setMaxValue(9999);
        npMonth.setMaxValue(12);
        npDay.setMaxValue(31);
        npHour.setMaxValue(23);
        npMinute.setMaxValue(59);

        npYear.setMinValue(0);
        npMonth.setMinValue(1);
        npDay.setMinValue(1);
        npHour.setMinValue(0);
        npMinute.setMinValue(0);

        npYear.setValue(dateTimeNow.getYear());
        npMonth.setValue(dateTimeNow.getMonth());
        npDay.setValue(dateTimeNow.getDay());
        npHour.setValue(dateTimeNow.getHour());
        npMinute.setValue(dateTimeNow.getMinute());

        setPickerMinMax();
    }

    private void setPickerMinMax() {
        int days = DateTimeUtil.getDaysOfMonth(dateTimeNow.getYear(), dateTimeNow.getMonth());
        if (npDay.getMaxValue() > days) {
            if (npDay.getValue() > days) {
                npDay.setValue(days);
            }
        }
        npDay.setMaxValue(days);
    }

    /**
     * 设置时间选择器数值
     *
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     */
    private void setNumPickerValue(Integer year, Integer month, Integer day, Integer hour, Integer minute) {
        if (year != null) {
            npYear.setValue(year);
        }
        if (month != null) {
            npMonth.setValue(month);
        }
        if (day != null) {
            npDay.setValue(day);
        }
        if (hour != null) {
            npHour.setValue(hour);
        }
        if (minute != null) {
            npMinute.setValue(minute);
        }
    }

    private void setTitle() {
        if (needDate) {
//            stlTab.setTitle(new SimpleDateFormat(formatDate).format(dateTimeNow.getDateTime().getTime()), 0);
        }
        if (needTime) {
//            stlTab.setTitle(new SimpleDateFormat(formatTime).format(dateTimeNow.getDateTime().getTime()), 1);
        }
    }

    /**
     * 校正初始化Date
     */
    private void correctionDateInit() {
        if (dateTimeNow.getDateTime() == null) {
            return;
        }
        if (dateTimeMin != null && dateTimeMin.getDateTime() != null && dateTimeMin.getDateTime().getTime() > dateTimeNow.getDateTime().getTime()) {
            dateTimeNow = (CalendarBean) dateTimeMin.clone();
        } else if (dateTimeMax != null && dateTimeMax.getDateTime() != null && dateTimeMax.getDateTime().getTime() < dateTimeNow.getDateTime().getTime()) {
            dateTimeNow = (CalendarBean) dateTimeMax.clone();
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public boolean isCancelable() {
        return cancelable;
    }

    @Override
    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public boolean isNeedDate() {
        return needDate;
    }

    public void setNeedDate(boolean needDate) {
        this.needDate = needDate;
    }

    public boolean isNeedTime() {
        return needTime;
    }

    public void setNeedTime(boolean needTime) {
        this.needTime = needTime;
    }

    public OnClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public Date getDateMax() {
        return dateTimeMax == null ? null : dateTimeMax.getDateTime();
    }

    public void setDateMax(Date dateMax) {
        dateTimeMax.setDateTime(dateMax);
        dateTimeMax.setSecond(59);
        if (dateTimeMin != null && dateTimeMin.getDateTime() != null && dateMax.getTime() < dateTimeMin.getDateTime().getTime()) {
            Log.e("HXB", "dateMax should bigger than dateMin");
            this.dateTimeMax = dateTimeMin;
        }
        correctionDateInit();
    }

    public void setDateMax(int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, hour, minute, 59);
        setDateMax(calendar.getTime());
    }

    public Date getDateMin() {
        return dateTimeMin == null ? null : dateTimeMin.getDateTime();
    }

    public void setDateMin(Date dateMin) {
        dateTimeMin.setDateTime(dateMin);
        dateTimeMin.setSecond(0);
        if (dateTimeMax != null && dateTimeMax.getDateTime() != null && dateMin != null && dateTimeMax.getDateTime().getTime() < dateMin.getTime()) {
            Log.e("HXB", "dateMin should smaller than dateMax");
            this.dateTimeMin = dateTimeMax;
        }
        correctionDateInit();
    }

    public void setDateMin(int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, hour, minute, 0);
        setDateMin(calendar.getTime());
    }

    public CalendarBean getDateTimeNow() {
        return dateTimeNow;
    }

    public void setDateTimeNow(Date date) {
        this.dateTimeNow.setDateTime(date);
        if (dateTimeMax.getDateTime() != null && dateTimeMax.getDateTime().getTime() < dateTimeNow.getDateTime().getTime()) {
            Log.e("HXB", "dateInit should smaller than dateMax");
            this.dateTimeNow = (CalendarBean) dateTimeMax.clone();
        }
        if (dateTimeMin.getDateTime() != null && dateTimeMin.getDateTime().getTime() > dateTimeNow.getDateTime().getTime()) {
            Log.e("HXB", "dateInit should bigger than dateMin");
            this.dateTimeNow = (CalendarBean) dateTimeMin.clone();
        }
    }

    public void setDateTimeNow(int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, hour, minute);
        setDateTimeNow(calendar.getTime());
    }

    public int getDividerColor() {
        return dividerColor;
    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
    }

    public String getFormatDate() {
        return formatDate;
    }

    public void setFormatDate(String formatDate) {
        this.formatDate = formatDate;
    }

    public String getFormatTime() {
        return formatTime;
    }

    public void setFormatTime(String formatTime) {
        this.formatTime = formatTime;
    }

    public String getTxtPositive() {
        return txtPositive;
    }

    public void setTxtPositive(String txtPositive) {
        this.txtPositive = txtPositive;
    }

    public String getTxtNegative() {
        return txtNegative;
    }

    public void setTxtNegative(String txtNegative) {
        this.txtNegative = txtNegative;
    }

    public interface OnClickListener {
        public void onOkClick(String dateformat, Date date, int year, int month, int day, int hour, int minute);

        public void onCancelClick();

        public void onDismiss();
    }

    public static class Builder {
        private DateTimePickerDialog dialog;

        public Builder(Context context) {
            dialog = new DateTimePickerDialog(context);
            dialog.setTxtPositive("确定");
            dialog.setTxtNegative("取消");
            dialog.setCancelable(true);
            dialog.setNeedDate(true);
            dialog.setNeedTime(true);
            dialog.setDateMax(9999, 12, 31, 23, 59);
            dialog.setDateMin(1000, 1, 1, 0, 0);
            dialog.setDateTimeNow(new Date(System.currentTimeMillis()));
            dialog.setDividerColor(Color.parseColor("#aa1515"));
            dialog.setFormatDate("yyyy年MM月dd日");
            dialog.setFormatTime("HH:mm");
        }

        public DateTimePickerDialog create() {
            dialog.initView();
            dialog.initListener();
            dialog.initData();
            return dialog;
        }

        public Builder setTxtPositive(String txtPositive) {
            dialog.setTxtPositive(txtPositive);
            return this;
        }

        public Builder setTxtNegative(String txtNegative) {
            dialog.setTxtNegative(txtNegative);
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            dialog.setCancelable(cancelable);
            return this;
        }

        public Builder setClickListener(OnClickListener clickListener) {
            dialog.setClickListener(clickListener);
            return this;
        }

        public Builder setNeedDate(boolean needDate) {
            dialog.setNeedDate(needDate);
            return this;
        }

        public Builder setNeedTime(boolean needTime) {
            dialog.setNeedTime(needTime);
            return this;
        }

        public Builder setDateMax(Date dateMax) {
            dialog.setDateMax(dateMax);
            return this;
        }

        public Builder setDateMax(int year, int month, int day, int hour, int minute) {
            dialog.setDateMax(year, month, day, hour, minute);
            return this;
        }

        public Builder setDateMin(Date dateMin) {
            dialog.setDateMin(dateMin);
            return this;
        }

        public Builder setDateMin(int year, int month, int day, int hour, int minute) {
            dialog.setDateMin(year, month, day, hour, minute);
            return this;
        }

        public Builder setDateTimeNow(Date date) {
            dialog.setDateTimeNow(date);
            return this;
        }

        public Builder setDateTimeNow(int year, int month, int day, int hour, int minute) {
            dialog.setDateTimeNow(year, month, day, hour, minute);
            return this;
        }

        public Builder setDividerColor(int dividerColor) {
            dialog.setDividerColor(dividerColor);
            return this;
        }

        public Builder setFormatDate(String formatDate) {
            if (!TextUtils.isEmpty(formatDate)) {
                dialog.setFormatDate(formatDate);
            }
            return this;
        }

        public Builder setFormatTime(String formatTime) {
            if (!TextUtils.isEmpty(formatTime)) {
                dialog.setFormatTime(formatTime);
            }
            return this;
        }
    }
}
