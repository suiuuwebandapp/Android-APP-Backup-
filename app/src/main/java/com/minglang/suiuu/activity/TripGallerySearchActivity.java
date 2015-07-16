package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;
import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseActivity;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/7/14 14:36
 * 修改人：Administrator
 * 修改时间：2015/7/14 14:36
 * 修改备注：
 */
public class TripGallerySearchActivity extends BaseActivity {
    private LinearLayout ll_trip_gallery_search_tag;
    private ImageView iv_top_back;
    private ImageView tv_top_right_more;
    private TextView tv_top_center;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_gallery_search);
        initView();
        viewAction();
    }

    private void initView() {
        ll_trip_gallery_search_tag = (LinearLayout) findViewById(R.id.ll_trip_gallery_search_tag);
        iv_top_back = (ImageView) findViewById(R.id.iv_top_back);
        tv_top_right_more = (ImageView) findViewById(R.id.tv_top_right_more);
        tv_top_right_more.setVisibility(View.INVISIBLE);
        tv_top_center = (TextView) findViewById(R.id.tv_top_center);
        tv_top_center.setText("旅 图");

        //初始化标签内容
        int[] mPhotosIntArray = new int[]{R.drawable.tag_family, R.drawable.tag_shopping, R.drawable.tag_nature, R.drawable.tag_dangerous, R.drawable.tag_romantic, R.drawable.tag_museam, R.drawable.tag_novelty};
        final String[] mTagIntArray = new String[]{"家庭", "购物", "自然", "惊险", "浪漫", "博物馆", "猎奇"};
        View itemView = null;
        ImageView imageView = null;
        TextView textView;
        for (int i = 0; i < mPhotosIntArray.length; i++) {
            itemView = LayoutInflater.from(this).inflate(R.layout.item_trip_gallery_tag, null);
            itemView.setTag(i);
            imageView = (ImageView) itemView.findViewById(R.id.iv_item_trip_gallery_tag);
            textView = (TextView) itemView.findViewById(R.id.tv_item_trip_gallery_tag);
            imageView.setImageResource(mPhotosIntArray[i]);
            textView.setText(mTagIntArray[i]);
            itemView.setPadding(10, 0, 0, 0);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (int) v.getTag();
                    Toast.makeText(TripGallerySearchActivity.this, "点击了" + mTagIntArray[tag], Toast.LENGTH_SHORT).show();
                }
            });
            ll_trip_gallery_search_tag.addView(itemView);
        }
    }

    private void viewAction() {
        iv_top_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
