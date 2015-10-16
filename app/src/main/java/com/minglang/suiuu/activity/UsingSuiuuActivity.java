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

    //    @Bind(R.id.using_suiuu_text)
    //    TextView UsingSuiuuText;

    @Bind(R.id.using_help_wab_view)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_using_suiuu);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        //        try {
        //            InputStream is = getAssets().open("UsingSuiuu.txt");
        //            InputStreamReader reader = new InputStreamReader(is);
        //
        //            BufferedReader bufferedReader = new BufferedReader(reader);
        //            StringBuffer buffer = new StringBuffer("");
        //
        //            String str;
        //            while ((str = bufferedReader.readLine()) != null) {
        //                buffer.append(str);
        //                buffer.append("\n");
        //            }
        //
        //            UsingSuiuuText.setText(buffer);
        //
        //        } catch (Exception e) {
        //            L.e(TAG, "文件读取失败:" + e.getMessage());
        //        }

        WebSettings webSettings = webView.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        //webView.setBackgroundColor(Color.TRANSPARENT);
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