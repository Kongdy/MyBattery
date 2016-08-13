package com.project.kongdy.mybattery.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.project.kongdy.mybattery.R;

/**
 * @author kongdy
 *         on 2016/8/13
 *  电池控件
 */
public class MyBattery extends View {

    /** 电池色 **/
    private Paint powerPaint;
    /** 基本色/无电色 **/
    private Paint basePaint;
    /** 电量百分比文字显示 **/
    private TextPaint percentPaint;
    /** 电量 0~100 **/
    private int batteryValue;
    /** 显示电量百分比 **/
    private boolean showPercent;

    private int batteryWidth;
    private int batteryHeight;

    public MyBattery(Context context) {
        super(context);
        init(null);
    }

    public MyBattery(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MyBattery(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyBattery(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MyBattery);

        showPercent = a.getBoolean(R.styleable.MyBattery_mb_showPercent,false);

        a.recycle();

        percentPaint = new TextPaint();
        basePaint = new Paint();
        powerPaint = new Paint();

        paintInit(basePaint);
        paintInit(powerPaint);
        paintInit(percentPaint);
    }

    private void paintInit(Paint paint) {
        paint.setAntiAlias(true); // 锯齿
        paint.setFilterBitmap(true); // 滤波
        paint.setDither(true); // 防抖
        paint.setSubpixelText(true); // 像素自处理
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.saveLayer(0,0,getMeasuredWidth(),getMeasuredHeight(),basePaint,Canvas.ALL_SAVE_FLAG);

       // canvas.drawRoundRect(0,0,);

        canvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // 默认电池长为宽的2.2倍

    }

    public int getBatteryValue() {
        return batteryValue;
    }

    public void setBatteryValue(int batteryValue) {
        if(batteryValue < 0) {
            batteryValue = 0;
        }
        if(batteryValue > 100) {
            batteryValue = 100;
        }
        this.batteryValue = batteryValue;
    }
}
