package com.minglang.suiuu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.CollectionLoopData;
import com.minglang.suiuu.utils.Utils;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 收藏的圈子数据适配器
 * <p/>
 * Created by Administrator on 2015/4/28.
 */
public class CollectionLoopAdapter extends BaseAdapter {

    private static final String TAG = CollectionLoopAdapter.class.getSimpleName();

    private Context context;

    private List<CollectionLoopData> list;

    private ImageLoader loader;

    private DisplayImageOptions displayImageOptions1;

    private int screenWidth;

    public CollectionLoopAdapter(Context context) {
        this.context = context;

        loader = ImageLoader.getInstance();

        displayImageOptions1 = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.loading)
                .showImageForEmptyUri(R.drawable.loading).showImageOnFail(R.drawable.loading_error)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public void setScreenParams(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public void setListData(List<CollectionLoopData> list) {
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
            return list.get(position);
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

        CollectionLoopData collectionLoopData = list.get(position);

        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_collection_loop, position);

        //主要图片
        ImageView imageView = holder.getView(R.id.item_collection_loop_image);
        //头像
        CircleImageView headImageView = holder.getView(R.id.item_collection_loop_head_image);
        //用户名
        TextView userName = holder.getView(R.id.item_collection_loop_user_name);
        //标题
        TextView title = holder.getView(R.id.item_collection_loop_title);
        //赞
        TextView praise = holder.getView(R.id.item_collection_loop_praise);
        //评论
        TextView comment = holder.getView(R.id.item_collection_loop_comments);

        String mainImagePath = collectionLoopData.getaImg();
        if (!TextUtils.isEmpty(mainImagePath)) {
            loader.displayImage(mainImagePath.trim().trim(), imageView, displayImageOptions1);
        }

        String headImagePath = collectionLoopData.getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            loader.displayImage(headImagePath.trim(), headImageView);
        }

        String strNickName = collectionLoopData.getNickname();
        if (!TextUtils.isEmpty(strNickName)) {
            userName.setText(strNickName);
        } else {
            userName.setText("");
        }

        String strTitle = collectionLoopData.getaTitle();
        if (!TextUtils.isEmpty(strTitle)) {
            title.setText(strTitle);
        } else {
            title.setText("");
        }

        String praiseCount = collectionLoopData.getaSupportCount();
        if (!TextUtils.isEmpty(praiseCount)) {
            praise.setText(praiseCount);
        } else {
            praise.setText("0");
        }

        String commentCount = collectionLoopData.getaCmtCount();
        if (!TextUtils.isEmpty(commentCount)) {
            comment.setText(commentCount);
        } else {
            comment.setText("0");
        }

        convertView = holder.getConvertView();
        int itemParams = screenWidth / 2 - Utils.newInstance(context).dip2px(10);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(itemParams, itemParams);
        convertView.setLayoutParams(params);

        return convertView;
    }
}
