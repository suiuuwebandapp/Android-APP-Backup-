package com.minglang.suiuu.fragment.center;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.TempAdapter;
import com.minglang.suiuu.utils.DeBugLog;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonalTravelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalTravelFragment extends Fragment {

    private static final String TAG = PersonalTravelFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String userSign;
    private String verification;

    @Bind(R.id.personal_travel_list)
    RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;

    private int lastVisibleItem;

    private TempAdapter adapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonalTravelFragment.
     */
    public static PersonalTravelFragment newInstance(String param1, String param2) {
        PersonalTravelFragment fragment = new PersonalTravelFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PersonalTravelFragment() {
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
        DeBugLog.i(TAG, "userSign:" + userSign + ",verification:" + verification);
        View rootView = inflater.inflate(R.layout.fragment_personal_travel, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        ViewAction();
        return rootView;
    }

    private void initView() {
        layoutManager = new LinearLayoutManager(getActivity());

        adapter = new TempAdapter(getActivity());

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void ViewAction() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    Toast.makeText(getActivity(), "已到达底部", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
    }

}