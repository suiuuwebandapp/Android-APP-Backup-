package com.minglang.suiuu.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.CityAssistData;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2015/5/13.
 * <p/>
 * 选择城市的数据适配器
 */
public class SelectCityAdapter extends BaseAdapter implements SectionIndexer {

    private Context context;

    private List<CityAssistData> list;

    public SelectCityAdapter(Context context, List<CityAssistData> list) {
        this.context = context;
        this.list = list;
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

        CityAssistData data = list.get(position);

        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_city, position);
        TextView indexText = holder.getView(R.id.item_city_index_letter);
        TextView cityName = holder.getView(R.id.item_city_name);

        // 根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        if (position == getPositionForSection(section)) {
            indexText.setText(data.getFirstLetter());
            indexText.setVisibility(View.VISIBLE);
        } else {
            indexText.setVisibility(View.GONE);
        }

        String StrCnName = data.getCname();
        String StrUsName = data.getEname();
        if (!TextUtils.isEmpty(StrCnName)) {
            if (!TextUtils.isEmpty(StrUsName)) {
                cityName.setText(StrCnName + "   " + StrUsName);
            } else {
                cityName.setText(StrCnName);
            }
        } else {
            cityName.setText("");
        }

        convertView = holder.getConvertView();
        return convertView;
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < list.size(); i++) {
            char firstChar = list.get(i).getFirstLetter().toUpperCase().charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return list.get(position).getFirstLetter().toUpperCase().charAt(0);
    }

}