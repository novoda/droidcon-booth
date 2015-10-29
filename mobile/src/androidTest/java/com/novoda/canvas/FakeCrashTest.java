package com.novoda.canvas;

import com.novoda.canvas.base.NovodaActivityTest;

import android.app.Activity;
import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FakeCrashTest extends NovodaActivityTest {

    enum OperatingSystem
    {
        WINDOWS_98("windows98", "document.getElementById('bsod').style.display='block';"),
        WINDOWS_XP("xp", "open('bsod.html','_self');"),
        WINDOWS_VISTA("vista", "open('bsod.html','_self');"),
        WINDOWS_WIN7("win7", "open('bsod.html','_self');"),
        WINDOWS_WIN8("win8", "open('bsod.html','_self');"),
        APPLE("apple", "open('panic.html','_self');");

        private final String os;
        private final String js;

        OperatingSystem(String os, String js) {
            this.os = os;
            this.js = js;
        }
    }

    private static final int SECONDS_BEFORE_CRASH = 3;
    private WebView webView;

    @Override
    public void startTestFor(Activity activity) {
        OperatingSystem os = OperatingSystem.values()[NovodaActivity.RANDOM.nextInt(OperatingSystem.values().length)];
        setupWebView(activity, os.js);
        start(os.os);
    }

    private void start(String os) {
        webView.loadUrl(String.format("http://fediafedia.com/prank/%s/index.html", os));
    }

    private void setupWebView(Activity activity, String js) {
        webView = new WebView(activity);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(client(js));
        getParent().addView(webView);
    }

    private WebViewClient client(final String js) {
        return new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                crashIn(SECONDS_BEFORE_CRASH, js);
            }
        };
    }

    private void crashIn(int seconds, final String js) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript:function crash(){" + js + "} crash();");
            }
        }, seconds*1000);
    }
}
