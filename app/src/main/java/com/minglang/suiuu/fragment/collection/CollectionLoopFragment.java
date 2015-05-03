package com.minglang.suiuu.fragment.collection;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.LoopArticleActivity;
import com.minglang.suiuu.adapter.CollectionLoopAdapter;
import com.minglang.suiuu.entity.CollectionLoop;
import com.minglang.suiuu.entity.CollectionLoopData;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtil;
import com.minglang.suiuu.utils.ScreenUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInformation;

import java.util.List;

/**
 * 收藏的圈子
 * <p/>
 * A simple {@link Fragment} subclass.
 * Use the {@link CollectionLoopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollectionLoopFragment extends Fragment {

    private static final String TAG = CollectionLoopFragment.class.getSimpleName();

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private GridView gridView;

    private int page = 1;

    private CollectionLoop collectionLoop;

    private List<CollectionLoopData> list;

    private Dialog dialog;

    private int screenWidth, screenHeight;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CollectionLoopFragment.
     */
    public static CollectionLoopFragment newInstance(String param1, String param2) {
        CollectionLoopFragment fragment = new CollectionLoopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CollectionLoopFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_collection_loop, container, false);

        ScreenUtils utils = new ScreenUtils(getActivity());
        screenWidth = utils.getScreenWidth();
        screenHeight = utils.getScreenHeight();

        initView(rootView);

        ViewAction();

        getCollectionLoop4Service();

        return rootView;
    }

    /**
     * 控件动作
     */
    private void ViewAction() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String articleId = list.get(position).getArticleId();
                Intent intent = new Intent(getActivity(), LoopArticleActivity.class);
                intent.putExtra("articleId", articleId);
                startActivity(intent);
            }
        });
    }

    /**
     * 从网络获取收藏的圈子数据
     */
    private void getCollectionLoop4Service() {

        if (dialog != null) {
            dialog.show();
        }

        RequestParams params = new RequestParams();
        params.addBodyParameter("page", String.valueOf(page));
        params.addBodyParameter(HttpServicePath.key, SuiuuInformation.ReadVerification(getActivity()));

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.CollectionLoopPath, new CollectionLoopRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 初始化方法
     *
     * @param rootView Fragment的根View
     */
    private void initView(View rootView) {

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_bar);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        gridView = (GridView) rootView.findViewById(R.id.collection_loop_grid_view);
    }

    class CollectionLoopRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            String str = stringResponseInfo.result;
            try {
                collectionLoop = JsonUtil.getInstance().fromJSON(CollectionLoop.class, str);
                list = collectionLoop.getData();
                CollectionLoopAdapter collectionLoopAdapter = new CollectionLoopAdapter(getActivity(), collectionLoop, list);
                collectionLoopAdapter.setScreenParams(screenWidth, screenHeight);
                gridView.setAdapter(collectionLoopAdapter);
            } catch (Exception e) {
                Log.e(TAG, "收藏的圈子的数据解析异常:" + e.getMessage());
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            Log.e(TAG, "收藏的圈子数据请求失败:" + s);
        }
    }

}
