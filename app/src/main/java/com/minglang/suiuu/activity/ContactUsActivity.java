package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.ContactUsAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.entity.Contact;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;

/**
 * 联系我们
 */
public class ContactUsActivity extends BaseAppCompatActivity {

    @BindColor(R.color.white)
    int titleColor;

    @Bind(R.id.contact_us_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.contact_us_info_list)
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
        toolbar.setTitleTextColor(titleColor);
        setSupportActionBar(toolbar);

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

        contactUsInfoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}