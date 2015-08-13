package com.minglang.suiuu.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.BaseHolderAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;

public class AboutUsActivity extends BaseAppCompatActivity {

    @Bind(R.id.about_us_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.about_us_list_view)
    ListView listView;

    @BindString(R.string.AboutUs)
    String title;

    @BindColor(R.color.white)
    int titleColor;

    @BindString(R.string.VersionNumber)
    String str1;

    @BindString(R.string.UpdateInfo)
    String str2;

    @BindString(R.string.FeedbackInfo)
    String str3;

    private List<String> infoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        init();
        viewAction();
    }

    private void init() {
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(titleColor);

        setSupportActionBar(toolbar);

        String version = "";
        try {
            PackageInfo packInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        infoList.add(version);
        infoList.add("");
        infoList.add("");

        listView.setAdapter(new BaseHolderAdapter<String>(this, infoList, R.layout.item_about_us) {

            @Override
            public void convert(ViewHolder helper, String item, long position) {
                TextView titleView = helper.getView(R.id.item_about_us_title);
                TextView contentView = helper.getView(R.id.item_about_us_content);

                switch ((int) position) {
                    case 0:
                        titleView.setText(str1);
                        contentView.setVisibility(View.VISIBLE);
                        titleView.setGravity(Gravity.END);
                        break;
                    case 1:
                        titleView.setText(str2);
                        titleView.setGravity(Gravity.CENTER_HORIZONTAL);
                        contentView.setVisibility(View.GONE);
                        break;
                    case 2:
                        titleView.setText(str3);
                        titleView.setGravity(Gravity.CENTER_HORIZONTAL);
                        contentView.setVisibility(View.GONE);
                        break;
                }

                contentView.setText(item);
            }
        });
    }

    private void viewAction() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        break;

                    case 2:
                        startActivity(new Intent(AboutUsActivity.this, FeedbackActivity.class));
                        break;
                }
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
