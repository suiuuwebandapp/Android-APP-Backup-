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

import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.NewApply;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpNewServicePath;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.OkHttpManager;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.io.IOException;
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

    private ProgressDialog progressDialog;

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

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private class IgnoreClickListener implements View.OnClickListener {

        private int index;

        private IgnoreClickListener(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            String applyId = list.get(index).getApplyId();

            String[] keyArray = new String[]{"applyId", "userSign", HttpServicePath.key, "token"};
            String[] valueArray = new String[]{applyId, SuiuuInfo.ReadUserSign(context),
                    SuiuuInfo.ReadVerification(context), SuiuuInfo.ReadAppTimeSign(context)};

            String url = OkHttpManager.addUrlAndParams(HttpNewServicePath.ignoreDataPath, keyArray, valueArray);

            try {
                OkHttpManager.onGetAsynRequest(url, new IgnoreResultCallback(index));
            } catch (IOException e) {
                e.printStackTrace();
                hideDialog();
                Toast.makeText(context, context.getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
            }
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

            String[] keyArray = new String[]{"applyId", "publisherId", "userSign", HttpServicePath.key, "token"};
            String[] valueArray = new String[]{applyId, publisherId, SuiuuInfo.ReadUserSign(context),
                    SuiuuInfo.ReadVerification(context), SuiuuInfo.ReadAppTimeSign(context)};

            String url = OkHttpManager.addUrlAndParams(HttpNewServicePath.agreeDataPath, keyArray, valueArray);

            try {
                OkHttpManager.onGetAsynRequest(url, new AgreeResultCallback(index));
            } catch (IOException e) {
                e.printStackTrace();
                hideDialog();
                Toast.makeText(context, context.getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
            }
        }

    }

    private class IgnoreResultCallback extends OkHttpManager.ResultCallback<String> {

        private int index;

        private IgnoreResultCallback(int index) {
            this.index = index;
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage(context.getResources().getString(R.string.load_wait));
                progressDialog.setCanceledOnTouchOutside(false);
            }
        }

        @Override
        public void onResponse(String response) {
            if (TextUtils.isEmpty(response)) {
                hideDialog();
                Toast.makeText(context, context.getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
            } else {
                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    if (status.equals("1")) {
                        list.remove(index);
                        setList(list);
                    } else {
                        Toast.makeText(context, context.getResources().getString(R.string.NetworkError), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    DeBugLog.e(TAG, "解析失败:" + e.getMessage());
                    hideDialog();
                    Toast.makeText(context, context.getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            DeBugLog.e(TAG, "Exception:" + e.getMessage());
            hideDialog();
            Toast.makeText(context, context.getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
        }

    }

    private class AgreeResultCallback extends OkHttpManager.ResultCallback<String> {

        private int index;

        private AgreeResultCallback(int index) {
            this.index = index;
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage(context.getResources().getString(R.string.load_wait));
                progressDialog.setCanceledOnTouchOutside(false);
            }
        }

        @Override
        public void onResponse(String response) {
            hideDialog();
            if (TextUtils.isEmpty(response)) {
                Toast.makeText(context, context.getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
            } else {
                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    if (status.equals("1")) {
                        list.remove(index);
                        setList(list);
                    } else {
                        Toast.makeText(context, context.getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    DeBugLog.e(TAG, "解析失败:" + e.getMessage());
                    Toast.makeText(context, context.getResources().getString(R.string.DataException), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            DeBugLog.e(TAG, "Exception:" + e.getMessage());
            hideDialog();
            Toast.makeText(context, context.getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
        }

    }

}