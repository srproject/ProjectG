package com.sr.projectg.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.sr.projectg.R;
import com.sr.projectg.adapter.MyAppWebViewClient;

public class web extends AppCompatActivity {

    private WebView mWebView;
    SwipeRefreshLayout mySwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);



       // findViewById(R.id.activity_main_webview).setVisibility(View.VISIBLE);
        mySwipeRefreshLayout = (SwipeRefreshLayout)this.findViewById(R.id.swipeContainer);

        String gg="https://projectg-70ce9.firebaseapp.com/index.html";

        mySwipeRefreshLayout.setColorSchemeResources(
                R.color.rp1,
                R.color.rp2,
                R.color.rp3,
                R.color.rp4,
                R.color.rp5);

        mWebView = (WebView) findViewById(R.id.activity_main_webview);

        if (!isNetworkStatusAvialablesr(getApplicationContext())) {
            mWebView.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),getString(R.string.web_refesh_error),Toast.LENGTH_SHORT).show();



        }else {
            mySwipeRefreshLayout.setRefreshing(true);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.clearCache(true);
            mWebView.clearFormData();
            CookieSyncManager.createInstance(this);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();

            mWebView.loadUrl(gg);

        }
         mWebView.setWebViewClient(new com.sr.projectg.adapter.MyAppWebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                mySwipeRefreshLayout.setRefreshing(false);

            }});





        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        if (isNetworkStatusAvialablesr(getApplicationContext())) {
                            mWebView.clearCache(true);

                            mWebView.clearHistory();

                            mWebView.reload();


                        }else {


                                    Toast.makeText(getApplicationContext(), getString(R.string.web_refesh_error), Toast.LENGTH_SHORT).show();
                            mWebView.setVisibility(View.GONE);
                            mySwipeRefreshLayout.setRefreshing(false);
                            new CountDownTimer(5000, 1000) {

                                public void onTick(long millisUntilFinished) {


                                }

                                public void onFinish() {
                                    if (isNetworkStatusAvialablesr(getApplicationContext())) {
                                        mWebView.reload();


                                    }
                                 }
                            }.start();



                        }

                    }
                }
        );

    }

    public static boolean isNetworkStatusAvialablesr (Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if(netInfos != null)
                if(netInfos.isConnected())
                    return true;
        }
        return false;
    }

}
