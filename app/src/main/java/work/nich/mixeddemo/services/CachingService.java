package work.nich.mixeddemo.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.widget.Toast;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CachingService extends JobService {
    private OkHttpClient mOkHttpClient;

    @Override
    public void onCreate() {
        super.onCreate();
        mOkHttpClient = new OkHttpClient.Builder().build();
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Single.create(e -> {
            Request request = new Request.Builder()
                    .url("https://raw.githubusercontent.com/nichbar/Aequorea/master/preview/preview_1.png")
                    .build();
            Response response = mOkHttpClient.newCall(request).execute();
            if (response != null) {
                e.onSuccess(response);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
                    onStopJob(params);
                }, throwable -> {
                    Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    onStopJob(params);
                });
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}
