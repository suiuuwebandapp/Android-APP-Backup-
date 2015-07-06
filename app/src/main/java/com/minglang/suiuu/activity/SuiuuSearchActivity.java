package com.minglang.suiuu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
    private String[] commentList = {"香港","新加坡"};
    private String[] asiaList = {"台湾","日本","马来西亚","韩国","泰国"};
    private String[] europeanList = {"法国","德国","英国","荷兰","瑞士","意大利","西班牙","葡萄牙","奥地利","比利时"};
    private List<TextView> list = new ArrayList<>();
    private EditText et_suiuu_search;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suiuu_search);
        initView();
        viewAction();
    }

    private void initView() {
        fl_suiuu_commend_country = (FlowLayout) findViewById(R.id.fl_suiuu_commend_country);
        fl_suiuu_asia_country = (FlowLayout) findViewById(R.id.fl_suiuu_asia_country);
        fl_suiuu_european_country = (FlowLayout) findViewById(R.id.fl_suiuu_european_country);
        et_suiuu_search = (EditText) findViewById(R.id.et_suiuu_search);
        setCommentGroup();
        setAsiaGroup();
        setEuropeanGroup();


    }

    private void viewAction() {
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
            tv.setId(commentList.length+i);
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
            tv.setId(commentList.length+asiaList.length + i);
            tv.setOnClickListener(new MyOnClick());
            list.add(tv);
            fl_suiuu_european_country.addView(tv);

        }
    }
    class MyOnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            for (int i = 0; i < list.size(); i++) {
                if (v.getId() == i) {
//                        list.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.tv_bg));
                    Intent intent = new Intent(SuiuuSearchActivity.this,SuiuuSearchDetailActivity.class);
                    intent.putExtra("country",list.get(i).getText().toString());
                    startActivity(intent);
                }
            }
        }
    }
}
