package com.minglang.suiuu.activity;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.SuiuuDataDetail;
import com.minglang.suiuu.entity.SuiuuDetailForInfo;
import com.minglang.suiuu.entity.SuiuuDetailForPublisherList;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtil;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 项目名称：Suiuu
 * 类描述：suiuu的详细列表
 * 创建人：Administrator
 * 创建时间：2015/5/4 19:13
 * 修改人：Administrator
 * 修改时间：2015/5/4 19:13
 * 修改备注：
 */
public class SuiuuDetailActivity extends Activity {
    private TextView tv_nikename;
    private TextView tv_selfsign;
    private TextView tv_title;
    private TextView tv_content;
    private JsonUtil jsonUtil = JsonUtil.getInstance();
    private SuiuuDetailForInfo detailInfo;
    private List<SuiuuDetailForPublisherList> publisherList;
    private CircleImageView suiuu_details_user_head_image;
    private DisplayImageOptions options;
    private TextView tv_service_time;
    private TextView tv_suiuu_travel_time;
    private TextView tv_atmost_people;
    /**
     * 验证信息
     */
    private String Verification;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suiuu_details_activity);
        String tripId = this.getIntent().getStringExtra("tripId");
        Log.i("suiuu","tripId="+tripId);
        Verification = SuiuuInfo.ReadVerification(this);
        initView();
        loadDate(tripId);
    }

    private void initView() {
        tv_nikename = (TextView) findViewById(R.id.tv_nickname);
        tv_selfsign = (TextView) findViewById(R.id.tv_self_sign);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_content = (TextView) findViewById(R.id.tv_content);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.progress_bar);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        suiuu_details_user_head_image = (CircleImageView) findViewById(R.id.suiuu_details_user_head_image);

        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_suiuu_image)
                .showImageForEmptyUri(R.drawable.default_suiuu_image).showImageOnFail(R.drawable.default_suiuu_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.NONE_SAFE).bitmapConfig(Bitmap.Config.RGB_565).build();
        tv_service_time = (TextView) findViewById(R.id.tv_service_time);
        tv_suiuu_travel_time = (TextView) findViewById(R.id.tv_suiuu_travel_time);
        tv_atmost_people = (TextView) findViewById(R.id.tv_atmost_people);

    }
    public void fullOfData() {
        tv_title.setText(detailInfo.getTitle());
        tv_content.setText(detailInfo.getInfo());
        tv_nikename.setText(publisherList.get(0).getNickname());
        tv_selfsign.setText(publisherList.get(0).getIntro());
        ImageLoader.getInstance().displayImage(publisherList.get(0).getHeadImg(), suiuu_details_user_head_image, options);
        Log.i("suiuu", "startTime=" + detailInfo.getStartTime() + ",endTime=" + detailInfo.getEndTime());
        tv_service_time.setText("服务时间:     " + detailInfo.getStartTime() + "-" + detailInfo.getEndTime());
        tv_suiuu_travel_time.setText("随游时长:     "+detailInfo.getTravelTime()+"个小时");
        tv_atmost_people.setText("最多人数:     "+detailInfo.getMakeUserCount()+"人");
        dialog.dismiss();
    }
    //访问网络
    private void loadDate(String tripId) {
        dialog.show();
        RequestParams params = new RequestParams();
        params.addBodyParameter("trId", tripId);
        params.addBodyParameter(HttpServicePath.key, Verification);

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getSuiuuItemInfo, new SuiuuItemInfoCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }
    /**
     * 请求数据网络接口回调
     */
    class SuiuuItemInfoCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            try {
                SuiuuDataDetail detail = jsonUtil.fromJSON(SuiuuDataDetail.class,responseInfo.result);
                if("1".equals(detail.getStatus())) {
                    detailInfo = detail.getData().getInfo();
                    publisherList = detail.getData().getPublisherList();
                    fullOfData();
                }else {
                    dialog.dismiss();
                    Toast.makeText(SuiuuDetailActivity.this, "数据请求失败，请稍候再试！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                dialog.dismiss();
                Toast.makeText(SuiuuDetailActivity.this, "数据请求失败，请稍候再试！", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
            }

        }

        @Override
        public void onFailure(HttpException error, String msg) {
            dialog.dismiss();
            Toast.makeText(SuiuuDetailActivity.this, "数据请求失败，请稍候再试！", Toast.LENGTH_SHORT).show();
        }
    }
}
