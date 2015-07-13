package com.minglang.suiuu.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.SuiuuSearchActivity;
import com.minglang.suiuu.base.BaseFragment;
/**
 * 项目名称：Android-APP-Backup-
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/7/8 15:46
 * 修改人：Administrator
 * 修改时间：2015/7/8 15:46
 * 修改备注：
 */
public class TripGalleryFragment extends BaseFragment {
    private LinearLayout mGalleryLinearLayout;
    private TextView tv_trip_grllery_search;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trip_gallery, null);
        initView(rootView);
        initData();
        viewAction();
        return rootView;
    }



    private void initView(View rootView){
        mGalleryLinearLayout=(LinearLayout) rootView.findViewById(R.id.galleryLinearLayout);
        tv_trip_grllery_search = (TextView) rootView.findViewById(R.id.tv_trip_grllery_search);
    }
    private void initData(){
        int[] mPhotosIntArray = new int[]{R.drawable.tag_family,R.drawable.tag_shopping,R.drawable.tag_nature,R.drawable.tag_dangerous,R.drawable.tag_romantic,R.drawable.tag_museam,R.drawable.tag_novelty};
        final String[] mTagIntArray = new String[]{"家庭","购物","自然","惊险","浪漫","博物馆","猎奇"};
        View itemView=null;
        ImageView imageView=null;
        TextView textView;
        for (int i = 0; i < mPhotosIntArray.length; i++) {
            itemView=LayoutInflater.from(getActivity()).inflate(R.layout.item_trip_gallery_tag, null);
            itemView.setTag(i);
            imageView=(ImageView) itemView.findViewById(R.id.iv_item_trip_gallery_tag);
            textView=(TextView) itemView.findViewById(R.id.tv_item_trip_gallery_tag);
            imageView.setImageResource(mPhotosIntArray[i]);
            textView.setText(mTagIntArray[i]);
            itemView.setPadding(10, 0, 0, 0);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (int) v.getTag();
                    Toast.makeText(getActivity(),"点击了"+mTagIntArray[tag],Toast.LENGTH_SHORT).show();
                }
            });
            mGalleryLinearLayout.addView(itemView);
        }
    }
    private void viewAction() {
        tv_trip_grllery_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SuiuuSearchActivity.class);
                intent.putExtra("searchClass",1);
                startActivity(intent);
            }
        });
    }

}
