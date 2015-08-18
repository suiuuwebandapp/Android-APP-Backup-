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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.SelectPictureActivity;
import com.minglang.suiuu.activity.ShowBigImage;
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

    public void onDateChange(List<String> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public void setSwipeListView(SwipeListView swipeListView) {
        this.swipeListView = swipeListView;
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


        if ("1".equals(type)) {
            picDescriptionView.setText(changeContentList.get(position));
            imageLoader.displayImage(list.get(position), pictureView, options);
        } else {
            bitmapUtils.display(pictureView, list.get(position));
        }
        picDescriptionView.setHint(R.string.picture_description);
        pictureView.setOnClickListener(new pictureViewClick(position));
        picRemoveView.setOnClickListener(new picRemoveViewClick(position, this));
        return convertView;
    }

    private class pictureViewClick implements View.OnClickListener {

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

    private class picRemoveViewClick implements View.OnClickListener {

        private int position;
        private EasyTackPhotoAdapter easyTackPhotoAdapter;

        private picRemoveViewClick(int position, EasyTackPhotoAdapter easyTackPhotoAdapter) {
            this.position = position;
            this.easyTackPhotoAdapter = easyTackPhotoAdapter;
        }

        @Override
        public void onClick(View v) {
            if (list.size() <= 1) {
                Toast.makeText(context, "至少选择一张图片", Toast.LENGTH_SHORT).show();
            } else {
                list.remove(position);
                swipeListView.closeOpenedItems();
                setListViewHeight(swipeListView);
            }
        }
    }

    // 重新计算子listview的高度
    public void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)) + 10;
        listView.setLayoutParams(params);
    }

}