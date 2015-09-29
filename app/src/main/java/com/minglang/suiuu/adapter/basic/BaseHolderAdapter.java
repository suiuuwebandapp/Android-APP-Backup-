package com.minglang.suiuu.adapter.basic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

public abstract class BaseHolderAdapter<T> extends BaseAdapter {
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> list;
    protected final int mItemLayoutId;

    /**
     * 控制是否修改Item的View的宽高
     */
    private boolean isAlter = false;

    private int convertViewWidth;
    private int convertViewHeight;

    public BaseHolderAdapter(Context context, List<T> list, int itemLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.list = list;
        this.mItemLayoutId = itemLayoutId;
    }

    public void setList(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setIsAlter(boolean isAlter) {
        this.isAlter = isAlter;
    }

    public void setConvertViewParams(int convertViewWidth, int convertViewHeight) {
        this.convertViewWidth = convertViewWidth;
        this.convertViewHeight = convertViewHeight;
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
    public T getItem(int position) {
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
        final ViewHolder viewHolder = getViewHolder(position, convertView, parent);
        convert(viewHolder, getItem(position), position);
        convertView = viewHolder.getConvertView();
        if (isAlter) {
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(convertViewWidth, convertViewHeight);
            convertView.setLayoutParams(params);
        }
        return convertView;
    }

    public abstract void convert(ViewHolder helper, T item, long position);

    private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return ViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);
    }

}