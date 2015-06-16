package com.minglang.suiuu.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.utils.DateTimePickDialogUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/6/11 15:08
 * 修改人：Administrator
 * 修改时间：2015/6/11 15:08
 * 修改备注：
 */
public class SuiuuOrderActivity extends BaseActivity {
    private TextView tv_travel_time;
    private String initTime; // 初始化结束时间
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    private ImageView iv_release;
    private TextView tv_enjoy_number;
    private ImageView iv_plus;
    private String titleInfo;
    private String titleImg;
    private SimpleDraweeView iv_suiuu_order_titlePic;
    private TextView tv_suiuu_order_title;
    private ImageView suiuu_order_back;
    private BootstrapButton bb_suiuu_order_pay;
    private Float price;
    private TextView tv_order_price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suiuu_order);
        titleInfo = this.getIntent().getStringExtra("titleInfo");
        titleImg = this.getIntent().getStringExtra("titleImg");
        price = Float.valueOf(this.getIntent().getStringExtra("price"));
        initView();
        viewAction();
    }

    private void initView() {
        initTime = sdf.format(new Date());
        tv_travel_time = (TextView) findViewById(R.id.tv_travel_time);
        tv_travel_time.setText(initTime);
        iv_release = (ImageView) findViewById(R.id.iv_release);
        tv_enjoy_number = (TextView) findViewById(R.id.tv_enjoy_number);
        iv_plus = (ImageView) findViewById(R.id.iv_plus);
        iv_suiuu_order_titlePic = (SimpleDraweeView) findViewById(R.id.iv_suiuu_order_titlePic);
        tv_suiuu_order_title = (TextView) findViewById(R.id.tv_suiuu_order_title);
        suiuu_order_back = (ImageView) findViewById(R.id.suiuu_order_back);
        bb_suiuu_order_pay = (BootstrapButton) findViewById(R.id.bb_suiuu_order_pay);
        tv_order_price = (TextView) findViewById(R.id.tv_order_price);

    }

    private void viewAction() {
        Uri uri = Uri.parse(titleImg);
        iv_suiuu_order_titlePic.setImageURI(uri);
        tv_suiuu_order_title.setText(titleInfo);
        tv_travel_time.setOnClickListener(new MyClick());
        iv_release.setOnClickListener(new MyClick());
        iv_plus.setOnClickListener(new MyClick());
        suiuu_order_back.setOnClickListener(new MyClick());
        bb_suiuu_order_pay.setOnClickListener(new MyClick());
        tv_order_price.setText(Float.toString(price));

    }

    class MyClick implements View.OnClickListener {

        /**
         * @param v
         */
        @Override
        public void onClick(View v) {
            int enjoy_peopleNumber = Integer.valueOf(String.valueOf(tv_enjoy_number.getText()));
            switch (v.getId()) {
                case R.id.tv_travel_time:
                    DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                            SuiuuOrderActivity.this, initTime);
                    dateTimePicKDialog.dateTimePicKDialog(tv_travel_time);
                    break;
                case R.id.iv_release:
                    if (enjoy_peopleNumber != 1) {
                        tv_enjoy_number.setText(String.valueOf(enjoy_peopleNumber - 1));
                        tv_order_price.setText(Float.toString(price * (enjoy_peopleNumber - 1)));

                    }
                    break;
                case R.id.iv_plus:
                    tv_enjoy_number.setText(String.valueOf(enjoy_peopleNumber + 1));
                    tv_order_price.setText(Float.toString(price*(enjoy_peopleNumber + 1)));

                    break;
                case R.id.suiuu_order_back:
                    finish();
                    break;
                case R.id.bb_suiuu_order_pay:
                    Intent intent = new Intent(SuiuuOrderActivity.this,SuiuuPayActivity.class);
                    intent.putExtra("peopleNumber",Integer.toString(enjoy_peopleNumber));
                    intent.putExtra("time",tv_travel_time.getText());
                    intent.putExtra("total_price",tv_order_price.getText());
                    intent.putExtra("destinnation",titleInfo);
                    startActivity(intent);

                    break;
                default:
                    break;
            }
        }
    }
}
