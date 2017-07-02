package work.nich.tinyimagepicker;

import android.app.Activity;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class TinyAlbumActivity extends Activity {
    private ArrayList<Album> mAlbums;

    private Handler mHandler;

    private Thread mWorkThread;
    private ContentObserver mObserver;

    private TinyAlbumAdapter mAdapter;

    private GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tip_activity_album);
        initView();
    }

    private void initView() {
        mGridView = (GridView) findViewById(R.id.grid_album);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), TinyImageActivity.class);
                intent.putExtra(TinyConst.INTENT_IMAGE_PICKER, mAlbums.get(position).name);
                startActivityForResult(intent, TinyConst.REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case TinyConst.EMPTY_ADAPTER:
                        Toast.makeText(TinyAlbumActivity.this, "adapter is empty!", Toast.LENGTH_SHORT).show();
                }
            }
        };
        mObserver = new ContentObserver(mHandler) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                loadAlbums();
            }
        };

        getContentResolver().registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, false, mObserver);
    }

    private void loadAlbums() {

    }

    @Override
    protected void onStop() {
        super.onStop();

//        stopThread();

        getContentResolver().unregisterContentObserver(mObserver);
        mObserver = null;

        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mAlbums = null;
        if (mAdapter != null) {
//            mAdapter.releaseResources();
        }
        mGridView.setOnItemClickListener(null);
    }

    private class AlbumLoaderRunnable implements Runnable {
        @Override
        public void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

            if (mAdapter == null) {
                sendMessage(TinyConst.EMPTY_ADAPTER);
            }

//            Cursor cursor = getApplicationContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,)
        }
    }

    private void sendMessage(int what) {
        if (mHandler == null) {
            return;
        }

        Message message = mHandler.obtainMessage();
        message.what = what;
        message.sendToTarget();
    }
}
