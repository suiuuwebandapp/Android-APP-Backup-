package com.minglang.suiuu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.minglang.suiuu.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 随问和随记公用的gridview的Adapter
 */
public class ShowGVPictureAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    private String type;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private BitmapUtils bitmapUtils;

    public ShowGVPictureAdapter(Context context,List<String> list,String type) {
        super();
        this.context = context;
        this.list = list;
        this.type = type;
        bitmapUtils = new BitmapUtils(context);
        if("1".equals(type)) {
            imageLoader = ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_suiuu_image)
                    .showImageForEmptyUri(R.drawable.default_suiuu_image).showImageOnFail(R.drawable.default_suiuu_image)
                    .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                    .imageScaleType(ImageScaleType.NONE_SAFE).bitmapConfig(Bitmap.Config.RGB_565).build();
        }
    }
    @Override
    public int getCount() {
        return list.size()+1;

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
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = View.inflate(context, R.layout.adapter_show_picture, null);
        ImageView iv_picture = (ImageView)view.findViewById(R.id.iv_picture);
        if(position>=list.size()) {
            iv_picture.setImageResource(R.drawable.btn_add_picture);
        }else {
            if("1".equals(type)) {
                imageLoader.displayImage(list.get(position), iv_picture, options);
            }else {
                bitmapUtils.display(iv_picture, list.get(position));
            }
        }
        return view;
    }


}
