package work.nich.mixeddemo.view

import android.os.Bundle
import android.view.View
import butterknife.ButterKnife
import work.nich.mixeddemo.BaseActivity
import work.nich.mixeddemo.R
import work.nich.mixeddemo.widget.ExperienceView
import work.nich.mixeddemo.widget.ProgressCircle

class CustomViewActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_view_layout)
        ButterKnife.bind(this)

        Notifier.create(this)
                .setDuration(5000)
                .setText("莎拉凯瑞甘回答了你关注的问题")
                .show()
    }

    fun setProgress(view: View) {
        (view as ProgressCircle).setProgress(90)
    }

    fun setExperienceProgress(view: View) {
        (view as ExperienceView).setExperience(43999)
    }
}
