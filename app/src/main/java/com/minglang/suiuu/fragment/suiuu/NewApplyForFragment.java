package com.minglang.suiuu.fragment.suiuu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.NewApplyForAdapter;
import com.minglang.suiuu.customview.pulltorefresh.PullToRefreshBase;
import com.minglang.suiuu.customview.pulltorefresh.PullToRefreshListView;
import com.minglang.suiuu.entity.NewApplyForEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link JoinFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewApplyForFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String userSign;
    private String verification;

    private PullToRefreshListView pullToRefreshListView;

    private List<NewApplyForEntity> newApplyForEntityList;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewApplyForFragment.
     */
    public static NewApplyForFragment newInstance(String param1, String param2) {
        NewApplyForFragment fragment = new NewApplyForFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public NewApplyForFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userSign = getArguments().getString(ARG_PARAM1);
            verification = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_new_apply_for, container, false);

        initView(rootView);
        ViewAction();
        return rootView;
    }

    /**
     * 初始化方法
     *
     * @param view Fragment的根View
     */
    private void initView(View view) {
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.ApplyForListView);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);

        ListView listView = pullToRefreshListView.getRefreshableView();

        newApplyForEntityList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            NewApplyForEntity newApplyForEntity = new NewApplyForEntity();
            newApplyForEntity.setUserName("佚名" + i);
            newApplyForEntity.setImagePath("http://q.qlogo.cn/qqapp/1104557000/C7A47D7E23F617EA5E0CF13B14A036FA/100");
            newApplyForEntityList.add(newApplyForEntity);
        }
        NewApplyForAdapter applyForAdapter = new NewApplyForAdapter(getActivity());
        applyForAdapter.setList(newApplyForEntityList);
        listView.setAdapter(applyForAdapter);
    }

    private void ViewAction() {

        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), newApplyForEntityList.get(position).getUserName(), Toast.LENGTH_SHORT).show();
            }
        });

        pullToRefreshListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                Toast.makeText(getActivity(), "已到达底部", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
