package com.minglang.suiuu.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.MySuiuuReleaseAdapter;
import com.minglang.suiuu.fragment.suiuu.JoinFragment;
import com.minglang.suiuu.fragment.suiuu.NewApplyForFragment;
import com.minglang.suiuu.utils.AppConstant;
import com.minglang.suiuu.utils.SuiuuInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 我发布的随游页面
 */
public class MySuiuuReleaseActivity extends AppCompatActivity {

    private static final String TAG = MySuiuuReleaseActivity.class.getSimpleName();

    /**
     * 返回键*
     */
    private ImageView back;

    /**
     * 我发布的随游标题，详细信息，价格
     */
    private TextView mySuiuuTitle, mySuiuuInfoText, mySuiuuPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_suiuu_release);

        initView();
        ViewAction();
    }

    /**
     * 初始化方法
     */
    private void initView() {

        back = (ImageView) findViewById(R.id.my_suiuu_release_back);
        ViewPager releaseViewPager = (ViewPager) findViewById(R.id.my_suiuu_release_viewPager);

        mySuiuuTitle = (TextView) findViewById(R.id.my_suiuu_release_title);
        mySuiuuInfoText = (TextView) findViewById(R.id.my_suiuu_release_info_text);
        mySuiuuPrice = (TextView) findViewById(R.id.my_suiuu_release_price);

        String join = "已参加";
        String applyFor = "新申请";

        //可滑动的Tab头
        TabLayout tabLayout = (TabLayout) findViewById(R.id.my_suiuu_release_tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(join), true);
        tabLayout.addTab(tabLayout.newTab().setText(applyFor), false);
        tabLayout.setTabTextColors(getResources().getColor(R.color.tr_black), getResources().getColor(R.color.text_select_true));

        String userSign = SuiuuInfo.ReadUserSign(this);
        String verification = SuiuuInfo.ReadVerification(this);

        JoinFragment joinFragment = JoinFragment.newInstance(userSign, verification);
        NewApplyForFragment newApplyForFragment = NewApplyForFragment.newInstance(userSign, verification);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(joinFragment);
        fragments.add(newApplyForFragment);
        List<String> titleList = new ArrayList<>();
        titleList.add(join);
        titleList.add(applyFor);

        MySuiuuReleaseAdapter releaseAdapter = new MySuiuuReleaseAdapter(getSupportFragmentManager(), fragments, titleList);
        releaseViewPager.setAdapter(releaseAdapter);
        tabLayout.setupWithViewPager(releaseViewPager);
        tabLayout.setTabsFromPagerAdapter(releaseAdapter);
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
                                    Toast.makeText(MySuiuuReleaseActivity.this, "新标题不能为空！", Toast.LENGTH_SHORT).show();
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
                intent.putExtra("oldInfo", infoText);
                startActivityForResult(intent, AppConstant.EDIT_SUIUU_INFO_TEXT);
            }
        });

        mySuiuuPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String priceNumber = mySuiuuPrice.getText().toString().trim();
                Intent intent = new Intent(MySuiuuReleaseActivity.this, EditSuiuuPriceActivity.class);
                intent.putExtra("oldPrice", priceNumber);
                startActivityForResult(intent, AppConstant.EDIT_SUIUU_PRICE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case AppConstant.EDIT_SUIUU_INFO_TEXT:
                if (resultCode == AppConstant.EDIT_SUIUU_INFO_TEXT_BACK) {
                    String newInfo = data.getStringExtra("newInfo");
                    mySuiuuInfoText.setText(newInfo);

                    Log.i(TAG, "newInfo:" + newInfo);
                }
                break;

            case AppConstant.EDIT_SUIUU_PRICE:
                if (resultCode == AppConstant.EDIT_SUIUU_PRICE_BACK) {
                    String newBasicPrice = data.getStringExtra("newBasicPrice");
                    String newAdditionalPrice = data.getStringExtra("newAdditionalPrice");
                    String newOtherPrice = data.getStringExtra("newOtherPrice");

                    Log.i(TAG, "newBasicPrice:" + newBasicPrice + ",newAdditionalPrice:" + newAdditionalPrice +
                            ",newOtherPrice:" + newOtherPrice);
                    String allPrice = String.valueOf(Integer.valueOf(newBasicPrice) + Integer.valueOf(newAdditionalPrice)
                            + Integer.valueOf(newAdditionalPrice));
                    mySuiuuPrice.setText(allPrice);
                }
                break;
        }

    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

}