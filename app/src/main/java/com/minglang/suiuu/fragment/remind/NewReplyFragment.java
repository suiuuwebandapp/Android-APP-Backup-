package com.minglang.suiuu.fragment.remind;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.MessageAdapter;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.entity.SuiuuMessage;
import com.minglang.suiuu.entity.SuiuuMessageData;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.util.LocalDisplay;
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

    private String userSign;
    private String verification;

    private PtrClassicFrameLayout mPtrFrame;

    private ListView newReplyList;

    private List<SuiuuMessageData> listAll = new ArrayList<>();

    private Dialog dialog;

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

        initView(rootView);
        ViewAction();
        getData();
        DeBugLog.i(TAG, "userSign:" + userSign);
        return rootView;
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
                SuiuuMessageData data = listAll.get(position);
                String remindId = data.getRemindId();
                DeBugLog.i(TAG, "remind:" + remindId);
            }
        });
    }

    private void getData() {
        if (dialog != null) {
            dialog.show();
        }
        getNewAt4Service(page);
    }

    /**
     * 从网络获取数据
     */
    private void getNewAt4Service(int page) {
        RequestParams params = new RequestParams();
        params.addBodyParameter(TYPE, "2");
        params.addBodyParameter(HttpServicePath.key, verification);
        params.addBodyParameter("number", String.valueOf(page));

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.GetMessageListPath, new NewReplyRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 初始化方法
     *
     * @param rootView Fragment根View
     */
    private void initView(View rootView) {

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_bar);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        mPtrFrame = (PtrClassicFrameLayout) rootView.findViewById(R.id.new_reply_fragment_head_frame);

        MaterialHeader header = new MaterialHeader(getActivity());
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, LocalDisplay.dp2px(15), 0, LocalDisplay.dp2px(10));
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

        newReplyList = (ListView) rootView.findViewById(R.id.newReplyList);

        adapter = new MessageAdapter(getActivity(), "4");
        newReplyList.setAdapter(adapter);
    }

    private class NewReplyRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            mPtrFrame.refreshComplete();

            if (page == 1) {
                if (listAll != null && listAll.size() > 0) {
                    listAll.clear();
                }
            }

            String str = stringResponseInfo.result;
            try {
                SuiuuMessage message = JsonUtils.getInstance().fromJSON(SuiuuMessage.class, str);
                List<SuiuuMessageData> list = message.getData().getData();
                if (list != null && list.size() > 0) {
                    listAll.addAll(list);
                    adapter.setList(list);
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "新回复数据请求失败:" + e.getMessage());
                Toast.makeText(getActivity(), getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            mPtrFrame.refreshComplete();

            DeBugLog.e(TAG, "新回复数据请求失败:" + s);
            Toast.makeText(getActivity(), getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
        }
    }

}
