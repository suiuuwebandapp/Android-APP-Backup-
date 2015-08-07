package com.minglang.suiuu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.MsgOrder.MsgOrderData.OrderEntity.MsgOrderItemData;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * Created by a on 2015/8/6.
 * <p/>
 * 订单消息数据适配器
 */
public class MsgOrderAdapter extends BaseHolderAdapter<MsgOrderItemData> {

    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public MsgOrderAdapter(Context context, List<MsgOrderItemData> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_head_image)
                .showImageForEmptyUri(R.drawable.default_head_image)
                .showImageOnFail(R.drawable.default_head_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    @Override
    public void convert(ViewHolder helper, MsgOrderItemData item, long position) {
        CircleImageView headImageView = helper.getView(R.id.item_msg_order_head_image_view);
        TextView userNameView = helper.getView(R.id.item_msg_order_user_name);
        TextView infoView = helper.getView(R.id.item_msg_order_info);
        TextView inView = helper.getView(R.id.item_msg_order_in);
        TextView purchase = helper.getView(R.id.item_msg_order_purchase);

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

        String type = item.getRelativeType();


    }

}