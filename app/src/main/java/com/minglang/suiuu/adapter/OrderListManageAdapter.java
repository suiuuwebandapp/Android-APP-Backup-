package com.minglang.suiuu.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.OrderManage.OrderManageData;
import com.minglang.suiuu.entity.TripJsonInfo;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * 订单管理数据适配器
 * <p/>
 * Created by Administrator on 2015/6/23.
 */
public class OrderListManageAdapter extends BaseAdapter {

    private static final String TAG = OrderListManageAdapter.class.getSimpleName();

    private Context context;
    private List<OrderManageData> list;

    public OrderListManageAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<OrderManageData> list) {
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
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_new_order_layout, position);
        convertView = holder.getConvertView();

        //用户头像
        SimpleDraweeView headImageView = holder.getView(R.id.new_oder_user_head_image);
        //标题
        TextView titleView = holder.getView(R.id.new_order_title);
        //价格
        TextView moneyView = holder.getView(R.id.new_oder_money_price);
        //简介
        TextView introView = holder.getView(R.id.new_oder_intro);
        //开始日期
        TextView startDateView = holder.getView(R.id.new_order_start_date);
        //开始时间
        TextView startTimeView = holder.getView(R.id.new_oder_start_time);
        //出行人数
        TextView personCountView = holder.getView(R.id.new_order_people_number);

        if (list != null && list.size() > 0) {
            OrderManageData newOrderData = list.get(position);

            String headImagePath = newOrderData.getHeadImg();
            if (!TextUtils.isEmpty(headImagePath)) {
                headImageView.setImageURI(Uri.parse(headImagePath));
            } else {
                headImageView.setImageURI(Uri.parse("res://com.minglang.suiuu/" + R.drawable.default_head_image_error));
            }

            String tripJsonInfo = newOrderData.getTripJsonInfo();
            try {
                TripJsonInfo jsonInfo = JsonUtils.getInstance().fromJSON(TripJsonInfo.class, tripJsonInfo);
                String title = jsonInfo.getInfo().getTitle();
                if (!TextUtils.isEmpty(title)) {
                    titleView.setText(title);
                } else {
                    titleView.setText("暂无标题");
                }
            } catch (Exception e) {
                L.e(TAG, "数据绑定Error:" + e.getMessage());
            }

            String totalPrice = newOrderData.getTotalPrice();
            if (!TextUtils.isEmpty(totalPrice)) {
                moneyView.setText(totalPrice);
            } else {
                moneyView.setText("0");
            }

            String intro = newOrderData.getIntro();
            if (!TextUtils.isEmpty(intro)) {
                introView.setText(intro);
            } else {
                introView.setText("暂无简介");
            }

            String startDate = newOrderData.getBeginDate();
            if (!TextUtils.isEmpty(startDate)) {
                startDateView.setText(startDate);
            } else {
                startDateView.setText("");
            }

            String startTime = newOrderData.getStartTime();
            if (!TextUtils.isEmpty(startTime)) {
                startTimeView.setText(startTime);
            } else {
                startTimeView.setText("");
            }

            String personCount = newOrderData.getPersonCount();
            if (!TextUtils.isEmpty(personCount)) {
                personCountView.setText("出行人数:" + personCount);
            } else {
                personCountView.setText("0");
            }

        }

        return convertView;
    }

}