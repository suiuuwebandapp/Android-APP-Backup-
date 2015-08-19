package com.minglang.suiuu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.MainSliderAdapter;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.fragment.main.CommunityFragment;
import com.minglang.suiuu.fragment.main.InformationFragment;
import com.minglang.suiuu.fragment.main.SuiuuFragment;
import com.minglang.suiuu.fragment.main.TripGalleryFragment;
import com.minglang.suiuu.utils.AppConstant;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.umeng.update.UmengUpdateAgent;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UmengUpdateAgent.update(this);
        ButterKnife.bind(this);
        initView();
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
                intent.putExtra(STATE, 1);
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
                Intent intent = new Intent(MainActivity.this, PutQuestionsActivity.class);
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
            ft.add(R.id.showLayout, communityFragment);
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
            ft.add(R.id.showLayout, informationFragment);
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