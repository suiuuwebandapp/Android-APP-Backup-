package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.ContactUsAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.entity.Contact;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 联系我们
 */
public class ContactUsActivity extends BaseAppCompatActivity {

    @Bind(R.id.contactUsBack)
    ImageView contactUsBack;

    @Bind(R.id.contactUsInfoList)
    ListView contactUsInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        ButterKnife.bind(this);
        initView();
        ViewAction();
    }

    /**
     * 初始化方法
     */
    private void initView() {
        String[] TITLES = getResources().getStringArray(R.array.contactUsArray1);
        String[] INFO_ARRAY = getResources().getStringArray(R.array.contactUsArray2);

        List<Contact> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Contact contact = new Contact();
            contact.setContactTitle(TITLES[i]);
            contact.setContactInfo(INFO_ARRAY[i]);
            list.add(contact);
        }

        ContactUsAdapter contactUsAdapter = new ContactUsAdapter(this, list, R.layout.item_contact_us);
        contactUsInfoList.setAdapter(contactUsAdapter);
    }

    private void ViewAction() {
        contactUsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        contactUsInfoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

}