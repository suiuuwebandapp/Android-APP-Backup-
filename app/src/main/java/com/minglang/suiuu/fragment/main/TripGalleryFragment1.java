package com.minglang.suiuu.fragment.main;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.TripGalleryDetailsActivity;
import com.minglang.suiuu.adapter.TripGalleryAdapter1;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.customview.ReFlashListView;
import com.minglang.suiuu.entity.TripGallery;
import com.minglang.suiuu.entity.TripGallery.DataEntity.TripGalleryDataInfo;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/7/8 15:46
 * 修改人：Administrator
 * 修改时间：2015/7/8 15:46
 * 修改备注：
 */

public class TripGalleryFragment1 extends BaseFragment {

    private static final String TAGS = "tags";
    private static final String SORT_NAME = "sortName";
    private static final String SEARCH = "search";
    private static final String NUMBER = "number";
    private static final String PAGES = "page";

    @BindString(R.string.load_wait)
    String dialogMsg;

    private ProgressDialog progressDialog;
    @Bind(R.id.lv_suiuu)
    ReFlashListView lv_trip_gallery;
    private TripGalleryAdapter1 adapter;

    private List<TripGalleryDataInfo> tripGalleryList = new ArrayList<>();

    private int page = 1;

    private String clickTag = "";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_route, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        viewAction();
        getTripGalleryData4Service();
        return rootView;
    }

    private void initView() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(dialogMsg);
        loadTripGalleryList(buildRequestParams(clickTag, "0", null, page));
    }

    @SuppressLint("InflateParams")


    private void viewAction() {
        lv_trip_gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == tripGalleryList.size()) {
                    if (!TextUtils.isEmpty(clickTag)) {
                        loadTripGalleryList(buildRequestParams(clickTag, "0", null, page += 1));
                    } else {
                        loadTripGalleryList(buildRequestParams(null, "0", null, page += 1));
                    }
                } else {
                    Intent intent = new Intent(getActivity(), TripGalleryDetailsActivity.class);
                    intent.putExtra("id", tripGalleryList.get(position).getId());
                    startActivity(intent);
                }
            }
        });
    }
    private void getTripGalleryData4Service() {
        page = 1;
        adapter = null;
        tripGalleryList.clear();
        loadTripGalleryList(buildRequestParams(null, "0", null, page));
    }
    private void showList(List<TripGalleryDataInfo> tripGalleryList) {
        if (adapter == null) {
            adapter = new TripGalleryAdapter1(getActivity(), tripGalleryList);
            lv_trip_gallery.setAdapter(adapter);
        } else {
            adapter.onDateChange(tripGalleryList);
        }
    }

    private RequestParams buildRequestParams(String tags, String sortName, String search, int page) {
        String newTag = "";
        if (tags != null && tags.length() > 1) {
            newTag = tags.substring(0, tags.length() - 1);
        }
        String str = SuiuuInfo.ReadVerification(getActivity());
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, str);
        params.addBodyParameter(TAGS, newTag);
        params.addBodyParameter(SORT_NAME, sortName);
        params.addBodyParameter(SEARCH, search);
        params.addBodyParameter(PAGES, Integer.toString(page));
        params.addBodyParameter(NUMBER, "10");
        return params;
    }

    private void loadTripGalleryList(RequestParams params) {
        SuHttpRequest suHttpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getTripGalleryList, new loadTripGalleryListCallBack());
        suHttpRequest.setParams(params);
        suHttpRequest.executive();
    }

    /**
     * 获取旅图数据回调接口
     */
    class loadTripGalleryListCallBack extends RequestCallBack<String> {

        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            progressDialog.dismiss();
            String result = stringResponseInfo.result;
            DeBugLog.i("suiuu", "result=" + result);
            try {
                JSONObject json = new JSONObject(result);
                int status = (int) json.get("status");
                if (status == 1) {
                    TripGallery tripGallery = JsonUtils.getInstance().fromJSON(TripGallery.class, result);
                    List<TripGalleryDataInfo> data = tripGallery.getData().getData();
                    if (data.size() < 1) {
                        if (!TextUtils.isEmpty(clickTag) && page == 1) {
                            lv_trip_gallery.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(getActivity(), "没有更多数据显示", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        lv_trip_gallery.setVisibility(View.VISIBLE);
                        tripGalleryList.addAll(data);
                        showList(tripGalleryList);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            progressDialog.dismiss();
            Log.i("suiuu", "请求失败------------------------------------" + s);
        }
    }

}