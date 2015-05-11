package com.minglang.suiuu.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.customview.LinearLayoutBaseAdapter;
import com.minglang.suiuu.entity.MainDynamicDataRecommendTravel;
import com.minglang.suiuu.fragment.main.MainFragment;
import com.minglang.suiuu.utils.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 热门动态数据适配器
 * <p/>
 * Created by Administrator on 2015/5/3.
 */
public class RecommendTravelAdapter extends LinearLayoutBaseAdapter {

    private Context context;

    private List<MainDynamicDataRecommendTravel> list;

    private ImageLoader imageLoader;

    private DisplayImageOptions options1, options2;

    private int ScreenHeight;

    public RecommendTravelAdapter(Context context, List<MainDynamicDataRecommendTravel> list) {
        super(context, list);
        this.context = context;
        this.list = list;

        imageLoader = ImageLoader.getInstance();
        if (!imageLoader.isInited()) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        }

        options1 = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.user_background)
                .showImageForEmptyUri(R.drawable.user_background).showImageOnFail(R.drawable.user_background)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();

        options2 = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_head_image)
                .showImageForEmptyUri(R.drawable.default_head_image).showImageOnFail(R.drawable.default_head_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();

    }

    public void setScreenHeight(int ScreenHeight) {
        this.ScreenHeight = ScreenHeight;
    }

    @Override
    public View getView(int position) {

        MainDynamicDataRecommendTravel recommendTravel = list.get(position);

        @SuppressLint("InflateParams")
        View view = getLayoutInflater().inflate(R.layout.item_recommend_reavel, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.item_recommend_travel_image);
        CircleImageView circleImageView = (CircleImageView) view.findViewById(R.id.item_recommend_travel_head);
        TextView title = (TextView) view.findViewById(R.id.item_recommend_travel_title);
        TextView content = (TextView) view.findViewById(R.id.item_recommend_travel_content);

        String imagePath = recommendTravel.getTitleImg();
        if (!TextUtils.isEmpty(imagePath)) {
            imageLoader.displayImage(imagePath, imageView, options1);
        }
        Log.i(MainFragment.class.getSimpleName(), "热门推荐图片URL:" + imagePath);

        String headPath = recommendTravel.getHeadImg();
        if (!TextUtils.isEmpty(headPath)) {
            imageLoader.displayImage(headPath, circleImageView, options2);
        }
        Log.i(MainFragment.class.getSimpleName(), "热门推荐头像URL:" + headPath);

        String strTitle = recommendTravel.getTitle();
        if (!TextUtils.isEmpty(strTitle)) {
            title.setText(strTitle);
        } else {
            title.setText("");
        }

        String strIntro = recommendTravel.getIntro();
        if (!TextUtils.isEmpty(strIntro)) {
            content.setText(strIntro);
        } else {
            content.setText("");
        }

        int itemHeight = ScreenHeight / 4 - Utils.newInstance(context).dip2px(10);

        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight);
        view.setLayoutParams(params);

        return view;
    }
}
