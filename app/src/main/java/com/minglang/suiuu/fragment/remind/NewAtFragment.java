package com.minglang.suiuu.fragment.remind;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.MessageAdapter;
import com.minglang.suiuu.entity.SuiuuMessage;
import com.minglang.suiuu.entity.SuiuuMessageData;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtil;
import com.minglang.suiuu.utils.SuHttpRequest;

import java.util.List;

import in.srain.cube.util.LocalDisplay;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * 新@页面
 * <p/>
 * A simple {@link Fragment} subclass.
 * Use the {@link NewAtFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewAtFragment extends Fragment {

    private static final String TAG = NewAtFragment.class.getSimpleName();

    private static final String TYPE = "type";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String userSign;
    private String verification;

    private PtrClassicFrameLayout mPtrFrame;

    private ListView newAtList;

    private List<SuiuuMessageData> list;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewAtFragment.
     */
    public static NewAtFragment newInstance(String param1, String param2) {
        NewAtFragment fragment = new NewAtFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public NewAtFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_new_at, container, false);

        initView(rootView);

        ViewAction();

        getNewAt4Service();

        return rootView;
    }

    private void ViewAction() {

        newAtList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, newAtList, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                getNewAt4Service();
            }
        });
    }

    /**
     * 从网络获取数据
     */
    private void getNewAt4Service() {
        RequestParams params = new RequestParams();
        params.addBodyParameter(TYPE, "1");
        params.addBodyParameter(HttpServicePath.key, verification);

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.GetMessageListPath, new NewAtRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 初始化方法
     *
     * @param rootView Fragment的根View
     */
    private void initView(View rootView) {

        mPtrFrame = (PtrClassicFrameLayout) rootView.findViewById(R.id.new_at_fragment_head_frame);

        MaterialHeader header = new MaterialHeader(getActivity());
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

        newAtList = (ListView) rootView.findViewById(R.id.newAtList);
    }

    private class NewAtRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            String str = stringResponseInfo.result;
            try {
                SuiuuMessage message = JsonUtil.getInstance().fromJSON(SuiuuMessage.class, str);
                list = message.getData();
                MessageAdapter adapter = new MessageAdapter(getActivity(), list, "1");
                newAtList.setAdapter(adapter);
            } catch (Exception e) {
                Log.e(TAG, "新@数据解析异常:" + e.getMessage());
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            Log.e(TAG, "获取新@数据失败:" + s);
        }
    }

}
