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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.SuiuuSearchActivity;
import com.minglang.suiuu.activity.TripGalleryDetailsActivity;
import com.minglang.suiuu.adapter.TripGalleryAdapter;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.customview.NoScrollBarListView;
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

public class TripGalleryFragment extends BaseFragment {

    private static final String TAGS = "tags";
    private static final String SORT_NAME = "sortName";
    private static final String SEARCH = "search";
    private static final String NUMBER = "number";
    private static final String PAGES = "page";

    @BindString(R.string.load_wait)
    String dialogMsg;

    private ProgressDialog progressDialog;

    @Bind(R.id.galleryLinearLayout)
    LinearLayout mGalleryLinearLayout;

    @Bind(R.id.trip_gallery_search)
    TextView tv_trip_gallery_search;

    @Bind(R.id.lv_trip_gallery)
    NoScrollBarListView lv_trip_gallery;

    @Bind(R.id.sv_trip_gallery)
    ScrollView sv_trip_gallery;

    @Bind(R.id.rl_common_no_data)
    RelativeLayout rl_common_no_data;

    private TripGalleryAdapter adapter;

    private List<TripGalleryDataInfo> tripGalleryList = new ArrayList<>();

    private int page = 1;

    private String clickTag = "";

    private List<ImageView> clickImageList = new ArrayList<>();
    private List<ImageView> imageList = new ArrayList<>();
    private List<String> clickString = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trip_gallery, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        initData();
        viewAction();
        getTripGalleryData4Service();
        return rootView;
    }

    private void initView() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(dialogMsg);

        sv_trip_gallery.smoothScrollTo(0, 0);

        ImageView imageFootView = new ImageView(getActivity());
        imageFootView.setImageResource(R.drawable.trip_gallery_loading_more);

        lv_trip_gallery.addFooterView(imageFootView);
    }

    @SuppressLint("InflateParams")
    private void initData() {
        int[] mPhotosIntArray = new int[]{R.drawable.tag_family, R.drawable.tag_shopping, R.drawable.tag_nature,
                R.drawable.tag_dangerous, R.drawable.tag_romantic, R.drawable.tag_museam, R.drawable.tag_novelty};

        final String[] mTagIntArray = getResources().getStringArray(R.array.tripGalleryTagName);

        View itemView;
        ImageView imageView;
        TextView textView;
        for (int i = 0; i < mPhotosIntArray.length; i++) {
            itemView = LayoutInflater.from(getActivity()).inflate(R.layout.item_trip_gallery_tag, null);
            itemView.setTag(i);

            imageView = (ImageView) itemView.findViewById(R.id.iv_item_trip_gallery_tag);
            textView = (TextView) itemView.findViewById(R.id.tv_item_trip_gallery_tag);

            imageList.add(imageView);
            imageView.setImageResource(mPhotosIntArray[i]);
            textView.setText(mTagIntArray[i]);
            itemView.setPadding(10, 0, 0, 0);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickTag = "";
                    page = 1;
                    tripGalleryList.clear();
                    int tag = (int) v.getTag();

                    if (clickImageList.contains(imageList.get(tag))) {
                        imageList.get(tag).setBackgroundResource(0);
                        clickImageList.remove(imageList.get(tag));
                        clickString.remove(mTagIntArray[tag]);
                    } else {
                        imageList.get(tag).setBackgroundResource(R.drawable.imageview_border_style);
                        clickImageList.add(imageList.get(tag));
                        clickString.add(mTagIntArray[tag]);
                    }

                    for (String clickString : TripGalleryFragment.this.clickString) {
                        clickTag += clickString + ",";
                    }

                    loadTripGalleryList(buildRequestParams(clickTag, "0", null, page));
                }
            });

            mGalleryLinearLayout.addView(itemView);
        }
    }

    private void viewAction() {

        tv_trip_gallery_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SuiuuSearchActivity.class);
                intent.putExtra("searchClass", 1);
                startActivity(intent);
            }
        });

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
            adapter = new TripGalleryAdapter(getActivity(), tripGalleryList);
            lv_trip_gallery.setAdapter(adapter);
        } else {
            adapter.onDateChange(tripGalleryList);
        }
    }

    private RequestParams buildRequestParams(String tags, String sortName, String search, int page) {
        DeBugLog.i("suiuu", "请求条件,tags:" + tags + ",search:" + search + ",page:" + page);

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
        suHttpRequest.requestNetworkData();
    }

    /**
     * 发布圈子文章回调接口
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
                            rl_common_no_data.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(getActivity(), "没有更多数据显示", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        lv_trip_gallery.setVisibility(View.VISIBLE);
                        rl_common_no_data.setVisibility(View.GONE);
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