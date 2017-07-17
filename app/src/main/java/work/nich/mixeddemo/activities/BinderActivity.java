package work.nich.mixeddemo.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import work.nich.mixeddemo.BaseActivity;
import work.nich.mixeddemo.Book;
import work.nich.mixeddemo.IBookManager;
import work.nich.mixeddemo.IOnBookAddListener;
import work.nich.mixeddemo.R;
import work.nich.mixeddemo.services.BinderService;

public class BinderActivity extends BaseActivity {
    private IBookManager mBookManager;
    private IOnBookAddListener mListener;
    private ServiceConnection mConnection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messager_layout);
        bindService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }

    private void bindService() {
        mListener = new IOnBookAddListener() {
            @Override
            public void onBookAdded(final Book book) throws RemoteException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BinderActivity.this, book.name + book.id, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public IBinder asBinder() {
                return null;
            }
        };

        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mBookManager = IBookManager.Stub.asInterface(service);
                try {
                    mBookManager.registerListener(mListener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };

        Intent intent = new Intent(this, BinderService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    public void bindService(View view) {
        Book book = new Book();
        book.id = "1";
        book.name = "Gundam Thunderbolt";

        try {
            mBookManager.addBook(book);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
