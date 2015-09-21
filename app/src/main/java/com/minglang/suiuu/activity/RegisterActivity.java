package com.minglang.suiuu.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.AreaCodeAdapter;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.customview.TextProgressDialog;
import com.minglang.suiuu.entity.AreaCode;
import com.minglang.suiuu.entity.AreaCodeData;
import com.minglang.suiuu.entity.UserBack;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：注册页面
 * 创建人：Administrator
 * 创建时间：2015/9/10 17:23
 * 修改人：Administrator
 * 修改时间：2015/9/10 17:23
 * 修改备注：
 */
public class RegisterActivity extends BaseActivity {
    private TimeCount time;
    @BindString(R.string.InternationalCodeFailure)
    String RepeatAreaCode;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.et_input_phone_number)
    EditText et_input_phone_number;
    @Bind(R.id.tv_get_confirm_number)
    TextView tv_get_confirm_number;
    @Bind(R.id.tv_register)
    TextView tv_register;
    @Bind(R.id.tv_zone)
    TextView tv_zone;
    @Bind(R.id.iv_suiuu_info_head)
    ImageView iv_suiuu_info_head;
    /**
     * 快速登录头部布局
     */
    @Bind(R.id.ll_register_is_quicky_login)
    LinearLayout ll_register_is_quicky_login;
    /**
     * 头像
     */
    @Bind(R.id.sdv_register_bind_head_image)
    SimpleDraweeView sdv_register_bind_head_image;
    /**
     * 名字
     */
    @Bind(R.id.tv_register_quickly_login_name)
    TextView tv_register_quickly_login_name;

    private EditText et_register_user;
    private EditText et_register_password;
    private EditText et_register_confirm_number;
    private static final String AREA_CODE = "areaCode";
    private static final String PHONE = "phone";
    private static final String PASSWORD = "password";
    private static final String C_PASSWORD = "cPassword";
    private static final String NICK = "nick";
    private static final String VALIDATE_CODE = "validateCode";

    private static final String STATUS = "status";

    private static final String USER_NAME = "username";

    /**
     * 国际电话区号数据集合
     */
    private List<AreaCodeData> areaCodeDataList;
    private boolean isQuicklyLogin;

    private String openId;
    private String headImage;
    private String nickName;
    private String type;

    private TextProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        isQuicklyLogin = this.getIntent().getBooleanExtra("isQuicklyLogin",false);
        initView();
        viewAction();
    }

    private void initView() {
        dialog = new TextProgressDialog(this);
        //判断是否为快速登录
        if(isQuicklyLogin) {
            Map<String, String> map = SuiuuInfo.ReadQuicklyLoginInfo(this);
            openId = map.get("openId");
            headImage = map.get("headImage");
            nickName = map.get("nickname");
            type = map.get("type");
            iv_suiuu_info_head.setVisibility(View.GONE);
            ll_register_is_quicky_login.setVisibility(View.VISIBLE);
            sdv_register_bind_head_image.setImageURI(Uri.parse(headImage));
            tv_register_quickly_login_name.setText(nickName);
        }
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
        View register_user = findViewById(R.id.register_user);
        View register_password = findViewById(R.id.register_password);
        View register_confirm_number = findViewById(R.id.register_confirm_number);
        et_register_user = (EditText) register_user.findViewById(R.id.et_value);
        et_register_user.setHint(R.string.NickName);
        et_register_password = (EditText) register_password.findViewById(R.id.et_value);
        et_register_password.setHint(R.string.password);
        et_register_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        et_register_confirm_number = (EditText) register_confirm_number.findViewById(R.id.et_value);
        et_register_confirm_number.setHint(R.string.please_input_obtain_captcha);
    }

    private void viewAction() {
        tv_register.setOnClickListener(new MyOnClickListener());
        tv_get_confirm_number.setOnClickListener(new MyOnClickListener());
        tv_zone.setOnClickListener(new MyOnClickListener());
        iv_back.setOnClickListener(new MyOnClickListener());
    }

    private class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //注册按钮
                case R.id.tv_register:
                    registerService();
                    break;
                //获取验证码
                case R.id.tv_get_confirm_number:
                    setPhoneNumber4Service();
                    break;

                case R.id.tv_zone:
                    getAreaCode();
                    break;
                case R.id.iv_back:
                    finish();
                    break;
            }
        }
    }

    /**
     * 注册
     */
    public void registerService() {
        dialog.showDialog();
        String user = et_register_user.getText().toString().trim();
        String passWord = et_register_password.getText().toString().trim();
        String phoneNumber = et_input_phone_number.getText().toString().trim();
        String confirmNumber = et_register_confirm_number.getText().toString().trim();

        if (TextUtils.isEmpty(confirmNumber)) {
            Toast.makeText(RegisterActivity.this, "验证码不能为空！", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(passWord)) {
            Toast.makeText(RegisterActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(user)) {
            Toast.makeText(RegisterActivity.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(RegisterActivity.this, "电话号码不能为空！", Toast.LENGTH_SHORT).show();
        } else {
            SuiuuInfo.ClearSuiuuInfo(RegisterActivity.this);
        }
        if(isQuicklyLogin) {
            Map<String,String> map = new HashMap<>();
            map.put("code",confirmNumber);
            map.put(PHONE,phoneNumber);
            map.put(PASSWORD,passWord);
            map.put("nickname",user);
            map.put(AREA_CODE,tv_zone.getText().toString().trim());
            map.put("type",type);
            map.put("unionID",openId);
            try {
                OkHttpManager.onPostAsynRequest(HttpNewServicePath.AccessRegister,
                        new RegisterResultCallback(), map);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {
            OkHttpManager.Params[] paramsArray = new OkHttpManager.Params[5];
            paramsArray[0] = new OkHttpManager.Params(PHONE, phoneNumber);
            paramsArray[1] = new OkHttpManager.Params(PASSWORD, passWord);
            paramsArray[2] = new OkHttpManager.Params(C_PASSWORD, passWord);
            paramsArray[3] = new OkHttpManager.Params(NICK, user);
            paramsArray[4] = new OkHttpManager.Params(VALIDATE_CODE, confirmNumber);
            try {
                OkHttpManager.onPostAsynRequest(HttpNewServicePath.Register4SuiuuPath,
                        new RegisterResultCallback(), paramsArray);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 把国际电话区号和手机号发送到服务器
     */
    private void setPhoneNumber4Service() {
        String zone = tv_zone.getText().toString().trim();
        String phoneNumber = et_input_phone_number.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(RegisterActivity.this, "电话号码不能为空！", Toast.LENGTH_SHORT).show();
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

    /**
     * 获取国际电话区号
     */
    private void getAreaCode() {
        try {
            OkHttpManager.onGetAsynRequest(HttpNewServicePath.getAreaCodeDataPath, new AreaCodeResultCallback());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class AreaCodeResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onError(Request request, Exception e) {
            Toast.makeText(RegisterActivity.this, RepeatAreaCode, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            try {
                AreaCode areaCode = JsonUtils.getInstance().fromJSON(AreaCode.class, response);
                areaCodeDataList = areaCode.getData();
                if (areaCodeDataList != null && areaCodeDataList.size() > 0) {
                    showAreaDialog();
                } else {
                    Toast.makeText(RegisterActivity.this, RepeatAreaCode, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
            }
        }

    }

    public void showAreaDialog() {
        AreaCodeAdapter areaCodeAdapter;
        final AlertDialog setTagDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.dialog_choice_area_code, null);
        ListView lv_choice_country = (ListView) view.findViewById(R.id.lv_choice_country);
        areaCodeAdapter = new AreaCodeAdapter(this);
        areaCodeAdapter.setList(areaCodeDataList);
        areaCodeAdapter.setZhCNLanguage(isZhCnLanguage);
        lv_choice_country.setAdapter(areaCodeAdapter);
        setTagDialog = builder.create();
        setTagDialog.setView(view, 0, 0, 0, 0);
        setTagDialog.show();
        lv_choice_country.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv_zone.setText(areaCodeDataList.get(position).getAreaCode());
                setTagDialog.dismiss();
            }
        });

    }

    private class RegisterResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            dialog.dismissDialog();
            Log.i("suiuu", response);
            try {
                UserBack user = JsonUtils.getInstance().fromJSON(UserBack.class, response);
                if (user.getStatus().equals("1")) {

                    if(isQuicklyLogin) {
                        UserBack.UserBackData data = user.getData();
                        SuiuuInfo.WriteVerification(RegisterActivity.this, user.getMessage());
                        SuiuuInfo.WriteUserSign(RegisterActivity.this, user.getData().getUserSign());
                        SuiuuInfo.WriteUserData(RegisterActivity.this, data);
                        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                    }else {
                        startActivity(new Intent(RegisterActivity.this, LoginsActivity.class));
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败，请稍候再试！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            dialog.dismissDialog();
            Toast.makeText(RegisterActivity.this, "注册失败，请检查网络后再试！", Toast.LENGTH_SHORT).show();
        }

    }

    private class PhoneNumberResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onError(Request request, Exception e) {
            Toast.makeText(RegisterActivity.this, "发送失败，请重试！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            try {
                JSONObject object = new JSONObject(response);
                String status = object.getString(STATUS);
                if ("1".equals(status.trim())) {
//                    popupWindowRegister2.showAtLocation(popupRegisterView2, Gravity.CENTER_HORIZONTAL, 0, 0);
                    Toast.makeText(RegisterActivity.this, "发送成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "发送失败，请检查手机号码是否填写正确！", Toast.LENGTH_SHORT).show();
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
            tv_get_confirm_number.setText("重新验证");
            tv_get_confirm_number.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            tv_get_confirm_number.setClickable(false);
            tv_get_confirm_number.setText(millisUntilFinished / 1000 + "秒");
        }
    }
}
