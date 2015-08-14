
package com.minglang.suiuu.activity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseActivity;


public class BaiduMapActivity extends BaseActivity implements LocationSource,
        AMapLocationListener {
    private MapView mapView;
    private AMap aMap;
    private UiSettings mUiSettings;
    private OnLocationChangedListener mListener;
    private LocationManagerProxy aMapManager;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private String address ;
    private Button btn_location_send;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidumap);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 必须要写
        init();
        viewAction();
        aMap.setLocationSource(this);// 设置定位监听
        mUiSettings.setMyLocationButtonEnabled(true);// 是否显示定位按钮
        aMap.setMyLocationEnabled(true);//
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        btn_location_send = (Button) findViewById(R.id.btn_location_send);
        if (aMap == null) {
            aMap = mapView.getMap();
            mUiSettings = aMap.getUiSettings();


        }
    }

    private void viewAction() {
        btn_location_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("suiuu", "点击了吧");
                Intent intent = BaiduMapActivity.this.getIntent();
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("address", address);
                intent.putExtra("country",country);
                intent.putExtra("city",city);
                BaiduMapActivity.this.setResult(RESULT_OK, intent);
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
            }
        });
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        deactivate();
    }
    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation aLocation) {
        if (mListener != null) {
            mListener.onLocationChanged(aLocation);// 显示系统小蓝点
        }
        Log.i("suiuu", "getAdCode=" + aLocation.getAdCode() + "getLatitude=" + aLocation.getLatitude() + "getLongitude()=" + aLocation.getLongitude() + "aLocation.getAddress()" + aLocation.getAddress());
        latitude = aLocation.getLatitude();
        longitude = aLocation.getLongitude();
        address = aLocation.getAddress();
        country = aLocation.getCountry();
        city = aLocation.getCity();
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (aMapManager == null) {
            aMapManager = LocationManagerProxy.getInstance(this);
            /*
			 * mAMapLocManager.setGpsEnable(false);//
			 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true
			 */
            // Location API定位采用GPS和网络混合定位方式，时间最短是2000毫秒
            aMapManager.requestLocationData(
                    LocationProviderProxy.AMapNetwork, 2000, 10, this);
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (aMapManager != null) {
            aMapManager.removeUpdates(this);
            aMapManager.destroy();
        }
        aMapManager = null;
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


}
