package com.minglang.suiuu.activity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiItemDetail;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.SearchBound;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.SearchNearMessageAdapter;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.customview.ReFlashListView;
import com.minglang.suiuu.customview.TextProgressDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class AMapActivity extends BaseActivity implements
        AMapLocationListener, PoiSearch.OnPoiSearchListener, ReFlashListView.IReflashListener, ReFlashListView.ILoadMoreDataListener {
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private String address;
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private int searchType = 0;// 搜索类型
    private PoiSearch poiSearch;
    private PoiResult poiResult; // poi返回的结果
    private List<PoiItem> poiItems;// poi数据
    private SearchNearMessageAdapter adapter;
    private TextProgressDialog dialog;
    @Bind(R.id.et_amp_location_search)
    EditText et_amp_location_search;
    @Bind(R.id.lv_location_message)
    ReFlashListView lv_location_message;
    @Bind(R.id.iv_top_callback)
    ImageView iv_top_callback;
    @Bind(R.id.iv_amp_top_search)
    ImageView iv_amp_top_search;
    private String searchKey;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amap);
        ButterKnife.bind(this);
        init();
        viewAction();
        dialog.showDialog();
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        LocationManagerProxy mLocationManagerProxy = LocationManagerProxy.getInstance(this);
        //此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        //注意设置合适的定位时间的间隔，并且在合适时间调用removeUpdates()方法来取消定位请求
        //在定位结束后，在合适的生命周期调用destroy()方法
        //其中如果间隔时间为-1，则定位只定一次
        mLocationManagerProxy.requestLocationData(
                LocationProviderProxy.AMapNetwork, -1, 15, this);
        mLocationManagerProxy.setGpsEnable(false);
        dialog = new TextProgressDialog(this);
        poiItems = new ArrayList<>();


    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() {
        LatLonPoint lp = new LatLonPoint(latitude, longitude);// 默认西单广场
        query = new PoiSearch.Query("", "", city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(30);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        query.setLimitDiscount(false);
        query.setLimitGroupbuy(false);

        if (lp != null) {
            poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.setBound(new SearchBound(lp, 2000, true));//
            poiSearch.searchPOIAsyn();// 异步搜索
        }
    }

    public void keySearch() {
        dialog.showDialog();
        query = new PoiSearch.Query(searchKey, "", city);
        // keyWord表示搜索字符串，
        // 第二个参数表示POI搜索类型，二者选填其一，
        //POI搜索类型共分为以下20种：汽车服务|汽车销售|
        // 汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|
        //住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|
        //金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施
        //cityCode表示POI搜索区域的编码，是必须设置参数
        query.setPageSize(30);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);//设置查询页码
        PoiSearch poiSearch = new PoiSearch(this, query);//初始化poiSearch对象
        poiSearch.setOnPoiSearchListener(this);//设置回调数据的监听器
        poiSearch.searchPOIAsyn();//开始搜索
    }

    /**
     * POI搜索回调方法
     */
    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        if (rCode == 0) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    poiItems.addAll(poiResult.getPois());// 取得第一页的poiitem数据，页数从数字0开始
                }
            }
        }
        if (currentPage == 1 && poiResult.getPois().size() < 1) {
            dialog.dismissDialog();
            Toast.makeText(this, R.string.no_search_data, Toast.LENGTH_SHORT).show();
        } else {
            showList();
        }
    }

    private void showList() {
        if (adapter == null) {
            lv_location_message.setInterface(this);
            lv_location_message.setLoadMoreInterface(this);
            adapter = new SearchNearMessageAdapter(AMapActivity.this, poiItems);
            lv_location_message.setAdapter(adapter);
        } else {
            adapter.onDateChange(poiItems);
        }
        dialog.dismissDialog();
    }

    @Override
    public void onPoiItemDetailSearched(PoiItemDetail poiItemDetail, int i) {
    }


    private void viewAction() {
        lv_location_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = AMapActivity.this.getIntent();
                intent.putExtra("latitude", poiItems.get(position - 1).getLatLonPoint().getLatitude());
                intent.putExtra("longitude", poiItems.get(position - 1).getLatLonPoint().getLongitude());
                intent.putExtra("address", TextUtils.isEmpty(poiItems.get(position - 1).getSnippet()) ? poiItems.get(position - 1).toString() : poiItems.get(position - 1).getSnippet());
                intent.putExtra("country", country);
                intent.putExtra("city", city);
                AMapActivity.this.setResult(RESULT_OK, intent);
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
            }
        });
        iv_top_callback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        iv_amp_top_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKey = et_amp_location_search.getText().toString().trim();
                if (TextUtils.isEmpty(searchKey)) {
                    Toast.makeText(AMapActivity.this, "请输入搜索地点", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentPage = 1;
                poiItems.clear();
                keySearch();
            }
        });

    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null && amapLocation.getAMapException().getErrorCode() == 0) {
            //获取位置信息
            latitude = amapLocation.getLatitude();
            longitude = amapLocation.getLongitude();
            address = amapLocation.getAddress();
            country = amapLocation.getCountry();
            city = amapLocation.getCity();
            doSearchQuery();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    protected void onPause() {
        super.onPause();
        //stopLocation();
    }

    @Override
    public void onLoadMoreData() {
        dialog.showDialog();
        currentPage += 1;
        if (TextUtils.isEmpty(searchKey)) {
            doSearchQuery();
        } else {
            keySearch();
        }
        lv_location_message.loadComplete();
    }

    @Override
    public void onReflash() {
        dialog.showDialog();
        poiItems.clear();
        currentPage = 0;
        doSearchQuery();
        lv_location_message.reflashComplete();
    }
}
