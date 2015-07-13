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
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.customview.FlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/7/2 17:26
 * 修改人：Administrator
 * 修改时间：2015/7/2 17:26
 * 修改备注：
 */
public class SuiuuSearchActivity extends BaseActivity {
    private FlowLayout fl_suiuu_commend_country;
    private FlowLayout fl_suiuu_asia_country;
    private FlowLayout fl_suiuu_european_country;
    private String[] commentList = {"香港", "新加坡"};
    private String[] asiaList = {"台湾", "日本", "马来西亚", "韩国", "泰国"};
    private String[] europeanList = {"法国", "德国", "英国", "荷兰", "瑞士", "意大利", "西班牙", "葡萄牙", "奥地利", "比利时"};
    private List<TextView> list = new ArrayList<>();
    private EditText et_suiuu_search;
    private ImageView iv_top_search;
    private ImageView iv_top_back;
    /**
     * 搜索的类别  1为旅途跳进来 2为随游跳进来
     */
    private int searchClass = 0;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suiuu_search);
        searchClass = this.getIntent().getIntExtra("searchClass",0);
        initView();
        viewAction();
    }

    private void initView() {
        fl_suiuu_commend_country = (FlowLayout) findViewById(R.id.fl_suiuu_commend_country);
        fl_suiuu_asia_country = (FlowLayout) findViewById(R.id.fl_suiuu_asia_country);
        fl_suiuu_european_country = (FlowLayout) findViewById(R.id.fl_suiuu_european_country);
        et_suiuu_search = (EditText) findViewById(R.id.et_suiuu_search);
        iv_top_search = (ImageView) findViewById(R.id.iv_top_search);
        iv_top_back = (ImageView) findViewById(R.id.iv_top_back);
        setCommentGroup();
        setAsiaGroup();
        setEuropeanGroup();


    }

    private void viewAction() {
        iv_top_search.setOnClickListener(new MyOnClick());
        iv_top_back.setOnClickListener(new MyOnClick());
    }

    public void setCommentGroup() {
        fl_suiuu_commend_country.removeAllViews();
        LayoutInflater mInflater = LayoutInflater.from(this);
        for (int i = 0; i < commentList.length; i++) {
            TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                    fl_suiuu_commend_country, false);
            tv.setText(commentList[i]);
            tv.setId(i);
            tv.setOnClickListener(new MyOnClick());
            list.add(tv);
            fl_suiuu_commend_country.addView(tv);

        }
    }

    public void setAsiaGroup() {
        fl_suiuu_asia_country.removeAllViews();
        LayoutInflater mInflater = LayoutInflater.from(this);
        for (int i = 0; i < asiaList.length; i++) {
            TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                    fl_suiuu_asia_country, false);
            tv.setText(asiaList[i]);
            tv.setId(commentList.length + i);
            tv.setOnClickListener(new MyOnClick());
            list.add(tv);
            fl_suiuu_asia_country.addView(tv);

        }
    }

    public void setEuropeanGroup() {
        fl_suiuu_european_country.removeAllViews();
        LayoutInflater mInflater = LayoutInflater.from(this);
        for (int i = 0; i < europeanList.length; i++) {
            TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                    fl_suiuu_european_country, false);
            tv.setText(europeanList[i]);
            tv.setId(commentList.length + asiaList.length + i);
            tv.setOnClickListener(new MyOnClick());
            list.add(tv);
            fl_suiuu_european_country.addView(tv);

        }
    }

    class MyOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int clickId = v.getId();
            Intent intent = new Intent(SuiuuSearchActivity.this, SuiuuSearchDetailActivity.class);
            for (int i = 0; i < list.size(); i++) {
                if (clickId == i) {
                    intent.putExtra("country", list.get(i).getText().toString());
                    startActivity(intent);
                }
            }
            switch (clickId) {
                case R.id.iv_top_back:
                    finish();
                    break;
                case R.id.iv_top_search:
                    if ("".equals(et_suiuu_search.getText().toString().trim())) {
                        Toast.makeText(SuiuuSearchActivity.this, R.string.please_enter_search_content, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    intent.putExtra("country", et_suiuu_search.getText().toString().trim());
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }
}
