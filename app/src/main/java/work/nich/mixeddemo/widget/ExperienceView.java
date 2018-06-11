package work.nich.mixeddemo.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by nich on 2016/12/1.
 * Custom experience view.
 */

public class ExperienceView extends View {

    private Paint mColorPaint;
    private Paint mTrianglePaint;
    private Paint mTextPaint;
    private Path mTrianglePath;

    private float mLineHeight; // 彩带距顶部的距离
    private float mLineWidth; // 彩带的长度
    private float mStartX, mStartY; // 起始位置
    private final float HEIGHT_RATIO = 0.65f;
    private final float WIDTH_RATIO = 0.9f;
    private float mBlockWidth; // 颜色块的长度
    private int mBlockNumber = 5; // 颜色块的数量
    private int mDividerBlockNumber = mBlockNumber - 1; //分割块的数量
    private float mTriangleWidth;
    private float mDividerBlockWidth; // 分割块的宽度
    private float mTriangleHeight;
    private float mTextOffsetY;
    private float mTextOffsetX;

    private int mColor[] = new int[]{0xff7ec4ff, 0xff79dc74, 0xffffb535, 0xffff9358, 0xffff7f7f}; // TODO: 颜色应可配置
    private SparseIntArray mColorRange = new SparseIntArray();

    private int mMaxExperience = 50000;
    private int mUserExperience = 12580;

    public ExperienceView(Context context) {
        this(context, null);
    }

    public ExperienceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExperienceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(LAYER_TYPE_HARDWARE, null);
        setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        initRes();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ExperienceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        final int width = getWidth();
        final int height = getHeight();

        mLineHeight = height * HEIGHT_RATIO;
        mLineWidth = width * WIDTH_RATIO;

        mStartX = (width - mLineWidth) / 2;
        mStartY = mLineHeight;

        mBlockWidth = (mLineWidth - (mDividerBlockNumber * mDividerBlockWidth)) / mBlockNumber;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int rangeNumber = 0;
        float startX = mStartX;
        float endX;
        boolean gotDividerBetween;

        for (int i = 1; i <= mBlockNumber; i++) {
            gotDividerBetween = !(i == mBlockNumber);
            endX = startX + mBlockWidth;
            mColorPaint.setColor(mColor[i - 1]);
            canvas.drawLine(startX, mStartY, endX, mStartY, mColorPaint);
            mColorRange.append(i - 1, (int) startX);// 保存每个颜色开始时横坐标的值，供对比三角形顶点位置取底部颜色条颜色用
            if (i == 1)
                canvas.drawText(Integer.toString(rangeNumber), startX - mTextOffsetY + mTextOffsetX, mStartY + 5 * mTextOffsetY, mTextPaint);
            rangeNumber = rangeNumber + 10000;
            startX = gotDividerBetween ? endX + mDividerBlockWidth : endX;
            canvas.drawText(Integer.toString(rangeNumber), startX - mTextOffsetY - mTextOffsetX, mStartY + 5 * mTextOffsetY, mTextPaint);
        }

        float triangleOriginY = mStartY - dp2px(10);
        if (mUserExperience > mMaxExperience) mUserExperience = mMaxExperience;
        float triangleStartX = mStartX + (float) mUserExperience / mMaxExperience * mLineWidth;
        mTrianglePath.reset();
        mTrianglePath.moveTo(triangleStartX, triangleOriginY);
        mTrianglePath.lineTo(triangleStartX + mTriangleWidth / 2, triangleOriginY - mTriangleHeight);
        mTrianglePath.lineTo(triangleStartX - mTriangleWidth / 2, triangleOriginY - mTriangleHeight);
        mTrianglePath.close();
        mTrianglePaint.setColor(getColor(triangleStartX));
        canvas.drawPath(mTrianglePath, mTrianglePaint);

        canvas.drawText(Integer.toString(mUserExperience), triangleStartX, triangleOriginY - mTriangleHeight - mTextOffsetY, mTextPaint);
    }

    private int getColor(float triangleStartX) {
        for (int i = 0; i < mColorRange.size(); i++) {
            if (((int) triangleStartX) < mColorRange.get(i) && (i > 0) && (i < mColor.length)) {
                return mColor[i - 1];
            } else if (mColorRange.get(i) < triangleStartX && mColorRange.get(i) + mBlockWidth > triangleStartX) {
                return mColor[i];
            }
        }
        return 0xffFF0000;
    }

    private void initRes() {
        mColorPaint = new Paint();
        mColorPaint.setAntiAlias(true);
        mColorPaint.setStrokeWidth(dp2px(7));

        mTrianglePaint = new Paint();
        mTrianglePaint.setAntiAlias(true);

        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics()));
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mTrianglePath = new Path();

        mTriangleWidth = dp2px(16);
        mDividerBlockWidth = dp2px(4);
        mTriangleHeight = (float) Math.sqrt(mTriangleWidth * mTriangleWidth - (mTriangleWidth / 2) * (mTriangleWidth / 2));
        mTextOffsetY = dp2px(5);
        mTextOffsetX = dp2px(5);
    }

    /**
     * 设置用户当前经验值
     *
     * @param current 用户当前经验值
     */
    public void setExperience(int current) {
        if (mUserExperience != current) startExperienceChangeAnimation(current);
    }

    private void startExperienceChangeAnimation(final int current) {
        final ValueAnimator translationAnimate = ValueAnimator.ofInt(mUserExperience, current);
        translationAnimate.setDuration(1000);
        translationAnimate.setInterpolator(new LinearInterpolator());
        translationAnimate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mUserExperience = Integer.parseInt(animation.getAnimatedValue().toString());
                postInvalidate();
            }
        });
        translationAnimate.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mUserExperience = current;
            }
        });
        translationAnimate.start();
    }

    /**
     * 设置经验最大值以及用户当前经验值
     *
     * @param max     经验最大值
     * @param current 用户当前经验值
     */
    public void setExperience(int max, int current) {
        mMaxExperience = max;
        mUserExperience = current;
        // TODO 看最终需求，未完成！
        postInvalidate();
    }

    /**
     * 设置颜色数组
     *
     * @param color 颜色数组，不设就是用默认的5种颜色
     */
    public void setColor(int color[]) {
        mColor = color;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
