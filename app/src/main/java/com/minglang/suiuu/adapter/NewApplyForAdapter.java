package com.minglang.suiuu.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
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
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.NewApply;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.SuiuuHttp;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONObject;

import java.util.List;

/**
 * 新申请数据适配器
 * <p/>
 * Created by Administrator on 2015/6/12.
 */
public class NewApplyForAdapter extends BaseAdapter {

    private static final String TAG = NewApplyForAdapter.class.getSimpleName();

    private Context context;

    private List<NewApply.NewApplyData> list;

    private ImageLoader imageLoader;

    private DisplayImageOptions options;

    public NewApplyForAdapter(Context context) {
        this.context = context;

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_head_image)
                .showImageForEmptyUri(R.drawable.default_head_image)
                .showImageOnFail(R.drawable.default_head_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public void setList(List<NewApply.NewApplyData> list) {
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

        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_my_suiuu_new_apply_for, position);
        convertView = holder.getConvertView();
        CircleImageView headImageView = holder.getView(R.id.item_my_suiuu_new_apply_for_head_image);
        TextView userNameView = holder.getView(R.id.item_my_suiuu_new_apply_for_user_name);
        Button ignoreBtn = holder.getView(R.id.item_my_suiuu_new_apply_for_ignore);
        Button agreeBtn = holder.getView(R.id.item_my_suiuu_new_apply_for_agree);

        NewApply.NewApplyData newApplyData = list.get(position);

        String headImagePath = newApplyData.getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            imageLoader.displayImage(headImagePath, headImageView, options);
        }

        String name = newApplyData.getNickname();
        if (!TextUtils.isEmpty(name)) {
            userNameView.setText(name);
        }

        ignoreBtn.setTag(position);
        ignoreBtn.setOnClickListener(new IgnoreClickListener(position));

        agreeBtn.setTag(position);
        agreeBtn.setOnClickListener(new AgreeClickListener(position));

        return convertView;
    }

    private class IgnoreClickListener implements View.OnClickListener {

        private int index;

        private IgnoreClickListener(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            String applyId = list.get(index).getApplyId();

            RequestParams params = new RequestParams();
            params.addBodyParameter("applyId", applyId);
            params.addBodyParameter("userSign", SuiuuInfo.ReadUserSign(context));
            params.addBodyParameter(HttpServicePath.key, SuiuuInfo.ReadVerification(context));

            SuiuuHttp httpRequest = new SuiuuHttp(HttpRequest.HttpMethod.POST,
                    HttpServicePath.ignoreDataPath, new IgnoreRequestCallBack(index));
            httpRequest.setParams(params);
            httpRequest.executive();
        }

    }

    private class AgreeClickListener implements View.OnClickListener {

        private int index;

        private AgreeClickListener(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            String applyId = list.get(index).getApplyId();
            String publisherId = list.get(index).getPublisherId();

            RequestParams params = new RequestParams();
            params.addBodyParameter("applyId", applyId);
            params.addBodyParameter("publisherId", publisherId);
            params.addBodyParameter("userSign", SuiuuInfo.ReadUserSign(context));
            params.addBodyParameter(HttpServicePath.key, SuiuuInfo.ReadVerification(context));

            SuiuuHttp httpRequest = new SuiuuHttp(HttpRequest.HttpMethod.POST,
                    HttpServicePath.agreeDataPath, new AgreeRequestCallBack(index));
            httpRequest.setParams(params);
            httpRequest.executive();
        }

    }

    private ProgressDialog progressDialog;

    private class IgnoreRequestCallBack extends RequestCallBack<String> {

        private int index;

        private IgnoreRequestCallBack(int index) {
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
                DeBugLog.e(TAG, "解析失败:" + e.getMessage());
            }

        }

        @Override
        public void onFailure(HttpException e, String s) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            Toast.makeText(context, context.getResources().getString(R.string.NetworkAnomaly),
                    Toast.LENGTH_SHORT).show();

        }

    }

    private class AgreeRequestCallBack extends RequestCallBack<String> {

        private int index;

        private AgreeRequestCallBack(int index) {
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
                DeBugLog.e(TAG, "解析失败:" + e.getMessage());
            }

        }

        @Override
        public void onFailure(HttpException e, String s) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            Toast.makeText(context, context.getResources().getString(R.string.NetworkAnomaly),
                    Toast.LENGTH_SHORT).show();

        }
    }

}
