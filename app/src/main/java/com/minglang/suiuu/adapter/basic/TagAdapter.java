package com.minglang.suiuu.adapter.basic;

import android.view.View;

import com.minglang.suiuu.customview.FlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public abstract class TagAdapter<T> {

    private List<T> tagList;
    private OnDataChangedListener mOnDataChangedListener;
    private HashSet<Integer> mCheckedPosList = new HashSet<>();

    public TagAdapter(List<T> tagList) {
        this.tagList = tagList;
    }

    public TagAdapter(T[] tagList) {
        this.tagList = new ArrayList<>(Arrays.asList(tagList));
    }

    public void setTagList(List<T> tagList) {
        this.tagList = tagList;
        notifyDataChanged();
    }

    public interface OnDataChangedListener {
        void onChanged();
    }

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        mOnDataChangedListener = listener;
    }

    public void setSelectedList(int... pos) {
        for (int po : pos) mCheckedPosList.add(po);
        notifyDataChanged();
    }

    public HashSet<Integer> getPreCheckedList() {
        return mCheckedPosList;
    }

    public int getCount() {
        if (tagList != null && tagList.size() > 0) {
            return tagList.size();
        } else {
            return 0;
        }
    }

    public void notifyDataChanged() {
        mOnDataChangedListener.onChanged();
    }

    public T getItem(int position) {
        if (tagList != null && tagList.size() > 0) {
            return tagList.get(position);
        } else {
            return null;
        }
    }

    public long getItemId(int position) {
        return position;
    }

    public abstract View getView(FlowLayout parent, int position, T t);

}