package com.minglang.suiuu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.minglang.pulltorefreshlibrary.PullToRefreshBase;
import com.minglang.pulltorefreshlibrary.PullToRefreshListView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.ShowSuiuuAdapter;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.customview.FlowLayout;
import com.minglang.suiuu.customview.TextProgressDialog;
import com.minglang.suiuu.customview.rangebar.RangeBar;
import com.minglang.suiuu.entity.SuiuuData;
import com.minglang.suiuu.entity.SuiuuItemData;
import com.minglang.suiuu.entity.SuiuuSearchTag;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
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
public class SuiuuSearchDetailsActivity extends BaseActivity {

    private static final String TAG = SuiuuSearchDetailsActivity.class.getSimpleName();

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

    @BindString(R.string.NoData)
    String NoData;

    @BindString(R.string.SystemException)
    String SystemException;

    @BindString(R.string.NetworkAnomaly)
    String NetworkError;

    @Bind(R.id.rangeBar)
    RangeBar rangebar;

    @Bind(R.id.suiuu_search_back)
    ImageButton backButton;

    @Bind(R.id.suiuu_search_more)
    ImageButton moreButton;

    @Bind(R.id.et_people_number)
    EditText peopleNumber;

    @Bind(R.id.tv_price_range)
    TextView tv_price_range;

    @Bind(R.id.id_flowLayout)
    FlowLayout tagLayout;

    @Bind(R.id.search_more_layout)
    RelativeLayout searchMoreLayout;

    @Bind(R.id.lv_search_suiuu)
    PullToRefreshListView suiuuSearchListView;

    private int page = 1;

    private ShowSuiuuAdapter adapter;

    @Bind(R.id.suiuu_search_add_people_number)
    ImageButton ib_plus;

    @Bind(R.id.suiuu_search_less_people_number)
    ImageButton ib_release;

    private String searchCountry;

    @Bind(R.id.search_confirm_button)
    Button searchConfirmButton;

    @Bind(R.id.common_no_data)
    RelativeLayout noDataHintView;

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
        token = SuiuuInfo.ReadAppTimeSign(SuiuuSearchDetailsActivity.this);

        dialog = new TextProgressDialog(this);

        rangebar.setTickStart(0);
        rangebar.setTickEnd(10);
        rangebar.setTickInterval(1);

        peopleNumber.setKeyListener(null);

    }

    private void viewAction() {

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearchMore();
            }
        });

        ib_release.setOnClickListener(new MyOnclick());

        searchConfirmButton.setOnClickListener(new MyOnclick());

        ib_plus.setOnClickListener(new MyOnclick());

        rangebar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue,
                                              String rightPinValue) {
                startTick = leftPinIndex * 1000;
                endTick = rightPinIndex * 1000;
                tv_price_range.setText(String.format("%s%s%s", leftPinIndex * 1000, "--", rightPinIndex * 1000));
            }
        });


        suiuuSearchListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

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

        suiuuSearchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SuiuuSearchDetailsActivity.this, SuiuuDetailsActivity.class);
                intent.putExtra("tripId", listAll.get(position - 1).getTripId());
                intent.putExtra(USER_SIGN, listAll.get(position - 1).getUserSign());
                intent.putExtra(HEAD_IMG, listAll.get(position - 1).getHeadImg());
                startActivity(intent);
            }
        });
    }

    private void loadDate(String countryOrCity, String peopleCount, String tags, String startPrice, String endPrice, int page) {
        dialog.show();
        String[] keyArray1 = new String[]{"cc", "peopleCount", "tag", "startPrice", "endPrice", "page", "number", "token"};
        String[] valueArray1 = new String[]{countryOrCity, peopleCount, tags, startPrice, endPrice, Integer.toString(page), "10", token};
        try {
            OkHttpManager.onGetAsynRequest(addUrlAndParams(HttpNewServicePath.getSuiuuList, keyArray1, valueArray1), new SuiuuSearchDetailsCallback());
        } catch (IOException e) {
            L.e(TAG, "数据请求错误:" + e.getMessage());
            dialog.dismiss();
        }
    }

    private void getSuiuuSearchTag() {
        try {
            OkHttpManager.onGetAsynRequest(HttpNewServicePath.getSuiuuSearchTag + "?token=" + token, new SuiuuSearchTagCallback());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showList(List<SuiuuItemData> suiuuItemData) {
        if (adapter == null) {
            suiuuSearchListView.setVisibility(View.VISIBLE);
            adapter = new ShowSuiuuAdapter(this, suiuuItemData);
            suiuuSearchListView.setAdapter(adapter);
        } else {
            adapter.setList(suiuuItemData);
        }
    }

    public void setTagLayoutContent() {
        tagLayout.removeAllViews();
        LayoutInflater mInflater = LayoutInflater.from(this);
        for (int i = 0; i < tagList.size() - 1; i++) {
            TextView tv = (TextView) mInflater.inflate(R.layout.tv, tagLayout, false);
            tv.setText(tagList.get(i));
            tv.setId(i);
            tv.setOnClickListener(new MyOnclick());
            list.add(tv);
            tagLayout.addView(tv);
        }
    }

    public void showSearchMore() {
        if (searchMoreLayout.isShown()) {
            suiuuSearchListView.setEnabled(true);
            searchMoreLayout.setVisibility(View.GONE);
        } else {
            suiuuSearchListView.setEnabled(false);
            searchMoreLayout.setVisibility(View.VISIBLE);
            if (tagList.size() < 1) {
                getSuiuuSearchTag();
            }
        }
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
                                suiuuSearchListView.setVisibility(View.GONE);
                                noDataHintView.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(SuiuuSearchDetailsActivity.this, NoData, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            suiuuSearchListView.setVisibility(View.VISIBLE);
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
                        list.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.tv_bg1));
                        listClick.remove(list.get(i));
                    } else {
                        list.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.tv_bg));
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

                case R.id.iv_top_back:
                    finish();
                    break;

                case R.id.search_confirm_button:
                    tags = "";
                    page = 1;
                    suiuuSearchListView.setEnabled(true);
                    listAll.clear();
                    if (searchMoreLayout.isShown()) {
                        enjoyPeopleCount = peopleNumber.getText().toString().trim();

                        for (TextView textV : listClick) {
                            tags += textV.getText() + ",";
                        }
                        loadDate(searchCountry, "0".equals(enjoyPeopleCount) ? "" :
                                        enjoyPeopleCount, "".equals(tags) ? tags : tags.substring(0, tags.length() - 1),
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