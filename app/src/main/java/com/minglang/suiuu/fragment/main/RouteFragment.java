package com.minglang.suiuu.fragment.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
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
    private TextView titleInfo;
    private ListView lv_suiuu;
    private JsonUtils jsonUtil = JsonUtils.getInstance();
    private List<SuiuuDataList> suiuuDataList;

    /**
     * @description PopupWindow
     */
    private PopupWindow popWindow;
    private TextView tv_country;
    private TextView tv_city;
    private TextView tv_type;
    private PullToRefreshView mPullToRefreshView;
    private int page = 1;
    private TextView tv_nodata_load;
    private mProgressDialog dialog;
    /**
     * 主页面底部导航栏
     */
    private LinearLayout tabSelect;

    private int lastVisibleItemPosition = 0;// 标记上次滑动位置

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
        //paramTest.topMargin = ConstantUtil.topHeight;
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
    }

    private void innitView(View rootView) {
//        iv_choice = (ImageView) rootView.findViewById(R.id.iv_choice);
        titleInfo = (TextView) rootView.findViewById(R.id.titleInfo);
        titleInfo.setVisibility(View.GONE);
        lv_suiuu = (ListView) rootView.findViewById(R.id.lv_suiuu);
        mPullToRefreshView = (PullToRefreshView) rootView.findViewById(R.id.main_pull_refresh_view);
        tv_nodata_load = (TextView) rootView.findViewById(R.id.tv_nodata_load);
        mPullToRefreshView.setVisibility(View.GONE);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        initPopWindow(getActivity());
        tabSelect = (LinearLayout) getActivity().findViewById(R.id.mainShowLayout).findViewById(R.id.tabSelect);
        dialog = new mProgressDialog(getActivity());


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

    private void initPopWindow(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopWindow = inflater.inflate(R.layout.popuwindowforchoicesuiuu, null, false);
        popWindow = new PopupWindow(vPopWindow, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(false);
        tv_country = (TextView) vPopWindow.findViewById(R.id.tv_country);
        tv_city = (TextView) vPopWindow.findViewById(R.id.tv_city);
        tv_type = (TextView) vPopWindow.findViewById(R.id.tv_type);
        tv_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popWindow.dismiss();
                lv_suiuu.setClickable(true);

            }
        });
    }

    private void showPopWindow(View parent) {
        int[] location = new int[2];
        parent.getLocationOnScreen(location);
        popWindow.showAtLocation(parent, Gravity.NO_GRAVITY, location[0] + parent.getWidth(), location[1] + parent.getHeight());
    }
}
