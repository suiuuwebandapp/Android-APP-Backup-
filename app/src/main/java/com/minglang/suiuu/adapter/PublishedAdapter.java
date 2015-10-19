package com.minglang.suiuu.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.basic.BaseHolderAdapter;
import com.minglang.suiuu.entity.Published.PublishedData;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * 发布的随游数据适配器
 * <p/>
 * Created by Administrator on 2015/6/19.
 */
public class PublishedAdapter extends BaseHolderAdapter<PublishedData>{

    public PublishedAdapter(Context context, List<PublishedData> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, PublishedData item, long position) {
        SimpleDraweeView publishImage = helper.getView(R.id.item_published_image);
        TextView publishTitle = helper.getView(R.id.item_published_title);
        TextView publishIntro = helper.getView(R.id.item_published_intro);

        String imagePath = item.getTitleImg();
        if (!TextUtils.isEmpty(imagePath)) {
            publishImage.setImageURI(Uri.parse(imagePath));
        }

        String title = item.getTitle();
        if (!TextUtils.isEmpty(title)) {
            publishTitle.setText(title);
        }

        String intro = item.getIntro();
        if (!TextUtils.isEmpty(intro)) {
            publishIntro.setText(intro);
        }

    }

}