package com.minglang.suiuu.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.customview.LinearLayoutBaseAdapter;
import com.minglang.suiuu.entity.MainDynamicDataUser;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * 首页关注动态数据适配器
 * <p/>
 * Created by Administrator on 2015/4/30.
 */
public class AttentionDynamicAdapter extends LinearLayoutBaseAdapter {

    private List<MainDynamicDataUser> list;

    private ImageLoader imageLoader;

    private int ScreenHeight;

    public AttentionDynamicAdapter(Context context, List<MainDynamicDataUser> list) {
        super(context, list);
        this.list = list;

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }

    public void setScreenHeight(int ScreenHeight) {
        this.ScreenHeight = ScreenHeight;
    }

    @Override
    public View getView(int position) {

        MainDynamicDataUser user = list.get(position);

        View view = getLayoutInflater().inflate(R.layout.item_attention_dynamic, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.item_attention_dynamic_image);
        CircleImageView circleImageView = (CircleImageView) view.findViewById(R.id.item_attention_dynamic_head);
        TextView title = (TextView) view.findViewById(R.id.item_attention_dynamic_title);
        TextView content = (TextView) view.findViewById(R.id.item_attention_dynamic_content);

        String imagePath = user.getaImg();
        if (!TextUtils.isEmpty(imagePath)) {
            imageLoader.displayImage(imagePath, imageView);
        }

        String headPath = user.getHeadImg();
        if (!TextUtils.isEmpty(headPath)) {
            imageLoader.displayImage(headPath, circleImageView);
        }

        String strTitle = user.getaTitle();
        if (!TextUtils.isEmpty(strTitle)) {
            title.setText(strTitle);
        } else {
            title.setText("");
        }

        String strContent = user.getaContent();
        if (!TextUtils.isEmpty(strContent)) {
            content.setText(strContent);
        } else {
            content.setText("");
        }

        int itemHeight = ScreenHeight / 4;

        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight);
        view.setLayoutParams(params);

        return view;
    }
}
