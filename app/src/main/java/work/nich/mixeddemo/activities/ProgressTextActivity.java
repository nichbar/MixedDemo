package work.nich.mixeddemo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import work.nich.mixeddemo.BaseActivity;
import work.nich.mixeddemo.R;
import work.nich.mixeddemo.views.ProgressView;

/**
 * Created by nich
 * on 17-12-7.
 */

public class ProgressTextActivity extends BaseActivity {
    @BindView(R.id.progress_text)
    ProgressView mProgressView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_text);

        ButterKnife.bind(this);
        mProgressView.setText("下载中");
    }

    @Override
    protected void onResume() {
        super.onResume();
        startProgress();
    }

    private void startProgress() {
        for (float i = 0; i < 1; i = (float) (i + 0.1)) {
            final float finalI = i;
            mProgressView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateProgress(finalI);
                }
            }, (long) (1000*finalI));
        }
    }

    private void updateProgress(float percentage) {
        mProgressView.setProgressByPercentage(percentage);
    }

}
