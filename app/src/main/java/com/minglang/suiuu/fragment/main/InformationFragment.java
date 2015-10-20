package com.minglang.suiuu.fragment.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.NewRemindPageAdapter;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.fragment.remind.MsgOrderFragment;
import com.minglang.suiuu.fragment.remind.MsgQuestionFragment;
import com.minglang.suiuu.fragment.remind.MsgSystemFragment;
import com.minglang.suiuu.fragment.remind.MsgTripImageFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 * <p/>
 * 消息页面
 */
public class InformationFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    @BindString(R.string.OrderMsg)
    String str1;

    @BindString(R.string.QuestionAndAnswerMsg)
    String str2;

    @BindString(R.string.TripGalleryMsg)
    String str3;

    @BindString(R.string.SystemMsg)
    String str4;

    @Bind(R.id.info_tab_layout)
    TabLayout tabLayout;

    @Bind(R.id.info_view_pager)
    ViewPager viewPager;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InformationFragment.
     */
    public static InformationFragment newInstance(String param1, String param2, String param3) {
        InformationFragment fragment = new InformationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    public InformationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userSign = getArguments().getString(ARG_PARAM1);
            verification = getArguments().getString(ARG_PARAM2);
            token = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_information, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        viewPager.setOffscreenPageLimit(4);

        List<String> titleList = new ArrayList<>();
        titleList.add(str1);
        titleList.add(str2);
        titleList.add(str3);
        titleList.add(str4);

        tabLayout.addTab(tabLayout.newTab().setText(str1), true);
        tabLayout.addTab(tabLayout.newTab().setText(str2), false);
        tabLayout.addTab(tabLayout.newTab().setText(str3), false);
        tabLayout.addTab(tabLayout.newTab().setText(str4), false);

        MsgOrderFragment msgOrderFragment = MsgOrderFragment.newInstance(userSign, verification, token);
        MsgQuestionFragment msgQuestionFragment = MsgQuestionFragment.newInstance(userSign, verification, token);
        MsgTripImageFragment msgTripImageFragment = MsgTripImageFragment.newInstance(userSign, verification, token);
        MsgSystemFragment msgSystemFragment = MsgSystemFragment.newInstance(userSign, verification, token);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(msgOrderFragment);
        fragmentList.add(msgQuestionFragment);
        fragmentList.add(msgTripImageFragment);
        fragmentList.add(msgSystemFragment);

        FragmentManager fm = getChildFragmentManager();

        NewRemindPageAdapter adapter = new NewRemindPageAdapter(fm, fragmentList, titleList);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);
    }

}