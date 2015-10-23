package com.minglang.suiuu.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.edmodo.rangebar.RangeBar;
import com.minglang.pulltorefreshlibrary.PullToRefreshBase;
import com.minglang.pulltorefreshlibrary.PullToRefreshListView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.ShowSuiuuAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.customview.FlowLayout;
import com.minglang.suiuu.customview.TextProgressDialog;
import com.minglang.suiuu.entity.SuiuuData;
import com.minglang.suiuu.entity.SuiuuItemData;
import com.minglang.suiuu.entity.SuiuuSearchTag;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.StatusBarCompat;
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
 * 项目名称：Android-APP-Backup-
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/6/25 15:02
 * 修改人：Administrator
 * 修改时间：2015/6/25 15:02
 * 修改备注：
 */
public class SuiuuSearchDetailsActivity extends BaseAppCompatActivity {

    private static final String TAG = SuiuuSearchDetailsActivity.class.getSimpleName();

    private static final String CLASS_NAME = "className";

    private JsonUtils jsonUtils = JsonUtils.getInstance();

    private List<String> tagList = new ArrayList<>();

    private List<SuiuuItemData> listAll = new ArrayList<>();

    private TextProgressDialog dialog;

    private List<TextView> list = new ArrayList<>();

    private List<TextView> listClick = new ArrayList<>();
    private String tags = "";

    private String enjoyPeopleCount;

    private int startTick = 0;

    private int endTick = 10000;

    @BindDrawable(R.color.DefaultGray1)
    Drawable Divider;

    @BindString(R.string.NoData)
    String NoData;

    @BindString(R.string.SystemException)
    String SystemException;

    @BindString(R.string.NetworkAnomaly)
    String NetworkError;

    @BindString(R.string.LoginInvalid)
    String LoginInvalid;

    @Bind(R.id.suiuu_search_details_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.suiuu_search_details_range_bar)
    RangeBar rangebar;

    @Bind(R.id.et_people_number)
    EditText peopleNumber;

    @Bind(R.id.tv_price_range)
    TextView showPriceSection;

    @Bind(R.id.id_flowLayout)
    FlowLayout tagLayout;

    @Bind(R.id.search_more_layout)
    RelativeLayout searchMoreLayout;

    @Bind(R.id.lv_search_suiuu)
    PullToRefreshListView pullToRefreshListView;

    private int page = 1;

    private ShowSuiuuAdapter adapter;

    @Bind(R.id.suiuu_search_add_people_number)
    ImageButton addPersonal;

    @Bind(R.id.suiuu_search_less_people_number)
    ImageButton lessPersonal;

    private String searchCountry;

    @Bind(R.id.search_confirm_button)
    Button searchConfirmButton;

    @Bind(R.id.common_no_data)
    RelativeLayout noDataHintView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suiuu_search_detail);

        StatusBarCompat.compat(this);
        ButterKnife.bind(this);

        searchCountry = this.getIntent().getStringExtra("country");

        initView();
        viewAction();
        loadDate(searchCountry, null, null, null, null, page);
    }

    private void initView() {
        token = SuiuuInfo.ReadAppTimeSign(SuiuuSearchDetailsActivity.this);

        dialog = new TextProgressDialog(this);

        setSupportActionBar(toolbar);

        rangebar.setTickCount(10);

        peopleNumber.setKeyListener(null);

        ListView listView = pullToRefreshListView.getRefreshableView();
        listView.setDivider(Divider);
        listView.setDividerHeight((int) getResources().getDimension(R.dimen.layout_5dp));
    }

    private void viewAction() {

        lessPersonal.setOnClickListener(new MyOnclick());

        searchConfirmButton.setOnClickListener(new MyOnclick());

        addPersonal.setOnClickListener(new MyOnclick());

        rangebar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex) {
                startTick = leftPinIndex * 1000;
                endTick = rightPinIndex * 1000;
                showPriceSection.setText(String.format("%s%s%s", leftPinIndex * 1000, "--", rightPinIndex * 1000));
            }
        });

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(SuiuuSearchDetailsActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                if (listAll != null && listAll.size() > 0) {
                    listAll.clear();
                }

                page = 1;
                loadDate(searchCountry, "0".equals(enjoyPeopleCount) ? "" : enjoyPeopleCount,
                        "".equals(tags) ? tags : tags.substring(0, tags.length() - 1),
                        Integer.toString(startTick), Integer.toString(endTick), page);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(SuiuuSearchDetailsActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                if (!dialog.isShow()) {
                    page++;
                    loadDate(searchCountry, "0".equals(enjoyPeopleCount) ? "" : enjoyPeopleCount,
                            "".equals(tags) ? tags : tags.substring(0, tags.length() - 1),
                            Integer.toString(startTick), Integer.toString(endTick), page);
                }

            }

        });

        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SuiuuSearchDetailsActivity.this, SuiuuDetailsActivity.class);
                intent.putExtra("tripId", listAll.get(position - 1).getTripId());
                intent.putExtra(USER_SIGN, listAll.get(position - 1).getUserSign());
                intent.putExtra(HEAD_IMG, listAll.get(position - 1).getHeadImg());
                intent.putExtra(CLASS_NAME, TAG);
                startActivity(intent);
            }
        });
    }

    private void loadDate(String countryOrCity, String peopleCount, String tags, String startPrice, String endPrice, int page) {
        dialog.show();
        String[] keyArray1 = new String[]{"cc", "peopleCount", "tag", "startPrice", "endPrice", "page", "number", "token"};
        String[] valueArray1 = new String[]{countryOrCity, peopleCount, tags, startPrice, endPrice, Integer.toString(page), "10", token};
        String url = addUrlAndParams(HttpNewServicePath.getSuiuuList, keyArray1, valueArray1);
        try {
            OkHttpManager.onGetAsynRequest(url, new SuiuuSearchDetailsCallback());
        } catch (IOException e) {
            L.e(TAG, "数据请求错误:" + e.getMessage());
            dialog.dismiss();
            Toast.makeText(SuiuuSearchDetailsActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }
    }

    private void getSuiuuSearchTag() {
        try {
            OkHttpManager.onGetAsynRequest(HttpNewServicePath.getSuiuuSearchTag + "?token=" + token, new SuiuuSearchTagCallback());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showList(List<SuiuuItemData> list) {
        if (adapter == null) {
            pullToRefreshListView.setVisibility(View.VISIBLE);
            adapter = new ShowSuiuuAdapter(this, list);
            pullToRefreshListView.setAdapter(adapter);
        } else {
            adapter.setList(list);
        }
    }

    public void setTagLayoutContent() {
        tagLayout.removeAllViews();
        LayoutInflater mInflater = LayoutInflater.from(this);
        for (int i = 0; i < tagList.size() - 1; i++) {
            TextView tv = (TextView) mInflater.inflate(R.layout.widget_tag_default_text_view, tagLayout, false);
            tv.setText(tagList.get(i));
            tv.setId(i);
            tv.setOnClickListener(new MyOnclick());
            list.add(tv);
            tagLayout.addView(tv);
        }
    }

    public void showSearchMore() {
        if (searchMoreLayout.isShown()) {
            pullToRefreshListView.setEnabled(true);
            searchMoreLayout.setVisibility(View.GONE);
        } else {
            pullToRefreshListView.setEnabled(false);
            searchMoreLayout.setVisibility(View.VISIBLE);
            if (tagList.size() < 1) {
                getSuiuuSearchTag();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_suiuu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.suiuu_search_more:
                showSearchMore();
                break;
        }
        return true;
    }

    /**
     * 获取随游标签回调接口
     */
    private class SuiuuSearchTagCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            try {
                SuiuuSearchTag suiuuSearchTag = jsonUtils.fromJSON(SuiuuSearchTag.class, response);
                String status = suiuuSearchTag.getStatus();
                switch (status) {
                    case "1":
                        tagList = suiuuSearchTag.getData();
                        setTagLayoutContent();
                        break;
                    default:
                        tagList.add("家庭");
                        tagList.add("美食");
                        break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            Toast.makeText(SuiuuSearchDetailsActivity.this, "数据获取失败，请重试！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish() {
            dialog.dismiss();
        }
    }

    /**
     * 获取随游列表的回调接口
     */
    private class SuiuuSearchDetailsCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            if (TextUtils.isEmpty(response)) {
                Toast.makeText(SuiuuSearchDetailsActivity.this, NoData, Toast.LENGTH_SHORT).show();
            } else try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString(STATUS);
                switch (status) {
                    case "1":
                        SuiuuData baseCollection = jsonUtils.fromJSON(SuiuuData.class, response);
                        List<SuiuuItemData> list = baseCollection.getData();
                        if (list.size() < 1) {
                            if (page == 1) {
                                pullToRefreshListView.setVisibility(View.GONE);
                                noDataHintView.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(SuiuuSearchDetailsActivity.this, NoData, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            pullToRefreshListView.setVisibility(View.VISIBLE);
                            noDataHintView.setVisibility(View.GONE);
                            listAll.addAll(list);
                            showList(listAll);
                        }
                        break;

                    case "-1":
                        Toast.makeText(SuiuuSearchDetailsActivity.this, SystemException, Toast.LENGTH_SHORT).show();
                        break;

                    case "-2":
                        String msg = jsonObject.getString(DATA);
                        Toast.makeText(SuiuuSearchDetailsActivity.this, msg, Toast.LENGTH_SHORT).show();
                        break;

                    case "-3":
                        Toast.makeText(SuiuuSearchDetailsActivity.this, LoginInvalid, Toast.LENGTH_SHORT).show();
                        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(SuiuuSearchDetailsActivity.this);
                        localBroadcastManager.sendBroadcast(new Intent(SettingActivity.class.getSimpleName()));
                        ReturnLoginActivity(SuiuuSearchDetailsActivity.this);
                        break;

                    case "-4":
                        Toast.makeText(SuiuuSearchDetailsActivity.this, jsonObject.getString(DATA), Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        Toast.makeText(SuiuuSearchDetailsActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
                        break;
                }
            } catch (Exception e) {
                L.e(TAG, "随游数据解析错误:" + e.getMessage());
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            L.i(TAG, "网络请求失败:" + e.getLocalizedMessage());
            Toast.makeText(SuiuuSearchDetailsActivity.this, "数据获取失败，请重试！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish() {
            dialog.dismiss();
        }

    }

    private class MyOnclick implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            //参与的人数
            int enjoy_peopleNumber = Integer.valueOf(String.valueOf(peopleNumber.getText()));
            for (int i = 0; i < tagList.size() - 1; i++) {
                if (v.getId() == i) {
                    if (listClick.contains(list.get(i))) {
                        list.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_tag_normal));
                        listClick.remove(list.get(i));
                    } else {
                        list.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_tag_press));
                        listClick.add(list.get(i));
                    }
                }
            }

            switch (v.getId()) {
                case R.id.suiuu_search_less_people_number:
                    if (enjoy_peopleNumber != 0) {
                        peopleNumber.setText(String.valueOf(enjoy_peopleNumber - 1));
                    }
                    break;

                case R.id.suiuu_search_add_people_number:
                    peopleNumber.setText(String.valueOf(enjoy_peopleNumber + 1));
                    break;

                case R.id.search_confirm_button:
                    tags = "";
                    page = 1;
                    pullToRefreshListView.setEnabled(true);
                    listAll.clear();
                    if (searchMoreLayout.isShown()) {
                        enjoyPeopleCount = peopleNumber.getText().toString().trim();

                        for (TextView textV : listClick) {
                            tags += textV.getText() + ",";
                        }

                        loadDate(searchCountry,
                                "0".equals(enjoyPeopleCount) ? "" : enjoyPeopleCount,
                                "".equals(tags) ? tags : tags.substring(0, tags.length() - 1),
                                Integer.toString(startTick), Integer.toString(endTick), page);

                        searchMoreLayout.setVisibility(View.GONE);
                    } else {
                        loadDate(searchCountry, null, null, null, null, page);
                    }
                    break;
            }
        }

    }

}