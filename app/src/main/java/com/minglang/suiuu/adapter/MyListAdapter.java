package com.minglang.suiuu.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.LoopBaseData;

import java.util.List;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/5/12 14:39
 * 修改人：Administrator
 * 修改时间：2015/5/12 14:39
 * 修改备注：
 */
public class MyListAdapter extends BaseAdapter {

    private static final String TAG = MyListAdapter.class.getSimpleName();

    private List<LoopBaseData> list;
    private Context context;

    public MyListAdapter(Context context, List<LoopBaseData> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder")
        View view = View.inflate(context, R.layout.adapter_simple_string, null);
        TextView tv_theme = (TextView) view.findViewById(R.id.tv_theme);
        tv_theme.setText(list.get(position).getcName());
        Log.i(TAG, list.get(position).getcName() + "----------");
        return view;
    }

}

