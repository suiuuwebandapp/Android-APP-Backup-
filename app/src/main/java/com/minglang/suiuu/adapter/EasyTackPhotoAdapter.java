package com.minglang.suiuu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.SelectPictureActivity;
import com.minglang.suiuu.chat.activity.ShowBigImage;
import com.minglang.suiuu.utils.AppConstant;
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
    private ImageView iv_picture;
    private EditText et_pic_description;
    private Activity activity;
    private String type;
    private ImageLoader imageLoader;

    private DisplayImageOptions options;
    private List<String> changeContentList;

    public EasyTackPhotoAdapter(Context context, List<String> list, List<String> changeContentList,String type) {
        super();
        this.context = context;
        this.list = list;
        activity = (Activity) context;
        this.type = type;
        this.changeContentList = changeContentList;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));

        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_suiuu_image)
                .showImageForEmptyUri(R.drawable.default_suiuu_image).showImageOnFail(R.drawable.default_suiuu_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.NONE_SAFE).bitmapConfig(Bitmap.Config.RGB_565).build();
    }
    public EasyTackPhotoAdapter(Context context, List<String> list, String type) {
        super();
        this.context = context;
        this.list = list;
        activity = (Activity) context;
        this.type = type;
        this.changeContentList = changeContentList;
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
        View view = View.inflate(context, R.layout.item_easy_tackphoto, null);
        iv_picture = (ImageView) view.findViewById(R.id.iv_item_tackphoto);
        et_pic_description = (EditText) view.findViewById(R.id.et_item_description);

        if (position >= list.size()) {
            iv_picture.setImageResource(R.drawable.btn_add_picture2);
        } else {
            if ("1".equals(type)) {
                Log.i("suiuu", "图片地址=" + AppConstant.IMG_FROM_SUIUU_CONTENT + list.get(position));
                et_pic_description.setText(changeContentList.get(position));
                imageLoader.displayImage(AppConstant.IMG_FROM_SUIUU_CONTENT + list.get(position), iv_picture, options);
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
                    showPicture.putExtra("path", list.get(position));
                    showPicture.putExtra("isHuanXin", false);
                    activity.startActivity(showPicture);
                }
            }
        });


        return view;
    }


}

