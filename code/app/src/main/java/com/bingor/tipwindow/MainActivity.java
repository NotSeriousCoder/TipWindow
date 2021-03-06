package com.bingor.tipwindow;

import android.app.Dialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bingor.numbertipview.NumTipView;
import com.bingor.poptipwindow.adapter.SimpleListAdapter;
import com.bingor.poptipwindow.builder.CustomTipWindowBuilder;
import com.bingor.poptipwindow.builder.DateTimePickerWindowBuilder;
import com.bingor.poptipwindow.builder.ListTipWindowBuilder;
import com.bingor.poptipwindow.builder.TipWindowBuilder;
import com.bingor.poptipwindow.builder.UniversalPickerWindowBuilder;
import com.bingor.poptipwindow.builder.WaitingWindowBuilder;
import com.bingor.poptipwindow.impl.OnDataSelectedListener;
import com.bingor.poptipwindow.impl.OnDataTimeDialogListener;
import com.bingor.poptipwindow.impl.OnItemClickListener;
import com.bingor.poptipwindow.impl.OnWindowStateChangedListener;
import com.bingor.poptipwindow.util.UnitConverter;
import com.bingor.poptipwindow.view.picker.datetimepicker.DateTimePickerView;
import com.bingor.poptipwindow.view.tip.CustomDialog;
import com.bingor.poptipwindow.view.tip.Tip;
import com.bingor.poptipwindow.view.wheel.NumberWheelView;
import com.bingor.poptipwindow.view.wheel.WheelItem;
import com.bingor.poptipwindow.view.wheel.WheelView;
import com.bingor.tipwindow.view.SoundVolume;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SimpleListAdapter adapter;
    private Tip tipWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customText();
        customView();
        dateTimePicker();
        list();
        dataPicker();
        waiting();
    }


    private void customText() {
        findViewById(R.id.bt_custom_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //这里有TipWindowBuilder.TIP_TYPE_WINDOW和TipWindowBuilder.TIP_TYPE_DIALOG两种模式
                //分别用PopupWindow和Dialog实现窗口
                new CustomTipWindowBuilder(MainActivity.this, TipWindowBuilder.TIP_TYPE_DIALOG)
                        //不设置就没有确定按钮
                        .setOK("好的")
                        //不设置就没有取消按钮
                        .setCancel("不要")
                        //窗口是否包裹内容
                        .setWrapContent(false)
                        //文字内容
                        .setTextContent("确定要删除这个文件吗~~")
                        //空白处不透明度1=全黑 0=透明
                        .setAlpha(1f)
                        //是否能点击空白处/返回键关闭窗口（WINDOW模式下，无法拦截返回键QAQ）
                        .setCancelable(false)
                        //回调
                        .setOnWindowStateChangedListener(new OnWindowStateChangedListener() {
                            @Override
                            public void onOKClicked() {
                                Toast.makeText(getBaseContext(), "好吧", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelClicked() {
                                Toast.makeText(getBaseContext(), "取消", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onOutsideClicked() {
                                Toast.makeText(getBaseContext(), "窗户消失", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create()
                        //DIALOG模式下，传null就行，WINDOW模式下，传界面里面的任意一个View就好
                        .show(findViewById(R.id.ll_main));
            }
        });
    }

    private void customView() {
        findViewById(R.id.bt_custom_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundVolume soundVolume = new SoundVolume();
                soundVolume.setListener(new SoundVolume.OnEvent() {
                    @Override
                    public void onStartPressed() {
                        Log.d("HXB", "onStartPressed");
                    }

                    @Override
                    public void onStop(int timeSecond) {
                        Log.d("HXB", "onStop");

                    }

                    @Override
                    public void onCancel() {
                        Log.d("HXB", "onCancel");

                    }
                });
                new CustomTipWindowBuilder(MainActivity.this, TipWindowBuilder.TIP_TYPE_DIALOG)
                        .setContentView(soundVolume.getSoundVolumeView(MainActivity.this))
//                        .setTextContent("确定要删除这个文件吗~~")
                        .setAlpha(0.3f)
                        .setOnWindowStateChangedListener(new OnWindowStateChangedListener() {
                            @Override
                            public void onOKClicked() {
                                Toast.makeText(getBaseContext(), "好吧", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelClicked() {
                                Toast.makeText(getBaseContext(), "取消", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onOutsideClicked() {
                                Toast.makeText(getBaseContext(), "窗户消失", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create()
                        .show(findViewById(R.id.ll_main));
//                        .show(getWindow().getDecorView());
            }
        });
    }

    private void dateTimePicker() {
        findViewById(R.id.bt_date_time_picker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DateTimePickerWindowBuilder(MainActivity.this, TipWindowBuilder.TIP_TYPE_WINDOW)
                        //设置空白处不透明度（0=完全透明 1=完全不透明）
                        .setAlpha(0.2f)
                        //设置是否能点击空白处取消（TIP_TYPE_WINDOW模式下对返回键无效，待改进）
                        .setCancelable(false)
                        .setOK("选定")
                        .setCancel("取消")
                        //日期选择器回调
                        .setOnDataTimeDialogListener(new OnDataTimeDialogListener() {
                            @Override
                            public void onOKClicked(@NotNull String dateTimeFormat, long dateTime) {
                                Toast.makeText(getBaseContext(), dateTimeFormat, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelClicked() {

                            }

                            @Override
                            public void onOutsideClicked() {

                            }
                        })
                        //滚轮竖向间距（2-4），不设置默认为2，数字越大，间距越大
                        .setLineSpaceMultiplier(2)
                        //设置字号（px），默认14sp
                        .setTextSize(UnitConverter.sp2px(getBaseContext(), 18))
                        //滚轮正常文字的颜色（同时也是顶部Tab的非选中文字颜色）
                        .setTextColorNormal(getResources().getColor(R.color.colorAccent))
                        //滚轮聚焦文字的颜色（同时也是顶部Tab的选中文字颜色）
                        .setTextColorFocus(Color.parseColor("#0069E6"))
                        //设置滚轮分割线颜色（同时也是顶部Tab的选中颜色）
                        .setDividerColor(Color.parseColor("#1E802A"))
                        //设置滚轮分割线宽度比例
                        .setDividerWidthRatio(0.7f)
                        //设置滚轮可见项数量
                        .setVisibleItemCount(7)
                        //设置是否可以循环滚动，默认可以
                        .setCycleable(true)
                        //起始时间，不设置默认1-1-1 0:0
                        .setDateTimeStart(1995, 3, 20, 0, 0)
                        //终止时间，不设置默认9999-12-31 23:59
                        .setDateTimeEnd(2222, 8, 8, 23, 59)
                        //默认显示时间，不设置默认当前时间
                        .setDateTimeInit(2018, 10, 22, 10, 29)
                        //设置显示模式（日期-时间/仅日期/仅时间）
                        /**
                         * {@link DateTimePickerView#TYPE_NORMAL}
                         * {@link DateTimePickerView#TYPE_JUST_DATE}
                         * {@link DateTimePickerView#TYPE_JUST_TIME}
                         */
                        .setType(DateTimePickerView.TYPE_JUST_TIME)
                        .create()
                        .show(findViewById(R.id.bt_data_picker));

            }
        });
    }

    private void list() {
        findViewById(R.id.bt_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tipWindow == null) {
                    if (adapter == null) {
                        List<String> data = new ArrayList<>();
                        data.add("13710267845");
                        data.add("15025515656");
                        data.add("13325161561");
                        data.add("18945851215");
                        data.add("18154545455");
                        data.add("16545145158");
                        data.add("15454661456");
                        data.add("18714545458");
                        adapter = new SimpleListAdapter(MainActivity.this, data, Color.parseColor("#1a56f1"));
                        //是否需要删除按钮
                        adapter.setNeedDelete(true);
                        //是否需要选中标签
                        adapter.setNeedTag(true);
                    }

                    tipWindow = new ListTipWindowBuilder(MainActivity.this, TipWindowBuilder.TIP_TYPE_DIALOG)
                            //设置空白处不透明度（0=完全透明 1=完全不透明）
                            .setAlpha(0.3f)
                            //设置是否能点击空白处取消（对返回键无效，待改进）
                            .setCancelable(true)
                            //列表适配器
                            .setAdapter(adapter)
                            .setOnItemClickListener(new OnItemClickListener<String>() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id, String data) {
                                    findViewById(R.id.bt_list).performClick();
                                    Toast.makeText(getBaseContext(), data, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onItemDeleteClick(int position, String data) {
                                    Toast.makeText(getBaseContext(), "del==" + data, Toast.LENGTH_SHORT).show();
                                }
                            })
                            .create();
                }
                tipWindow.show(findViewById(R.id.bt_list));
            }
        });
    }

    private void dataPicker() {
        findViewById(R.id.bt_data_picker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<TestBean> d_1 = new ArrayList();

                // 第一层节点1
                TestBean tb_1a = new TestBean("1a");

                List<TestBean> d_2a = new ArrayList();
                // 第二层节点123
                TestBean tb_2a = new TestBean("2a");
                TestBean tb_2b = new TestBean("2b");
                TestBean tb_2c = new TestBean("2c");
                TestBean tb_2d = new TestBean("2d");
                TestBean tb_2e = new TestBean("2e");
                TestBean tb_2f = new TestBean("2f");
                TestBean tb_2g = new TestBean("2g");
                d_2a.add(tb_2a);
                d_2a.add(tb_2b);
                d_2a.add(tb_2c);
                d_2a.add(tb_2d);
                d_2a.add(tb_2e);
                d_2a.add(tb_2f);
                d_2a.add(tb_2g);
                tb_1a.setChildren(d_2a);
                d_1.add(tb_1a);

                // 第一层节点2
                TestBean tb_1b = new TestBean("1b");
                List<TestBean> d_2b = new ArrayList();
                // 第二层节点4
                TestBean tb_2h = new TestBean("2h");
                List<TestBean> d_3a = new ArrayList();
                // 第三层节点12
                TestBean tb_3a = new TestBean("3a");
                TestBean tb_3b = new TestBean("3b");
                d_3a.add(tb_3a);
                d_3a.add(tb_3b);
                tb_2d.setChildren(d_3a);
                d_2b.add(tb_2d);
                tb_1b.setChildren(d_2b);
                d_1.add(tb_1b);

                // 第一层节点3
                TestBean tb_1c = new TestBean("1c");
                List<TestBean> d_2c = new ArrayList();
                // 第二层节点56
                TestBean tb_2i = new TestBean("2i");
                TestBean tb_2j = new TestBean("2j");
                List<TestBean> d_3b = new ArrayList();
                // 第三层节点3
                TestBean tb_3c = new TestBean("3c");
                List<TestBean> d_4a = new ArrayList();
                // 第四层节点12
                TestBean tb_4a = new TestBean("4a");
                TestBean tb_4b = new TestBean("4b");

                d_4a.add(tb_4a);
                d_4a.add(tb_4b);
                tb_3c.setChildren(d_4a);
                d_3b.add(tb_3c);
                tb_2e.setChildren(d_3b);
                d_2c.add(tb_2e);
                d_2c.add(tb_2f);
                tb_1c.setChildren(d_2c);
                d_1.add(tb_1c);

                new UniversalPickerWindowBuilder(MainActivity.this, TipWindowBuilder.TIP_TYPE_WINDOW)
                        //设置空白处不透明度（0=完全透明 1=完全不透明）
                        .setAlpha(0.2f)
                        //设置是否能点击空白处取消（TIP_TYPE_WINDOW模式下对返回键无效，待改进）
                        .setCancelable(false)
                        .setOK("选定")
                        .setOnDataSelectedListener(new OnDataSelectedListener() {
                            @Override
                            public void onOKClicked(@NotNull List<? extends WheelItem> items, @NotNull int[] positions) {
                                for (int i = 0; i < items.size(); i++) {
                                    TestBean testBean = (TestBean) items.get(i);
                                    Log.d("HXB", testBean.getName());
                                }
                                for (int pos : positions) {
                                    Log.d("HXB", "pos==" + pos);
                                }
                            }

                            @Override
                            public void onCancelClicked() {

                            }

                            @Override
                            public void onOutsideClicked() {

                            }
                        })
                        //滚轮竖向间距（2-4），不设置默认为2，数字越大，间距越大
                        .setLineSpaceMultiplier(2)
                        //设置字号（px），默认14sp
                        .setTextSize(UnitConverter.sp2px(getBaseContext(), 18))
                        //滚轮正常文字的颜色（同时也是顶部Tab的非选中文字颜色）
                        .setTextColorNormal(getResources().getColor(R.color.colorAccent))
                        //滚轮聚焦文字的颜色（同时也是顶部Tab的选中文字颜色）
                        .setTextColorFocus(Color.parseColor("#0069E6"))
                        //设置滚轮分割线颜色（同时也是顶部Tab的选中颜色）
                        .setDividerColor(Color.parseColor("#1E802A"))
                        //设置滚轮分割线宽度比例
                        .setDividerWidthRatio(0.7f)
                        //设置滚轮可见项数量
                        .setVisibleItemCount(7)
                        //设置是否可以循环滚动，默认可以
                        .setCycleable(true)
                        //设置数据
                        .setDatas(d_1)
                        .create()
                        .show(findViewById(R.id.bt_data_picker));
            }
        });
    }

    private void waiting() {
        findViewById(R.id.bt_waiting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new WaitingWindowBuilder(MainActivity.this, TipWindowBuilder.TIP_TYPE_WINDOW)
                        //设置空白处不透明度（0=完全透明 1=完全不透明）
                        .setAlpha(0.2f)
                        //设置是否能点击空白处取消（TIP_TYPE_WINDOW模式下对返回键无效，待改进）
                        .setCancelable(false)
                        //提示语
                        .setMsg("读取中")
                        //设置字号（px），默认16sp
                        .setTextSize(UnitConverter.sp2px(getBaseContext(), 18))
                        //动画的图标
                        .setImageResource(R.drawable.ic_test)
                        .create()
                        .show(findViewById(R.id.bt_data_picker));
            }
        });
    }
}
