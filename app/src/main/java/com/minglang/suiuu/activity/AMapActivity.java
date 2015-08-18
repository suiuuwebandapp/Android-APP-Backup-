package com.minglang.suiuu.activity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

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
    private ReFlashListView lv_location_message;
    private SearchNearMessageAdapter adapter;
    private TextProgressDialog dialog;
    private ImageView iv_top_callback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amap);
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
        lv_location_message = (ReFlashListView) findViewById(R.id.lv_location_message);
        iv_top_callback = (ImageView) findViewById(R.id.iv_top_callback);
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
            poiSearch.setOnPoiSearchListener((PoiSearch.OnPoiSearchListener) this);
            poiSearch.setBound(new SearchBound(lp, 2000, true));//
            poiSearch.searchPOIAsyn();// 异步搜索
        }
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
        showList();
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
                intent.putExtra("address", TextUtils.isEmpty(poiItems.get(position - 1).getSnippet()) ?poiItems.get(position - 1).toString():poiItems.get(position - 1).getSnippet());
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
        doSearchQuery();
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
