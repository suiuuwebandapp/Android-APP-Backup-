package com.minglang.suiuu.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.SuiuuMessageData;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;


/**
 * 消息数据适配器
 * <p/>
 * Created by Administrator on 2015/5/3.
 */
public class MessageAdapter extends BaseAdapter {

    private Context context;

    private List<SuiuuMessageData> list;

    private ImageLoader imageLoader;

    private String type;

    public MessageAdapter(Context context, List<SuiuuMessageData> list, String type) {
        this.context = context;
        this.list = list;
        this.type = type;

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
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
        SuiuuMessageData data = list.get(position);

        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_message_layout, position);

        CircleImageView headImage = holder.getView(R.id.item_message_head_image);
        TextView info = holder.getView(R.id.item_message_info);

        String strHeadImage = data.getHeadImg();
        if (!TextUtils.isEmpty(strHeadImage)) {
            imageLoader.displayImage(strHeadImage, headImage);
        }

        String strInfo = data.getNickname();
        switch (type) {

            case "1":
                if (!TextUtils.isEmpty(strInfo)) {
                    info.setText(strInfo + " 评论了你，快去看吧。");
                } else {
                    info.setText("匿名用户 评论了你，快去看吧。");
                }
                break;

            case "2":
                break;

            case "3":
                break;

            case "4":
                break;
        }

        return convertView;
    }
}
