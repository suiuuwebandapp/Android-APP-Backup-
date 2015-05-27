package com.minglang.suiuu.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.AreaCodeData;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

public class AreaCodeAdapter extends BaseAdapter {

    private Context context;

    private List<AreaCodeData> list;

    private boolean isZhCNLanguage;

    public AreaCodeAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<AreaCodeData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setZhCNLanguage(boolean isCNLanguage) {
        this.isZhCNLanguage = isCNLanguage;
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
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_area_layout, position);
        convertView = holder.getConvertView();
        TextView name = holder.getView(R.id.item_area_name);
        TextView code = holder.getView(R.id.item_area_code);

        String strName = list.get(position).getCname();
        String strName2 = list.get(position).getEname();

        if (isZhCNLanguage) {
            if (TextUtils.isEmpty(strName)) {
                name.setText("");
            } else {
                name.setText(strName);
            }
        } else {
            if (TextUtils.isEmpty(strName2)) {
                name.setText("");
            } else {
                name.setText(strName2);
            }
        }

        String strCode = list.get(position).getAreaCode();
        if (TextUtils.isEmpty(strCode)) {
            code.setText("");
        } else {
            code.setText(strCode);
        }

        return convertView;
    }
}
