package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;

public class PrivateLetterChatActivity extends BaseAppCompatActivity {

    @BindColor(R.color.white)
    int titleColor;

    @Bind(R.id.private_letter_details_tool_bar)
    Toolbar toolBar;

    @Bind(R.id.private_letter_details_recycler_view)
    RecyclerView letterDetailsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_letter_chat);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        toolBar.setTitleTextColor(titleColor);
        setSupportActionBar(toolBar);
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
