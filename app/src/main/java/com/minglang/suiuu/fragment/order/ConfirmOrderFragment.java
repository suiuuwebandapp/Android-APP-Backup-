package com.minglang.suiuu.fragment.order;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.pulltorefresh.PullToRefreshBase;
import com.minglang.suiuu.customview.pulltorefresh.PullToRefreshListView;

/**
 * 已接单页面
 * <p/>
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfirmOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfirmOrderFragment extends Fragment {

    private static final String TAG = ConfirmOrderFragment.class.getSimpleName();

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String userSign;
    private String verification;

    private PullToRefreshListView pullToRefreshListView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfirmOrderFragment.
     */
    public static ConfirmOrderFragment newInstance(String param1, String param2) {
        ConfirmOrderFragment fragment = new ConfirmOrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ConfirmOrderFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_confirm_order, container, false);

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
        Log.i(TAG, "userSign:" + userSign + ",verification:" + verification);

        pullToRefreshListView = (PullToRefreshListView) rootView.findViewById(R.id.confirm_order_list_view);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
    }

    /**
     * 控件动作
     */
    private void ViewAction() {

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                pullToRefreshListView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                pullToRefreshListView.onRefreshComplete();
            }
        });

    }

}
