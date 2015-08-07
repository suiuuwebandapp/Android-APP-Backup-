package com.minglang.suiuu.adapter;

import android.content.Context;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.Contact;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

public class ContactUsAdapter extends BaseHolderAdapter<Contact> {

    public ContactUsAdapter(Context context, List<Contact> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, Contact item, long position) {
        TextView TitleView = helper.getView(R.id.contactUsTitle);
        TextView InfoView = helper.getView(R.id.contactUsInfo);
        TitleView.setText(item.getContactTitle());
        InfoView.setText(item.getContactInfo());
    }

}