package com.minglang.suiuu.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 关于随游页面
 */
public class AboutSuiuuActivity extends BaseAppCompatActivity {

    @BindColor(R.color.white)
    int titleColor;

    @BindString(R.string.VersionName)
    String versionNameBefore;

    @BindString(R.string.VersionNameFailure)
    String versionNameFailure;

    @Bind(R.id.about_suiuu_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.about_suiuu_version)
    TextView aboutSuiuuVersion;

    @Bind(R.id.about_suiuu_evaluation)
    TextView aboutSuiuuEvaluation;

    @Bind(R.id.about_suiuu_help)
    TextView aboutSuiuuHelp;

    @Bind(R.id.about_suiuu_update)
    TextView aboutSuiuuUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_suiuu);
        ButterKnife.bind(this);
        init();
        viewAction();
    }

    private void init() {
        toolbar.setTitleTextColor(titleColor);
        setSupportActionBar(toolbar);

        try {
            String name = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            aboutSuiuuVersion.setText(versionNameBefore + name);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            aboutSuiuuVersion.setText(versionNameFailure);
        }

    }

    private void viewAction() {
        aboutSuiuuEvaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mAddress = "market://details?id=" + getPackageName();
                Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                marketIntent.setData(Uri.parse(mAddress));
                startActivity(marketIntent);
            }
        });

        aboutSuiuuHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        aboutSuiuuUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about_us, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}