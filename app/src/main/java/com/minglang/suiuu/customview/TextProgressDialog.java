package com.minglang.suiuu.customview;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minglang.suiuu.R;

public class TextProgressDialog {

    private static Dialog loadingDialog;
    private TextView tipTextView;

    private Context context;

    /**
     * 进度条构造
     *
     * @param context 上下文对象
     */
    public TextProgressDialog(Context context) {
        this.context = context;
        createLoadingDialog("");
    }

    /**
     * 得到自定义的progressDialog
     */
    @SuppressLint("InflateParams")
    private void createLoadingDialog(String msg) {
        View view = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null);// 得到加载view

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.dialog_view);// 加载布局

        ImageView spaceshipImage = (ImageView) view.findViewById(R.id.img);
        tipTextView = (TextView) view.findViewById(R.id.tipTextView);// 提示文字

        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.loading_animation);
        spaceshipImage.startAnimation(hyperspaceJumpAnimation); // 使用ImageView显示动画

        tipTextView.setText(msg);// 设置加载信息

        loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

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