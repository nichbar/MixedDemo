package work.nich.mixeddemo.tools;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by nich- on 2016/9/23.
 */

public class WebInterface {
    Context mContext;

    public WebInterface(Context context) {
        mContext = context;
    }

    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }
}

