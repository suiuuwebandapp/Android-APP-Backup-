package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import butterknife.Bind;
import butterknife.ButterKnife;
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

    @Bind(R.id.fansBack)
    ImageView fansBack;

    @Bind(R.id.fans_head_frame)
    PtrClassicFrameLayout mPtrFrame;

    @Bind(R.id.fansList)
    ListView fansList;

    private ProgressDialog progressDialog;

    private List<FansData> fansDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans);

        ButterKnife.bind(this);

        initView();
        ViewAction();
        getFansData2Service();
    }

    /**
     * 初始化
     */
    private void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.load_wait));
        progressDialog.setCanceledOnTouchOutside(false);

        MaterialHeader header = new MaterialHeader(this);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, Utils.newInstance().dip2px(15, this), 0, Utils.newInstance().dip2px(10, this));
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

    private void showOrHideDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        mPtrFrame.refreshComplete();

    }

    private void bindData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            Toast.makeText(FansActivity.this, getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
        } else {
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
    }

    private class FansRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onStart() {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            String str = stringResponseInfo.result;
            bindData2View(str);
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "获取粉丝列表请求失败的异常信息:" + s);
            showOrHideDialog();
            Toast.makeText(FansActivity.this, getResources().getString(R.string.NetworkAnomaly),
                    Toast.LENGTH_SHORT).show();
        }

    }

}