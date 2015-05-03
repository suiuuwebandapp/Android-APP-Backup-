package com.minglang.suiuu.fragment.loop;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.minglang.suiuu.activity.LoopDetailsActivity;
import com.minglang.suiuu.adapter.AreaAdapter;
import com.minglang.suiuu.entity.Loop;
import com.minglang.suiuu.entity.LoopData;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtil;
import com.minglang.suiuu.utils.ScreenUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInformation;

import java.util.List;

/**
 * 地区页面
 * <p/>
 * A simple {@link Fragment} subclass.
 * Use the {@link AreaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AreaFragment extends Fragment {

    private static final String TAG = AreaFragment.class.getSimpleName();

    private GridView areaGridView;

    /**
     * 数据集合
     */
    private List<LoopData> list;

    private ProgressDialog progressDialog;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    /**
     * 屏幕宽度
     */
    private int screenWidth;

    /**
     * 屏幕高度
     */
    private int screenHeight;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AreaFragment.
     */
    public static AreaFragment newInstance(String param1, String param2) {
        AreaFragment fragment = new AreaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AreaFragment() {
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
    @SuppressLint("InflateParams")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_area, null);

        ScreenUtils screenUtils = new ScreenUtils(getActivity());
        screenWidth = screenUtils.getScreenWidth();
        screenHeight = screenUtils.getScreenHeight();

        initView(rootView);
        ViewAction();
        getInternetServiceData();

        return rootView;
    }

    /**
     * 控件动作
     */
    private void ViewAction() {
        areaGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String circleId = list.get(position).getcId();
                Intent intent = new Intent(getActivity(), LoopDetailsActivity.class);
                intent.putExtra("circleId", circleId);
                intent.putExtra("type","2");
                startActivity(intent);
            }
        });
    }

    /**
     * 从网络获取数据
     */
    private void getInternetServiceData() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }

        String str = SuiuuInformation.ReadVerification(getActivity());

        RequestParams params = new RequestParams();
        //TODO 忽略身份验证KEY
//        params.addBodyParameter(HttpServicePath.key, str);
        params.addBodyParameter("type", "2");

        SuHttpRequest suHttpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.LoopDataPath, new AreaRequestCallback());
        suHttpRequest.setParams(params);
        suHttpRequest.requestNetworkData();
    }

    /**
     * 初始化方法
     *
     * @param rootView Fragment根View
     */
    private void initView(View rootView) {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        progressDialog.setMessage(getResources().getString(R.string.load_wait));

        areaGridView = (GridView) rootView.findViewById(R.id.areaGridView);
    }

    /**
     * 网络请求回调接口
     */
    class AreaRequestCallback extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            String str = responseInfo.result;
            try{
                Loop loop = JsonUtil.getInstance().fromJSON(Loop.class, str);
                Log.i(TAG, loop.toString());
                if (Integer.parseInt(loop.getStatus()) == 1) {
                    list = loop.getData();
                    AreaAdapter areaAdapter = new AreaAdapter(getActivity(), loop, list);
                    areaAdapter.setScreenParams(screenWidth, screenHeight);
                    areaGridView.setAdapter(areaAdapter);
                } else {
                    Toast.makeText(getActivity(), "数据获取失败，请重试！", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                Log.e(TAG,e.getMessage());
            }
        }

        @Override
        public void onFailure(HttpException error, String msg) {

            Log.i(TAG, msg);

//            if (progressDialog.isShowing()) {
            progressDialog.dismiss();
            Log.i(TAG, "progressDialog dismiss");
//            }

            Toast.makeText(getActivity(), "数据获取失败，请重试！", Toast.LENGTH_SHORT).show();
        }
    }

}