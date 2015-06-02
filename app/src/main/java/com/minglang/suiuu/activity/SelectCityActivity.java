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

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
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
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class SelectCityActivity extends BaseActivity {

    private static final String TAG = SelectCityActivity.class.getSimpleName();

    private ImageView back;

    private SideBar sideBar;

    private ListView cityListView;

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

        countryId = intent.getStringExtra("countryId");
        countryCNname = intent.getStringExtra("countryCNname");

        initView();
        getData();
        ViewAction();
    }

    private void getData() {

        if (progressDialog != null) {
            progressDialog.show();
        }

        getSelectCity4Service();
    }

    private void getSelectCity4Service() {
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, SuiuuInfo.ReadVerification(this));
        params.addBodyParameter("countryId", countryId);

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getCityListPath, new SelectCityRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    private void ViewAction() {

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
                        "您选择的城市为:" + selectCityName, Toast.LENGTH_LONG).show();

                if (TextUtils.isEmpty(selectCityId) && TextUtils.isEmpty(selectCityName)) {
                    Toast.makeText(SelectCityActivity.this, "请选择城市!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("countryId", countryId);
                    intent.putExtra("countryCNname", countryCNname);
                    intent.putExtra("cityId", selectCityId);
                    intent.putExtra("cityName", selectCityName);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });
    }

    /**
     * 初始化方法
     */
    private void initView() {
        back = (ImageView) findViewById(R.id.select_city_back);
        cityListView = (ListView) findViewById(R.id.select_city_list_view);
        sideBar = (SideBar) findViewById(R.id.select_city_sidebar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.load_wait));
        progressDialog.setCanceledOnTouchOutside(false);

        characterParser = CharacterParser.getInstance();
        cityNameComparator = new CityNameComparator();
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

    /**
     * 城市列表网络请求回调接口
     */
    private class SelectCityRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            String str = stringResponseInfo.result;
            if (TextUtils.isEmpty(str)) {
                Toast.makeText(SelectCityActivity.this, getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
            } else {
                try {
                    City city = JsonUtils.getInstance().fromJSON(City.class, str);
                    List<CityData> dataList = city.getData();
                    list = TransformationData(dataList);
                    Collections.sort(list, cityNameComparator);
                    adapter = new SelectCityAdapter(SelectCityActivity.this, list);
                    cityListView.setAdapter(adapter);
                } catch (Exception e) {
                    DeBugLog.e(TAG, "数据解析错误:" + e.getMessage());
                    Toast.makeText(SelectCityActivity.this,
                            getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            DeBugLog.e(TAG, "请求失败:" + s);
            Toast.makeText(SelectCityActivity.this,
                    getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
        }
    }
}