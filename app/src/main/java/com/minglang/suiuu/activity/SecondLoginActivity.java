package com.minglang.suiuu.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.customview.TextProgressDialog;
import com.minglang.suiuu.entity.UserBack;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：登录绑定共同界面
 * 创建人：Administrator
 * 创建时间：2015/9/10 15:33
 * 修改人：Administrator
 * 修改时间：2015/9/10 15:33
 * 修改备注：
 */
public class SecondLoginActivity extends BaseActivity {

    private static final String TAG = SecondLoginActivity.class.getSimpleName();

    private static final String PASSWORD = "password";
    private static final String USER_NAME = "username";
    private static final String TYPE = "type";
    private static final String OPENID = "unionID";

    @Bind(R.id.second_login_user_name)
    EditText inputUserName;

    @Bind(R.id.second_login_password)
    EditText inputPassword;

    @BindString(R.string.User_name_cannot_be_empty)
    String UserNameNotNull;

    @BindString(R.string.Password_cannot_be_empty)
    String PasswordNotNull;

    @Bind(R.id.second_login_back)
    ImageView iv_back;

    @Bind(R.id.second_login_logo)
    ImageView iv_suiuu_info_image;

    @Bind(R.id.second_login_register)
    TextView RegisterButton;

    @Bind(R.id.second_login_button)
    TextView LoginButton;

    @Bind(R.id.second_login_name)
    TextView tv_quickly_login_name;

    @Bind(R.id.second_login_find_password)
    TextView tv_find_password;

    @Bind(R.id.second_login_is_login)
    LinearLayout isLoginLayout;

    @Bind(R.id.second_login_bind_head_image)
    SimpleDraweeView bindHeadImage;

    private boolean isQuicklyLogin;

    private String openId;
    private String type;
    private TextProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_login);
        ButterKnife.bind(this);
        isQuicklyLogin = this.getIntent().getBooleanExtra("isQuicklyLogin", false);
        initView();
        viewAction();
    }

    private void initView() {
        dialog = new TextProgressDialog(this);

        if (isQuicklyLogin) {
            Map<String, String> map = SuiuuInfo.ReadQuicklyLoginInfo(this);
            openId = map.get("openId");
            String headImage = map.get("headImage");
            String nickName = map.get("nickname");
            type = map.get("type");

            isLoginLayout.setVisibility(View.VISIBLE);
            iv_suiuu_info_image.setVisibility(View.GONE);

            tv_quickly_login_name.setText(nickName);
            Uri uri = Uri.parse(headImage);
            bindHeadImage.setImageURI(uri);
            LoginButton.setText("绑 定");
        }

    }

    private void viewAction() {
        iv_back.setOnClickListener(new MyOnClickListener());
        RegisterButton.setOnClickListener(new MyOnClickListener());
        LoginButton.setOnClickListener(new MyOnClickListener());
        tv_find_password.setOnClickListener(new MyOnClickListener());
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.second_login_back:
                    finish();
                    break;
                case R.id.second_login_register:
                    startActivity(new Intent(SecondLoginActivity.this, RegisterActivity.class));
                    finish();
                    break;
                case R.id.second_login_button:
                    //登陆用户名
                    String loginUserName = inputUserName.getText().toString().trim();
                    //登陆密码
                    String loginPassword = inputPassword.getText().toString().trim();

                    if (TextUtils.isEmpty(loginUserName)) {
                        Toast.makeText(SecondLoginActivity.this, UserNameNotNull, Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(loginPassword)) {
                        Toast.makeText(SecondLoginActivity.this, PasswordNotNull, Toast.LENGTH_SHORT).show();
                    } else {
                        suiuuLogin(loginUserName, loginPassword);
                    }
                    break;
                case R.id.second_login_find_password:
                    Intent intent2ChangePassWord = new Intent(SecondLoginActivity.this, LoginChangePasswordActivity.class);
                    intent2ChangePassWord.putExtra("isQuicklyLogin", isQuicklyLogin);
                    startActivity(intent2ChangePassWord);
                    break;
            }
        }
    }

    /**
     * 登陆到服务器
     *
     * @param userName 用户名
     * @param password 密码
     */
    private void suiuuLogin(String userName, String password) {
        dialog.show();
        Map<String, String> map = new HashMap<>();
        map.put(USER_NAME, userName);
        map.put(PASSWORD, password);

        if (isQuicklyLogin) {
            map.put(TYPE, type);
            map.put(OPENID, openId);
            try {
                OkHttpManager.onPostAsynRequest(HttpNewServicePath.BindLoginPath, new LoginResultCallback(), map);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                OkHttpManager.onPostAsynRequest(HttpNewServicePath.SelfLoginPath, new LoginResultCallback(), map);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class LoginResultCallback extends OkHttpManager.ResultCallback<String> {
        @Override
        public void onResponse(String response) {
            dialog.dismiss();
            try {
                JSONObject json = new JSONObject(response);
                if (json.getInt("status") == 1) {
                    UserBack user = JsonUtils.getInstance().fromJSON(UserBack.class, response);
                    UserBack.UserBackData data = user.getData();
                    SuiuuInfo.WriteVerification(SecondLoginActivity.this, user.getMessage());
                    SuiuuInfo.WriteUserSign(SecondLoginActivity.this, data.getUserSign());
                    SuiuuInfo.WriteUserData(SecondLoginActivity.this, data);
                    startActivity(new Intent(SecondLoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(SecondLoginActivity.this, json.getString("data"), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                L.e(TAG, "LoginResultCallback Exception" + e.getMessage());
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            dialog.dismiss();
            Toast.makeText(SecondLoginActivity.this, "登录失败，请稍候再试", Toast.LENGTH_SHORT).show();
        }

    }

}
