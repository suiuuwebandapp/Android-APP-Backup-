package com.minglang.suiuu.fragment.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.pulltorefreshlibrary.PullToRefreshBase;
import com.minglang.pulltorefreshlibrary.PullToRefreshScrollView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.SearchActivity;
import com.minglang.suiuu.activity.TripImageDetailsActivity;
import com.minglang.suiuu.adapter.TripImageAdapter;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.customview.NoScrollBarListView;
import com.minglang.suiuu.entity.TripImage;
import com.minglang.suiuu.entity.TripImage.TripImageData.TripImageItemData;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindDrawable;
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
public class TripImageFragment extends BaseFragment {

    private static final String TAG = TripImageFragment.class.getSimpleName();

    private static final String TAGS = "tags";
    private static final String SORT_NAME = "sortName";
    private static final String SEARCH = "search";
    private static final String NUMBER = "number";
    private static final String PAGES = "page";
    private static final String ID = "id";

    private static final String STATUS = "status";
    private static final String DATA = "data";

    private static final String SEARCH_CLASS = "searchClass";

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

    private ProgressDialog progressDialog;

    @Bind(R.id.trip_image_scroll_view)
    PullToRefreshScrollView pullToRefreshScrollView;

    @Bind(R.id.trip_image_search)
    TextView tripImageSearch;

    @Bind(R.id.trip_image_tag_layout)
    LinearLayout tripImageTagLayout;

    @Bind(R.id.trip_image_list_view)
    NoScrollBarListView noScrollBarListView;

    private TripImageAdapter adapter;

    private List<TripImageItemData> listAll = new ArrayList<>();

    private int page = 1;

    private boolean isPullToRefresh = true;

    private String[] mTagIntArray;

    private List<ImageView> imageViewList = new ArrayList<>();
    private List<ImageView> clickImageList = new ArrayList<>();
    private List<String> clickTagList = new ArrayList<>();

    private String AddTag = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trip_image, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        viewAction();
        loadTripImageList(null, "0", null, page);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initView() {
        token = SuiuuInfo.ReadAppTimeSign(getActivity());

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(DialogMsg);

        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);

        adapter = new TripImageAdapter(getActivity(), listAll);
        noScrollBarListView.setAdapter(adapter);

        mTagIntArray = getResources().getStringArray(R.array.tripImageTagName);

        initTagLayout();
    }

    private void initTagLayout() {
        int[] mPhotosIntArray = new int[]{R.drawable.tag_0, R.drawable.tag_1, R.drawable.tag_2,
                R.drawable.tag_3, R.drawable.tag_4, R.drawable.tag_5, R.drawable.tag_6};

        SimpleDraweeView imageView;
        TextView textView;

        for (int i = 0; i < mPhotosIntArray.length; i++) {
            View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.item_trip_image_tag, tripImageTagLayout, false);
            itemView.setTag(i);

            imageView = (SimpleDraweeView) itemView.findViewById(R.id.item_trip_image_tag);
            textView = (TextView) itemView.findViewById(R.id.item_trip_text_tag);

            imageViewList.add(imageView);
            imageView.setImageURI(Uri.parse("res://com.minglang.suiuu/" + mPhotosIntArray[i]));
            textView.setText(mTagIntArray[i]);

            tripImageTagLayout.addView(itemView);

            if (clickTagList.contains(mTagIntArray[i])) {
                imageView.setBackgroundResource(R.drawable.image_view_border_style);
            } else {
                imageView.setBackgroundResource(0);
            }

            itemView.setPadding(10, 0, 0, 0);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (int) v.getTag();

                    if (clickTagList.contains(mTagIntArray[tag])) {
                        imageViewList.get(tag).setBackgroundResource(0);
                        clickImageList.remove(imageViewList.get(tag));
                        clickTagList.remove(mTagIntArray[tag]);
                    } else {
                        imageViewList.get(tag).setBackground(Frame);
                        clickImageList.add(imageViewList.get(tag));
                        clickTagList.add(mTagIntArray[tag]);
                    }

                    int clickTagListSize = clickTagList.size();
                    switch (clickTagListSize) {
                        case 0:
                            AddTag = "";
                            break;
                        case 1:
                            AddTag = clickTagList.get(0);
                            break;
                        default:
                            AddTag = "";
                            for (int i = 0; i < clickTagListSize; i++) {
                                if (i == clickTagListSize - 1) {
                                    AddTag = AddTag + clickTagList.get(i);
                                } else {
                                    AddTag = AddTag + clickTagList.get(i) + ",";
                                }
                            }
                            break;
                    }

                    loadFirstPageData(null);
                }
            });

        }

    }

    private void viewAction() {

        tripImageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra(SEARCH_CLASS, 1);
                startActivity(intent);
            }
        });


        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                loadFirstPageData(null);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page++;
                isPullToRefresh = false;
                loadTripImageList(AddTag, "0", null, page);
            }

        });

        noScrollBarListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), TripImageDetailsActivity.class);
                intent.putExtra(ID, listAll.get(position).getId());
                startActivity(intent);
            }
        });

    }

    public void loadFirstPageData(String search) {
        page = 1;

        isPullToRefresh = false;

        if (listAll != null && listAll.size() > 0) {
            listAll.clear();
        }

        loadTripImageList(AddTag, "0", search, page);
    }

    /**
     * 加载旅图列表
     *
     * @param tags     标签
     * @param sortName 排序方式
     * @param search   搜索内容
     * @param page     页码
     */
    private void loadTripImageList(String tags, String sortName, String search, int page) {
        if (isPullToRefresh) {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        String newTag = "";

        if (tags != null && tags.length() > 1) {
            newTag = tags.substring(0, tags.length());
        }

        String[] keyArray1 = new String[]{TAGS, SORT_NAME, SEARCH, PAGES, NUMBER, TOKEN};
        String[] valueArray1 = new String[]{newTag, sortName, search, Integer.toString(page), "10", token};
        String url = addUrlAndParams(HttpNewServicePath.getTripImageDataPath, keyArray1, valueArray1);
        L.i(TAG, "旅图数据请求URL:" + url);
        try {
            OkHttpManager.onGetAsynRequest(url, new loadTripImageListCallBack());
        } catch (IOException e) {
            L.e(TAG, "网络请求异常:" + e.getMessage());
            Toast.makeText(getActivity(), NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        pullToRefreshScrollView.onRefreshComplete();
    }

    /**
     * 获取旅图数据回调接口
     */
    private class loadTripImageListCallBack extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String result) {
            L.i(TAG, "旅途返回数据:" + result);
            if (TextUtils.isEmpty(result)) {
                Toast.makeText(getActivity(), NoData, Toast.LENGTH_SHORT).show();
            } else try {
                JSONObject object = new JSONObject(result);
                String status = object.getString(STATUS);
                switch (status) {
                    case "1":
                        TripImage tripImage = JsonUtils.getInstance().fromJSON(TripImage.class, result);
                        List<TripImageItemData> list = tripImage.getData().getData();
                        if (list != null && list.size() > 0) {
                            listAll.addAll(list);
                            adapter.updateData(listAll);
                        } else {
                            Toast.makeText(getActivity(), NoData, Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case "-1":
                        Toast.makeText(getActivity(), SystemException, Toast.LENGTH_SHORT).show();
                        break;

                    case "-2":
                        Toast.makeText(getActivity(), object.getString(DATA), Toast.LENGTH_SHORT).show();
                        break;

                    case "-3":
                        ReturnLoginActivity(getActivity());
                        break;

                    case "-4":
                        Toast.makeText(getActivity(), object.getString(DATA), Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        Toast.makeText(getActivity(), DataError, Toast.LENGTH_SHORT).show();
                        break;
                }
            } catch (Exception e) {
                L.e(TAG, "Error Data Parse Failure:" + e.getMessage());
                Toast.makeText(getActivity(), DataError, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "网络请求失败:" + e.getMessage());
            Toast.makeText(getActivity(), NetworkError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish() {
            hideDialog();
        }

    }

}