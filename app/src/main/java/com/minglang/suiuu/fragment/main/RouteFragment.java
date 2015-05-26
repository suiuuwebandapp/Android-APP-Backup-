package com.minglang.suiuu.fragment.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import com.minglang.suiuu.customview.FlowLayout;
import com.minglang.suiuu.customview.PullToRefreshView;
import com.minglang.suiuu.customview.mProgressDialog;
import com.minglang.suiuu.customview.rangebar.RangeBar;
import com.minglang.suiuu.entity.SuiuuDataList;
import com.minglang.suiuu.entity.SuiuuReturnDate;
import com.minglang.suiuu.entity.SuiuuSearchTag;
import com.minglang.suiuu.utils.AppUtils;
import com.minglang.suiuu.utils.ConstantUtil;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 随游页面
 */
public class RouteFragment extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {
    private List<TextView> list = new ArrayList<TextView>();
    private List<TextView> listClick = new ArrayList<TextView>();
    private List<String> tagList;
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
    private int startTick = 0;
    private int endTick = 10000;
    private FlowLayout id_flowlayout;
    private boolean isSearch;
    private RelativeLayout rl_search_no_data;
    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_route, null);
        innitView(rootView);
        loadDate(null,null,null, null, null, page);
        LinearLayout.LayoutParams paramTest = (LinearLayout.LayoutParams) lv_suiuu.getLayoutParams();
        paramTest.setMargins(10, ConstantUtil.topHeight+10, 10, 0);
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
                loadDate(null,null,null, null, null, page);
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
                startTick = leftPinIndex * 1000;
                endTick = rightPinIndex * 1000;
                tv_price_range.setText(leftPinIndex * 1000 + "--" + rightPinIndex * 1000);
            }
        });
    }

    private void innitView(View rootView) {
        tagList = new ArrayList<>();
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

        et_suiuu = (EditText) topView.findViewById(R.id.et_suiuu);
        iv_suiuu_search_more = (ImageView) topView.findViewById(R.id.iv_suiuu_search_more);
        ib_suiuu_search = (ImageButton) topView.findViewById(R.id.ib_suiuu_search);
        ib_suiuu_search.setOnClickListener(new MyOnclick());


        fl_search_more = (FrameLayout) rootView.findViewById(R.id.fl_search_more);
        iv_suiuu_search_more.setOnClickListener(new MyOnclick());
        ib_release = (ImageButton) rootView.findViewById(R.id.ib_release);
        ib_release.setOnClickListener(new MyOnclick());
        et_peple_number = (EditText) rootView.findViewById(R.id.et_peple_number);
        ib_plus = (ImageButton) rootView.findViewById(R.id.ib_plus);
        ib_plus.setOnClickListener(new MyOnclick());
        rangebar = (RangeBar) rootView.findViewById(R.id.rangebar);
        rangebar.setTickStart(0);
        rangebar.setTickEnd(10);
        rangebar.setTickInterval(1);
        tv_price_range = (TextView) rootView.findViewById(R.id.tv_price_range);
        id_flowlayout = (FlowLayout) rootView.findViewById(R.id.id_flowlayout);
        rl_search_no_data = (RelativeLayout) rootView.findViewById(R.id.rl_search_no_data);
        rl_search_no_data.setOnClickListener(new MyOnclick());
    }


    /**
     * @param countryOrCity
     * @param peopelCount
     * @param tags
     * @param startPrice
     * @param endPrice
     * @param page
     */
    private void loadDate(String countryOrCity, String peopelCount, String tags,String startPrice,String endPrice, int page) {
        Log.i("suiuu","countryOrctity="+countryOrCity+"peoplecount="+peopelCount+"tags="+tags+"startPrice="+startPrice+"endprice="+endPrice+"page="+page);
        dialog.showDialog();
        et_suiuu.setText("");
        String str = SuiuuInfo.ReadVerification(getActivity().getApplicationContext());
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, str);
        params.addBodyParameter("cc", countryOrCity);
        params.addBodyParameter("peopleCount", peopelCount);
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

    private void getSuiuSearchTag() {
        String str = SuiuuInfo.ReadVerification(getActivity().getApplicationContext());
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, str);
        SuHttpRequest suHttpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getSuiuuSearchTag, new getSuiuuSearchTagCallBack());
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
                    if(isSearch) {
                        loadDate(searchText,"0".equals(enjoyPeopleCount)?"":enjoyPeopleCount,"".equals(tags)?tags:tags.substring(0,tags.length()-1), Integer.toString(startTick), Integer.toString(endTick), page);
                    }else {

                        loadDate(null,null,null, null, null, page);
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
                loadDate(null,null,null, null, null, page);
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
                String status = baseCollection.getStatus();
                Log.i("suiuu", status);
                if ("1".equals(baseCollection.getStatus())) {
                    mPullToRefreshView.setVisibility(View.VISIBLE);
                    suiuuDataList = baseCollection.getData();
                    if (suiuuDataList.size() < 1) {
                        if(isSearch) {
                            rl_search_no_data.setVisibility(View.VISIBLE);
                            mPullToRefreshView.setVisibility(View.INVISIBLE);
                            page=1;
                        }else {
                            mPullToRefreshView.setVisibility(View.INVISIBLE);
                            tv_nodata_load.setVisibility(View.VISIBLE);
                        }
                    }
                    lv_suiuu.setAdapter(new ShowSuiuuAdapter(getActivity().getApplicationContext(), suiuuDataList));
                    tabSelect.setVisibility(View.VISIBLE);
                } else if ("-3".equals(baseCollection.getStatus())) {
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
            Log.i("suiuu", "获取了一次");
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

    public void fragmentShow() {
        if (fl_search_more.isShown()) {
            lv_suiuu.setEnabled(true);
            fl_search_more.setVisibility(View.GONE);
        } else {
            lv_suiuu.setEnabled(false);
            fl_search_more.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams paramTest1 = (RelativeLayout.LayoutParams) fl_search_more.getLayoutParams();
            paramTest1.setMargins(10, ConstantUtil.topHeight + 10, 10, 0);
            fl_search_more.setLayoutParams(paramTest1);
            if (tagList.size() < 1) {
                getSuiuSearchTag();
            }
        }
    }

    public void setViewGroup() {
        id_flowlayout.removeAllViews();
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        for (int i = 0; i < tagList.size() - 1; i++) {
            TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                    id_flowlayout, false);
            tv.setText(tagList.get(i));
            tv.setId(i);
            tv.setOnClickListener(new MyOnclick());
            list.add(tv);
            id_flowlayout.addView(tv);

        }
    }
    String tags ="";
    String searchText ;
    String enjoyPeopleCount ;
    class MyOnclick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            enjoy_peopleNumber = Integer.valueOf(String.valueOf(et_peple_number.getText()));
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
                    loadDate(null,null,null, null, null, page);
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
                    page=1;
                    lv_suiuu.setEnabled(true);
                    searchText = String.valueOf(et_suiuu.getText());
                    if("".equals(searchText) && !fl_search_more.isShown()) {
                        Toast.makeText(getActivity().getApplicationContext(), R.string.please_enter_search_content, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    isSearch = true;
                    if(fl_search_more.isShown()) {
                        enjoyPeopleCount = et_peple_number.getText().toString().trim();
                        for(TextView textV : listClick ) {
                            tags +=  textV.getText()+",";
                        }
                        loadDate(searchText,"0".equals(enjoyPeopleCount)?"":enjoyPeopleCount,"".equals(tags)?tags:tags.substring(0,tags.length()-1), Integer.toString(startTick), Integer.toString(endTick), page);
                        fl_search_more.setVisibility(View.GONE);
                    }else {
                        loadDate(searchText,null,null, null, null, page);
                    }
                    tags = "";
                    break;
            }
        }
    }

}

