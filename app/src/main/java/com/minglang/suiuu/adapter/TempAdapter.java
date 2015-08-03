package com.minglang.suiuu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minglang.suiuu.R;
import com.minglang.suiuu.interfaces.RecyclerViewOnItemClickListener;

/**
 * 临时数据适配器
 * <p/>
 * Created by Administrator on 2015/7/14.
 */
public class TempAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecyclerViewOnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RecyclerViewOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_temp_personal, parent, false);
        return new InnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final View view = holder.itemView;
        final int index = position;

        if (onItemClickListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(view, index);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    private class InnerViewHolder extends RecyclerView.ViewHolder {
        public InnerViewHolder(View itemView) {
            super(itemView);
        }
    }

}