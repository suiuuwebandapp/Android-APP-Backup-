package com.minglang.suiuu.fragment.remind;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.TripImageDetailsActivity;
import com.minglang.suiuu.adapter.MsgTripImageAdapter;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.entity.MsgTripImage;
import com.minglang.suiuu.entity.MsgTripImage.MsgTripImageData.MsgTripImageItemData;
import com.minglang.suiuu.utils.AppUtils;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * 新@页面
 * <p/>
 * Use the {@link MsgTripImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MsgTripImageFragment extends BaseFragment {

    private static final String TAG = MsgTripImageFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private static final String PAGE = "page";
    private static final String NUMBER = "number";

    private static final String STATUS = "status";
    private static final String DATA = "data";

    private String userSign;
    private String verification;

    @BindString(R.string.load_wait)
    String DialogMsg;

    @BindString(R.string.NoData)
    String NoData;

    @BindString(R.string.DataError)
    String DataError;

    @BindString(R.string.NetworkAnomaly)
    String NetworkError;

    @BindString(R.string.SystemException)
    String SystemException;

    @BindString(R.string.LoginInvalid)
    String LoginInvalid;

    @Bind(R.id.msg_trip_image_fragment_head_frame)
    PtrClassicFrameLayout mPtrFrame;

    @Bind(R.id.msg_trip_image_list_view)
    ListView msgTripImageList;

    private List<MsgTripImageItemData> listAll = new ArrayList<>();

    private ProgressDialog progressDialog;

    private MsgTripImageAdapter adapter;

    private int page = 1;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MsgTripImageFragment.
     */
    public static MsgTripImageFragment newInstance(String param1, String param2, String param3) {
        MsgTripImageFragment fragment = new MsgTripImageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    public MsgTripImageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userSign = getArguments().getString(ARG_PARAM1);
            verification = getArguments().getString(ARG_PARAM2);
            token = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_msg_trip_image, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        viewAction();
        getData4Service(page);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 初始化方法
     */
    private void initView() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(DialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        int paddingParams = AppUtils.dip2px(15, getActivity());

        MaterialHeader header = new MaterialHeader(getActivity());
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, paddingParams, 0, paddingParams);
        header.setPtrFrameLayout(mPtrFrame);

        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);
        mPtrFrame.setPinContent(true);

        adapter = new MsgTripImageAdapter(getActivity(), listAll, R.layout.item_msg_trip_image);
        msgTripImageList.setAdapter(adapter);
    }

    private void viewAction() {

        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, msgTripImageList, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                page = 1;
                getData4Service(page);
            }

        });

        msgTripImageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String relativeId = listAll.get(position).getRelativeId();
                Intent intent = new Intent(getActivity(), TripImageDetailsActivity.class);
                intent.putExtra(ID, relativeId);
                startActivity(intent);
            }
        });

    }

    /**
     * 从网络获取数据
     */
    private void getData4Service(int page) {
        String[] keyArray = new String[]{HttpNewServicePath.key, PAGE, NUMBER, TOKEN};
        String[] valueArray = new String[]{verification, String.valueOf(page), String.valueOf(15), token};
        String url = addUrlAndParams(HttpNewServicePath.getTripImageMsgDataPath, keyArray, valueArray);
        L.i(TAG, "旅图信息请求URL:" + url);
        try {
            OkHttpManager.onGetAsynRequest(url, new MsgTripImageResultCallback());
        } catch (IOException e) {
            L.e(TAG, "旅图消息请求异常:" + e.getMessage());
            hideDialog();
            failureLessPage();
            Toast.makeText(getActivity(), NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        mPtrFrame.refreshComplete();
    }

    private void failureLessPage() {
        if (page > 1) {
            page = page - 1;
        }
    }

    private void clearDataList() {
        if (page == 1) {
            if (listAll != null && listAll.size() > 0) {
                listAll.clear();
            }
        }
    }

    private void bindData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            failureLessPage();
            Toast.makeText(getActivity(), NoData, Toast.LENGTH_SHORT).show();
        } else try {
            JSONObject object = new JSONObject(str);
            String status = object.getString(STATUS);
            switch (status) {
                case "1":
                    MsgTripImage msgTripImage = JsonUtils.getInstance().fromJSON(MsgTripImage.class, str);
                    List<MsgTripImageItemData> list = msgTripImage.getData().getData();
                    if (list != null && list.size() > 0) {
                        clearDataList();
                        listAll.addAll(list);
                    } else {
                        failureLessPage();
                        Toast.makeText(getActivity(), NoData, Toast.LENGTH_SHORT).show();
                    }
                    adapter.setList(listAll);
                    break;

                case "-1":
                    Toast.makeText(getActivity(), SystemException, Toast.LENGTH_SHORT).show();
                    break;

                case "-2":
                    Toast.makeText(getActivity(), object.getString(DATA), Toast.LENGTH_SHORT).show();
                    break;

                case "-3":
                    Toast.makeText(getActivity(), LoginInvalid, Toast.LENGTH_SHORT).show();
                    ReturnLoginActivity(getActivity());
                    break;

                case "-4":
                    Toast.makeText(getActivity(), object.getString(DATA), Toast.LENGTH_SHORT).show();
                    break;

                default:
                    Toast.makeText(getActivity(), DataError, Toast.LENGTH_SHORT).show();
                    break;
            }
        } catch (Exception e) {
            L.e(TAG, "数据解析异常:" + e.getMessage());
            failureLessPage();
            Toast.makeText(getActivity(), DataError, Toast.LENGTH_SHORT).show();
        }
    }

    public String getUserSign() {
        return userSign;
    }

    private class MsgTripImageResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            L.i(TAG, "旅图返回的数据:" + response);
            bindData2View(response);
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "获取旅途消息失败:" + e.getMessage());
            failureLessPage();
            Toast.makeText(getActivity(), NetworkError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish() {
            hideDialog();
        }

    }

}