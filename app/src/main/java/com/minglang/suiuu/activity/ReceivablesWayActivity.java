package com.minglang.suiuu.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.ReceivablesWayAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.entity.AccountInfo;
import com.minglang.suiuu.entity.AccountInfo.AccountInfoData;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpNewServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.OkHttpManager;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 管理收款方式页面
 */
public class ReceivablesWayActivity extends BaseAppCompatActivity {

    private static final String TAG = ReceivablesWayActivity.class.getSimpleName();

    private static final String KEY = "key";

    private static final String ALIPAY = "alipay";
    private static final String WECHAT = "weChat";

    @BindString(R.string.load_wait)
    String dialogMessage;

    @BindString(R.string.NetworkAnomaly)
    String NetworkError;

    @BindString(R.string.NoData)
    String DataNull;

    @BindString(R.string.DataError)
    String DataError;

    @BindColor(R.color.white)
    int titleColor;

    @Bind(R.id.receivables_way_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.receivables_way_list_view)
    ListView receivablesWayListView;

    @Bind(R.id.receivables_way_hint_layout)
    RelativeLayout relativeLayout;

    private PopupWindow addPopupWindow;

    private View addRootView;

    private Button alipayButton;

    private Button weChatButton;

    private PopupWindow deletePopupWindow;

    private View deleteRootView;

    private Button okButton;

    private Button cancelButton;

    /**
     * 按钮点击选择
     */
    private boolean isSelected = true;

    private ProgressDialog progressDialog;

    private List<AccountInfoData> listAll = new ArrayList<>();

    private ReceivablesWayAdapter adapter;

    private long index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receivables_way);
        ButterKnife.bind(this);
        initView();
        viewAction();
    }

    private void initView() {
        toolbar.setTitleTextColor(titleColor);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(dialogMessage);
        progressDialog.setCanceledOnTouchOutside(false);

        initPopupWindow();

        adapter = new ReceivablesWayAdapter(this, listAll, R.layout.item_receivables_way);
        receivablesWayListView.setAdapter(adapter);
    }

    @SuppressLint("InflateParams")
    @SuppressWarnings("deprecation")
    private void initPopupWindow() {
        addRootView = LayoutInflater.from(this).inflate(R.layout.popup_add_receivables, null);
        alipayButton = (Button) addRootView.findViewById(R.id.popup_add_receivables_alipay);
        weChatButton = (Button) addRootView.findViewById(R.id.popup_add_receivables_wechat);

        addPopupWindow = new PopupWindow(addRootView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        addPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        addPopupWindow.setAnimationStyle(R.style.time_popup_window_anim_style);
        addPopupWindow.setOutsideTouchable(true);

        deleteRootView = LayoutInflater.from(this).inflate(R.layout.popup_delete_receivables, null);
        okButton = (Button) deleteRootView.findViewById(R.id.popup_delete_receivables_ok);
        cancelButton = (Button) deleteRootView.findViewById(R.id.popup_delete_receivables_cancel);

        deletePopupWindow = new PopupWindow(deleteRootView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        deletePopupWindow.setBackgroundDrawable(new BitmapDrawable());
        deletePopupWindow.setAnimationStyle(R.style.time_popup_window_anim_style);
        deletePopupWindow.setOutsideTouchable(true);
    }

    private void viewAction() {

        alipayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelected = true;
                addPopupWindow.dismiss();
            }
        });

        weChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelected = false;
                addPopupWindow.dismiss();
            }
        });

        addPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Intent intent = new Intent(ReceivablesWayActivity.this, AddReceivablesWayActivity.class);
                if (isSelected) {
                    intent.putExtra(KEY, ALIPAY);
                } else {
                    intent.putExtra(KEY, WECHAT);
                }
                startActivity(intent);
            }
        });

        adapter.setOnDeleteReceivablesItemListener(new ReceivablesWayAdapter.OnDeleteReceivablesItemListener() {
            @Override
            public void onDeleteReceivablesItem(long position) {
                index = position;
                deletePopupWindow.showAtLocation(deleteRootView, Gravity.BOTTOM, 0, 0);
            }
        });

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserBindAccountList4Service();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listAll.remove(Long.bitCount(index));
                adapter.notifyDataSetChanged();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePopupWindow.dismiss();
            }
        });

    }

    private void getUserBindAccountList4Service() {
        if (progressDialog != null && !progressDialog.isShowing())
            progressDialog.show();

        relativeLayout.setVisibility(View.GONE);

        try {
            OkHttpManager.onGetAsynRequest(HttpNewServicePath.getUserBindAccountListData, new ReceivablesWayResultCallback());
        } catch (IOException e) {
            e.printStackTrace();
            hideDialog();
            relativeLayout.setVisibility(View.VISIBLE);
            Toast.makeText(ReceivablesWayActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private void bindData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            relativeLayout.setVisibility(View.VISIBLE);
            Toast.makeText(ReceivablesWayActivity.this, DataNull, Toast.LENGTH_SHORT).show();
        } else {
            try {
                AccountInfo accountInfo = JsonUtils.getInstance().fromJSON(AccountInfo.class, str);
                List<AccountInfoData> list = accountInfo.getData();
                if (list != null && list.size() > 0) {
                    listAll.addAll(list);
                    adapter.setList(listAll);
                } else {
                    relativeLayout.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "数据解析错误:" + e.getMessage());
                relativeLayout.setVisibility(View.VISIBLE);
                Toast.makeText(ReceivablesWayActivity.this, DataError, Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserBindAccountList4Service();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_receivables_way, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;

            case R.id.add_receivables:
                addPopupWindow.showAtLocation(addRootView, Gravity.BOTTOM, 0, 0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ReceivablesWayResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            DeBugLog.i(TAG, "返回的数据:" + response);
            hideDialog();
            bindData2View(response);
        }

        @Override
        public void onError(Request request, Exception e) {
            DeBugLog.e(TAG, "Exception:" + e.getMessage());
            hideDialog();
            relativeLayout.setVisibility(View.VISIBLE);
            Toast.makeText(ReceivablesWayActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

}