package com.minglang.suiuu.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
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
import com.minglang.suiuu.utils.ViewHolder;

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
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_grid_select_picture, position);
        convertView = holder.getConvertView();

        SimpleDraweeView iv = holder.getView(R.id.iv);
        Button checkBox = holder.getView(R.id.check);

        if (position == 0) {
            iv.setImageResource(R.drawable.camera);
            checkBox.setVisibility(View.INVISIBLE);
        } else {
            position = position - 1;
            checkBox.setVisibility(View.VISIBLE);

            final ImageItem item = currentImageFolder.images.get(position);
            iv.setImageURI(Uri.parse("file://com.minglang.suiuu/" + item.path));
            boolean isSelected = selectedPicture.contains(item.path);

            checkBox.setOnClickListener(new View.OnClickListener() {
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
                    complete.setText(String.format("%s%s%s%s", "完成", selectedPicture.size(), "/", MAX_NUM));
                    v.setSelected(selectedPicture.contains(item.path));
                }
            });

            checkBox.setSelected(isSelected);
        }

        return convertView;
    }

}