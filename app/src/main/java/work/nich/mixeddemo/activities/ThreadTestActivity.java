package work.nich.mixeddemo.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import work.nich.mixeddemo.BaseActivity;

/**
 * Created by nich- on 2016/10/17.
 */

public class ThreadTestActivity extends BaseActivity {

    // 实验Looper和Thread的
    private MyThread myThread = new MyThread();
    private MyThread myThread2 = new MyThread();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 模拟两个任务,尝试队列
        mockTwoTask();
    }

    private void mockTwoTask() {
        /*********** 模拟两个任务 ***********/
        postToNoneUIThread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        postToNoneUIThread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        /*********** 模拟两个任务OVER ***********/
        Log.d("current thread is", Long.toString(Thread.currentThread().getId()));
    }

    /**
     * 放入到子线程队列中
     */
    public void postToNoneUIThread(Runnable runnable) {
        // 执行到这里的时候，子线程可能尚未启动，等待子线程启动，等待的时间会很短，
        while (myThread.handler == null) {
        }
        myThread.handler.post(runnable);
        myThread2.handler.post(runnable);
        myThread.handler.sendEmptyMessage(100);
        myThread2.handler.sendEmptyMessage(100);
    }

    /**
     * 可以设计为网络请求队列
     */
    class MyThread extends Thread {
        {
            start(); // 类加载完成后直接启动
        }

        private Handler handler;

        @Override
        public void run() {
            while (true) {

                Looper.prepare(); // 创建该线程的Looper对象
                handler = new Handler(Looper.myLooper()) {
                    public void handleMessage(android.os.Message msg) {
                        Log.d("handleMessage", "" + msg.what);
                        Log.d("current thread is", Long.toString(Thread.currentThread().getId()));
                    }
                };

                Looper.loop(); // 这里是一个死循环
                // 此后的代码无法执行
            }
        }
    }
}
