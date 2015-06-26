package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.ShowSuiuuAdapter;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.customview.FlowLayout;
import com.minglang.suiuu.customview.ReFlashListView;
import com.minglang.suiuu.customview.TextProgressDialog;
import com.minglang.suiuu.customview.rangebar.RangeBar;
import com.minglang.suiuu.entity.SuiuuDataList;
import com.minglang.suiuu.entity.SuiuuReturnDate;
import com.minglang.suiuu.entity.SuiuuSearchTag;
import com.minglang.suiuu.utils.AppUtils;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/6/25 15:02
 * 修改人：Administrator
 * 修改时间：2015/6/25 15:02
 * 修改备注：
 */
public class SuiuuSearchActivity extends BaseActivity implements ReFlashListView.IReflashListener, ReFlashListView.ILoadMoreDataListener {
    private JsonUtils jsonUtil = JsonUtils.getInstance();
    private List<String> tagList;
    private RangeBar rangebar;
    private List<SuiuuDataList> suiuuDataList;
    private TextProgressDialog dialog;
    private List<TextView> list = new ArrayList<>();
    private List<TextView> listClick = new ArrayList<>();
    String tags = "";
    String searchText;
    String enjoyPeopleCount;

    private TextView tv_price_range;
    private int startTick = 0;
    private int endTick = 10000;
    private EditText peopleNumber;
    private FlowLayout flowLayout;
    private FrameLayout fl_search_more;
    private ReFlashListView lv_search_suiuu;
    private int page = 1;
    //处理头部
    private TextView titleInfo;
    private RelativeLayout rl_top_info;
    private ImageButton ib_suiuu_search;
    private EditText et_suiuu;
    private ShowSuiuuAdapter adapter = null;
    private ImageView iv_suiuu_search_more;
    private ImageButton ib_plus;
    private ImageButton ib_release;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suiuu_search);
        initView();
        viewAction();
        getSuiuuSearchTag();
    }

    private void initView() {
        dialog = new TextProgressDialog(this);
        suiuuDataList = new ArrayList<>();
        tagList = new ArrayList<>();
        rangebar = (RangeBar) findViewById(R.id.rangeBar);
        rangebar.setTickStart(0);
        rangebar.setTickEnd(10);
        rangebar.setTickInterval(1);
        lv_search_suiuu = (ReFlashListView) findViewById(R.id.lv_search_suiuu);
        fl_search_more = (FrameLayout) findViewById(R.id.fl_search_more);
        tv_price_range = (TextView) findViewById(R.id.tv_price_range);
        peopleNumber = (EditText) findViewById(R.id.et_people_number);
        peopleNumber.setKeyListener(null);
        flowLayout = (FlowLayout) findViewById(R.id.id_flowLayout);
        titleInfo = (TextView) findViewById(R.id.titleInfo);
        titleInfo.setVisibility(View.GONE);
        rl_top_info = (RelativeLayout) findViewById(R.id.rl_top_info);
        rl_top_info.setVisibility(View.VISIBLE);
        ib_suiuu_search = (ImageButton) findViewById(R.id.ib_suiuu_search);
        et_suiuu = (EditText) findViewById(R.id.et_suiuu);
        iv_suiuu_search_more = (ImageView) findViewById(R.id.iv_suiuu_search_more);
        iv_suiuu_search_more.setVisibility(View.VISIBLE);
        ib_plus = (ImageButton) findViewById(R.id.ib_plus);
        ib_release = (ImageButton) findViewById(R.id.ib_release);
    }

    private void viewAction() {
        ib_release.setOnClickListener(new MyOnclick());
        ib_plus.setOnClickListener(new MyOnclick());
        iv_suiuu_search_more.setOnClickListener(new MyOnclick());
        ib_suiuu_search.setOnClickListener(new MyOnclick());
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

    private void loadDate(String countryOrCity, String peopleCount, String tags, String startPrice, String endPrice, int page) {
        Log.i("suiuu", "contryOrcity=" + countryOrCity + ",peoplecount=" + peopleCount + ",tags=" + tags + "startPrice=" + startPrice + ",endprice=" + endPrice + ",page=" + page);
        dialog.showDialog();
        et_suiuu.setText("");

        String str = SuiuuInfo.ReadVerification(this);
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


    private void getSuiuuSearchTag() {
        String str = SuiuuInfo.ReadVerification(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, str);
        SuHttpRequest suHttpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getSuiuuSearchTag, new getSuiuuSearchTagCallBack());
        suHttpRequest.setParams(params);
        suHttpRequest.requestNetworkData();
    }

    private void showList(List<SuiuuDataList> suiuuDataList) {
        if (adapter == null) {
            lv_search_suiuu.setVisibility(View.VISIBLE);
            lv_search_suiuu.setInterface(this);
            lv_search_suiuu.setLoadMoreInterface(this);
            adapter = new ShowSuiuuAdapter(this, suiuuDataList);
            lv_search_suiuu.setAdapter(adapter);
        } else {
            adapter.onDateChange(suiuuDataList);
        }
    }

    @Override
    public void onLoadMoreData() {
        if (!dialog.isShow()) {
            page += 1;
            loadDate(searchText, "0".equals(enjoyPeopleCount) ? "" :
                            enjoyPeopleCount, "".equals(tags) ? tags : tags.substring(0, tags.length() - 1),
                    Integer.toString(startTick), Integer.toString(endTick), page);
        }
        lv_search_suiuu.loadComplete();
    }

    @Override
    public void onReflash() {
        suiuuDataList.clear();
        page = 1;
        loadDate(searchText, "0".equals(enjoyPeopleCount) ? "" :
                        enjoyPeopleCount, "".equals(tags) ? tags : tags.substring(0, tags.length() - 1),
                Integer.toString(startTick), Integer.toString(endTick), page);
        lv_search_suiuu.reflashComplete();
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
            Toast.makeText(SuiuuSearchActivity.this, "数据获取失败，请重试！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取随游列表的回调接口
     */
    class getSuiuuDateCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            Log.i("suiuu", stringResponseInfo.result);
            dialog.dismissDialog();
            try {
                JSONObject json = new JSONObject(stringResponseInfo.result);
                String status = json.getString("status");
                if ("1".equals(status)) {
                    SuiuuReturnDate baseCollection = jsonUtil.fromJSON(SuiuuReturnDate.class, stringResponseInfo.result);

                    List<SuiuuDataList> suiuuDataListNew = baseCollection.getData();
                    if (suiuuDataListNew.size() < 1) {
                        page = 1;
                        Toast.makeText(SuiuuSearchActivity.this, "数据加载完毕", Toast.LENGTH_SHORT).show();
                    }
                    suiuuDataList.addAll(suiuuDataListNew);
                    showList(suiuuDataList);
                } else if ("-3".equals(status)) {
                    Toast.makeText(SuiuuSearchActivity.this, "登录信息过期,请重新登录", Toast.LENGTH_SHORT).show();
                    AppUtils.intentLogin(SuiuuSearchActivity.this);
                    SuiuuSearchActivity.this.finish();
                } else {
                    Toast.makeText(SuiuuSearchActivity.this, "数据获取失败，请重试！", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            dialog.dismissDialog();
            Toast.makeText(SuiuuSearchActivity.this, "数据获取失败，请重试！", Toast.LENGTH_SHORT).show();
        }
    }


    public void setViewGroup() {
        flowLayout.removeAllViews();
        LayoutInflater mInflater = LayoutInflater.from(this);
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

    public void fragmentShow() {
        if (fl_search_more.isShown()) {
            lv_search_suiuu.setEnabled(true);
            fl_search_more.setVisibility(View.GONE);
        } else {
            lv_search_suiuu.setEnabled(false);
            fl_search_more.setVisibility(View.VISIBLE);
//            LinearLayout.LayoutParams paramTest1 = (LinearLayout.LayoutParams) fl_search_more.getLayoutParams();
//            paramTest1.setMargins(10, ConstantUtils.topHeight + 10, 10, 0);
//            fl_search_more.setLayoutParams(paramTest1);
            if (tagList.size() < 1) {
                getSuiuuSearchTag();
            }
        }
    }

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
                    lv_search_suiuu.setEnabled(true);
                    searchText = String.valueOf(et_suiuu.getText());
                    if ("".equals(searchText) && !fl_search_more.isShown()) {
                        Toast.makeText(SuiuuSearchActivity.this, R.string.please_enter_search_content, Toast.LENGTH_SHORT).show();
                        return;
                    }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
