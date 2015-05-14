package com.minglang.suiuu.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.OtherUserDataArticle;
import com.minglang.suiuu.utils.Utils;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * 其他用户个人主页里发布的贴子数据适配器
 * <p/>
 * Created by Administrator on 2015/5/1.
 */
public class OtherUserArticleAdapter extends BaseAdapter {

    private Context context;

    private List<OtherUserDataArticle> list;
    BitmapUtils bitmapUtils;

    private int screenWidth, screenHeight;

    public OtherUserArticleAdapter(Context context, List<OtherUserDataArticle> list, int screenWidth, int screenHeight) {
        this.context = context;
        this.list = list;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        bitmapUtils = new BitmapUtils(context);
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

        OtherUserDataArticle article = list.get(position);

        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_other_user_article, position);

        ImageView image = holder.getView(R.id.item_other_user_main_image);
        TextView title = holder.getView(R.id.item_other_user_name);
        TextView praise = holder.getView(R.id.item_other_user_praise);
        TextView reply = holder.getView(R.id.item_other_user_reply);

        String imagePath = article.getaImg();
        Log.i("suiuu","imagePath" + imagePath);
        if (!TextUtils.isEmpty(imagePath)) {
            bitmapUtils.display(image, imagePath);
        }

        String strTitle = article.getaTitle();
        if (!TextUtils.isEmpty(strTitle)) {
            title.setText(strTitle);
        } else {
            title.setText("");
        }

        String praiseCount = article.getaSupportCount();
        if (!TextUtils.isEmpty(praiseCount)) {
            praise.setText(praiseCount);
        } else {
            praise.setText("0");
        }

        String replyCount = article.getaCmtCount();
        if (!TextUtils.isEmpty(replyCount)) {
            reply.setText(replyCount);
        } else {
            reply.setText("0");
        }

        convertView = holder.getConvertView();

        int itemParams = screenWidth / 2 - Utils.newInstance(context).dip2px(10);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(itemParams, itemParams);
        convertView.setLayoutParams(params);

        return convertView;
    }
}
