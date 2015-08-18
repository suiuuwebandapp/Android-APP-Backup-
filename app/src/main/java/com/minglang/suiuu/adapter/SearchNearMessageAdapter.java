package com.minglang.suiuu.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.minglang.suiuu.R;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：搜索附近位置信息Adapter
 * 创建人：Administrator
 * 创建时间：2015/8/17 16:28
 * 修改人：Administrator
 * 修改时间：2015/8/17 16:28
 * 修改备注：
 */
public class SearchNearMessageAdapter extends BaseAdapter {

    private Context context;
    private List<PoiItem> list;

    public SearchNearMessageAdapter(Context context, List<PoiItem> list) {
        this.context = context;
        this.list = list;
    }

    public void onDateChange(List<PoiItem> list) {
        this.list = list;
        this.notifyDataSetChanged();
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
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.adapter_near_search_message, position);
        convertView = holder.getConvertView();
        TextView tv_main_location_name = holder.getView(R.id.tv_main_location_name);
        TextView tv_main_address_name = holder.getView(R.id.tv_main_address_name);
        tv_main_location_name.setText(list.get(position).toString());
        String street = list.get(position).getSnippet();
        if(!TextUtils.isEmpty(street)){
            tv_main_address_name.setText(street);
        }
        return convertView;

    }
}
