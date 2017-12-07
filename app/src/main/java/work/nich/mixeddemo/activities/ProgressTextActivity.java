package work.nich.mixeddemo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import work.nich.mixeddemo.BaseActivity;
import work.nich.mixeddemo.R;
import work.nich.mixeddemo.views.ProgressText;

/**
 * Created by nich
 * on 17-12-7.
 */

public class ProgressTextActivity extends BaseActivity {
    @BindView(R.id.progress_text)
    ProgressText mProgressText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_text);

        ButterKnife.bind(this);
        mProgressText.setText("Progress");
        startProgress();
    }

    private void startProgress() {
        updateProgress(0.85f);
    }

    private void updateProgress(float percentage) {
        mProgressText.setProgressByPercentage(percentage);
    }

}
