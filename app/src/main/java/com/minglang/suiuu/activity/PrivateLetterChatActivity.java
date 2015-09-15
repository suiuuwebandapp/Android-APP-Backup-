package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.koushikdutta.WebSocketClient;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.PrivateLetterChatAdapter;
import com.minglang.suiuu.application.SuiuuApplication;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.entity.PrivateChat;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpNewServicePath;
import com.minglang.suiuu.utils.OkHttpManager;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;

public class PrivateLetterChatActivity extends BaseAppCompatActivity {

    private static final String TAG = PrivateLetterChatActivity.class.getSimpleName();

    private static final String RELATE_ID = "relateId";

    private static final String R_USER_SIGN = "rUserSign";

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

    @BindColor(R.color.white)
    int titleColor;

    @Bind(R.id.private_letter_details_tool_bar)
    Toolbar toolBar;

    @Bind(R.id.private_letter_details_recycler_view)
    RecyclerView letterDetailsRecyclerView;

    @Bind(R.id.private_letter_details_send)
    ImageView sendMessage;

    @Bind(R.id.private_letter_details_input)
    EditText inputMessage;

    private Context context;

    private ProgressDialog progressDialog;

    private List<PrivateChat> listAll = new ArrayList<>();

    private PrivateLetterChatAdapter adapter;

    private WebSocketClient webSocketClient;

    private String relateId;

    private LocalBroadcastManager localBroadcastManager;

    private OnConnectBroadcast onConnectBroadcast;

    private OnStringBroadcast onStringBroadcast;

    private OnByteBroadcast onByteBroadcast;

    private OnDisconnectBroadcast onDisconnectBroadcast;

    private OnErrorBroadcast onErrorBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_letter_chat);

        relateId = getIntent().getStringExtra(RELATE_ID);

        ButterKnife.bind(this);
        initView();
        initLocalBroadcast();
        viewAction();
        sendRequest();
    }

    private void initView() {
        toolBar.setTitleTextColor(titleColor);
        setSupportActionBar(toolBar);

        context = this;

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(DialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        adapter = new PrivateLetterChatAdapter(this, listAll);
        letterDetailsRecyclerView.setAdapter(adapter);

        webSocketClient = SuiuuApplication.getWebSocketClient();

        sendMessage.setEnabled(false);

        try {
            token = URLEncoder.encode(SuiuuInfo.ReadAppTimeSign(context), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void initLocalBroadcast() {
        localBroadcastManager = LocalBroadcastManager.getInstance(context);

        onConnectBroadcast = new OnConnectBroadcast();
        IntentFilter onConnectFilter = new IntentFilter(SuiuuApplication.CONNECT);
        localBroadcastManager.registerReceiver(onConnectBroadcast, onConnectFilter);

        onStringBroadcast = new OnStringBroadcast();
        IntentFilter onStringFiler = new IntentFilter(SuiuuApplication.STRING_MESSAGE);
        localBroadcastManager.registerReceiver(onStringBroadcast, onStringFiler);

        onByteBroadcast = new OnByteBroadcast();
        IntentFilter onByteFilter = new IntentFilter(SuiuuApplication.BYTE_MESSAGE);
        localBroadcastManager.registerReceiver(onByteBroadcast, onByteFilter);

        onDisconnectBroadcast = new OnDisconnectBroadcast();
        IntentFilter onDisconnectFilter = new IntentFilter(SuiuuApplication.DISCONNECT);
        localBroadcastManager.registerReceiver(onDisconnectBroadcast, onDisconnectFilter);

        onErrorBroadcast = new OnErrorBroadcast();
        IntentFilter onErrorFilter = new IntentFilter(SuiuuApplication.ERROR);
        localBroadcastManager.registerReceiver(onErrorBroadcast, onErrorFilter);
    }

    private void viewAction() {

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        inputMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    sendMessage.setEnabled(false);
                    sendMessage.setImageResource(R.drawable.icon_send_unable);
                } else {
                    sendMessage.setEnabled(true);
                    sendMessage.setImageResource(R.drawable.icon_send_able);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void sendRequest() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }

        String[] keyArray = new String[]{R_USER_SIGN, TOKEN};
        String[] valueArray = new String[]{relateId, token};

        String url = addUrlAndParams(HttpNewServicePath.getPrivateLetterInfoPath, keyArray, valueArray);

        try {
            OkHttpManager.onGetAsynRequest(url, new OkHttpManager.ResultCallback<String>() {

                @Override
                public void onResponse(String response) {
                    DeBugLog.i(TAG, "response:" + response);
                }

                @Override
                public void onError(Request request, Exception e) {
                    DeBugLog.e(TAG, "Error:" + e.getMessage());
                    hideDialog();
                }

                @Override
                public void onFinish() {
                    hideDialog();
                }

            });
        } catch (IOException e) {
            e.printStackTrace();
            hideDialog();
        }
    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        localBroadcastManager.unregisterReceiver(onConnectBroadcast);
        localBroadcastManager.unregisterReceiver(onStringBroadcast);
        localBroadcastManager.unregisterReceiver(onByteBroadcast);
        localBroadcastManager.unregisterReceiver(onDisconnectBroadcast);
        localBroadcastManager.unregisterReceiver(onErrorBroadcast);
    }

    private class OnConnectBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String str = intent.getStringExtra(SuiuuApplication.CONNECT);
            DeBugLog.i(TAG, "onConnect()" + str);
        }
    }

    private class OnStringBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

    private class OnByteBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

    private class OnDisconnectBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

    private class OnErrorBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

}
