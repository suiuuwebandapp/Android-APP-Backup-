package com.minglang.suiuu.activity;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.chat.widgt.Sidebar;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInformation;

/**
 * 项目名称：Suiuu
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/5/5 13:52
 * 修改人：Administrator
 * 修改时间：2015/5/5 13:52
 * 修改备注：
 */
public class ChoiceCountryActivity extends Activity {
    private TextView tv_top_right;
    private TextView tv_top_center;
    private ListView listView;
    private Sidebar sidebar;
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_choice_activity);
        initView();
        loadDate();
    }

    private void initView() {
        tv_top_right = (TextView) findViewById(R.id.tv_top_right);
        tv_top_right.setVisibility(View.GONE);
        tv_top_center = (TextView) findViewById(R.id.tv_top_center);
        tv_top_center.setVisibility(View.VISIBLE);
        tv_top_center.setText("国家");
        listView = (ListView) findViewById(R.id.listView);
        sidebar = (Sidebar) findViewById(R.id.sidebar);
        sidebar.setListView(listView);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.progress_bar);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
    }

    /**
     * 加载数据
     */
    private void loadDate() {
        dialog.show();
        String str = SuiuuInformation.ReadVerification(this.getApplicationContext());
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, str);
        SuHttpRequest suHttpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getCountryData, new getSuiuuDateCallBack());
        suHttpRequest.setParams(params);
        suHttpRequest.requestNetworkData();
    }
    /**
     * 获取国家列表的回调接口
     */
    class getSuiuuDateCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {

        }
        @Override
        public void onFailure(HttpException e,String s) {
            dialog.dismiss();
            Toast.makeText(ChoiceCountryActivity.this, "数据获取失败，请重试！", Toast.LENGTH_SHORT).show();
        }
    }
}
