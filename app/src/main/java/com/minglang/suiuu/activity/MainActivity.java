package com.minglang.suiuu.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
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

import com.easemob.EMConnectionListener;
import com.easemob.EMError;
import com.easemob.chat.CmdMessageBody;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.ChatType;
import com.easemob.chat.EMMessage.Type;
import com.easemob.util.EMLog;
import com.easemob.util.EasyUtils;
import com.easemob.util.NetUtils;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.MainSliderAdapter;
import com.minglang.suiuu.application.SuiuuApplication;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.chat.activity.ChatActivity;
import com.minglang.suiuu.chat.chat.Constant;
import com.minglang.suiuu.chat.utils.CommonUtils;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.fragment.main.CommunityFragment;
import com.minglang.suiuu.fragment.main.InformationFragment;
import com.minglang.suiuu.fragment.main.SuiuuFragment;
import com.minglang.suiuu.fragment.main.TripGalleryFragment;
import com.minglang.suiuu.utils.AppConstant;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 应用程序主界面
 */
public class MainActivity extends BaseActivity {

    protected NotificationManager notificationManager;

    private static final int notificationId = 11;

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int NUMBER1 = 1;
    private static final int NUMBER2 = 2;
    private static final int NUMBER3 = 3;
    private static final int NUMBER4 = 4;

    @BindString(R.string.SuiuuAccount)
    String SuiuuAccount;

    @BindString(R.string.OrdinaryAccount)
    String OrdinaryAccount;

    @Bind(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;

    //侧滑布局
    @Bind(R.id.sliderLayout)
    RelativeLayout sliderView;

    /**
     * 点击修改昵称
     */
    @Bind(R.id.nickName)
    TextView nickNameView;

    /**
     * 点击修改头像
     */
    @Bind(R.id.headImage)
    CircleImageView headImageView;

    @Bind(R.id.switchSuiuu)
    Switch switchSuiuu;

    @Bind(R.id.sideListView)
    ListView sideListView;

    @Bind(R.id.sideListView2)
    ListView sideListView2;

    //标题
    @Bind(R.id.titleInfo)
    TextView titleInfo;

    @Bind(R.id.drawerSwitch)
    CircleImageView drawerSwitch;

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
     * 账号在别处登录
     */
    public boolean isConflict = false;

//    private NewMessageReceiver msgReceiver;

    /**
     * 当前为fragment的第几页
     */
    private int currentIndex = 0;

    private TextView msgCount;

    @Bind(R.id.rl_error_item)
    RelativeLayout errorItem;

    private TextView errorText;

    /**
     * 旅图页面按钮布局
     */
    @Bind(R.id.TravelImageLayout)
    RelativeLayout TravelImageLayout;

    /**
     * 随游页面按钮布局
     */
    @Bind(R.id.SuiuuButtonLayout)
    FrameLayout SuiuuButtonLayout;

    @Bind(R.id.CommunityLayout)
    RelativeLayout CommunityLayout;

    /**
     * 收件箱页面按钮布局
     */
    @Bind(R.id.InboxButtonLayout)
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

    private ExitReceiver exitReceiver;

    private EMChatManager chatManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initReadSavedInstanceState(savedInstanceState);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        setContentView(R.layout.activity_main);

        UmengUpdateAgent.update(this);
        ButterKnife.bind(this);
        initView();
        initRegisterReceiver();
        ViewAction();
    }

    @Override
    protected void onResume() {
        super.onResume();

        String network_headImage_path = SuiuuInfo.ReadUserData(this).getHeadImg();
        if (!TextUtils.isEmpty(network_headImage_path)) {
            imageLoader.displayImage(network_headImage_path, headImageView);
        }

        String user_name = SuiuuInfo.ReadUserData(this).getNickname();
        if (!TextUtils.isEmpty(user_name)) {
            nickNameView.setText(user_name);
        }

    }

    private void initReadSavedInstanceState(Bundle savedInstanceState) {
        boolean saveFlag = savedInstanceState != null;

        if (saveFlag && savedInstanceState.getBoolean(Constant.ACCOUNT_REMOVED, false)) {
            // 防止被移除后，没点确定按钮然后按了home键，长期在后台又进app导致的crash
            // 三个fragment里加的判断同理
            SuiuuApplication.getInstance().logout(null);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            if (saveFlag && savedInstanceState.getBoolean("isConflict", false)) {
                // 防止被T后，没点确定按钮然后按了home键，长期在后台又进app导致的crash
                // 三个fragment里加的判断同理
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        }

    }

    /**
     * 初始化方法
     */
    private void initView() {
        msgCount = (TextView) findViewById(R.id.unread_msg_number);//使用注解报错
        errorText = (TextView) errorItem.findViewById(R.id.tv_connect_errormsg);//使用注解正常，为保险起见暂不使用注解

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
            imageLoader.displayImage(strHeadImagePath, headImageView);
            imageLoader.displayImage(strHeadImagePath, drawerSwitch);
        }

        switchSuiuu.setChecked(true);
        switchSuiuu.setText(SuiuuAccount);

        MainSliderAdapter adapter =
                new MainSliderAdapter(this, getResources().getStringArray(R.array.sideList));
        adapter.setScreenHeight(screenHeight);
        sideListView.setAdapter(adapter);

        MainSliderAdapter adapter2 =
                new MainSliderAdapter(this, getResources().getStringArray(R.array.sideList2));
        adapter2.setScreenHeight(screenHeight);
        sideListView2.setAdapter(adapter2);

        initFragment();
    }

    private void initFragment() {
        userSign = SuiuuInfo.ReadUserSign(this);
        verification = SuiuuInfo.ReadVerification(this);

        switchViewState(NUMBER1);

        tripGalleryFragment = new TripGalleryFragment();
        informationFragment = InformationFragment.newInstance(userSign, verification);
        communityFragment = CommunityFragment.newInstance(userSign, verification);

        LoadDefaultFragment();
    }

    private void initRegisterReceiver() {
        chatManager = EMChatManager.getInstance();

        MobclickAgent.updateOnlineConfig(this);
        if (getIntent().getBooleanExtra("conflict", false) && !isConflictDialogShow) {
            showConflictDialog();
        } else if (getIntent().getBooleanExtra(Constant.ACCOUNT_REMOVED, false) && !isAccountRemovedDialogShow) {
            showAccountRemovedDialog();
        }

        MobclickAgent.updateOnlineConfig(this);

        // 注册一个接收消息的BroadcastReceiver
//        msgReceiver = new NewMessageReceiver();
//        IntentFilter intentFilter = new IntentFilter(chatManager.getNewMessageBroadcastAction());
//        intentFilter.setPriority(3);
//        registerReceiver(msgReceiver, intentFilter);

        // 注册一个ack回执消息的BroadcastReceiver
        IntentFilter ackMessageIntentFilter = new IntentFilter(chatManager.getAckMessageBroadcastAction());
        ackMessageIntentFilter.setPriority(3);
        registerReceiver(ackMessageReceiver, ackMessageIntentFilter);

        //注册一个透传消息的BroadcastReceiver
        IntentFilter cmdMessageIntentFilter = new IntentFilter(chatManager.getCmdMessageBroadcastAction());
        cmdMessageIntentFilter.setPriority(3);
        registerReceiver(cmdMessageReceiver, cmdMessageIntentFilter);


        // 注册一个离线消息的BroadcastReceiver
        // IntentFilter offlineMessageIntentFilter = new
        // IntentFilter(EMChatManager.getInstance()
        // .getOfflineMessageBroadcastAction());
        // registerReceiver(offlineMessageReceiver, offlineMessageIntentFilter);

//        setContactListener监听联系人的变化等
//        EMContactManager.getInstance().setContactListener(new MyContactListener());
        // 注册一个监听连接状态的listener
        chatManager.addConnectionListener(new MyConnectionListener());

        // 注册群聊相关的listener
//		EMGroupManager.getInstance().addGroupChangeListener(new MyGroupChangeListener());
        // 通知sdk，UI 已经初始化完毕，注册了相应的receiver和listener, 可以接受broadcast了
        EMChat.getInstance().setAppInited();

        exitReceiver = new ExitReceiver();
        IntentFilter intentFilter2 = new IntentFilter();
//        intentFilter.addAction(SettingActivity.class.getSimpleName());
        this.registerReceiver(exitReceiver, intentFilter2);
    }

    /**
     * 控件相关事件
     */
    private void ViewAction() {

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
                Intent intent = new Intent(MainActivity.this, SelectPictureActivity.class);
                intent.putExtra("state", 1);
                startActivity(intent);
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
                Intent intent = new Intent(MainActivity.this, CommunitySearchActivity.class);
                startActivityForResult(intent, AppConstant.COMMUNITY_SEARCH_SKIP);
            }
        });

        Main_3_Questions.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuestionsActivity.class);
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

        sideListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDrawerLayout.closeDrawer(sliderView);
                switch (position) {
                    case 0:
                        Intent intent0 = new Intent(MainActivity.this, NewRemindActivity.class);
                        startActivity(intent0);
                        break;

                    case 1:
                        Intent intent1 = new Intent(MainActivity.this, MySuiuuInfoActivity.class);
                        startActivity(intent1);
                        break;

                    case 2:
                        Intent intent2 = new Intent(MainActivity.this, OrderManageActivity.class);
                        startActivity(intent2);
                        break;

                    case 3:
                        Intent intent3 = new Intent(MainActivity.this, AccountManageActivity.class);
                        startActivity(intent3);
                        break;
                }
            }
        });

        sideListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDrawerLayout.closeDrawer(sliderView);
                switch (position) {
                    case 0:
                        Intent intent0 = new Intent(MainActivity.this, GeneralOrderListActivity.class);
                        startActivity(intent0);
                        break;

                    case 1:
                        Intent intent1 = new Intent(MainActivity.this, AttentionActivity.class);
                        startActivity(intent1);
                        break;

                    case 2:
                        Intent intent2 = new Intent(MainActivity.this, NewRemindActivity.class);
                        startActivity(intent2);
                        break;

                    case 3:
                        finish();
                        break;

                }
            }
        });

        tab1.setOnClickListener(onClickListener);
        tab2.setOnClickListener(onClickListener);
        tab3.setOnClickListener(onClickListener);
        tab4.setOnClickListener(onClickListener);

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
            ft.add(R.id.showLayout, tripGalleryFragment);
        }

        currentIndex = 0;
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
            ft.add(R.id.showLayout, suiuuFragment);
        }
        currentIndex = 1;
        ft.commit();
    }

    /**
     * 加载路线页面
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
            ft.add(R.id.showLayout, communityFragment);
        }

        currentIndex = 2;
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
            ft.add(R.id.showLayout, informationFragment);
        }

        currentIndex = 3;
        ft.commit();
    }

    /**
     * 加载初始默认页面
     */
    private void LoadDefaultFragment() {
        FragmentTransaction ft = fm.beginTransaction();
        if (tripGalleryFragment == null) {
            tripGalleryFragment = new TripGalleryFragment();
            ft.add(R.id.showLayout, tripGalleryFragment);
        } else {
            ft.add(R.id.showLayout, tripGalleryFragment);
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

    //账号被移除
    private boolean isCurrentAccountRemoved = false;
    private boolean isAccountRemovedDialogShow;
    private android.app.AlertDialog.Builder accountRemovedBuilder;
    private android.app.AlertDialog.Builder conflictBuilder;
    private boolean isConflictDialogShow;

    /**
     * 检查当前用户是否被删除
     */
    public boolean getCurrentAccountRemoved() {
        return isCurrentAccountRemoved;
    }

    /**
     * 帐号被移除的dialog
     */
    private void showAccountRemovedDialog() {
        isAccountRemovedDialogShow = true;
        SuiuuApplication.getInstance().logout(null);
        String st5 = getResources().getString(R.string.Remove_the_notification);
        if (!MainActivity.this.isFinishing()) {
            // clear up global variables
            try {
                if (accountRemovedBuilder == null)
                    accountRemovedBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                accountRemovedBuilder.setTitle(st5);
                accountRemovedBuilder.setMessage(R.string.em_user_remove);
                accountRemovedBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        accountRemovedBuilder = null;
                        finish();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    }
                });
                accountRemovedBuilder.setCancelable(false);
                accountRemovedBuilder.create().show();
                isCurrentAccountRemoved = true;
            } catch (Exception e) {
                EMLog.e(TAG, "---------color userRemovedBuilder error" + e.getMessage());
            }

        }

    }

    /**
     * 显示帐号在别处登录dialog
     */
    private void showConflictDialog() {
        isConflictDialogShow = true;
        SuiuuApplication.getInstance().logout(null);
        String st = getResources().getString(R.string.Logoff_notification);
        if (!MainActivity.this.isFinishing()) {
            // clear up global variables
            try {
                if (conflictBuilder == null)
                    conflictBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                conflictBuilder.setTitle(st);
                conflictBuilder.setMessage(R.string.connect_conflict);
                conflictBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        conflictBuilder = null;
                        finish();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    }
                });
                conflictBuilder.setCancelable(false);
                conflictBuilder.create().show();
                isConflict = true;
            } catch (Exception e) {
                EMLog.e(TAG, "---------color conflictBuilder error" + e.getMessage());
            }

        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (getIntent().getBooleanExtra("conflict", false) && !isConflictDialogShow) {
            showConflictDialog();
        } else if (getIntent().getBooleanExtra(Constant.ACCOUNT_REMOVED, false) && !isAccountRemovedDialogShow) {
            showAccountRemovedDialog();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        DeBugLog.i(TAG, "requestCode:" + requestCode + ",resultCode:" + resultCode);
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
    }

    /**
     * 新消息广播接收者
     */
//    private class NewMessageReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            // 主页面收到消息后，主要为了提示未读，实际消息内容需要到chat页面查看
//
//            String from = intent.getStringExtra("from");
//            // 消息id
//            String msgId = intent.getStringExtra("msgid");
//            EMMessage message = chatManager.getMessage(msgId);
//
//            // fix: logout crash， 如果正在接收大量消息
//            // 因为此时已经logout，消息队列已经被清空， broadcast延时收到，所以会出现message为空的情况
//            if (message == null) {
//                return;
//            }
//
//            // 2014-10-22 修复在某些机器上，在聊天页面对方发消息过来时不立即显示内容的bug
//            if (ChatActivity.activityInstance != null) {
//                if (message.getChatType() == ChatType.GroupChat) {
//                    if (message.getTo().equals(ChatActivity.activityInstance.getToChatUsername()))
//                        return;
//                } else {
//                    if (from.equals(ChatActivity.activityInstance.getToChatUsername()))
//                        return;
//                }
//            }
//
//            // 注销广播接收者，否则在ChatActivity中会收到这个广播
//            abortBroadcast();
//
//            notifyNewMessage(message);
//
//            // 刷新bottom bar消息未读数
//            updateUnreadLabel();
//            if (currentIndex == 3) {
//                // 当前页面如果为聊天历史页面，刷新此页面
//                if (chatFragment != null) {
//                    chatFragment.refresh();
//                    msgCount.setVisibility(View.INVISIBLE);
//                }
//            }
//
//        }
//    }

    /**
     * 消息回执BroadcastReceiver
     */
    private BroadcastReceiver ackMessageReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            abortBroadcast();

            String msgId = intent.getStringExtra("msgid");
            String from = intent.getStringExtra("from");

            EMConversation conversation = chatManager.getConversation(from);
            if (conversation != null) {
                EMMessage msg = conversation.getMessage(msgId);// 把message设为已读
                if (msg != null) {
                    if (ChatActivity.activityInstance != null) {
                        if (msg.getChatType() == ChatType.Chat) {
                            if (from.equals(ChatActivity.activityInstance.getToChatUsername()))
                                return;
                        }
                    }
                    msg.isAcked = true;
                }
            }
        }
    };

    /**
     * 透传消息BroadcastReceiver
     */
    private BroadcastReceiver cmdMessageReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            abortBroadcast();
            String msgId = intent.getStringExtra("msgid"); //获取cmd message对象
            DeBugLog.i(TAG, "msgId:" + msgId);
            EMMessage message = intent.getParcelableExtra("message");
            CmdMessageBody cmdMsgBody = (CmdMessageBody) message.getBody(); //获取消息body
            String action = cmdMsgBody.action;//获取自定义action
            DeBugLog.d(TAG, String.format("透传消息：action:%s,message:%s", action, message.toString()));
            String st9 = getResources().getString(R.string.receive_the_passthrough);
            Toast.makeText(MainActivity.this, st9 + action, Toast.LENGTH_SHORT).show();
        }
    };

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

    /**
     * 当应用在前台时，如果当前消息不是属于当前会话，在状态栏提示一下
     * 如果不需要，注释掉即可
     *
     * @param message 消息
     */
    protected void notifyNewMessage(EMMessage message) {
        //如果是设置了不提醒只显示数目的群组(这个是app里保存这个数据的，demo里不做判断)
        //以及设置了setShowNotificationInBackGroup:false(设为false后，后台时sdk也发送广播)
        if (!EasyUtils.isAppRunningForeground(this)) {
            return;
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(getApplicationInfo().icon)
                .setWhen(System.currentTimeMillis()).setAutoCancel(true);
        String ticker = CommonUtils.getMessageDigest(message, this);
        String st = getResources().getString(R.string.expression);
        if (message.getType() == Type.TXT)
            ticker = ticker.replaceAll("\\[.{2,3}\\]", st);
        //设置状态栏提示
        mBuilder.setTicker(message.getFrom() + ": " + ticker);

        //必须设置PendingIntent，否则在2.3的机器上会有bug
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, notificationId, intent, PendingIntent.FLAG_ONE_SHOT);
        mBuilder.setContentIntent(pendingIntent);
        Notification notification = mBuilder.build();
        notificationManager.notify(notificationId, notification);
        notificationManager.cancel(notificationId);
    }

    /**
     * 连接监听listener
     */
    private class MyConnectionListener implements EMConnectionListener {

        @Override
        public void onConnected() {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    errorItem.setVisibility(View.GONE);
                }

            });
        }

        @Override
        public void onDisconnected(final int error) {
            final String st1 = getResources().getString(R.string.Less_than_chat_server_connection);
            final String st2 = getResources().getString(R.string.the_current_network);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                        showAccountRemovedDialog();
                    } else if (error == EMError.CONNECTION_CONFLICT) {
                        // 显示帐号在其他设备登陆dialog
                        showConflictDialog();
                    } else {
                        errorItem.setVisibility(View.VISIBLE);
                        if (NetUtils.hasNetwork(MainActivity.this))
                            errorText.setText(st1);
                        else
                            errorText.setText(st2);

                    }
                }

            });
        }

    }

    private class MyOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.drawerSwitch:
                    if (mDrawerLayout.isDrawerVisible(sliderView)) {
                        mDrawerLayout.closeDrawer(sliderView);
                    } else {
                        mDrawerLayout.openDrawer(sliderView);
                    }
                    break;

                case R.id.headImage:
                    Intent headIntent = new Intent(MainActivity.this, PersonalCenterActivity.class);
                    startActivity(headIntent);
                    mDrawerLayout.closeDrawer(sliderView);
                    break;

                case R.id.nickName:
                    Intent nickIntent = new Intent(MainActivity.this, PersonalCenterActivity.class);
                    startActivity(nickIntent);
                    mDrawerLayout.closeDrawer(sliderView);
                    break;

                case R.id.tab1:
                    titleInfo.setText(getResources().getString(R.string.mainTitle1));
                    switchViewState(NUMBER1);
                    LoadMainFragment();
                    break;

                case R.id.tab2:
                    titleInfo.setText(getResources().getString(R.string.mainTitle2));
                    switchViewState(NUMBER2);
                    LoadSuiuuFragment();
                    break;

                case R.id.tab3:
                    titleInfo.setText(getResources().getString(R.string.mainTitle3));
                    switchViewState(NUMBER3);
                    LoadCommunityFragment();
                    break;

                case R.id.tab4:
                    titleInfo.setText(getResources().getString(R.string.mainTitle4));
                    switchViewState(NUMBER4);
                    LoadConversationFragment();
                    break;
            }
        }

    }

    /**
     * 刷新未读消息数
     */
    public void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();
        if (count > 0) {
            msgCount.setText(String.valueOf(count));
            msgCount.setVisibility(View.VISIBLE);
        } else {
            msgCount.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 获取未读消息数
     *
     * @return 未读消息数量
     */
    public int getUnreadMsgCountTotal() {
        int unreadMsgCountTotal;
        unreadMsgCountTotal = chatManager.getUnreadMsgsCount();
        return unreadMsgCountTotal;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 注销广播接收者
        try {
            unregisterReceiver(ackMessageReceiver);
        } catch (Exception ignored) {
            DeBugLog.e(TAG, "ackMessageReceiver:" + ignored.getMessage());
        }

        try {
            unregisterReceiver(cmdMessageReceiver);
        } catch (Exception ignored) {
            DeBugLog.e(TAG, "cmdMessageReceiver:" + ignored.getMessage());
        }

        try {
            unregisterReceiver(exitReceiver);
        } catch (Exception e) {
            DeBugLog.e(TAG, "反注册ExitBroadcastReceiver失败:" + e.getMessage());
        }

        // try {
        // unregisterReceiver(offlineMessageReceiver);
        // } catch (Exception e) {
        // }
        if (conflictBuilder != null) {
            conflictBuilder.create().dismiss();
            conflictBuilder = null;
        }

    }

    @Override
    public void finish() {
        imageLoader.clearMemoryCache();
        super.finish();
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

}