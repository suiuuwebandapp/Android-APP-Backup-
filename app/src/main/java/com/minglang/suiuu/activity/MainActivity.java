package com.minglang.suiuu.activity;

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
import android.view.animation.AnimationSet;
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
import com.minglang.suiuu.chat.activity.ChatAllHistoryFragment;
import com.minglang.suiuu.chat.chat.Constant;
import com.minglang.suiuu.chat.utils.CommonUtils;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.fragment.main.LoopFragment;
import com.minglang.suiuu.fragment.main.OldMainFragment;
import com.minglang.suiuu.fragment.main.SuiuuFragment;
import com.minglang.suiuu.utils.ConstantUtils;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 应用程序主界面
 */
public class MainActivity extends BaseActivity {

    protected NotificationManager notificationManager;
    private static final int notificationId = 11;

    private static final String TAG = MainActivity.class.getSimpleName();

    private DrawerLayout mDrawerLayout;

    //侧滑布局
    private RelativeLayout sliderView;

    /**
     * 点击修改昵称
     */
    private TextView nickNameView;

    /**
     * 点击修改头像
     */
    private CircleImageView headImageView;

    private Switch switchSuiuu;

    private ListView sideListView;

    //标题
    private TextView titleInfo;

    private ImageView drawerSwitch;

    /**
     * 各个Tab页
     */
    private LinearLayout tab1, tab2, tab3, tab4;

    /**
     * 主页页面
     */
    private OldMainFragment oldMainFragment;

    /**
     * 圈子页面
     */
    private LoopFragment loopFragment;

    /**
     * 路线页面
     */
    private SuiuuFragment suiuuFragment;

    /**
     * 会话页面
     */
    private ChatAllHistoryFragment conversationFragment;

    private AnimationSet animationSetHide;

    // 账号在别处登录
    public boolean isConflict = false;
    private NewMessageBroadcastReceiver msgReceiver;

    //当前为fragment的第几页
    private int currentIndex = 0;
    private TextView msgCount;
    private RelativeLayout errorItem;
    private TextView errorText;

    /**
     * 旅图页面按钮布局
     */
    private RelativeLayout TravelImageLayout;

    /**
     * 随游页面按钮布局
     */
    private FrameLayout SuiuuBtnLayout;

    /**
     * 问答页面按钮布局
     */
    private RelativeLayout QaLayout;

    /**
     * 收件箱页面按钮布局
     */
    private FrameLayout InboxBtnLayout;

    /**
     * 旅图页面搜索按钮
     */
    private ImageView Main_1_Search;

    /**
     * 旅图页面标签按钮
     */
    private ImageView Main_1_Tag;

    /**
     * 旅图页面相册按钮
     */
    private ImageView Main_1_Album;

    /**
     * 随游页面搜索按钮
     */
    private ImageView Main_2_Search;

    private ImageView Main_3_Pic;

    private ImageView Main_3_Record;

    private ImageView Main_3_Ask;

    private ImageView Main_4_Search;

    private ImageView iv_theme;
    private ImageView iv_loop;
    private ImageView iv_suiuu;
    private ImageView iv_conversation;
    private RelativeLayout rl_top_info;

    private ExitBroadcastReceiver exitBroadcastReceiver;

    private EMChatManager chatManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initReadSavedInstanceState(savedInstanceState);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        setContentView(R.layout.activity_main);

        UmengUpdateAgent.update(this);

        exitBroadcastReceiver = new ExitBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SettingActivity.class.getSimpleName());
        this.registerReceiver(exitBroadcastReceiver, intentFilter);

        initView();
        initRegisterAllBroadcastReceiver();
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
            return;
        } else if (saveFlag && savedInstanceState.getBoolean("isConflict", false)) {
            // 防止被T后，没点确定按钮然后按了home键，长期在后台又进app导致的crash
            // 三个fragment里加的判断同理
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        if (saveFlag) {
            if ("saveState".equals(savedInstanceState.getString("save"))) {
                if (savedInstanceState.getBoolean("main", false)) {
                    LoadMainFragment();
                } else if (savedInstanceState.getBoolean("loop", false)) {
                    LoadLoopFragment();
                } else if (savedInstanceState.getBoolean("suiuu", false)) {
                    LoadSuiuuFragment();
                } else if (savedInstanceState.getBoolean("chat", false)) {
                    LoadConversationFragment();
                } else {
                    LoadDefaultFragment();
                }
            }
        }
    }

    /**
     * 初始化方法
     */
    private void initView() {
        errorItem = (RelativeLayout) findViewById(R.id.rl_error_item);
        errorText = (TextView) errorItem.findViewById(R.id.tv_connect_errormsg);
        msgCount = (TextView) findViewById(R.id.unread_msg_number);

        sliderView = (RelativeLayout) findViewById(R.id.sliderLayout);
        ViewGroup.LayoutParams sliderNavigationViewParams = sliderView.getLayoutParams();
        sliderNavigationViewParams.width = screenWidth / 4 * 3;
        sliderNavigationViewParams.height = screenHeight;
        sliderView.setLayoutParams(sliderNavigationViewParams);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerLayout.setFocusableInTouchMode(true);

        RelativeLayout titleLayout = (RelativeLayout) findViewById(R.id.titleLayout);
        ConstantUtils.topHeight = titleLayout.getLayoutParams().height;

        TravelImageLayout = (RelativeLayout) findViewById(R.id.TravelImageLayout);
        SuiuuBtnLayout = (FrameLayout) findViewById(R.id.SuiuuButtonLayout);
        QaLayout = (RelativeLayout) findViewById(R.id.QA_Layout);
        InboxBtnLayout = (FrameLayout) findViewById(R.id.InboxButtonLayout);

        Main_1_Search = (ImageView) findViewById(R.id.main_1_search);
        Main_1_Tag = (ImageView) findViewById(R.id.main_1_tag);
        Main_1_Album = (ImageView) findViewById(R.id.main_1_album);

        Main_2_Search = (ImageView) findViewById(R.id.main_2_search);

        Main_3_Pic = (ImageView) findViewById(R.id.main_3_pic);
        Main_3_Record = (ImageView) findViewById(R.id.main_3_record);
        Main_3_Ask = (ImageView) findViewById(R.id.main_3_ask);

        Main_4_Search = (ImageView) findViewById(R.id.main_4_search);

        titleInfo = (TextView) findViewById(R.id.titleInfo);
        drawerSwitch = (ImageView) findViewById(R.id.drawerSwitch);

        nickNameView = (TextView) findViewById(R.id.nickName);
        String strNickName = SuiuuInfo.ReadUserData(this).getNickname();
        if (!TextUtils.isEmpty(strNickName)) {
            nickNameView.setText(strNickName);
        } else {
            nickNameView.setText("");
        }

        headImageView = (CircleImageView) findViewById(R.id.headImage);
        String strHeadImagePath = SuiuuInfo.ReadUserData(this).getHeadImg();
        if (!TextUtils.isEmpty(strHeadImagePath)) {
            imageLoader.displayImage(strHeadImagePath, headImageView);
        }

        switchSuiuu = (Switch) findViewById(R.id.switchSuiuu);
        switchSuiuu.setChecked(true);

        sideListView = (ListView) findViewById(R.id.sideListView);
        List<String> strList = new ArrayList<>();
        Collections.addAll(strList, getResources().getStringArray(R.array.sideList));
        MainSliderAdapter adapter = new MainSliderAdapter(this, strList);
        adapter.setScreenHeight(screenHeight);
        sideListView.setAdapter(adapter);

        tab1 = (LinearLayout) findViewById(R.id.tab1);
        tab2 = (LinearLayout) findViewById(R.id.tab2);
        tab3 = (LinearLayout) findViewById(R.id.tab3);
        tab4 = (LinearLayout) findViewById(R.id.tab4);

        //初始化底部控件
        iv_theme = (ImageView) findViewById(R.id.img1);
        iv_loop = (ImageView) findViewById(R.id.img2);
        iv_suiuu = (ImageView) findViewById(R.id.img3);
        iv_conversation = (ImageView) findViewById(R.id.img4);

        changeTheme(true);

        rl_top_info = (RelativeLayout) findViewById(R.id.rl_top_info);

        oldMainFragment = new OldMainFragment();
        conversationFragment = new ChatAllHistoryFragment();
        LoadDefaultFragment();
    }

    private void initRegisterAllBroadcastReceiver() {
        chatManager = EMChatManager.getInstance();

        MobclickAgent.updateOnlineConfig(this);
        if (getIntent().getBooleanExtra("conflict", false) && !isConflictDialogShow) {
            showConflictDialog();
        } else if (getIntent().getBooleanExtra(Constant.ACCOUNT_REMOVED, false) && !isAccountRemovedDialogShow) {
            showAccountRemovedDialog();
        }

        MobclickAgent.updateOnlineConfig(this);


        // 注册一个接收消息的BroadcastReceiver
        msgReceiver = new NewMessageBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(chatManager.getNewMessageBroadcastAction());
        intentFilter.setPriority(3);
        registerReceiver(msgReceiver, intentFilter);

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
    }

    /**
     * 控件行为
     */
    private void ViewAction() {

        switchSuiuu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        Main_1_Search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Main_1_Tag.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Main_1_Album.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Main_2_Search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Main_3_Ask.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AskQuestionActivity.class);
                startActivity(intent);
            }
        });

        Main_3_Pic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SelectPictureActivity.class);
                intent.putExtra("state", 1);
                startActivity(intent);
            }
        });

        Main_3_Record.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AskQuestionActivity.class);
                intent.putExtra("record", 1);
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

        tab1.setOnClickListener(onClickListener);
        tab2.setOnClickListener(onClickListener);
        tab3.setOnClickListener(onClickListener);
        tab4.setOnClickListener(onClickListener);

    }

    public void showCommon() {
        titleInfo.setVisibility(View.VISIBLE);
        rl_top_info.setVisibility(View.GONE);
    }

    /**
     * 加载主页页面
     */
    private void LoadMainFragment() {
        FragmentTransaction ft = fm.beginTransaction();
        if (loopFragment != null) {
            if (loopFragment.isAdded()) {
                ft.hide(loopFragment);
            }
        }
        if (suiuuFragment != null) {
            if (suiuuFragment.isAdded()) {
                ft.hide(suiuuFragment);
            }
        }
        if (conversationFragment != null) {
            if (conversationFragment.isAdded()) {
                ft.hide(conversationFragment);
            }
        }
        if (oldMainFragment == null) {
            oldMainFragment = new OldMainFragment();
        }
        if (oldMainFragment.isAdded()) {
            ft.show(oldMainFragment);
        } else {
            ft.add(R.id.showLayout, oldMainFragment);
        }
        currentIndex = 0;
        ft.commit();
        showTravelImage();
    }

    /**
     * 加载圈子页面
     */
    private void LoadLoopFragment() {
        FragmentTransaction ft = fm.beginTransaction();
        if (oldMainFragment != null) {
            if (oldMainFragment.isAdded()) {
                ft.hide(oldMainFragment);
            }
        }
        if (suiuuFragment != null) {
            if (suiuuFragment.isAdded()) {
                ft.hide(suiuuFragment);
            }
        }
        if (conversationFragment != null) {
            if (conversationFragment.isAdded()) {
                ft.hide(conversationFragment);
            }
        }
        if (loopFragment == null) {
            loopFragment = new LoopFragment();
        }
        if (loopFragment.isAdded()) {
            ft.show(loopFragment);
        } else {
            ft.add(R.id.showLayout, loopFragment);
        }
        currentIndex = 1;
        ft.commit();
        showSuiuu();
    }

    /**
     * 加载路线页面
     */
    private void LoadSuiuuFragment() {
        FragmentTransaction ft = fm.beginTransaction();
        if (oldMainFragment != null) {
            if (oldMainFragment.isAdded()) {
                ft.hide(oldMainFragment);
            }
        }
        if (loopFragment != null) {
            if (loopFragment.isAdded()) {
                ft.hide(loopFragment);
            }
        }
        if (conversationFragment != null) {
            if (conversationFragment.isAdded()) {
                ft.hide(conversationFragment);
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
        currentIndex = 2;
        ft.commit();
        showQA();
    }

    /**
     * 加载会话页面
     */
    private void LoadConversationFragment() {
        FragmentTransaction ft = fm.beginTransaction();
        if (oldMainFragment != null) {
            if (oldMainFragment.isAdded()) {
                ft.hide(oldMainFragment);
            }
        }
        if (loopFragment != null) {
            if (loopFragment.isAdded()) {
                ft.hide(loopFragment);
            }
        }
        if (suiuuFragment != null) {
            if (suiuuFragment.isAdded()) {
                ft.hide(suiuuFragment);
            }
        }

        if (conversationFragment.isAdded()) {
            ft.show(conversationFragment);
        } else {
            ft.add(R.id.showLayout, conversationFragment);

        }
        currentIndex = 3;
        msgCount.setVisibility(View.INVISIBLE);
        ft.commit();
        showInbox();
    }

    /**
     * 加载初始默认页面
     */
    private void LoadDefaultFragment() {
        FragmentTransaction ft = fm.beginTransaction();
        if (oldMainFragment == null) {
            oldMainFragment = new OldMainFragment();
            ft.add(R.id.showLayout, oldMainFragment);
        } else {
            ft.add(R.id.showLayout, oldMainFragment);
        }
        ft.commit();
        showTravelImage();
    }

    private void showTravelImage() {
        TravelImageLayout.setVisibility(View.VISIBLE);
        SuiuuBtnLayout.setVisibility(View.GONE);
        QaLayout.setVisibility(View.GONE);
        InboxBtnLayout.setVisibility(View.GONE);
    }

    private void showSuiuu() {
        TravelImageLayout.setVisibility(View.GONE);
        SuiuuBtnLayout.setVisibility(View.VISIBLE);
        QaLayout.setVisibility(View.GONE);
        InboxBtnLayout.setVisibility(View.GONE);
    }

    private void showQA() {
        TravelImageLayout.setVisibility(View.GONE);
        SuiuuBtnLayout.setVisibility(View.GONE);
        QaLayout.setVisibility(View.VISIBLE);
        InboxBtnLayout.setVisibility(View.GONE);
    }

    private void showInbox() {
        TravelImageLayout.setVisibility(View.GONE);
        SuiuuBtnLayout.setVisibility(View.GONE);
        QaLayout.setVisibility(View.GONE);
        InboxBtnLayout.setVisibility(View.VISIBLE);
    }

    //判断主题
    private void changeTheme(Boolean isChoice) {
        if (isChoice) {
            iv_theme.setImageResource(R.drawable.icon_main_1_green);
        } else {
            iv_theme.setImageResource(R.drawable.icon_main_1_white);
        }
    }

    //判断圈子
    private void changeLoop(Boolean isChoice) {
        if (isChoice) {
            iv_loop.setImageResource(R.drawable.icon_main_2_green);
        } else {
            iv_loop.setImageResource(R.drawable.icon_main_2_white);
        }
    }

    //判断随游
    private void changeSuiuu(Boolean isChoice) {
        if (isChoice) {
            iv_suiuu.setImageResource(R.drawable.icon_main_3_green);
        } else {
            iv_suiuu.setImageResource(R.drawable.icon_main_3_white);
        }
    }

    //判断会话
    private void changeConversation(Boolean isChoice) {
        if (isChoice) {
            iv_conversation.setImageResource(R.drawable.icon_main_4_green);
        } else {
            iv_conversation.setImageResource(R.drawable.icon_main_4_white);
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
                accountRemovedBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        boolean mainFragmentFlag, loopFragmentFlag, suiuuFragmentFlag, chatFragmentFlag;

        mainFragmentFlag = oldMainFragment != null && !oldMainFragment.isInLayout();
        loopFragmentFlag = loopFragment != null && !loopFragment.isInLayout();
        suiuuFragmentFlag = suiuuFragment != null && !suiuuFragment.isInLayout();
        chatFragmentFlag = conversationFragment != null && !conversationFragment.isInLayout();

        String str = "saveState";

        outState.putString("save", str);
        outState.putBoolean("main", mainFragmentFlag);
        outState.putBoolean("loop", loopFragmentFlag);
        outState.putBoolean("suiuu", suiuuFragmentFlag);
        outState.putBoolean("chat", chatFragmentFlag);
    }

    /**
     * 新消息广播接收者
     */
    private class NewMessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 主页面收到消息后，主要为了提示未读，实际消息内容需要到chat页面查看

            String from = intent.getStringExtra("from");
            // 消息id
            String msgId = intent.getStringExtra("msgid");
            EMMessage message = chatManager.getMessage(msgId);

            // fix: logout crash， 如果正在接收大量消息
            // 因为此时已经logout，消息队列已经被清空， broadcast延时收到，所以会出现message为空的情况
            if (message == null) {
                return;
            }

            // 2014-10-22 修复在某些机器上，在聊天页面对方发消息过来时不立即显示内容的bug
            if (ChatActivity.activityInstance != null) {
                if (message.getChatType() == ChatType.GroupChat) {
                    if (message.getTo().equals(ChatActivity.activityInstance.getToChatUsername()))
                        return;
                } else {
                    if (from.equals(ChatActivity.activityInstance.getToChatUsername()))
                        return;
                }
            }

            // 注销广播接收者，否则在ChatActivity中会收到这个广播
            abortBroadcast();

            notifyNewMessage(message);

            // 刷新bottom bar消息未读数
            updateUnreadLabel();
            if (currentIndex == 3) {
                // 当前页面如果为聊天历史页面，刷新此页面
                if (conversationFragment != null) {
                    conversationFragment.refresh();
                    msgCount.setVisibility(View.INVISIBLE);
                }
            }

        }
    }

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

    private class ExitBroadcastReceiver extends BroadcastReceiver {

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
                    Intent headIntent = new Intent(MainActivity.this, PersonalActivity.class);
                    startActivity(headIntent);
                    mDrawerLayout.closeDrawer(sliderView);
                    break;

                case R.id.nickName:
                    Intent nickIntent = new Intent(MainActivity.this, PersonalActivity.class);
                    startActivity(nickIntent);
                    mDrawerLayout.closeDrawer(sliderView);
                    break;

                case R.id.tab1:
                    showCommon();
                    titleInfo.setText(getResources().getString(R.string.mainTitle1));
                    changeTheme(true);
                    changeLoop(false);
                    changeSuiuu(false);
                    changeConversation(false);
                    LoadMainFragment();
                    break;

                case R.id.tab2:
                    showCommon();
                    titleInfo.setText(getResources().getString(R.string.mainTitle2));
                    changeTheme(false);
                    changeLoop(true);
                    changeSuiuu(false);
                    changeConversation(false);
                    LoadLoopFragment();
                    break;

                case R.id.tab3:
                    titleInfo.setVisibility(View.GONE);
                    rl_top_info.setVisibility(View.VISIBLE);
                    changeTheme(false);
                    changeLoop(false);
                    changeSuiuu(true);
                    changeConversation(false);
                    LoadSuiuuFragment();
                    break;

                case R.id.tab4:
                    showCommon();
                    titleInfo.setText(getResources().getString(R.string.mainTitle4));
                    changeTheme(false);
                    changeLoop(false);
                    changeSuiuu(false);
                    changeConversation(true);
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
            unregisterReceiver(msgReceiver);
        } catch (Exception ignored) {
            DeBugLog.e(TAG, "msgReceiver:" + ignored.getMessage());
        }

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
            unregisterReceiver(exitBroadcastReceiver);
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
//                DemoApplication.getInstance().exit();
                    finish();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}