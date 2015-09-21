package com.minglang.suiuu.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.minglang.suiuu.utils.L;

import java.net.URLEncoder;

public class BaseAppCompatActivity extends AppCompatActivity {

    private static final String TAG = BaseAppCompatActivity.class.getSimpleName();

    public static final String TOKEN = "token";

    public static final String USER_SIGN = "userSign";

    public static final String STATUS = "status";
    public static final String DATA = "data";

    public static final String PAGE = "page";
    public static final String NUMBER = "number";

    public DisplayMetrics dm;

    /**
     * 屏幕宽度
     */
    public int screenWidth;
    /**
     * 屏幕高度
     */
    public int screenHeight;

    /**
     * 屏幕密度
     * (像素比例：0.75, 1.0, 1.5, 2.0)
     */
    public float density;

    /**
     * 屏幕密度
     * (每寸像素：120, 160, 240, 320)
     */
    public int densityDPI;

    public String userSign;
    public String verification;
    public String token;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        initScreen();
    }

    private void initScreen() {
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        density = dm.density;
        densityDPI = dm.densityDpi;
    }

    public String addUrlAndParams(String url, String[] keyArray, String[] valueArray) {
        String _url = url + "?";
        for (int i = 0; i < keyArray.length; i++) {
            String key = keyArray[i];
            String value;
            try {
                value = URLEncoder.encode(valueArray[i], "UTF-8");
            } catch (Exception e) {
                L.e(TAG, e.getMessage());
                value = "";
            }

            if (keyArray.length > i + 1) {
                _url = _url + key + "=" + value + "&";
            } else {
                _url = _url + key + "=" + value;
            }
        }
        return _url;
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