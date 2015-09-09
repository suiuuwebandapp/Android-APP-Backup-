package com.minglang.suiuu.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpNewServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.OkHttpManager;
import com.minglang.suiuu.utils.ScreenUtils;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.Utils;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 随拍图片选择完成
 * <p/>
 * Created by Administrator on 2015/4/24.
 */
public class EasyTackPhotoActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private static final String TAG = EasyTackPhotoActivity.class.getSimpleName();

    //    protected static final int getData_Success = 0;
    //    protected static final int getData_FAILURE = 1;

    int status = 0;

    //    private static OSSService ossService = OSSServiceProvider.getService();
    //    private static OSSBucket bucket = ossService.getOssBucket("suiuu");
    private String[] suiuuTag = {"家庭", "购物", "自然", "惊险", "浪漫", "博物馆", "猎奇"};

    @BindString(R.string.unable_to_get_location)
    String NoLocation;

    /**
     * 取消按钮
     */
    @Bind(R.id.tv_top_cancel)
    TextView tv_cancel;

    private ArrayList<String> picList = new ArrayList<>();

    /**
     * 显示选择的照片
     */
    @Bind(R.id.lv_picture_description)
    SwipeListView lv_pic_description;

    /**
     * 完成按钮
     */
    @Bind(R.id.tv_top_right)
    TextView tv_top_right;

    /**
     * 保存照片描述的List集合
     */
    private List<String> picDescriptionList = new ArrayList<>();

    /**
     * 标题描写
     */
    @Bind(R.id.search_question)
    EditText search_question;

    private static final int REQUEST_CODE_MAP = 8;

    /**
     * 主题数据集合
     */
    //    private List<LoopBaseData> list;

    /**
     * 选择位置
     */
    @Bind(R.id.tv_show_your_location)
    TextView tv_show_your_location;

    //    private int picSuccessCount = 0;

    private TextProgressDialog dialog;

    private AlertDialog setTagDialog;

    @Bind(R.id.iv_top_back)
    ImageView iv_top_back;


    private JsonUtils jsonUtil = JsonUtils.getInstance();


    private List<TextView> suiuuTagClick = new ArrayList<>();
    private List<TextView> suiuuTagList = new ArrayList<>();
    private String tagText;

    @Bind(R.id.fl_easy_take_photo)
    FlowLayout fl_easy_take_photo;

    @Bind(R.id.tv_top_center)
    TextView tv_top_center;

    @Bind(R.id.tv_show_tag)
    TextView tv_show_tag;

    @Bind(R.id.tv_top_right_more)
    ImageView tv_top_right_more;

    @Bind(R.id.sll)
    ScrollView scrollView;
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

        picList = this.getIntent().getStringArrayListExtra("pictureMessage");
        //articleDetail = (LoopArticleData)getIntent().getSerializableExtra("articleDetail");
        ButterKnife.bind(this);
        initView();
        setSuiuuTag();
        ViewAction();
    }

    private void initView() {
        dialog = new TextProgressDialog(this);
        iv_top_back.setVisibility(View.GONE);
        tv_top_right.setVisibility(View.VISIBLE);
        tv_top_right_more.setVisibility(View.GONE);
        tv_top_center.setText(R.string.trip_gallery_publish);
        //判断如果文章详情信息不为空就是修改文章的图片反之为新文章图片
        //iv_cancel.setVisibility(View.GONE);
        tv_cancel.setVisibility(View.VISIBLE);

        scrollView.smoothScrollTo(0, 0);

        dialog = new TextProgressDialog(this);

        EasyTackPhotoAdapter adapter = new EasyTackPhotoAdapter(this, picList);
        adapter.setSwipeListView(lv_pic_description);


        lv_pic_description.setAdapter(adapter);
        Utils.setListViewHeightBasedOnChildren(lv_pic_description);

        verification = SuiuuInfo.ReadVerification(this);
        int layout100dp = (int) getResources().getDimension(R.dimen.layout_100dp);
        int layout16dp = (int) getResources().getDimension(R.dimen.layout_16dp);
        int itemRemoveBtnWidth = Utils.newInstance().px2dip(layout100dp, this);
        int screenWidth = new ScreenUtils(this).getScreenWidth();
        DeBugLog.i(TAG, "layout100DpToPx=" + layout100dp + ",layout16Dp:" + layout16dp
                + ",itemRemoveBtnWidthPx=" + itemRemoveBtnWidth + ",screenWidth=" + screenWidth);
        lv_pic_description.setOffsetLeft(screenWidth - layout100dp - layout16dp * 2);
    }

    private void ViewAction() {
        tv_cancel.setOnClickListener(this);
        tv_top_right.setOnClickListener(this);
        iv_top_back.setOnClickListener(this);
        tv_show_your_location.setOnClickListener(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == 9) {
            picList = data.getStringArrayListExtra("pictureMessage");
            EasyTackPhotoAdapter adapter = new EasyTackPhotoAdapter(this, picList);
            adapter.setSwipeListView(lv_pic_description);
            lv_pic_description.setAdapter(adapter);
            Utils.setListViewHeightBasedOnChildren(lv_pic_description);

        } else if (data != null && requestCode == REQUEST_CODE_MAP) {
            latitude = data.getDoubleExtra("latitude", 0);
            longitude = data.getDoubleExtra("longitude", 0);
            locationAddress = data.getStringExtra("address");
            locationCountry = data.getStringExtra("country");
            locationCity = data.getStringExtra("city");

            if (locationAddress != null && !locationAddress.equals("")) {
                tv_show_your_location.setText(locationAddress);
            } else {
                Toast.makeText(this, NoLocation, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        int mId = v.getId();
        if (mId == R.id.tv_top_cancel) {
            finish();
        } else if (mId == R.id.tv_top_right) {
                loadDate();
        } else if (mId == R.id.tv_show_your_location) {
            startActivityForResult(new Intent(EasyTackPhotoActivity.this,
                    AMapActivity.class), REQUEST_CODE_MAP);
        } else if (mId == R.id.iv_top_back) {
            finish();
        }
    }

    /**
     * 发布旅图
     */
    private void loadDate() {
        for (int i = 0; i < lv_pic_description.getChildCount(); i++) {
            FrameLayout layout = (FrameLayout) lv_pic_description.getChildAt(i);// 获得子item的layout
            EditText et = (EditText) layout.findViewById(R.id.item_tack_description);// 从layout中获得控件,根据其id
            // EditText et = (EditText) layout.getChildAt(1)//或者根据Y位置,在这我假设TextView在前，EditText在后
            picDescriptionList.add(et.getText().toString());
        }
        if (TextUtils.isEmpty(search_question.getText().toString().trim())) {
            Toast.makeText(this, R.string.title_is_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(locationAddress)) {
            Toast.makeText(this, R.string.choice_location, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(tagText)) {
            Toast.makeText(this, R.string.please_choice_tag, Toast.LENGTH_SHORT).show();
            return;
        }
        //访问网络相关
        dialog.showDialog();
        Map<String, String> map = new HashMap<>();
        map.put("title", search_question.getText().toString().trim());
        map.put("contents", jsonUtil.toJSON(picDescriptionList));
        map.put("country", locationCountry);
        map.put("city", locationCity);
        map.put("lon", String.valueOf(longitude));
        map.put("lat", String.valueOf(latitude));
        map.put("tags", tagText.replace(" ", ","));
        map.put("address", locationAddress);
        List<String> picNameList = new ArrayList<>();
        for (String string : picList) {
            String substring = string.substring(string.lastIndexOf("/"));
            picNameList.add(AppConstant.IMG_FROM_SUIUU + "suiuu_content" + substring);
        }
        map.put("picList", jsonUtil.toJSON(picNameList));
        map.put("titleImg", picNameList.get(0));
        try {
            OkHttpManager.onPostAsynRequest(HttpNewServicePath.createTripGallery + "?token=" + SuiuuInfo.ReadAppTimeSign(EasyTackPhotoActivity.this), new CreateLoopCallBack(), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发布圈子文章回调接口
     */
    class CreateLoopCallBack extends OkHttpManager.ResultCallback<String> {
        @Override
        public void onError(Request request, Exception e) {
            DeBugLog.e(TAG, "HttpException:" + e.getMessage() + ",Error:" + e.toString());
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
    public void setSuiuuTag() {
        fl_easy_take_photo.removeAllViews();
        LayoutInflater mInflater = LayoutInflater.from(this);
        for (int i = 0; i < suiuuTag.length + 1; i++) {
            TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                    fl_easy_take_photo, false);
            if (suiuuTag.length == i) {
                tv.setBackgroundResource(R.drawable.icon_plus);
            } else {
                tv.setBackgroundResource(R.drawable.tag_shape_trip_gallery_publish);
                tv.setText(suiuuTag[i]);
            }
            tv.setTag(i);
            suiuuTagList.add(tv);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tagText = "";
                    int tagNumber = (int) v.getTag();
                    if (suiuuTag.length == tagNumber) {
                        showSetTagDialog();
                    } else {
                        if (suiuuTagClick.contains(suiuuTagList.get(tagNumber))) {
                            suiuuTagList.get(tagNumber).setBackgroundResource(R.drawable.tag_shape_trip_gallery_publish);
                            suiuuTagList.get(tagNumber).setTextColor(getResources().getColor(R.color.gray));
                            suiuuTagClick.remove(suiuuTagList.get(tagNumber));
                        } else {
                            suiuuTagList.get(tagNumber).setBackgroundResource(R.drawable.tag_shape_trip_gallery_publish_press);
                            suiuuTagList.get(tagNumber).setTextColor(getResources().getColor(R.color.white));
                            suiuuTagClick.add(suiuuTagList.get(tagNumber));
                        }
                        showTag();
                    }
                }
            });
            fl_easy_take_photo.addView(tv);
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
        View view = View.inflate(this, R.layout.dialog_set_customer_tag, null);
        final EditText et_input_customer_tag = (EditText) view.findViewById(R.id.et_input_customer_tag);

        Button ok = (Button) view.findViewById(R.id.bt_conffirm_button);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newSetTag = et_input_customer_tag.getText().toString().trim();
                if (TextUtils.isEmpty(newSetTag)) {
                    Toast.makeText(EasyTackPhotoActivity.this, "你的输入不能为空,请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                setCustomerTag += newSetTag + " ";
                //输入的密码相等
                showTag();
                setTagDialog.dismiss();
            }
        });
        Button cancel = (Button) view.findViewById(R.id.bt_cancel_button);
        //点击取消
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTagDialog.dismiss();
            }
        });
        setTagDialog = builder.create();
        setTagDialog.setView(view, 0, 0, 0, 0);
        setTagDialog.show();
    }
}