package com.minglang.suiuu.service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.SuiuuInfo;

public class LocationService extends Service implements AMapLocationListener {

    private static final String TAG = LocationService.class.getSimpleName();

    private LocationManagerProxy mLocationManagerProxy;

    private final IBinder locationBinder = new LocationBinder();

    public LocationService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        L.i(TAG, "Service Create");
        if (mLocationManagerProxy == null) {
            mLocationManagerProxy = LocationManagerProxy.getInstance(this);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        L.i(TAG, "Service Start Command");

        if (mLocationManagerProxy == null) {
            mLocationManagerProxy = LocationManagerProxy.getInstance(this);
        }

        //此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        //注意设置合适的定位时间的间隔，并且在合适时间调用removeUpdates()方法来取消定位请求
        //在定位结束后，在合适的生命周期调用destroy()方法
        //其中如果间隔时间为-1，则定位只定一次
        mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, -1, 15, this);
        mLocationManagerProxy.setGpsEnable(false);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        L.i(TAG, "Service Destroy");

        if (mLocationManagerProxy != null) {
            mLocationManagerProxy.removeUpdates(this);
            mLocationManagerProxy.destroy();
        }
        mLocationManagerProxy = null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        L.i(TAG, "Service Bind");
        return locationBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        L.i(TAG, "Service Unbind");
        return super.onUnbind(intent);
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getAMapException().getErrorCode() == 0) {
            Double latitude = aMapLocation.getLatitude();
            Double longitude = aMapLocation.getLongitude();
            SuiuuInfo.WriteUserLocation(this, latitude, longitude);
            L.i(TAG, "latitude:" + latitude + ",longitude:" + longitude);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Double latitude = location.getLatitude();
        Double longitude = location.getLongitude();
        L.i(TAG, "latitude:" + latitude + ",longitude:" + longitude);
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

    public class LocationBinder extends Binder {

        public LocationService getLocationService() {
            return LocationService.this;
        }

        public void endService() {
            LocationService.this.stopSelf();
        }

    }

}