package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.WebSocketClient;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.PrivateLetterChatAdapter;
import com.minglang.suiuu.application.SuiuuApplication;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.entity.PrivateChat;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpNewServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.OkHttpManager;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

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

    private static final String TYPE = "type";
    private static final String SAY = "say";

    private static final String CLIENT_ID = "to_client_id";
    private static final String CONTENT = "content";

    private static final String HEAD_IMAGE_PATH = "headImagePath";

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
    EditText inputMessageView;

    private Context context;

    private ProgressDialog progressDialog;

    private List<PrivateChat.PrivateChatData> listAll = new ArrayList<>();

    private PrivateLetterChatAdapter adapter;

    private String relateId;

    private String headImagePath;

    private String inputString;

    private static WebSocketClient webSocketClient;

    private LocalBroadcastManager localBroadcastManager;

    private OnConnectBroadcast onConnectBroadcast;

    private OnStringBroadcast onStringBroadcast;

    private OnByteBroadcast onByteBroadcast;

    private OnDisconnectBroadcast onDisconnectBroadcast;

    private OnErrorBroadcast onErrorBroadcast;

    //private Socket mSocket;

    //    private Emitter.Listener newMessageListener = new Emitter.Listener() {
    //
    //        @Override
    //        public void call(final Object... args) {
    //            runOnUiThread(new Runnable() {
    //                @Override
    //                public void run() {
    //                    DeBugLog.i(TAG, "args:" + args[0].toString());
    //                }
    //            });
    //        }
    //
    //    };

    //    {
    //        mSocket = SuiuuApplication.getSocketClient();
    //    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_letter_chat);

        relateId = getIntent().getStringExtra(RELATE_ID);
        headImagePath = getIntent().getStringExtra(HEAD_IMAGE_PATH);

        ButterKnife.bind(this);
        initView();
        initLocalBroadcast();
        viewAction();
        sendRequest();
    }

    /**
     * 初始化方法
     */
    private void initView() {
        toolBar.setTitleTextColor(titleColor);
        setSupportActionBar(toolBar);

        context = this;

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(DialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        adapter = new PrivateLetterChatAdapter(this, listAll);
        adapter.setOtherHeadImagePath(headImagePath);

        letterDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        letterDetailsRecyclerView.setAdapter(adapter);

        webSocketClient = SuiuuApplication.getWebSocketClient();

        //mSocket.on("new message", newMessageListener);

        sendMessage.setEnabled(false);

        try {
            token = URLEncoder.encode(SuiuuInfo.ReadAppTimeSign(context), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        verification = SuiuuInfo.ReadVerification(context);
        DeBugLog.i(TAG, "verification:" + verification);
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

    /**
     * 控件动作
     */
    private void viewAction() {

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(inputString)) {
                    Toast.makeText(context, "请输入信息", Toast.LENGTH_SHORT).show();
                } else {
                    String message = buildSendMessage();
                    addMessage();
                    webSocketClient.send(message);
                }
            }
        });

        inputMessageView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == R.id.private_letter_details_send || actionId == EditorInfo.IME_NULL) {
                    //attemptSendMessage();
                    DeBugLog.i(TAG, "inputString:" + inputString);
                    return true;
                }
                return false;
            }
        });

        inputMessageView.addTextChangedListener(new TextWatcher() {

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
                inputString = s.toString().trim();
            }

        });

    }

    //    /**
    //     * 发送消息
    //     */
    //    private void attemptSendMessage() {
    //        boolean isConnected = !mSocket.connected();
    //        DeBugLog.i(TAG, "isConnected:" + isConnected);
    //        if (isConnected) return;
    //
    //        if (TextUtils.isEmpty(inputString)) {
    //            Toast.makeText(context, "请输入信息", Toast.LENGTH_SHORT).show();
    //        } else {
    //            String message = buildSendMessage();
    //            DeBugLog.i(TAG, "message:" + message);
    //            addMessage();
    //            mSocket.emit("new message", inputMessageView);
    //        }
    //    }

    /**
     * 构建发送信息
     *
     * @return 要发送的信息
     */
    private String buildSendMessage() {
        JSONObject object = new JSONObject();
        try {
            object.put(TYPE, SAY);
            object.put(CLIENT_ID, verification);
            object.put(CONTENT, inputString);
            return object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 添加消息到列表
     */
    private void addMessage() {
        PrivateChat.PrivateChatData privateChatData = new PrivateChat.PrivateChatData();
        privateChatData.setContent(inputString);
        privateChatData.setSenderId(verification);
        privateChatData.setIsRead("");
        privateChatData.setIsShield("");
        privateChatData.setReadTime("");
        privateChatData.setMessageId("");
        privateChatData.setReceiveId("");
        privateChatData.setSendTime("");
        privateChatData.setSessionkey("");
        privateChatData.setUrl("");

        listAll.add(privateChatData);
        adapter.notifyItemInserted(listAll.size() - 1);
    }

    private void addReceiveMessage(String str) {
        DeBugLog.i(TAG, "接收到的消息:" + str);
    }

    /**
     * 获取先前的聊天记录
     */
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
                    if (TextUtils.isEmpty(response)) {
                        Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
                    } else try {
                        PrivateChat privateChat = JsonUtils.getInstance().fromJSON(PrivateChat.class, response);
                        List<PrivateChat.PrivateChatData> list = privateChat.getData();
                        if (list != null && list.size() > 0) {
                            listAll.addAll(list);
                            adapter.setList(listAll);
                        } else {
                            Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        DeBugLog.e(TAG, "Exception:" + e.getMessage());
                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.getString(STATUS);
                            switch (status) {
                                case "-1":
                                    Toast.makeText(context, SystemException, Toast.LENGTH_SHORT).show();
                                    break;
                                case "-2":
                                    Toast.makeText(context, object.getString(DATA), Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                            Toast.makeText(context, DataError, Toast.LENGTH_SHORT).show();
                        }
                    }
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

    /**
     * 隐藏Dialog
     */
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

        //mSocket.disconnect();
        //mSocket.off("new message", newMessageListener);

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
            String str = intent.getStringExtra(SuiuuApplication.STRING_MESSAGE);
            addReceiveMessage(str);
        }

    }

    private class OnByteBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String str = intent.getStringExtra(SuiuuApplication.BYTE_MESSAGE);
            addReceiveMessage(str);
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