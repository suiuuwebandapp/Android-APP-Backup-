package com.minglang.suiuu.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.minglang.suiuu.activity.TripGalleryDetailActivity;
import com.minglang.suiuu.adapter.TripGalleryAdapter;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.customview.NoScrollBarListView;
import com.minglang.suiuu.entity.TripGallery;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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

    private LinearLayout mGalleryLinearLayout;
    private TextView tv_trip_gallery_search;
    private NoScrollBarListView lv_trip_gallery;
    private TripGalleryAdapter adapter;
    private List<TripGallery.DataEntity.TripGalleryDataInfo> tripGalleryList;
    private int page = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trip_gallery, null);
        initView(rootView);
        initData();
        viewAction();
        loadTripGalleryList(null, "0", null, page);
        return rootView;
    }

    private void initView(View rootView) {
//        LayoutInflater inflater = LayoutInflater.from(getActivity());
        mGalleryLinearLayout = (LinearLayout) rootView.findViewById(R.id.galleryLinearLayout);
        tv_trip_gallery_search = (TextView) rootView.findViewById(R.id.tv_trip_grllery_search);
        lv_trip_gallery = (NoScrollBarListView) rootView.findViewById(R.id.lv_trip_gallery);
        ScrollView sv_trip_gallery = (ScrollView) rootView.findViewById(R.id.sv_trip_gallery);
        sv_trip_gallery.smoothScrollTo(0, 0);
//        View footer = inflater.inflate(R.layout.footer_layout, null);
        ImageView ddd = new ImageView(getActivity());
        ddd.setImageResource(R.drawable.trip_gallery_loading_more);
        lv_trip_gallery.addFooterView(ddd);
    }

    private void initData() {

        int[] mPhotosIntArray =  new int[]{R.drawable.tag_family, R.drawable.tag_shopping, R.drawable.tag_nature,
                        R.drawable.tag_dangerous, R.drawable.tag_romantic, R.drawable.tag_museam, R.drawable.tag_novelty};

        final String[] mTagIntArray = new String[]{"家庭", "购物", "自然", "惊险", "浪漫", "博物馆", "猎奇"};

        View itemView;
        ImageView imageView;
        TextView textView;

        for (int i = 0; i < mPhotosIntArray.length; i++) {

            itemView = LayoutInflater.from(getActivity()).inflate(R.layout.item_trip_gallery_tag, null);
            itemView.setTag(i);

            imageView = (ImageView) itemView.findViewById(R.id.iv_item_trip_gallery_tag);
            textView = (TextView) itemView.findViewById(R.id.tv_item_trip_gallery_tag);

            imageView.setImageResource(mPhotosIntArray[i]);
            textView.setText(mTagIntArray[i]);

            itemView.setPadding(10, 0, 0, 0);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (int) v.getTag();
                    Toast.makeText(getActivity(), "点击了" + mTagIntArray[tag], Toast.LENGTH_SHORT).show();
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
                Log.i("suiuu", "点击的position=" + position);

                if(position == tripGalleryList.size()) {
                }
                Intent intent = new Intent(getActivity(), TripGalleryDetailActivity.class);
                intent.putExtra("id",tripGalleryList.get(position).getId());
                Log.i("suiuu","旅图的id为="+tripGalleryList.get(position).getId());
                startActivity(intent);
            }
        });
    }

    private void showList(List<TripGallery.DataEntity.TripGalleryDataInfo> tripGalleryList) {
        if (adapter == null) {
            adapter = new TripGalleryAdapter(getActivity().getApplication(), tripGalleryList);
            lv_trip_gallery.setAdapter(adapter);
        } else {
            adapter.onDateChange(tripGalleryList);
        }
    }
    private void loadTripGalleryList(String tags,String sortName,String search,int page) {
        String str = SuiuuInfo.ReadVerification(getActivity());
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, str);
        params.addBodyParameter("tag", tags);
        params.addBodyParameter("sortName", sortName);
        params.addBodyParameter("search", search);
        params.addBodyParameter("page", Integer.toString(page));
        params.addBodyParameter("number", "10");
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
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            String result = stringResponseInfo.result;
            Log.i("suiuu","result="+result);
            try {
                JSONObject json = new JSONObject(result);
                int status = (int) json.get("status");
                if(status == 1) {
                    Log.i("suiuu",json.getString("data"));
                    TripGallery tripGallery = JsonUtils.getInstance().fromJSON(TripGallery.class, result);
                    tripGalleryList = tripGallery.getData().getData();
                    showList(tripGalleryList);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onFailure(HttpException e, String s) {
            Log.i("suiuu", "请求失败------------------------------------" + s);
        }
    }


}