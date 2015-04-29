package com.minglang.suiuu.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.ContactUsAdapter;
import com.minglang.suiuu.entity.Contact;
import com.minglang.suiuu.utils.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 联系我们
 */
public class ContactUsActivity extends Activity {

    private static final String[] TITLES = {"网站","邮箱","电话","地址"};

    private static final String[] INFOS = {"www.suiuu.com","info@suiuu.com","010-58483692","北京市东城区银河SOHO"};

    /**
     * 返回键
     */
    private ImageView contactUsBack;

    private ListView contactUsInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        initView();

        ViewAction();

    }

    private void ViewAction() {

        contactUsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        contactUsInfoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    /**
     * 初始化方法
     */
    private void initView() {

        /****************设置状态栏颜色*************/
        SystemBarTintManager mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(false);
        mTintManager.setTintColor(getResources().getColor(R.color.tr_black));

        int statusHeight = mTintManager.getConfig().getStatusBarHeight();

        boolean isKITKAT = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        if (isKITKAT) {
            RelativeLayout contactUsRootLayout = (RelativeLayout) findViewById(R.id.contactUsRootLayout);
            contactUsRootLayout.setPadding(0, statusHeight, 0, 0);
        }

        contactUsBack = (ImageView) findViewById(R.id.contactUsBack);

        contactUsInfoList = (ListView) findViewById(R.id.contactUsInfoList);

        List<Contact> list = new ArrayList<>();

        for(int i=0;i<4;i++){

            Contact contact = new Contact();
            contact.setContactTitle(TITLES[i]);
            contact.setContactInfo(INFOS[i]);

            list.add(contact);
        }

        ContactUsAdapter contactUsAdapter = new ContactUsAdapter(this,list);
        contactUsInfoList.setAdapter(contactUsAdapter);

    }


}
