package work.nich.mixeddemo.activities;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import work.nich.chinesename.YourName;
import work.nich.mixeddemo.AppExecutors;
import work.nich.mixeddemo.BaseActivity;
import work.nich.mixeddemo.R;
import work.nich.mixeddemo.services.CachingService;

public class MainActivity extends BaseActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    private AppExecutors mExecutor;

    private static final int LOAD_IMAGE = 9999;

    private long mStartTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mExecutor = new AppExecutors();
    }

    public void openSimpleWebView(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.setAction("file:///android_asset/webkitQ.html");
        startActivity(intent);
    }

    public void openMessenger(View view) {
        Intent intent = new Intent(this, MessengerActivity.class);
        startActivity(intent);
        String x;
    }

    public void openCustomView(View view) {
        Intent intent = new Intent(this, CustomViewActivity.class);
        startActivity(intent);
    }

    public void openCoordinator(View view) {
        Intent intent = new Intent(this, CoordinatorActivity.class);
        startActivity(intent);
    }

    public void openDagger2(View view) {
        Intent intent = new Intent(this, Dagger2Activity.class);
        startActivity(intent);
    }

    public void testYourName(View view) {
        YourName yourName = new YourName(this);
        Toast.makeText(this, yourName.generateName(YourName.TWO_CHARACTER), Toast.LENGTH_SHORT).show();
    }


    public void openPermission(View view) {
        Intent intent = new Intent(this, PermissionTrialActivity.class);
        startActivity(intent);
    }

    public void openBinder(View view) {
        startActivity(new Intent(this, BinderActivity.class));
    }

    public void progressText(View view) {
        startActivity(new Intent(this, ProgressTextActivity.class));
    }

    public void startSchedulerJob(View view) {
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (jobScheduler != null) {
            jobScheduler.schedule(new JobInfo.Builder(LOAD_IMAGE, new ComponentName(this, CachingService.class))
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .build());
        }
    }
    
    public void testReadZipEntryTime(View view) {
        mExecutor.diskIO().execute(() -> {
            mStartTime = System.currentTimeMillis();
            File file = new File(Environment.getExternalStorageDirectory() + "/channelApk");
            File[] zipFile = file.listFiles();
            for (File aZipFile : zipFile) {
                long startTime = System.currentTimeMillis();

                ZipFile zip = null;
                try {
                    zip = new ZipFile(aZipFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Enumeration<? extends ZipEntry> enu = zip.entries();
                while (enu.hasMoreElements()) {
                    ZipEntry entry = enu.nextElement();
                    if (entry.isDirectory()) {
                        continue;
                    }
                    String name = entry.getName().toUpperCase();
                    if (name.contains("META-INF") && name.contains("TEST_CHANNEL")) {
                        long foundTime = System.currentTimeMillis();
                        Log.d(TAG, "Elapsed time of finding " + zip.getName() + " " + entry.getName() + " is " + (foundTime - startTime));
                    }
                }
            }

            Log.d(TAG, "Total elapsed time of finding all " + zipFile.length + " APK's channel is " + (System.currentTimeMillis() - mStartTime)  + " millisecond.");
        });
    }
}
