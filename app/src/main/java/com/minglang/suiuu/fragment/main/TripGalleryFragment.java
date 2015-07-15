package com.minglang.suiuu.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.SuiuuSearchActivity;
import com.minglang.suiuu.adapter.TripGalleryAdapter;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.customview.NoScrollBarListView;
import com.minglang.suiuu.entity.TripGallery;

import java.util.ArrayList;
import java.util.List;

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
    private NoScrollBarListView lv_trip_gallery;
    private TripGalleryAdapter adapter;
    private List<TripGallery> tripGalleryList = new ArrayList<>();
    private ScrollView sv_trip_gallery;
    private View footer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trip_gallery, null);
        initView(rootView);
        initData();
        viewAction();
        TripGallery tg = new TripGallery();
        tg.setHeadPotrait("http://file-www.sioe.cn/201109/14/222299785.jpg");
        tg.setHeadUrl("http://h.hiphotos.baidu.com/baike/c0%3Dbaike92%2C5%2C5%2C92%2C30/sign=13643272a6cc7cd9ee203c8b58684a5a/d1160924ab18972b6bcc2955e3cd7b899f510abe.jpg");
        tg.setLocationDistance("普吉岛\n11KM");
        tg.setLoveNumber("20");
        tg.setTripGalleryName("带你畅玩普吉岛");
        tg.setTripGalleryTag("自然   惊奇");
        TripGallery tg1 = new TripGallery();
        tg1.setHeadPotrait("http://file-www.sioe.cn/201109/14/222299785.jpg");
        tg1.setHeadUrl("http://www.9tour.cn/UploadFile/news/2009-12-23/3131538734.jpg");
        tg1.setLocationDistance("普吉岛\n11KM");
        tg1.setLoveNumber("20");
        tg1.setTripGalleryName("带你畅玩普吉岛");
        tg1.setTripGalleryTag("自然   惊奇");
        TripGallery tg2 = new TripGallery();
        tg2.setHeadPotrait("http://file-www.sioe.cn/201109/14/222299785.jpg");
        tg2.setHeadUrl("http://f.hiphotos.baidu.com/image/pic/item/58ee3d6d55fbb2fb06ad82a74d4a20a44723dc85.jpg");
        tg2.setLocationDistance("普吉岛\n11KM");
        tg2.setLoveNumber("20");
        tg2.setTripGalleryName("带你畅玩普吉岛");
        tg2.setTripGalleryTag("自然   惊奇");
        TripGallery tg3 = new TripGallery();
        tg3.setHeadPotrait("http://file-www.sioe.cn/201109/14/222299785.jpg");
        tg3.setHeadUrl("http://pic41.nipic.com/20140522/18818002_090044572197_2.jpg");
        tg3.setLocationDistance("普吉岛\n11KM");
        tg3.setLoveNumber("20");
        tg3.setTripGalleryName("带你畅玩普吉岛");
        tg3.setTripGalleryTag("自然   惊奇");
        TripGallery tg4 = new TripGallery();
        tg4.setHeadPotrait("http://file-www.sioe.cn/201109/14/222299785.jpg");
        tg4.setHeadUrl("http://www.3487.com/res/allimg/110622/1153022227-1.jpg");
        tg4.setLocationDistance("普吉岛\n11KM");
        tg4.setLoveNumber("20");
        tg4.setTripGalleryName("带你畅玩普吉岛");
        tg4.setTripGalleryTag("自然   惊奇");
        tripGalleryList.add(tg);
        tripGalleryList.add(tg1);
        tripGalleryList.add(tg2);
        tripGalleryList.add(tg3);
        tripGalleryList.add(tg4);
        showList(tripGalleryList);
        return rootView;
    }
    private void initView(View rootView) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        mGalleryLinearLayout = (LinearLayout) rootView.findViewById(R.id.galleryLinearLayout);
        tv_trip_grllery_search = (TextView) rootView.findViewById(R.id.tv_trip_grllery_search);
        lv_trip_gallery = (NoScrollBarListView) rootView.findViewById(R.id.lv_trip_gallery);
        sv_trip_gallery = (ScrollView) rootView.findViewById(R.id.sv_trip_gallery);
        sv_trip_gallery.smoothScrollTo(0, 0);
        footer = inflater.inflate(R.layout.footer_layout, null);
        lv_trip_gallery.addFooterView(footer);
    }

    private void initData() {
        int[] mPhotosIntArray = new int[]{R.drawable.tag_family, R.drawable.tag_shopping, R.drawable.tag_nature, R.drawable.tag_dangerous, R.drawable.tag_romantic, R.drawable.tag_museam, R.drawable.tag_novelty};
        final String[] mTagIntArray = new String[]{"家庭", "购物", "自然", "惊险", "浪漫", "博物馆", "猎奇"};
        View itemView = null;
        ImageView imageView = null;
        TextView textView;
        for (int i = 0; i < mPhotosIntArray.length; i++) {
            itemView = LayoutInflater.from(getActivity()).inflate(R.layout.item_trip_gallery_tag, null);
            itemView.setTag(i);
            imageView = (ImageView) itemView.findViewById(R.id.iv_item_trip_gallery_tag);
            textView = (TextView) itemView.findViewById(R.id.tv_item_trip_gallery_tag);
            imageView.setImageResource(mPhotosIntArray[i]);
            textView.setText(mTagIntArray[i]);
            itemView.setPadding(10, 0, 0, 0);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (int) v.getTag();
                    Toast.makeText(getActivity(), "点击了" + mTagIntArray[tag], Toast.LENGTH_SHORT).show();
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
                intent.putExtra("searchClass", 1);
                startActivity(intent);
            }
        });
        lv_trip_gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("suiuu", "点击的position=" + position);
                if(position == tripGalleryList.size()) {
                    Toast.makeText(getActivity(),"加载更多",Toast.LENGTH_SHORT).show();
                    TripGallery tg6 = new TripGallery();
                    tg6.setHeadPotrait("http://file-www.sioe.cn/201109/14/222299785.jpg");
                    tg6.setLocationDistance("普吉岛\n11KM");
                    tg6.setHeadUrl("http://image.tianjimedia.com/uploadImages/2012/096/KX88V30738W0.jpg");
                    tg6.setLoveNumber("20");
                    tg6.setTripGalleryName("带你畅玩普吉岛");
                    tg6.setTripGalleryTag("自然   惊奇");
                    tripGalleryList.add(tg6);
                    TripGallery tg7 = new TripGallery();
                    tg7.setHeadPotrait("http://file-www.sioe.cn/201109/14/222299785.jpg");
                    tg7.setHeadUrl("http://www.9tour.cn/UploadFile/news/2009-12-23/3131538734.jpg");
                    tg7.setLocationDistance("普吉岛\n11KM");
                    tg7.setLoveNumber("20");
                    tg7.setTripGalleryName("带你畅玩普吉岛");
                    tg7.setTripGalleryTag("自然   惊奇");
                    tripGalleryList.add(tg7);
                    showList(tripGalleryList);
                }
            }
        });
    }

    private void showList(List<TripGallery> tripGalleryList) {
        if (adapter == null) {
            adapter = new TripGalleryAdapter(getActivity().getApplication(), tripGalleryList);
            lv_trip_gallery.setAdapter(adapter);
        } else {
            adapter.onDateChange(tripGalleryList);
        }
    }

}
