package work.nich.mixeddemo.activities;

import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import work.nich.mixeddemo.BaseActivity;
import work.nich.mixeddemo.R;
import work.nich.mixeddemo.tools.WebInterface;

/**
 * Created by nich- on 2016/9/23.
 * Simple WebView Activity.
 */
public class WebViewActivity extends BaseActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_layout);

        LinearLayout container = (LinearLayout) findViewById(R.id.container);
        initWebView();
        container.addView(webView);
    }

    private void initWebView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webView = new WebView(getApplicationContext());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return !Uri.parse(url)
                    .getHost()
                    .equals("nich.work") && super.shouldOverrideUrlLoading(view, url);
            }
        });
        webView.addJavascriptInterface(new WebInterface(this), "Android");
        webView.loadUrl(getIntent().getAction());
        webView.loadUrl("javascript:show({\\\"key\\\": 1234})");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
