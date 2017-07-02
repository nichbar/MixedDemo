package work.nich.mixeddemo;

import android.app.Activity;
import android.view.MotionEvent;

import work.nich.mixeddemo.tools.SwipeWindowHelper;

/**
 * Created by nich on 2016/12/7.
 * Your base activity.
 */

public class BaseActivity extends Activity {
    private SwipeWindowHelper mSwipeWindowHelper;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!supportSlideBack()) {
            return super.dispatchTouchEvent(ev);
        }

        if (mSwipeWindowHelper == null) {
            mSwipeWindowHelper = new SwipeWindowHelper(getWindow());
        }
        return mSwipeWindowHelper.processTouchEvent(ev) || super.dispatchTouchEvent(ev);
    }

    /**
     * 是否支持滑动返回
     */
    protected boolean supportSlideBack() {
        return true;
    }
}
