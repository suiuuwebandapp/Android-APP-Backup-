package com.minglang.suiuu.fragment.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.SuiuuDetailsActivity;
import com.minglang.suiuu.activity.SuiuuSearchActivity;
import com.minglang.suiuu.adapter.ShowSuiuuAdapter;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.customview.ReFlashListView;
import com.minglang.suiuu.customview.TextProgressDialog;
import com.minglang.suiuu.entity.SuiuuDataList;
import com.minglang.suiuu.entity.SuiuuReturnDate;
import com.minglang.suiuu.utils.AppUtils;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 随游页面
 */
public class SuiuuFragment extends BaseFragment
        implements ReFlashListView.IReflashListener, ReFlashListView.ILoadMoreDataListener {
    private ReFlashListView suiuuListView;
    private JsonUtils jsonUtil = JsonUtils.getInstance();
    private List<SuiuuDataList> suiuuDataList;
    private int page = 1;
    private TextView noDataLoad;
    private TextProgressDialog dialog;
    /**
     * 主页面底部导航栏
     */
    private LinearLayout tabSelect;
    private int lastVisibleItemPosition = 0;// 标记上次滑动位置
    //头部空间
    private ImageView et_suiuu;
    private ShowSuiuuAdapter adapter;

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), android.R.style.Theme_Light_NoTitleBar);
        inflater.cloneInContext(contextThemeWrapper);
        View rootView = inflater.inflate(R.layout.fragment_route, null);
        initView(rootView);
        loadDate(null, null, null, null, null, page);
        viewAction();
        return rootView;
    }

    private void initView(View rootView) {
        suiuuDataList = new ArrayList<>();
        suiuuListView = (ReFlashListView) rootView.findViewById(R.id.lv_suiuu);
        noDataLoad = (TextView) rootView.findViewById(R.id.tv_noDataLoad);
        View topView = getActivity().findViewById(R.id.main_show_layout);
        tabSelect = (LinearLayout) topView.findViewById(R.id.tab_select_card_layout);
        dialog = new TextProgressDialog(getActivity());
        //处理头部控件
        et_suiuu = (ImageView) topView.findViewById(R.id.main_2_search);
    }

    private void viewAction() {
        et_suiuu.setOnClickListener(new MyOnclick());
        suiuuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SuiuuDetailsActivity.class);
                intent.putExtra("tripId", suiuuDataList.get(position - 1).getTripId());
                startActivity(intent);
            }
        });

        noDataLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                loadDate(null, null, null, null, null, page);
                noDataLoad.setVisibility(View.GONE);
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
    private void loadDate(String countryOrCity, String peopleCount, String tags, String startPrice, String endPrice, int page) {
        dialog.showDialog();
        String str = SuiuuInfo.ReadVerification(getActivity().getApplicationContext());
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, str);
        params.addBodyParameter("cc", countryOrCity);
        params.addBodyParameter("peopleCount", peopleCount);
        params.addBodyParameter("tag", tags);
        params.addBodyParameter("startPrice", startPrice);
        params.addBodyParameter("endPrice", endPrice);
        params.addBodyParameter("page", Integer.toString(page));
        params.addBodyParameter("number", "10");
        SuHttpRequest suHttpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getSuiuuList, new getSuiuuDateCallBack());
        suHttpRequest.setParams(params);
        suHttpRequest.executive();
    }

    @Override
    public void onLoadMoreData() {
        if (!dialog.isShow()) {
            page += 1;
            loadDate(null, null, null, null, null, page);
        }
        suiuuListView.loadComplete();
    }

    @Override
    public void onReflash() {
        suiuuDataList.clear();
        page = 1;
        adapter = null;
        loadDate(null, null, null, null, null, page);
        suiuuListView.reflashComplete();
    }
    private void showList(List<SuiuuDataList> suiuuDataList) {
        if (adapter == null) {
            suiuuListView.setInterface(this);
            suiuuListView.setLoadMoreInterface(this);
            adapter = new ShowSuiuuAdapter(getActivity().getApplication(), suiuuDataList);
            suiuuListView.setAdapter(adapter);
        } else {
            adapter.onDateChange(suiuuDataList);
        }
    }
    /**
     * 获取随游列表的回调接口
     */
    class getSuiuuDateCallBack extends RequestCallBack<String> {
        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            dialog.dismissDialog();
            try {
                JSONObject json = new JSONObject(stringResponseInfo.result);
                String status = json.getString("status");
                if ("1".equals(status)) {
                    SuiuuReturnDate baseCollection = jsonUtil.fromJSON(SuiuuReturnDate.class,stringResponseInfo.result);
                    List<SuiuuDataList> suiuuDataListNew = baseCollection.getData();
                    if (suiuuDataListNew.size() < 1) {
                        Toast.makeText(getActivity().getApplicationContext(), "数据加载完毕", Toast.LENGTH_SHORT).show();
                    }
                    suiuuDataList.addAll(suiuuDataListNew);
                    showList(suiuuDataList);
                    tabSelect.setVisibility(View.VISIBLE);
                } else if ("-3".equals(status)) {
                    Toast.makeText(getActivity().getApplicationContext(), "登录信息过期,请重新登录", Toast.LENGTH_SHORT).show();
                    AppUtils.intentLogin(getActivity());
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "数据获取失败，请重试！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            dialog.dismissDialog();
            Toast.makeText(getActivity().getApplicationContext(), "数据获取失败，请重试！", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("deprecation")
    class MyOnclick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.main_2_search:
                    //跳转到搜索页面
                    Intent intent = new Intent(getActivity(),SuiuuSearchActivity.class);
                    intent.putExtra("searchClass",2);
                    startActivity(intent);
                    break;
            }
        }
    }

}

