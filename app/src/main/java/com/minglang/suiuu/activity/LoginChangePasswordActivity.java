package com.minglang.suiuu.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
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
import com.minglang.suiuu.utils.HttpNewServicePath;
import com.minglang.suiuu.utils.OkHttpManager;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：修改密码页面
 * 创建人：Administrator
 * 创建时间：2015/9/14 9:19
 * 修改人：Administrator
 * 修改时间：2015/9/14 9:19
 * 修改备注：
 */
public class LoginChangePasswordActivity extends BaseActivity {
    private TimeCount time;
    /**
     * 是否是三方登录绑定页面进来
     */
    private boolean isQuicklyLogin;
    /**
     * 返回键
     */
    @Bind(R.id.iv_change_password_back)
    ImageView iv_change_password_back;
    /**
     * 随游默认头像
     */
    @Bind(R.id.iv_change_password_suiuu_info_head)
    ImageView iv_change_password_suiuu_info_head;
    /**
     * 显示三方登录绑定页面进来的页面
     */
    @Bind(R.id.ll_change_password_is_quicky_login)
    LinearLayout ll_change_password_is_quicky_login;
    /**
     * 显示三方登录绑定页面进来的头像
     */
    @Bind(R.id.sdv_change_password_bind_head_image)
    SimpleDraweeView sdv_change_password_bind_head_image;
    /**
     * 显示三方登录绑定页面进来的名字
     */
    @Bind(R.id.tv_change_password_quickly_login_name)
    TextView tv_change_password_quickly_login_name;
    /**
     * 选择地区下拉框
     */
    @Bind(R.id.tv_change_password_zone)
    TextView tv_change_password_zone;
    /**
     * 确认修改密码按钮
     */
    @Bind(R.id.tv_confirm_change_password)
    TextView tv_confirm_change_password;
    /**
     * 获取验证码按钮
     */
    @Bind(R.id.tv_change_password_get_confirm_number)
    TextView tv_change_password_get_confirm_number;
    /**
     * 输入电话号码
     */
    @Bind(R.id.et_change_password_input_phone_number)
    EditText et_change_password_input_phone_number;
    /**
     * 验证码
     */
    private EditText et_verification_code;
    /**
     * 修改的密码
     */
    private EditText et_change_passWord;

    private static final String AREA_CODE = "areaCode";
    private static final String PHONE = "phone";
    private static final String STATUS = "status";

    private String openId;
    private String headImage;
    private String nickName;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_change_password);
        ButterKnife.bind(this);
        isQuicklyLogin = this.getIntent().getBooleanExtra("isQuicklyLogin", false);
        initView();
        viewAction();
    }

    private void initView() {
        //判断是否为快速登录
        if(isQuicklyLogin) {
            Map<String, String> map = SuiuuInfo.ReadQuicklyLoginInfo(this);
            openId = map.get("openId");
            headImage = map.get("headImage");
            nickName = map.get("nickname");
            type = map.get("type");
            iv_change_password_suiuu_info_head.setVisibility(View.GONE);
            ll_change_password_is_quicky_login.setVisibility(View.VISIBLE);
            sdv_change_password_bind_head_image.setImageURI(Uri.parse(headImage));
            tv_change_password_quickly_login_name.setText(nickName);
        }


        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
        View user = findViewById(R.id.change_password_verification_code);
        View passWord = findViewById(R.id.change_password_number);
        et_verification_code = (EditText) user.findViewById(R.id.et_value);
        et_change_passWord = (EditText) passWord.findViewById(R.id.et_value);
        et_verification_code.setHint(R.string.please_input_obtain_captcha);
        et_change_passWord.setHint(R.string.please_input_password);
        et_change_passWord.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    private void viewAction() {
        iv_change_password_back.setOnClickListener(new MyOnClickListener());
        tv_change_password_get_confirm_number.setOnClickListener(new MyOnClickListener());
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_change_password_back:
                    finish();
                    break;
                case R.id.tv_change_password_get_confirm_number:
                    setPhoneNumber4Service();
                    break;


            }
        }
    }
    /**
     * 把国际电话区号和手机号发送到服务器
     */
    private void setPhoneNumber4Service() {
        String zone = tv_change_password_zone.getText().toString().trim();
        String phoneNumber = et_change_password_input_phone_number.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(LoginChangePasswordActivity.this, "电话号码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        } else {
            time.start();//开始计时
        }
        OkHttpManager.Params[] paramsArray = new OkHttpManager.Params[2];
        paramsArray[0] = new OkHttpManager.Params(AREA_CODE, zone);
        paramsArray[1] = new OkHttpManager.Params(PHONE, phoneNumber);
        try {
            OkHttpManager.onPostAsynRequest(HttpNewServicePath.SendAreaCodeAndPhoneNumber,
                    new PhoneNumberResultCallback(), paramsArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private class PhoneNumberResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onError(Request request, Exception e) {
            Toast.makeText(LoginChangePasswordActivity.this, "发送失败，请重试！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            try {
                JSONObject object = new JSONObject(response);
                String status = object.getString(STATUS);
                if ("1".equals(status.trim())) {
//                    popupWindowRegister2.showAtLocation(popupRegisterView2, Gravity.CENTER_HORIZONTAL, 0, 0);
                    Toast.makeText(LoginChangePasswordActivity.this, "发送成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginChangePasswordActivity.this, "发送失败，请检查手机号码是否填写正确！", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            tv_change_password_get_confirm_number.setText("重新验证");
            tv_change_password_get_confirm_number.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            tv_change_password_get_confirm_number.setClickable(false);
            tv_change_password_get_confirm_number.setText(millisUntilFinished / 1000 + "秒");
        }
    }
}