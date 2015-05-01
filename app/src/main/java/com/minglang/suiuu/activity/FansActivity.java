package com.minglang.suiuu.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.FansAdapter;
import com.minglang.suiuu.entity.Fans;
import com.minglang.suiuu.entity.FansData;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtil;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInformation;
import com.minglang.suiuu.utils.SystemBarTintManager;

import java.util.List;

import in.srain.cube.util.LocalDisplay;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * 粉丝页面
 */
public class FansActivity extends Activity {

    private static final String TAG = FansActivity.class.getSimpleName();

    private ImageView fansBack;

    private PtrClassicFrameLayout mPtrFrame;

    private MaterialHeader header;

    private ListView fansList;

    private ProgressDialog dialog;

    private List<FansData> fansDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans);

        initView();
        ViewAction();

        dialog.show();
        getFansData2Service();

    }

    private void getFansData2Service() {

        String msg = SuiuuInformation.ReadVerification(this);

        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, msg);

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.FansInformationPath, new FansRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
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
                intent.putExtra("usersign", userSign);
                startActivity(intent);
            }
        });

    }

    /**
     * 初始化
     */
    private void initView() {

        SystemBarTintManager mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(false);
        mTintManager.setTintColor(getResources().getColor(R.color.tr_black));

        int statusHeight = mTintManager.getConfig().getStatusBarHeight();

        boolean isKITKAT = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKITKAT) {
            RelativeLayout fansRootLayout = (RelativeLayout) findViewById(R.id.fansRootLayout);
            fansRootLayout.setPadding(0, statusHeight, 0, 0);
        }

        dialog = new ProgressDialog(this);
        dialog.setMessage(getResources().getString(R.string.load_wait));
        dialog.setCanceledOnTouchOutside(false);

        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.fans_head_frame);

        fansBack = (ImageView) findViewById(R.id.fansBack);
        fansList = (ListView) findViewById(R.id.fansList);

        header = new MaterialHeader(this);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, LocalDisplay.dp2px(15), 0, LocalDisplay.dp2px(10));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
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
                Fans fans = JsonUtil.getInstance().fromJSON(Fans.class, str);
                fansDataList = fans.getData();
                FansAdapter adapter = new FansAdapter(FansActivity.this, fansDataList);
                fansList.setAdapter(adapter);
            } catch (Exception e) {
                Log.e(TAG, "粉丝数据解析错误异常信息:" + e.getMessage());
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            mPtrFrame.refreshComplete();

            Log.e(TAG, "获取粉丝列表请求失败的异常信息:" + s);

            Toast.makeText(FansActivity.this, "网络异常，请稍候再试！", Toast.LENGTH_SHORT).show();
        }
    }

}
