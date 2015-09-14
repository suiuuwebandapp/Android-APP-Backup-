package com.minglang.suiuu.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.MainSliderAdapter;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.fragment.main.CommunityFragment;
import com.minglang.suiuu.fragment.main.InformationFragment;
import com.minglang.suiuu.fragment.main.SuiuuFragment;
import com.minglang.suiuu.fragment.main.TripGalleryFragment;
import com.minglang.suiuu.receiver.ConnectionNetChangeReceiver;
import com.minglang.suiuu.utils.AppConstant;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpNewServicePath;
import com.minglang.suiuu.utils.MD5Utils;
import com.minglang.suiuu.utils.OkHttpManager;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.squareup.okhttp.Request;
import com.umeng.update.UmengUpdateAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 应用程序主界面
 */
public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int NUMBER1 = 1;
    private static final int NUMBER2 = 2;
    private static final int NUMBER3 = 3;
    private static final int NUMBER4 = 4;

    private static final String STATE = "state";

    private static final String COUNTRY_ID = "countryId";
    private static final String CITY_ID = "cityId";

    private static final String TIME_STAMP = "timestamp";
    private static final String APP_SIGN = "appSign";
    private static final String SIGN = "sign";

    private static final String STATUS = "status";
    private static final String DATA = "data";

    private static final String USER_SIGN = "userSign";

    private static final String APP_ID = "appId";
    private static final String CLIENT_TYPE = "clientType";
    private static final String VERSION_ID = "versionId";
    private static final String VERSION_MINI = "versionMini";

    @BindString(R.string.SuiuuAccount)
    String SuiuuAccount;

    @BindString(R.string.OrdinaryAccount)
    String OrdinaryAccount;

    @BindString(R.string.LoginOverdue)
    String LoginOverdue;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.slider_layout)
    RelativeLayout sliderView;

    /**
     * 点击修改昵称
     */
    @Bind(R.id.nick_name)
    TextView nickNameView;

    /**
     * 点击修改头像
     */
    @Bind(R.id.head_image)
    SimpleDraweeView headImageView;

    @Bind(R.id.switch_suiuu)
    Switch switchSuiuu;

    @Bind(R.id.side_list_view)
    ListView sideListView;

    @Bind(R.id.side_list_view_2)
    ListView sideListView2;

    @Bind(R.id.titleInfo)
    TextView titleInfo;

    @Bind(R.id.drawer_switch)
    SimpleDraweeView drawerSwitch;

    @Bind(R.id.tab1)
    LinearLayout tab1;

    @Bind(R.id.tab2)
    LinearLayout tab2;

    @Bind(R.id.tab3)
    LinearLayout tab3;

    @Bind(R.id.tab4)
    LinearLayout tab4;

    /**
     * 旅图页面
     */
    private TripGalleryFragment tripGalleryFragment;

    /**
     * 随游页面
     */
    private SuiuuFragment suiuuFragment;

    /**
     * 问答社区页面
     */
    private CommunityFragment communityFragment;

    /**
     * 消息提醒页面
     */
    private InformationFragment informationFragment;

    /**
     * 旅图页面按钮布局
     */
    @Bind(R.id.travel_image_layout)
    RelativeLayout TravelImageLayout;

    /**
     * 随游页面按钮布局
     */
    @Bind(R.id.suiuu_button_layout)
    FrameLayout SuiuuButtonLayout;

    @Bind(R.id.community_layout)
    RelativeLayout CommunityLayout;

    /**
     * 收件箱页面按钮布局
     */
    @Bind(R.id.inbox_button_layout)
    FrameLayout InboxBtnLayout;

    /**
     * 旅图页面相册按钮
     */
    @Bind(R.id.main_1_album)
    ImageView Main_1_Album;

    /**
     * 随游页面搜索按钮
     */
    @Bind(R.id.main_2_search)
    ImageView Main_2_Search;

    @Bind(R.id.main_3_search)
    ImageView Main_3_Search;

    @Bind(R.id.main_3_questions)
    ImageView Main_3_Questions;

    @Bind(R.id.main_4_search)
    ImageView Main_4_Search;

    @Bind(R.id.img1)
    ImageView iv_tab1;

    @Bind(R.id.img2)
    ImageView iv_tab2;

    @Bind(R.id.img3)
    ImageView iv_tab3;

    @Bind(R.id.img4)
    ImageView iv_tab4;

    @Bind(R.id.rl_net_error)
    TextView rl_net_error;

    private ExitReceiver exitReceiver;

    private ConnectionNetChangeReceiver connectionNetChangeReceiver;

    private TokenBroadcastReceiver tokenBroadcastReceiver;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UmengUpdateAgent.update(this);
        ButterKnife.bind(this);
        context = MainActivity.this;
        getServiceTime();
        versionCheck();
    }

    @Override
    protected void onResume() {
        super.onResume();

        String network_headImage_path = SuiuuInfo.ReadUserData(this).getHeadImg();

        if (!TextUtils.isEmpty(network_headImage_path)) {
            headImageView.setImageURI(Uri.parse(network_headImage_path));
        } else {
            headImageView.setImageURI(Uri.parse("res://com.minglang.suiuu/" + R.drawable.default_head_image_error));
        }

        String user_name = SuiuuInfo.ReadUserData(this).getNickname();
        if (!TextUtils.isEmpty(user_name)) {
            nickNameView.setText(user_name);
        }
    }

    /**
     * 得到服务器时间
     */
    private void getServiceTime() {
        try {
            OkHttpManager.onGetAsynRequest(HttpNewServicePath.getTime, new OkHttpManager.ResultCallback<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                        String status = object.getString(STATUS);
                        if (status.equals("1")) {
                            String data = object.getString(DATA);
                            getAppTimeSign(data);
                        }
                    } catch (JSONException e) {
                        DeBugLog.e(TAG, "时间数据解析异常:");
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Request request, Exception e) {
                    DeBugLog.e(TAG, "时间获取错误:" + e.getMessage());
                }

                @Override
                public void onFinish() {
                    DeBugLog.i(TAG, "Service Time Request Finish!");
                }

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据时间请求AppTimeSign
     *
     * @param time 服务器时间
     */
    private void getAppTimeSign(String time) {
        try {
            verification = SuiuuInfo.ReadVerification(this);
            String sign = MD5Utils.getMD5(time + verification + HttpNewServicePath.ConfusedCode);

            String _url = addUrlAndParams(HttpNewServicePath.getToken, new String[]{TIME_STAMP, APP_SIGN, SIGN},
                    new String[]{time, verification, sign});

            OkHttpManager.onGetAsynRequest(_url, new OkHttpManager.ResultCallback<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                        String status = object.getString(STATUS);
                        switch (status) {
                            case "1":
                                String appTimeSign = object.getString(DATA);
                                DeBugLog.i(TAG, "appTimeSign:" + appTimeSign);
                                SuiuuInfo.WriteAppTimeSign(context, appTimeSign);
                                break;
                            case "-3":
                                SuiuuInfo.ClearSuiuuInfo(context);
                                SuiuuInfo.ClearWeChatInfo(context);
                                SuiuuInfo.ClearAliPayInfo(context);
                                Toast.makeText(context, LoginOverdue, Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                DeBugLog.e(TAG, "获取失败");
                                break;
                        }
                    } catch (JSONException e) {
                        DeBugLog.e(TAG, "JSONException:" + e.getMessage());
                    }
                }

                @Override
                public void onError(Request request, Exception e) {
                    DeBugLog.e(TAG, "Network Exception:" + e.getMessage());
                }

                @Override
                public void onFinish() {
                    initView();
                    viewAction();
                }

            });
        } catch (NoSuchAlgorithmException e) {
            DeBugLog.e(TAG, "NoSuchAlgorithmException:" + e.getMessage());
        } catch (IOException e) {
            DeBugLog.e(TAG, "IOException:" + e.getMessage());
        }
    }

    /**
     * 初始化方法
     */
    private void initView() {
        mDrawerLayout.setFocusableInTouchMode(true);

        ViewGroup.LayoutParams sliderNavigationViewParams = sliderView.getLayoutParams();
        sliderNavigationViewParams.width = screenWidth / 4 * 3;
        sliderNavigationViewParams.height = screenHeight;
        sliderView.setLayoutParams(sliderNavigationViewParams);

        String strNickName = SuiuuInfo.ReadUserData(this).getNickname();
        if (!TextUtils.isEmpty(strNickName)) {
            nickNameView.setText(strNickName);
        } else {
            nickNameView.setText("");
        }

        String strHeadImagePath = SuiuuInfo.ReadUserData(this).getHeadImg();
        if (!TextUtils.isEmpty(strHeadImagePath)) {
            headImageView.setImageURI(Uri.parse(strHeadImagePath));
            drawerSwitch.setImageURI(Uri.parse(strHeadImagePath));
        } else {
            String failurePath = "res://com.minglang.suiuu/" + R.drawable.default_head_image_error;
            headImageView.setImageURI(Uri.parse(failurePath));
            drawerSwitch.setImageURI(Uri.parse(failurePath));
        }

        String publisher = SuiuuInfo.ReadUserData(this).getIsPublisher();
        if (TextUtils.isEmpty(publisher)) {
            switchSuiuu.setChecked(false);
            switchSuiuu.setText(OrdinaryAccount);
            switchSuiuu.setEnabled(false);
            sideListView.setVisibility(View.GONE);
            sideListView2.setVisibility(View.VISIBLE);
        } else {
            if (publisher.equals("1")) {
                switchSuiuu.setChecked(true);
                switchSuiuu.setText(SuiuuAccount);
                sideListView.setVisibility(View.VISIBLE);
                sideListView2.setVisibility(View.GONE);
            } else {
                switchSuiuu.setChecked(false);
                switchSuiuu.setText(OrdinaryAccount);
                switchSuiuu.setEnabled(false);
                sideListView.setVisibility(View.GONE);
                sideListView2.setVisibility(View.VISIBLE);
            }
        }

        MainSliderAdapter adapter =
                new MainSliderAdapter(this, getResources().getStringArray(R.array.sideList));
        adapter.setScreenHeight(screenHeight);
        sideListView.setAdapter(adapter);

        MainSliderAdapter adapter2 =
                new MainSliderAdapter(this, getResources().getStringArray(R.array.sideList2));
        adapter2.setScreenHeight(screenHeight);
        sideListView2.setAdapter(adapter2);

        initFragment();
        initReceiver();
    }

    private void initFragment() {
        userSign = SuiuuInfo.ReadUserSign(this);
        token = SuiuuInfo.ReadAppTimeSign(this);

        switchViewState(NUMBER1);

        tripGalleryFragment = new TripGalleryFragment();
        informationFragment = InformationFragment.newInstance(userSign, verification, token);
        communityFragment = CommunityFragment.newInstance(userSign, verification);

        LoadDefaultFragment();
    }

    private void initReceiver() {
        exitReceiver = new ExitReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SettingActivity.class.getSimpleName());
        this.registerReceiver(exitReceiver, intentFilter);

        tokenBroadcastReceiver = new TokenBroadcastReceiver();
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(tokenBroadcastReceiver, intentFilter2);

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        connectionNetChangeReceiver = new ConnectionNetChangeReceiver();
        this.registerReceiver(connectionNetChangeReceiver, filter);
    }

    private void versionCheck() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String serialNumber = tm.getDeviceId();
        DeBugLog.i(TAG, "手机串号:" + serialNumber);

        OkHttpManager.Params[] paramArray = new OkHttpManager.Params[4];
        paramArray[0] = new OkHttpManager.Params(APP_ID, serialNumber);
        paramArray[1] = new OkHttpManager.Params(CLIENT_TYPE, "androidPhone");
        paramArray[2] = new OkHttpManager.Params(VERSION_ID, "1");
        paramArray[3] = new OkHttpManager.Params(VERSION_MINI, "20");

        try {
            OkHttpManager.onPostAsynRequest(HttpNewServicePath.getVersionInfoPath,
                    new OkHttpManager.ResultCallback<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (TextUtils.isEmpty(response)) {
                                DeBugLog.e(TAG, "无返回信息！");
                            } else try {
                                JSONObject object = new JSONObject(response);
                                String status = object.getString(STATUS);
                                if (status.equals("1")) {
                                    JSONObject data = object.getJSONObject(DATA);
                                    String versionId = data.getString("vId");
                                    DeBugLog.i(TAG, "versionId:" + versionId);
                                    SuiuuInfo.WriteVersionId(context, versionId);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(Request request, Exception e) {
                            DeBugLog.e(TAG, "版本信息请求失败:" + e.getMessage());
                        }
                    }, paramArray);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 控件相关事件
     */
    private void viewAction() {

        switchSuiuu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switchSuiuu.setText(SuiuuAccount);
                    sideListView.setVisibility(View.VISIBLE);
                    sideListView2.setVisibility(View.GONE);
                } else {
                    switchSuiuu.setText(OrdinaryAccount);
                    sideListView.setVisibility(View.GONE);
                    sideListView2.setVisibility(View.VISIBLE);
                }
            }
        });

        Main_1_Album.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SelectPictureActivity.class);
                intent.putExtra(STATE, 1);
                startActivityForResult(intent, AppConstant.PUBLISTH_TRIP_GALLERY_SUCCESS);
            }
        });

        Main_2_Search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Main_3_Search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommunitySearchActivity.class);
                startActivityForResult(intent, AppConstant.COMMUNITY_SEARCH_SKIP);
            }
        });

        Main_3_Questions.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PutQuestionsActivity.class);
                intent.putExtra(COUNTRY_ID, communityFragment.getCountryId());
                intent.putExtra(CITY_ID, communityFragment.getCityId());
                startActivity(intent);
            }
        });

        Main_4_Search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        MyOnClickListener onClickListener = new MyOnClickListener();

        drawerSwitch.setOnClickListener(onClickListener);
        nickNameView.setOnClickListener(onClickListener);
        headImageView.setOnClickListener(onClickListener);

        final Class<?>[] classArray1 = new Class[]{PrivateLetterActivity.class, MySuiuuInfoActivity.class, OrderManageActivity.class,
                AccountManagerActivity.class, SettingActivity.class};

        sideListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDrawerLayout.closeDrawer(sliderView);
                startActivity(new Intent(context, classArray1[position]));
            }
        });

        final Class<?>[] classArray2 = new Class[]{GeneralOrderListActivity.class, AttentionActivity.class, PrivateLetterActivity.class,
                SettingActivity.class};

        sideListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDrawerLayout.closeDrawer(sliderView);
                if (position == 4) {
                    finish();
                } else {
                    Intent intent = new Intent(context, classArray2[position]);
                    if (position == 1) {
                        intent.putExtra(USER_SIGN, userSign);
                    }
                    startActivity(intent);
                }
            }
        });

        tab1.setOnClickListener(onClickListener);
        tab2.setOnClickListener(onClickListener);
        tab3.setOnClickListener(onClickListener);
        tab4.setOnClickListener(onClickListener);

        //广播监听
        connectionNetChangeReceiver.setConnectionChangeListener(new ConnectionNetChangeReceiver.ConnectionChangeListener() {
            @Override
            public void connectionBreakOff(Context b) {
                rl_net_error.setVisibility(View.VISIBLE);
            }

            @Override
            public void connectionResume(Context b) {
                if (rl_net_error.isEnabled()) {
                    rl_net_error.setVisibility(View.GONE);
                }
            }

        });

        //跳到设置界面
        rl_net_error.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_SETTINGS));
            }
        });
    }

    /**
     * 加载主页页面
     */
    private void LoadMainFragment() {
        FragmentTransaction ft = fm.beginTransaction();
        if (suiuuFragment != null) {
            if (suiuuFragment.isAdded()) {
                ft.hide(suiuuFragment);
            }
        }

        if (communityFragment != null) {
            if (communityFragment.isAdded()) {
                ft.hide(communityFragment);
            }
        }

        if (informationFragment != null) {
            if (informationFragment.isAdded()) {
                ft.hide(informationFragment);
            }
        }

        if (tripGalleryFragment == null) {
            tripGalleryFragment = new TripGalleryFragment();
        }

        if (tripGalleryFragment.isAdded()) {
            ft.show(tripGalleryFragment);
        } else {
            ft.add(R.id.show_layout, tripGalleryFragment);
        }

        ft.commit();
    }

    /**
     * 加载圈子页面
     */
    private void LoadSuiuuFragment() {
        FragmentTransaction ft = fm.beginTransaction();
        if (tripGalleryFragment != null) {
            if (tripGalleryFragment.isAdded()) {
                ft.hide(tripGalleryFragment);
            }
        }

        if (communityFragment != null) {
            if (communityFragment.isAdded()) {
                ft.hide(communityFragment);
            }
        }

        if (informationFragment != null) {
            if (informationFragment.isAdded()) {
                ft.hide(informationFragment);
            }
        }

        if (suiuuFragment == null) {
            suiuuFragment = new SuiuuFragment();
        }

        if (suiuuFragment.isAdded()) {
            ft.show(suiuuFragment);
        } else {
            ft.add(R.id.show_layout, suiuuFragment);
        }
        ft.commit();
    }

    /**
     * 加载问答社区页面
     */
    private void LoadCommunityFragment() {
        FragmentTransaction ft = fm.beginTransaction();
        if (tripGalleryFragment != null) {
            if (tripGalleryFragment.isAdded()) {
                ft.hide(tripGalleryFragment);
            }
        }

        if (suiuuFragment != null) {
            if (suiuuFragment.isAdded()) {
                ft.hide(suiuuFragment);
            }
        }

        if (informationFragment != null) {
            if (informationFragment.isAdded()) {
                ft.hide(informationFragment);
            }
        }

        if (communityFragment == null) {
            communityFragment = CommunityFragment.newInstance(userSign, verification);
        }

        if (communityFragment.isAdded()) {
            ft.show(communityFragment);
        } else {
            ft.add(R.id.show_layout, communityFragment);
        }

        ft.commit();
    }

    /**
     * 加载会话页面
     */
    private void LoadConversationFragment() {
        FragmentTransaction ft = fm.beginTransaction();
        if (tripGalleryFragment != null) {
            if (tripGalleryFragment.isAdded()) {
                ft.hide(tripGalleryFragment);
            }
        }
        if (suiuuFragment != null) {
            if (suiuuFragment.isAdded()) {
                ft.hide(suiuuFragment);
            }
        }
        if (communityFragment != null) {
            if (communityFragment.isAdded()) {
                ft.hide(communityFragment);
            }
        }

        if (informationFragment.isAdded()) {
            ft.show(informationFragment);
        } else {
            ft.add(R.id.show_layout, informationFragment);
        }

        ft.commit();
    }

    /**
     * 加载初始默认页面
     */
    private void LoadDefaultFragment() {
        FragmentTransaction ft = fm.beginTransaction();
        if (tripGalleryFragment == null) {
            tripGalleryFragment = new TripGalleryFragment();
            ft.add(R.id.show_layout, tripGalleryFragment);
        } else {
            ft.add(R.id.show_layout, tripGalleryFragment);
        }

        ft.commit();
    }

    /**
     * 切换页面钻咋状态
     *
     * @param number 页码
     */
    private void switchViewState(int number) {

        switch (number) {
            case NUMBER1:
                iv_tab1.setImageResource(R.drawable.icon_main_1_green);
                iv_tab2.setImageResource(R.drawable.icon_main_2_white);
                iv_tab3.setImageResource(R.drawable.icon_main_3_white);
                iv_tab4.setImageResource(R.drawable.icon_main_4_white);

                TravelImageLayout.setVisibility(View.VISIBLE);
                SuiuuButtonLayout.setVisibility(View.GONE);
                CommunityLayout.setVisibility(View.GONE);
                InboxBtnLayout.setVisibility(View.GONE);
                break;

            case NUMBER2:
                iv_tab1.setImageResource(R.drawable.icon_main_1_white);
                iv_tab2.setImageResource(R.drawable.icon_main_2_green);
                iv_tab3.setImageResource(R.drawable.icon_main_3_white);
                iv_tab4.setImageResource(R.drawable.icon_main_4_white);

                TravelImageLayout.setVisibility(View.GONE);
                SuiuuButtonLayout.setVisibility(View.VISIBLE);
                CommunityLayout.setVisibility(View.GONE);
                InboxBtnLayout.setVisibility(View.GONE);
                break;

            case NUMBER3:
                iv_tab1.setImageResource(R.drawable.icon_main_1_white);
                iv_tab2.setImageResource(R.drawable.icon_main_2_white);
                iv_tab3.setImageResource(R.drawable.icon_main_3_green);
                iv_tab4.setImageResource(R.drawable.icon_main_4_white);

                TravelImageLayout.setVisibility(View.GONE);
                SuiuuButtonLayout.setVisibility(View.GONE);
                CommunityLayout.setVisibility(View.VISIBLE);
                InboxBtnLayout.setVisibility(View.GONE);
                break;
            case NUMBER4:
                iv_tab1.setImageResource(R.drawable.icon_main_1_white);
                iv_tab2.setImageResource(R.drawable.icon_main_2_white);
                iv_tab3.setImageResource(R.drawable.icon_main_3_white);
                iv_tab4.setImageResource(R.drawable.icon_main_4_green);

                TravelImageLayout.setVisibility(View.GONE);
                SuiuuButtonLayout.setVisibility(View.GONE);
                CommunityLayout.setVisibility(View.GONE);
                InboxBtnLayout.setVisibility(View.VISIBLE);
                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            DeBugLog.e(TAG, "return information is null");
        } else if (data == null) {
            DeBugLog.e(TAG, "back data is null!");
        } else {
            switch (requestCode) {
                case AppConstant.COMMUNITY_SEARCH_SKIP:
                    String searchString = data.getStringExtra("Search");
                    DeBugLog.i(TAG, "Search Str:" + searchString);
                    communityFragment.setSearchString(searchString);
                    break;
            }
        }
        if (requestCode == AppConstant.PUBLISTH_TRIP_GALLERY_SUCCESS) {
            tripGalleryFragment.onReflash();
        }
    }

    private class ExitReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            DeBugLog.i(TAG, "action:" + action);
            if (action.equals(SettingActivity.class.getSimpleName())) {
                MainActivity.this.finish();
            }
        }
    }

    private class TokenBroadcastReceiver extends BroadcastReceiver {

        private int count = 0;

        @Override
        public void onReceive(Context context, Intent intent) {
            count++;
            DeBugLog.i(TAG, "count:" + count);
            if (count == 120) {
                getServiceTime();
                count = 0;
            }
        }

    }

    private class MyOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.drawer_switch:
                    if (mDrawerLayout.isDrawerVisible(sliderView)) {
                        mDrawerLayout.closeDrawer(sliderView);
                    } else {
                        mDrawerLayout.openDrawer(sliderView);
                    }
                    break;

                case R.id.head_image:
                    Intent headIntent = new Intent(context, PersonalMainPagerActivity.class);
                    headIntent.putExtra(USER_SIGN, userSign);
                    startActivity(headIntent);
                    mDrawerLayout.closeDrawer(sliderView);
                    break;

                case R.id.nick_name:
                    Intent nickIntent = new Intent(context, PersonalMainPagerActivity.class);
                    nickIntent.putExtra(USER_SIGN, userSign);
                    startActivity(nickIntent);
                    mDrawerLayout.closeDrawer(sliderView);
                    break;

                case R.id.tab1:
                    titleInfo.setText(getResources().getString(R.string.MainTitle1));
                    switchViewState(NUMBER1);
                    LoadMainFragment();
                    break;

                case R.id.tab2:
                    titleInfo.setText(getResources().getString(R.string.MainTitle2));
                    switchViewState(NUMBER2);
                    LoadSuiuuFragment();
                    break;

                case R.id.tab3:
                    titleInfo.setText(getResources().getString(R.string.MainTitle3));
                    switchViewState(NUMBER3);
                    LoadCommunityFragment();
                    break;

                case R.id.tab4:
                    titleInfo.setText(getResources().getString(R.string.MainTitle4));
                    switchViewState(NUMBER4);
                    LoadConversationFragment();
                    break;
            }
        }

    }

    private long exitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (mDrawerLayout.isDrawerVisible(sliderView)) {
                mDrawerLayout.closeDrawer(sliderView);
            } else {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            unregisterReceiver(exitReceiver);
        } catch (Exception e) {
            DeBugLog.e(TAG, "反注册ExitBroadcastReceiver失败:" + e.getMessage());
        }

        try {
            unregisterReceiver(connectionNetChangeReceiver);
        } catch (Exception e) {
            DeBugLog.e(TAG, "反注册ConnectionNetChangeReceiver失败:" + e.getMessage());
        }

        try {
            unregisterReceiver(tokenBroadcastReceiver);
        } catch (Exception e) {
            DeBugLog.e(TAG, "反注册TokenBroadcastReceiver失败:" + e.getMessage());
        }

    }

}