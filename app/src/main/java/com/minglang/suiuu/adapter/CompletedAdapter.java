package com.minglang.suiuu.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.CompletedOrder;
import com.minglang.suiuu.entity.TripJsonInfo;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2015/7/28.
 * <p/>
 * 普通用户-已完成的订单数据适配器
 */
public class CompletedAdapter extends BaseHolderAdapter<CompletedOrder.CompletedOrderData> {

    private static final String TAG = CompletedAdapter.class.getSimpleName();

    public CompletedAdapter(Context context, List<CompletedOrder.CompletedOrderData> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, CompletedOrder.CompletedOrderData item, long position) {
        SimpleDraweeView mainImageView = helper.getView(R.id.item_my_order_main_image);
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
            L.e(TAG, "已完成的订单第" + position + "条的数据解析异常:" + e.getMessage());
        } finally {
            if (!TextUtils.isEmpty(mainImagePath)) {
                Uri uri = Uri.parse(mainImagePath);
                mainImageView.setImageURI(uri);
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