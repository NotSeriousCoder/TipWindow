package com.bingor.tipwindow;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.bingor.numbertipview.NumTipView;
import com.bingor.picker.datetimepickerdialog.wheel.WheelItem;
import com.bingor.picker.universalPicker.UniversalPickerView;
import com.bingor.poptipwindow.TipWindow;
import com.bingor.poptipwindow.adapter.SimpleListAdapter;
import com.bingor.poptipwindow.builder.CustomTipWindowBuilder;
import com.bingor.poptipwindow.builder.ListTipWindowBuilder;
import com.bingor.poptipwindow.builder.UniversalPickerWindowBuilder;
import com.bingor.poptipwindow.impl.OnDataSelectedListener;
import com.bingor.poptipwindow.impl.OnItemClickListener;
import com.bingor.poptipwindow.impl.OnWindowStateChangedListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SimpleListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    adapter.setNeedDelete(false);
                    adapter.setNeedTag(true);
                }
                new ListTipWindowBuilder(MainActivity.this)
                        .setAdapter(adapter)
                        .setCancelable(false)
                        .setOnItemClickListener(new OnItemClickListener<String>() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id, String data) {
                                Toast.makeText(getBaseContext(), data, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onItemDeleteClick(int position, String data) {
                                Toast.makeText(getBaseContext(), "del==" + data, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setAlpha(0.3f)
                        .create()
                        .show(findViewById(R.id.tv));
            }
        });

        NumTipView numTipView = findViewById(R.id.ntv_m_frg_mine_p_msg_num);
        numTipView.setNum(15);


        findViewById(R.id.bt_custom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = new TextView(MainActivity.this);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tv.setLayoutParams(lp);
                tv.setText("确定要删除这个文件吗？");
                new CustomTipWindowBuilder(MainActivity.this)
                        .setOK("好的")
                        .setCancel("不要")
                        .setContentView(tv)
//                        .setTextContent("确定要删除这个文件吗~~")
                        .setAlpha(0.3f)
                        .setCancelable(false)
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
                        .show(findViewById(R.id.tv));
            }
        });

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
                d_2a.add(tb_2a);
                d_2a.add(tb_2b);
                d_2a.add(tb_2c);
                tb_1a.setChildren(d_2a);
                d_1.add(tb_1a);

                // 第一层节点2
                TestBean tb_1b = new TestBean("1b");
                List<TestBean> d_2b = new ArrayList();
                // 第二层节点4
                TestBean tb_2d = new TestBean("2d");
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
                TestBean tb_2e = new TestBean("2e");
                TestBean tb_2f = new TestBean("2f");
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

                new UniversalPickerWindowBuilder(MainActivity.this)
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
                        .setDatas(d_1)
                        .create()
                        .show(findViewById(R.id.bt_data_picker));
            }
        });


//        UniversalPickerView universalPickerView = findViewById(R.id.upv_test);
//        universalPickerView.setDatas(d_1);
    }
}
