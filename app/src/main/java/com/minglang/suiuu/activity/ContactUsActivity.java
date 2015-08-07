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

    private static final String[] TITLES = {"网站","邮箱","电话","地址"};

    private static final String[] INFO_ARRAY = {"www.suiuu.com","info@suiuu.com","010-58483692","北京市东城区银河SOHO"};

    @Bind(R.id.contactUsBack)
    ImageView contactUsBack;

    @Bind(R.id.contactUsInfoList)
    private ListView contactUsInfoList;

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
        List<Contact> list = new ArrayList<>();

        for(int i=0;i<4;i++){
            Contact contact = new Contact();
            contact.setContactTitle(TITLES[i]);
            contact.setContactInfo(INFO_ARRAY[i]);
            list.add(contact);
        }

        ContactUsAdapter contactUsAdapter = new ContactUsAdapter(this,list,R.layout.item_contact_us);
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