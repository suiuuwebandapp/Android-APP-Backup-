package com.minglang.suiuu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.MsgTripGallery.MsgTripGalleryData.MsgTripGalleryItemData;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * Created by a on 2015/8/6.
 * <p/>
 * 旅途消息数据适配器
 */
public class MsgTripGalleryAdapter extends BaseHolderAdapter<MsgTripGalleryItemData> {

    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public MsgTripGalleryAdapter(Context context, List<MsgTripGalleryItemData> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_head_image)
                .showImageForEmptyUri(R.drawable.default_head_image)
                .showImageOnFail(R.drawable.default_head_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    @Override
    public void convert(ViewHolder helper, MsgTripGalleryItemData item, long position) {
        CircleImageView headImageView = helper.getView(R.id.item_msg_trip_gallery_head_image_view);
        TextView userNameView = helper.getView(R.id.item_msg_trip_gallery_user_name);
        TextView infoView = helper.getView(R.id.item_msg_trip_gallery_info);
        TextView actionView = helper.getView(R.id.item_msg_trip_gallery_action);

        String headImagePath = item.getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            imageLoader.displayImage(headImagePath, headImageView, options);
        }

        String userName = item.getNickname();
        if (!TextUtils.isEmpty(userName)) {
            userNameView.setText(Html.fromHtml("<u>" + userName + "</u>"));
        } else {
            userNameView.setText("");
        }

        String info = item.getTitle();
        if (!TextUtils.isEmpty(info)) {
            infoView.setText(Html.fromHtml("<u>" + info + "</u>"));
        } else {
            infoView.setText("");
        }

        String action = item.getRelativeType();
        if (!TextUtils.isEmpty(action)) {
            switch (action) {
                case "2":
                    actionView.setText("评论了你");
                    break;
                case "3":
                    actionView.setText("回复了你");
                    break;
                case "4":
                    actionView.setText("关注了你");
                    break;
                default:
                    actionView.setText("");
                    break;
            }
        } else {
            actionView.setText("");
        }

    }

}