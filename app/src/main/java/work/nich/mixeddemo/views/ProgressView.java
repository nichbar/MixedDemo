package work.nich.mixeddemo.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import work.nich.mixeddemo.R;

/**
 * Original Repository
 * https://github.com/withparadox2/ProgressText
 */
public class ProgressView extends AppCompatTextView implements View.OnClickListener {
    private int mPreColor;
    private int mPostColor;
    private int mBackgroundColor;

    private int mWidth;
    private int mHeight;

    private float mPercentage = 0f;
    private float mInitPercentage = -1f;

    private BitmapShader mShader;
    private Matrix matrix;
    private Paint mBackgroundPaint;
    private Paint mBackgroundRectPaint;
    private Rect mBackgroundRect;
    private Rect mBackgroundRingRect;
    private OnClickListener mClickListener;

    private static final int DEFAULT_BEFORE_PROGRESS_COLOR = Color.WHITE;
    private static final int DEFAULT_AFTER_PROGRESS_COLOR = Color.RED;
    private static final int DEFAULT_BACKGROUND_COLOR = Color.RED;

    private int mStrokeWidth = dp2px(3);

    public ProgressView(Context context) {
        this(context, null, 0);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ProgressView, defStyle, 0);
        mPreColor = typedArray.getColor(R.styleable.ProgressView_postColor, DEFAULT_BEFORE_PROGRESS_COLOR);
        mPostColor = typedArray.getColor(R.styleable.ProgressView_preColor, DEFAULT_AFTER_PROGRESS_COLOR);
        mBackgroundColor = typedArray.getColor(R.styleable.ProgressView_backgroundColor, DEFAULT_BACKGROUND_COLOR);
        typedArray.recycle();

        matrix = new Matrix();
        mShader = new BitmapShader(getShaderBitmap(), Shader.TileMode.CLAMP, Shader.TileMode.REPEAT);
        mShader.setLocalMatrix(matrix);

        mBackgroundRect = new Rect();
        mBackgroundRingRect = new Rect();

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(mBackgroundColor);

        mBackgroundRectPaint = new Paint();
        mBackgroundRectPaint.setColor(mBackgroundColor);
        mBackgroundRectPaint.setStyle(Paint.Style.STROKE);
        mBackgroundRectPaint.setStrokeWidth(mStrokeWidth);
    }

    private Bitmap getShaderBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(2, 1, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(bitmap);
        paint.setColor(mPreColor);
        canvas.drawPoint(0.5f, 0.5f, paint);
        paint.setColor(mPostColor);
        canvas.drawPoint(1.5f, 0.5f, paint);
        return bitmap;
    }

    public void setProgressByPixels(int pixels) {
        updatePassBackground(pixels);
        matrix.setTranslate(pixels, 0);
        mShader.setLocalMatrix(matrix);
        invalidate();
    }

    public void setProgressByPercentage(float percentage) {
        // mWidth为0时，View还被渲染测量
        if (mWidth == 0) {
            mInitPercentage = percentage;
        } else {
            setProgressWithInterpolator(percentage);
        }
    }

    private void setProgressWithInterpolator(final float percentage) {
        ValueAnimator percentageAnimator = ValueAnimator.ofFloat(mPercentage, percentage);
        percentageAnimator.setDuration(200);
        percentageAnimator.setInterpolator(new LinearInterpolator());
        percentageAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPercentage = (float) animation.getAnimatedValue();
                setProgressByPixels((int) (mWidth * mPercentage));
                postInvalidate();
            }
        });
        percentageAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mPercentage = percentage;
            }
        });
        percentageAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mWidth = getWidth();
        mHeight = getHeight();

        mBackgroundRingRect.set(0, 0, mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mInitPercentage > -1f) {
            float passX = mInitPercentage * mWidth;

            updatePassBackground(passX);

            matrix.setTranslate(passX, 0);
            mShader.setLocalMatrix(matrix);
            mInitPercentage = -1;
        }

        getPaint().setStyle(Paint.Style.FILL);
        getPaint().setShader(mShader);

        canvas.drawRect(mBackgroundRingRect, mBackgroundRectPaint);
        canvas.drawRect(mBackgroundRect, mBackgroundPaint);

        super.onDraw(canvas);
    }

    private void updatePassBackground(float passX) {
        mBackgroundRect.set(0, 0, (int) passX, mHeight);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }


    @Override
    public void onClick(View v) {
        if (mClickListener != null) {
            mClickListener.onClick(v);
        }
    }

    public void setOnClickListener(OnClickListener listener) {
        mClickListener = listener;
    }
}