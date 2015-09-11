package com.minglang.suiuu.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.PersonalMainPagerActivity;
import com.minglang.suiuu.activity.TripGalleryDetailsActivity;
import com.minglang.suiuu.entity.MsgTripGallery.MsgTripGalleryData.MsgTripGalleryItemData;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * Created by a on 2015/8/6.
 * <p/>
 * 旅途消息数据适配器
 */
public class MsgTripGalleryAdapter extends BaseHolderAdapter<MsgTripGalleryItemData> {

    private static final String USER_SIGN = "userSign";
    private static final String ID = "id";

    private Context context;

    public MsgTripGalleryAdapter(Context context, List<MsgTripGalleryItemData> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder helper, MsgTripGalleryItemData item, long position) {
        SimpleDraweeView headImageView = helper.getView(R.id.item_msg_trip_gallery_head_image_view);
        TextView userNameView = helper.getView(R.id.item_msg_trip_gallery_user_name);
        TextView inView = helper.getView(R.id.item_msg_trip_gallery_in);
        TextView infoView = helper.getView(R.id.item_msg_trip_gallery_info);
        TextView actionView = helper.getView(R.id.item_msg_trip_gallery_action);

        String headImagePath = item.getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            headImageView.setImageURI(Uri.parse(headImagePath));
        } else {
            headImageView.setImageURI(Uri.parse("res://com.minglang.suiuu/" + R.drawable.default_head_image_error));
        }

        String userName = item.getNickname();
        if (!TextUtils.isEmpty(userName)) {
            userNameView.setText(Html.fromHtml("<u>" + userName + "</u>"));
        } else {
            userNameView.setText("");
        }

        String info = item.getTitle();
        if (!TextUtils.isEmpty(info)) {
            inView.setVisibility(View.VISIBLE);
            infoView.setText(Html.fromHtml("<u>" + info + "</u>"));
        } else {
            inView.setVisibility(View.GONE);
            infoView.setText("");
        }

        String action = item.getRelativeType();
        if (!TextUtils.isEmpty(action)) {
            switch (action) {
                case "2":
                    actionView.setText("评论了你");
                    break;
                case "3":
                    actionView.setText("回复了你");
                    break;
                case "4":
                    actionView.setText("关注了你");
                    break;
                default:
                    actionView.setText("");
                    break;
            }
        } else {
            actionView.setText("");
        }

        headImageView.setOnClickListener(new HeadImageViewClickListener(item));
        userNameView.setOnClickListener(new UserNameViewClickListener(item));
        infoView.setOnClickListener(new InfoViewClickListener(item));

    }

    private class HeadImageViewClickListener implements View.OnClickListener {

        private MsgTripGalleryItemData item;

        private HeadImageViewClickListener(MsgTripGalleryItemData item) {
            this.item = item;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, PersonalMainPagerActivity.class);
            intent.putExtra(USER_SIGN, item.getCreateUserSign());
            context.startActivity(intent);
        }

    }

    private class UserNameViewClickListener implements View.OnClickListener {

        private MsgTripGalleryItemData item;

        private UserNameViewClickListener(MsgTripGalleryItemData item) {
            this.item = item;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, PersonalMainPagerActivity.class);
            intent.putExtra(USER_SIGN, item.getCreateUserSign());
            context.startActivity(intent);
        }

    }

    private class InfoViewClickListener implements View.OnClickListener {

        private MsgTripGalleryItemData item;

        private InfoViewClickListener(MsgTripGalleryItemData item) {
            this.item = item;
        }

        @Override
        public void onClick(View v) {
            DeBugLog.i(getClass().getSimpleName(), "RelativeId:" + item.getRelativeId());
            Intent intent = new Intent(context, TripGalleryDetailsActivity.class);
            intent.putExtra(ID, item.getRelativeId());
            context.startActivity(intent);
        }

    }

}