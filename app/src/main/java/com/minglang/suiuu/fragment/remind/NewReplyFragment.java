package com.minglang.suiuu.fragment.remind;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.OtherUserActivity;
import com.minglang.suiuu.activity.PersonalActivity;
import com.minglang.suiuu.adapter.MessageAdapter;
import com.minglang.suiuu.application.SuiuuApplication;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.entity.SuiuuMessage;
import com.minglang.suiuu.entity.SuiuuMessageData;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * 新回复页面
 * <p/>
 * Use the {@link NewReplyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewReplyFragment extends BaseFragment {

    private static final String TAG = NewReplyFragment.class.getSimpleName();

    private static final String TYPE = "type";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String PAGE = "page";
    private static final String NUMBER = "number";

    private static final String USER_SIGN = "userSign";

    private String userSign;
    private String verification;

    @BindString(R.string.load_wait)
    String loadString;

    @BindString(R.string.NoData)
    String noData;

    @BindString(R.string.NetworkAnomaly)
    String netWorkError;

    @Bind(R.id.new_reply_fragment_head_frame)
    PtrClassicFrameLayout mPtrFrame;

    @Bind(R.id.newReplyList)
    ListView newReplyList;

    private List<SuiuuMessageData> listAll = new ArrayList<>();

    private ProgressDialog progressDialog;

    private MessageAdapter adapter;

    private int page = 1;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewReplyFragment.
     */
    public static NewReplyFragment newInstance(String param1, String param2) {
        NewReplyFragment fragment = new NewReplyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public NewReplyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userSign = getArguments().getString(ARG_PARAM1);
            verification = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_reply, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        ViewAction();
        getNewAt4Service(page);
        DeBugLog.i(TAG, "userSign:" + userSign);
        return rootView;
    }

    /**
     * 初始化方法
     */
    private void initView() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(loadString);
        progressDialog.setCanceledOnTouchOutside(false);

        int paddingParams = Utils.newInstance().dip2px(15, getActivity());

        MaterialHeader header = new MaterialHeader(getActivity());
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, paddingParams, 0, paddingParams);
        header.setPtrFrameLayout(mPtrFrame);
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

        adapter = new MessageAdapter(getActivity(), "4");
        newReplyList.setAdapter(adapter);
    }

    private void ViewAction() {

        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, newReplyList, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
                getNewAt4Service(page);
            }
        });

        newReplyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String OtherUserSign = listAll.get(position).getCreateUserSign();
                Intent intent;
                if (OtherUserSign.equals(userSign)) {
                    intent = new Intent(getActivity(), PersonalActivity.class);
                } else {
                    intent = new Intent(getActivity(), OtherUserActivity.class);
                    intent.putExtra(USER_SIGN, OtherUserSign);
                }
                startActivity(intent);
            }
        });
    }

    /**
     * 从网络获取数据
     */
    private void getNewAt4Service(int page) {
        RequestParams params = new RequestParams();
        params.addBodyParameter(TYPE, "2");
        params.addBodyParameter(HttpServicePath.key, verification);
        params.addBodyParameter(PAGE, String.valueOf(page));
        params.addBodyParameter(NUMBER, String.valueOf(10));

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.GetMessageListPath, new NewReplyRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        mPtrFrame.refreshComplete();
    }

    private void failureLessPage() {
        if (page > 1) {
            page = page - 1;
        }
    }

    private void clearDataList() {
        if (page == 1) {
            if (listAll != null && listAll.size() > 0) {
                listAll.clear();
            }
        }
    }

    private void bindData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            failureLessPage();
            Toast.makeText(SuiuuApplication.applicationContext, noData, Toast.LENGTH_SHORT).show();
        } else {
            try {
                SuiuuMessage message = JsonUtils.getInstance().fromJSON(SuiuuMessage.class, str);
                List<SuiuuMessageData> list = message.getData().getData();
                if (list != null && list.size() > 0) {
                    clearDataList();
                    listAll.addAll(list);
                    adapter.setList(listAll);
                } else {
                    failureLessPage();
                    Toast.makeText(SuiuuApplication.applicationContext, noData, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "新回复数据请求失败:" + e.getMessage());
                failureLessPage();
                Toast.makeText(SuiuuApplication.applicationContext, netWorkError, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class NewReplyRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onStart() {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            String str = stringResponseInfo.result;
            DeBugLog.i(TAG, "新回复返回的数据:" + str);
            hideDialog();
            bindData2View(str);
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "新回复数据请求失败:" + s);
            hideDialog();
            failureLessPage();
            Toast.makeText(SuiuuApplication.applicationContext, netWorkError, Toast.LENGTH_SHORT).show();
        }

    }

}