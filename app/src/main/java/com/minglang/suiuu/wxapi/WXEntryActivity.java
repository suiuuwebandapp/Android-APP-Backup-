package com.minglang.suiuu.wxapi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.LoginActivity;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.wechat.WeChatConstant;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = WXEntryActivity.class.getSimpleName();

    private Context context = WXEntryActivity.this;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);

        IWXAPI weChatApi = WXAPIFactory.createWXAPI(this, WeChatConstant.APP_ID, false);
        weChatApi.handleIntent(getIntent(), this);

        dialog = new ProgressDialog(this);
        dialog.setMessage("正在请求数据，请稍候...");
        dialog.setCanceledOnTouchOutside(false);
    }

    private void WeChat2Login(String code) {
        Intent intent = new Intent(WXEntryActivity.this, LoginActivity.class);
        intent.putExtra("TAG", TAG);
        intent.putExtra("code", code);
        startActivity(intent);
        Log.i(TAG, "*************" + TAG);
        setResult(100, intent);
    }


    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq baseReq) {
        Log.d(TAG, "微信发送请求到第三方应用时，会回调到该方法");
        Log.d(TAG, "onReq().baseReq:" + baseReq.toString());
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp baseResp) {

        if (dialog != null) {
            dialog.show();
        }

        int errCode = baseResp.errCode;

        Log.i(TAG, "baseResp:" + errCode);

        switch (errCode) {

            case BaseResp.ErrCode.ERR_OK:
                String code = ((SendAuth.Resp) baseResp).code;
//                WeChat2Login(code);
                SuiuuInfo.WriteWxCode(context, code);
                finish();
                break;

            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                Toast.makeText(context, "用户取消授权", Toast.LENGTH_SHORT).show();
                WeChat2Login("");
                break;

            case BaseResp.ErrCode.ERR_UNSUPPORT:
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                Toast.makeText(context, "不支持该授权方式！", Toast.LENGTH_SHORT).show();
                WeChat2Login("");
                break;

            case BaseResp.ErrCode.ERR_SENT_FAILED:
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                Toast.makeText(context, "授权失败！", Toast.LENGTH_SHORT).show();
                WeChat2Login("");
                break;

            default:
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                Toast.makeText(context, "发生未知错误！", Toast.LENGTH_SHORT).show();
                WeChat2Login("");
                break;
        }
    }

}
