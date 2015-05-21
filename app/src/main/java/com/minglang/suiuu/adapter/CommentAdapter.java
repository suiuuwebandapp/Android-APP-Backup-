package com.minglang.suiuu.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.LoopArticleCommentList;

import java.util.List;

/**
 * Created by Administrator on 2015/4/9.
 */
public class CommentAdapter extends BaseAdapter {

    private Context context;

    private List<LoopArticleCommentList> list;

    private BitmapUtils bitMap;
    public CommentAdapter(Context context){
        this.context =context;
    }

    public CommentAdapter(Context context, List<LoopArticleCommentList> list) {
        this.context = context;
        this.list = list;
        bitMap = new BitmapUtils(context);
    }

    public void setList(List<LoopArticleCommentList> list){
        this.list = list;
        notifyDataSetChanged();
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

        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment, null);
            holder.headImage = (CircleImageView) convertView.findViewById(R.id.item_comment_head_image);
            holder.title = (TextView) convertView.findViewById(R.id.item_comment_title);
            holder.content = (TextView) convertView.findViewById(R.id.item_comment_content);
            holder.time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(!TextUtils.isEmpty(list.get(position).getHeadImg())) {
            bitMap.display(holder.headImage, list.get(position).getHeadImg());
        }
        holder.title.setText(list.get(position).getNickname());
        holder.time.setText(list.get(position).getcTime());
        if(!TextUtils.isEmpty(list.get(position).getrTitle())) {
            holder.content.setText(list.get(position).getrTitle() +"  "+list.get(position).getContent());
        }else {
            holder.content.setText(list.get(position).getContent());

        }

        return convertView;
    }

    class ViewHolder {
        CircleImageView headImage;
        TextView title;
        TextView content;
        TextView time;
    }

}
