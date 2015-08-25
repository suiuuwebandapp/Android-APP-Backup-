package com.minglang.suiuu.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.ShowBigPictureAdapter;
import com.minglang.suiuu.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：展示大图片
 * 创建人：Administrator
 * 创建时间：2015/8/25 17:25
 * 修改人：Administrator
 * 修改时间：2015/8/25 17:25
 * 修改备注：
 */
public class ShowBigPictureActivity extends BaseActivity{
    private List<TextView> tvs;
    private String[] items = {"第一个","第二个","第三个"};
    private TabLayout tabLayout;
    private ViewPager vp;
    private ShowBigPictureAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_big_picture);
        tvs = new ArrayList<TextView>();
        for (int i = 0; i < items.length; i++) {
            TextView tv = new TextView(this);
            tv.setText(items[i]);
            LinearLayout.LayoutParams lp =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            tv.setTextColor(Color.BLACK);
            tv.setBackgroundColor(Color.WHITE);
            tv.setGravity(Gravity.CENTER);
            tv.setLayoutParams(lp);
            tv.setTextSize(22);
            tvs.add(tv);
        }
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setTabTextColors(Color.WHITE, Color.GRAY);//设置文本在选中和为选中时候的颜色
        vp = (ViewPager) findViewById(R.id.vp);
        List<String> titleList = new ArrayList<>();
        titleList.add("yi");
        titleList.add("er");
        titleList.add("san");

        adapter = new ShowBigPictureAdapter(tvs,titleList);
        vp.setAdapter(adapter);

        //用来设置tab的，同时也要覆写  PagerAdapter 的 CharSequence getPageTitle(int position) 方法，要不然 Tab 没有 title
        tabLayout.setupWithViewPager(vp);
        //关联 TabLayout viewpager
        tabLayout.setTabsFromPagerAdapter(adapter);
    }


}
