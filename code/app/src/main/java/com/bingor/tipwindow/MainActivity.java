package com.bingor.tipwindow;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bingor.numbertipview.NumTipView;
import com.bingor.poptipwindow.ListTipWindow;
import com.bingor.poptipwindow.adapter.SimpleListAdapter;
import com.bingor.poptipwindow.impl.OnItemClickListener;

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
                new ListTipWindow.Builder(MainActivity.this)
                        .setAdapter(adapter)
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
    }
}
