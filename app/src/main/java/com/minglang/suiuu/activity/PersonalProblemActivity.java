package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.fragment.center.PersonalProblemFragment;
import com.minglang.suiuu.utils.StatusBarCompat;
import com.minglang.suiuu.utils.SuiuuInfo;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;

public class PersonalProblemActivity extends BaseAppCompatActivity {

    private static final String USER_SIGN = "userSign";

    @BindColor(R.color.white)
    int titleColor;

    @Bind(R.id.personal_problem_tool_bar)
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_problem);

        StatusBarCompat.compat(this);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(titleColor);

        userSign = getIntent().getStringExtra(USER_SIGN);

        verification = SuiuuInfo.ReadVerification(this);
        token = SuiuuInfo.ReadAppTimeSign(this);

        PersonalProblemFragment personalProblemFragment = PersonalProblemFragment.newInstance(userSign, verification, token);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.personal_problem_root_layout, personalProblemFragment);
        ft.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}