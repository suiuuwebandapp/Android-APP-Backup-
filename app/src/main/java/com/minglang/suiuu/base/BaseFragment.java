package com.minglang.suiuu.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minglang.suiuu.activity.FirstLoginActivity;
import com.minglang.suiuu.utils.L;

import java.net.URLEncoder;

public class BaseFragment extends Fragment {

    private static final String TAG = BaseFragment.class.getSimpleName();

    public static final String PAGE = "page";
    public static final String NUMBER = "number";
    public static final String TOKEN = "token";

    public static final String USER_SIGN = "userSign";

    public static final String HEAD_IMG = "headImagePath";

    public static final String ID = "id";

    public String userSign;
    public String verification;
    public String token;

    /**
     * 屏幕高度
     */
    public int screenHeight;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        L.i(TAG, getClass().getSimpleName() + ":onAttach(Context)");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        L.i(TAG, getClass().getSimpleName() + ":onAttach(Activity)");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.i(TAG, getClass().getSimpleName() + ":onCreate(Bundle)");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        L.i(TAG, getClass().getSimpleName() + ":onCreateView(LayoutInflater,ViewGroup,Bundle)");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        L.i(TAG, getClass().getSimpleName() + ":onActivityCreated(Bundle)");
    }

    @Override
    public void onStart() {
        super.onStart();
        L.i(TAG, getClass().getSimpleName() + ":onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        L.i(TAG, getClass().getSimpleName() + ":onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        L.i(TAG, getClass().getSimpleName() + ":onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        L.i(TAG, getClass().getSimpleName() + ":onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        L.i(TAG, getClass().getSimpleName() + ":onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.i(TAG, getClass().getSimpleName() + ":onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        L.i(TAG, getClass().getSimpleName() + ":onDetach()");
    }

    public String addUrlAndParams(String url, String[] keyArray, String[] valueArray) {
        String _url = url + "?";
        for (int i = 0; i < keyArray.length; i++) {
            String key = keyArray[i];
            String value;
            try {
                value = URLEncoder.encode(valueArray[i], "UTF-8");
            } catch (Exception e) {
                L.e(TAG, "参数值Encoder异常:" + e.getMessage());
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

    public void ReturnLoginActivity(Activity activity) {
        Intent intent = new Intent(activity, FirstLoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

}