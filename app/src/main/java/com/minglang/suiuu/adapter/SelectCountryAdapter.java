package com.minglang.suiuu.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.CountryAssistData;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * 选择国家数据适配器
 * <p/>
 * Created by LZY on 2015/5/12 0012.
 */
public class SelectCountryAdapter extends BaseAdapter implements SectionIndexer {

    private Context context;

    private List<CountryAssistData> list;

    public SelectCountryAdapter(Context context) {
        this.context = context;
    }

    public SelectCountryAdapter(Context context, List<CountryAssistData> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<CountryAssistData> list) {
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
        CountryAssistData data = list.get(position);

        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_country, position);
        convertView = holder.getConvertView();

        TextView countryName = holder.getView(R.id.item_country_name);
        TextView indexLetter = holder.getView(R.id.item_country_index_letter);

        // 根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        if (position == getPositionForSection(section)) {
            indexLetter.setText(data.getFirstLetter());
            indexLetter.setVisibility(View.VISIBLE);
        } else {
            indexLetter.setVisibility(View.GONE);
        }

        String StrCnName = data.getCname();
        String StrUsName = data.getEname();

        if (!TextUtils.isEmpty(StrCnName)) {
            if (!TextUtils.isEmpty(StrUsName)) {
                countryName.setText(StrCnName + "   " + StrUsName);
            } else {
                countryName.setText(StrCnName);
            }
        } else {
            countryName.setText("");
        }


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