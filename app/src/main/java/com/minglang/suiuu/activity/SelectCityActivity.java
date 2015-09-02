package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.SelectCityAdapter;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.customview.SideBar;
import com.minglang.suiuu.entity.City;
import com.minglang.suiuu.entity.CityAssistData;
import com.minglang.suiuu.entity.CityData;
import com.minglang.suiuu.utils.CharacterParser;
import com.minglang.suiuu.utils.CityNameComparator;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpNewServicePath;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.OkHttpManager;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

public class SelectCityActivity extends BaseActivity {

    private static final String TAG = SelectCityActivity.class.getSimpleName();

    private static final String COUNTRY_ID = "countryId";
    private static final String COUNTRY_C_NAME = "countryCNname";

    private static final String CITY_ID = "cityId";

    private static final String COUNTRY_CN_NAME = "countryCNname";
    private static final String CITY_NAME = "cityName";

    @BindString(R.string.load_wait)
    String DialogMsg;

    @BindString(R.string.NoData)
    String NoData;

    @BindString(R.string.DataError)
    String DataError;

    @BindString(R.string.NetworkAnomaly)
    String NetworkAnomaly;

    @Bind(R.id.select_city_back)
    ImageView back;

    @Bind(R.id.select_city_sidebar)
    SideBar sideBar;

    @Bind(R.id.select_city_list_view)
    ListView cityListView;

    private ProgressDialog progressDialog;

    private SelectCityAdapter adapter;

    private CharacterParser characterParser;

    private CityNameComparator cityNameComparator;

    private List<CityAssistData> list;

    private String countryId;

    private String countryCNname;

    private String selectCityName;

    private String selectCityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        Intent intent = getIntent();
        countryId = intent.getStringExtra(COUNTRY_ID);
        countryCNname = intent.getStringExtra(COUNTRY_C_NAME);

        ButterKnife.bind(this);
        initView();
        getSelectCity4Service();
        viewAction();
    }

    /**
     * 初始化方法
     */
    private void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(DialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        characterParser = CharacterParser.getInstance();
        cityNameComparator = new CityNameComparator();

        verification = SuiuuInfo.ReadVerification(this);
        token = SuiuuInfo.ReadAppTimeSign(this);
    }

    private void viewAction() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    cityListView.setSelection(position);
                }
            }
        });

        cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CityAssistData data = list.get(position);
                selectCityName = data.getCname();
                selectCityId = data.getId();

                Toast.makeText(SelectCityActivity.this,
                        "您选择的城市为:" + selectCityName, Toast.LENGTH_SHORT).show();

                if (TextUtils.isEmpty(selectCityId) && TextUtils.isEmpty(selectCityName)) {
                    Toast.makeText(SelectCityActivity.this, "请选择城市!", Toast.LENGTH_SHORT).show();
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
        String url = addUrlAndParams(HttpNewServicePath.getCityListPath, keyArray, valueArray);

        try {
            OkHttpManager.onGetAsynRequest(url, new SelectCityResultCallback());
        } catch (IOException e) {
            e.printStackTrace();
            hideDialog();
            Toast.makeText(SelectCityActivity.this, NetworkAnomaly, Toast.LENGTH_SHORT).show();
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

    private class SelectCityResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            hideDialog();
            if (TextUtils.isEmpty(response)) {
                Toast.makeText(SelectCityActivity.this, NoData, Toast.LENGTH_SHORT).show();
            } else {
                try {
                    City city = JsonUtils.getInstance().fromJSON(City.class, response);
                    List<CityData> dataList = city.getData();
                    list = TransformationData(dataList);
                    Collections.sort(list, cityNameComparator);
                    adapter = new SelectCityAdapter(SelectCityActivity.this, list);
                    cityListView.setAdapter(adapter);
                } catch (Exception e) {
                    DeBugLog.e(TAG, "数据解析错误:" + e.getMessage());
                    Toast.makeText(SelectCityActivity.this, DataError, Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            DeBugLog.e(TAG, "Exception:" + e.getMessage());
            hideDialog();
            Toast.makeText(SelectCityActivity.this, NetworkAnomaly, Toast.LENGTH_SHORT).show();
        }

    }

}