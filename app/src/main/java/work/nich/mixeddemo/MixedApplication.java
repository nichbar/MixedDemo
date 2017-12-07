package work.nich.mixeddemo;

import android.app.Application;

import work.nich.mixeddemo.tools.ActivityLifecycleHelper;

public class MixedApplication extends Application {
    private ActivityLifecycleHelper mActivityLifecycleHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(mActivityLifecycleHelper = new ActivityLifecycleHelper());
    }

    public ActivityLifecycleHelper getActivityLifecycleHelper() {
        return mActivityLifecycleHelper;
    }
}
