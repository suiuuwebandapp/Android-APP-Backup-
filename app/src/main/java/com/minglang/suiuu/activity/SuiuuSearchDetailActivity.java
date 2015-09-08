package com.minglang.suiuu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
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
import com.minglang.suiuu.utils.HttpNewServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.OkHttpManager;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/6/25 15:02
 * 修改人：Administrator
 * 修改时间：2015/6/25 15:02
 * 修改备注：
 */
public class SuiuuSearchDetailActivity extends BaseActivity
        implements ReFlashListView.IReflashListener, ReFlashListView.ILoadMoreDataListener {
    private JsonUtils jsonUtil = JsonUtils.getInstance();
    private List<String> tagList;
    private RangeBar rangebar;
    private List<SuiuuDataList> suiuuDataList;
    private TextProgressDialog dialog;
    private List<TextView> list = new ArrayList<>();
    private List<TextView> listClick = new ArrayList<>();
    private String tags = "";
    private String enjoyPeopleCount;
    private ImageView iv_top_back;
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
    private ShowSuiuuAdapter adapter = null;
    private ImageView iv_suiuu_search_more;
    private ImageButton ib_plus;
    private ImageButton ib_release;
    private String searchCountry;
    private BootstrapButton bb_search_confire;
    @Bind(R.id.rl_common_no_data)
    RelativeLayout rl_no_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suiuu_search_detail);
        ButterKnife.bind(this);
        searchCountry = this.getIntent().getStringExtra("country");
        initView();
        viewAction();
//        getSuiuuSearchTag();
        loadDate(searchCountry, null, null, null, null, page);
    }

    private void initView() {
        dialog = new TextProgressDialog(this);
        suiuuDataList = new ArrayList<>();
        tagList = new ArrayList<>();
        rangebar = (RangeBar) findViewById(R.id.rangeBar);
        rangebar.setTickStart(0);
        rangebar.setTickEnd(10);
        rangebar.setTickInterval(1);
        iv_top_back = (ImageView) findViewById(R.id.iv_top_back);
        lv_search_suiuu = (ReFlashListView) findViewById(R.id.lv_search_suiuu);
        fl_search_more = (FrameLayout) findViewById(R.id.fl_search_more);
        tv_price_range = (TextView) findViewById(R.id.tv_price_range);
        peopleNumber = (EditText) findViewById(R.id.et_people_number);
        peopleNumber.setKeyListener(null);
        flowLayout = (FlowLayout) findViewById(R.id.id_flowLayout);
        titleInfo = (TextView) findViewById(R.id.tv_top_center);
        bb_search_confire = (BootstrapButton) findViewById(R.id.bb_search_confire);
        iv_suiuu_search_more = (ImageView) findViewById(R.id.tv_top_right_more);
        iv_suiuu_search_more.setVisibility(View.VISIBLE);
        ib_plus = (ImageButton) findViewById(R.id.ib_plus);
        ib_release = (ImageButton) findViewById(R.id.ib_release);
    }

    private void viewAction() {
        iv_top_back.setOnClickListener(new MyOnclick());
        ib_release.setOnClickListener(new MyOnclick());
        bb_search_confire.setOnClickListener(new MyOnclick());
        ib_plus.setOnClickListener(new MyOnclick());
        iv_suiuu_search_more.setOnClickListener(new MyOnclick());

        rangebar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue,
                                              String rightPinValue) {
                startTick = leftPinIndex * 1000;
                endTick = rightPinIndex * 1000;
                tv_price_range.setText(leftPinIndex * 1000 + "--" + rightPinIndex * 1000);
            }
        });
        lv_search_suiuu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SuiuuSearchDetailActivity.this, SuiuuDetailsActivity.class);
                intent.putExtra("tripId", suiuuDataList.get(position - 1).getTripId());
                startActivity(intent);
            }
        });
    }

    private void loadDate(String countryOrCity, String peopleCount, String tags, String startPrice, String endPrice, int page) {
        dialog.showDialog();
        String[] keyArray1 = new String[]{"cc", "peopleCount","tag","startPrice","endPrice","page","number", "token"};
        String[] valueArray1 = new String[]{countryOrCity, peopleCount, tags,  startPrice, endPrice,Integer.toString(page),"10", SuiuuInfo.ReadAppTimeSign(SuiuuSearchDetailActivity.this)};
        try {
            OkHttpManager.onGetAsynRequest(addUrlAndParams(HttpNewServicePath.getSuiuuList, keyArray1, valueArray1), new getSuiuuDateCallBack());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getSuiuuSearchTag() {
        try {
        OkHttpManager.onGetAsynRequest(HttpNewServicePath.getSuiuuSearchTag+"?token="+SuiuuInfo.ReadAppTimeSign(SuiuuSearchDetailActivity.this), new getSuiuuSearchTagCallBack());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            loadDate(searchCountry, "0".equals(enjoyPeopleCount) ? "" :
                            enjoyPeopleCount, "".equals(tags) ? tags : tags.substring(0, tags.length() - 1),
                    Integer.toString(startTick), Integer.toString(endTick), page);
        }
        lv_search_suiuu.loadComplete();
    }

    @Override
    public void onReflash() {
        suiuuDataList.clear();
        page = 1;
        loadDate(searchCountry, "0".equals(enjoyPeopleCount) ? "" :
                        enjoyPeopleCount, "".equals(tags) ? tags : tags.substring(0, tags.length() - 1),
                Integer.toString(startTick), Integer.toString(endTick), page);
        lv_search_suiuu.reflashComplete();
    }

    /**
     * 获取随游标签回调接口
     */
    class getSuiuuSearchTagCallBack extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onError(Request request, Exception e) {
            dialog.dismissDialog();
            Toast.makeText(SuiuuSearchDetailActivity.this, "数据获取失败，请重试！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            try {
                SuiuuSearchTag suiuuSearchTag = jsonUtil.fromJSON(SuiuuSearchTag.class, response);
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
    }

    /**
     * 获取随游列表的回调接口
     */
    class getSuiuuDateCallBack extends OkHttpManager.ResultCallback<String> {
        @Override
        public void onError(Request request, Exception e) {
            Toast.makeText(SuiuuSearchDetailActivity.this, "数据获取失败，请重试！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            dialog.dismissDialog();
            try {
                JSONObject json = new JSONObject(response);
                String status = json.getString("status");
                if ("1".equals(status)) {

                    SuiuuReturnDate baseCollection = jsonUtil.fromJSON(SuiuuReturnDate.class, response);
                    List<SuiuuDataList> suiuuDataListNew = baseCollection.getData();
                    if (suiuuDataListNew.size() < 1) {
                        if(page == 1) {
                            lv_search_suiuu.setVisibility(View.GONE);
                            rl_no_data.setVisibility(View.VISIBLE);
                        }else {
                            Toast.makeText(SuiuuSearchDetailActivity.this, "没有更多数据显示", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        lv_search_suiuu.setVisibility(View.VISIBLE);
                        rl_no_data.setVisibility(View.GONE);
                        suiuuDataList.addAll(suiuuDataListNew);
                        showList(suiuuDataList);
                    }
                } else {
                    Toast.makeText(SuiuuSearchDetailActivity.this, "数据获取失败，请重试！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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
                case R.id.ib_release:
                    if (enjoy_peopleNumber != 0) {
                        peopleNumber.setText(String.valueOf(enjoy_peopleNumber - 1));
                    }
                    break;

                case R.id.ib_plus:
                    peopleNumber.setText(String.valueOf(enjoy_peopleNumber + 1));
                    break;
                case R.id.iv_top_back:
                    finish();
                    break;
                case R.id.tv_top_right_more:
                    fragmentShow();
                    break;

                case R.id.bb_search_confire:
                    tags = "";
                    page = 1;
                    lv_search_suiuu.setEnabled(true);
                    suiuuDataList.clear();
                    if (fl_search_more.isShown()) {
                        enjoyPeopleCount = peopleNumber.getText().toString().trim();

                        for (TextView textV : listClick) {
                            tags += textV.getText() + ",";
                        }
                        loadDate(searchCountry, "0".equals(enjoyPeopleCount) ? "" :
                                        enjoyPeopleCount, "".equals(tags) ? tags : tags.substring(0, tags.length() - 1),
                                Integer.toString(startTick), Integer.toString(endTick), page);
                        fl_search_more.setVisibility(View.GONE);
                    } else {
                        loadDate(searchCountry, null, null, null, null, page);
                    }
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
