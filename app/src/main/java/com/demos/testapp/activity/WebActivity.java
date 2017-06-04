package com.demos.testapp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.demos.testapp.R;

public class WebActivity extends AppCompatActivity {

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        WebView webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        webView.loadUrl("file:///android_asset/find.html");
    }

    public class WebAppInterface {
        private Context context;

        WebAppInterface(Context context) {
            this.context = context;
        }
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
        }
    }
}
