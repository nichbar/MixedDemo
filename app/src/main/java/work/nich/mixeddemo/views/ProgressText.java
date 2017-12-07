package work.nich.mixeddemo.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import work.nich.mixeddemo.R;

/**
 * Original Repository
 * https://github.com/withparadox2/ProgressText
 */
public class ProgressText extends AppCompatTextView {
    private int beforeProgressColor;
    private int afterProgressColor;
    private BitmapShader shader;
    private Matrix matrix;

    private int default_before_progress_color = Color.RED;
    private int default_after_progress_color = Color.WHITE;

    private float initialProcessPercentage = -1f;

    public ProgressText(Context context) {
        this(context, null, 0);
    }

    public ProgressText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ProgressText, defStyle, 0);
        beforeProgressColor = typedArray.getColor(R.styleable.ProgressText_before_progress_color, default_before_progress_color);
        afterProgressColor = typedArray.getColor(R.styleable.ProgressText_after_progress_color, default_after_progress_color);
        typedArray.recycle();

        matrix = new Matrix();
        shader = new BitmapShader(getShaderBitmap(), Shader.TileMode.CLAMP, Shader.TileMode.REPEAT);
        shader.setLocalMatrix(matrix);
    }

    public void setBeforeProgressColor(int color) {
        this.beforeProgressColor = color;
        updateShader();
    }

    public void setAfterProgressColor(int color) {
        this.afterProgressColor = color;
        updateShader();
    }

    private void updateShader() {
        shader = new BitmapShader(getShaderBitmap(), Shader.TileMode.CLAMP, Shader.TileMode.REPEAT);
        shader.setLocalMatrix(matrix);
        invalidate();
    }

    private Bitmap getShaderBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(2, 1, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(bitmap);
        paint.setColor(beforeProgressColor);
        canvas.drawPoint(0.5f, 0.5f, paint);
        paint.setColor(afterProgressColor);
        canvas.drawPoint(1.5f, 0.5f, paint);
        return bitmap;
    }

    public void setProgressByPixels(int pixels) {
        matrix.setTranslate(pixels, 0);
        shader.setLocalMatrix(matrix);
        invalidate();
    }

    public void setProgressByPercentage(float percentage) {
        if (getWidth() == 0) {
            initialProcessPercentage = percentage;
        } else {
            setProgressByPixels((int) (getMeasuredWidth() * percentage));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (initialProcessPercentage > -1f) {
            matrix.setTranslate(initialProcessPercentage * getWidth(), 0);
            shader.setLocalMatrix(matrix);
            initialProcessPercentage = -1;
        }
        getPaint().setStyle(Paint.Style.FILL);
        getPaint().setShader(shader);
        super.onDraw(canvas);
    }
}