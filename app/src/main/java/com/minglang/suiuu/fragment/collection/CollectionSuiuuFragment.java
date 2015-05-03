package com.minglang.suiuu.fragment.collection;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.minglang.suiuu.R;

/**
 * 收藏的路线
 * <p/>
 * A simple {@link Fragment} subclass.
 * Use the {@link CollectionSuiuuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollectionSuiuuFragment extends Fragment {

    private static final String TAG = CollectionSuiuuFragment.class.getSimpleName();

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private GridView gridView;

    private int page = 1;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CollectionRouteFragment.
     */
    public static CollectionSuiuuFragment newInstance(String param1, String param2) {
        CollectionSuiuuFragment fragment = new CollectionSuiuuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CollectionSuiuuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_collection_suiuu, container, false);

        initView(rootView);

        return rootView;
    }


    private void initView(View rootView) {

    }

}
