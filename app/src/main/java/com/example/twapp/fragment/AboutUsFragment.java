package com.example.twapp.fragment;

import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.twapp.R;
import com.example.twapp.base.BaseFragment;


/**
 * Created by lenovo-pc on 2017/7/17.
 */

public class AboutUsFragment extends BaseFragment {
    WebView webView;

    @Override
    protected int getViewID() {
        return R.layout.fragment_aboutus;
    }

    @Override
    protected void initView(View conteView) {
        webView = conteView.findViewById(R.id.webview);
        webView.loadUrl("http://www.healthwear-tech.com/index.php?s=/article/lists/id/10.html");
        //启用支持javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setSupportZoom(true);  //支持放大缩小
        webView.getSettings().setBuiltInZoomControls(true);
    }

    @Override
    protected void setListener() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }
        });


        webView.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void create() {

    }

    @Override
    protected void resuem() {

    }

    @Override
    protected void psuse() {

    }

}
