package com.minglang.suiuu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 问答社区-搜索
 */
public class CommunitySearchActivity extends BaseAppCompatActivity {

    private static final String SEARCH = "Search";

    @Bind(R.id.community_search_layout_back)
    ImageView back;

    @Bind(R.id.community_search_view)
    EditText searchView;

    @Bind(R.id.community_search_btn)
    ImageView searchBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_search);
        ButterKnife.bind(this);
        viewAction();
    }

    private void viewAction() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = searchView.getText().toString().trim();
                if (TextUtils.isEmpty(str)) {
                    setResult(RESULT_CANCELED);
                } else {
                    Intent intent = new Intent();
                    intent.putExtra(SEARCH, str);
                    setResult(RESULT_OK, intent);
                }
                finish();
            }
        });

    }

}