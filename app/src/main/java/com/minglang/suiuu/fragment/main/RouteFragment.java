package com.minglang.suiuu.fragment.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.customview.PullToRefreshView;
import com.minglang.suiuu.customview.mProgressDialog;
import com.minglang.suiuu.customview.rangebar.RangeBar;
import com.minglang.suiuu.entity.SuiuuDataList;
import com.minglang.suiuu.entity.SuiuuReturnDate;
import com.minglang.suiuu.utils.AppUtils;
import com.minglang.suiuu.utils.ConstantUtil;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.SystemBarTintManager;

import java.util.Date;
import java.util.List;

/**
 * 随游页面
 */
public class RouteFragment extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {
    private ListView lv_suiuu;
    private JsonUtils jsonUtil = JsonUtils.getInstance();
    private List<SuiuuDataList> suiuuDataList;

    /**
     * @description PopupWindow
     */
    private PullToRefreshView mPullToRefreshView;
    private int page = 1;
    private TextView tv_nodata_load;
    private mProgressDialog dialog;
    /**
     * 主页面底部导航栏
     */
    private LinearLayout tabSelect;

    private int lastVisibleItemPosition = 0;// 标记上次滑动位置
    //头部空间
    private TextView titleInfo;
    private RelativeLayout rl_top_info;
    private EditText et_suiuu;
    private ImageView iv_suiuu_search_more;
    private ImageButton ib_suiuu_search;
    private FrameLayout fl_search_more;
    private ImageButton ib_release;
    private EditText et_peple_number;
    private ImageButton ib_plus;

    /**
     * 参与的人数
     */
    private int enjoy_peopleNumber;
    private RangeBar rangebar;
    private TextView tv_price_range;
    private int startTick;
    private int endTick;

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_route, null);
        if (ConstantUtil.isKITKAT) {
            RelativeLayout rootLayout = (RelativeLayout) rootView.findViewById(R.id.suiuu_root);
            rootLayout.setPadding(0, new SystemBarTintManager(getActivity()).getConfig().getStatusBarHeight(), 0, 0);
        }
        innitView(rootView);
        loadDate(null, null, null, page);
        LinearLayout.LayoutParams paramTest = (LinearLayout.LayoutParams) lv_suiuu.getLayoutParams();
        paramTest.setMargins(10, ConstantUtil.topHeight + 10, 10, 0);
        lv_suiuu.setLayoutParams(paramTest);

        viewAction();
        return rootView;
    }

    private void viewAction() {

        final Animation transAnim = new TranslateAnimation(0, 0, tabSelect.getHeight(), 0);
//        final Animation transAnim = new TranslateAnimation(0,0, dm.heightPixels, dm.heightPixels-tabSelect.getHeight());
        transAnim.setFillAfter(true);
        transAnim.setDuration(500);

        final Animation transAnimTo = new TranslateAnimation(0, 0, 0, tabSelect.getHeight());
//        final Animation transAnimTo = new TranslateAnimation(0,0, dm.heightPixels-tabSelect.getHeight(), dm.heightPixels);
        transAnimTo.setFillAfter(true);
        transAnimTo.setDuration(300);

        lv_suiuu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SuiuuDetailActivity.class);
                intent.putExtra("tripId", suiuuDataList.get(position).getTripId());
                startActivity(intent);
            }
        });

        tv_nodata_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                loadDate(null, null, null, page);
                tv_nodata_load.setVisibility(View.GONE);
            }
        });
        //滚动监听
        lv_suiuu.setOnScrollListener(new AbsListView.OnScrollListener() {

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
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex,
                                              int rightPinIndex,
                                              String leftPinValue, String rightPinValue) {
                Log.i("suiuu", leftPinIndex + "----------------" + rightPinIndex);
                startTick = leftPinIndex;
                endTick = rightPinIndex;
                tv_price_range.setText(leftPinIndex * 1000 + "--" + rightPinIndex * 1000);
            }
        });
    }

    private void innitView(View rootView) {
        lv_suiuu = (ListView) rootView.findViewById(R.id.lv_suiuu);
        mPullToRefreshView = (PullToRefreshView) rootView.findViewById(R.id.main_pull_refresh_view);
        tv_nodata_load = (TextView) rootView.findViewById(R.id.tv_nodata_load);
        mPullToRefreshView.setVisibility(View.GONE);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        View topView = getActivity().findViewById(R.id.mainShowLayout);
        tabSelect = (LinearLayout) topView.findViewById(R.id.tabSelect);
        dialog = new mProgressDialog(getActivity());
        //处理头部控件
        titleInfo = (TextView) topView.findViewById(R.id.titleInfo);
        titleInfo.setVisibility(View.GONE);
        rl_top_info = (RelativeLayout) topView.findViewById(R.id.rl_top_info);
        rl_top_info.setVisibility(View.VISIBLE);
        et_suiuu = (EditText) topView.findViewById(R.id.et_suiuu);
        iv_suiuu_search_more = (ImageView) topView.findViewById(R.id.iv_suiuu_search_more);
        ib_suiuu_search = (ImageButton) topView.findViewById(R.id.ib_suiuu_search);
        ib_suiuu_search.setOnClickListener(new MyOnclick());
        iv_suiuu_search_more.setVisibility(View.VISIBLE);

        fl_search_more = (FrameLayout) rootView.findViewById(R.id.fl_search_more);
        iv_suiuu_search_more.setOnClickListener(new MyOnclick());
        ib_release = (ImageButton) rootView.findViewById(R.id.ib_release);
        ib_release.setOnClickListener(new MyOnclick());
        et_peple_number = (EditText) rootView.findViewById(R.id.et_peple_number);
        et_peple_number.setOnClickListener(new MyOnclick());
        ib_plus = (ImageButton) rootView.findViewById(R.id.ib_plus);
        ib_plus.setOnClickListener(new MyOnclick());
        rangebar = (RangeBar) rootView.findViewById(R.id.rangebar);
        rangebar.setTickStart(0);
        rangebar.setTickEnd(10);
        rangebar.setTickInterval(1);
        tv_price_range = (TextView) rootView.findViewById(R.id.tv_price_range);

    }

    /**
     * 加载数据
     *
     * @param countryId
     * @param cityId
     * @param tags
     */
    private void loadDate(String countryId, String cityId, String tags, int page) {
        dialog.showDialog();
        String str = SuiuuInfo.ReadVerification(getActivity().getApplicationContext());
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, str);
        if (!TextUtils.isEmpty(countryId)) {
            params.addBodyParameter("countryId", countryId);
        }
        if (!TextUtils.isEmpty(cityId)) {
            params.addBodyParameter("cityId", cityId);
        }
        if (!TextUtils.isEmpty(tags)) {
            params.addBodyParameter("tag", tags);
        }
        params.addBodyParameter("page", Integer.toString(page));
        params.addBodyParameter("number", "20");
        SuHttpRequest suHttpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getSuiuuList, new getSuiuuDateCallBack());
        suHttpRequest.setParams(params);
        suHttpRequest.requestNetworkData();
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        mPullToRefreshView.postDelayed(new Runnable() {

            @Override
            public void run() {
                //重新加载数据
                if (!dialog.isShow()) {
                    page += 1;
                    loadDate(null, null, null, page);
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
                loadDate(null, null, null, page);
                mPullToRefreshView.onHeaderRefreshComplete("最近更新:" + new Date().toLocaleString());
                mPullToRefreshView.onHeaderRefreshComplete();
            }
        }, 1000);


    }


    /**
     * 获取随游列表的回调接口
     */
    class getSuiuuDateCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            Log.i("suiuu", stringResponseInfo.result + "what-----------");
            dialog.dismissDialog();
            try {
                SuiuuReturnDate baseCollection = jsonUtil.fromJSON(SuiuuReturnDate.class, stringResponseInfo.result);
                if ("1".equals(baseCollection.getStatus())) {
                    mPullToRefreshView.setVisibility(View.VISIBLE);
                    suiuuDataList = baseCollection.getData();
                    if (suiuuDataList.size() < 1) {
                        mPullToRefreshView.setVisibility(View.INVISIBLE);
                        tv_nodata_load.setVisibility(View.VISIBLE);
                    }
                    lv_suiuu.setAdapter(new ShowSuiuuAdapter(getActivity().getApplicationContext(), suiuuDataList));
                    tabSelect.setVisibility(View.VISIBLE);
                } else if ("-3".equals(baseCollection.getStatus())) {
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

    public void fragmentShow() {
        if (fl_search_more.isShown()) {
            fl_search_more.setVisibility(View.GONE);

        } else {

            fl_search_more.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams paramTest1 = (RelativeLayout.LayoutParams) fl_search_more.getLayoutParams();
            paramTest1.setMargins(10, ConstantUtil.topHeight + 10, 10, 0);
            fl_search_more.setLayoutParams(paramTest1);
        }
    }

    class MyOnclick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            enjoy_peopleNumber = Integer.valueOf(String.valueOf(et_peple_number.getText()));
            switch (v.getId()) {
                case R.id.iv_suiuu_search_more:
                    fragmentShow();
                    break;
                case R.id.ib_release:
                    if (enjoy_peopleNumber != 0) {
                        et_peple_number.setText(String.valueOf(enjoy_peopleNumber - 1));
                    }
                    break;
                case R.id.ib_plus:
                    et_peple_number.setText(String.valueOf(enjoy_peopleNumber + 1));
                    break;
                case R.id.ib_suiuu_search:
                    Log.i("suiuu", "点击了搜索");
                    if (fl_search_more.isShown()) {
                        et_suiuu.getText();
                        loadDate(null, null, null, page);
                    }
                    break;
            }
        }
    }

}
