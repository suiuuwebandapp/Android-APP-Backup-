package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.PersonalTripImageAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.entity.UserTravel;
import com.minglang.suiuu.entity.UserTravel.UserTravelData.UserTravelItemData;
import com.minglang.suiuu.interfaces.RecyclerViewOnItemClickListener;
import com.minglang.suiuu.interfaces.RecyclerViewOnItemLongClickListener;
import com.minglang.suiuu.utils.AppUtils;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

public class PersonalTripImageActivity extends BaseAppCompatActivity {

    private static final String TAG = PersonalTripImageActivity.class.getSimpleName();

    private static final String USER_SIGN = "userSign";

    private static final String PAGE = "page";
    private static final String NUMBER = "number";

    private static final String STATUS = "status";
    private static final String DATA = "data";

    private static final String ID = "id";

    @BindColor(R.color.white)
    int titleColor;

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

    @BindString(R.string.ConfirmDelete)
    String ConfirmDelete;

    @Bind(R.id.personal_trip_gallery_tool_bar)
    Toolbar toolBar;

    @Bind(R.id.personal_trip_gallery_head_frame)
    PtrClassicFrameLayout frame;

    @Bind(R.id.personal_trip_gallery_recycler_view)
    RecyclerView recyclerView;

    private boolean isPullToRefresh = true;

    private int page = 1;

    private Context context;

    private ProgressDialog progressDialog;

    private PersonalTripImageAdapter adapter;

    private GridLayoutManager layoutManager;

    private List<UserTravelItemData> listAll = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_trip_gallery);
        ButterKnife.bind(this);
        initView();
        viewAction();
        sendRequest(page);
    }

    private void initView() {
        userSign = getIntent().getStringExtra(USER_SIGN);
        verification = SuiuuInfo.ReadVerification(this);
        token = SuiuuInfo.ReadAppTimeSign(this);

        context = this;

        toolBar.setTitleTextColor(titleColor);
        setSupportActionBar(toolBar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(DialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        int paddingParams = AppUtils.dip2px(15, this);

        MaterialHeader header = new MaterialHeader(this);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, paddingParams, 0, paddingParams);
        header.setPtrFrameLayout(frame);

        frame.setHeaderView(header);
        frame.addPtrUIHandler(header);
        frame.setPinContent(true);

        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PersonalTripImageAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void viewAction() {

        frame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view1) {
                return PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, recyclerView, view1);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                page = 1;
                isPullToRefresh = false;
                sendRequest(page);
            }

        });

        adapter.setOnItemClickListener(new RecyclerViewOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String tripId = listAll.get(position).getId();
                Intent intent = new Intent(context, TripImageDetailsActivity.class);
                intent.putExtra(ID, tripId);
                startActivity(intent);
            }
        });

        adapter.setOnItemLongClickListener(new RecyclerViewOnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                new AlertDialog.Builder(context)
                        .setMessage(ConfirmDelete)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null)
                        .create().show();
            }
        });

    }

    private void sendRequest(int page) {
        if (isPullToRefresh) {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        String[] keyArray = new String[]{HttpNewServicePath.key, PAGE, NUMBER, USER_SIGN, TOKEN};
        String[] valueArray = new String[]{verification, String.valueOf(page), String.valueOf(10), userSign, token};
        String url = addUrlAndParams(HttpNewServicePath.getPersonalTripDataPath, keyArray, valueArray);
        L.i(TAG, "指定用户旅途请求URL:" + url);
        try {
            OkHttpManager.onGetAsynRequest(url, new PersonalTripGalleryCallback());
        } catch (IOException e) {
            e.printStackTrace();
            hideDialog();
            failureLessPage();
            Toast.makeText(this, NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 隐藏进度框
     */
    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        frame.refreshComplete();
    }

    /**
     * 请求失败,页码减1
     */
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

    /**
     * 绑定数据
     *
     * @param str JSON字符串
     */
    private void bindData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            failureLessPage();
            Toast.makeText(this, NoData, Toast.LENGTH_SHORT).show();
        } else {
            try {
                UserTravel userTravel = JsonUtils.getInstance().fromJSON(UserTravel.class, str);
                if (userTravel != null) {
                    List<UserTravelItemData> list = userTravel.getData().getData();
                    if (list != null && list.size() > 0) {
                        clearDataList();
                        listAll.addAll(list);
                        adapter.setList(listAll);
                    } else {
                        failureLessPage();
                        Toast.makeText(this, NoData, Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                L.e(TAG, "数据解析异常:" + e.getMessage());
                failureLessPage();
                try {
                    JSONObject object = new JSONObject(str);
                    String status = object.getString(STATUS);
                    if (status.equals("-1")) {
                        Toast.makeText(this, SystemException, Toast.LENGTH_SHORT).show();
                    } else if (status.equals("-2")) {
                        Toast.makeText(this, object.getString(DATA), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                    Toast.makeText(this, DataError, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class PersonalTripGalleryCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            L.i(TAG, "指定用户返回的旅图数据:" + response);
            hideDialog();
            bindData2View(response);
        }

        @Override
        public void onError(Request request, Exception e) {
            hideDialog();
            failureLessPage();
            Toast.makeText(PersonalTripImageActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

}