package com.minglang.suiuu.fragment.main;

import com.minglang.suiuu.R;
import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 路线页面
 */
public class RouteFragment extends Fragment {
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_route, null);
		
		return rootView;
	}

}
