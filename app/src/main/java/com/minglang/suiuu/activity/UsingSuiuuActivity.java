package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UsingSuiuuActivity extends BaseAppCompatActivity {

    private static final String TAG = UsingSuiuuActivity.class.getSimpleName();

    @Bind(R.id.using_suiuu_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.using_help_wab_view)
    WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_using_suiuu);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        WebSettings webSettings = webView.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webView.loadUrl("file:///android_asset/appHelp.html");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}