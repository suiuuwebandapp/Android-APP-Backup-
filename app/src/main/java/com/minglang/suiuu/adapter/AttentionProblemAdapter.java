package com.minglang.suiuu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.AttentionProblem.AttentionProblemData.AttentionProblemItemData;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 关注的问答的数据适配器
 * <p/>
 * Created by Administrator on 2015/7/21.
 */
public class AttentionProblemAdapter extends BaseHolderAdapter<AttentionProblemItemData> {

    private ImageLoader imageLoader;

    private DisplayImageOptions options;

    public AttentionProblemAdapter(Context context, List<AttentionProblemItemData> list, int itemLayoutId) {
        super(context, list, itemLayoutId);

        imageLoader = ImageLoader.getInstance();

        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_head_image)
                .showImageForEmptyUri(R.drawable.default_head_image)
                .showImageOnFail(R.drawable.default_head_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    @Override
    public void convert(ViewHolder helper, AttentionProblemItemData item, long position) {
        CircleImageView headView = helper.getView(R.id.item_attention_problem_head_view);
        TextView commentNumber = helper.getView(R.id.item_attention_problem_comment_number);
        TextView titleView = helper.getView(R.id.item_attention_problem_title);
        TextView contentView = helper.getView(R.id.item_attention_problem_content);

        String headImagePath = item.getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            imageLoader.displayImage(headImagePath, headView, options);
        }

        String strCommentNumber = item.getAttentionNumber();
        if (!TextUtils.isEmpty(strCommentNumber)) {
            commentNumber.setText(strCommentNumber);
        } else {
            commentNumber.setText("0");
        }

        String strTitle = item.getQTitle();
        if (TextUtils.isEmpty(strTitle)) {
            titleView.setText(strTitle);
        } else {
            titleView.setText("");
        }

        String strContent = item.getQContent();
        if (!TextUtils.isEmpty(strContent)) {
            contentView.setText(strContent);
        } else {
            contentView.setText("");
        }

    }

}