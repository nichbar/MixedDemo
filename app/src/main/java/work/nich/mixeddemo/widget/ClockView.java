package work.nich.mixeddemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.Calendar;

/**
 * Created by nich on 2016/9/26.
 * A simple clock.
 */

public class ClockView extends View {

    private final int CLOCK_BG_COLOR = 0xFFF0F0F0;
    private final int OUTER_CIRCLE_COLOR = 0xFFF8F8F8;
    private final int HOUR_MINUTE_HAND_COLOR = 0xFF5B5B5B;
    private final int SECOND_HAND_COLOR = 0xFFB55050;

    private final int MIN_SIZE = 200;
    private final int HOUR_MINUTE_HAND_STROKE_WIDTH = 30;
    private final int SECOND_HAND_WIDTH = 8;
    private final int OUTER_CIRCLE_WIDTH = 10;

    private final int DEGREE_PER_SEC = 6;

    private int hour, minute, second;
    private int clockSize;
    private int hourHandLength, minuteHandLength, secondHandLength;

    private Paint clockPaint, outerCirclePaint;
    private Paint hourPaint, minutePaint, secondPaint;
    private Paint centerCirclePaint, centerRedCirclePaint;

    private Calendar calendar;

    public ClockView(Context context) {
        super(context);
        initPaint();
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }


    private void initPaint() {
        calendar = Calendar.getInstance();

        clockPaint = new Paint();
        clockPaint.setColor(CLOCK_BG_COLOR);
        clockPaint.setAntiAlias(true);

        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(OUTER_CIRCLE_COLOR);
        outerCirclePaint.setStrokeWidth(dp2px(OUTER_CIRCLE_WIDTH));
        outerCirclePaint.setStyle(Paint.Style.STROKE);
        outerCirclePaint.setAntiAlias(true);
        outerCirclePaint.setShadowLayer(4, 2, 2, 0x80000000); // 设置阴影

        hourPaint = new Paint();
        hourPaint.setColor(HOUR_MINUTE_HAND_COLOR);
        hourPaint.setStyle(Paint.Style.STROKE);
        hourPaint.setAntiAlias(true);
        hourPaint.setShadowLayer(4, 0, 0, 0x80000000);
        hourPaint.setStrokeCap(Paint.Cap.ROUND); // 圆头
        hourPaint.setStrokeWidth(HOUR_MINUTE_HAND_STROKE_WIDTH);

        minutePaint = hourPaint;

        secondPaint = new Paint();
        secondPaint.setAntiAlias(true);
        secondPaint.setStrokeCap(Paint.Cap.ROUND);
        secondPaint.setStyle(Paint.Style.STROKE);
        secondPaint.setColor(SECOND_HAND_COLOR);
        secondPaint.setStrokeWidth(SECOND_HAND_WIDTH);
        secondPaint.setShadowLayer(4, 3, 0, 0x80000000);

        centerCirclePaint = new Paint();
        centerCirclePaint.setColor(HOUR_MINUTE_HAND_COLOR);
        centerCirclePaint.setAntiAlias(true);
        centerCirclePaint.setShadowLayer(5, 0, 0, 0x80000000);

        centerRedCirclePaint = new Paint();
        centerRedCirclePaint.setColor(SECOND_HAND_COLOR);
        centerRedCirclePaint.setAntiAlias(true);
        centerRedCirclePaint.setShadowLayer(5, 0, 0, 0x80000000);

        clockSize = dp2px(MIN_SIZE);
        hourHandLength = clockSize / 4;
        minuteHandLength = clockSize / 3;
        secondHandLength = clockSize / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(clockSize, clockSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        calendar = Calendar.getInstance();
        parseTime();
        canvas.translate(clockSize / 2, clockSize / 2); // 居中画布

        drawCircle(canvas, clockPaint, clockSize / 2);
        drawCircle(canvas, outerCirclePaint, clockSize / 2 - 20);
        drawHand(canvas, hourPaint, hourHandLength, hour * 30 + minute / 2, 0);
        drawHand(canvas, minutePaint, minuteHandLength, minute * DEGREE_PER_SEC, 0);
        drawCircle(canvas, centerCirclePaint, clockSize / 20);
        drawCircle(canvas, centerRedCirclePaint, clockSize / 40);
        drawHand(canvas, secondPaint, secondHandLength * 4 / 5, second * DEGREE_PER_SEC, secondHandLength / 5);
        postInvalidateDelayed(1000);
    }

    private void drawHand(Canvas canvas, Paint paint, int length, float degree, int yOffset) {
        canvas.save();
        canvas.rotate(degree);
        canvas.drawLine(0, yOffset, 0, -length, paint);
        canvas.restore();
    }

    private void drawCircle(Canvas canvas, Paint paint, int radius) {
        canvas.drawCircle(0, 0, radius, paint);
        canvas.save();
    }

    private void parseTime() {
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
