package com.minglang.suiuu.fragment.main;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.SuiuuDetailActivity;
import com.minglang.suiuu.adapter.ShowSuiuuAdapter;
import com.minglang.suiuu.entity.SuiuuDataList;
import com.minglang.suiuu.entity.SuiuuReturnDate;
import com.minglang.suiuu.utils.ConstantUtil;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtil;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInformation;

import java.util.List;

/**
 * 随游页面
 */
public class RouteFragment extends Fragment {
    private TextView titleInfo;
    private ListView lv_suiuu;
    private JsonUtil jsonUtil = JsonUtil.getInstance();
    private List<SuiuuDataList> suiuuDataList;
    private Dialog dialog;
    /**
     * @description PopupWindow
     */
    private PopupWindow popWindow;
    private TextView tv_country;
    private TextView tv_city;
    private TextView tv_type;
//    private ImageView iv_choice;

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_route, null);
        innitView(rootView);
        loadDate(null, null, null);
        RelativeLayout.LayoutParams paramTest = (RelativeLayout.LayoutParams) lv_suiuu.getLayoutParams();
        //paramTest.topMargin = ConstantUtil.topHeight;
        paramTest.setMargins(10, ConstantUtil.topHeight + 10, 10, 0);
        lv_suiuu.setLayoutParams(paramTest);
        viewAction();
        return rootView;
    }

    private void viewAction() {
        lv_suiuu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SuiuuDetailActivity.class);
                intent.putExtra("tripId", suiuuDataList.get(position).getTripId());
                startActivity(intent);
            }
        });

    }

    private void innitView(View rootView) {
//        iv_choice = (ImageView) rootView.findViewById(R.id.iv_choice);
        titleInfo = (TextView) rootView.findViewById(R.id.titleInfo);
        titleInfo.setVisibility(View.GONE);
        lv_suiuu = (ListView) rootView.findViewById(R.id.lv_suiuu);
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.progress_bar);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        initPopWindow(getActivity());


    }

    /**
     * 加载数据
     *
     * @param countryId
     * @param cityId
     * @param tags
     */
    private void loadDate(String countryId, String cityId, String tags) {
        dialog.show();
        String str = SuiuuInformation.ReadVerification(getActivity().getApplicationContext());
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, str);
        if (!TextUtils.isEmpty(countryId)) {
            params.addBodyParameter("countryId", countryId);
        }
        if (!TextUtils.isEmpty(cityId)) {
            params.addBodyParameter("cityId", cityId);
        }
        if (!TextUtils.isEmpty(tags)) {
            params.addBodyParameter("tag", tags);
        }

        SuHttpRequest suHttpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getSuiuuList, new getSuiuuDateCallBack());
        suHttpRequest.setParams(params);
        suHttpRequest.requestNetworkData();
    }

    /**
     * 获取随游列表的回调接口
     */
    class getSuiuuDateCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            dialog.dismiss();
            try {
                SuiuuReturnDate baseCollection = jsonUtil.fromJSON(SuiuuReturnDate.class, stringResponseInfo.result);
                if ("1".equals(baseCollection.getStatus())) {
                    suiuuDataList = baseCollection.getData();
                    lv_suiuu.setAdapter(new ShowSuiuuAdapter(getActivity().getApplicationContext(), suiuuDataList));
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "数据获取失败，请重试！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            dialog.dismiss();
            Toast.makeText(getActivity().getApplicationContext(), "数据获取失败，请重试！", Toast.LENGTH_SHORT).show();
        }
    }

    private void initPopWindow(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopWindow = inflater.inflate(R.layout.popuwindowforchoicesuiuu, null, false);
        popWindow = new PopupWindow(vPopWindow, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(false);
        tv_country = (TextView) vPopWindow.findViewById(R.id.tv_country);
        tv_city = (TextView) vPopWindow.findViewById(R.id.tv_city);
        tv_type = (TextView) vPopWindow.findViewById(R.id.tv_type);
        tv_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popWindow.dismiss();
                lv_suiuu.setClickable(true);

            }
        });

        tv_city.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popWindow.dismiss();
                lv_suiuu.setClickable(true);
            }
        });
    }

    private void showPopWindow(View parent) {
        int[] location = new int[2];
        parent.getLocationOnScreen(location);
        popWindow.showAtLocation(parent, Gravity.NO_GRAVITY, location[0] + parent.getWidth(), location[1] + parent.getHeight());
    }
}
