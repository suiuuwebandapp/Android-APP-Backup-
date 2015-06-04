package com.minglang.suiuu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.SelectPictureActivity;
import com.minglang.suiuu.chat.activity.ShowBigImage;
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
                .showImageForEmptyUri(R.drawable.loading).showImageOnFail(R.drawable.loading_error)
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
                .showImageForEmptyUri(R.drawable.default_suiuu_image).showImageOnFail(R.drawable.default_suiuu_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.NONE_SAFE).bitmapConfig(Bitmap.Config.RGB_565).build();
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
        // TODO Auto-generated method stub
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        BitmapUtils bitmapUtils = new BitmapUtils(context);
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_easy_tackphoto, position);
        convertView = holder.getConvertView();

        ImageView iv_picture = holder.getView(R.id.iv_item_tackphoto);
        EditText et_pic_description = holder.getView(R.id.et_item_description);

        if (position >= list.size()) {
            iv_picture.setImageResource(R.drawable.btn_add_picture2);
        } else {
            if ("1".equals(type)) {
                et_pic_description.setText(changeContentList.get(position));
                imageLoader.displayImage(list.get(position), iv_picture, options);
            } else {
                bitmapUtils.display(iv_picture, list.get(position));
            }
        }

        et_pic_description.setHint(R.string.picture_description);

        iv_picture.setOnClickListener(new View.OnClickListener() {
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
        });

        return convertView;
    }


}

