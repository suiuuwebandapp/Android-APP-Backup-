package com.minglang.suiuu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.CommunityItem.CommunityItemData.AnswerEntity;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 社区条目数据适配器
 * <p/>
 * Created by Administrator on 2015/7/3.
 */
public class CommunityItemAdapter extends BaseAdapter {

    private static final String TAG = CommunityItemAdapter.class.getSimpleName();

    private Context context;

    private List<AnswerEntity> list;

    private ImageLoader imageLoader;

    private DisplayImageOptions options;

    public CommunityItemAdapter(Context context) {
        this.context = context;

        imageLoader = ImageLoader.getInstance();

        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_head_image)
                .showImageForEmptyUri(R.drawable.default_head_image)
                .showImageOnFail(R.drawable.default_head_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public void setList(List<AnswerEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if (list != null && list.size() > 0) {
            return list.size();
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder4 = ViewHolder.get(context, convertView, parent, R.layout.item_community_layout, position);
        convertView = holder4.getConvertView();

        CircleImageView headImageView = holder4.getView(R.id.item_community_layout_4_head_image_view);
        TextView nameView = holder4.getView(R.id.item_community_layout_4_name);
        TextView dateAndTimeView = holder4.getView(R.id.item_community_layout_4_date_time);
        TextView detailsView = holder4.getView(R.id.item_community_layout_4_details);

        AnswerEntity answerEntity = list.get(position);

        String headImagePath = answerEntity.getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            imageLoader.displayImage(headImagePath, headImageView, options);
        }

        String strName = answerEntity.getNickname();
        if (!TextUtils.isEmpty(strName)) {
            nameView.setText(strName);
        } else {
            nameView.setText("");
        }

        String strDateAndTime = answerEntity.getACreateTime();
        if (!TextUtils.isEmpty(strDateAndTime)) {
            dateAndTimeView.setText(strDateAndTime);
        } else {
            dateAndTimeView.setText("");
        }

        String strContent = answerEntity.getAContent();
        if (!TextUtils.isEmpty(strContent)) {
            detailsView.setText(strContent);
        } else {
            detailsView.setText("");
        }

        headImageView.setOnClickListener(new HeadViewClickListener(position));

        return convertView;
    }

    private class HeadViewClickListener implements View.OnClickListener {

        private int position;

        private HeadViewClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            String userSign = list.get(position).getAUserSign();
            DeBugLog.i(TAG, "userSign:" + userSign);
        }

    }

}