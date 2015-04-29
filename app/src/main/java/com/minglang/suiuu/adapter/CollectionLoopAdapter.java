package com.minglang.suiuu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.CollectionLoop;
import com.minglang.suiuu.entity.CollectionLoopData;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 收藏的圈子数据适配器
 * <p/>
 * Created by Administrator on 2015/4/28.
 */
public class CollectionLoopAdapter extends BaseAdapter {

    private Context context;

    private CollectionLoop collectionLoop;

    private List<CollectionLoopData> list;

    private ImageLoader loader;

    private DisplayImageOptions displayImageOptions1, displayImageOptions2;

    public CollectionLoopAdapter(Context context, CollectionLoop collectionLoop, List<CollectionLoopData> list) {
        this.context = context;
        this.collectionLoop = collectionLoop;
        this.list = list;

        loader = ImageLoader.getInstance();
        loader.init(ImageLoaderConfiguration.createDefault(context));

        displayImageOptions1 = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.scroll1)
                .showImageForEmptyUri(R.drawable.scroll1).showImageOnFail(R.drawable.scroll1)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).bitmapConfig(Bitmap.Config.RGB_565).build();

        displayImageOptions2 = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_head_image2)
                .showImageForEmptyUri(R.drawable.default_head_image2).showImageOnFail(R.drawable.default_head_image2)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).bitmapConfig(Bitmap.Config.RGB_565).build();
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
        ImageView imageView = holder.getView(R.id.item_collection_loop_details_image);
        //头像
        CircleImageView headImage = holder.getView(R.id.item_collection_loop_head_image);
        //用户名
        TextView userName = holder.getView(R.id.item_collection_loop_user_name);
        //标题
        TextView title = holder.getView(R.id.item_collection_loop_title);
        //赞
        TextView praise = holder.getView(R.id.item_collection_loop_praise);
        //评论
        TextView comment = holder.getView(R.id.item_collection_loop_comments);

        int height = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_head_image2).getHeight();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(headImage.getLayoutParams());
        params.setMargins(0, 0, 0, height / 2);
        imageView.setLayoutParams(params);

        loader.displayImage(collectionLoopData.getaImg(), imageView, displayImageOptions1);
        loader.displayImage(collectionLoopData.getHeadImg(), headImage, displayImageOptions2);

        userName.setText(collectionLoopData.getNickname());
        title.setText(collectionLoopData.getaTitle());

        praise.setText(collectionLoopData.getaSupportCount());
        comment.setText(collectionLoopData.aCmtCount);

        return holder.getConvertView();
    }
}
