package com.minglang.suiuu.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.customview.LinearLayoutBaseAdapter;
import com.minglang.suiuu.entity.MainDynamicDataRecommendTravel;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * 热门动态数据适配器
 * <p/>
 * Created by Administrator on 2015/5/3.
 */
public class RecommendTravelAdapter extends LinearLayoutBaseAdapter {

    private List<MainDynamicDataRecommendTravel> list;

    private ImageLoader imageLoader;

    public RecommendTravelAdapter(Context context, List<MainDynamicDataRecommendTravel> list) {
        super(context, list);
        this.list = list;

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }

    @Override
    public View getView(int position) {

        MainDynamicDataRecommendTravel recommendTravel = list.get(position);

        View view = getLayoutInflater().inflate(R.layout.item_recommend_reavel, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.item_recommend_travel_image);
        CircleImageView circleImageView = (CircleImageView) view.findViewById(R.id.item_recommend_travel_head);
        TextView title = (TextView) view.findViewById(R.id.item_recommend_travel_title);
        TextView content = (TextView) view.findViewById(R.id.item_recommend_travel_content);

        String imagePath = recommendTravel.getTitleImg();
        if (!TextUtils.isEmpty(imagePath)) {
            imageLoader.displayImage(imagePath, imageView);
        }

        String headPath = recommendTravel.getHeadImg();
        if (!TextUtils.isEmpty(headPath)) {
            imageLoader.displayImage(headPath, circleImageView);
        }

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

        return view;
    }
}
