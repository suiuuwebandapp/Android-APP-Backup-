package com.minglang.suiuu.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.ImageFolder;
import com.minglang.suiuu.entity.ImageItem;

import java.util.List;

public class SelectPictureAdapter extends BaseAdapter {

    private static int MAX_NUM = 9;

    private Context context;

    private List<ImageItem> list;

    private ImageFolder currentImageFolder;

    private List<String> selectedPicture;

    private TextView complete;

    public SelectPictureAdapter(Context context, List<ImageItem> list, ImageFolder currentImageFolder,
                                List<String> selectedPicture, TextView complete) {
        this.context = context;
        this.list = list;
        this.currentImageFolder = currentImageFolder;
        this.selectedPicture = selectedPicture;
        this.complete = complete;
    }

    public void setImageFolder(ImageFolder currentImageFolder) {
        this.currentImageFolder = currentImageFolder;
        this.list = currentImageFolder.images;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_grid_select_picture, null);
            holder.iv = (SimpleDraweeView) convertView.findViewById(R.id.iv);
            holder.checkBox = (Button) convertView.findViewById(R.id.check);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == 0) {
            holder.iv.setImageResource(R.drawable.camera);
            holder.checkBox.setVisibility(View.INVISIBLE);
        } else {
            position = position - 1;
            holder.checkBox.setVisibility(View.VISIBLE);

            final ImageItem item = currentImageFolder.images.get(position);
            holder.iv.setImageURI(Uri.parse("file://com.minglang.suiuu/" + item.path));
            boolean isSelected = selectedPicture.contains(item.path);

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!v.isSelected() && selectedPicture.size() + 1 > MAX_NUM) {
                        Toast.makeText(context, "最多选择" + MAX_NUM + "张", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (selectedPicture.contains(item.path)) {
                        selectedPicture.remove(item.path);
                    } else {
                        selectedPicture.add(item.path);
                    }
                    complete.setText("完成" + selectedPicture.size() + "/" + MAX_NUM);
                    v.setSelected(selectedPicture.contains(item.path));
                }
            });

            holder.checkBox.setSelected(isSelected);
        }

        return convertView;
    }

    class ViewHolder {
        SimpleDraweeView iv;
        Button checkBox;
    }

}