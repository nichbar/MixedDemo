package work.nich.mixeddemo;

import android.app.Application;

import org.polaric.colorful.Colorful;

import work.nich.mixeddemo.tools.ActivityLifecycleHelper;

public class MixedApplication extends Application {
    private ActivityLifecycleHelper mActivityLifecycleHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(mActivityLifecycleHelper = new ActivityLifecycleHelper());

        Colorful.init(this);
    }

    public ActivityLifecycleHelper getActivityLifecycleHelper() {
        return mActivityLifecycleHelper;
    }
}
