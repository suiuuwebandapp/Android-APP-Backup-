package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.FansAdapter;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.entity.Fans;
import com.minglang.suiuu.entity.FansData;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.Utils;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * 粉丝页面
 */
public class FansActivity extends BaseActivity {

    private static final String TAG = FansActivity.class.getSimpleName();

    private ImageView fansBack;

    private PtrClassicFrameLayout mPtrFrame;

    private ListView fansList;

    private ProgressDialog dialog;

    private List<FansData> fansDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans);

        initView();
        ViewAction();
        getData();
    }

    /**
     * 初始化
     */
    private void initView() {
        dialog = new ProgressDialog(this);
        dialog.setMessage(getResources().getString(R.string.load_wait));
        dialog.setCanceledOnTouchOutside(false);

        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.fans_head_frame);

        fansBack = (ImageView) findViewById(R.id.fansBack);
        fansList = (ListView) findViewById(R.id.fansList);

        MaterialHeader header = new MaterialHeader(this);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, Utils.newInstance().dip2px(15,this), 0,Utils.newInstance().dip2px(10, this));
        header.setPtrFrameLayout(mPtrFrame);

        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);
        mPtrFrame.setPinContent(true);

        // the following are default settings
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
    }

    /**
     * 控件数据
     */
    private void ViewAction() {
        fansBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mPtrFrame.setLastUpdateTimeRelateObject(this);

        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, fansList, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                getFansData2Service();
            }
        });

        fansList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String userSign = fansDataList.get(position).getUserSign();
                Intent intent = new Intent(FansActivity.this, OtherUserActivity.class);
                intent.putExtra("userSign", userSign);
                startActivity(intent);
            }
        });

    }

    private void getData() {
        dialog.show();
        getFansData2Service();
    }

    /**
     * 数据请求
     */
    private void getFansData2Service() {
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, verification);

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.FansInformationPath, new FansRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    private class FansRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            mPtrFrame.refreshComplete();

            String str = stringResponseInfo.result;
            try {
                Fans fans = JsonUtils.getInstance().fromJSON(Fans.class, str);
                fansDataList = fans.getData().getData();
                if (fansDataList != null && fansDataList.size() > 0) {
                    FansAdapter adapter = new FansAdapter(FansActivity.this, fansDataList);
                    fansList.setAdapter(adapter);
                } else {
                    Toast.makeText(FansActivity.this, getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "粉丝数据解析错误异常信息:" + e.getMessage());
                Toast.makeText(FansActivity.this, getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            mPtrFrame.refreshComplete();

            DeBugLog.e(TAG, "获取粉丝列表请求失败的异常信息:" + s);
            Toast.makeText(FansActivity.this, getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
        }
    }

}
