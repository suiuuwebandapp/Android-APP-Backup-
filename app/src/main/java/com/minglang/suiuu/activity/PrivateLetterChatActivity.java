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

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.WebSocketClient;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.PrivateLetterChatAdapter;
import com.minglang.suiuu.application.SuiuuApplication;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.entity.PrivateChat;
import com.minglang.suiuu.entity.PrivateChat.PrivateChatData;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.StatusBarCompat;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
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

    @Bind(R.id.private_letter_details_tool_bar)
    Toolbar toolBar;

    @Bind(R.id.private_letter_details_recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.private_letter_details_send)
    ImageView sendMessage;

    @Bind(R.id.private_letter_details_input)
    EditText inputMessageView;

    private Context context;

    private ProgressDialog progressDialog;

    private List<PrivateChatData> listAll = new ArrayList<>();

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

    private SimpleDateFormat sdf;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_letter_chat);

        relateId = getIntent().getStringExtra(RELATE_ID);
        headImagePath = getIntent().getStringExtra(HEAD_IMG);

        StatusBarCompat.compat(this);
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
        setSupportActionBar(toolBar);

        context = this;

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(DialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        adapter = new PrivateLetterChatAdapter(this, listAll);
        adapter.setOtherHeadImagePath(headImagePath);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        webSocketClient = SuiuuApplication.getWebSocketClient();

        sendMessage.setEnabled(false);

        try {
            token = URLEncoder.encode(SuiuuInfo.ReadAppTimeSign(context), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        userSign = SuiuuInfo.ReadUserSign(context);

        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    }

    /**
     * 初始化LocalBroadcast
     */
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
                    L.i(TAG, "Send Message:" + message);
                    addSendMessage();
                    scrollToBottom();
                    webSocketClient.send(message);
                    inputMessageView.setText("");
                }
            }
        });

        inputMessageView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    inputString= v.getText().toString().trim();
                    if (TextUtils.isEmpty(inputString)) {
                        Toast.makeText(context, "请输入信息", Toast.LENGTH_SHORT).show();
                    }else {
                        String message = buildSendMessage();
                        L.i(TAG, "Send Message:" + message);
                        addSendMessage();
                        scrollToBottom();
                        webSocketClient.send(message);
                        inputMessageView.setText("");
                    }
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

    /**
     * 构建发送信息
     *
     * @return 要发送的信息
     */
    private String buildSendMessage() {
        JSONObject object = new JSONObject();
        try {
            object.put(TYPE, SAY);
            object.put(CLIENT_ID, relateId);
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
    private void addSendMessage() {
        PrivateChat.PrivateChatData privateChatData = new PrivateChat.PrivateChatData();
        privateChatData.setContent(inputString);
        privateChatData.setSenderId(userSign);
        privateChatData.setIsRead("");
        privateChatData.setIsShield("");
        privateChatData.setReadTime("");
        privateChatData.setMessageId("");
        privateChatData.setReceiveId("");
        privateChatData.setSendTime(sdf.format(new Date(System.currentTimeMillis())));
        privateChatData.setSessionkey("");
        privateChatData.setUrl("");

        listAll.add(privateChatData);
        adapter.notifyItemInserted(listAll.size() - 1);
    }

    /**
     * 添加接收到的消息
     *
     * @param str 接受到的消息字符串
     */
    private void addReceiveMessage(String str) {
        if (TextUtils.isEmpty(str)) {
            L.e(TAG, "返回数据为Null");
        } else try {
            L.i(TAG, "接收到的消息:" + str);
            parseUnderLineToHumpName(str);
            PrivateChat.PrivateChatData data = parseUnderLineToHumpName(str);
            if (data != null && !TextUtils.isEmpty(data.getContent())) {
                String contentStr = data.getContent();
                L.i(TAG, "Content:" + contentStr);
                listAll.add(data);
                adapter.notifyItemInserted(listAll.size() - 1);
            }
        } catch (Exception e) {
            try {
                JSONObject object = new JSONObject(str);
                L.i(TAG, "Type:" + object.get("type"));
            } catch (JSONException e1) {
                e1.printStackTrace();
                L.e(TAG, "数据解析失败:" + e.getMessage());
            }
        }
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
                    L.i(TAG, "response:" + response);
                    if (TextUtils.isEmpty(response)) {
                        Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
                    } else try {
                        PrivateChat privateChat = JsonUtils.getInstance().fromJSON(PrivateChat.class, response);
                        List<PrivateChat.PrivateChatData> list = privateChat.getData();
                        if (list != null && list.size() > 0) {
                            listAll.addAll(list);
                            adapter.setList(listAll);
                            scrollToBottom();
                        } else {
                            Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        L.e(TAG, "Exception:" + e.getMessage());
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
                    L.e(TAG, "Error:" + e.getMessage());
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

    private PrivateChat.PrivateChatData parseUnderLineToHumpName(String json) {
        JsonParser jp = new JsonParser();
        JsonObject je = jp.parse(json).getAsJsonObject();

        String senderId = je.get("sender_id").getAsString();
        String senderHeadImg = je.get("sender_HeadImg").getAsString();
        String receiveId = je.get("receive_id").getAsString();
        String content = je.get("content").getAsString();
        String sessionKey = je.get("session_key").getAsString();
        String time = je.get("time").getAsString();

        String type = je.get("type").getAsString();
        String senderName = je.get("sender_name").getAsString();

        PrivateChat.PrivateChatData data = new PrivateChat.PrivateChatData();
        data.setSessionkey(sessionKey);
        data.setContent(content);
        data.setSenderId(senderId);
        data.setReceiveId(receiveId);
        data.setUrl(senderHeadImg);
        data.setReadTime(time);

        return data;
    }

    /**
     * 滚动到最底部
     */
    private void scrollToBottom() {
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
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
            L.i(TAG, "onConnect()" + str);
        }
    }

    private class OnStringBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String str = intent.getStringExtra(SuiuuApplication.STRING_MESSAGE);
            addReceiveMessage(str);
            scrollToBottom();
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