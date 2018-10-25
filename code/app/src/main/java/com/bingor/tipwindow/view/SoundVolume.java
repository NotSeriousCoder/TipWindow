package com.bingor.tipwindow.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bingor.tipwindow.R;

/**
 * Created by HXB on 2018/10/24.
 */
public class SoundVolume {
    private View rootView, tvCopyRight;
    private SoundVolumeView svvVolume;
    private TextView tvTip;
    private ImageView ivButton;
    private boolean started;

    private OnEvent listener;


    public View getSoundVolumeView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_sound_dialog, null);
        init(view);
        return view;
    }

    private void init(View view) {
        rootView = view;
        svvVolume = rootView.findViewById(R.id.svv_m_view_sound_dialog_p_volume);
        tvTip = rootView.findViewById(R.id.tv_m_view_sound_dialog_p_tip);
        ivButton = rootView.findViewById(R.id.iv_m_view_sound_dialog_p_button);
        tvCopyRight = rootView.findViewById(R.id.tv_m_view_sound_dialog_p_copyright);

        ivButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        started = true;
                        if (listener != null) {
                            listener.onStartPressed();
                        }
                        start();
                        tvTip.setText("松开完成");
                        break;
                    case MotionEvent.ACTION_UP:
                        stop();
                        if (listener != null) {
                            listener.onStop((int) (svvVolume.getTimeSpend() / 1000));
                        }
                        tvTip.setText("按下说话");
                        break;
                }
                return true;
            }
        });
        svvVolume.setOnListenerEvent(new SoundVolumeView.OnListenerEvent() {
            @Override
            public void onStop() {
                if (listener != null) {
                    listener.onStop((int) (svvVolume.getTimeSpend() / 1000l));
                }
                tvTip.post(new Runnable() {
                    @Override
                    public void run() {
                        tvTip.setText("按下说话");

                    }
                });
            }
        });
        svvVolume.setVisibility(View.VISIBLE);
    }

    public void start() {
        svvVolume.start();
    }

    public void stop() {
        svvVolume.stop();
    }

    public void setListener(OnEvent listener) {
        this.listener = listener;
    }

    public interface OnEvent {

        public void onStartPressed();

        public void onStop(int timeSecond);

        public void onCancel();
    }
}
