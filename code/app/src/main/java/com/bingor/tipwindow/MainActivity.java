package com.bingor.tipwindow;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.bingor.numbertipview.NumTipView;
import com.bingor.poptipwindow.TipWindow;
import com.bingor.poptipwindow.adapter.SimpleListAdapter;
import com.bingor.poptipwindow.builder.CustomTipWindowBuilder;
import com.bingor.poptipwindow.builder.ListTipWindowBuilder;
import com.bingor.poptipwindow.impl.OnItemClickListener;
import com.bingor.poptipwindow.impl.OnWindowStateChangedListener;

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
    }
}
