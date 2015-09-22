package com.minglang.suiuu.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.SuiuuSearchActivity;
import com.minglang.suiuu.entity.TripImage.TripImageData.TripImageItemData;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.AppUtils;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 项目名称：Android-APP-Backup-
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/7/15 11:29
 * 修改人：Administrator
 * 修改时间：2015/7/15 11:29
 * 修改备注：
 */
public class TripImageAdapter extends BaseAdapter {

    public static final int SEARCH = 0;
    public static final int SET_TAG = 1;
    public static final int DATA = 2;

    private static final String LAT = "lat";
    private static final String LNG = "lng";

    private static final String HOME_PAGE = "homePage";

    private Context context;
    private List<TripImageItemData> list;

    private String lat;
    private String lng;

    private List<ImageView> clickImageList = new ArrayList<>();
    private List<ImageView> imageList = new ArrayList<>();
    private List<String> clickString = new ArrayList<>();

    private String type;
    private LoadChoiceTag loadChoiceTag;

    public TripImageAdapter(Context context, List<TripImageItemData> list, String type, List<String> clickString) {
        this.context = context;
        this.list = list;
        Map map = SuiuuInfo.ReadUserLocation(context);
        this.lat = (String) map.get(LAT);
        this.lng = (String) map.get(LNG);
        this.type = type;
        this.clickString = clickString;

    }

    public void updateData(List<TripImageItemData> list, String type, List<String> clickString) {
        this.list = list;
        this.type = type;
        this.clickString = clickString;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list != null && list.size() > 0) {
            if (HOME_PAGE.equals(type)) {
                return list.size() + 2;
            } else {
                return list.size();
            }
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
        if (HOME_PAGE.equals(type)) {
            //顶部的搜索页面
            int type = getItemViewType(position);
            switch (type) {
                case SEARCH:
                    ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_trip_image1, position);
                    convertView = holder.getConvertView();

                    TextView tripImageSearch = holder.getView(R.id.trip_image_search);
                    tripImageSearch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, SuiuuSearchActivity.class);
                            intent.putExtra("searchClass", 1);
                            context.startActivity(intent);
                        }
                    });
                    break;

                case SET_TAG:
                    ViewHolder holder1 = ViewHolder.get(context, convertView, parent, R.layout.item_trip_image2, position);
                    convertView = holder1.getConvertView();
                    LinearLayout showImageLayout = holder1.getView(R.id.show_image_layout);
                    showTag(showImageLayout);
                    break;

                case DATA:
                    ViewHolder holderData = ViewHolder.get(context, convertView, parent, R.layout.item_trip_image, position);
                    convertView = holderData.getConvertView();

                    SimpleDraweeView picContent = holderData.getView(R.id.trip_image_item_picture);
                    SimpleDraweeView headImageView = holderData.getView(R.id.trip_image_head_portrait);
                    TextView distanceView = holderData.getView(R.id.trip_image_location_distance);
                    TextView titleView = holderData.getView(R.id.trip_image_title);
                    TextView tagView = holderData.getView(R.id.trip_image_tag);
                    TextView loveNumberView = holderData.getView(R.id.trip_image_love_number);

                    TripImageItemData itemData = list.get(position - 2);

                    String longitude = itemData.getLon();
                    String latitude = itemData.getLat();
                    String cityName = itemData.getCity();
                    if (!TextUtils.isEmpty(longitude) && !TextUtils.isEmpty(latitude) && !TextUtils.isEmpty(cityName)) {
                        double distance = AppUtils.distanceByLngLat(Double.valueOf(lng), Double.valueOf(lat),
                                Double.valueOf(longitude), Double.valueOf(latitude));
                        distanceView.setText(cityName + "\n" + distance + "KM");
                    } else {
                        distanceView.setText("");
                    }

                    String title = itemData.getTitle();
                    if (!TextUtils.isEmpty(title)) {
                        titleView.setText(title);
                    } else {
                        titleView.setText("");
                    }

                    String tags = itemData.getTags();
                    if (!TextUtils.isEmpty(tags)) {
                        tagView.setText(tags.replace(",", " "));
                    } else {
                        tagView.setText("");
                    }

                    String attentionCount = itemData.getAttentionCount();
                    if (!TextUtils.isEmpty(attentionCount)) {
                        loveNumberView.setText(attentionCount);
                    } else {
                        loveNumberView.setText("");
                    }

                    String titleImagePath = itemData.getTitleImg();
                    if (!TextUtils.isEmpty(titleImagePath)) {
                        picContent.setImageURI(Uri.parse(titleImagePath));
                    } else {
                        picContent.setImageURI(Uri.parse("res://com.suiuu.minglang/" + R.drawable.loading_error));
                    }

                    String headImagePath = itemData.getHeadImg();
                    if (!TextUtils.isEmpty(headImagePath)) {
                        headImageView.setImageURI(Uri.parse(itemData.getHeadImg()));
                    } else {
                        headImageView.setImageURI(Uri.parse("res://com.suiuu.minglang/" + R.drawable.default_head_image_error));
                    }

                    break;

                default:
                    break;
            }
            return convertView;
        } else {
            ViewHolder holderData = ViewHolder.get(context, convertView, parent, R.layout.item_trip_image, position);
            convertView = holderData.getConvertView();

            SimpleDraweeView picContent = holderData.getView(R.id.trip_image_item_picture);
            SimpleDraweeView headImageView = holderData.getView(R.id.trip_image_head_portrait);
            TextView distanceView = holderData.getView(R.id.trip_image_location_distance);
            TextView titleView = holderData.getView(R.id.trip_image_title);
            TextView tagView = holderData.getView(R.id.trip_image_tag);
            TextView loveNumberView = holderData.getView(R.id.trip_image_love_number);

            TripImageItemData itemData = list.get(position);

            String longitude = itemData.getLon();
            String latitude = itemData.getLat();
            String cityName = itemData.getCity();
            if (!TextUtils.isEmpty(longitude) && !TextUtils.isEmpty(latitude) && !TextUtils.isEmpty(cityName)) {
                double distance = AppUtils.distanceByLngLat(Double.valueOf(lng), Double.valueOf(lat),
                        Double.valueOf(longitude), Double.valueOf(latitude));
                distanceView.setText(cityName + "\n" + distance + "KM");
            } else {
                distanceView.setText("");
            }

            String title = itemData.getTitle();
            if (!TextUtils.isEmpty(title)) {
                titleView.setText(title);
            } else {
                titleView.setText("");
            }

            String tags = itemData.getTags();
            if (!TextUtils.isEmpty(tags)) {
                tagView.setText(tags.replace(",", " "));
            } else {
                tagView.setText("");
            }

            String attentionCount = itemData.getAttentionCount();
            if (!TextUtils.isEmpty(attentionCount)) {
                loveNumberView.setText(attentionCount);
            } else {
                loveNumberView.setText("");
            }

            String titleImagePath = itemData.getTitleImg();
            if (!TextUtils.isEmpty(titleImagePath)) {
                picContent.setImageURI(Uri.parse(titleImagePath));
            } else {
                picContent.setImageURI(Uri.parse("res://com.suiuu.minglang/" + R.drawable.loading_error));
            }

            String headImagePath = itemData.getHeadImg();
            if (!TextUtils.isEmpty(headImagePath)) {
                headImageView.setImageURI(Uri.parse(itemData.getHeadImg()));
            } else {
                headImageView.setImageURI(Uri.parse("res://com.suiuu.minglang/" + R.drawable.default_head_image_error));
            }

            return convertView;
        }
    }

    private void showTag(LinearLayout mGalleryLinearLayout) {
        mGalleryLinearLayout.removeAllViews();

        int[] mPhotosIntArray = new int[]{R.drawable.tag_family, R.drawable.tag_shopping, R.drawable.tag_nature,
                R.drawable.tag_dangerous, R.drawable.tag_romantic, R.drawable.tag_museam, R.drawable.tag_novelty};

        final String[] mTagIntArray = context.getResources().getStringArray(R.array.tripGalleryTagName);

        View itemView;
        ImageView imageView;
        TextView textView;

        for (int i = 0; i < mPhotosIntArray.length; i++) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_trip_gallery_tag, null);
            itemView.setTag(i);

            imageView = (ImageView) itemView.findViewById(R.id.iv_item_trip_gallery_tag);
            textView = (TextView) itemView.findViewById(R.id.tv_item_trip_gallery_tag);

            imageList.add(imageView);
            imageView.setImageResource(mPhotosIntArray[i]);
            textView.setText(mTagIntArray[i]);

            if (clickString.contains(mTagIntArray[i])) {
                imageView.setBackgroundResource(R.drawable.image_view_border_style);
            } else {
                imageView.setBackgroundResource(0);
            }

            itemView.setPadding(10, 0, 0, 0);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (int) v.getTag();
                    if (clickString.contains(mTagIntArray[tag])) {
                        imageList.get(tag).setBackgroundResource(0);
                        clickImageList.remove(imageList.get(tag));
                        clickString.remove(mTagIntArray[tag]);
                    } else {
                        imageList.get(tag).setBackgroundResource(R.drawable.image_view_border_style);
                        clickImageList.add(imageList.get(tag));
                        clickString.add(mTagIntArray[tag]);
                    }

                    loadChoiceTag.getClickTagList(clickString);
                }
            });

            mGalleryLinearLayout.addView(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position > 1) {
            return DATA;
        }
        return position;
    }

    /**
     * 返回所有的layout的数量
     */
    @Override
    public int getViewTypeCount() {
        if (HOME_PAGE.equals(type)) {
            return 3;
        } else {
            return 1;
        }
    }

    public void setLoadChoiceTagInterface(LoadChoiceTag loadChoiceTag) {
        this.loadChoiceTag = loadChoiceTag;
    }

    public interface LoadChoiceTag {
        void getClickTagList(List<String> tagList);
    }

}