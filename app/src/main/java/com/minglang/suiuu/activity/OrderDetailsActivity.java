package com.minglang.suiuu.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.customview.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 订单详情页面-普通用户
 */
public class OrderDetailsActivity extends BaseAppCompatActivity {

    @Bind(R.id.order_details_money)
    TextView orderDetailsMoney;

    @Bind(R.id.order_details_title)
    TextView orderDetailsTitle;

    @Bind(R.id.order_details_indicator)
    RatingBar orderDetailsIndicator;

    @Bind(R.id.order_details_head_image_view)
    CircleImageView orderDetailsHeadImageView;

    @Bind(R.id.order_details_name)
    TextView orderDetailsName;

    @Bind(R.id.order_details_state)
    TextView orderDetailsState;

    @Bind(R.id.order_details_phone)
    TextView orderDetailsPhone;

    @Bind(R.id.order_details_date)
    TextView orderDetailsDate;

    @Bind(R.id.order_details_time)
    TextView orderDetailsTime;

    @Bind(R.id.order_details_suiuu_number)
    TextView orderDetailsSuiuuNumber;

    @Bind(R.id.order_details_additional_service_prices)
    TextView orderDetailsAdditionalServicePrices;

    @Bind(R.id.order_details_airport_pick_up_prices)
    TextView orderDetailsAirportPickUpPrices;

    @Bind(R.id.order_details_tickets_prices)
    TextView orderDetailsTicketsPrices;

    @Bind(R.id.order_details_total_prices)
    TextView orderDetailsTotalPrices;

    @Bind(R.id.order_details_back)
    ImageView orderDetailsBack;

    private ImageLoader imageLoader;

    private DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);
        initView();
        ViewAction();
    }

    private void initView() {
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_head_image2)
                .showImageForEmptyUri(R.drawable.default_head_image2)
                .showImageOnFail(R.drawable.default_head_image2).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    private void ViewAction() {

        orderDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}