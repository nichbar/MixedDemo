package work.nich.mixeddemo;

import android.app.Application;

import work.nich.mixeddemo.tools.ActivityLifecycleHelper;

public class MixedApplication extends Application {

    private ActivityLifecycleHelper mActivityLifecycleHelper;
    private static MixedApplication mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        registerActivityLifecycleCallbacks(mActivityLifecycleHelper = new ActivityLifecycleHelper());
    }

    public ActivityLifecycleHelper getActivityLifecycleHelper() {
        return mActivityLifecycleHelper;
    }

    public static MixedApplication getApp() {
        return mApp;
    }
}
