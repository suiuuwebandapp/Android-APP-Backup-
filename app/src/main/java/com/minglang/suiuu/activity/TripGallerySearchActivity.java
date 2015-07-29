package com.minglang.suiuu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.TripGalleryAdapter;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.customview.ReFlashListView;
import com.minglang.suiuu.customview.TextProgressDialog;
import com.minglang.suiuu.entity.TripGallery;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/7/14 14:36
 * 修改人：Administrator
 * 修改时间：2015/7/14 14:36
 * 修改备注：
 */
public class TripGallerySearchActivity extends BaseActivity implements ReFlashListView.IReflashListener, ReFlashListView.ILoadMoreDataListener {
    private LinearLayout ll_trip_gallery_search_tag;
    private ImageView iv_top_back;
    private ImageView tv_top_right_more;
    private TextView tv_top_center;
    private String searchCountry;
    private TextProgressDialog dialog;
    private String clickTag;
    private int page = 1;
    private RelativeLayout rl_common_nodata;
    private TripGalleryAdapter adapter;
    private ReFlashListView rfv_trip_gallery_search;
    private List<TripGallery.DataEntity.TripGalleryDataInfo> tripGalleryList;
    private List<ImageView> clickImageList;
    private List<ImageView> imageList;
    private List<String> clickString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_gallery_search);
        searchCountry = this.getIntent().getStringExtra("country");
        initView();
        loadTripGalleryList(null, "0", searchCountry, page);
        viewAction();
    }

    private void initView() {
        dialog = new TextProgressDialog(this);
        tripGalleryList = new ArrayList<>();
        clickImageList = new ArrayList<>();
        clickString = new ArrayList<>();
        imageList = new ArrayList<>();
        rfv_trip_gallery_search = (ReFlashListView) findViewById(R.id.rfv_trip_gallery_search);
        rl_common_nodata = (RelativeLayout) findViewById(R.id.rl_common_nodata);
        ll_trip_gallery_search_tag = (LinearLayout) findViewById(R.id.ll_trip_gallery_search_tag);
        iv_top_back = (ImageView) findViewById(R.id.iv_top_back);
        tv_top_right_more = (ImageView) findViewById(R.id.tv_top_right_more);
        tv_top_right_more.setVisibility(View.INVISIBLE);
        tv_top_center = (TextView) findViewById(R.id.tv_top_center);
        tv_top_center.setText("旅 图");

        //初始化标签内容
        int[] mPhotosIntArray = new int[]{R.drawable.tag_family, R.drawable.tag_shopping, R.drawable.tag_nature, R.drawable.tag_dangerous, R.drawable.tag_romantic, R.drawable.tag_museam, R.drawable.tag_novelty};
        final String[] mTagIntArray = new String[]{"家庭", "购物", "自然", "惊险", "浪漫", "博物馆", "猎奇"};
        View itemView = null;
        ImageView imageView = null;
        TextView textView;
        for (int i = 0; i < mPhotosIntArray.length; i++) {
            itemView = LayoutInflater.from(this).inflate(R.layout.item_trip_gallery_tag, null);
            itemView.setTag(i);
            imageView = (ImageView) itemView.findViewById(R.id.iv_item_trip_gallery_tag);
            imageList.add(imageView);
            textView = (TextView) itemView.findViewById(R.id.tv_item_trip_gallery_tag);
            imageView.setImageResource(mPhotosIntArray[i]);
            textView.setText(mTagIntArray[i]);
            itemView.setPadding(10, 0, 0, 0);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickTag = "";
                    int tag = (int) v.getTag();
                    page = 1;
                    tripGalleryList.clear();
                    if (clickImageList.contains(imageList.get(tag))) {
                        imageList.get(tag).setBackgroundResource(0);
                        clickImageList.remove(imageList.get(tag));
                        clickString.remove(mTagIntArray[tag]);
                    } else {
                        imageList.get(tag).setBackgroundResource(R.drawable.imageview_border_style);
                        clickImageList.add(imageList.get(tag));
                        clickString.add(mTagIntArray[tag]);
                    }
                    Log.i("suiuu","clickTag="+clickTag);
                    for(String clickstring : clickString) {
                        clickTag += clickstring+",";
                    }
                    loadTripGalleryList(clickTag, "0", searchCountry, page);
                }
            });
            ll_trip_gallery_search_tag.addView(itemView);
        }
    }

    private void viewAction() {
        iv_top_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rfv_trip_gallery_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TripGallerySearchActivity.this, TripGalleryDetailActivity.class);
                intent.putExtra("id", tripGalleryList.get(position).getId());
                startActivity(intent);
            }
        });
    }

    private void loadTripGalleryList(String tags, String sortName, String search, int page) {
        dialog.showDialog();
        Log.i("suiuu", "请求条件tags=" + tags + "search=" + search + ",page=" + page);
        String tagnew = "";
        if(tags != null && tags.length() >1) {
            tagnew = tags.substring(0,tags.length()-1);
        }
        String str = SuiuuInfo.ReadVerification(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, str);
        params.addBodyParameter("tags", tagnew);
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
            dialog.dismissDialog();
            String result = stringResponseInfo.result;
            Log.i("suiuu", "result=" + result);
            try {
                JSONObject json = new JSONObject(result);
                int status = (int) json.get("status");
                if (status == 1) {
                    TripGallery tripGallery = JsonUtils.getInstance().fromJSON(TripGallery.class, result);
                    List<TripGallery.DataEntity.TripGalleryDataInfo> data = tripGallery.getData().getData();
                    if (data.size() < 1) {
                        if (!TextUtils.isEmpty(clickTag) && page == 1) {
                            rfv_trip_gallery_search.setVisibility(View.GONE);
                            rl_common_nodata.setVisibility(View.VISIBLE);
                        } else if (page == 1) {
                            rfv_trip_gallery_search.setVisibility(View.GONE);
                            rl_common_nodata.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(TripGallerySearchActivity.this, "没有更多数据显示", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        rfv_trip_gallery_search.setVisibility(View.VISIBLE);
                        rl_common_nodata.setVisibility(View.GONE);
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
            dialog.dismissDialog();
            Log.i("suiuu", "请求失败------------------------------------" + s);
        }
    }

    private void showList(List<TripGallery.DataEntity.TripGalleryDataInfo> tripGalleryList) {
        if (adapter == null) {
            rfv_trip_gallery_search.setInterface(this);
            rfv_trip_gallery_search.setLoadMoreInterface(this);
            adapter = new TripGalleryAdapter(this, tripGalleryList);
            rfv_trip_gallery_search.setAdapter(adapter);
        } else {
            adapter.onDateChange(tripGalleryList);
        }
    }

    @Override
    public void onLoadMoreData() {
        loadTripGalleryList(TextUtils.isEmpty(clickTag) ? null : clickTag, "0", searchCountry, page += 1);
        rfv_trip_gallery_search.loadComplete();
    }

    @Override
    public void onReflash() {
        page = 1;
        tripGalleryList.clear();
        loadTripGalleryList(TextUtils.isEmpty(clickTag) ? null : clickTag, "0", searchCountry, page);
        rfv_trip_gallery_search.reflashComplete();
    }
}
