package com.minglang.suiuu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.MsgOrder.MsgOrderData.MsgOrderItemData;
import com.minglang.suiuu.entity.TripJsonInfo;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.JsonUtils;
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

    private static final String TAG = MsgOrderAdapter.class.getSimpleName();

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
        TextView titleView = helper.getView(R.id.item_msg_order_title);

        TextView inView = helper.getView(R.id.item_msg_order_in);
        TextView purchase = helper.getView(R.id.item_msg_order_purchase);
        TextView cancelView = helper.getView(R.id.item_msg_order_cancel);
        TextView confirmView = helper.getView(R.id.item_msg_order_confirm);
        TextView confirmRefundView = helper.getView(R.id.item_msg_order_confirm_refund);

        TripJsonInfo tripJsonInfo = null;
        String strTripInfo = item.getTripJsonInfo();
        if (!TextUtils.isEmpty(strTripInfo)) {
            try {
                tripJsonInfo = JsonUtils.getInstance().fromJSON(TripJsonInfo.class, strTripInfo);
            } catch (Exception e) {
                DeBugLog.e(TAG, "Trip数据解析失败:" + e.getMessage());
            }
        } else {
            DeBugLog.e(TAG, "第" + position + "Trip数据为空");
        }


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
        if (!TextUtils.isEmpty(type)) {
            switch (type) {
                case "5":
                    inView.setVisibility(View.VISIBLE);
                    purchase.setVisibility(View.GONE);
                    cancelView.setVisibility(View.GONE);
                    confirmView.setVisibility(View.GONE);
                    confirmRefundView.setVisibility(View.GONE);

                    if (tripJsonInfo != null) {
                        String title = tripJsonInfo.getInfo().getTitle();
                        if (!TextUtils.isEmpty(title)) {
                            titleView.setText(Html.fromHtml("<u>" + title + "</u>"));
                        } else {
                            titleView.setText("");
                        }
                    } else {
                        titleView.setText("");
                    }
                    break;

                case "9":
                    inView.setVisibility(View.GONE);
                    purchase.setVisibility(View.GONE);
                    cancelView.setVisibility(View.VISIBLE);
                    confirmView.setVisibility(View.GONE);
                    confirmRefundView.setVisibility(View.GONE);

                    if (tripJsonInfo != null) {
                        String title = tripJsonInfo.getInfo().getTitle();
                        if (!TextUtils.isEmpty(title)) {
                            titleView.setText(title);
                        } else {
                            titleView.setText("");
                        }
                    } else {
                        titleView.setText("");
                    }
                    break;

                case "10":
                    inView.setVisibility(View.GONE);
                    purchase.setVisibility(View.VISIBLE);
                    cancelView.setVisibility(View.GONE);
                    confirmView.setVisibility(View.GONE);
                    confirmRefundView.setVisibility(View.GONE);

                    if (tripJsonInfo != null) {
                        String title = tripJsonInfo.getInfo().getTitle();
                        if (!TextUtils.isEmpty(title)) {
                            titleView.setText(title);
                        } else {
                            titleView.setText("");
                        }
                    } else {
                        titleView.setText("");
                    }

                    break;

                case "11":
                    inView.setVisibility(View.GONE);
                    purchase.setVisibility(View.GONE);
                    cancelView.setVisibility(View.GONE);
                    confirmView.setVisibility(View.GONE);
                    confirmRefundView.setVisibility(View.VISIBLE);
                    titleView.setText("");
                    break;

                case "12":
                    inView.setVisibility(View.GONE);
                    purchase.setVisibility(View.GONE);
                    cancelView.setVisibility(View.GONE);
                    confirmView.setVisibility(View.VISIBLE);
                    confirmRefundView.setVisibility(View.GONE);

                    if (tripJsonInfo != null) {
                        String title = tripJsonInfo.getInfo().getTitle();
                        if (!TextUtils.isEmpty(title)) {
                            titleView.setText(title);
                        } else {
                            titleView.setText("");
                        }
                    } else {
                        titleView.setText("");
                    }
                    break;

                default:
                    inView.setVisibility(View.GONE);
                    purchase.setVisibility(View.GONE);
                    cancelView.setVisibility(View.GONE);
                    confirmRefundView.setVisibility(View.GONE);
                    confirmView.setVisibility(View.GONE);
                    titleView.setText("");
                    break;
            }

        } else {
            inView.setVisibility(View.GONE);
            purchase.setVisibility(View.GONE);
            cancelView.setVisibility(View.GONE);
            confirmRefundView.setVisibility(View.GONE);
            confirmView.setVisibility(View.GONE);
            titleView.setText("");
        }

    }

}