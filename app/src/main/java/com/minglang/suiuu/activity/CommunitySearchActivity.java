package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.customview.FlowLayout;
import com.minglang.suiuu.utils.DeBugLog;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 问答社区-搜索
 */
public class CommunitySearchActivity extends BaseAppCompatActivity {

    private static final String TAG = CommunitySearchActivity.class.getSimpleName();

    @Bind(R.id.community_search_layout_back)
    ImageView back;

    @Bind(R.id.community_search_view)
    EditText searchView;

    @Bind(R.id.community_search_btn)
    ImageView searchBtn;

    @Bind(R.id.community_search_tag_layout)
    FlowLayout tagFlowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_search);

        ButterKnife.bind(this);
        ViewAction();
    }

    private void ViewAction() {

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
                DeBugLog.i(TAG, "search info:" + str);

                if (TextUtils.isEmpty(str)) {
                    setResult(RESULT_CANCELED);
                } else {
                    setResult(RESULT_OK);
                }
                finish();
            }
        });

        ArrayList<TextView> textViewList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.template_tag_text_view,
                    tagFlowLayout, false);
            tv.setText("TextView" + i);
            tv.setTag(i);
            tv.setOnClickListener(new TagLayoutOnClick(i));
            textViewList.add(tv);
            tagFlowLayout.addView(tv);
        }
        DeBugLog.i(TAG,"textViewList size:"+textViewList.size());
    }

    private class TagLayoutOnClick implements View.OnClickListener {

        private int tag;

        private TagLayoutOnClick(int tag) {
            this.tag = tag;
        }

        @Override
        public void onClick(View v) {
            int object = (int) v.getTag();
            if (tag == object) {
                Toast.makeText(CommunitySearchActivity.this, "Click Tag:" + tag,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}