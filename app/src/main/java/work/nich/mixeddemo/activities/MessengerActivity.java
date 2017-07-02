package work.nich.mixeddemo.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.Toast;

import work.nich.mixeddemo.BaseActivity;
import work.nich.mixeddemo.R;
import work.nich.mixeddemo.consts.ParamsConsts;
import work.nich.mixeddemo.services.MessengerService;

/**
 * Created by nich- on 2016/10/17.
 */

public class MessengerActivity extends BaseActivity {

    private Messenger mMessenger;
    private Messenger mReplyMessenger;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMessenger = new Messenger(service);
            Message msg = Message.obtain(null, ParamsConsts.MSG_FROM_CLIENT);
            Bundle data = new Bundle();
            data.putString(ParamsConsts.MSG_ARG, "Hi there, here comes a message from Earth.");
            msg.setData(data);
            msg.replyTo = mReplyMessenger;

            try {
                mMessenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messager_layout);
        mReplyMessenger = new Messenger(new MessengerHandler(getApplicationContext()));
    }

    @Override
    protected void onDestroy() {
        try {
            unbindService(serviceConnection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    public void bindService(View view) {
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void unbindService(View view) {
        try {
            unbindService(serviceConnection);
            Toast.makeText(view.getContext(), "Bind Successful!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(view.getContext(), "Bind Failed =_= ", Toast.LENGTH_SHORT).show();
        }
    }

    public static class MessengerHandler extends Handler {
        private Context mContext;

        public MessengerHandler(Context context) {
            mContext = context.getApplicationContext();
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ParamsConsts.MSG_FROM_SERVICE:
                    String content = String.valueOf("Client : " + msg.getData()
                        .getString(ParamsConsts.REPLY_ARG));
                    Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }
}



