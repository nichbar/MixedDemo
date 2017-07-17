package work.nich.mixeddemo.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

import java.util.concurrent.CopyOnWriteArrayList;

import work.nich.mixeddemo.IBookManager;
import work.nich.mixeddemo.IOnBookAddListener;
import work.nich.mixeddemo.consts.ParamsConsts;
import work.nich.mixeddemo.Book;

public class BinderService extends Service {

    private CopyOnWriteArrayList<Book> mBookList;
    private IOnBookAddListener mListener;

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList = new CopyOnWriteArrayList<>();
    }

    private IBinder mBinder = new IBookManager.Stub(){

        @Override
        public void addBook(work.nich.mixeddemo.Book book) throws RemoteException {
            mBookList.add(book);
            if (mListener != null){
                mListener.onBookAdded(book);
            }
        }

        @Override
        public void registerListener(IOnBookAddListener listener) throws RemoteException {
            mListener = listener;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
