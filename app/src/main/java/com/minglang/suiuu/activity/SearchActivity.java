package com.minglang.suiuu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.customview.FlowLayout;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.StatusBarCompat;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/7/2 17:26
 * 修改人：Administrator
 * 修改时间：2015/7/2 17:26
 * 修改备注：
 */
public class SearchActivity extends BaseAppCompatActivity {

    private static final String TAG = SearchActivity.class.getSimpleName();

    private static final String SEARCH_CLASS = "searchClass";
    private static final String COUNTRY = "country";

    @BindString(R.string.please_enter_search_content)
    String searchContent;

    @Bind(R.id.suiuu_commend_country)
    FlowLayout suiuuCommendCountry;

    @Bind(R.id.suiuu_asia_country)
    FlowLayout suiuuAsiaCountry;

    @Bind(R.id.suiuu_european_country)
    FlowLayout suiuuEuropeanCountry;

    private String[] commentList = {"香港", "新加坡"};
    private String[] asiaList = {"台湾", "日本", "马来西亚", "韩国", "泰国"};
    private String[] europeanList = {"法国", "德国", "英国", "荷兰", "瑞士", "意大利", "西班牙", "葡萄牙", "奥地利", "比利时"};

    private List<TextView> list = new ArrayList<>();

    @Bind(R.id.et_suiuu_search)
    EditText inputSearchView;

    @Bind(R.id.iv_top_search)
    ImageView searchClickView;

    @Bind(R.id.iv_top_back)
    ImageView iv_top_back;

    /**
     * 搜索的类别  1为旅途跳进来 2为随游跳进来
     */
    private int searchClass = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suiuu_search);

        StatusBarCompat.compat(this);
        ButterKnife.bind(this);

        searchClass = this.getIntent().getIntExtra(SEARCH_CLASS, -1);

        initView();
        viewAction();
    }

    private void initView() {
        setCommentGroup();
        setAsiaGroup();
        setEuropeanGroup();
    }

    private void viewAction() {
        searchClickView.setOnClickListener(new MyOnClick());
        iv_top_back.setOnClickListener(new MyOnClick());
    }

    public void setCommentGroup() {
        suiuuCommendCountry.removeAllViews();
        LayoutInflater mInflater = LayoutInflater.from(this);
        for (int i = 0; i < commentList.length; i++) {
            TextView tv = (TextView) mInflater.inflate(R.layout.widget_tag_default_text_view, suiuuCommendCountry, false);

            tv.setText(commentList[i]);
            tv.setId(i);

            tv.setOnClickListener(new MyOnClick());
            list.add(tv);

            suiuuCommendCountry.addView(tv);
        }
    }

    public void setAsiaGroup() {
        suiuuAsiaCountry.removeAllViews();
        LayoutInflater mInflater = LayoutInflater.from(this);
        for (int i = 0; i < asiaList.length; i++) {
            TextView tv = (TextView) mInflater.inflate(R.layout.widget_tag_default_text_view, suiuuAsiaCountry, false);

            tv.setText(asiaList[i]);
            tv.setId(commentList.length + i);

            tv.setOnClickListener(new MyOnClick());
            list.add(tv);

            suiuuAsiaCountry.addView(tv);

        }
    }

    public void setEuropeanGroup() {
        suiuuEuropeanCountry.removeAllViews();
        LayoutInflater mInflater = LayoutInflater.from(this);
        for (int i = 0; i < europeanList.length; i++) {
            TextView tv = (TextView) mInflater.inflate(R.layout.widget_tag_default_text_view, suiuuEuropeanCountry, false);
            tv.setText(europeanList[i]);

            tv.setId(commentList.length + asiaList.length + i);
            tv.setOnClickListener(new MyOnClick());

            list.add(tv);
            suiuuEuropeanCountry.addView(tv);
        }
    }

    class MyOnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int clickId = v.getId();
            for (int i = 0; i < list.size(); i++) {
                if (clickId == i) {
                    String clickViewText = list.get(i).getText().toString().trim();
                    L.i(TAG, "clickViewText:" + clickViewText);
                    judgeIntent(clickViewText);
                }
            }

            switch (clickId) {
                case R.id.iv_top_back:
                    finish();
                    break;

                case R.id.iv_top_search:
                    String inputSearchText = inputSearchView.getText().toString().trim();
                    L.i(TAG, "inputSearchText:" + inputSearchText);
                    if ("".equals(inputSearchText)) {
                        Toast.makeText(SearchActivity.this, searchContent, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    judgeIntent(inputSearchText);
                    break;

                default:
                    break;
            }
        }
    }

    public void judgeIntent(String country) {
        if (searchClass == 1) {
            Intent intentGallery = new Intent(SearchActivity.this, TripImageSearchActivity.class);
            intentGallery.putExtra(COUNTRY, country);
            startActivity(intentGallery);
        } else if (searchClass == 2) {
            Intent intentSuiuu = new Intent(SearchActivity.this, SuiuuSearchDetailsActivity.class);
            intentSuiuu.putExtra(COUNTRY, country);
            startActivity(intentSuiuu);
        }
    }

}