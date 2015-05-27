package com.minglang.suiuu.customview;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.chat.activity.ShowBigImage;

public class TextProgressDialog {

    private static Dialog loadingDialog;
    private TextView tipTextView;

    private Context context;

    /**
     * 进度条构造
     *
     * @param showBigImage
     * @param s
     */
    public TextProgressDialog(ShowBigImage showBigImage, String s) {
        createLoadingDialog(showBigImage, s);
    }

    /**
     * 进度条构造
     *
     * @param context
     */
    public TextProgressDialog(Context context) {
        this.context = context;
        createLoadingDialog(context, "");
    }

    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @param msg
     * @return
     */
    @SuppressLint("InflateParams")
    public void createLoadingDialog(@Nullable Context context, String msg) {
        if (context != null) {
            this.context = context;
        }

        LayoutInflater inflater = LayoutInflater.from(this.context);
        View v = inflater.inflate(R.layout.progressdialog, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                this.context, R.anim.loading_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        tipTextView.setText(msg);// 设置加载信息

        loadingDialog = new Dialog(this.context, R.style.loading_dialog);// 创建自定义样式dialog

        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        loadingDialog.setCancelable(false);
        loadingDialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 显示loadingDialog
     */
    public void showDialog() {
        try {
            if (loadingDialog != null) {
                loadingDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭loadingDialog
     */
    public void dismissDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    public boolean isShow() {
        return loadingDialog.isShowing();
    }

    public void setMessage(String message) {
        if (loadingDialog.isShowing()) {
            tipTextView.setText(message);
        }
    }
}