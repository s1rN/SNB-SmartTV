package com.senior_board;

import android.app.ProgressDialog;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.seniorboard.R;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressDialog progDailog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Change this setting to necessary URL
        final String url = "https://192.168.0.220/";


        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        // TODO: Change this scale according to TV and screen density/sizes
        webView.setInitialScale(100);

        progDailog = ProgressDialog.show(this, "Loading","Please wait...", true);
        progDailog.setCancelable(false);

        //webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);

        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.getSettings().setDomStorageEnabled(true);  // Open DOM storage function
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setUserAgentString("SmartTV Webview");

        webView.setWebViewClient(new WebViewClient(){


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progDailog.show();
                view.loadUrl(url);

                return true;
            }
            @Override
            public void onPageFinished(WebView view, final String url) {
                progDailog.dismiss();
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // Ignore SSL certificate errors
            }

           // @Override
           // public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
           //     super.onReceivedHttpError(view, request, errorResponse);
           //     if(errorResponse.getStatusCode() >= 400){
           //         view.loadUrl("file:///android_asset/error.html");
           //     }
           // }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                    view.loadUrl("file:///android_asset/error.html");
            }

        });


        webView.loadUrl(url);

    }


    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
