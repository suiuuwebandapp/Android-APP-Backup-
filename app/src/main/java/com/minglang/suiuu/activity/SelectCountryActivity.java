package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.SelectCountryAdapter;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.customview.SideBar;
import com.minglang.suiuu.entity.Country;
import com.minglang.suiuu.entity.CountryAssistData;
import com.minglang.suiuu.entity.CountryData;
import com.minglang.suiuu.utils.AppConstant;
import com.minglang.suiuu.utils.CharacterParser;
import com.minglang.suiuu.utils.CountryNameComparator;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuiuuHttp;
import com.minglang.suiuu.utils.SuiuuInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

public class SelectCountryActivity extends BaseActivity {

    private static final String TAG = SelectCountryActivity.class.getSimpleName();

    private static final String COUNTRY_ID = "countryId";
    private static final String CITY_ID = "cityId";

    private static final String COUNTRY_CN_NAME = "countryCNname";
    private static final String CITY_NAME = "cityName";

    @BindString(R.string.load_wait)
    String wait;

    @BindString(R.string.NoData)
    String NoData;

    @BindString(R.string.DataError)
    String DataError;

    @BindString(R.string.NetworkAnomaly)
    String NetworkAnomaly;

    @Bind(R.id.select_country_back)
    ImageView back;

    @Bind(R.id.select_country_dialog)
    TextView textDialog;

    @Bind(R.id.select_country_index_side_bar)
    SideBar sideBar;

    @Bind(R.id.select_country_index_list_view)
    ListView countryListView;

    private ProgressDialog progressDialog;

    private SelectCountryAdapter adapter;

    private CharacterParser characterParser;

    private CountryNameComparator countryNameComparator;

    private List<CountryAssistData> list;

    private String selectCountryId;

    private String selectCountryCNname;

    private String selectCountryUSname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_country);
        ButterKnife.bind(this);
        initView();
        ViewAction();
        getCountryData4Service();
    }

    /**
     * 初始化方法
     */
    private void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(wait);
        progressDialog.setCanceledOnTouchOutside(false);

        characterParser = CharacterParser.getInstance();
        countryNameComparator = new CountryNameComparator();

        sideBar.setTextView(textDialog);
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
                    countryListView.setSelection(position);
                }
            }
        });

        countryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CountryAssistData data = list.get(position);
                selectCountryId = data.getId();
                selectCountryCNname = data.getCname();
                selectCountryUSname = data.getEname();

                Toast.makeText(SelectCountryActivity.this,
                        "您选择的国家为:" + selectCountryCNname, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(SelectCountryActivity.this, SelectCityActivity.class);
                intent.putExtra(COUNTRY_ID, selectCountryId);
                intent.putExtra("countryCNname", selectCountryCNname);
                intent.putExtra("countryName2", selectCountryUSname);
                startActivityForResult(intent, AppConstant.SELECT_CITY_OK);
            }
        });

    }

    private void getCountryData4Service() {
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, SuiuuInfo.ReadVerification(this));

        SuiuuHttp httpRequest = new SuiuuHttp(HttpRequest.HttpMethod.POST,
                HttpServicePath.getCountryData, new SelectCountryRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.executive();
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

    /**
     * 国家列表网络请求回调接口
     */
    private class SelectCountryRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onStart() {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            String str = stringResponseInfo.result;
            DeBugLog.i(TAG, "返回的国家信息数据:" + str);
            hideDialog();
            try {
                Country country = JsonUtils.getInstance().fromJSON(Country.class, str);
                List<CountryData> countryDataList = country.getData();
                if (countryDataList != null && countryDataList.size() > 0) {
                    list = TransformationData(countryDataList);
                    Collections.sort(list, countryNameComparator);
                    adapter = new SelectCountryAdapter(SelectCountryActivity.this, list);
                    countryListView.setAdapter(adapter);
                } else {
                    Toast.makeText(SelectCountryActivity.this, NoData, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "解析失败:" + e.getMessage());
                Toast.makeText(SelectCountryActivity.this, DataError, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "国家数据请求失败:" + s);
            hideDialog();
            Toast.makeText(SelectCountryActivity.this, NetworkAnomaly, Toast.LENGTH_SHORT).show();
        }

    }

}