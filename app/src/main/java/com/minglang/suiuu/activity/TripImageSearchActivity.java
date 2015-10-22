package com.minglang.suiuu.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.pulltorefreshlibrary.PullToRefreshBase;
import com.minglang.pulltorefreshlibrary.PullToRefreshListView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.TripImageAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.entity.TripImage;
import com.minglang.suiuu.entity.TripImage.TripImageData.TripImageItemData;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.StatusBarCompat;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/7/14 14:36
 * 修改人：Administrator
 * 修改时间：2015/7/14 14:36
 * 修改备注：
 */
public class TripImageSearchActivity extends BaseAppCompatActivity {

    private static final String TAG = TripImageSearchActivity.class.getSimpleName();

    private static final String TAGS = "tags";
    private static final String SORT_NAME = "sortName";
    private static final String SEARCH = "search";

    private String searchCountry;

    private String clickTag;
    private int page = 1;

    @BindColor(R.color.white)
    int titleColor;

    @BindDrawable(R.drawable.image_view_border_style)
    Drawable Frame;

    @BindString(R.string.load_wait)
    String DialogMsg;

    @BindString(R.string.NoData)
    String NoData;

    @BindString(R.string.DataError)
    String DataError;

    @BindString(R.string.NetworkAnomaly)
    String NetworkError;

    @BindString(R.string.SystemException)
    String SystemException;

    @Bind(R.id.trip_image_search_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.common_no_data)
    RelativeLayout NoDataLayout;

    private TripImageAdapter adapter;

    @Bind(R.id.trip_image_search_list_view)
    PullToRefreshListView pullToRefreshListView;

    @Bind(R.id.trip_image_search_tag)
    LinearLayout tripImageSearchLayout;

    private ProgressDialog progressDialog;

    private List<TripImageItemData> listAll = new ArrayList<>();

    private List<ImageView> clickImageList = new ArrayList<>();
    private List<ImageView> imageList = new ArrayList<>();
    private List<String> clickStringList = new ArrayList<>();

    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_image_search);

        StatusBarCompat.compat(this);
        ButterKnife.bind(this);

        searchCountry = this.getIntent().getStringExtra(COUNTRY);

        initView();
        loadTripGalleryList(null, "0", searchCountry, page);
        viewAction();
    }

    @SuppressLint("InflateParams")
    private void initView() {
        context = this;

        token = SuiuuInfo.ReadAppTimeSign(this);

        toolbar.setTitleTextColor(titleColor);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(DialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        adapter = new TripImageAdapter(this, listAll);
        pullToRefreshListView.setAdapter(adapter);

        //初始化标签内容
        int[] mPhotosIntArray = new int[]{R.drawable.tag_0,
                R.drawable.tag_1, R.drawable.tag_2,
                R.drawable.tag_3, R.drawable.tag_4,
                R.drawable.tag_5, R.drawable.tag_6};

        final String[] mTagIntArray = getResources().getStringArray(R.array.tripImageTagName);

        View itemView;
        SimpleDraweeView imageView;
        TextView textView;

        for (int i = 0; i < mPhotosIntArray.length; i++) {
            itemView = LayoutInflater.from(this).inflate(R.layout.item_trip_image_tag, null);
            itemView.setTag(i);

            imageView = (SimpleDraweeView) itemView.findViewById(R.id.item_trip_image_tag);
            imageList.add(imageView);

            textView = (TextView) itemView.findViewById(R.id.item_trip_text_tag);
            imageView.setImageURI(Uri.parse("res://com.minglang.suiuu/" + mPhotosIntArray[i]));

            textView.setText(mTagIntArray[i]);
            itemView.setPadding(10, 0, 0, 0);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickTag = "";
                    int tag = (int) v.getTag();

                    page = 1;
                    listAll.clear();

                    if (clickImageList.contains(imageList.get(tag))) {
                        imageList.get(tag).setBackgroundResource(0);
                        clickImageList.remove(imageList.get(tag));
                        clickStringList.remove(mTagIntArray[tag]);
                    } else {
                        imageList.get(tag).setBackground(Frame);
                        clickImageList.add(imageList.get(tag));
                        clickStringList.add(mTagIntArray[tag]);
                    }
                    for (String clickString : clickStringList) {
                        clickTag += clickString + ",";
                    }

                    loadTripGalleryList(clickTag, "0", searchCountry, page);
                }
            });

            tripImageSearchLayout.addView(itemView);
        }
    }

    private void viewAction() {

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(context, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = 1;
                if (listAll != null && listAll.size() > 0) {
                    listAll.clear();
                }

                loadTripGalleryList(TextUtils.isEmpty(clickTag) ? null : clickTag, "0", searchCountry, page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(context, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page++;
                loadTripGalleryList(TextUtils.isEmpty(clickTag) ? null : clickTag, "0", searchCountry, page);
            }

        });

        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int location = position - 1;
                Intent intent = new Intent(TripImageSearchActivity.this, TripImageDetailsActivity.class);
                intent.putExtra(ID, listAll.get(location).getId());
                startActivity(intent);
            }
        });
    }

    private void loadTripGalleryList(String tags, String sortName, String search, int page) {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }

        String tagNew = "";
        if (tags != null && tags.length() > 1) {
            tagNew = tags.substring(0, tags.length() - 1);
        }

        String[] keyArray1 = new String[]{TAGS, SORT_NAME, SEARCH, PAGE, NUMBER, TOKEN};
        String[] valueArray1 = new String[]{tagNew, sortName, search, Integer.toString(page), "10", token};
        String url = addUrlAndParams(HttpNewServicePath.getTripImageDataPath, keyArray1, valueArray1);

        try {
            OkHttpManager.onGetAsynRequest(url, new LoadTripImageListCallBack());
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }
    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        pullToRefreshListView.onRefreshComplete();
    }

    /**
     * 发布圈子文章回调接口
     */
    private class LoadTripImageListCallBack extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String result) {
            if (TextUtils.isEmpty(result)) {
                Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
            } else try {
                JSONObject json = new JSONObject(result);
                int status = (int) json.get(STATUS);
                switch (status) {
                    case 1:
                        TripImage tripImage = JsonUtils.getInstance().fromJSON(TripImage.class, result);
                        List<TripImageItemData> data = tripImage.getData().getData();
                        if (data.size() < 1) {
                            if (!TextUtils.isEmpty(clickTag) && page == 1) {
                                pullToRefreshListView.setVisibility(View.GONE);
                                NoDataLayout.setVisibility(View.VISIBLE);
                            } else if (page == 1) {
                                pullToRefreshListView.setVisibility(View.GONE);
                                NoDataLayout.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            pullToRefreshListView.setVisibility(View.VISIBLE);
                            NoDataLayout.setVisibility(View.GONE);
                            listAll.addAll(data);
                            adapter.updateData(listAll);
                        }
                        break;

                    case -1:
                        Toast.makeText(context, SystemException, Toast.LENGTH_SHORT).show();
                        break;

                    case -2:
                        Toast.makeText(context, json.getString(DATA), Toast.LENGTH_SHORT).show();
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, DataError, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "加载失败:" + e.getMessage());
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish() {
            hideDialog();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}