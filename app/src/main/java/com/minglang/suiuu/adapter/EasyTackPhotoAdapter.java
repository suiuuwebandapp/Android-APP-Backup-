package com.minglang.suiuu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.SelectPictureActivity;
import com.minglang.suiuu.chat.activity.ShowBigImage;
import com.minglang.suiuu.customview.swipelistview.SwipeListView;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 随问和随记公用的gridview的Adapter
 */
public class EasyTackPhotoAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    private Activity activity;
    private String type;
    private ImageLoader imageLoader;

    private DisplayImageOptions options;
    private List<String> changeContentList;

    private SwipeListView swipeListView;

    public EasyTackPhotoAdapter(Context context, List<String> list, List<String> changeContentList, String type) {
        super();
        this.context = context;
        this.list = list;
        activity = (Activity) context;
        this.type = type;
        this.changeContentList = changeContentList;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));

        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.loading)
                .showImageForEmptyUri(R.drawable.loading)
                .showImageOnFail(R.drawable.loading_error)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.NONE_SAFE).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public EasyTackPhotoAdapter(Context context, List<String> list, String type) {
        super();
        this.context = context;
        this.list = list;
        activity = (Activity) context;
        this.type = type;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));

        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_suiuu_image)
                .showImageForEmptyUri(R.drawable.default_suiuu_image)
                .showImageOnFail(R.drawable.default_suiuu_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.NONE_SAFE).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public void setSwipeListView(SwipeListView swipeListView) {
        this.swipeListView = swipeListView;
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BitmapUtils bitmapUtils = new BitmapUtils(context);
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_easy_tack_photo, position);
        convertView = holder.getConvertView();

        ImageView pictureView = holder.getView(R.id.item_tack_photo);
        EditText picDescriptionView = holder.getView(R.id.item_tack_description);
        Button picRemoveView = holder.getView(R.id.item_tack_remove);

        if (position >= list.size()) {
            pictureView.setImageResource(R.drawable.btn_add_picture2);
        } else {
            if ("1".equals(type)) {
                picDescriptionView.setText(changeContentList.get(position));
                imageLoader.displayImage(list.get(position), pictureView, options);
            } else {
                bitmapUtils.display(pictureView, list.get(position));
            }
        }

        picDescriptionView.setHint(R.string.picture_description);

        pictureView.setOnClickListener(new pictureViewClick(position));

        picRemoveView.setOnClickListener(new picRemoveViewClick(position));

        return convertView;
    }

    private class pictureViewClick implements View.OnClickListener{

        private int position;

        private pictureViewClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (position == list.size()) {
                Intent intent = new Intent(context, SelectPictureActivity.class);
                activity.startActivityForResult(intent, 0);
            } else {
                Intent showPicture = new Intent(context, ShowBigImage.class);
                showPicture.putExtra("remotepath", list.get(position));
                showPicture.putExtra("isHuanXin", false);
                activity.startActivity(showPicture);
            }
        }
    }

    private class picRemoveViewClick implements View.OnClickListener{

        private int position;

        private picRemoveViewClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            list.remove(position);
            notifyDataSetChanged();

            swipeListView.closeOpenedItems();
        }
    }

}