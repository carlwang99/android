package com.bytedance.androidcamp.network.dou.fragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;

//系统自带的marquee，需要自定义一个给它焦点
public class MarqueeTextView extends AppCompatTextView implements Runnable{
    private int currentScrollX = 0;// 当前滚动的位置
    private boolean isStop = false;
    private int textWidth;
    private boolean isMeasure = false;
    public MarqueeTextView(Context context) {
        super(context);
// TODO Auto-generated constructor stub
    }
    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    protected void onDraw(Canvas canvas) {
// TODO Auto-generated method stub
        super.onDraw(canvas);
        if (!isMeasure) {// 文字宽度只需获取一次就可以了
            getTextWidth();
            isMeasure = true;
        }
    }
    /**
     * 获取文字宽度
     */
    private void getTextWidth() {
        Paint paint = this.getPaint();
        String str = this.getText().toString();
        textWidth = (int) ((TextPaint) paint).measureText(str);
    }
    @Override
    public void run() {
        currentScrollX += 2;// 滚动速度
        scrollTo(currentScrollX, 0);
        if (isStop) {
            return;
        }
        if (getScrollX() >= this.getWidth()) {
            scrollTo(textWidth, 0);
            currentScrollX = -this.getWidth();
        }
        postDelayed(this, 15);
    }
    // 开始滚动
    public void startScroll() {
        isStop = false;
        this.removeCallbacks(this);
        post(this);
    }
    // 停止滚动
    public void stopScroll() {
        isStop = true;
    }
    // 从头开始滚动
    public void startFor0() {
        currentScrollX = 0;
        startScroll();
    }
}