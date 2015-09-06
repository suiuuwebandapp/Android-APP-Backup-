package com.minglang.suiuu.fragment.main;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.TripGalleryDetailsActivity;
import com.minglang.suiuu.adapter.TripGalleryAdapter;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.customview.ReFlashListView;
import com.minglang.suiuu.dbhelper.DataCacheUtils;
import com.minglang.suiuu.entity.TripGallery;
import com.minglang.suiuu.entity.TripGallery.DataEntity.TripGalleryDataInfo;
import com.minglang.suiuu.utils.HttpNewServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.OkHttpManager;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.Utils;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

public class TripGalleryFragment extends BaseFragment implements ReFlashListView.IReflashListener, ReFlashListView.ILoadMoreDataListener, TripGalleryAdapter.LoadChoiceTag {
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
    private TripGalleryAdapter adapter;
    private List<TripGalleryDataInfo> tripGalleryList = new ArrayList<>();
    private int page = 1;
    private String clickTag = "";
    /**
     * 点击了的标签集合
     */
    private List<String> tagList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_route, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        viewAction();
        loadTripGalleryList(null, "0", null, page);
        return rootView;
    }


    private void initView() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(dialogMsg);
    }

    @SuppressLint("InflateParams")
    private void viewAction() {
        lv_trip_gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 2) {
                    if (Utils.isNetworkConnected(getActivity())) {
                        Intent intent = new Intent(getActivity(), TripGalleryDetailsActivity.class);
                        intent.putExtra("id", tripGalleryList.get(position - 3).getId());
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), R.string.lsq_network_connection_interruption, Toast.LENGTH_SHORT).show();
                    }

                }
            }

        });
    }

    private void showList(List<TripGalleryDataInfo> tripGalleryList) {
        if (adapter == null) {
            lv_trip_gallery.setInterface(this);
            lv_trip_gallery.setLoadMoreInterface(this);
            adapter = new TripGalleryAdapter(getActivity(), tripGalleryList, "homePage", tagList);
            adapter.setLoadChoiceTagInterface(this);
            lv_trip_gallery.setAdapter(adapter);
        } else {
            adapter.onDateChange(tripGalleryList, "homePage", tagList);
        }
    }

    @Override
    public void onLoadMoreData() {
        if(Utils.isNetworkConnected(getActivity()) ) {
            page += 1;
            loadTripGalleryList("".equals(clickTag) ? null : clickTag, "0", null, page);
            lv_trip_gallery.loadComplete();
        }else {
            Toast.makeText(getActivity(),R.string.NetworkAnomaly, Toast.LENGTH_SHORT).show();
            lv_trip_gallery.loadComplete();
        }
    }

    @Override
    public void onReflash() {
        page = 1;
        tagList.clear();
        adapter = null;
        tripGalleryList.clear();
        loadTripGalleryList(null, "0", null, page);
        lv_trip_gallery.reflashComplete();
    }

    @Override
    public void getClickTagList(List<String> tagList) {
        this.tagList = tagList;
        clickTag = "";
        adapter = null;
        tripGalleryList.clear();
        for (String clickString : tagList) {
            clickTag += clickString + ",";
        }
        page = 1;
        loadTripGalleryList(clickTag, "0", null, page);
    }
    /**
     * 加载旅图列表
     * @param tags 标签
     * @param sortName 排序方式
     * @param search
     * @param page
     */
    private void loadTripGalleryList(String tags, String sortName, String search, int page) {
        progressDialog.show();
        String newTag = "";
        if (tags != null && tags.length() > 1) {
            newTag = tags.substring(0, tags.length() - 1);
        }
        String[] keyArray1 = new String[]{TAGS, SORT_NAME, SEARCH, PAGES, NUMBER, "token"};
        String[] valueArray1 = new String[]{newTag, sortName, search, Integer.toString(page), "10", SuiuuInfo.ReadAppTimeSign(getActivity())};
        try {
            OkHttpManager.onGetAsynRequest(addUrlAndParams(HttpNewServicePath.getTripGalleryList, keyArray1, valueArray1), new loadTripGalleryListCallBack());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 获取旅图数据回调接口
     */
    class loadTripGalleryListCallBack extends OkHttpManager.ResultCallback<String> {
        @Override
        public void onError(Request request, Exception e) {
            tripGalleryList.addAll(JsonUtils.getInstance().fromJSON(TripGallery.class, (String) DataCacheUtils.getCacheData(getActivity(),"1").get("data")).getData().getData());
            showList(tripGalleryList);
            progressDialog.dismiss();
        }

        @Override
            public void onResponse(String result) {
            progressDialog.dismiss();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd", Locale.CHINA);
            Date date = new Date();
            String format = simpleDateFormat.format(date);
            try {
                JSONObject json = new JSONObject(result);
                int status = (int) json.get("status");
                if (status == 1) {
                    TripGallery tripGallery = JsonUtils.getInstance().fromJSON(TripGallery.class, result);
                    List<TripGalleryDataInfo> data = tripGallery.getData().getData();
                    if (data.size() < 1) {
                        Toast.makeText(getActivity(), "没有更多数据显示", Toast.LENGTH_SHORT).show();
                    } else {
                        //缓存数据
                        Map tripGalleryCacheData = DataCacheUtils.getCacheData(getActivity(),"1");
                        //上次缓存时间
                        String time = (String) tripGalleryCacheData.get("time");
                        if (time == null) {
                            DataCacheUtils.insertCacheData(getActivity(), format, result,"1");
                        } else if (Utils.compareDate(time, format) == -1) {
                            DataCacheUtils.updateCacheData(getActivity(), format, (String) tripGalleryCacheData.get("id"), result);
                        }
                        lv_trip_gallery.setVisibility(View.VISIBLE);
                        tripGalleryList.addAll(data);
                        showList(tripGalleryList);
                    }
                }else if(status == -5){
                    tripGalleryList.addAll(JsonUtils.getInstance().fromJSON(TripGallery.class, (String) DataCacheUtils.getCacheData(getActivity(), "1").get("data")).getData().getData());
                    showList(tripGalleryList);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}