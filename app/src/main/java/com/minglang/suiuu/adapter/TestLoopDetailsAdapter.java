package com.minglang.suiuu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.OtherUserActivity;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.LoopDetailsDataList;
import com.minglang.suiuu.utils.ScreenUtils;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

public class TestLoopDetailsAdapter extends BaseAdapter {

    private static final String TAG = TestLoopDetailsAdapter.class.getSimpleName();

    private Context context;

    private List<LoopDetailsDataList> list;

    private ImageLoader loader;

    private DisplayImageOptions displayImageOptions1, displayImageOptions2;

    private int screenWidth, screenHeight;

    public TestLoopDetailsAdapter(Context context) {
        this.context = context;
        loader = ImageLoader.getInstance();
        if (!loader.isInited()) {
            loader.init(ImageLoaderConfiguration.createDefault(context));
        }

        displayImageOptions1 = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.user_background)
                .showImageForEmptyUri(R.drawable.user_background).showImageOnFail(R.drawable.user_background)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();

        displayImageOptions2 = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_head_image)
                .showImageForEmptyUri(R.drawable.default_head_image).showImageOnFail(R.drawable.default_head_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();

        screenWidth = new ScreenUtils((Activity) context).getScreenWidth();
        screenHeight = new ScreenUtils((Activity) context).getScreenHeight();
    }

    public void setDataList(List<LoopDetailsDataList> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if (list != null && list.size() > 0) {
            return list.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LoopDetailsDataList loopDetailsDataList = list.get(position);

        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_test_loop_details, position);
        convertView = holder.getConvertView();

        AbsListView.LayoutParams params = new AbsListView.LayoutParams(screenWidth / 2, screenHeight / 3);
        convertView.setLayoutParams(params);

        ImageView mainImageView = holder.getView(R.id.item_test_loop_details_image);
        CircleImageView headImageView = holder.getView(R.id.item_test_loop_details_head_image);

        TextView userName = holder.getView(R.id.item_test_loop_details_user_name);
        TextView title = holder.getView(R.id.item_test_loop_details_title);
        TextView praise = holder.getView(R.id.item_test_loop_details_praise);
        TextView comments = holder.getView(R.id.item_test_loop_details_comments);

        String imagePath = loopDetailsDataList.getaImg();
        if (!TextUtils.isEmpty(imagePath)) {
            loader.displayImage(imagePath, mainImageView, displayImageOptions1);
        }

        String headImagePath = loopDetailsDataList.getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            loader.displayImage(headImagePath, headImageView, displayImageOptions2);
        }

        String str_userName = loopDetailsDataList.getNickname();
        if (TextUtils.isEmpty(str_userName)) {
            userName.setText("匿名");
        } else {
            userName.setText(str_userName);
        }

        String str_title = loopDetailsDataList.getaTitle();
        if (TextUtils.isEmpty(str_title)) {
            title.setText("暂无标题");
        } else {
            Log.i(TAG, "标题:" + str_title);
            title.setText(str_title);
        }

        String comments_str = loopDetailsDataList.getaCmtCount();
        if (TextUtils.isEmpty(comments_str)) {
            comments.setText("0");
        } else {
            comments.setText(comments_str);
        }

        String praise_str = loopDetailsDataList.getaSupportCount();
        if (TextUtils.isEmpty(praise_str)) {
            praise.setText("0");
        } else {
            praise.setText(praise_str);
        }

        headImageView.setTag(position);
        headImageView.setOnClickListener(new MyClickListener(position));

        return convertView;
    }

    private class MyClickListener implements View.OnClickListener {

        private int index;

        private MyClickListener(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            String userSign = list.get(index).getaCreateUserSign();
            Log.i(TAG, "CreateUserSign:" + userSign);
            Intent intent = new Intent(context, OtherUserActivity.class);
            intent.putExtra("userSign", userSign);
            context.startActivity(intent);
        }
    }

}
