package com.minglang.suiuu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.minglang.suiuu.R;

/**
 * 临时数据适配器
 * <p/>
 * Created by Administrator on 2015/7/14.
 */
public class TempAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    public TempAdapter(Context context) {
        this.context = context;
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
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "position:" + index, Toast.LENGTH_SHORT).show();
            }
        });
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