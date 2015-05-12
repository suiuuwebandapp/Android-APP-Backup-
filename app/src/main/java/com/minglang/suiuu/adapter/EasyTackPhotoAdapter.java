package com.minglang.suiuu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.SelectPictureActivity;
import com.minglang.suiuu.chat.activity.ShowBigImage;

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



    public EasyTackPhotoAdapter(Context context, List<String> list) {
        super();
        this.context = context;
        this.list = list;
        activity = (Activity) context;
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
            bitmapUtils.display(iv_picture, list.get(position));
        }
        et_pic_description.setHint(R.string.picture_description);

            iv_picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position == list.size()) {
                        Intent intent = new Intent(context, SelectPictureActivity.class);
                        activity.startActivityForResult(intent,0);
                    }else {
                        Intent showPicture = new Intent(context, ShowBigImage.class);
                        showPicture.putExtra("path", list.get(position));
                        showPicture.putExtra("isHuanXin",false);
                        activity.startActivity(showPicture);
                    }
                }
            });



        return view;
    }



}

