package com.minglang.suiuu.fragment.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.minglang.pulltorefreshlibrary.PullToRefreshBase;
import com.minglang.pulltorefreshlibrary.PullToRefreshListView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.TripImageDetailsActivity;
import com.minglang.suiuu.adapter.TripImageAdapter;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.entity.TripImage;
import com.minglang.suiuu.entity.TripImage.TripImageData.TripImageItemData;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.L;
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

    private static final String HOME_PAGE = "homePage";

    private static final String ID = "id";

    private static final String STATUS = "status";
    private static final String DATA = "data";

    @BindDrawable(R.color.TripImageDividerColor)
    Drawable Divider;

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

    @Bind(R.id.trip_image_list_view)
    PullToRefreshListView tripImageListView;

    private TripImageAdapter adapter;

    private List<TripImageItemData> listAll = new ArrayList<>();

    private int page = 1;

    private String clickTag = "";

    /**
     * 点击了的标签集合
     */
    private List<String> tagList = new ArrayList<>();

    private boolean isPullToRefresh = true;

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

        tripImageListView.setMode(PullToRefreshBase.Mode.BOTH);
        ListView listView = tripImageListView.getRefreshableView();
        listView.setDivider(Divider);
        listView.setDividerHeight((int) getResources().getDimension(R.dimen.layout_10dp));

        adapter = new TripImageAdapter(getActivity(), listAll, HOME_PAGE, tagList);
        tripImageListView.setAdapter(adapter);
    }

    private void viewAction() {

        tripImageListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                loadFirstPageData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page++;
                isPullToRefresh = false;
                loadTripImageList("".equals(clickTag) ? null : clickTag, "0", null, page);
            }
        });

        tripImageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int location = position - 3;
                L.i(TAG, "location:" + location);
                Intent intent = new Intent(getActivity(), TripImageDetailsActivity.class);
                intent.putExtra(ID, listAll.get(location).getId());
                startActivity(intent);
            }

        });

        adapter.setLoadChoiceTagInterface(new TripImageAdapter.LoadChoiceTag() {
            @Override
            public void getClickTagList(List<String> tagList) {
                TripImageFragment.this.tagList = tagList;
                clickTag = "";

                if (listAll != null && listAll.size() > 0) {
                    listAll.clear();
                }

                for (String clickString : tagList) {
                    clickTag += clickString + ",";
                }

                page = 1;
                isPullToRefresh = false;
                loadTripImageList(clickTag, "0", null, page);
            }
        });

    }

    public void loadFirstPageData() {
        page = 1;

        isPullToRefresh = false;

        if (tagList != null && tagList.size() > 0) {
            tagList.clear();
        }

        if (listAll != null && listAll.size() > 0) {
            listAll.clear();
        }

        loadTripImageList(null, "0", null, page);
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
            newTag = tags.substring(0, tags.length() - 1);
        }

        String[] keyArray1 = new String[]{TAGS, SORT_NAME, SEARCH, PAGES, NUMBER, TOKEN};
        String[] valueArray1 = new String[]{newTag, sortName, search, Integer.toString(page), "10", token};
        String url = addUrlAndParams(HttpNewServicePath.getTripImageDataPath, keyArray1, valueArray1);
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

        tripImageListView.onRefreshComplete();
    }

    /**
     * 获取旅图数据回调接口
     */
    private class loadTripImageListCallBack extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String result) {
            L.i(TAG, "旅途返回的数据:" + result);
            if (TextUtils.isEmpty(result)) {
                Toast.makeText(getActivity(), NoData, Toast.LENGTH_SHORT).show();
            } else try {
                TripImage tripImage = JsonUtils.getInstance().fromJSON(TripImage.class, result);
                List<TripImageItemData> list = tripImage.getData().getData();
                if (list != null && list.size() > 0) {
                    listAll.addAll(list);
                    adapter.updateData(listAll, HOME_PAGE, tagList);
                } else {
                    Toast.makeText(getActivity(), NoData, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getString(STATUS);
                    switch (status) {
                        case "-1":
                            Toast.makeText(getActivity(), SystemException, Toast.LENGTH_SHORT).show();
                            break;

                        case "-2":
                            Toast.makeText(getActivity(), object.getString(DATA), Toast.LENGTH_SHORT).show();
                            break;

                        default:
                            Toast.makeText(getActivity(), DataError, Toast.LENGTH_SHORT).show();
                            break;
                    }
                } catch (JSONException e1) {
                    L.e(TAG, "Error Data Parse Failure:" + e1.getMessage());
                    Toast.makeText(getActivity(), DataError, Toast.LENGTH_SHORT).show();
                }
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