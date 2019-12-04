package com.krish.app.playo.features.web;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.krish.app.playo.R;
import com.krish.app.playo.databinding.FragmentWebBinding;
import com.krish.app.playo.features.application.base.BaseFragment;

public class WebFragment extends BaseFragment{
    FragmentWebBinding fragmentWebBinding;
    public static final String ARG_URL= "arg_url";
    private static final String ARG_TITLE = "arg_title";
    private boolean isLoaded = false;

    public static WebFragment newInstance(String query, String title) {
        WebFragment fragment = new WebFragment();
        Bundle bundle= new Bundle();
        bundle.putString(ARG_URL,query);
        bundle.putString(ARG_TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentWebBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_web, container, false);
        initView();
        return fragmentWebBinding.getRoot();
    }

    private void initView() {
        fragmentWebBinding.webView.getSettings().setJavaScriptEnabled(true);

        //Increase loading data performance of web view
        if (Build.VERSION.SDK_INT >= 19) {
            fragmentWebBinding.webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            fragmentWebBinding.webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragmentWebBinding.webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        fragmentWebBinding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                fragmentWebBinding.progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                fragmentWebBinding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                fragmentWebBinding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        Bundle bundle = getArguments();
        if(bundle!=null){
            String url = bundle.getString(ARG_URL);
            if(!url.contains("https://")){
                url = url.replace("http","https");
            }
            fragmentWebBinding.webView.loadUrl(url);
            fragmentWebBinding.textTitle.setText(bundle.getString(ARG_TITLE));
        }

    }
}
