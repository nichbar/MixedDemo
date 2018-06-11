package work.nich.mixeddemo.common;

import android.widget.Toast;

import work.nich.mixeddemo.MixedApplication;

public class ToastUtils {
    private static Toast mToast;

    public static void showShortToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(MixedApplication.getApp(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }
}