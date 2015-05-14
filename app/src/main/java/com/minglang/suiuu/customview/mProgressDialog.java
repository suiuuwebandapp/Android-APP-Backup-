package com.minglang.suiuu.customview;


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

public class mProgressDialog {
	
	private static Dialog loadingDialog;

	/**
	 * 进度条构造
	 */
	public mProgressDialog() {
		
	}
	
	/**
	 * 进度条构造
	 * @param context
	 */
	public mProgressDialog(Context context) {
		createLoadingDialog(context, "");
	}
	
	/**
	 * 得到自定义的progressDialog
	 * @param context
	 * @param msg
	 * @return
	 */
	public void createLoadingDialog(Context context, String msg) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.progressdialog, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		// main.xml中的ImageView
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		// 加载动画
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				context, R.anim.loading_animation);
		// 使用ImageView显示动画
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		tipTextView.setText(msg);// 设置加载信息

		loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

//		loadingDialog.setCancelable(false);// 不可以用“返回键”取消
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
        loadingDialog.setCancelable(false);
	}
	
	/**
	 * 显示loadingDialog
	 */
	public void showDialog(){
        try {
            if (loadingDialog != null) {
                loadingDialog.show();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 关闭loadingDialog
	 */
	public void dismissDialog(){
		if(loadingDialog != null){
			loadingDialog.dismiss();
		}
	}
    public boolean isShow() {
        if(loadingDialog.isShowing()) {
            return true;
        }else {
            return false;
        }
    }
}