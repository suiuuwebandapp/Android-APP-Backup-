package com.minglang.suiuu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.NewOrder;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 新订单管理数据适配器
 * <p/>
 * Created by Administrator on 2015/6/23.
 */
public class NewOrderAdapter extends BaseAdapter {

    private Context context;

    private List<NewOrder.NewOrderData> list;

    private ImageLoader imageLoader;

    private DisplayImageOptions options;

    public NewOrderAdapter(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_head_image)
                .showImageForEmptyUri(R.drawable.loading).showImageOnFail(R.drawable.loading_error)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public void setList(List<NewOrder.NewOrderData> list) {
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
        CircleImageView headImageView = holder.getView(R.id.new_oder_user_head_image);
        //标题
        TextView titleView = holder.getView(R.id.new_order_title);
        //价格
        TextView moneyView = holder.getView(R.id.new_oder_money_number);
        //简介
        TextView introView = holder.getView(R.id.new_oder_intro);
        //开始日期
        TextView startDateView = holder.getView(R.id.new_order_start_date);
        //开始时间
        TextView startTimeView = holder.getView(R.id.new_oder_start_time);
        //出行人数
        TextView peopleNumber = holder.getView(R.id.new_order_people_number);

        return convertView;
    }
}
