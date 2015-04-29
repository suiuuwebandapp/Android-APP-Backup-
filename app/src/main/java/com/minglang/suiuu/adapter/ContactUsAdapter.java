package com.minglang.suiuu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.Contact;

import java.util.List;

/**
 * Created by Administrator on 2015/4/3.
 */
public class ContactUsAdapter extends BaseAdapter {

    private Context context;

    private List<Contact> list;

    public ContactUsAdapter(Context context, List<Contact> list) {
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

        Contact contact = list.get(position);

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = LayoutInflater.from(context).inflate(R.layout.item_contact_us, null);
            holder.TitleView = (TextView) convertView.findViewById(R.id.contactUsTitle);
            holder.InfoView = (TextView) convertView.findViewById(R.id.contactUsInfo);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.TitleView.setText(contact.getContactTitle());
        holder.InfoView.setText(contact.getContactInfo());

        return convertView;
    }

    class ViewHolder {
        TextView TitleView;
        TextView InfoView;
    }

}
