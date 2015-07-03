package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.customview.FlowLayout;

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
        setViewGroup();


    }

    private void viewAction() {
    }
    public void setViewGroup() {
        fl_suiuu_commend_country.removeAllViews();
        LayoutInflater mInflater = LayoutInflater.from(this);
        for (int i = 0; i < commentList.length; i++) {
            TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                    fl_suiuu_commend_country, false);
            tv.setText(commentList[i]);
            tv.setId(i);
//            tv.setOnClickListener(new MyOnclick());
//            list.add(tv);
            fl_suiuu_commend_country.addView(tv);

        }
    }
}
