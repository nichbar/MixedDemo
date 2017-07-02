package work.nich.mixeddemo.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import work.nich.mixeddemo.BaseActivity;
import work.nich.mixeddemo.R;
import work.nich.mixeddemo.views.ExperienceView;
import work.nich.mixeddemo.views.ProgressCircle;
import work.nich.mixeddemo.views.SlideUp;
import work.nich.mixeddemo.views.alerter.Alerter;

/**
 * Created by nich- on 2016/9/26.
 * For you,
 */

public class CustomViewActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_view_layout);

        View view = findViewById(R.id.slide_up);
        final View dim = findViewById(R.id.dim);
        SlideUp slideUp = new SlideUp(view);

        slideUp.setSlideListener(new SlideUp.SlideListener() {
            @Override
            public void onSlide(float percent) {
                dim.setAlpha(1 - (percent / 100) - 0.5f);
            }

            @Override
            public void onVisibilityChanged(int visibility) {
                if (visibility == View.GONE) {
                    dim.setVisibility(View.GONE);
                }
            }
        });

        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(this, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        dpd.show(getFragmentManager(), "dialog");
    }

    /**
     * Alerter的原理是在添加到Activity的decorView里
     * 一些收获:
     * 1. 用了弱引用的方式持有的activity实例
     * 2. 直观的构造者风格代码，构造器private final声明防止了直接new 出新实例，
     * 3.
     * <p>
     * 地址：https://github.com/Tapadoo/Alerter
     */
    public void showAlert(View view) {
        Alerter.create(this)
                .setText("there comes an alert")
                .show();
    }

    public void setProgress(View view) {
        ((ProgressCircle) view).setProgress(90);
    }

    public void setExperienceProgress(View view) {
        ((ExperienceView) view).setExperience(43999);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }
}
