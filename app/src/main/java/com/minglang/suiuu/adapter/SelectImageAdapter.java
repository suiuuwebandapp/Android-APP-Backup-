package com.minglang.suiuu.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.minglang.suiuu.adapter.basic.BaseHolderAdapter;
import com.minglang.suiuu.entity.CheckImgBean;
import com.minglang.suiuu.utils.ViewHolder;
import com.minglang.suiuu.R;

import java.util.List;

/**
 * Created by Administrator on 2015/4/13.
 */
public class SelectImageAdapter extends BaseHolderAdapter<String> {


    private Context context;
    /**
     * 文件夹路径
     */
    private String mDirPath;

    private TextView complete;

    public SelectImageAdapter(Context context, List<String> mData, int itemLayoutId, String mDirPath, TextView complete) {
        super(context, mData, itemLayoutId);
        this.context = context;
        this.mDirPath = mDirPath;
        this.complete = complete;
    }

    @Override
    public void convert(ViewHolder helper, final String item, long position) {


        ImageView mImageView = helper.getView(R.id.id_item_image);
        ImageView mSelect = helper.getView(R.id.id_item_select);

        // 设置no_pic
        helper.setImageResource(R.id.id_item_image, R.drawable.pictures_no);
        // 设置no_selected
        helper.setImageResource(R.id.id_item_select, R.drawable.picture_unselected);

        // 设置图片
        helper.setImageByUrl(R.id.id_item_image, mDirPath + "/" + item);

        mImageView.setColorFilter(null);
        mImageView.setOnClickListener(new ImageClick(item, mImageView, mSelect));

    }

    class ImageClick implements View.OnClickListener {

        private String item;

        private ImageView mImageView;
        private ImageView mSelect;

        public ImageClick(String item, ImageView mImageView, ImageView mSelect) {
            this.item = item;
            this.mImageView = mImageView;
            this.mSelect = mSelect;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.id_item_image:

                    if (CheckImgBean.getImgBean().getListImg().contains(mDirPath + "/" + item)) {
                        CheckImgBean.getImgBean().getListImg().remove(mDirPath + "/" + item);
                        mSelect.setImageResource(R.drawable.picture_unselected);
                        mImageView.setColorFilter(null);
                        CheckImgBean.getImgBean().mCount--;
                        complete.setText("完成(" + CheckImgBean.getImgBean().mCount
                                + "/9)");
                        if (CheckImgBean.getImgBean().mCount <= 0) {
                            complete.setTextColor(context.getResources().getColor(R.color.text_color_black_middle));
                            complete.setEnabled(false);
                        }
                    } else {
                        if (CheckImgBean.getImgBean().mCount < 9) {
                            // 未选择该图片
                            CheckImgBean.getImgBean().getListImg().add(mDirPath + "/" + item);
                            mSelect.setImageResource(R.drawable.pictures_selected);
                            mImageView.setColorFilter(Color.parseColor("#77000000"));
                            CheckImgBean.getImgBean().mCount++;
                            complete.setText("完成("
                                    + CheckImgBean.getImgBean().mCount + "/9)");
                            if (CheckImgBean.getImgBean().mCount > 0) {
                                complete.setTextColor(context.getResources().getColor(R.color.text_color_white));
                                complete.setEnabled(true);
                            }
                        } else {
                            Toast.makeText(context, "最多选取9张图片!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
            }
        }
    }

}
