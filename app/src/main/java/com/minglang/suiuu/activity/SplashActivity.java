package com.minglang.suiuu.activity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.location.core.AMapLocException;
import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.chat.chat.DemoHXSDKHelper;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.SuiuuInfo;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity implements AMapLocationListener {

    private static final String TAG = SplashActivity.class.getSimpleName();

    @Bind(R.id.iv_background)
    ImageView iv_backGround;

    @Bind(R.id.iv_background2)
    ImageView iv_backGround2;

    @Bind(R.id.im_splash)
    ImageView iv_showInCenter;
    private LocationManagerProxy mLocationManagerProxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);
        init();
        AlphaAnimation animation = new AlphaAnimation(0.2f, 1.0f);
        animation.setDuration(1500);
        findViewById(R.id.root).startAnimation(animation);

        final Animation transAnim = new TranslateAnimation(0, -50, 0, 0);
        transAnim.setFillAfter(true);
        transAnim.setDuration(3500);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv_backGround.setVisibility(View.GONE);
                iv_backGround2.setVisibility(View.INVISIBLE);
                iv_backGround2.setImageResource(R.drawable.splash2);
                iv_showInCenter.setImageResource(R.drawable.splash_text);
                iv_showInCenter.setPadding(0, screenHeight / 3, 0, 0);
                iv_backGround2.setAnimation(transAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });

        transAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //		 如果用户名密码都有，直接进入主页面
                if (DemoHXSDKHelper.getInstance().isLogined()) {
                    boolean autoLogin = true;
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });
    }

    public void init() {
        mLocationManagerProxy = LocationManagerProxy.getInstance(this);

        //此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        //注意设置合适的定位时间的间隔，并且在合适时间调用removeUpdates()方法来取消定位请求
        //在定位结束后，在合适的生命周期调用destroy()方法
        //其中如果间隔时间为-1，则定位只定一次
        mLocationManagerProxy.requestLocationData(
                LocationProviderProxy.AMapNetwork, 60 * 1000, 15, this);
        mLocationManagerProxy.setGpsEnable(false);
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            try {
                AMapLocException aMapLocException = amapLocation.getAMapException();
                DeBugLog.e(TAG, "ErrorCode1:" + aMapLocException.getErrorCode());
                DeBugLog.e(TAG, "ErrorCode1.5:" + aMapLocException.getMessage());
            } catch (Exception e) {
                DeBugLog.e(TAG, "ErrorCOde2:" + e.getMessage());
            }
//            if (aMapLocException.getErrorCode() == 0) {
            //获取位置信息
            Double geoLat = amapLocation.getLatitude();
            Double geoLng = amapLocation.getLongitude();
            DeBugLog.i(TAG, "当前用户位置lat=" + geoLat + ",lng=" + geoLng);
            SuiuuInfo.WriteUserLocation(this, geoLat, geoLng);
//            }
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
//        stopLocation();
    }

    private void stopLocation() {
        if (mLocationManagerProxy != null) {
            mLocationManagerProxy.removeUpdates(this);
            mLocationManagerProxy.destory();
        }
        mLocationManagerProxy = null;
    }

}