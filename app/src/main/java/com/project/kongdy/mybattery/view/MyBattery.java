package com.project.kongdy.mybattery.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
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

    private Paint noPowerPaint;
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

    private float batteryWidth;
    private float batteryHeight;
    private float radius;
    private float percentOffset;
    private float batterySpace;
    private float powerHeight;

    private RectF bgRectF;
    private RectF headF;

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
        noPowerPaint = new Paint();

        bgRectF = new RectF();
        headF = new RectF();

        basePaint.setColor(Color.GRAY);
        basePaint.setStyle(Paint.Style.STROKE);
        basePaint.setStrokeWidth(16);

        paintInit(noPowerPaint);
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
        canvas.drawRoundRect(bgRectF,radius,radius,basePaint);
        basePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawRoundRect(headF,radius/2f,radius/2f,basePaint);
        drawBattery(canvas);
        basePaint.setStyle(Paint.Style.STROKE);
        float gravityOffset = getGravityOffset();
        canvas.translate(gravityOffset,0);
        canvas.drawText(String.valueOf(batteryValue)+"%",headF.right,getMeasuredHeight()/2+percentPaint.getFontSpacing()/4f,
                percentPaint);
        canvas.translate(-gravityOffset,0);
        canvas.restore();
    }

    private void drawBattery(Canvas canvas) {
        float tempWidth = batterySpace+basePaint.getStrokeWidth();
        float top = bgRectF.top+basePaint.getStrokeWidth()/2f+powerHeight/4;
        for (int i = 0; i < 5;i++){
            canvas.drawRect(tempWidth,top,tempWidth+2*batterySpace,top+powerHeight,noPowerPaint);
            tempWidth = tempWidth+3*batterySpace;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        percentPaint.setTextSize((w>h?h:w)/3f);

        // 默认电池长为宽的2.2倍
        percentOffset = 0;
        if(showPercent) {
            percentOffset = percentPaint.measureText("100%");
        }
        basePaint.setStrokeWidth((w-percentOffset)/30f);
        float batteryHead = basePaint.getStrokeWidth()*2f;
        if(2.2*h >= w-percentOffset-batteryHead-basePaint.getStrokeWidth()/2f) {
            batteryHeight = (w-percentOffset-batteryHead)/2.2f;
            batteryWidth = w-percentOffset-batteryHead-basePaint.getStrokeWidth()/2f;
        } else {
            batteryHeight = h;
            batteryWidth = h*2.2f;
        }

        batterySpace = (batteryWidth-basePaint.getStrokeWidth())/16f;
        radius = batteryWidth/14;
        bgRectF.left = (getMeasuredWidth()-(batteryWidth+percentOffset+batteryHead-basePaint.getStrokeWidth()/2f))/2f;
        bgRectF.top = (getMeasuredHeight()-batteryHeight)/2f;
        bgRectF.bottom = bgRectF.top + batteryHeight;
        bgRectF.right = bgRectF.left + batteryWidth;

        headF.left = bgRectF.right;
        headF.top = bgRectF.top+batteryHeight/4f;
        headF.right = bgRectF.right+batteryHead;
        headF.bottom = bgRectF.top+3f*batteryHeight/4f;

        powerHeight = (batteryHeight-basePaint.getStrokeWidth())*(2f/3f);

        noPowerPaint.setColor(basePaint.getColor());

    }

    private float getGravityOffset() {
        return percentOffset-percentPaint.measureText(batteryValue+"%");
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
