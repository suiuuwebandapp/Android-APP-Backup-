package com.minglang.suiuu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.minglang.suiuu.interfaces.RecyclerViewOnItemClickListener;

/**
 * Created by Administrator on 2015/9/14.
 * <p/>
 * 私信聊天数据适配器
 */
public class PrivateLetterChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private RecyclerViewOnItemClickListener itemClickListener;

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private class MyLetterChatViewHolder extends RecyclerView.ViewHolder{
        public MyLetterChatViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class OtherLetterChatViewHolder extends RecyclerView.ViewHolder{
        public OtherLetterChatViewHolder(View itemView) {
            super(itemView);
        }
    }

}