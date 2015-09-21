package com.minglang.suiuu.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
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
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.minglang.suiuu.utils.SuiuuInfo;
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
public class LoginsActivity extends BaseActivity {
    private EditText et_userName;
    private EditText et_passWord;
    private static final String PASSWORD = "password";
    private static final String USER_NAME = "username";
    private static final String TYPE = "type";
    private static final String OPENID = "unionID";
    /**
     * 登陆用户名
     */
    private String loginUserName;

    /**
     * 登陆密码
     */
    private String loginPassword;
    @BindString(R.string.User_name_cannot_be_empty)
    String UserNameNotNull;
    @BindString(R.string.Password_cannot_be_empty)
    String PasswordNotNull;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.iv_suiuu_info_image)
    ImageView iv_suiuu_info_image;
    @Bind(R.id.tv_register)
    TextView tv_register;
    @Bind(R.id.tv_login)
    TextView tv_login;
    @Bind(R.id.tv_quickly_login_name)
    TextView tv_quickly_login_name;
    @Bind(R.id.tv_find_password)
    TextView tv_find_password;
    @Bind(R.id.ll_is_quicky_login)
    LinearLayout ll_is_quicky_login;
    @Bind(R.id.sdv_bind_head_image)
    SimpleDraweeView sdv_bind_head_image;
    private boolean isQuicklyLogin;

    private String openId;
    private String headImage;
    private String nickName;
    private String type;
    private TextProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logins);
        ButterKnife.bind(this);
        isQuicklyLogin =  this.getIntent().getBooleanExtra("isQuicklyLogin",false);
        initView();
        viewAction();
    }


    private void initView() {
        dialog = new TextProgressDialog(this);
        if(isQuicklyLogin) {
            Map<String, String> map = SuiuuInfo.ReadQuicklyLoginInfo(this);
            openId = map.get("openId");
            headImage = map.get("headImage");
            nickName = map.get("nickname");
            type = map.get("type");
            ll_is_quicky_login.setVisibility(View.VISIBLE);
            iv_suiuu_info_image.setVisibility(View.GONE);
            tv_quickly_login_name.setText(nickName);
            Uri uri = Uri.parse(headImage);
            sdv_bind_head_image.setImageURI(uri);
            tv_login.setText("绑 定");
        }
        View user = findViewById(R.id.user);
        View passWord = findViewById(R.id.password);
        et_userName = (EditText) user.findViewById(R.id.et_value);
        et_passWord = (EditText) passWord.findViewById(R.id.et_value);
        et_passWord.setHint(R.string.password);
        et_passWord.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    private void viewAction() {
        iv_back.setOnClickListener(new MyOnClickListener());
        tv_register.setOnClickListener(new MyOnClickListener());
        tv_login.setOnClickListener(new MyOnClickListener());
        tv_find_password.setOnClickListener(new MyOnClickListener());
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_back:
                    finish();
                    break;
                case R.id.tv_register:
                    startActivity(new Intent(LoginsActivity.this,RegisterActivity.class));
                    break;
                case R.id.tv_login:
                    loginUserName = et_userName.getText().toString().trim();
                    loginPassword = et_passWord.getText().toString().trim();

                    if (TextUtils.isEmpty(loginUserName)) {
                        Toast.makeText(LoginsActivity.this, UserNameNotNull, Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(loginPassword)) {
                        Toast.makeText(LoginsActivity.this, PasswordNotNull, Toast.LENGTH_SHORT).show();
                    } else {
                        suiuuLogin(loginUserName, loginPassword);
                    }
                    break;
                case R.id.tv_find_password:
                    Intent intent2ChangePassWord = new Intent(LoginsActivity.this,LoginChangePasswordActivity.class);
                    intent2ChangePassWord.putExtra("isQuicklyLogin",isQuicklyLogin);
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
        dialog.showDialog();
        Map<String,String> map = new HashMap<>();
        map.put(USER_NAME,userName);
        map.put(PASSWORD,password);

       /* OkHttpManager.Params[] paramsArray = new OkHttpManager.Params[]{};
        paramsArray[0] = new OkHttpManager.Params(USER_NAME, userName);
        paramsArray[1] = new OkHttpManager.Params(PASSWORD, password);*/
        if(isQuicklyLogin) {
//            paramsArray[2] = new OkHttpManager.Params(TYPE, type);
//            paramsArray[3] = new OkHttpManager.Params(OPENID, openId);
            map.put(TYPE,type);
            map.put(OPENID,openId);
            try {
                OkHttpManager.onPostAsynRequest(HttpNewServicePath.BindLoginPath,
                        new LoginResultCallback(), map);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                OkHttpManager.onPostAsynRequest(HttpNewServicePath.SelfLoginPath,
                        new LoginResultCallback(), map);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private class LoginResultCallback extends OkHttpManager.ResultCallback<String> {
        @Override
        public void onResponse(String response) {
            dialog.dismissDialog();
            Log.i("suiuu", response);
            try {
                JSONObject json = new JSONObject(response);
                if (json.getInt("status") == 1) {
                    UserBack user = JsonUtils.getInstance().fromJSON(UserBack.class, response);
                    UserBack.UserBackData data = user.getData();
                    SuiuuInfo.WriteVerification(LoginsActivity.this, user.getMessage());
                    SuiuuInfo.WriteUserSign(LoginsActivity.this, data.getUserSign());
                    SuiuuInfo.WriteUserData(LoginsActivity.this, data);
                    startActivity(new Intent(LoginsActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginsActivity.this, json.getString("data"), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            dialog.dismissDialog();
            Toast.makeText(LoginsActivity.this, "登录失败，请稍候再试", Toast.LENGTH_SHORT).show();
        }

    }

}
