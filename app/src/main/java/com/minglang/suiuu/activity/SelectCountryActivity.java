package com.minglang.suiuu.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.minglang.suiuu.customview.SideBar;
import com.minglang.suiuu.entity.Country;
import com.minglang.suiuu.entity.CountryAssistData;
import com.minglang.suiuu.entity.CountryData;
import com.minglang.suiuu.utils.AppConstant;
import com.minglang.suiuu.utils.CharacterParser;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtil;
import com.minglang.suiuu.utils.CountryNameComparator;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class SelectCountryActivity extends Activity {

    private static final String TAG = SelectCountryActivity.class.getSimpleName();

    private ImageView back;

    private TextView next;

    private SideBar sideBar;

    private ListView countryListView;

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

        initView();

        ViewAction();

        getData();
    }

    private void ViewAction() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectCountryActivity.this, SelectCityActivity.class);
                intent.putExtra("countryId", selectCountryId);
                intent.putExtra("countryCNname", selectCountryCNname);
//                intent.putExtra("countryName2", selectCountryUSname);
                startActivityForResult(intent, AppConstant.SELECT_CITY_OK);
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
            }
        });

    }

    private void getData() {

        if (progressDialog != null) {
            progressDialog.show();
        }

        getCountryData4Service();
    }

    private void getCountryData4Service() {
        String ver = SuiuuInfo.ReadVerification(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, ver);

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getCountryData, new SelectCountryRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            String countryId = data.getStringExtra("countryId");
            String countryCNname = data.getStringExtra("countryCNname");
            String cityId = data.getStringExtra("cityId");
            String cityName = data.getStringExtra("cityName");
            Log.i(TAG, "countryId:" + countryId);
            Log.i(TAG,"countryCNname:"+countryCNname);
            Log.i(TAG, "cityId:" + cityId);
            Log.i(TAG, "cityName:" + cityName);

            setResult(RESULT_OK, data);
            finish();
        }
    }

    /**
     * 初始化方法
     */
    private void initView() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.load_wait));
        progressDialog.setCanceledOnTouchOutside(false);

        characterParser = CharacterParser.getInstance();
        countryNameComparator = new CountryNameComparator();

        back = (ImageView) findViewById(R.id.select_country_back);
        next = (TextView) findViewById(R.id.select_country_next);
        TextView textDialog = (TextView) findViewById(R.id.select_country_dialog);
        sideBar = (SideBar) findViewById(R.id.select_country_index_side_bar);
        sideBar.setTextView(textDialog);

        countryListView = (ListView) findViewById(R.id.select_country_index_list_view);
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
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            String str = stringResponseInfo.result;
            try {
                Country country = JsonUtil.getInstance().fromJSON(Country.class, str);
                List<CountryData> countryDataList = country.getData();
                if (countryDataList != null && countryDataList.size() > 0) {
                    list = TransformationData(countryDataList);
                    Collections.sort(list, countryNameComparator);
                    adapter = new SelectCountryAdapter(SelectCountryActivity.this, list);
                    countryListView.setAdapter(adapter);
                } else {
                    Toast.makeText(SelectCountryActivity.this,
                            getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e(TAG, "解析失败:" + e.getMessage());
                Toast.makeText(SelectCountryActivity.this,
                        getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            Log.e(TAG, "国家数据请求失败:" + s);

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            Toast.makeText(SelectCountryActivity.this,
                    getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}