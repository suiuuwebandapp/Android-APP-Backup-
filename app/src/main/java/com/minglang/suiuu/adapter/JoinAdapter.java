package com.minglang.suiuu.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.PersonalMainPagerActivity;
import com.minglang.suiuu.entity.ConfirmJoinSuiuu.ConfirmJoinSuiuuData.PublisherListEntity;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.HttpServicePath;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.ViewHolder;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * 已参加的数据适配器
 * <p/>
 * Created by Administrator on 2015/6/12.
 */
public class JoinAdapter extends BaseAdapter {

    private static final String TAG = JoinAdapter.class.getSimpleName();

    private static final String USERSIGN = "userSign";

    private Context context;

    private List<PublisherListEntity> list;

    private ProgressDialog progressDialog;

    public JoinAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<PublisherListEntity> list) {
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

        SimpleDraweeView headImageView = holder.getView(R.id.item_my_suiuu_join_head_image);
        TextView nameView = holder.getView(R.id.item_my_suiuu_join_name);
        TextView locationView = holder.getView(R.id.item_my_suiuu_join_location);
        Button removeBtn = holder.getView(R.id.item_my_suiuu_join_remove);

        PublisherListEntity entity = list.get(position);
        String headImagePath = entity.getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            headImageView.setImageURI(Uri.parse(headImagePath));
        }

        String name = entity.getNickname();
        if (!TextUtils.isEmpty(name)) {
            nameView.setText(name);
        } else {
            nameView.setText("");
        }

        String countryName = entity.getCountryName();
        String cityName = entity.getCityName();

        if (!TextUtils.isEmpty(countryName)) {
            if (!TextUtils.isEmpty(cityName)) {
                locationView.setText(countryName + " " + cityName);
            } else {
                locationView.setText(countryName);
            }
        } else {
            locationView.setText("");
        }


        headImageView.setTag(position);
        headImageView.setOnClickListener(new LookSuiuuDetailsClickListener(position));

        removeBtn.setTag(position);
        removeBtn.setOnClickListener(new RemoveClickListener(position));

        return convertView;
    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private class LookSuiuuDetailsClickListener implements View.OnClickListener {

        private int index;

        private LookSuiuuDetailsClickListener(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            String userSign = list.get(index).getUserSign();
            Intent intent = new Intent(context, PersonalMainPagerActivity.class);
            intent.putExtra(USERSIGN, userSign);
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

            String[] keyArray = new String[]{"userSign", HttpServicePath.key, "tripId", "tripPublisherId", "token"};
            String[] valueArray = new String[]{SuiuuInfo.ReadUserSign(context), SuiuuInfo.ReadVerification(context),
                    tripId, tripPublisherId, SuiuuInfo.ReadAppTimeSign(context)};

            String url = OkHttpManager.addUrlAndParams(HttpNewServicePath.removeSuiuuUserPath, keyArray, valueArray);

            try {
                OkHttpManager.onGetAsynRequest(url, new RemoveResultCallback(index));
            } catch (IOException e) {
                e.printStackTrace();
                hideDialog();
                Toast.makeText(context, context.getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
            }
        }

    }

    private class RemoveResultCallback extends OkHttpManager.ResultCallback<String> {

        private int index;

        private RemoveResultCallback(int index) {
            this.index = index;

            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(context.getResources().getString(R.string.load_wait));
            progressDialog.setCanceledOnTouchOutside(false);
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
                        Toast.makeText(context, context.getResources().getString(R.string.NetworkError), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    L.e(TAG, "解析错误:" + e.getMessage());
                    Toast.makeText(context, context.getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onError(Request request, Exception e) {

        }

    }

}
