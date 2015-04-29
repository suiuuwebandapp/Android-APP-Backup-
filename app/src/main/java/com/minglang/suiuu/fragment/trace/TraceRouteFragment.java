package com.minglang.suiuu.fragment.trace;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.minglang.suiuu.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TraceRouteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TraceRouteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private GridView traceRouteGird;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TraceRouteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TraceRouteFragment newInstance(String param1, String param2) {
        TraceRouteFragment fragment = new TraceRouteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TraceRouteFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trace_route, container, false);

        initView(rootView);

        return rootView;
    }

    private void initView(View rootView) {
        traceRouteGird = (GridView) rootView.findViewById(R.id.TraceRouteGrid);
    }

}
