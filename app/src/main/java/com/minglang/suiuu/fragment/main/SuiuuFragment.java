package com.minglang.suiuu.fragment.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.minglang.suiuu.adapter.ShowSuiuuAdapter;
import com.minglang.suiuu.application.SuiuuApplication;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.customview.FlowLayout;
import com.minglang.suiuu.customview.PullToRefreshView;
import com.minglang.suiuu.customview.TextProgressDialog;
import com.minglang.suiuu.customview.rangebar.RangeBar;
import com.minglang.suiuu.entity.SuiuuDataList;
import com.minglang.suiuu.entity.SuiuuReturnDate;
import com.minglang.suiuu.entity.SuiuuSearchTag;
import com.minglang.suiuu.utils.AppUtils;
import com.minglang.suiuu.utils.ConstantUtils;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.squareup.leakcanary.RefWatcher;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 随游页面
 */
public class SuiuuFragment extends BaseFragment
        implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {

    private List<TextView> list = new ArrayList<>();
    private List<TextView> listClick = new ArrayList<>();

    private List<String> tagList;
    private ListView suiuuListView;
    private JsonUtils jsonUtil = JsonUtils.getInstance();
    private List<SuiuuDataList> suiuuDataList;

    /**
     * @description PopupWindow
     */
    private PullToRefreshView mPullToRefreshView;
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
    private ImageView iv_suiuu_search_more;
    private ImageButton ib_suiuu_search;
    private FrameLayout fl_search_more;
    private ImageButton ib_release;
    private EditText peopleNumber;
    private ImageButton ib_plus;

    private RangeBar rangebar;
    private TextView tv_price_range;
    private int startTick = 0;
    private int endTick = 10000;

    private FlowLayout flowLayout;
    private boolean isSearch;
    private RelativeLayout rl_search_no_data;
    private ImageView main_suiuu_pic;
    private ImageView main_suiuu_record;
    private ImageView main_suiuu_ask;


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
        tagList = new ArrayList<>();
        suiuuListView = (ListView) rootView.findViewById(R.id.lv_suiuu);
        mPullToRefreshView = (PullToRefreshView) rootView.findViewById(R.id.main_pull_refresh_view);
        noDataLoad = (TextView) rootView.findViewById(R.id.tv_noDataLoad);
        mPullToRefreshView.setVisibility(View.GONE);

        View topView = getActivity().findViewById(R.id.mainShowLayout);
        tabSelect = (LinearLayout) topView.findViewById(R.id.tabSelect);
        dialog = new TextProgressDialog(getActivity());

        //处理头部控件
        et_suiuu = (EditText) topView.findViewById(R.id.et_suiuu);
        iv_suiuu_search_more = (ImageView) topView.findViewById(R.id.iv_suiuu_search_more);
        ib_suiuu_search = (ImageButton) topView.findViewById(R.id.ib_suiuu_search);

        main_suiuu_pic = (ImageView) topView.findViewById(R.id.main_pic);
        main_suiuu_record = (ImageView) topView.findViewById(R.id.main_record);
        main_suiuu_ask = (ImageView) topView.findViewById(R.id.main_ask);

        fl_search_more = (FrameLayout) rootView.findViewById(R.id.fl_search_more);

        ib_release = (ImageButton) rootView.findViewById(R.id.ib_release);

        peopleNumber = (EditText) rootView.findViewById(R.id.et_people_number);
        peopleNumber.setKeyListener(null);

        ib_plus = (ImageButton) rootView.findViewById(R.id.ib_plus);

        rangebar = (RangeBar) rootView.findViewById(R.id.rangeBar);
        rangebar.setTickStart(0);
        rangebar.setTickEnd(10);
        rangebar.setTickInterval(1);

        tv_price_range = (TextView) rootView.findViewById(R.id.tv_price_range);
        flowLayout = (FlowLayout) rootView.findViewById(R.id.id_flowLayout);
        rl_search_no_data = (RelativeLayout) rootView.findViewById(R.id.rl_search_no_data);

        LinearLayout.LayoutParams paramTest = (LinearLayout.LayoutParams) suiuuListView.getLayoutParams();
        paramTest.setMargins(10, ConstantUtils.topHeight + 10, 10, 0);
        suiuuListView.setLayoutParams(paramTest);
    }

    private void viewAction() {

        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);

        iv_suiuu_search_more.setOnClickListener(new MyOnclick());
        ib_release.setOnClickListener(new MyOnclick());
        ib_plus.setOnClickListener(new MyOnclick());
        ib_suiuu_search.setOnClickListener(new MyOnclick());
        rl_search_no_data.setOnClickListener(new MyOnclick());

        final Animation transAnim = new TranslateAnimation(0, 0, tabSelect.getHeight(), 0);
        transAnim.setFillAfter(true);
        transAnim.setDuration(500);

        final Animation transAnimTo = new TranslateAnimation(0, 0, 0, tabSelect.getHeight());
        transAnimTo.setFillAfter(true);
        transAnimTo.setDuration(300);

        suiuuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SuiuuDetailActivity.class);
                intent.putExtra("tripId", suiuuDataList.get(position).getTripId());
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

        //滚动监听
        suiuuListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {


                switch (scrollState) {
                    case SCROLL_STATE_IDLE://空闲状态
                        tabSelect.setVisibility(View.VISIBLE);
                        break;
                    case SCROLL_STATE_FLING://滚动状态
                        tabSelect.setVisibility(View.GONE);
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL://触摸后滚动
                        tabSelect.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                hideWhenScroll();
                if (firstVisibleItem > lastVisibleItemPosition) {// 上滑
                    transAnim.cancel();
                    tabSelect.startAnimation(transAnim);
                    tabSelect.setVisibility(View.VISIBLE);
                } else if (firstVisibleItem < lastVisibleItemPosition) {// 下滑
                    tabSelect.setVisibility(View.GONE);
                }
                lastVisibleItemPosition = firstVisibleItem;
            }
        });

        rangebar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue,
                                              String rightPinValue) {
                startTick = leftPinIndex * 1000;
                endTick = rightPinIndex * 1000;
                tv_price_range.setText(leftPinIndex * 1000 + "--" + rightPinIndex * 1000);
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
        params.addBodyParameter("number", "20");

        SuHttpRequest suHttpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getSuiuuList, new getSuiuuDateCallBack());
        suHttpRequest.setParams(params);
        suHttpRequest.requestNetworkData();
    }

    private void getSuiuuSearchTag() {
        String str = SuiuuInfo.ReadVerification(getActivity());
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, str);
        SuHttpRequest suHttpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getSuiuuSearchTag, new getSuiuuSearchTagCallBack());
        suHttpRequest.setParams(params);
        suHttpRequest.requestNetworkData();
    }

    public void fragmentShow() {
        if (fl_search_more.isShown()) {
            suiuuListView.setEnabled(true);
            fl_search_more.setVisibility(View.GONE);
        } else {
            suiuuListView.setEnabled(false);
            fl_search_more.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams paramTest1 = (RelativeLayout.LayoutParams) fl_search_more.getLayoutParams();
            paramTest1.setMargins(10, ConstantUtils.topHeight + 10, 10, 0);
            fl_search_more.setLayoutParams(paramTest1);
            if (tagList.size() < 1) {
                getSuiuuSearchTag();
            }
        }
    }

    public void setViewGroup() {
        flowLayout.removeAllViews();
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        for (int i = 0; i < tagList.size() - 1; i++) {
            TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                    flowLayout, false);
            tv.setText(tagList.get(i));
            tv.setId(i);
            tv.setOnClickListener(new MyOnclick());
            list.add(tv);
            flowLayout.addView(tv);

        }
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        mPullToRefreshView.postDelayed(new Runnable() {

            @Override
            public void run() {
                //重新加载数据
                if (!dialog.isShow()) {
                    page += 1;
                    if (isSearch) {
                        loadDate(searchText, "0".equals(enjoyPeopleCount) ? "" : enjoyPeopleCount, "".equals(tags) ? tags : tags.substring(0, tags.length() - 1), Integer.toString(startTick), Integer.toString(endTick), page);
                    } else {

                        loadDate(null, null, null, null, null, page);
                    }
                    mPullToRefreshView.onFooterRefreshComplete();
                }
            }
        }, 1000);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        mPullToRefreshView.postDelayed(new Runnable() {

            @Override
            public void run() {
                // 设置更新时间
                page = 1;
                isSearch = false;
                loadDate(null, null, null, null, null, page);
//                mPullToRefreshView.onHeaderRefreshComplete("最近更新:" + new Date().toLocaleString());
                mPullToRefreshView.onHeaderRefreshComplete("最近更新:" + DateFormat.getDateTimeInstance().format(new Date()));
                mPullToRefreshView.onHeaderRefreshComplete();
            }
        }, 1000);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = SuiuuApplication.getRefWatcher();
        refWatcher.watch(this);
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
                    SuiuuReturnDate baseCollection = jsonUtil.fromJSON(SuiuuReturnDate.class, stringResponseInfo.result);
                    mPullToRefreshView.setVisibility(View.VISIBLE);
                    suiuuDataList = baseCollection.getData();

                    if (suiuuDataList.size() < 1) {
                        if (isSearch) {
                            rl_search_no_data.setVisibility(View.VISIBLE);
                            mPullToRefreshView.setVisibility(View.INVISIBLE);
                            page = 1;
                        } else {
                            mPullToRefreshView.setVisibility(View.INVISIBLE);
                            noDataLoad.setVisibility(View.VISIBLE);
                        }
                    }
                    suiuuListView.setAdapter(new ShowSuiuuAdapter(getActivity(), suiuuDataList));
                    tabSelect.setVisibility(View.VISIBLE);
                } else if ("-3".equals(status)) {
                    Toast.makeText(getActivity().getApplicationContext(), "登录信息过期,请重新登录", Toast.LENGTH_SHORT).show();
                    AppUtils.intentLogin(getActivity());
                    getActivity().finish();
                } else {
                    rl_search_no_data.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity().getApplicationContext(), "数据获取失败，请重试！", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            dialog.dismissDialog();
            rl_search_no_data.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity().getApplicationContext(), "数据获取失败，请重试！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取随游列表的回调接口
     */
    class getSuiuuSearchTagCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            try {
                SuiuuSearchTag suiuuSearchTag = jsonUtil.fromJSON(SuiuuSearchTag.class, stringResponseInfo.result);
                if ("1".equals(suiuuSearchTag.getStatus())) {
                    tagList = suiuuSearchTag.getData();
                    setViewGroup();
                } else {
                    tagList.add("家庭");
                    tagList.add("美食");
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

    String tags = "";
    String searchText;
    String enjoyPeopleCount;

    @SuppressWarnings("deprecation")
    class MyOnclick implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            //参与的人数
            int enjoy_peopleNumber = Integer.valueOf(String.valueOf(peopleNumber.getText()));
            for (int i = 0; i < tagList.size() - 1; i++) {
                if (v.getId() == i) {
                    if (listClick.contains(list.get(i))) {
                        list.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.tv_bg1));
                        listClick.remove(list.get(i));
                    } else {
                        list.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.tv_bg));
                        listClick.add(list.get(i));
                    }
                }
            }

            switch (v.getId()) {
                case R.id.iv_suiuu_search_more:
                    fragmentShow();
                    break;

                case R.id.rl_search_no_data:
                    rl_search_no_data.setVisibility(View.GONE);
                    loadDate(null, null, null, null, null, page);
                    break;

                case R.id.ib_release:
                    if (enjoy_peopleNumber != 0) {
                        peopleNumber.setText(String.valueOf(enjoy_peopleNumber - 1));
                    }
                    break;

                case R.id.ib_plus:
                    peopleNumber.setText(String.valueOf(enjoy_peopleNumber + 1));
                    break;

                case R.id.ib_suiuu_search:
                    page = 1;

                    suiuuListView.setEnabled(true);
                    searchText = String.valueOf(et_suiuu.getText());
                    if ("".equals(searchText) && !fl_search_more.isShown()) {
                        Toast.makeText(getActivity(), R.string.please_enter_search_content, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    isSearch = true;

                    if (fl_search_more.isShown()) {
                        enjoyPeopleCount = peopleNumber.getText().toString().trim();

                        for (TextView textV : listClick) {
                            tags += textV.getText() + ",";
                        }

                        loadDate(searchText, "0".equals(enjoyPeopleCount) ? "" :
                                        enjoyPeopleCount, "".equals(tags) ? tags : tags.substring(0, tags.length() - 1),
                                Integer.toString(startTick), Integer.toString(endTick), page);

                        fl_search_more.setVisibility(View.GONE);
                    } else {
                        loadDate(searchText, null, null, null, null, page);
                    }
                    tags = "";
                    break;
            }
        }
    }

}

