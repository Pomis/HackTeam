package kekify.io.hackteam.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import kekify.io.hackteam.App;
import kekify.io.hackteam.DataRepository;
import kekify.io.hackteam.R;
import kekify.io.hackteam.RxUtils;
import kekify.io.hackteam.models.AccessTokenRequest;

public class LoginActivity extends AppCompatActivity {

    private DataRepository repository;

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
        loadUrl(url + "client_id=" + App.TWIST_CLIENT_ID
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
                    code = url.substring(url.indexOf("&code=") + 6, url.length());

                    onInitialCodeReceived(code);
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
        System.out.println(App.getAppInstance().getPreferencesWrapper().getAuthToken("twist").split(":")[1]);
        finish();
    }

    public void onInitialCodeReceived(String code) {

        getAndSaveAccessCode(code)
                .compose(RxUtils.applyCompletableSchedulers())
                .subscribe(() -> {
                            setAuthResult(-1);
                        },
                        throwable -> {
                            setAuthResult(0);
                        });
    }

    public Completable getAndSaveAccessCode(String code) {
        repository = new DataRepository();

        return repository.getUserCode(App.TWIST_CLIENT_ID, App.TWIST_CLIENT_SECRET, code)
                .doOnSuccess(accessTokenResponse -> {
                    App.getAppInstance()
                            .getPreferencesWrapper()
                            .setAuthToken("twist", accessTokenResponse.getAccessToken());
                })
                .toCompletable();
    }

}
