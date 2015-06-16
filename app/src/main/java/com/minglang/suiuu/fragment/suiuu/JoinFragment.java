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
import com.minglang.suiuu.adapter.JoinAdapter;
import com.minglang.suiuu.customview.pulltorefresh.PullToRefreshBase;
import com.minglang.suiuu.customview.pulltorefresh.PullToRefreshListView;
import com.minglang.suiuu.entity.JoinEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JoinFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JoinFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String userSign;
    private String verification;

    private PullToRefreshListView pullToRefreshListView;

    private List<JoinEntity> joinEntityList;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JoinFragment.
     */
    public static JoinFragment newInstance(String param1, String param2) {
        JoinFragment fragment = new JoinFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public JoinFragment() {
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

        View rootView = inflater.inflate(R.layout.fragment_join, container, false);

        initView(rootView);
        ViewAction();
        return rootView;
    }

    /**
     * 初始化方法
     *
     * @param rootView Fragment根View
     */
    private void initView(View rootView) {
        pullToRefreshListView = (PullToRefreshListView) rootView.findViewById(R.id.JoinListView);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);

        ListView listView = pullToRefreshListView.getRefreshableView();

        joinEntityList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            JoinEntity entity = new JoinEntity();
            entity.setImagePath("http://q.qlogo.cn/qqapp/1104557000/C7A47D7E23F617EA5E0CF13B14A036FA/100");
            entity.setUserName("佚名" + i);
            entity.setUserLocation("北京");
            entity.setRemoveFlag(true);
            joinEntityList.add(entity);
        }

        JoinAdapter adapter = new JoinAdapter(getActivity());
        adapter.setList(joinEntityList);
        listView.setAdapter(adapter);
    }

    private void ViewAction() {

        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), joinEntityList.get(position).getUserName(), Toast.LENGTH_SHORT).show();
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
