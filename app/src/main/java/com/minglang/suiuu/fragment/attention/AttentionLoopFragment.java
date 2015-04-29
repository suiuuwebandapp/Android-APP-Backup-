package com.minglang.suiuu.fragment.attention;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.AttentionLoopAdapter;
import com.minglang.suiuu.entity.AttentionLoop;
import com.minglang.suiuu.entity.AttentionLoopData;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtil;
import com.minglang.suiuu.utils.SuHttpRequest;

import java.util.List;

/**
 * 关注主题
 */
public class AttentionLoopFragment extends Fragment {

    private static final String TAG = AttentionLoopFragment.class.getSimpleName();

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private GridView attentionThemeGridView;

    private int page = 1;

    private List<AttentionLoopData> list;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AttentionThemeFragment.
     */
    public static AttentionLoopFragment newInstance(String param1, String param2) {
        AttentionLoopFragment fragment = new AttentionLoopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AttentionLoopFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_attention_theme, container, false);

        initView(rootView);
        getAttentionData4Service();
        ViewAction();

        return rootView;
    }

    private void ViewAction() {
        attentionThemeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AttentionLoopData attentionLoopData = list.get(position);
                String cID = attentionLoopData.getcId();
            }
        });
    }

    /**
     * 从服务器获取数据
     */
    private void getAttentionData4Service() {

        RequestParams params = new RequestParams();
//        TODO 忽略身份验证KEY
//        params.addBodyParameter(HttpServicePath.key, SuiuuInformation.ReadVerification(getActivity()));
        params.addBodyParameter("page", String.valueOf(page));

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.AttentionLoopPath, new AttentionThemeRequestCallback());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();

    }

    /**
     * 初始化方法
     *
     * @param rootView Fragment根View
     */
    private void initView(View rootView) {
        attentionThemeGridView = (GridView) rootView.findViewById(R.id.attentionThemeGridView);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * 关注的圈子网络请求回调接口
     */
    private class AttentionThemeRequestCallback extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            String str = stringResponseInfo.result;

            try {
                AttentionLoop attentionLoop = JsonUtil.getInstance().fromJSON(AttentionLoop.class, str);
                if (attentionLoop.getStatus().equals("1")) {

                    list = attentionLoop.getData();

                    AttentionLoopAdapter attentionLoopAdapter = new AttentionLoopAdapter(getActivity(),
                            attentionLoop, list);
                    attentionThemeGridView.setAdapter(attentionLoopAdapter);

                } else {
                    Toast.makeText(getActivity(), "数据获取失败，请稍候再试！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                Toast.makeText(getActivity(), "数据获取异常，请稍候再试！", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            Log.e(TAG, s);
            Toast.makeText(getActivity(), "网络异常，请稍候再试！", Toast.LENGTH_SHORT).show();
        }
    }

}