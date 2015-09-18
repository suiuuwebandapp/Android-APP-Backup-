package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.HaveCountryAdapter;
import com.minglang.suiuu.adapter.SelectCountryAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.customview.SideBar;
import com.minglang.suiuu.entity.Country;
import com.minglang.suiuu.entity.CountryAssistData;
import com.minglang.suiuu.entity.CountryData;
import com.minglang.suiuu.entity.HaveAssistCountry;
import com.minglang.suiuu.entity.HaveCountry;
import com.minglang.suiuu.fragment.main.ProblemFragment;
import com.minglang.suiuu.utils.AppConstant;
import com.minglang.suiuu.utils.CharacterParser;
import com.minglang.suiuu.utils.comparator.CountryNameComparator;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.comparator.HaveCountryNameComparator;
import com.minglang.suiuu.utils.HttpNewServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.OkHttpManager;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;

public class SelectCountryActivity extends BaseAppCompatActivity {

    private static final String TAG = SelectCountryActivity.class.getSimpleName();

    private static final String OTHER_TAG = "OtherTag";

    private static final String COUNTRY_ID = "countryId";
    private static final String CITY_ID = "cityId";

    private static final String COUNTRY_CN_NAME = "countryCNname";
    private static final String CITY_NAME = "cityName";

    private static final String COUNTRY_NAME_2 = "countryName2";

    private static final String IS_ALL_COUNTRY = "isAllCountry";

    @BindColor(R.color.white)
    int titleColor;

    @BindString(R.string.load_wait)
    String DialogMsg;

    @BindString(R.string.NoData)
    String NoData;

    @BindString(R.string.DataError)
    String DataError;

    @BindString(R.string.NetworkAnomaly)
    String NetworkAnomaly;

    @Bind(R.id.select_country_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.select_country_dialog)
    TextView textDialog;

    @Bind(R.id.select_country_index_side_bar)
    SideBar sideBar;

    @Bind(R.id.select_country_index_list_view)
    ListView countryListView;

    private ProgressDialog progressDialog;

    private SelectCountryAdapter adapter;

    private HaveCountryAdapter haveAdapter;

    private CharacterParser characterParser;

    private CountryNameComparator countryNameComparator;

    private HaveCountryNameComparator haveCountryNameComparator;

    private List<CountryAssistData> list;

    private List<HaveAssistCountry> haveList;

    private String selectCountryId;

    private String selectCountryCNname;

    private String selectCountryUSname;

    private boolean isAllCountry = false;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_country);
        ButterKnife.bind(this);

        String otherTag = getIntent().getStringExtra(OTHER_TAG);
        DeBugLog.i(TAG, "otherTag:" + otherTag);
        if (!TextUtils.isEmpty(otherTag) && otherTag.equals(ProblemFragment.class.getSimpleName())) isAllCountry = true;

        initView();
        viewAction();
        getCountryData4Service();
    }

    /**
     * 初始化方法
     */
    private void initView() {
        toolbar.setTitleTextColor(titleColor);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(DialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        characterParser = CharacterParser.getInstance();
        countryNameComparator = new CountryNameComparator();
        haveCountryNameComparator = new HaveCountryNameComparator();

        context = this;

        sideBar.setTextView(textDialog);

        verification = SuiuuInfo.ReadVerification(this);
        try {
            token = URLEncoder.encode(SuiuuInfo.ReadAppTimeSign(this), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void viewAction() {

        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                int position = isAllCountry ? haveAdapter.getPositionForSection(s.charAt(0))
                        : adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    countryListView.setSelection(position);
                }
            }
        });

        countryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectCountryId = isAllCountry ? haveList.get(position).getQCountryId() : list.get(position).getId();
                selectCountryCNname = isAllCountry ? haveList.get(position).getCname() : list.get(position).getCname();
                selectCountryUSname = isAllCountry ? haveList.get(position).getCname() : list.get(position).getEname();

                Toast.makeText(context, "您选择的国家为:" + selectCountryCNname, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(context, SelectCityActivity.class);
                intent.putExtra(COUNTRY_ID, selectCountryId);
                intent.putExtra(COUNTRY_CN_NAME, selectCountryCNname);
                intent.putExtra(COUNTRY_NAME_2, selectCountryUSname);
                intent.putExtra(IS_ALL_COUNTRY, isAllCountry);
                startActivityForResult(intent, AppConstant.SELECT_CITY_OK);
            }
        });

    }

    private void getCountryData4Service() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }

        String baseUrl = isAllCountry ? HttpNewServicePath.getHaveCountryPath : HttpNewServicePath.getCountryData;
        String url = baseUrl + "?" + HttpNewServicePath.key + "=" + verification + "&" + TOKEN + "=" + token;
        DeBugLog.i(TAG, "请求URL:" + url);

        try {
            OkHttpManager.onGetAsynRequest(url,
                    isAllCountry ? new HaveCountryResultCallback() : new SelectCountryResultCallback());
        } catch (IOException e) {
            e.printStackTrace();
            hideDialog();
            Toast.makeText(context, NetworkAnomaly, Toast.LENGTH_SHORT).show();
        }
    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            String countryId = data.getStringExtra(COUNTRY_ID);
            String countryCNname = data.getStringExtra(COUNTRY_CN_NAME);
            String cityId = data.getStringExtra(CITY_ID);
            String cityName = data.getStringExtra(CITY_NAME);

            DeBugLog.i(TAG, "countryId:" + countryId);
            DeBugLog.i(TAG, "countryCNname:" + countryCNname);
            DeBugLog.i(TAG, "cityId:" + cityId);
            DeBugLog.i(TAG, "cityName:" + cityName);

            setResult(RESULT_OK, data);
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<CountryAssistData> TransformationData(List<CountryData> countryDataList) {
        List<CountryAssistData> list = new ArrayList<>();
        for (CountryData data : countryDataList) {
            CountryAssistData cad = new CountryAssistData();
            cad.setId(data.getId());
            cad.setAreaCode(data.getAreaCode());
            cad.setCode(data.getCode());
            cad.setEname(data.getEname());
            cad.setCname(data.getCname());
            String firstLetter = characterParser.getSelling(data.getCname()).substring(0, 1).toLowerCase(Locale.getDefault());
            cad.setFirstLetter(firstLetter);
            list.add(cad);
        }
        return list;
    }

    private List<HaveAssistCountry> HaveTransformationData(List<HaveCountry.HaveCountryData> haveList) {
        List<HaveAssistCountry> list = new ArrayList<>();
        for (HaveCountry.HaveCountryData haveCountryData : haveList) {
            HaveAssistCountry haveAssistCountry = new HaveAssistCountry();
            haveAssistCountry.setQCountryId(haveCountryData.getQCountryId());
            haveAssistCountry.setCname(haveCountryData.getCname());
            haveAssistCountry.setEname(haveCountryData.getEname());
            String firstLetter = characterParser.getSelling(haveCountryData.getCname()).substring(0, 1)
                    .toLowerCase(Locale.getDefault());
            haveAssistCountry.setFirstLetter(firstLetter);
            list.add(haveAssistCountry);
        }
        return list;
    }

    private class SelectCountryResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            DeBugLog.i(TAG, "返回的国家信息数据:" + response);
            if (TextUtils.isEmpty(response)) {
                Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
            } else try {
                Country country = JsonUtils.getInstance().fromJSON(Country.class, response);
                List<CountryData> countryDataList = country.getData();
                if (countryDataList != null && countryDataList.size() > 0) {
                    list = TransformationData(countryDataList);
                    Collections.sort(list, countryNameComparator);
                    adapter = new SelectCountryAdapter(context, list);
                    countryListView.setAdapter(adapter);
                } else {
                    Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "解析失败:" + e.getMessage());
                Toast.makeText(context, DataError, Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onError(Request request, Exception e) {
            DeBugLog.e(TAG, "国家数据请求失败:" + e.getMessage());
            Toast.makeText(context, NetworkAnomaly, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish() {
            hideDialog();
        }

    }

    private class HaveCountryResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            DeBugLog.i(TAG, "返回已有的国家信息数据:" + response);
            if (TextUtils.isEmpty(response)) {
                Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
            } else try {
                HaveCountry haveCountry = JsonUtils.getInstance().fromJSON(HaveCountry.class, response);
                List<HaveCountry.HaveCountryData> list = haveCountry.getData();
                if (list != null && list.size() > 0) {
                    haveList = HaveTransformationData(list);
                    Collections.sort(haveList, haveCountryNameComparator);
                    haveAdapter = new HaveCountryAdapter(context, haveList);
                    countryListView.setAdapter(haveAdapter);
                } else {
                    Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "解析失败:" + e.getMessage());
                Toast.makeText(context, DataError, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            DeBugLog.e(TAG, "已有的国家数据请求失败:" + e.getMessage());
            Toast.makeText(context, NetworkAnomaly, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish() {
            hideDialog();
        }

    }

}