package com.minglang.suiuu.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.CommentAdapter;
import com.minglang.suiuu.utils.SystemBarTintManager;

/**
 * 评论列表
 */
public class CommentsActivity extends Activity {

    /**
     * 返回键
     */
    private ImageView back;

    /**
     * 评论列表
     */
    private ListView mListView;

    private CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        initView();

        ViewAction();

        mListView.setAdapter(adapter);

    }

    private void ViewAction() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    /**
     * 初始化方法
     */
    private void initView() {

        SystemBarTintManager mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(false);
        mTintManager.setTintColor(getResources().getColor(R.color.tr_black));

        int statusHeight = mTintManager.getConfig().getStatusBarHeight();

        /**
         系统版本是否高于4.4
         */
        boolean isKITKAT = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        if (isKITKAT) {
            RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.CommentRootLayout);
            rootLayout.setPadding(0, statusHeight, 0, 0);
        }

        back = (ImageView) findViewById(R.id.CommentsBack);
        mListView = (ListView) findViewById(R.id.CommentList);

        adapter = new CommentAdapter(this);

    }

}
