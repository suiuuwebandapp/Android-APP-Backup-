package com.minglang.suiuu.fragment.attention;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.minglang.suiuu.adapter.AttentionUserAdapter;
import com.minglang.suiuu.entity.AttentionUser;
import com.minglang.suiuu.entity.AttentionUserData;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtil;
import com.minglang.suiuu.utils.SuHttpRequest;

import java.util.List;

/**
 * 关注用户
 */
public class AttentionUserFragment extends Fragment {

    private static final String TAG = AttentionUserFragment.class.getSimpleName();

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String userSign;
    private String verification;

    private ListView listView;

    private int page = 1;

    private List<AttentionUserData> list;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AttentionUserFragment.
     */
    public static AttentionUserFragment newInstance(String param1, String param2) {
        AttentionUserFragment fragment = new AttentionUserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AttentionUserFragment() {

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

        View rootView = inflater.inflate(R.layout.fragment_attention_user, container, false);
        initView(rootView);

        getAttentionData4Service();

        ViewAction();

        return rootView;
    }

    private void ViewAction() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    /**
     * 从服务获取关注用户的数据
     */
    private void getAttentionData4Service() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("page", String.valueOf(page));
        params.addBodyParameter(HttpServicePath.key, verification);

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.AttentionUserPath, new AttentionUserRequestCallback());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 初始化方法
     *
     * @param rootView Fragment根View
     */
    private void initView(View rootView) {
        listView = (ListView) rootView.findViewById(R.id.attention_user_ListView);
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
     * 网络请求回调接口
     */
    private class AttentionUserRequestCallback extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            String str = stringResponseInfo.result;
            Log.i(TAG, "关注的用户数据:" + str);
            try {
                AttentionUser attentionUser = JsonUtil.getInstance().fromJSON(AttentionUser.class, str);
                if (attentionUser.getStatus().equals("1")) {
                    list = attentionUser.getData();
                    AttentionUserAdapter attentionUserAdapter = new AttentionUserAdapter(getActivity(), list);
                    listView.setAdapter(attentionUserAdapter);
                } else {
                    Toast.makeText(getActivity(), "数据获取异常，请稍候再试！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e(TAG, "关注的用户的数据解析失败:" + e.getMessage());
                Toast.makeText(getActivity(), "数据获取失败，请重试！", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            Log.e(TAG, "关注的用户数据请求失败:" + s);
            Toast.makeText(getActivity(), "网络异常，请检查后重试！", Toast.LENGTH_SHORT).show();
        }
    }

}
