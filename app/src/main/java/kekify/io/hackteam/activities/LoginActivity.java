package kekify.io.hackteam.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import kekify.io.hackteam.R;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.wv_auth_page)
    WebView webView;
    @BindView(R.id.progressbar)
    android.view.View progressbar;

    private boolean hasUrlLoaded;
    private WebViewClient webViewClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setupWebView();
    }

    @Override
    protected void onStart() {
        super.onStart();

        String url = "https://twistapp.com/oauth/authorize?";
        loadUrl(url + "client_id=" + "2850e856d2a4460d3330eab29c5e4df079f"
         + "&scope=" + "messages:read&state=state");
    }

    private void setupWebView() {
        webView.setVerticalScrollBarEnabled(true);
        webView.setHorizontalScrollBarEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);

        WebSettings settings = webView.getSettings();

        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);

        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webViewClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("url", url);
                System.out.println(url);
                String code = "";
                hasUrlLoaded = true;

                if (url.contains("&code=")) {
                    if (url.contains("&state")) {
                        code = url.substring(url.indexOf("?code=") + 40, url.indexOf("&"));
                    } else {
                        code = url.substring(url.indexOf("?code=") + 40, url.length());
                    }

                    //presenter.onInitialCodeReceived(code);
                    System.out.println("_____CODE______" + code);
                    finish();


                    return true;

                } else if (url.contains("?code=")) {

                }

                return false;
            }
        };

        webView.setWebViewClient(webViewClient);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack())
            webView.goBack();
        else
            super.onBackPressed();
    }

    public void showProgressbar() {
        progressbar.setVisibility(android.view.View.VISIBLE);
        webView.setVisibility(View.GONE);
    }

    public void hideProgressbar() {
        progressbar.setVisibility(android.view.View.GONE);
        webView.setVisibility(View.VISIBLE);
    }

    public void loadUrl(String url) {
        webView.loadUrl(url);
        System.out.println(url);
    }

    public void postUrl(String url, byte[] postData) {
        webView.postUrl(url, postData);
    }

    public boolean hasUrlLoaded() {
        return hasUrlLoaded;
    }

    public void setAuthResult(int result) {
        setResult(result);
        //finish()
    }

}
