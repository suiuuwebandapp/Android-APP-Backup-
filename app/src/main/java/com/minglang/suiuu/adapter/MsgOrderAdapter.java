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
import com.minglang.suiuu.entity.MsgOrder.MsgOrderData.MsgOrderItemData;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * Created by a on 2015/8/6.
 * <p/>
 * 订单消息数据适配器
 */
public class MsgOrderAdapter extends BaseAdapter {

    private static final int ORDER_CANCEL = 0;
    private static final int ORDER_BOOK = 1;
    private static final int ORDER_RECEIVE = 2;
    private static final int ORDER_PLAY = 3;

    private Context context;

    private List<MsgOrderItemData> list;

    public MsgOrderAdapter(Context context, List<MsgOrderItemData> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<MsgOrderItemData> list) {
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
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        String type = list.get(position).getRelativeType();
        switch (type) {
            case "9":
                return ORDER_CANCEL;

            case "10":
                return ORDER_BOOK;

            case "11":
                return ORDER_RECEIVE;

            case "12":
                return ORDER_PLAY;
        }
        return -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemType = getItemViewType(position);
        switch (itemType) {
            case ORDER_CANCEL:
                ViewHolder cancelHolder = ViewHolder.get(context, convertView, parent, R.layout.item_msg_order_9, position);
                convertView = cancelHolder.getConvertView();

                SimpleDraweeView cancelHeadImageView = cancelHolder.getView(R.id.item_msg_order_9_head_image_view);
                String cancelHeadImagePath = list.get(position).getHeadImg();
                cancelHeadImageView.setImageURI(Uri.parse(cancelHeadImagePath));

                TextView cancelUserNameView = cancelHolder.getView(R.id.item_msg_order_9_user_name);
                String cancelUserName = list.get(position).getNickname();
                if (!TextUtils.isEmpty(cancelUserName)) {
                    cancelUserNameView.setText(cancelUserName);
                }
                break;

            case ORDER_BOOK:
                ViewHolder bookHolder = ViewHolder.get(context, convertView, parent, R.layout.item_msg_order_10, position);
                convertView = bookHolder.getConvertView();

                SimpleDraweeView bookHeadImageView = bookHolder.getView(R.id.item_msg_order_10_head_image_view);
                String bookHeadImagePath = list.get(position).getHeadImg();
                bookHeadImageView.setImageURI(Uri.parse(bookHeadImagePath));

                TextView bookUserNameView = bookHolder.getView(R.id.item_msg_order_10_user_name);
                String bookUserName = list.get(position).getNickname();
                if (!TextUtils.isEmpty(bookUserName)) {
                    bookUserNameView.setText(bookUserName);
                }

                TextView bookOrderNumberView = bookHolder.getView(R.id.item_msg_order_10_order_number);
                String bookOrderNumber = list.get(position).getRelativeId();
                if (!TextUtils.isEmpty(bookOrderNumber)) {
                    bookOrderNumberView.setText(bookOrderNumber);
                }
                break;

            case ORDER_RECEIVE:
                ViewHolder receiveHolder = ViewHolder.get(context, convertView, parent, R.layout.item_msg_order_11, position);
                convertView = receiveHolder.getConvertView();

                SimpleDraweeView receiveHeadImageView = receiveHolder.getView(R.id.item_msg_order_11_head_image_view);
                String receiveHeadImagePath = list.get(position).getHeadImg();
                receiveHeadImageView.setImageURI(Uri.parse(receiveHeadImagePath));

                TextView receiveUserNameView = receiveHolder.getView(R.id.item_msg_order_11_user_name);
                String receiveUserName = list.get(position).getNickname();
                if (!TextUtils.isEmpty(receiveUserName)) {
                    receiveUserNameView.setText(receiveUserName);
                }

                TextView receiveOrderNumberView = receiveHolder.getView(R.id.item_msg_order_11_order_number);
                String receiveOrderNumber = list.get(position).getRelativeId();
                if (!TextUtils.isEmpty(receiveOrderNumber)) {
                    receiveOrderNumberView.setText(receiveOrderNumber);
                }
                break;

            case ORDER_PLAY:
                ViewHolder playHolder = ViewHolder.get(context, convertView, parent, R.layout.item_msg_order_12, position);
                convertView = playHolder.getConvertView();

                SimpleDraweeView playHeadImageView = playHolder.getView(R.id.item_msg_order_12_head_image_view);
                String playHeadImagePath = list.get(position).getHeadImg();
                playHeadImageView.setImageURI(Uri.parse(playHeadImagePath));

                TextView playUserNameView = playHolder.getView(R.id.item_msg_order_12_user_name);
                String playUserName = list.get(position).getNickname();
                if (!TextUtils.isEmpty(playUserName)) {
                    playUserNameView.setText(playUserName);
                }

                TextView playOrderNumberView = playHolder.getView(R.id.item_msg_order_12_order_number);
                String playOrderNumber = list.get(position).getRelativeId();
                if (!TextUtils.isEmpty(playOrderNumber)) {
                    playOrderNumberView.setText(playOrderNumber);
                }
                break;
        }
        return convertView;
    }

}