### Chapter 4

效果：

![1562850736995](C:\Users\Carl\AppData\Roaming\Typora\typora-user-images\1562850736995.png)

![1562850745196](C:\Users\Carl\AppData\Roaming\Typora\typora-user-images\1562850745196.png)

相关代码：

定时刷新：

```java
private Runnable tickRunnable = new Runnable() {
    public void run() {
        mClockView.invalidate();
        mHandler.postDelayed(tickRunnable, 1000);
    }
};
private Handler mHandler = new Handler();
```

在OnCreate方法中

```java
mHandler.post(tickRunnable);
```

画指针：

```java
private void drawNeedles(final Canvas canvas) {
    // Default Color:
    // - secondsNeedleColor
    // - hoursNeedleColor
    // - minutesNeedleColor
    //0.70.60.4

    float mSecondRadius = 0.55f*mRadius;
    float mMinuteRadius = 0.45f*mRadius;
    float mHourRadius = 0.3f*mRadius;

    float stopX;
    float stopY;

    Calendar calendar = Calendar.getInstance();

    int hour = calendar.get(Calendar.HOUR);
    int minute = calendar.get(Calendar.MINUTE);
    int second = calendar.get(Calendar.SECOND);

    Paint mLinePaint = new Paint();
    mLinePaint.setStyle(Paint.Style.FILL);
    mLinePaint.setStrokeCap(Paint.Cap.ROUND);

    //画秒针
    mLinePaint.setColor(secondsNeedleColor);
    mLinePaint.setStrokeWidth(6f);
    stopX = mCenterX+mSecondRadius*(float)Math.cos(Math.toRadians(second/60f*360f-90f));
    stopY = mCenterY+mSecondRadius*(float)Math.sin(Math.toRadians(second/60f*360f-90f));
    canvas.drawLine(mCenterX,mCenterY,stopX,stopY,mLinePaint);

    //画分针
    mLinePaint.setColor(minutesNeedleColor);
    mLinePaint.setStrokeWidth(9f);
    stopX = mCenterX+mMinuteRadius*(float)Math.cos(Math.toRadians((minute*60f+second)/(60f*60f)*360f-90f));
    stopY = mCenterY+mMinuteRadius*(float)Math.sin(Math.toRadians((minute*60f+second)/(60f*60f)*360f-90f));
    canvas.drawLine(mCenterX,mCenterY,stopX,stopY,mLinePaint);

    //画时针
    mLinePaint.setColor(hoursNeedleColor);
    mLinePaint.setStrokeWidth(12f);
    stopX = mCenterX+mHourRadius*(float)Math.cos(Math.toRadians((hour*60+minute)/(12f*60f)*360f-90f));
    stopY = mCenterY+mHourRadius*(float)Math.sin(Math.toRadians((hour*60+minute)/(12f*60f)*360f-90f));
    canvas.drawLine(mCenterX,mCenterY,stopX,stopY,mLinePaint);

}
```

画中心圆：

```java
private void drawCenter(Canvas canvas) {
    // Default Color:
    // - centerInnerColor
    // - centerOuterColor
    Paint mPaint = new Paint();
    mPaint.setColor(centerInnerColor);
    mPaint.setStyle(Paint.Style.FILL);
    canvas.drawCircle(mCenterX,mCenterY,0.015f*mWidth,mPaint);
    mPaint.setColor(centerOuterColor);
    mPaint.setStyle(Paint.Style.STROKE);
    mPaint.setStrokeWidth(0.01f*mWidth);
    canvas.drawCircle(mCenterX,mCenterY,0.015f*mWidth,mPaint);
}
```

画时间：

```java
private void drawHoursValues(Canvas canvas) {
    // Default Color:
    // - hoursValuesColor
    Paint textPaint = new Paint();
    textPaint.setColor(hoursValuesColor);
    textPaint.setTextSize(0.1f*mWidth);
    textPaint.setStyle(Paint.Style.FILL);
    textPaint.setTextAlign(Paint.Align.CENTER);
    textPaint.setAntiAlias(true);

    for(int i = 1; i <= 12; i++){
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;

        double CenterX = mCenterX + (0.7f*mRadius)* Math.cos(Math.toRadians((i-3f)*30f));
        double CenterY = mCenterX + (0.7f*mRadius) * Math.sin(Math.toRadians((i-3f)*30f));

        int baseLineY = (int)(CenterY+((bottom-top)/2-bottom));
        canvas.drawText(i+"",(float)CenterX, baseLineY, textPaint);

    }
}
```