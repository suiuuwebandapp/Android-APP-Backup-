package com.minglang.suiuu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.NotFinishedOrder;
import com.minglang.suiuu.entity.TripJsonInfo;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * Created by Administrator on 2015/7/28.
 * <p/>
 * 未完成的订单数据适配器
 */
public class NotFinishedAdapter extends BaseHolderAdapter<NotFinishedOrder.NotFinishedOrderData> {
    private static final String TAG = NotFinishedAdapter.class.getSimpleName();

    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public NotFinishedAdapter(Context context, List<NotFinishedOrder.NotFinishedOrderData> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.loading)
                .showImageForEmptyUri(R.drawable.loading).showImageOnFail(R.drawable.loading_error)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    @Override
    public void convert(ViewHolder helper, NotFinishedOrder.NotFinishedOrderData item, long position) {
        ImageView mainImageView = helper.getView(R.id.item_my_order_main_image);
        TextView orderStatus = helper.getView(R.id.item_my_order_status);
        TextView orderTitle = helper.getView(R.id.item_my_order_title);
        TextView orderBeginDate = helper.getView(R.id.item_my_order_begin_date);

        String strTripJsonInfo = item.getTripJsonInfo();
        TripJsonInfo tripJsonInfo;
        String mainImagePath = null;
        String status = null;
        String title = null;
        String beginDate = null;

        try {
            tripJsonInfo = JsonUtils.getInstance().fromJSON(TripJsonInfo.class, strTripJsonInfo);
            mainImagePath = tripJsonInfo.getInfo().getTitleImg();
            status = item.getStatus();
            title = tripJsonInfo.getInfo().getTitle();
            beginDate = item.getBeginDate();
        } catch (Exception e) {
            DeBugLog.e(TAG, "未完成的订单第" + position + "条的数据解析异常:" + e.getMessage());
        } finally {
            if (!TextUtils.isEmpty(mainImagePath)) {
                imageLoader.displayImage(mainImagePath, mainImageView, options);
            }

            if (!TextUtils.isEmpty(status)) {
                switch (status) {
                    case "0":
                        orderStatus.setText("待支付");
                        break;
                    case "1":
                        orderStatus.setText("已支付 待确认");
                        break;
                    case "2":
                        orderStatus.setText("已支付 已确认");
                        break;
                    case "3":
                        orderStatus.setText("未支付 已取消");
                        break;
                    case "4":
                        orderStatus.setText("待退款");
                        break;
                    case "5":
                        orderStatus.setText("退款成功");
                        break;
                    case "6":
                        orderStatus.setText("游玩结束 待付款给随友");
                        break;
                    case "7":
                        orderStatus.setText("结束，已经付款给随友");
                        break;
                    case "8":
                        orderStatus.setText("退款审核中");
                        break;
                    case "9":
                        orderStatus.setText("退款审核失败");
                        break;
                    case "10":
                        orderStatus.setText("随友取消订单");
                        break;
                    default:
                        orderStatus.setText("订单状态未知");
                        break;
                }
            } else {
                orderStatus.setText("订单状态未知");
            }

            if (!TextUtils.isEmpty(title)) {
                orderTitle.setText(title);
            } else {
                orderTitle.setText("");
            }

            if (!TextUtils.isEmpty(beginDate)) {
                orderBeginDate.setText(beginDate);
            } else {
                orderBeginDate.setText("");
            }

        }

    }

}