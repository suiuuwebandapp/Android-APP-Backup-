package com.minglang.suiuu.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.MySuiuuReleaseAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.fragment.suiuu.JoinFragment;
import com.minglang.suiuu.fragment.suiuu.NewApplyForFragment;
import com.minglang.suiuu.utils.AppConstant;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.SuiuuInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 我发布的随游页面
 */
public class MySuiuuReleaseActivity extends BaseAppCompatActivity {

    private static final String TAG = MySuiuuReleaseActivity.class.getSimpleName();

    private static final String TITLE = "title";
    private static final String INFO = "info";
    private static final String PRICE = "price";
    private static final String TRIP_ID = "tripId";
    private static final String TITLE_IMG = "titleImg";

    private static final String OLD_INFO = "oldInfo";
    private static final String OLD_PRICE = "oldPrice";

    private static final String NEW_INFO = "newInfo";

    private static final String NEW_BASIC_PRICE = "newBasicPrice";
    private static final String NEW_ADDITIONAL_PRICE = "newAdditionalPrice";
    private static final String NEW_OTHER_PRICE = "newOtherPrice";

    @BindString(R.string.Participate)
    String join;

    @BindString(R.string.NewApply)
    String applyFor;

    @BindColor(R.color.tr_black)
    int normalColor;

    @BindColor(R.color.mainColor)
    int selectedColor;

    @BindString(R.string.NoTitle)
    String NoTitle;

    @BindString(R.string.NoInfo)
    String NoInfo;

    @BindString(R.string.NewTitleNotNull)
    String NewTitleNotNull;

    /**
     * 返回键
     */
    @Bind(R.id.my_suiuu_release_back)
    ImageView back;

    @Bind(R.id.my_suiuu_release_show_image)
    SimpleDraweeView titleImageView;

    @Bind(R.id.my_suiuu_release_viewPager)
    ViewPager releaseViewPager;

    @Bind(R.id.my_suiuu_release_main_title)
    TextView mainTitleView;

    /**
     * 我发布的随游标题，详细信息，价格
     */
    @Bind(R.id.my_suiuu_release_title)
    TextView mySuiuuTitle;

    @Bind(R.id.my_suiuu_release_info_text)
    TextView mySuiuuInfoText;

    @Bind(R.id.my_suiuu_release_price)
    TextView mySuiuuPrice;

    @Bind(R.id.my_suiuu_release_tabLayout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_suiuu_release);
        ButterKnife.bind(this);
        initView();
        ViewAction();
    }

    /**
     * 初始化方法
     */
    private void initView() {
        Intent oldIntent = getIntent();

        String title = oldIntent.getStringExtra(TITLE);
        String info = oldIntent.getStringExtra(INFO);
        String price = oldIntent.getStringExtra(PRICE);
        String tripId = oldIntent.getStringExtra(TRIP_ID);
        String titleImg = oldIntent.getStringExtra(TITLE_IMG);

        if (!TextUtils.isEmpty(title)) {
            mySuiuuTitle.setText(title);
            mainTitleView.setText(title);
        } else {
            mySuiuuTitle.setText(NoTitle);
            mainTitleView.setText(NoTitle);
        }

        if (!TextUtils.isEmpty(info)) {
            mySuiuuInfoText.setText(info);
        } else {
            mySuiuuInfoText.setText(NoInfo);
        }

        if (!TextUtils.isEmpty(price)) {
            mySuiuuPrice.setText(price);
        } else {
            mySuiuuPrice.setText("0.0");
        }

        //可滑动的Tab头
        tabLayout.addTab(tabLayout.newTab().setText(join), true);
        tabLayout.addTab(tabLayout.newTab().setText(applyFor), false);
        tabLayout.setTabTextColors(normalColor, selectedColor);

        userSign = SuiuuInfo.ReadUserSign(this);
        verification = SuiuuInfo.ReadVerification(this);

        JoinFragment joinFragment = JoinFragment.newInstance(userSign, verification, tripId);
        NewApplyForFragment newApplyForFragment = NewApplyForFragment.newInstance(userSign, verification, tripId);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(joinFragment);
        fragments.add(newApplyForFragment);

        List<String> titleList = new ArrayList<>();
        titleList.add(join);
        titleList.add(applyFor);

        MySuiuuReleaseAdapter releaseAdapter
                = new MySuiuuReleaseAdapter(getSupportFragmentManager(), fragments, titleList);
        releaseViewPager.setAdapter(releaseAdapter);
        tabLayout.setupWithViewPager(releaseViewPager);
        tabLayout.setTabsFromPagerAdapter(releaseAdapter);

        if (!TextUtils.isEmpty(titleImg))
            titleImageView.setImageURI(Uri.parse(titleImg));
    }

    /**
     * 控件动作
     */
    private void ViewAction() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mySuiuuTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(MySuiuuReleaseActivity.this);

                new AlertDialog.Builder(MySuiuuReleaseActivity.this).setTitle("请输入新标题").setView(editText)
                        .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String newTitle = editText.getText().toString().trim();
                                if (TextUtils.isEmpty(newTitle)) {
                                    Toast.makeText(MySuiuuReleaseActivity.this, NewTitleNotNull,
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    mySuiuuTitle.setText(newTitle);
                                }
                            }
                        })
                        .setPositiveButton(android.R.string.no, null)
                        .create().show();
            }
        });

        mySuiuuInfoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String infoText = mySuiuuInfoText.getText().toString().trim();
                Intent intent = new Intent(MySuiuuReleaseActivity.this, EditSuiuuInfoActivity.class);
                intent.putExtra(OLD_INFO, infoText);
                startActivityForResult(intent, AppConstant.EDIT_SUIUU_INFO_TEXT);
            }
        });

        mySuiuuPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String priceNumber = mySuiuuPrice.getText().toString().trim();
                Intent intent = new Intent(MySuiuuReleaseActivity.this, EditSuiuuPriceActivity.class);
                intent.putExtra(OLD_PRICE, priceNumber);
                startActivityForResult(intent, AppConstant.EDIT_SUIUU_PRICE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case AppConstant.EDIT_SUIUU_INFO_TEXT:
                if (resultCode == AppConstant.EDIT_SUIUU_INFO_TEXT_BACK) {
                    String newInfo = data.getStringExtra(NEW_INFO);
                    mySuiuuInfoText.setText(newInfo);

                    DeBugLog.i(TAG, "newInfo:" + newInfo);
                }
                break;

            case AppConstant.EDIT_SUIUU_PRICE:
                if (resultCode == AppConstant.EDIT_SUIUU_PRICE_BACK) {
                    String newBasicPrice = data.getStringExtra(NEW_BASIC_PRICE);
                    String newAdditionalPrice = data.getStringExtra(NEW_ADDITIONAL_PRICE);
                    String newOtherPrice = data.getStringExtra(NEW_OTHER_PRICE);

                    DeBugLog.i(TAG, "newBasicPrice:" + newBasicPrice
                            + ",newAdditionalPrice:" + newAdditionalPrice
                            + ",newOtherPrice:" + newOtherPrice);

                    String allPrice
                            = String.valueOf(Double.valueOf(newBasicPrice)
                            + Double.valueOf(newAdditionalPrice)
                            + Double.valueOf(newAdditionalPrice));
                    mySuiuuPrice.setText(allPrice);
                }
                break;
        }

    }

}