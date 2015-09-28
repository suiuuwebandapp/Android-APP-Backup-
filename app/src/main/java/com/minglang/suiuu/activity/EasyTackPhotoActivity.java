package com.minglang.suiuu.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.EasyTackPhotoAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.customview.FlowLayout;
import com.minglang.suiuu.customview.TextProgressDialog;
import com.minglang.suiuu.customview.swipelistview.SwipeListView;
import com.minglang.suiuu.service.UpdateImageService;
import com.minglang.suiuu.utils.AppConstant;
import com.minglang.suiuu.utils.AppUtils;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.ScreenUtils;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 随拍图片选择完成
 * <p/>
 * Created by Administrator on 2015/4/24.
 */
public class EasyTackPhotoActivity extends BaseAppCompatActivity {

    private static final String TAG = EasyTackPhotoActivity.class.getSimpleName();

    private static final String PICTURE_MESSAGE = "pictureMessage";

    private static final String TITLE = "title";
    private static final String CONTENTS = "contents";
    private static final String COUNTRY = "country";
    private static final String CITY = "city";
    private static final String LON = "lon";
    private static final String LAT = "lat";
    private static final String TAGS = "tags";
    private static final String ADDRESS = "address";
    private static final String PIC_LIST = "picList";
    private static final String TITLE_IMG = "titleImg";

    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";

    int status = 0;

    private String[] suiuuTag = {"家庭", "购物", "自然", "惊险", "浪漫", "博物馆", "猎奇"};

    @BindColor(R.color.white)
    int titleColor;

    @BindDrawable(R.drawable.shape_trip_image_publish_tag)
    Drawable tripImagePublishTag;

    @BindDrawable(R.drawable.shape_trip_image_publish_press_tag)
    Drawable tripImagePublishPressTag;

    @BindString(R.string.unable_to_get_location)
    String NoLocation;

    @BindString(R.string.DialogTitle)
    String DialogTitle;

    @BindString(R.string.InputTagHint)
    String InputTagHint;

    @Bind(R.id.easy_tack_photo_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.easy_tack_photo_scroll_view)
    ScrollView scrollView;

    /**
     * 显示选择的照片
     */
    @Bind(R.id.lv_picture_description)
    SwipeListView picDescription;

    @Bind(R.id.tv_show_tag)
    TextView tv_show_tag;

    @Bind(R.id.fl_easy_take_photo)
    FlowLayout easyTakePhotoLayout;

    /**
     * 旅途图片地址集合
     */
    private ArrayList<String> picList = new ArrayList<>();

    /**
     * 旅途图片描述List集合
     */
    private List<String> picDescriptionList = new ArrayList<>();

    /**
     * 标题描写
     */
    @Bind(R.id.input_trip_image_title)
    EditText inputTripImageTitle;

    /**
     * 选择位置
     */
    @Bind(R.id.selected_your_location)
    TextView selectedYourLocation;

    private static final int REQUEST_CODE_MAP = 8;

    private TextProgressDialog dialog;

    private JsonUtils jsonUtil = JsonUtils.getInstance();

    private List<TextView> suiuuTagClick = new ArrayList<>();
    private List<TextView> suiuuTagList = new ArrayList<>();

    private String tripImageTitle;

    private String tagText;

    /**
     * 位置相关
     */
    private double latitude;
    private double longitude;

    private String locationAddress;
    private String locationCountry;
    private String locationCity;

    private String setCustomerTag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_tackphoto);

        ButterKnife.bind(this);
        picList = getIntent().getStringArrayListExtra(PICTURE_MESSAGE);

        initView();
        setSuiuuTag();
        viewAction();
    }

    private void initView() {
        toolbar.setTitleTextColor(titleColor);
        setSupportActionBar(toolbar);

        dialog = new TextProgressDialog(this);

        scrollView.smoothScrollTo(0, 0);

        dialog = new TextProgressDialog(this);

        EasyTackPhotoAdapter adapter = new EasyTackPhotoAdapter(this, picList);
        adapter.setSwipeListView(picDescription);

        picDescription.setAdapter(adapter);
        AppUtils.setListViewHeightBasedOnChildren(picDescription);

        verification = SuiuuInfo.ReadVerification(this);
        token = SuiuuInfo.ReadAppTimeSign(this);

        int layout100dp = (int) getResources().getDimension(R.dimen.layout_100dp);
        int layout16dp = (int) getResources().getDimension(R.dimen.layout_16dp);
        int itemRemoveBtnWidth = AppUtils.newInstance().px2dip(layout100dp, this);
        int screenWidth = new ScreenUtils(this).getScreenWidth();
        L.i(TAG, "layout100DpToPx=" + layout100dp
                + ",layout16Dp:" + layout16dp
                + ",itemRemoveBtnWidthPx=" + itemRemoveBtnWidth
                + ",screenWidth=" + screenWidth);
        picDescription.setOffsetLeft(screenWidth - layout100dp - layout16dp * 2);
    }

    private void viewAction() {
        selectedYourLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(EasyTackPhotoActivity.this, AMapActivity.class), REQUEST_CODE_MAP);
            }
        });
    }

    private boolean judgeTripImageInfo() {
        tripImageTitle = inputTripImageTitle.getText().toString().trim();

        if (TextUtils.isEmpty(tripImageTitle)) {
            Toast.makeText(this, R.string.title_is_empty, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(locationAddress)) {
            Toast.makeText(this, R.string.choice_location, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(tagText)) {
            Toast.makeText(this, R.string.please_choice_tag, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * 发布旅图
     */
    private void releaseTripImage() {
        if (judgeTripImageInfo()) {

            for (int i = 0; i < picDescription.getChildCount(); i++) {
                FrameLayout layout = (FrameLayout) picDescription.getChildAt(i);// 获得子item的layout
                EditText et = (EditText) layout.findViewById(R.id.item_tack_description);// 从layout中获得控件,根据其id
                picDescriptionList.add(et.getText().toString());
            }

            dialog.show();

            String strLongitude = "0";
            String strLatitude = "0";
            try {
                L.i(TAG, "longitude:" + longitude);
                L.i(TAG, "latitude:" + latitude);

                strLongitude = String.valueOf(longitude);
                strLatitude = String.valueOf(latitude);
            } catch (Exception e) {
                L.e(TAG, "类型转换异常:" + e.getMessage());
            }

            Map<String, String> map = new HashMap<>();
            map.put(TITLE, tripImageTitle);
            map.put(CONTENTS, jsonUtil.toJSON(picDescriptionList));

            if (!TextUtils.isEmpty(locationCountry)) {
                map.put(COUNTRY, locationCountry);
            }

            if (!TextUtils.isEmpty(locationCity)) {
                map.put(CITY, locationCity);
            }

            map.put(LON, strLongitude);
            map.put(LAT, strLatitude);
            map.put(TAGS, tagText.replace(" ", ","));
            map.put(ADDRESS, locationAddress);

            List<String> picNameList = new ArrayList<>();
            for (String string : picList) {
                String substring = string.substring(string.lastIndexOf("/"));
                picNameList.add(AppConstant.OSS_ROOT_PATH + "suiuu_content" + substring);
            }

            map.put(PIC_LIST, jsonUtil.toJSON(picNameList));
            map.put(TITLE_IMG, picNameList.get(0));

            L.e(TAG, "参数集合:" + map.toString());

            String url = HttpNewServicePath.createTripImagePath + "?" + TOKEN + "=" + token;

            try {
                OkHttpManager.onPostAsynRequest(url, new CreateLoopCallBack(), map);
            } catch (Exception e) {
                L.e(TAG, "网络请求异常:" + e.getMessage());
            }
        }
    }

    public void setSuiuuTag() {
        easyTakePhotoLayout.removeAllViews();
        LayoutInflater mInflater = LayoutInflater.from(this);

        for (int i = 0; i < suiuuTag.length + 1; i++) {
            TextView tv = (TextView) mInflater.inflate(R.layout.tv, easyTakePhotoLayout, false);
            if (suiuuTag.length == i) {
                tv.setBackgroundResource(R.drawable.icon_plus);
            } else {
                tv.setBackground(tripImagePublishTag);
                tv.setText(suiuuTag[i]);
            }

            tv.setTag(i);
            suiuuTagList.add(tv);

            tv.setOnClickListener(new View.OnClickListener() {
                @SuppressWarnings("deprecation")
                @Override
                public void onClick(View v) {
                    tagText = "";
                    int tagNumber = (int) v.getTag();
                    if (suiuuTag.length == tagNumber) {
                        showSetTagDialog();
                    } else {
                        if (suiuuTagClick.contains(suiuuTagList.get(tagNumber))) {
                            suiuuTagList.get(tagNumber).setBackground(tripImagePublishTag);
                            suiuuTagList.get(tagNumber).setTextColor(getResources().getColor(R.color.gray));
                            suiuuTagClick.remove(suiuuTagList.get(tagNumber));
                        } else {
                            suiuuTagList.get(tagNumber).setBackground(tripImagePublishPressTag);
                            suiuuTagList.get(tagNumber).setTextColor(getResources().getColor(R.color.white));
                            suiuuTagClick.add(suiuuTagList.get(tagNumber));
                        }
                        showTag();
                    }
                }
            });
            easyTakePhotoLayout.addView(tv);
        }
    }

    public void showTag() {
        for (TextView tv : suiuuTagClick) {
            tagText += tv.getText() + " ";
        }

        tagText += setCustomerTag;
        if ("".equals(tagText)) {
            if ("".equals(setCustomerTag)) {
                tv_show_tag.setText(R.string.please_choice_tag);
            } else {
                tv_show_tag.setText(setCustomerTag);
            }
        } else {
            tv_show_tag.setText(tagText);
        }
    }

    /**
     * 设置自定义标签弹出框
     */
    protected void showSetTagDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText inputTagEdit = new EditText(this);
        inputTagEdit.setHint(InputTagHint);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String inputTag = inputTagEdit.getText().toString().trim();
                if (TextUtils.isEmpty(inputTag)) {
                    Toast.makeText(EasyTackPhotoActivity.this, "你的输入不能为空,请重新输入", Toast.LENGTH_SHORT).show();
                } else {
                    setCustomerTag += inputTag + " ";
                    showTag();
                }
            }
        });

        builder.setTitle(DialogTitle);
        builder.setNegativeButton(android.R.string.cancel, null);

        AlertDialog alertDialog = builder.create();
        alertDialog.setView(inputTagEdit);
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        L.i(TAG, "requestCode:" + requestCode + ",resultCode:" + resultCode);

        if (data != null && resultCode == 9) {
            picList = data.getStringArrayListExtra(PICTURE_MESSAGE);
            EasyTackPhotoAdapter adapter = new EasyTackPhotoAdapter(this, picList);
            adapter.setSwipeListView(picDescription);
            picDescription.setAdapter(adapter);

            AppUtils.setListViewHeightBasedOnChildren(picDescription);

        } else if (data != null && requestCode == REQUEST_CODE_MAP) {
            latitude = data.getDoubleExtra(LATITUDE, 0);
            longitude = data.getDoubleExtra(LONGITUDE, 0);
            locationAddress = data.getStringExtra(ADDRESS);
            locationCountry = data.getStringExtra(COUNTRY);
            locationCity = data.getStringExtra(CITY);

            if (locationAddress != null && !locationAddress.equals("")) {
                selectedYourLocation.setText(locationAddress);
            } else {
                Toast.makeText(this, NoLocation, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_easy_task_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;

            case R.id.easy_tack_photo_accomplish:
                releaseTripImage();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 发布圈子文章回调接口
     */
    private class CreateLoopCallBack extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "HttpException:" + e.getMessage() + ",Error:" + e.toString());
        }

        @Override
        public void onResponse(String result) {
            try {
                JSONObject json = new JSONObject(result);
                status = (int) json.get("status");
                if (status == 1) {
                    Intent intent = new Intent(EasyTackPhotoActivity.this, UpdateImageService.class);
                    intent.putExtra("imageList", jsonUtil.toJSON(picList));
                    startService(intent);
                    Toast.makeText(EasyTackPhotoActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EasyTackPhotoActivity.this, "发布没有成功,请重试", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}