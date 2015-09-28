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
import com.minglang.suiuu.entity.SuiuuItemData;
import com.minglang.suiuu.entity.SuiuuData;
import com.minglang.suiuu.entity.SuiuuSearchTag;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.http.OkHttpManager;
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

    private List<String> tagList = new ArrayList<>();

    @Bind(R.id.rangeBar)
    RangeBar rangebar;

    private List<SuiuuItemData> suiuuItemData = new ArrayList<>();

    private TextProgressDialog dialog;

    private List<TextView> list = new ArrayList<>();
    private List<TextView> listClick = new ArrayList<>();

    private String tags = "";

    private String enjoyPeopleCount;

    @Bind(R.id.iv_top_back)
    ImageView iv_top_back;

    @Bind(R.id.tv_price_range)
    TextView tv_price_range;

    private int startTick = 0;
    private int endTick = 10000;

    @Bind(R.id.et_people_number)
    EditText peopleNumber;

    @Bind(R.id.id_flowLayout)
    FlowLayout flowLayout;

    @Bind(R.id.fl_search_more)
    FrameLayout fl_search_more;

    @Bind(R.id.lv_search_suiuu)
    ReFlashListView lv_search_suiuu;

    private int page = 1;

    //处理头部
    @Bind(R.id.tv_top_center)
    TextView titleInfo;

    private ShowSuiuuAdapter adapter = null;

    @Bind(R.id.tv_top_right_more)
    ImageView iv_suiuu_search_more;

    @Bind(R.id.ib_plus)
    ImageButton ib_plus;

    @Bind(R.id.ib_release)
    ImageButton ib_release;

    private String searchCountry;

    @Bind(R.id.bb_search_confire)
    BootstrapButton bb_search_confirm;

    @Bind(R.id.common_no_data)
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
        token = SuiuuInfo.ReadAppTimeSign(SuiuuSearchDetailActivity.this);

        dialog = new TextProgressDialog(this);

        rangebar.setTickStart(0);
        rangebar.setTickEnd(10);
        rangebar.setTickInterval(1);

        peopleNumber.setKeyListener(null);

        iv_suiuu_search_more.setVisibility(View.VISIBLE);

    }

    private void viewAction() {
        iv_top_back.setOnClickListener(new MyOnclick());

        ib_release.setOnClickListener(new MyOnclick());

        bb_search_confirm.setOnClickListener(new MyOnclick());

        ib_plus.setOnClickListener(new MyOnclick());

        iv_suiuu_search_more.setOnClickListener(new MyOnclick());

        rangebar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue,
                                              String rightPinValue) {
                startTick = leftPinIndex * 1000;
                endTick = rightPinIndex * 1000;
                tv_price_range.setText(String.format("%s%s%s", leftPinIndex * 1000, "--", rightPinIndex * 1000));
            }
        });

        lv_search_suiuu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SuiuuSearchDetailActivity.this, SuiuuDetailsActivity.class);
                intent.putExtra("tripId", suiuuItemData.get(position - 1).getTripId());
                intent.putExtra(USER_SIGN, suiuuItemData.get(position - 1).getUserSign());
                intent.putExtra(HEAD_IMG, suiuuItemData.get(position - 1).getHeadImg());
                startActivity(intent);
            }
        });
    }

    private void loadDate(String countryOrCity, String peopleCount, String tags, String startPrice, String endPrice, int page) {
        dialog.show();
        String[] keyArray1 = new String[]{"cc", "peopleCount", "tag", "startPrice", "endPrice", "page", "number", "token"};
        String[] valueArray1 = new String[]{countryOrCity, peopleCount, tags, startPrice, endPrice, Integer.toString(page), "10", token};
        try {
            OkHttpManager.onGetAsynRequest(addUrlAndParams(HttpNewServicePath.getSuiuuList, keyArray1, valueArray1), new getSuiuuDateCallBack());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getSuiuuSearchTag() {
        try {
            OkHttpManager.onGetAsynRequest(HttpNewServicePath.getSuiuuSearchTag + "?token=" + token, new getSuiuuSearchTagCallBack());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showList(List<SuiuuItemData> suiuuItemData) {
        if (adapter == null) {
            lv_search_suiuu.setVisibility(View.VISIBLE);
            lv_search_suiuu.setInterface(this);
            lv_search_suiuu.setLoadMoreInterface(this);
            adapter = new ShowSuiuuAdapter(this, suiuuItemData);
            lv_search_suiuu.setAdapter(adapter);
        } else {
            adapter.upDateData(suiuuItemData);
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
        suiuuItemData.clear();
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
            dialog.dismiss();
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
            dialog.dismiss();
            try {
                JSONObject json = new JSONObject(response);
                String status = json.getString("status");
                if ("1".equals(status)) {

                    SuiuuData baseCollection = jsonUtil.fromJSON(SuiuuData.class, response);
                    List<SuiuuItemData> suiuuItemDataNew = baseCollection.getData();
                    if (suiuuItemDataNew.size() < 1) {
                        if (page == 1) {
                            lv_search_suiuu.setVisibility(View.GONE);
                            rl_no_data.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(SuiuuSearchDetailActivity.this, "没有更多数据显示", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        lv_search_suiuu.setVisibility(View.VISIBLE);
                        rl_no_data.setVisibility(View.GONE);
                        suiuuItemData.addAll(suiuuItemDataNew);
                        showList(suiuuItemData);
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
                    suiuuItemData.clear();
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
