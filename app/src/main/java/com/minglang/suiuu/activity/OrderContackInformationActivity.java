package com.minglang.suiuu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.customview.TextProgressDialog;
import com.minglang.suiuu.utils.HttpNewServicePath;
import com.minglang.suiuu.utils.OkHttpManager;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：订单联系人信息页面
 * 创建人：Administrator
 * 创建时间：2015/9/17 10:29
 * 修改人：Administrator
 * 修改时间：2015/9/17 10:29
 * 修改备注：
 */
public class OrderContackInformationActivity extends BaseActivity {
    @Bind(R.id.iv_top_back)
    ImageView iv_top_back;
    @Bind(R.id.tv_top_right_more)
    ImageView tv_top_right_more;
    @Bind(R.id.tv_top_center)
    TextView tv_top_center;
    /**
     * 微信号
     */
    @Bind(R.id.et_we_chat)
    EditText et_we_chat;
    /**
     * 名字
     */
    @Bind(R.id.et_your_name)
    EditText et_your_name;
    /**
     * 国内手机号
     */
    @Bind(R.id.et_native_phone_phone)
    EditText et_native_phone_phone;
    /**
     * 常用联系号码
     */
    @Bind(R.id.et_standby_phone_number)
    EditText et_standby_phone_number;
    /**
     * 实际姓名
     */
    @Bind(R.id.et_actual_name)
    EditText et_actual_name;
    /**
     * 联系人手机
     */
    @Bind(R.id.et_contack_phone_number)
    EditText et_contack_phone_number;
    /**
     * 确认按钮
     */
    @Bind(R.id.tv_confirm_btn)
    TextView tv_confirm_btn;

    private String peopleNumber;
    private String time;
    private String total_price;
    private String destination;
    private String orderNumber;
    private TextProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_contact_info);
        ButterKnife.bind(this);
        peopleNumber = this.getIntent().getStringExtra("peopleNumber");
        time = this.getIntent().getStringExtra("time");
        total_price = this.getIntent().getStringExtra("total_price");
        destination = this.getIntent().getStringExtra("destination");
        orderNumber = this.getIntent().getStringExtra("orderNumber");
        initView();
        viewAction();
    }

    private void initView() {
        tv_top_center.setText("个人信息完整");
        tv_top_right_more.setVisibility(View.GONE);
        dialog = new TextProgressDialog(this);
    }

    private void viewAction() {
        iv_top_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushData2Service();
            }
        });
    }

    private void pushData2Service() {
        dialog.showDialog();
        String userName = et_your_name.getText().toString().trim();
        String nativePhoneNumber = et_native_phone_phone.getText().toString().trim();
        String urgentName = et_actual_name.getText().toString().trim();
        String contactPhoneNumber = et_contack_phone_number.getText().toString().trim();
        if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(nativePhoneNumber) || TextUtils.isEmpty(urgentName) || TextUtils.isEmpty(contactPhoneNumber)) {
            Toast.makeText(this,"请完善信息",Toast.LENGTH_SHORT).show();
            return;
        }
        String wechatNumber = et_we_chat.getText().toString().trim();
        String sparePhoneNumber = et_standby_phone_number.getText().toString().trim();
        Map<String,String> map = new HashMap<>();
        map.put("orderNumber",orderNumber);
        map.put("username",userName);
        map.put("phone",nativePhoneNumber);
        map.put("sparePhone",sparePhoneNumber);
        map.put("wechat",wechatNumber);
        map.put("urgentUsername",urgentName);
        map.put("urgentPhone",contactPhoneNumber);
//        map.put("arriveFlyNumber",);
//        map.put("leaveFlyNumber",);
        map.put("destination",destination);
        try {
            OkHttpManager.onPostAsynRequest(HttpNewServicePath.orderContactInformation + "?token=" + SuiuuInfo.ReadAppTimeSign(OrderContackInformationActivity.this), new PushInformationCallBack(), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 提交信息回调接口
     */
    class PushInformationCallBack extends OkHttpManager.ResultCallback<String> {
        @Override
        public void onError(Request request, Exception e) {
            dialog.dismissDialog();
            Toast.makeText(OrderContackInformationActivity.this, "提交失败,请重试", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            dialog.dismissDialog();
            int status = 0;
            String data = null;
            try {
                JSONObject json = new JSONObject(response);
                status = (int) json.get("status");
                data = (String) json.get("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(status == 1 && "".equals(data)) {
                Toast.makeText(OrderContackInformationActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OrderContackInformationActivity.this, SuiuuPayActivity.class);
                intent.putExtra("orderNumber", orderNumber);
                startActivity(intent);
            }else {
                Toast.makeText(OrderContackInformationActivity.this, data, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
