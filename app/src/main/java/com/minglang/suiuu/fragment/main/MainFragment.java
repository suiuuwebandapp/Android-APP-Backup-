package com.minglang.suiuu.fragment.main;

import com.minglang.suiuu.R;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


/**
 * 主页面
 */
public class MainFragment extends Fragment {

    private ListView mainPageListView;

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, null);

        initView(rootView);

        ViewAction();

        return rootView;
    }

    private void ViewAction() {
        mainPageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    /**
     * 初始化方法
     */
    private void initView(View rootView) {
        mainPageListView = (ListView) rootView.findViewById(R.id.mainPageListView);
    }

}
