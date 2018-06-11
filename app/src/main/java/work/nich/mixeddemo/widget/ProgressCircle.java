package work.nich.mixeddemo.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * This file is created by nich
 * on 2017/3/2 in fansclub
 */

public class ProgressCircle extends View {

    private static final int DEFAULT_LIGHT_BLUE = 0xFFd0e7ef;
    private static final int DEFAULT_BLUE = 0xFF1eb2f0;
    private static final int DOT_SEPARATE_ANGLE = 30;
    private static final int CONTAINER_SPACING = 15;

    private Paint mLightBluePaint;
    private Paint mBluePaint;
    private Paint mProgressDotPaint;
    private Paint mProgressTextPaint;

    private float mRingRadius; // 外层圈蓝色点的半径
    private float mProgressDotRadius; // 进度圆点的半径
    private float mCenterX; // View的圆心X坐标
    private float mCenterY; // View的圆心Y坐标

    private double mDotStartX; // 小点起始位置
    private double mDotStartY;
    private double mDotEndX; // 小点结束位置
    private double mDotEndY;

    private int mRingStrokeWidth;
    private int mDotSpacing;
    private int mDotHeight;
    private int mTextSize;

    private int mProgress = 0;

    public ProgressCircle(Context context) {
        super(context);
        init();
        initRes();
    }

    public ProgressCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initRes();
    }

    public ProgressCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initRes();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ProgressCircle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        int width = getWidth() - CONTAINER_SPACING;
        int height = getHeight() - CONTAINER_SPACING;

        mRingRadius = (width <= height ? width / 2 : height / 2) - mRingStrokeWidth - mDotHeight - mDotSpacing; // 由于有外源小点所以要留空mDotSpacing

        mCenterX = (this.getRight() - this.getLeft()) / 2;
        mCenterY = (this.getBottom() - this.getTop()) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBackgroundRing(canvas);
        drawProgressRing(canvas);
        drawProgressDot(canvas);
        drawProgressText(canvas);
        drawDot(canvas);
    }

    public void setProgress(int progress) {
        if (mProgress != progress) {
            startProgressAnimation(progress);
        }
    }

    private void startProgressAnimation(final int current) {
        final ValueAnimator translationAnimate = ValueAnimator.ofInt(0, current);
        translationAnimate.setDuration(2000);
        translationAnimate.setInterpolator(new LinearInterpolator());
        translationAnimate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = Integer.parseInt(animation.getAnimatedValue().toString());
                postInvalidate();
            }
        });
        translationAnimate.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgress = current;
            }
        });
        translationAnimate.start();
    }

    private void drawProgressText(Canvas canvas) {
        float xPos = mCenterX;
        float yPos = (mCenterY - ((mProgressTextPaint.descent() + mProgressTextPaint.ascent()) / 2));
        canvas.drawText(Integer.toString(mProgress), xPos, yPos, mProgressTextPaint);
    }

    private void drawProgressDot(Canvas canvas) {
        float angle = mProgress * 3.6f - 90f;
        double dotX = Math.cos(Math.toRadians(angle)) * mRingRadius + getWidth() * .5f;
        double dotY = Math.sin(Math.toRadians(angle)) * mRingRadius + getHeight() * .5f;
        canvas.drawCircle((float) dotX, (float) dotY, mProgressDotRadius, mProgressDotPaint);
    }

    private void drawProgressRing(Canvas canvas) {
        float angle = mProgress * 3.6f;
        RectF rectF = new RectF(mCenterX - mRingRadius, mCenterY - mRingRadius, mCenterX + mRingRadius, mCenterY + mRingRadius);
        canvas.drawArc(rectF, -90.f, angle, false, mBluePaint);
    }

    private void drawDot(Canvas canvas) {
        for (int angle = 0; angle <= 360; angle += DOT_SEPARATE_ANGLE) {
            mDotStartX = Math.cos(Math.toRadians(angle)) * (mRingRadius + mLightBluePaint.getStrokeWidth() + mDotSpacing) + getWidth() * .5f;
            mDotStartY = Math.sin(Math.toRadians(angle)) * (mRingRadius + mLightBluePaint.getStrokeWidth() + mDotSpacing) + getHeight() * .5f;

            mDotEndX = Math.cos(Math.toRadians(angle)) * (mRingRadius + mLightBluePaint.getStrokeWidth() + mDotHeight + mDotSpacing) + getWidth() * .5f;
            mDotEndY = Math.sin(Math.toRadians(angle)) * (mRingRadius + mLightBluePaint.getStrokeWidth() + mDotHeight + mDotSpacing) + getHeight() * .5f;

            canvas.drawLine((float) mDotStartX, (float) mDotStartY, (float) mDotEndX, (float) mDotEndY, mLightBluePaint);
        }
    }

    private void drawBackgroundRing(Canvas canvas) {
        canvas.drawCircle(mCenterX, mCenterY, mRingRadius, mLightBluePaint);
    }

    private void init() {
        setLayerType(LAYER_TYPE_HARDWARE, null);
        setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));

        mRingStrokeWidth = dp2px(3);
        mDotHeight = dp2px(12);
        mDotSpacing = dp2px(12);
        mProgressDotRadius = dp2px(6);
        mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 48, getResources().getDisplayMetrics());
    }

    private void initRes() {
        mLightBluePaint = new Paint();
        mLightBluePaint.setAntiAlias(true);
        mLightBluePaint.setColor(DEFAULT_LIGHT_BLUE);
        mLightBluePaint.setStrokeWidth(mRingStrokeWidth);
        mLightBluePaint.setStyle(Paint.Style.STROKE);

        mBluePaint = new Paint();
        mBluePaint.setAntiAlias(true);
        mBluePaint.setColor(DEFAULT_BLUE);
        mBluePaint.setStrokeWidth(mRingStrokeWidth);
        mBluePaint.setStyle(Paint.Style.STROKE);

        mProgressDotPaint = new Paint();
        mProgressDotPaint.setAntiAlias(true);
        mProgressDotPaint.setColor(DEFAULT_BLUE);
        mProgressDotPaint.setStyle(Paint.Style.FILL);

        mProgressTextPaint = new TextPaint();
        mProgressTextPaint.setTextSize(mTextSize);
        mProgressTextPaint.setColor(DEFAULT_BLUE);
        mProgressTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
