package com.minglang.suiuu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.EasyTackPhotoAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.chat.activity.BaiduMapActivity;
import com.minglang.suiuu.customview.FlowLayout;
import com.minglang.suiuu.customview.TextProgressDialog;
import com.minglang.suiuu.customview.swipelistview.SwipeListView;
import com.minglang.suiuu.entity.LoopArticleData;
import com.minglang.suiuu.service.UpdateImageService;
import com.minglang.suiuu.utils.AppConstant;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.ScreenUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    @Bind(R.id.iv_top_back)
    ImageView iv_top_back;

    private String dataNum;

    private LoopArticleData articleDetail;

    private JsonUtils jsonUtil = JsonUtils.getInstance();

    //修改文章进来是否重新选择图片
    private boolean isChangePic = false;

    private List<TextView> suiuuTagClick = new ArrayList<>();
    private List<TextView> suiuuTagList = new ArrayList<>();
    private String tagText;

    @Bind(R.id.fl_easy_take_photo)
    FlowLayout fl_easy_take_photo;

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

    //    private Handler handler = new Handler(new Handler.Callback() {
    //        @Override
    //        public boolean handleMessage(Message msg) {
    //            switch (msg.what) {
    //                case getData_Success:
    //                    if (1 == status && picSuccessCount == picList.size()) {
    //
    //                        dialog.dismissDialog();
    //                        Toast.makeText(EasyTackPhotoActivity.this, R.string.article_publish_success, Toast.LENGTH_SHORT).show();
    //                          Intent intent = new Intent(EasyTackPhotoActivity.this, LoopArticleActivity.class);
    //                          intent.putExtra("articleId", dataNum);
    //                          intent.putExtra("TAG", TAG);
    //                          startActivity(intent);
    //                        finish();
    //                    } else {
    //                        dialog.dismissDialog();
    //                        Toast.makeText(EasyTackPhotoActivity.this, R.string.article_publish_failure, Toast.LENGTH_SHORT).show();
    //                    }
    //                    break;
    //
    //                case getData_FAILURE:
    //                    dialog.dismissDialog();
    //                    Toast.makeText(EasyTackPhotoActivity.this, R.string.article_publish_failure, Toast.LENGTH_SHORT).show();
    //                    break;
    //
    //                default:
    //                    break;
    //            }
    //            return false;
    //        }
    //    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_tackphoto);

        picList = this.getIntent().getStringArrayListExtra("pictureMessage");
        //articleDetail = (LoopArticleData)getIntent().getSerializableExtra("articleDetail");
        articleDetail = jsonUtil.fromJSON(LoopArticleData.class, getIntent().getStringExtra("articleDetail"));

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

        //判断如果文章详情信息不为空就是修改文章的图片反之为新文章图片
        //iv_cancel.setVisibility(View.GONE);
        tv_cancel.setVisibility(View.VISIBLE);

        scrollView.smoothScrollTo(0, 0);

        dialog = new TextProgressDialog(this);

        EasyTackPhotoAdapter adapter = new EasyTackPhotoAdapter(this, picList, "0");
        adapter.setSwipeListView(lv_pic_description);

        if (articleDetail != null) {
            changeArticleFullData();
        } else {
            lv_pic_description.setAdapter(adapter);
            Utils.setListViewHeightBasedOnChildren(lv_pic_description);
        }

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

    private void changeArticleFullData() {
        search_question.setText(articleDetail.getaTitle());
        tv_show_your_location.setText(articleDetail.getaAddr());

        List<String> changePicList
                = jsonUtil.fromJSON(new TypeToken<ArrayList<String>>() {
        }.getType(), articleDetail.getaImgList());

        List<String> changeContentList
                = jsonUtil.fromJSON(new TypeToken<ArrayList<String>>() {
        }.getType(), articleDetail.getaContent());

        EasyTackPhotoAdapter adapter = new EasyTackPhotoAdapter(this, changePicList, changeContentList, "1");
        adapter.setSwipeListView(lv_pic_description);
        lv_pic_description.setAdapter(adapter);

        Utils.setListViewHeightBasedOnChildren(lv_pic_description);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == 9) {
            if (articleDetail != null) {
                isChangePic = true;
            }
            picList = data.getStringArrayListExtra("pictureMessage");

            EasyTackPhotoAdapter adapter = new EasyTackPhotoAdapter(this, picList, "0");
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
            if (articleDetail != null) {
                changeLoadDate();
            } else {
                loadDate();
            }
        } else if (mId == R.id.tv_show_your_location) {
            startActivityForResult(new Intent(EasyTackPhotoActivity.this,
                    BaiduMapActivity.class), REQUEST_CODE_MAP);
        } else if (mId == R.id.iv_top_back) {
            finish();
        }
    }

    private void loadDate() {
        for (int i = 0; i < lv_pic_description.getChildCount() - 1; i++) {
            FrameLayout layout = (FrameLayout) lv_pic_description.getChildAt(i);// 获得子item的layout
            EditText et = (EditText) layout.findViewById(R.id.item_tack_description);// 从layout中获得控件,根据其id
            // EditText et = (EditText) layout.getChildAt(1)//或者根据Y位置,在这我假设TextView在前，EditText在后
            picDescriptionList.add(et.getText().toString());
        }
        if (TextUtils.isEmpty(locationAddress)) {
            Toast.makeText(this, "请选择位置", Toast.LENGTH_SHORT).show();
            return;
        }

        //访问网络相关
        dialog.showDialog();

        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, verification);
        params.addBodyParameter("title", search_question.getText().toString().trim());
        params.addBodyParameter("contents", jsonUtil.toJSON(picDescriptionList));
        params.addBodyParameter("country", locationCountry);
        params.addBodyParameter("city", locationCity);
        params.addBodyParameter("lon", String.valueOf(longitude));
        params.addBodyParameter("lat", String.valueOf(latitude));
        params.addBodyParameter("tags", tagText.replace(" ", ","));
        params.addBodyParameter("address", locationAddress);
        List<String> picNameList = new ArrayList<>();
        for (String string : picList) {
//            updateDate(string);
            String substring = string.substring(string.lastIndexOf("/"));
            picNameList.add(AppConstant.IMG_FROM_SUIUU + "suiuu_content" + substring);
        }
        params.addBodyParameter("picList", jsonUtil.toJSON(picNameList));
        params.addBodyParameter("titleImg", picNameList.get(0));
        SuHttpRequest suHttpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.createTripGallery, new CreateLoopCallBack());
        suHttpRequest.setParams(params);
        suHttpRequest.requestNetworkData();
    }

    private void changeLoadDate() {
        RequestParams params = new RequestParams();
        for (int i = 0; i < lv_pic_description.getChildCount() - 1; i++) {
            LinearLayout layout = (LinearLayout) lv_pic_description.getChildAt(i);// 获得子item的layout
            EditText et = (EditText) layout.findViewById(R.id.item_tack_description);// 从layout中获得控件,根据其id
            // EditText et = (EditText) layout.getChildAt(1)//或者根据Y位置,在这我假设TextView在前，EditText在后
            picDescriptionList.add(et.getText().toString());
        }

        if (TextUtils.isEmpty(search_question.getText().toString().trim())) {
            Toast.makeText(this, R.string.title_is_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        dialog.showDialog();

        params.addBodyParameter(HttpServicePath.key, verification);
        params.addBodyParameter("articleId", articleDetail.getArticleId());
        params.addBodyParameter("title", search_question.getText().toString().trim());
        params.addBodyParameter("content", jsonUtil.toJSON(picDescriptionList));
        params.addBodyParameter("addr", tv_show_your_location.getText().toString().trim());

        List<String> picNameList = new ArrayList<>();
        if (isChangePic) {
            for (String string : picList) {
                //updateDate(string);
                String substring = string.substring(string.lastIndexOf("/"));
                picNameList.add(AppConstant.IMG_FROM_SUIUU + "suiuu_content" + substring);
            }
            params.addBodyParameter("imgList", JsonUtils.getInstance().toJSON(picNameList));
            if (picNameList.size() >= 1) {
                params.addBodyParameter("img", picNameList.get(0));
            }
        } else {
            params.addBodyParameter("imgList", articleDetail.getaImgList());
            params.addBodyParameter("img", articleDetail.getaImg());
        }

        SuHttpRequest suHttpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.updateLoop, new UpdateLoopCallBack());
        suHttpRequest.setParams(params);
        suHttpRequest.requestNetworkData();
    }

    //    private void updateDate(final String path) {
    //        new Thread(new Runnable() {
    //            @Override
    //            public void run() {
    //                String type = path.substring(path.lastIndexOf("/"));
    //                String name = type.substring(type.lastIndexOf(".") + 1);
    //                String newPath = null;
    //                  try {
    //                      newPath = CompressImageUtil.compressImage(path, name, 50);
    //                  } catch (FileNotFoundException e) {
    //                      e.printStackTrace();
    //                  }
    //                OSSFile bigFile = ossService.getOssFile(bucket, "suiuu_content" + type);
    //                try {
    //                    bigFile.setUploadFilePath(path,name);
    //                    bigFile.ResumableUploadInBackground(new SaveCallback() {
    //                        @Override
    //                        public void onSuccess(String objectKey) {
    //                            picSuccessCount += 1;
    //                            if (picSuccessCount == picList.size()) {
    //                            }
    //                        }
    //                        @Override
    //                        public void onProgress(String objectKey, int byteCount, int totalSize) {
    //                            Log.i("suiuu", "[onProgress] - current upload " + objectKey + " bytes: " + byteCount + " in total: " + totalSize);
    //                        }
    //                        @Override
    //                        public void onFailure(String objectKey, OSSException ossException) {
    //                            Log.i("suiuu", "[onFailure] - upload " + objectKey + " failed!\n" + ossException.toString());
    //                            ossException.printStackTrace();
    //                            ossException.getException().printStackTrace();
    //                        }
    //                    });
    //                } catch (FileNotFoundException e) {
    //                    e.printStackTrace();
    //                }
    //            }
    //        }).start();
    //    }

    /**
     * 发布圈子文章回调接口
     */
    class CreateLoopCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            String result = stringResponseInfo.result;
            try {
                JSONObject json = new JSONObject(result);
                status = (int) json.get("status");
                dataNum = (String) json.get("data");
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

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "HttpException:" + e.getMessage() + ",Error:" + s);
        }

    }

    /**
     * 修改圈子文章回调接口
     */
    class UpdateLoopCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            String result = stringResponseInfo.result;
            try {
                JSONObject json = new JSONObject(result);
                status = (int) json.get("status");
                dataNum = (String) json.get("data");
                if ("success".equals(dataNum)) {
                    Toast.makeText(EasyTackPhotoActivity.this, R.string.article_update_success, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EasyTackPhotoActivity.this, LoopArticleActivity.class);
                    intent.putExtra("articleId", articleDetail.getArticleId());
                    intent.putExtra("TAG", TAG);
                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "发布文章失败:" + s);
        }
    }

    public void setSuiuuTag() {
        fl_easy_take_photo.removeAllViews();

        LayoutInflater mInflater = LayoutInflater.from(this);

        for (int i = 0; i < suiuuTag.length; i++) {
            TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                    fl_easy_take_photo, false);

            tv.setBackgroundResource(R.drawable.tag_shape_trip_gallery_publish);
            tv.setText(suiuuTag[i]);
            tv.setTag(i);

            suiuuTagList.add(tv);

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tagText = "";
                    int tagNumber = (int) v.getTag();
                    if (suiuuTagClick.contains(suiuuTagList.get(tagNumber))) {
                        suiuuTagList.get(tagNumber).setBackgroundResource(R.drawable.tag_shape_trip_gallery_publish);
                        suiuuTagList.get(tagNumber).setTextColor(getResources().getColor(R.color.gray));
                        suiuuTagClick.remove(suiuuTagList.get(tagNumber));
                    } else {
                        suiuuTagList.get(tagNumber).setBackgroundResource(R.drawable.tag_shape_trip_gallery_publish_press);
                        suiuuTagList.get(tagNumber).setTextColor(getResources().getColor(R.color.white));
                        suiuuTagClick.add(suiuuTagList.get(tagNumber));
                    }
                    for (TextView tv : suiuuTagClick) {
                        tagText += tv.getText() + " ";
                    }
                    if ("".equals(tagText)) {
                        tv_show_tag.setText("选择标签");
                    } else {
                        tv_show_tag.setText(tagText);

                    }
                }
            });

            fl_easy_take_photo.addView(tv);
        }

    }

}