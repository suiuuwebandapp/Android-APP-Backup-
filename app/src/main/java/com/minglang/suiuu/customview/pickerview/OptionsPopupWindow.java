package com.minglang.suiuu.customview.pickerview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.minglang.suiuu.R;

import java.util.ArrayList;

/**
 * 选项选择器，可支持一二三级联动选择
 *
 * @author Sai
 */
public class OptionsPopupWindow extends PopupWindow implements OnClickListener {

    WheelOptions wheelOptions;

    private OnOptionsSelectListener optionsSelectListener;
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";

    public OptionsPopupWindow(Context context) {
        super(context);

        this.setWidth(LayoutParams.MATCH_PARENT);
        this.setHeight(LayoutParams.WRAP_CONTENT);
        this.setBackgroundDrawable(new BitmapDrawable());// 这样设置才能点击屏幕外dismiss窗口
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.time_popup_window_anim_style);

        LayoutInflater mLayoutInflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams")
        View rootView = mLayoutInflater.inflate(R.layout.popup_options, null);

        // -----确定和取消按钮
        View btnSubmit = rootView.findViewById(R.id.btnSubmit);
        btnSubmit.setTag(TAG_SUBMIT);

        View btnCancel = rootView.findViewById(R.id.btnCancel);
        btnCancel.setTag(TAG_CANCEL);

        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        // ----转轮
        final View optionsPicker = rootView.findViewById(R.id.options_picker);
        ScreenInfo screenInfo = new ScreenInfo((Activity) context);
        wheelOptions = new WheelOptions(optionsPicker);

        wheelOptions.screenHeight = screenInfo.getHeight();

        setContentView(rootView);
    }

    public void setPicker(ArrayList<String> optionsItems) {
        wheelOptions.setPicker(optionsItems, null, null, false);
    }

    public void setPicker(ArrayList<String> options1Items,
                          ArrayList<ArrayList<String>> options2Items, boolean linkage) {
        wheelOptions.setPicker(options1Items, options2Items, null, linkage);
    }

    public void setPicker(ArrayList<String> options1Items,
                          ArrayList<ArrayList<String>> options2Items,
                          ArrayList<ArrayList<ArrayList<String>>> options3Items,
                          boolean linkage) {
        wheelOptions.setPicker(options1Items, options2Items, options3Items,
                linkage);
    }

    /**
     * 设置选中的item位置
     *
     * @param option1 位置
     */
    public void setSelectOptions(int option1) {
        wheelOptions.setCurrentItems(option1, 0, 0);
    }

    /**
     * 设置选中的item位置
     *
     * @param option1 位置
     * @param option2 位置
     */
    public void setSelectOptions(int option1, int option2) {
        wheelOptions.setCurrentItems(option1, option2, 0);
    }

    /**
     * 设置选中的item位置
     *
     * @param option1 位置
     * @param option2 位置
     * @param option3 位置
     */
    public void setSelectOptions(int option1, int option2, int option3) {
        wheelOptions.setCurrentItems(option1, option2, option3);
    }

    /**
     * 设置选项的单位
     *
     * @param label1 单位
     */
    public void setLabels(String label1) {
        wheelOptions.setLabels(label1, null, null);
    }

    /**
     * 设置选项的单位
     *
     * @param label1 单位
     * @param label2 单位
     */
    public void setLabels(String label1, String label2) {
        wheelOptions.setLabels(label1, label2, null);
    }

    /**
     * 设置选项的单位
     *
     * @param label1 单位
     * @param label2 单位
     * @param label3 单位
     */
    public void setLabels(String label1, String label2, String label3) {
        wheelOptions.setLabels(label1, label2, label3);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic 循环滚动标识
     */
    public void setCyclic(boolean cyclic) {
        wheelOptions.setCyclic(cyclic);
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_CANCEL)) {
            dismiss();
        } else {
            if (optionsSelectListener != null) {
                int[] optionsCurrentItems = wheelOptions.getCurrentItems();
                optionsSelectListener.onOptionsSelect(optionsCurrentItems[0], optionsCurrentItems[1], optionsCurrentItems[2]);
            }
            dismiss();
        }
    }

    public interface OnOptionsSelectListener {
        void onOptionsSelect(int options1, int option2, int options3);
    }

    public void setOnOptionsSelectListener(
            OnOptionsSelectListener optionsSelectListener) {
        this.optionsSelectListener = optionsSelectListener;
    }
}