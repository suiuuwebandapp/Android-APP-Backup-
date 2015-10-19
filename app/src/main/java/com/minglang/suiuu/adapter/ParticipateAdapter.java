package com.minglang.suiuu.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.basic.BaseHolderAdapter;
import com.minglang.suiuu.entity.Participate.ParticipateData;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * 参加的随游数据适配器
 * <p/>
 * Created by Administrator on 2015/6/23.
 */
public class ParticipateAdapter extends BaseHolderAdapter<ParticipateData>{

    public ParticipateAdapter(Context context, List<ParticipateData> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, ParticipateData item, long position) {
        SimpleDraweeView participateImage = helper.getView(R.id.item_participate_image);
        TextView participateTitle = helper.getView(R.id.item_participate_title);
        TextView participateIntro = helper.getView(R.id.item_participate_intro);

        String imagePath = item.getTitleImg();
        if (!TextUtils.isEmpty(imagePath)) {
            participateImage.setImageURI(Uri.parse(imagePath));
        }

        String title = item.getTitle();
        if (!TextUtils.isEmpty(title)) {
            participateTitle.setText(title);
        }

        String intro = item.getIntro();
        if (!TextUtils.isEmpty(intro)) {
            participateIntro.setText(intro);
        }

    }

}