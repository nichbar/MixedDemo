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

import work.nich.mixeddemo.consts.ParamsConsts;

/**
 * Created by nich- on 2016/10/17.
 *
 */

public class MessengerService extends Service {

    private Messenger mMessenger;

    @Override
    public void onCreate() {
        super.onCreate();
        mMessenger = new Messenger(new MessengerHandler(getApplicationContext()));
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
    
    
    private static class MessengerHandler extends Handler {
        private Context mContext;

        MessengerHandler(Context context) {
            mContext = context.getApplicationContext();
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ParamsConsts.MSG_FROM_CLIENT:
                    // 收到消息
                    String content = String.valueOf("Service : " + msg.getData()
                        .getString(ParamsConsts.MSG_ARG));
                    Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();

                    // 回复消息
                    Messenger client = msg.replyTo;
                    Message reply = Message.obtain(null, ParamsConsts.MSG_FROM_SERVICE);
                    Bundle data = new Bundle();
                    data.putString(ParamsConsts.REPLY_ARG, "Hey Earth, messages well received.");
                    reply.setData(data);

                    try {
                        client.send(reply);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
