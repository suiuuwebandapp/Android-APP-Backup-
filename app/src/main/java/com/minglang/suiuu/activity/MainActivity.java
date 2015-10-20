package com.minglang.suiuu.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.koushikdutta.WebSocketClient;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.MainSliderAdapter;
import com.minglang.suiuu.application.SuiuuApplication;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.fragment.main.InformationFragment;
import com.minglang.suiuu.fragment.main.ProblemFragment;
import com.minglang.suiuu.fragment.main.SuiuuFragment;
import com.minglang.suiuu.fragment.main.TripImageFragment;
import com.minglang.suiuu.receiver.ConnectionNetChangeReceiver;
import com.minglang.suiuu.utils.AppConstant;
import com.minglang.suiuu.utils.AppUtils;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.MD5Utils;
import com.minglang.suiuu.utils.StatusBarCompat;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.squareup.okhttp.Request;
import com.umeng.update.UmengUpdateAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 应用程序主界面
 */
public class MainActivity extends BaseAppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int NUMBER1 = 1;
    private static final int NUMBER2 = 2;
    private static final int NUMBER3 = 3;
    private static final int NUMBER4 = 4;

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

    private static final String TYPE = "type";
    private static final String USER_KEY = "user_key";
    private static final String LOGIN = "login";

    @BindString(R.string.SuiuuUser)
    String SuiuuUser;

    @BindString(R.string.GeneralUser)
    String GeneralUser;

    @BindString(R.string.LoginOverdue)
    String LoginOverdue;

    @BindString(R.string.SystemException)
    String SystemException;

    @BindString(R.string.LoadingVerificationInfo)
    String LoadingVerificationInfo;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.main_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.slider_layout)
    RelativeLayout sliderView;

    @Bind(R.id.nick_name)
    TextView nickNameView;

    @Bind(R.id.head_image)
    SimpleDraweeView headImageView;

    @Bind(R.id.main_top_view)
    View leftTopView;

    @Bind(R.id.side_list_view)
    ListView sideListView;

    @Bind(R.id.side_list_view_2)
    ListView sideListView2;

    @Bind(R.id.switch_view)
    Button switchUser;

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

    @Bind(R.id.img1)
    ImageView imageTabOne;

    @Bind(R.id.img2)
    ImageView imageTabTwo;

    @Bind(R.id.img3)
    ImageView imageTabThree;

    @Bind(R.id.img4)
    ImageView imageTabFour;

    @Bind(R.id.network_error_view)
    TextView networkErrorView;

    /**
     * 旅图页面
     */
    private TripImageFragment tripImageFragment;

    /**
     * 随游页面
     */
    private SuiuuFragment suiuuFragment;

    /**
     * 问答社区页面
     */
    private ProblemFragment problemFragment;

    /**
     * 消息提醒页面
     */
    private InformationFragment informationFragment;

    private ExitReceiver exitReceiver;

    private ConnectionNetChangeReceiver connectionNetChangeReceiver;

    private TokenBroadcastReceiver tokenBroadcastReceiver;

    private Context context;

    private WebSocketClient webSocketClient;

    private boolean isConnected;

    private LocalBroadcastManager localBroadcastManager;

    //    private LocationService.LocationBinder locationBinder;
    //
    //    private ServiceConnection serviceConnection = new ServiceConnection() {
    //
    //        @Override
    //        public void onServiceConnected(ComponentName name, IBinder service) {
    //            locationBinder = (LocationService.LocationBinder) service;
    //        }
    //
    //        @Override
    //        public void onServiceDisconnected(ComponentName name) {
    //            L.i(TAG, "ComponentName:" + name.toString());
    //        }
    //
    //    };

    private boolean isPublisher = false;

    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StatusBarCompat.compat(this);
        UmengUpdateAgent.update(this);
        ButterKnife.bind(this);

        context = MainActivity.this;
        verification = SuiuuInfo.ReadVerification(context);

        if (AppUtils.isNetworkConnect(context)) {
            webSocketClient = SuiuuApplication.getWebSocketClient();
            isConnected = webSocketClient.isConnected();
            L.i(TAG, "isConnected:" + isConnected);

            String loginMessage = buildLoginMessage();
            L.i(TAG, "loginMessage:" + loginMessage);
            webSocketClient.send(loginMessage);

            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(LoadingVerificationInfo);
            progressDialog.setCanceledOnTouchOutside(false);

            getServiceTime();
            versionCheck();

            //暂停使用定位服务
            //bindService(new Intent(context, LocationService.class), serviceConnection, Context.BIND_AUTO_CREATE);
        } else {
            new AlertDialog.Builder(context)
                    .setMessage("无可用网络，请检查网络设置！")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Settings.ACTION_SETTINGS));
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .create().show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        String networkHeadImagePath = SuiuuInfo.ReadUserData(this).getHeadImg();

        if (!TextUtils.isEmpty(networkHeadImagePath)) {
            headImageView.setImageURI(Uri.parse(networkHeadImagePath));
        } else {
            headImageView.setImageURI(Uri.parse("res://com.minglang.suiuu/" + R.drawable.default_head_image_error));
        }

        String user_name = SuiuuInfo.ReadUserData(this).getNickname();
        if (!TextUtils.isEmpty(user_name)) {
            nickNameView.setText(user_name);
        }

        if (webSocketClient != null)
            if (!isConnected) {
                webSocketClient.send(buildLoginMessage());
            }

    }

    /**
     * 得到服务器时间
     */
    private void getServiceTime() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }

        try {
            OkHttpManager.onGetAsynRequest(HttpNewServicePath.getTime, new OkHttpManager.ResultCallback<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                        String status = object.getString(STATUS);
                        switch (status) {
                            case "1":
                                String data = object.getString(DATA);
                                getAppTimeSign(data);
                                break;
                            case "-1":
                                L.e(TAG, SystemException);
                                break;
                            case "-2":
                                L.e(TAG, object.getString(DATA));
                                break;
                            default:
                                L.e(TAG, "Data Error");
                                break;
                        }
                    } catch (JSONException e) {
                        L.e(TAG, "时间数据解析异常:" + e.getMessage());
                    }
                }

                @Override
                public void onError(Request request, Exception e) {
                    L.e(TAG, "时间获取错误:" + e.getMessage());
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }

                    showErrorDialog("时间获取失败，请尝试重新获取！");
                }

                @Override
                public void onFinish() {
                    L.i(TAG, "Service Time Request Finish!");
                }

            });
        } catch (IOException e) {
            L.e(TAG, "获取服务器时间请求错误:" + e.getMessage());
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            showErrorDialog("时间获取失败，请尝试重新获取！");
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
                                L.i(TAG, "appTimeSign:" + appTimeSign);
                                SuiuuInfo.WriteAppTimeSign(context, appTimeSign);
                                break;
                            case "-3":
                                SuiuuInfo.ClearSuiuuInfo(context);
                                SuiuuInfo.ClearWeChatInfo(context);
                                SuiuuInfo.ClearAliPayInfo(context);
                                Toast.makeText(context, LoginOverdue, Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                L.e(TAG, "获取失败");
                                break;
                        }
                    } catch (JSONException e) {
                        L.e(TAG, "JSONException:" + e.getMessage());
                    }
                }

                @Override
                public void onError(Request request, Exception e) {
                    L.e(TAG, "Network Exception:" + e.getMessage());
                    showErrorDialog("身份信息获取失败，请尝试重新获取！");
                }

                @Override
                public void onFinish() {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }

                    initView();
                    viewAction();
                }

            });
        } catch (Exception e) {
            L.e(TAG, "获取Token请求错误:" + e.getMessage());
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    private void showErrorDialog(String message) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getServiceTime();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .create().show();
    }

    /**
     * 初始化方法
     */
    private void initView() {
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        fm = getSupportFragmentManager();

        int statusBarHeight = AppUtils.getStatusBarHeight(this);
        int leftViewWidth = screenWidth / 4 * 3;

        ViewGroup.LayoutParams viewParams = leftTopView.getLayoutParams();
        viewParams.width = leftViewWidth;
        viewParams.height = statusBarHeight;
        leftTopView.setLayoutParams(viewParams);

        ViewGroup.LayoutParams sliderNavigationViewParams = sliderView.getLayoutParams();
        sliderNavigationViewParams.width = leftViewWidth;
        sliderNavigationViewParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
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
            sideListView.setVisibility(View.GONE);
            sideListView2.setVisibility(View.VISIBLE);

            isPublisher = false;

            switchUser.setText(SuiuuUser);
            switchUser.setEnabled(false);
        } else {
            if (publisher.equals("1")) {
                sideListView.setVisibility(View.VISIBLE);
                sideListView2.setVisibility(View.GONE);

                isPublisher = true;

                switchUser.setText(GeneralUser);
                switchUser.setEnabled(true);
            } else {
                sideListView.setVisibility(View.GONE);
                sideListView2.setVisibility(View.VISIBLE);

                isPublisher = false;

                switchUser.setText(SuiuuUser);
                switchUser.setEnabled(false);
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

        initReceiver();
        initFragment();
    }

    private void initFragment() {
        userSign = SuiuuInfo.ReadUserSign(this);
        token = SuiuuInfo.ReadAppTimeSign(this);

        switchViewState(NUMBER1);

        tripImageFragment = new TripImageFragment();
        informationFragment = InformationFragment.newInstance(userSign, verification, token);
        problemFragment = ProblemFragment.newInstance(userSign, verification);

        LoadDefaultFragment();
    }

    private void initReceiver() {
        localBroadcastManager = LocalBroadcastManager.getInstance(context);

        exitReceiver = new ExitReceiver();
        localBroadcastManager.registerReceiver(exitReceiver, new IntentFilter(SettingActivity.class.getSimpleName()));

        tokenBroadcastReceiver = new TokenBroadcastReceiver();
        registerReceiver(tokenBroadcastReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));

        connectionNetChangeReceiver = new ConnectionNetChangeReceiver();
        registerReceiver(connectionNetChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private void versionCheck() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String serialNumber = tm.getDeviceId();
        L.i(TAG, "手机串号:" + serialNumber);

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
                                L.e(TAG, "无返回信息！");
                            } else try {
                                JSONObject object = new JSONObject(response);
                                String status = object.getString(STATUS);
                                if (status.equals("1")) {
                                    JSONObject data = object.getJSONObject(DATA);
                                    String versionId = data.getString("vId");
                                    L.i(TAG, "versionId:" + versionId);
                                    SuiuuInfo.WriteVersionId(context, versionId);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Request request, Exception e) {
                            L.e(TAG, "版本信息请求失败:" + e.getMessage());
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

        switchUser.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPublisher) {
                    isPublisher = false;
                    switchUser.setText(SuiuuUser);

                    sideListView.setVisibility(View.GONE);
                    sideListView2.setVisibility(View.VISIBLE);
                } else {
                    isPublisher = true;
                    switchUser.setText(GeneralUser);

                    sideListView.setVisibility(View.VISIBLE);
                    sideListView2.setVisibility(View.GONE);
                }
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
                networkErrorView.setVisibility(View.VISIBLE);
            }

            @Override
            public void connectionResume(Context b) {
                if (networkErrorView.isEnabled()) {
                    networkErrorView.setVisibility(View.GONE);
                }
            }

        });

        //跳到设置界面
        networkErrorView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_SETTINGS));
            }
        });
    }

    private String buildLoginMessage() {
        JSONObject object = new JSONObject();
        try {
            object.put(USER_KEY, verification);
            object.put(TYPE, LOGIN);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
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

        if (problemFragment != null) {
            if (problemFragment.isAdded()) {
                ft.hide(problemFragment);
            }
        }

        if (informationFragment != null) {
            if (informationFragment.isAdded()) {
                ft.hide(informationFragment);
            }
        }

        if (tripImageFragment == null) {
            tripImageFragment = new TripImageFragment();
        }

        if (tripImageFragment.isAdded()) {
            ft.show(tripImageFragment);
        } else {
            ft.add(R.id.show_layout, tripImageFragment);
        }

        ft.commit();
    }

    /**
     * 加载圈子页面
     */
    private void LoadSuiuuFragment() {
        FragmentTransaction ft = fm.beginTransaction();
        if (tripImageFragment != null) {
            if (tripImageFragment.isAdded()) {
                ft.hide(tripImageFragment);
            }
        }

        if (problemFragment != null) {
            if (problemFragment.isAdded()) {
                ft.hide(problemFragment);
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
        if (tripImageFragment != null) {
            if (tripImageFragment.isAdded()) {
                ft.hide(tripImageFragment);
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

        if (problemFragment == null) {
            problemFragment = ProblemFragment.newInstance(userSign, verification);
        }

        if (problemFragment.isAdded()) {
            ft.show(problemFragment);
        } else {
            ft.add(R.id.show_layout, problemFragment);
        }

        ft.commit();
    }

    /**
     * 加载会话页面
     */
    private void LoadConversationFragment() {
        FragmentTransaction ft = fm.beginTransaction();
        if (tripImageFragment != null) {
            if (tripImageFragment.isAdded()) {
                ft.hide(tripImageFragment);
            }
        }
        if (suiuuFragment != null) {
            if (suiuuFragment.isAdded()) {
                ft.hide(suiuuFragment);
            }
        }
        if (problemFragment != null) {
            if (problemFragment.isAdded()) {
                ft.hide(problemFragment);
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
        if (tripImageFragment == null) {
            tripImageFragment = new TripImageFragment();
            ft.add(R.id.show_layout, tripImageFragment);
        } else {
            ft.add(R.id.show_layout, tripImageFragment);
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
                imageTabOne.setImageResource(R.drawable.icon_main_1_green);
                imageTabTwo.setImageResource(R.drawable.icon_main_2_white);
                imageTabThree.setImageResource(R.drawable.icon_main_3_white);
                imageTabFour.setImageResource(R.drawable.icon_main_4_white);
                break;

            case NUMBER2:
                imageTabOne.setImageResource(R.drawable.icon_main_1_white);
                imageTabTwo.setImageResource(R.drawable.icon_main_2_green);
                imageTabThree.setImageResource(R.drawable.icon_main_3_white);
                imageTabFour.setImageResource(R.drawable.icon_main_4_white);
                break;

            case NUMBER3:
                imageTabOne.setImageResource(R.drawable.icon_main_1_white);
                imageTabTwo.setImageResource(R.drawable.icon_main_2_white);
                imageTabThree.setImageResource(R.drawable.icon_main_3_green);
                imageTabFour.setImageResource(R.drawable.icon_main_4_white);
                break;

            case NUMBER4:
                imageTabOne.setImageResource(R.drawable.icon_main_1_white);
                imageTabTwo.setImageResource(R.drawable.icon_main_2_white);
                imageTabThree.setImageResource(R.drawable.icon_main_3_white);
                imageTabFour.setImageResource(R.drawable.icon_main_4_green);
                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            L.e(TAG, "return information is null");
        } else if (data == null) {
            L.e(TAG, "back data is null!");
        } else {
            switch (requestCode) {
                case AppConstant.COMMUNITY_SEARCH_SKIP:
                    String searchString = data.getStringExtra("Search");
                    L.i(TAG, "Search Str:" + searchString);
                    problemFragment.setSearchString(searchString);
                    break;
            }
        }

        if (requestCode == AppConstant.PUBLISTH_TRIP_GALLERY_SUCCESS) {
            tripImageFragment.loadFirstPageData(null);
        }
    }

    private class ExitReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            L.i(TAG, "action:" + action);
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
            L.i(TAG, "count:" + count);
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
                    Toast.makeText(context, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    if (isConnected) {
                        webSocketClient.disconnect();
                    }

                    //定位服务相关
                    //unbindService(serviceConnection);
                    //locationBinder.endService();

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
            localBroadcastManager.unregisterReceiver(exitReceiver);
        } catch (Exception e) {
            L.e(TAG, "反注册ExitBroadcastReceiver失败:" + e.getMessage());
        }

        try {
            unregisterReceiver(connectionNetChangeReceiver);
        } catch (Exception e) {
            L.e(TAG, "反注册ConnectionNetChangeReceiver失败:" + e.getMessage());
        }

        try {
            unregisterReceiver(tokenBroadcastReceiver);
        } catch (Exception e) {
            L.e(TAG, "反注册TokenBroadcastReceiver失败:" + e.getMessage());
        }

    }

}