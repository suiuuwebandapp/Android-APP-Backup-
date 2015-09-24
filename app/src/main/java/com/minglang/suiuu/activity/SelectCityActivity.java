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
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.HaveCityAdapter;
import com.minglang.suiuu.adapter.SelectCityAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.customview.SideBar;
import com.minglang.suiuu.entity.City;
import com.minglang.suiuu.entity.CityAssistData;
import com.minglang.suiuu.entity.CityData;
import com.minglang.suiuu.entity.HaveAssistCity;
import com.minglang.suiuu.entity.HaveCity;
import com.minglang.suiuu.utils.CharacterParser;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.comparator.CityNameComparator;
import com.minglang.suiuu.utils.comparator.HaveCityNameComparator;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.HttpServicePath;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONObject;

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

public class SelectCityActivity extends BaseAppCompatActivity {

    private static final String TAG = SelectCityActivity.class.getSimpleName();

    private static final String COUNTRY_ID = "countryId";
    private static final String COUNTRY_C_NAME = "countryCNname";

    private static final String CITY_ID = "cityId";

    private static final String COUNTRY_CN_NAME = "countryCNname";
    private static final String CITY_NAME = "cityName";

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

    @Bind(R.id.select_city_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.select_city_sidebar)
    SideBar sideBar;

    @Bind(R.id.select_city_list_view)
    ListView cityListView;

    private ProgressDialog progressDialog;

    private SelectCityAdapter adapter;

    private HaveCityAdapter haveCityAdapter;

    private CharacterParser characterParser;

    private CityNameComparator cityNameComparator;

    private HaveCityNameComparator haveCityNameComparator;

    private List<CityAssistData> list;

    private List<HaveAssistCity> haveList;

    private String countryId;

    private String countryCNname;

    private String selectCityName;

    private String selectCityId;

    private boolean isAllCountry = false;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        Intent intent = getIntent();
        countryId = intent.getStringExtra(COUNTRY_ID);
        countryCNname = intent.getStringExtra(COUNTRY_C_NAME);
        isAllCountry = intent.getBooleanExtra(IS_ALL_COUNTRY, false);

        ButterKnife.bind(this);
        initView();
        getSelectCity4Service();
        viewAction();
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
        cityNameComparator = new CityNameComparator();
        haveCityNameComparator = new HaveCityNameComparator();

        context = this;

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
                int position = isAllCountry ? haveCityAdapter.getPositionForSection(s.charAt(0))
                        : adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    cityListView.setSelection(position);
                }
            }
        });

        cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectCityName = isAllCountry ? haveList.get(position).getCname() : list.get(position).getCname();
                selectCityId = isAllCountry ? haveList.get(position).getqCityId() : list.get(position).getId();

                Toast.makeText(context, "您选择的城市为:" + selectCityName, Toast.LENGTH_SHORT).show();

                if (TextUtils.isEmpty(selectCityId) && TextUtils.isEmpty(selectCityName)) {
                    Toast.makeText(context, "请选择城市!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra(COUNTRY_ID, countryId);
                    intent.putExtra(COUNTRY_CN_NAME, countryCNname);
                    intent.putExtra(CITY_ID, selectCityId);
                    intent.putExtra(CITY_NAME, selectCityName);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });
    }

    private void getSelectCity4Service() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }

        String[] keyArray = new String[]{HttpServicePath.key, COUNTRY_ID, TOKEN};
        String[] valueArray = new String[]{verification, countryId, token};

        String baseUrl = isAllCountry ? HttpNewServicePath.getHaveCityPath : HttpNewServicePath.getCityListPath;
        String url = addUrlAndParams(baseUrl, keyArray, valueArray);

        try {
            OkHttpManager.onGetAsynRequest(url, isAllCountry ? new HaveCountryResultCallback() : new SelectCityResultCallback());
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

    private List<CityAssistData> TransformationData(List<CityData> countryDataList) {
        List<CityAssistData> list = new ArrayList<>();
        for (CityData data : countryDataList) {
            CityAssistData cad = new CityAssistData();
            cad.setId(data.getId());
            cad.setCode(data.getCode());
            cad.setEname(data.getEname());
            cad.setCname(data.getCname());
            String firstLetter = characterParser.getSelling(data.getCname()).substring(0, 1).toLowerCase(Locale.getDefault());
            cad.setFirstLetter(firstLetter);
            list.add(cad);
        }
        return list;
    }

    private List<HaveAssistCity> HaveTransformationData(List<HaveCity.HaveCityData> haveList) {
        List<HaveAssistCity> list = new ArrayList<>();
        for (HaveCity.HaveCityData data : haveList) {
            HaveAssistCity assistCity = new HaveAssistCity();
            assistCity.setqCityId(data.getQCityId());
            assistCity.setCname(data.getCname());
            assistCity.setEname(data.getEname());
            String firstLetter = characterParser.getSelling(data.getCname()).substring(0, 1).toLowerCase(Locale.getDefault());
            assistCity.setFirstLetter(firstLetter);
            list.add(assistCity);
        }
        return list;
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

    private class SelectCityResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            if (TextUtils.isEmpty(response)) {
                Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
            } else try {
                City city = JsonUtils.getInstance().fromJSON(City.class, response);
                List<CityData> dataList = city.getData();
                if (dataList != null && dataList.size() > 0) {
                    list = TransformationData(dataList);
                    Collections.sort(list, cityNameComparator);
                    adapter = new SelectCityAdapter(context, list);
                    cityListView.setAdapter(adapter);
                } else {
                    Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                L.e(TAG, "数据解析错误:" + e.getMessage());
                Toast.makeText(context, DataError, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "Exception:" + e.getMessage());
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
            L.i(TAG, "返回已有的国家的城市的数据:" + response);
            if (TextUtils.isEmpty(response)) {
                Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
            } else try {
                HaveCity haveCity = JsonUtils.getInstance().fromJSON(HaveCity.class, response);
                List<HaveCity.HaveCityData> list = haveCity.getData();
                if (list != null && list.size() > 0) {
                    haveList = HaveTransformationData(list);
                    Collections.sort(haveList, haveCityNameComparator);
                    haveCityAdapter = new HaveCityAdapter(context, haveList);
                    cityListView.setAdapter(haveCityAdapter);
                } else {
                    Intent intent = new Intent();
                    intent.putExtra(COUNTRY_ID, countryId);
                    intent.putExtra(COUNTRY_CN_NAME, countryCNname);
                    intent.putExtra(CITY_ID, "");
                    intent.putExtra(CITY_NAME, "");
                    setResult(RESULT_OK, intent);
                    finish();
                }
            } catch (Exception e) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray data = object.getJSONArray("data");
                    String city = data.get(0).toString();
                    JSONObject cityObject = new JSONObject(city);

                    String cityId = cityObject.get("qCityId").toString();
                    String cCityName = cityObject.get("cname").toString();

                    Intent intent = new Intent();
                    intent.putExtra(COUNTRY_ID, countryId);
                    intent.putExtra(COUNTRY_CN_NAME, countryCNname);

                    if (TextUtils.isEmpty(cityId)) {
                        intent.putExtra(CITY_ID, "");
                        intent.putExtra(CITY_NAME, "");
                    } else {
                        intent.putExtra(CITY_ID, "");
                        intent.putExtra(CITY_NAME, cCityName);
                    }
                    setResult(RESULT_OK, intent);
                    finish();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    L.e(TAG, "数据解析错误:" + ex.getMessage());
                    Toast.makeText(context, DataError, Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "Exception:" + e.getMessage());
            Toast.makeText(context, NetworkAnomaly, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish() {
            hideDialog();
        }

    }

}