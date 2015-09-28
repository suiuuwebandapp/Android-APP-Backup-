package com.minglang.suiuu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.customview.TextProgressDialog;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.BindColor;
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
public class OrderContactInformationActivity extends BaseAppCompatActivity {

    private static final String TAG = OrderContactInformationActivity.class.getSimpleName();

    @Bind(R.id.order_contact_information_toolbar)
    Toolbar toolbar;

    @BindColor(R.color.white)
    int titleColor;

    /**
     * 微信号
     */
    @Bind(R.id.et_we_chat)
    EditText weChatNumber;

    /**
     * 名字
     */
    @Bind(R.id.et_your_name)
    EditText yourName;

    /**
     * 国内手机号
     */
    @Bind(R.id.et_native_phone_phone)
    EditText NativePhoneNumber;

    /**
     * 常用联系号码
     */
    @Bind(R.id.et_standby_phone_number)
    EditText StandbyPhoneNumber;

    /**
     * 实际姓名
     */
    @Bind(R.id.et_actual_name)
    EditText ActualName;

    /**
     * 联系人手机
     */
    @Bind(R.id.et_contact_phone_number)
    EditText inputContactPhoneNumber;

    private String destination;
    private String orderNumber;
    private TextProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_contact_info);
        ButterKnife.bind(this);

        String peopleNumber = this.getIntent().getStringExtra("peopleNumber");
        String time = this.getIntent().getStringExtra("time");
        String total_price = this.getIntent().getStringExtra("total_price");
        destination = this.getIntent().getStringExtra("destination");
        orderNumber = this.getIntent().getStringExtra("orderNumber");

        initView();
    }

    private void initView() {
        token = SuiuuInfo.ReadAppTimeSign(OrderContactInformationActivity.this);

        toolbar.setTitleTextColor(titleColor);
        setSupportActionBar(toolbar);

        dialog = new TextProgressDialog(this);
    }

    private void pushData2Service() {
        dialog.show();
        String userName = yourName.getText().toString().trim();
        String nativePhoneNumber = NativePhoneNumber.getText().toString().trim();
        String urgentName = ActualName.getText().toString().trim();
        String contactPhoneNumber = inputContactPhoneNumber.getText().toString().trim();

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(nativePhoneNumber) || TextUtils.isEmpty(urgentName) || TextUtils.isEmpty(contactPhoneNumber)) {
            dialog.dismiss();
            Toast.makeText(this, "请完善信息", Toast.LENGTH_SHORT).show();
            return;
        }

        String wechatNumber = weChatNumber.getText().toString().trim();
        String sparePhoneNumber = StandbyPhoneNumber.getText().toString().trim();

        Map<String, String> map = new HashMap<>();
        map.put("orderNumber", orderNumber);
        map.put("username", userName);
        map.put("phone", nativePhoneNumber);
        map.put("sparePhone", sparePhoneNumber);
        map.put("wechat", wechatNumber);
        map.put("urgentUsername", urgentName);
        map.put("urgentPhone", contactPhoneNumber);
//        map.put("arriveFlyNumber",);
//        map.put("leaveFlyNumber",);
        map.put("destination", destination);
        try {
            OkHttpManager.onPostAsynRequest(HttpNewServicePath.orderContactInformation + "?token=" + token, new PushInformationCallBack(), map);
        } catch (IOException e) {
            L.e(TAG, "发送数据到服务器异常:" + e.getMessage());
            dialog.dismiss();
        }
    }

    /**
     * 提交信息回调接口
     */
    class PushInformationCallBack extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            int status = 0;
            String data = null;
            try {
                JSONObject json = new JSONObject(response);
                status = (int) json.get("status");
                data = (String) json.get("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (status == 1 && "".equals(data)) {
                Toast.makeText(OrderContactInformationActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OrderContactInformationActivity.this, SuiuuPayActivity.class);
                intent.putExtra("orderNumber", orderNumber);
                startActivity(intent);
            } else {
                Toast.makeText(OrderContactInformationActivity.this, data, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "提交信息请求失败:" + e.getMessage());
            Toast.makeText(OrderContactInformationActivity.this, "提交失败,请重试", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish() {
            dialog.dismiss();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_answer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.confirm_information:
                pushData2Service();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}