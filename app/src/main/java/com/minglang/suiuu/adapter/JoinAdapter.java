package com.minglang.suiuu.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.SuiuuUserInfoActivity;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.ConfirmJoinSuiuu;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONObject;

import java.util.List;

/**
 * 已参加的数据适配器
 * <p/>
 * Created by Administrator on 2015/6/12.
 */
public class JoinAdapter extends BaseAdapter {

    private static final String TAG = JoinAdapter.class.getSimpleName();

    private Context context;

    private List<ConfirmJoinSuiuu.ConfirmJoinSuiuuData.PublisherListEntity> list;

    private ImageLoader imageLoader;

    private DisplayImageOptions options;

    public JoinAdapter(Context context) {
        this.context = context;

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_head_image)
                .showImageForEmptyUri(R.drawable.default_head_image)
                .showImageOnFail(R.drawable.default_head_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public void setList(List<ConfirmJoinSuiuu.ConfirmJoinSuiuuData.PublisherListEntity> list) {
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
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_my_suiuu_join, position);
        convertView = holder.getConvertView();

        CircleImageView headImageView = holder.getView(R.id.item_my_suiuu_join_head_image);
        TextView nameView = holder.getView(R.id.item_my_suiuu_join_name);
        TextView locationView = holder.getView(R.id.item_my_suiuu_join_location);
        Button removeBtn = holder.getView(R.id.item_my_suiuu_join_remove);

        ConfirmJoinSuiuu.ConfirmJoinSuiuuData.PublisherListEntity entity = list.get(position);

        imageLoader.displayImage(entity.getHeadImg(), headImageView, options);
        nameView.setText(entity.getNickname());
        locationView.setText(entity.getCountryName() + " " + entity.getCityName());

        headImageView.setTag(position);
        headImageView.setOnClickListener(new LookSuiuuDetailsClickListener(position));

        removeBtn.setTag(position);
        removeBtn.setOnClickListener(new RemoveClickListener(position));

        return convertView;
    }

    private class LookSuiuuDetailsClickListener implements View.OnClickListener {

        private int index;

        private LookSuiuuDetailsClickListener(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            String userSign = list.get(index).getUserSign();
            Intent intent = new Intent(context, SuiuuUserInfoActivity.class);
            intent.putExtra("userSign", userSign);
            context.startActivity(intent);
        }
    }

    private class RemoveClickListener implements View.OnClickListener {

        private int index;

        private RemoveClickListener(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            String tripId = list.get(index).getTripId();
            String tripPublisherId = list.get(index).getTripPublisherId();

            RequestParams params = new RequestParams();
            params.addBodyParameter("userSign", SuiuuInfo.ReadUserSign(context));
            params.addBodyParameter(HttpServicePath.key, SuiuuInfo.ReadVerification(context));
            params.addBodyParameter("tripId", tripId);
            params.addBodyParameter("tripPublisherId", tripPublisherId);

            SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                    HttpServicePath.removeSuiuuUserPath, new RemoveRequestCallBack(index));
            httpRequest.setParams(params);
            httpRequest.executive();
        }

    }

    private ProgressDialog progressDialog;

    private class RemoveRequestCallBack extends RequestCallBack<String> {

        private int index;

        private RemoveRequestCallBack(int index) {
            this.index = index;
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("请稍候...");
            progressDialog.setCanceledOnTouchOutside(false);
        }

        @Override
        public void onStart() {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            String str = responseInfo.result;
            DeBugLog.i(TAG, "移除返回数据:" + str);
            try {
                JSONObject object = new JSONObject(str);
                String status = object.getString("status");
                if (status.equals("1")) {
                    list.remove(index);
                    setList(list);
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.NetworkError),
                            Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "解析错误:" + e.getMessage());
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "HttpException:" + e.getMessage() + ",error:" + s);

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            Toast.makeText(context, context.getResources().getString(R.string.NetworkAnomaly),
                    Toast.LENGTH_SHORT).show();
        }
    }

}
