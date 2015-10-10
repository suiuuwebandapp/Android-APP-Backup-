package com.minglang.suiuu.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.utils.L;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：三方登录后绑定的主页面
 * 创建人：Administrator
 * 创建时间：2015/9/11 12:42
 * 修改人：Administrator
 * 修改时间：2015/9/11 12:42
 * 修改备注：
 */
public class MainBindActivity extends BaseActivity {

    private static final String TAG = MainBindActivity.class.getSimpleName();

    private static final String IS_QUICKLY_LOGIN = "isQuicklyLogin";

    private static final String HEAD_IMG = "headImg";
    private static final String NICK_NAME = "nickname";
    //private static final String TYPE = "type";
    //private static final String OPEN_ID = "openId";

    private static final String HEAD_IMAGE = "headImage";

    private static final String SEX = "sex";

    @Bind(R.id.iv_bind_back)
    ImageView iv_bind_back;

    @Bind(R.id.second_login_bind_head_image)
    SimpleDraweeView sdv_bind_head_image;

    @Bind(R.id.tv_username)
    TextView tv_username;

    @Bind(R.id.tv_account_exist)
    TextView tv_account_exist;

    @Bind(R.id.tv_join_suiuu)
    TextView tv_join_suiuu;

    private String headImage;
    private String nickName;
    //private String type;
    //private String openId;
    private String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_binding);
        ButterKnife.bind(this);

        headImage = getIntent().getStringExtra(HEAD_IMG);
        nickName = getIntent().getStringExtra(NICK_NAME);
        //type = getIntent().getStringExtra(TYPE);
        //openId = getIntent().getStringExtra(OPEN_ID);
        sex = getIntent().getStringExtra(SEX);

        L.i(TAG, "头像URL:" + headImage + ",性别:" + sex);

        initView();
        viewAction();

    }

    private void initView() {
        if (!TextUtils.isEmpty(headImage)) {
            sdv_bind_head_image.setImageURI(Uri.parse(headImage));
        }

        tv_username.setText(nickName);
    }

    private void viewAction() {
        iv_bind_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_account_exist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainBindActivity.this, SecondLoginActivity.class);
                intent.putExtra(IS_QUICKLY_LOGIN, true);
                //intent.putExtra(HEAD_IMAGE, headImage);
                //intent.putExtra(SEX, sex);
                startActivity(intent);
            }
        });

        tv_join_suiuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainBindActivity.this, RegisterActivity.class);
                intent.putExtra(IS_QUICKLY_LOGIN, true);
                intent.putExtra(HEAD_IMAGE, headImage);
                intent.putExtra(SEX, sex);
                startActivity(intent);
            }
        });

    }

}