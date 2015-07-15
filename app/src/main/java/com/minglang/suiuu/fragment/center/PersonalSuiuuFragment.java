package com.minglang.suiuu.fragment.center;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.TempAdapter;
import com.minglang.suiuu.utils.DeBugLog;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonalSuiuuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalSuiuuFragment extends Fragment {

    private static final String TAG = PersonalTravelFragment.class.getSimpleName();

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String userSign;
    private String verification;

    @Bind(R.id.personal_suiuu_grid)
    RecyclerView recyclerView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonalSuiuuFragment.
     */
    public static PersonalSuiuuFragment newInstance(String param1, String param2) {
        PersonalSuiuuFragment fragment = new PersonalSuiuuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PersonalSuiuuFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_personal_suiuu, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 2));
        recyclerView.setAdapter(new TempAdapter(getActivity()));
    }

}