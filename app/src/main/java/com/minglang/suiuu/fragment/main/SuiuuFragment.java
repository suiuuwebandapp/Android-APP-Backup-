package com.minglang.suiuu.fragment.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.minglang.pulltorefreshlibrary.PullToRefreshBase;
import com.minglang.pulltorefreshlibrary.PullToRefreshListView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.SuiuuDetailsActivity;
import com.minglang.suiuu.adapter.ShowSuiuuAdapter;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.entity.SuiuuData;
import com.minglang.suiuu.entity.SuiuuItemData;
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
 * 随游页面
 */
public class SuiuuFragment extends BaseFragment {

    private static final String TAG = SuiuuFragment.class.getSimpleName();

    private static final String CC = "cc";
    private static final String PEOPLE_COUNT = "peopleCount";
    private static final String TAGS = "tag";
    private static final String START_PRICE = "startPrice";
    private static final String END_PRICE = "endPrice";

    private static final String STATUS = "status";
    private static final String DATA = "data";

    private static final String TRIP_ID = "tripId";

    private static final String CLASS_NAME = "className";

    @BindDrawable(R.color.DefaultGray1)
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

    @Bind(R.id.suiuu_list_view)
    PullToRefreshListView pullToRefreshListView;

    private JsonUtils jsonUtil = JsonUtils.getInstance();

    private List<SuiuuItemData> listAll = new ArrayList<>();

    private int page = 1;

    private ShowSuiuuAdapter adapter;

    private boolean isPullToRefresh = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), android.R.style.Theme_Light_NoTitleBar);
        inflater.cloneInContext(contextThemeWrapper);
        View rootView = inflater.inflate(R.layout.fragment_suiuu, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        sendRequestSuiuuData(null, null, null, null, null, page);
        viewAction();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initView() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(DialogMsg);

        token = SuiuuInfo.ReadAppTimeSign(getActivity());

        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        ListView listView = pullToRefreshListView.getRefreshableView();
        listView.setDivider(Divider);
        listView.setDividerHeight((int) getResources().getDimension(R.dimen.layout_5dp));

        adapter = new ShowSuiuuAdapter(getActivity(), listAll);
        pullToRefreshListView.setAdapter(adapter);
    }

    private void viewAction() {

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = 1;

                isPullToRefresh = false;

                if (listAll != null && listAll.size() > 0) {
                    listAll.clear();
                }

                sendRequestSuiuuData(null, null, null, null, null, page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page++;

                isPullToRefresh = false;

                sendRequestSuiuuData(null, null, null, null, null, page);
            }
        });

        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int location = position - 1;
                Intent intent = new Intent(getActivity(), SuiuuDetailsActivity.class);
                intent.putExtra(TRIP_ID, listAll.get(location).getTripId());
                intent.putExtra(USER_SIGN, listAll.get(location).getUserSign());
                intent.putExtra(HEAD_IMG, listAll.get(location).getHeadImg());
                intent.putExtra(CLASS_NAME, TAG);
                startActivity(intent);
            }
        });

    }

    /**
     * @param countryOrCity 国家或城市
     * @param peopleCount   人数
     * @param tags          标签
     * @param startPrice    起始价格
     * @param endPrice      目标价格
     * @param page          页码
     */
    private void sendRequestSuiuuData(String countryOrCity, String peopleCount, String tags, String startPrice, String endPrice, int page) {
        if (isPullToRefresh) {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        String[] keyArray1 = new String[]{CC, PEOPLE_COUNT, TAGS, START_PRICE, END_PRICE, PAGE, NUMBER, TOKEN};
        String[] valueArray1 = new String[]{countryOrCity, peopleCount, tags, startPrice, endPrice, Integer.toString(page), "10", token};
        String url = addUrlAndParams(HttpNewServicePath.getSuiuuList, keyArray1, valueArray1);
        L.i(TAG, "随游数据请求URL:" + url);
        try {
            OkHttpManager.onGetAsynRequest(url, new SuiuuDataCallBack());
        } catch (IOException e) {
            L.e(TAG, "网络请求异常:" + e.getMessage());
            Toast.makeText(getActivity(), NetworkError, Toast.LENGTH_SHORT).show();
        }
    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        pullToRefreshListView.onRefreshComplete();
    }

    /**
     * 获取随游列表的回调接口
     */
    private class SuiuuDataCallBack extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            if (TextUtils.isEmpty(response)) {
                Toast.makeText(getActivity(), NoData, Toast.LENGTH_SHORT).show();
            } else try {
                JSONObject object = new JSONObject(response);
                String status = object.getString(STATUS);
                switch (status) {
                    case "1":
                        SuiuuData suiuuData = jsonUtil.fromJSON(SuiuuData.class, response);
                        List<SuiuuItemData> list = suiuuData.getData();
                        if (list != null && list.size() > 0) {
                            listAll.addAll(list);
                            adapter.setList(listAll);
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