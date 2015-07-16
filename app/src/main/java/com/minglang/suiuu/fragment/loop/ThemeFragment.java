package com.minglang.suiuu.fragment.loop;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.pulltorefreshlibrary.PullToRefreshBase;
import com.minglang.pulltorefreshlibrary.PullToRefreshGridView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.LoopDetailsActivity;
import com.minglang.suiuu.adapter.ThemeAdapter;
import com.minglang.suiuu.application.SuiuuApplication;
import com.minglang.suiuu.customview.TextProgressDialog;
import com.minglang.suiuu.entity.LoopBase;
import com.minglang.suiuu.entity.LoopBaseData;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.ScreenUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * 主题页面
 * <p/>
 * A simple {@link Fragment} subclass.
 * Use the {@link ThemeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThemeFragment extends Fragment {

    private static final String TAG = ThemeFragment.class.getSimpleName();

    private static final String TYPE = "type";
    private static final String CIRCLEID = "circleId";

    private PullToRefreshGridView themeGridView;

    /**
     * 数据集合
     */
    private List<LoopBaseData> listAll = new ArrayList<>();

    private ProgressDialog progressDialog;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String userSign;
    private String verification;

    /**
     * 屏幕宽度
     */
    private int screenWidth;

    private int page = 1;

    private TextView refreshDataView;

    private TextProgressDialog mProgressDialog;

    private ThemeAdapter themeAdapter;

    private ImageLoader imageLoader;

    private DisplayImageOptions displayImageOptions;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AreaFragment.
     */
    public static ThemeFragment newInstance(String param1, String param2) {
        ThemeFragment fragment = new ThemeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ThemeFragment() {
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
    @SuppressLint("InflateParams")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_theme, null);

        ScreenUtils screenUtils = new ScreenUtils(getActivity());
        screenWidth = screenUtils.getScreenWidth();

        initView(rootView);
        ViewAction();
        getData();
        return rootView;
    }

    /**
     * 初始化方法
     *
     * @param rootView Fragment根view
     */
    private void initView(View rootView) {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        progressDialog.setMessage(getResources().getString(R.string.load_wait));

        mProgressDialog = new TextProgressDialog(getActivity());
        mProgressDialog.setMessage(getResources().getString(R.string.pull_to_refresh_footer_refreshing_label));

        themeGridView = (PullToRefreshGridView) rootView.findViewById(R.id.themeGridView);
        themeGridView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        refreshDataView = (TextView) rootView.findViewById(R.id.themeNoDataView);

        themeAdapter = new ThemeAdapter(getActivity());
        themeAdapter.setScreenParams(screenWidth);
        themeAdapter.setImageLoadingListener(new ThemeImageLoadingListener());
        themeGridView.setAdapter(themeAdapter);

        imageLoader = ImageLoader.getInstance();
        displayImageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.loading)
                .showImageForEmptyUri(R.drawable.loading).showImageOnFail(R.drawable.loading_error)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    /**
     * 控件动作
     */
    private void ViewAction() {

        refreshDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                refreshDataView.setVisibility(View.INVISIBLE);

                if (mProgressDialog != null) {
                    mProgressDialog.showDialog();
                }

                page = 1;
                getInternetServiceData(page);
            }
        });

        themeGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                themeGridView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                page = page + 1;
                getInternetServiceData(page);
            }
        });

        themeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String circleId = listAll.get(position).getcId();
                String loopName = listAll.get(position).getcName();
                Intent intent = new Intent(getActivity(), LoopDetailsActivity.class);
                intent.putExtra(CIRCLEID, circleId);
                intent.putExtra(TYPE, "1");
                intent.putExtra("name", loopName);
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

    }

    /**
     * 启动网络请求
     */
    private void getData() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        getInternetServiceData(page);
    }

    /**
     * 从网络获取数据
     */
    private void getInternetServiceData(int page) {
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, verification);
        params.addBodyParameter(TYPE, "1");
        params.addBodyParameter("page", String.valueOf(page));
        params.addBodyParameter("number", String.valueOf(10));

        SuHttpRequest suHttpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.LoopDataPath, new ThemeRequestCallback());
        suHttpRequest.setParams(params);
        suHttpRequest.requestNetworkData();
    }

    private void showOrHideDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (mProgressDialog.isShow()) {
            mProgressDialog.dismissDialog();
        }

        themeGridView.onRefreshComplete();
    }

    private void failureComputePage() {
        if (page > 1) {
            page = page - 1;
        }
    }

    private void noDataShowReload(){
        if(listAll!=null&&listAll.size()>0){
            refreshDataView.setVisibility(View.INVISIBLE);
        }else{
            refreshDataView.setVisibility(View.VISIBLE);
        }
    }

    public String getUserSign() {
        return userSign;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = SuiuuApplication.getRefWatcher();
        refWatcher.watch(this);
    }

    /**
     * 网络请求回调接口
     */
    class ThemeRequestCallback extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            showOrHideDialog();

            String str = responseInfo.result;
            try {
                LoopBase loopBase = JsonUtils.getInstance().fromJSON(LoopBase.class, str);
                if (Integer.parseInt(loopBase.getStatus()) == 1) {
                    List<LoopBaseData> list = loopBase.getData().getData();
                    if (list != null && list.size() > 0) {
                        listAll.addAll(list);
                        noDataShowReload();
                        themeAdapter.setList(listAll);
                    } else {
                        failureComputePage();
                        noDataShowReload();
                        Toast.makeText(getActivity(), getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    failureComputePage();
                    noDataShowReload();
                    Toast.makeText(getActivity(), getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                failureComputePage();
                noDataShowReload();
                Toast.makeText(getActivity(), getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
                DeBugLog.e(TAG, "主题地区数据解析失败:" + e.getMessage());
            }
        }

        @Override
        public void onFailure(HttpException error, String msg) {
            showOrHideDialog();
            failureComputePage();
            noDataShowReload();
            Toast.makeText(getActivity(), getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
            DeBugLog.e(TAG, "主题数据请求失败:" + msg);
        }
    }

    class ThemeImageLoadingListener implements ImageLoadingListener {

        @Override
        public void onLoadingStarted(String imageUri, View view) {
            DeBugLog.i(TAG, "onLoadingStarted():" + imageUri);
            DeBugLog.i(TAG, "onLoadingStarted():view.getId():" + view.getId());
        }

        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            DeBugLog.i(TAG, "onLoadingStarted():" + imageUri);
            DeBugLog.i(TAG, "onLoadingStarted():view.getId():" + view.getId());

            switch (failReason.getType()) {
                case IO_ERROR:
                    DeBugLog.e(TAG, "ImageLoader is appear IO_ERROR!");

                case DECODING_ERROR:
                    DeBugLog.e(TAG, "ImageLoader is not DECODING_ERROR!");

                case NETWORK_DENIED:
                    DeBugLog.e(TAG, "ImageLoader is request failure!");
                    imageLoader.displayImage(imageUri, (ImageView) view, displayImageOptions, new ThemeImageLoadingListener());
                    break;

                case OUT_OF_MEMORY:
                    DeBugLog.e(TAG, "ImageLoader is appear OUT_OF_MEMORY!");
                    imageLoader.clearMemoryCache();
                    break;

                default:
                    DeBugLog.e(TAG, "ImageLoader is appear UNKNOWN ERROR!");
                    break;
            }
        }

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            DeBugLog.i(TAG, "onLoadingComplete():" + imageUri);
            DeBugLog.i(TAG, "onLoadingComplete():view.getId():" + view.getId());
        }

        @Override
        public void onLoadingCancelled(String imageUri, View view) {
            DeBugLog.i(TAG, "onLoadingCancelled():" + imageUri);
            DeBugLog.i(TAG, "onLoadingCancelled():view.getId():" + view.getId());
        }
    }
}