package com.bingor.tipwindow.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.bingor.poptipwindow.util.UnitConverter;
import com.bingor.tipwindow.R;

import java.util.ArrayDeque;

/**
 * Created by HXB on 2017-07-11.
 */

public class SoundVolumeView extends SurfaceView implements SurfaceHolder.Callback {
    private int heightMax, heightMin;
    private int latticeWidth;
    private int colorSound;
    private float alphaMax, alphaMin;
    private int spacingHor;
    private int numLattice;
    private ArrayDeque<Float> volumes;
    private float[] alphas;
    private int timeSize;
    private int textMargin = 15;

    private boolean drawing;
    private boolean run;
    private DrawThread drawThread;
    private SurfaceHolder holder;
    private long startTime, timeSpend;
    private int durationLimit;
    private OnListenerEvent onListenerEvent;


    public SoundVolumeView(Context context) {
        super(context);
        initView(context, null);
    }

    public SoundVolumeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public SoundVolumeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        holder = getHolder();
        holder.addCallback(this); //设置Surface生命周期回调

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SoundVolumeView);
        heightMax = ta.getDimensionPixelSize(R.styleable.SoundVolumeView_heightMax, UnitConverter.dip2px(context, 20));
        heightMin = ta.getDimensionPixelSize(R.styleable.SoundVolumeView_heightMin, UnitConverter.dip2px(context, 4));
        colorSound = ta.getColor(R.styleable.SoundVolumeView_colorSound, Color.parseColor("#3DC1C7"));
        alphaMax = ta.getFloat(R.styleable.SoundVolumeView_alphaMax, 1.0f);
        alphaMin = ta.getFloat(R.styleable.SoundVolumeView_alphaMin, 0.5f);
        numLattice = ta.getInt(R.styleable.SoundVolumeView_numLattice, 18);
        spacingHor = ta.getDimensionPixelSize(R.styleable.SoundVolumeView_spacingHor, UnitConverter.dip2px(context, 4));
        timeSize = ta.getDimensionPixelSize(R.styleable.SoundVolumeView_spacingHor, UnitConverter.dip2px(context, 14));
        latticeWidth = ta.getDimensionPixelSize(R.styleable.SoundVolumeView_spacingHor, UnitConverter.dip2px(context, 4));
        ta.recycle();

        //        ViewGroup.LayoutParams lp = getLayoutParams();
        //        if (lp == null) {
        //            lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightMax + 30);
        //        } else {
        //            lp.height = heightMax + 30;
        //        }
        //        setLayoutParams(lp);

        if (alphaMax < alphaMin) {
            alphaMax = 1.0f;
            alphaMin = 0.5f;
        }


        volumes = new ArrayDeque<>();
        alphas = new float[numLattice];
        //初始化每一格的透明度
        float deltaAlpha = (alphaMax - alphaMin) / numLattice;
        for (int i = 0; i < numLattice; i++) {
            if (i == 0) {
                alphas[i] = alphaMax;
            } else if (i == numLattice - 1) {
                alphas[i] = alphaMin;
            } else {
                alphas[i] = alphas[i - 1] - deltaAlpha;
            }
            volumes.add(0f);
        }

        setZOrderOnTop(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(heightMax + 30, MeasureSpec.getMode(heightMeasureSpec)));
    }

    private int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }


    public void setVolume(float volume) {
        if (drawing) {
            return;
        }
        if (volume > 1 || volume < 0) {
            return;
        }
        volumes.addFirst(volume);
        if (volumes.size() > numLattice) {
            volumes.removeLast();
        }
    }

    public int getDurationLimit() {
        return durationLimit;
    }

    public void setDurationLimit(int durationLimit) {
        this.durationLimit = durationLimit;
    }

    public OnListenerEvent getOnListenerEvent() {
        return onListenerEvent;
    }

    public void setOnListenerEvent(OnListenerEvent onListenerEvent) {
        this.onListenerEvent = onListenerEvent;
    }

    public long getTimeSpend() {
        return timeSpend;
    }

    public void start() {
        stop();
        run = true;
        startTime = System.currentTimeMillis();
        drawThread = new DrawThread();
        drawThread.start();
    }

    public void stop() {
        run = false;
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new DrawPrimaryThread().start();
        timeSpend = System.currentTimeMillis() - startTime;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
//        Log.d("HXB", "surfaceCreated(SurfaceHolder holder)");
        new DrawPrimaryThread().start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //        stop();
    }

    private class DrawThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (run) {
                Canvas canvas = holder.lockCanvas();
                doDraw(canvas);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (canvas != null) {
                        holder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }

    private class DrawPrimaryThread extends Thread {
        @Override
        public void run() {
            Canvas canvas = holder.lockCanvas(null);
            doDrawPrimary(canvas);
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void doDrawPrimary(Canvas canvas) {
        if (canvas == null) {
            return;
        }
        drawing = true;
        //这个很重要，清屏操作，清楚掉上次绘制的残留图像
        canvas.drawColor(Color.WHITE);

        Paint textPaint = new Paint();
        textPaint.setTextSize(timeSize);
        textPaint.setColor(Color.parseColor("#cccccc"));
        int textWidth = getTextWidth(textPaint, "99:99");

        float x = (getWidth() - (textWidth + 2 * numLattice * (latticeWidth + spacingHor))) / 2, y = (getHeight() - heightMin) / 2f;
        Paint paint = new Paint();
        paint.setColor(colorSound);
        for (int i = 0; i < numLattice; i++) {
            paint.setAlpha((int) (255 * alphaMin));
            RectF rectF = new RectF(x, y, x + latticeWidth, y + heightMin);
            canvas.drawRoundRect(rectF, 6, 6, paint);

            x += (latticeWidth + spacingHor);
        }


        int num = (textWidth / (latticeWidth + spacingHor)) + 1;

        for (int i = 0; i < num; i++) {
            paint.setAlpha(255);
            RectF rectF = new RectF(x, y, x + latticeWidth, y + heightMin);
            canvas.drawRoundRect(rectF, 6, 6, paint);
            x += (latticeWidth + spacingHor);
        }

        for (int i = 0; i < numLattice; i++) {
            paint.setAlpha((int) (255 * alphaMin));
            RectF rectF = new RectF(x, y, x + latticeWidth, y + heightMin);
            canvas.drawRoundRect(rectF, 6, 6, paint);
            x += (latticeWidth + spacingHor);
        }


        drawing = false;
    }


    private void doDraw(Canvas canvas) {
//        Log.d("HXB", "doDraw(Canvas canvas)---" + (canvas == null));
        if (canvas == null) {
            return;
        }
        drawing = true;
        //这个很重要，清屏操作，清楚掉上次绘制的残留图像
        canvas.drawColor(Color.WHITE);

        float centerX = getWidth() / 2;

        long time = (long) ((System.currentTimeMillis() - startTime) / 1000f);
        String timeText = "";
        Paint textPaint = new Paint();
        int textWidth = 0;
        textPaint.setTextSize(timeSize);
        int minute = 0, second = 0;
        textPaint.setColor(Color.parseColor("#cccccc"));
        if (durationLimit == 0) {
            minute = (int) (time / 60);
            second = (int) (time % 60);
        } else {
            time = durationLimit - time;
            if (time <= 0) {
                if (onListenerEvent != null) {
                    onListenerEvent.onStop();
                }
                stop();
                time = 0;
            } else {
                minute = (int) (time / 60);
                second = (int) (time % 60);
            }
            if (time <= 10) {
                textPaint.setColor(getResources().getColor(R.color.red_num_tip_view));
            }
        }
        timeText = (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second);
        textWidth = getTextWidth(textPaint, timeText);
        Rect bounds = new Rect();
        textPaint.getTextBounds(timeText, 0, timeText.length(), bounds);
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(timeText, centerX - textWidth / 2, baseline, textPaint);

        //左边
        float leftTopX = centerX - textWidth / 2 - textMargin - latticeWidth;
        //右边
        float rightTopX = centerX + textWidth / 2 + textMargin;
        float top = 0;
        Float[] tempVolumes = volumes.toArray(new Float[0]);
        for (int i = 0; i < numLattice; i++) {
            float ratio = tempVolumes[i];
            float height = ratio * heightMax;
            if (height < heightMin) {
                height = heightMin;
            }
            top = (getHeight() - height) / 2;

            Paint paint = new Paint();
            paint.setColor(colorSound);
            paint.setAlpha((int) (255 * alphas[i]));

            RectF rectFLeft = new RectF(leftTopX, top, leftTopX + latticeWidth, top + height);
            RectF rectFRight = new RectF(rightTopX, top, rightTopX + latticeWidth, top + height);
            canvas.drawRoundRect(rectFLeft, 6, 6, paint);
            canvas.drawRoundRect(rectFRight, 6, 6, paint);


            leftTopX -= (latticeWidth + spacingHor);
            rightTopX += (latticeWidth + spacingHor);
        }
        drawing = false;
    }

    public interface OnListenerEvent {
        public void onStop();
    }
}
