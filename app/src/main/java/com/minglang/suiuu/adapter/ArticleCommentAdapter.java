package com.minglang.suiuu.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.customview.LinearLayoutBaseAdapter;
import com.minglang.suiuu.entity.LoopArticleCommentList;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

public class ArticleCommentAdapter extends LinearLayoutBaseAdapter {

    private Context context;

    private List<LoopArticleCommentList> list;

    private ImageLoader imageLoader;

    private DisplayImageOptions options;

    public ArticleCommentAdapter(Context context, List<LoopArticleCommentList> list) {
        super(context, list);
        this.context = context;
        this.list = list;

        imageLoader = ImageLoader.getInstance();

        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_head_image)
                .showImageForEmptyUri(R.drawable.default_head_image)
                .showImageOnFail(R.drawable.default_head_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    @Override
    @SuppressLint("InflateParams")
    public View getView(int position) {
        View view = getLayoutInflater().inflate(R.layout.item_comment, null);
        CircleImageView headImageView = (CircleImageView) view.findViewById(R.id.item_comment_head_image);
        TextView commentTitle = (TextView) view.findViewById(R.id.item_comment_title);
        TextView content = (TextView) view.findViewById(R.id.item_comment_content);

        LoopArticleCommentList articleComment = list.get(position);

        String headImagePath = articleComment.getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            imageLoader.displayImage(headImagePath, headImageView, options);
        }

        String strTitle = articleComment.getNickname();
        if (!TextUtils.isEmpty(strTitle)) {
            commentTitle.setText(strTitle);
        } else {
            commentTitle.setText("");
        }

        String strContent = articleComment.getContent();
        if (!TextUtils.isEmpty(strContent)) {
            content.setText(strContent);
        } else {
            content.setText("");
        }

        return view;
    }
}
