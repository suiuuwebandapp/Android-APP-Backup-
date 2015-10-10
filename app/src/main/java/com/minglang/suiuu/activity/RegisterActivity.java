package com.minglang.suiuu.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
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
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.OkHttpManager;
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

    private static final String TAG = RegisterActivity.class.getSimpleName();

    private static final String AREA_CODE = "areaCode";
    private static final String PHONE = "phone";
    private static final String PASSWORD = "password";
    private static final String C_PASSWORD = "cPassword";
    private static final String NICK = "nick";
    private static final String VALIDATE_CODE = "validateCode";

    private static final String STATUS = "status";

    private static final String CODE = "code";

    private static final String OPEN_ID = "openId";
    private static final String HEAD_IMAGE = "headImage";
    private static final String NICK_NAME = "nickname";
    private static final String TYPE = "type";

    private static final String UNION_ID = "unionID";

    private static final String SEX = "sex";

    private static final String HEAD_IMG = "headImg";

    private TimeCount time;

    @BindString(R.string.NoData)
    String NoData;

    @BindString(R.string.InternationalCodeFailure)
    String RepeatAreaCode;

    @BindString(R.string.DataError)
    String DataError;

    @BindString(R.string.SystemException)
    String SystemException;

    @BindString(R.string.NickName)
    String NickName;

    @BindString(R.string.password)
    String Password;

    @BindString(R.string.please_input_obtain_captcha)
    String inputObtainCaptcha;

    @Bind(R.id.second_login_back)
    ImageView back;

    @Bind(R.id.et_input_phone_number)
    EditText inputPhoneNumberEdit;

    @Bind(R.id.tv_get_confirm_number)
    TextView getConfirmNumber;

    @Bind(R.id.second_login_register)
    TextView registerButton;

    @Bind(R.id.tv_zone)
    TextView tv_zone;

    @Bind(R.id.iv_suiuu_info_head)
    ImageView suiuuInfoHead;

    @Bind(R.id.register_user)
    View registerUserView;

    @Bind(R.id.register_password)
    View registerPasswordView;

    @Bind(R.id.register_confirm_number)
    View registerConfirmNumberView;

    /**
     * 快速登录头部布局
     */
    @Bind(R.id.register_is_quick_login)
    LinearLayout registerIsQuickLoginLayout;

    /**
     * 头像
     */
    @Bind(R.id.sdv_register_bind_head_image)
    SimpleDraweeView registerBindHeadImage;

    /**
     * 名字
     */
    @Bind(R.id.register_quick_login_name)
    TextView registerQuicklyLoginName;
    private EditText registerUserEdit;

    private EditText registerPasswordEdit;

    private EditText registerConfirmNumberEdit;

    /**
     * 国际电话区号数据集合
     */
    private List<AreaCodeData> areaCodeDataList;

    private boolean isQuicklyLogin;

    private String headImagePath;
    private String sex;

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

        isQuicklyLogin = getIntent().getBooleanExtra("isQuicklyLogin", false);

        if (isQuicklyLogin) {
            headImagePath = getIntent().getStringExtra(HEAD_IMAGE);
            sex = getIntent().getStringExtra(SEX);
            L.i(TAG, "头像URL:" + headImagePath + ",性别:" + sex);
        }

        initView();
        viewAction();
    }

    private void initView() {
        dialog = new TextProgressDialog(this);

        //判断是否为快速登录
        if (isQuicklyLogin) {
            Map<String, String> map = SuiuuInfo.ReadQuicklyLoginInfo(this);
            openId = map.get(OPEN_ID);
            headImage = map.get(HEAD_IMAGE);
            nickName = map.get(NICK_NAME);
            type = map.get(TYPE);

            suiuuInfoHead.setVisibility(View.GONE);
            registerIsQuickLoginLayout.setVisibility(View.VISIBLE);
            registerBindHeadImage.setImageURI(Uri.parse(headImage));
            registerQuicklyLoginName.setText(nickName);
        }

        time = new TimeCount(60000, 1000);//构造CountDownTimer对象

        registerUserEdit = (EditText) registerUserView.findViewById(R.id.et_value);
        registerUserEdit.setHint(NickName);

        registerPasswordEdit = (EditText) registerPasswordView.findViewById(R.id.et_value);
        registerPasswordEdit.setHint(Password);
        registerPasswordEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        registerConfirmNumberEdit = (EditText) registerConfirmNumberView.findViewById(R.id.et_value);
        registerConfirmNumberEdit.setHint(inputObtainCaptcha);
    }

    private void viewAction() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerService();
            }
        });

        getConfirmNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPhoneNumber4Service();
            }
        });

        tv_zone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAreaCode();
            }
        });

    }

    /**
     * 注册
     */
    public void registerService() {
        dialog.show();

        String user = registerUserEdit.getText().toString().trim();
        String passWord = registerPasswordEdit.getText().toString().trim();
        String phoneNumber = inputPhoneNumberEdit.getText().toString().trim();
        String confirmNumber = registerConfirmNumberEdit.getText().toString().trim();

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

        if (isQuicklyLogin) {
            Map<String, String> map = new HashMap<>();
            map.put(CODE, confirmNumber);
            map.put(PHONE, phoneNumber);
            map.put(PASSWORD, passWord);
            map.put(NICK_NAME, user);
            map.put(AREA_CODE, tv_zone.getText().toString().trim());
            map.put(TYPE, type);
            map.put(UNION_ID, openId);
            map.put(SEX,sex);
            map.put(HEAD_IMG, headImagePath);

            try {
                OkHttpManager.onPostAsynRequest(HttpNewServicePath.AccessRegister, new RegisterResultCallback(), map);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            OkHttpManager.Params[] paramsArray = new OkHttpManager.Params[5];
            paramsArray[0] = new OkHttpManager.Params(PHONE, phoneNumber);
            paramsArray[1] = new OkHttpManager.Params(PASSWORD, passWord);
            paramsArray[2] = new OkHttpManager.Params(C_PASSWORD, passWord);
            paramsArray[3] = new OkHttpManager.Params(NICK, user);
            paramsArray[4] = new OkHttpManager.Params(VALIDATE_CODE, confirmNumber);

            try {
                OkHttpManager.onPostAsynRequest(HttpNewServicePath.Register4SuiuuPath, new RegisterResultCallback(), paramsArray);
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
        String phoneNumber = inputPhoneNumberEdit.getText().toString().trim();

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
            OkHttpManager.onPostAsynRequest(HttpNewServicePath.SendAreaCodeAndPhoneNumber, new PhoneNumberResultCallback(), paramsArray);
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
            L.e(TAG, "国际电话区号数据请求异常:" + e.getMessage());
        }
    }

    private class AreaCodeResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "国际电话区号数据获取异常:" + e.getMessage());
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
                L.e(TAG, "国际电话区号数据解析失败:" + e.getMessage());
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

    private void AbnormalHandle(String AbnormalField, String showInfo) {
        try {
            JSONObject object = new JSONObject(AbnormalField);
            String status = object.getString(STATUS);
            String data = object.getString(DATA);
            switch (status) {
                case "-1":
                    Toast.makeText(this, TextUtils.isEmpty(data) ? SystemException : data, Toast.LENGTH_SHORT).show();
                    break;
                case "-2":
                    Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, showInfo, Toast.LENGTH_SHORT).show();
        }
    }

    private class RegisterResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            L.i(TAG, "注册返回数据:" + response);
            if (TextUtils.isEmpty(response)) {
                Toast.makeText(RegisterActivity.this, NoData, Toast.LENGTH_SHORT).show();
            } else try {
                UserBack user = JsonUtils.getInstance().fromJSON(UserBack.class, response);
                String status = user.getStatus();
                switch (status) {
                    case "1":
                        if (isQuicklyLogin) {
                            UserBack.UserBackData data = user.getData();
                            SuiuuInfo.WriteVerification(RegisterActivity.this, user.getMessage());
                            SuiuuInfo.WriteUserSign(RegisterActivity.this, user.getData().getUserSign());
                            SuiuuInfo.WriteUserData(RegisterActivity.this, data);
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        } else {
                            startActivity(new Intent(RegisterActivity.this, SecondLoginActivity.class));
                        }
                        break;

                    case "-1":
                        Toast.makeText(RegisterActivity.this, SystemException, Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        Toast.makeText(RegisterActivity.this, "注册失败，请稍候再试！", Toast.LENGTH_SHORT).show();
                        break;
                }
            } catch (Exception e) {
                L.e(TAG, "注册数据解析错误:" + e.getMessage());
                AbnormalHandle(response, DataError);

            }
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "注册网络请求异常:" + e.getMessage());
            Toast.makeText(RegisterActivity.this, "注册失败，请检查网络后再试！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish() {
            dialog.dismiss();
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
                    Toast.makeText(RegisterActivity.this, "发送成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "发送失败，请检查手机号码是否填写正确！", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            getConfirmNumber.setText("重新验证");
            getConfirmNumber.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            getConfirmNumber.setClickable(false);
            getConfirmNumber.setText(String.format("%s%s", millisUntilFinished / 1000, "秒"));
        }
    }

}