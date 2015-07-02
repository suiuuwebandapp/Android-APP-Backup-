package com.minglang.suiuu.fragment.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.SuiuuDetailActivity;
import com.minglang.suiuu.activity.SuiuuSearchActivity;
import com.minglang.suiuu.adapter.ShowSuiuuAdapter;
import com.minglang.suiuu.application.SuiuuApplication;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.customview.ReFlashListView;
import com.minglang.suiuu.customview.TextProgressDialog;
import com.minglang.suiuu.entity.SuiuuDataList;
import com.minglang.suiuu.entity.SuiuuReturnDate;
import com.minglang.suiuu.utils.AppUtils;
import com.minglang.suiuu.utils.ConstantUtils;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.squareup.leakcanary.RefWatcher;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 随游页面
 */
public class SuiuuFragment extends BaseFragment
        implements ReFlashListView.IReflashListener, ReFlashListView.ILoadMoreDataListener, ReFlashListView.IScrollListener {
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
    private EditText et_suiuu;
    private ImageView main_suiuu_pic;
    private ImageView main_suiuu_record;
    private ImageView main_suiuu_ask;
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
        suiuuListView.setIScrollListener(this);
        noDataLoad = (TextView) rootView.findViewById(R.id.tv_noDataLoad);
        View topView = getActivity().findViewById(R.id.mainShowLayout);
        tabSelect = (LinearLayout) topView.findViewById(R.id.tabSelect);
        dialog = new TextProgressDialog(getActivity());
        //处理头部控件
        et_suiuu = (EditText) topView.findViewById(R.id.et_suiuu);
        main_suiuu_pic = (ImageView) topView.findViewById(R.id.main_3_pic);
        main_suiuu_record = (ImageView) topView.findViewById(R.id.main_3_record);
        main_suiuu_ask = (ImageView) topView.findViewById(R.id.main_3_ask);

        RelativeLayout.LayoutParams paramTest = (RelativeLayout.LayoutParams) suiuuListView.getLayoutParams();
        paramTest.setMargins(10, ConstantUtils.topHeight + 10, 10, 0);
        suiuuListView.setLayoutParams(paramTest);
    }

    private void viewAction() {
        et_suiuu.setOnClickListener(new MyOnclick());
        suiuuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SuiuuDetailActivity.class);
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

    public void hideWhenScroll() {
        if (ConstantUtils.isShowArticleAnim) {
            main_suiuu_record.setVisibility(View.INVISIBLE);
            main_suiuu_pic.setVisibility(View.INVISIBLE);
            main_suiuu_ask.setVisibility(View.INVISIBLE);
            ConstantUtils.isShowArticleAnim = false;
        }
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
        et_suiuu.setText("");

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
        suHttpRequest.requestNetworkData();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = SuiuuApplication.getRefWatcher();
        refWatcher.watch(this);
    }

    @Override
    public void onLoadMoreData() {
        Log.i("suiuu", "加载更多数据了");
        if (!dialog.isShow()) {
            page += 1;
            loadDate(null, null, null, null, null, page);
        }

        suiuuListView.loadComplete();
    }

    @Override
    public void onReflash() {
        Log.i("suiuu", "下拉刷新了");
        suiuuDataList.clear();
        page = 1;
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


    @Override
    public void onScroll() {
        final Animation transAnim = new TranslateAnimation(0, 0, tabSelect.getHeight(), 0);
        transAnim.setFillAfter(true);
        transAnim.setDuration(500);

        final Animation transAnimTo = new TranslateAnimation(0, 0, 0, tabSelect.getHeight());
        transAnimTo.setFillAfter(true);
        transAnimTo.setDuration(300);

        hideWhenScroll();
        if (suiuuListView.getFirstVisiblePosition() > lastVisibleItemPosition) {// 上滑
            transAnim.cancel();
            tabSelect.startAnimation(transAnim);
            tabSelect.setVisibility(View.VISIBLE);
        } else if (suiuuListView.getFirstVisiblePosition() < lastVisibleItemPosition) {// 下滑
            tabSelect.setVisibility(View.GONE);
        }
        lastVisibleItemPosition = suiuuListView.getFirstVisiblePosition();

    }


    @Override
    public void onScrollStateChanged(int state) {
        switch (state) {
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE://空闲状态
                tabSelect.setVisibility(View.VISIBLE);
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING://滚动状态
                tabSelect.setVisibility(View.GONE);
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL://触摸后滚动
                tabSelect.setVisibility(View.GONE);
                break;
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
                Log.i("suiuu","suiuu="+stringResponseInfo.result);
                String status = json.getString("status");
                if ("1".equals(status)) {
                    SuiuuReturnDate baseCollection = jsonUtil.fromJSON(SuiuuReturnDate.class, stringResponseInfo.result);

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
                case R.id.et_suiuu:
                    //跳转到搜索页面
                    startActivity(new Intent(getActivity(), SuiuuSearchActivity.class));
                    break;
            }
        }
    }

}

