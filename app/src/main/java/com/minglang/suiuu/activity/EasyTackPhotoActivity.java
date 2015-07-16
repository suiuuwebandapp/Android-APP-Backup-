package com.minglang.suiuu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.OSSService;
import com.alibaba.sdk.android.oss.OSSServiceProvider;
import com.alibaba.sdk.android.oss.callback.SaveCallback;
import com.alibaba.sdk.android.oss.model.OSSException;
import com.alibaba.sdk.android.oss.storage.OSSBucket;
import com.alibaba.sdk.android.oss.storage.OSSFile;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.EasyTackPhotoAdapter;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.chat.activity.BaiduMapActivity;
import com.minglang.suiuu.customview.TextProgressDialog;
import com.minglang.suiuu.entity.LoopArticleData;
import com.minglang.suiuu.entity.LoopBaseData;
import com.minglang.suiuu.utils.AppConstant;
import com.minglang.suiuu.utils.CompressImageUtil;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * 随拍图片选择完成
 * <p/>
 * Created by Administrator on 2015/4/24.
 */
public class EasyTackPhotoActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = EasyTackPhotoActivity.class.getSimpleName();

    private static OSSService ossService = OSSServiceProvider.getService();
    private static OSSBucket bucket = ossService.getOssBucket("suiuu");
    /**
     * 取消按钮
     */
    private TextView tv_cancel;
    private ArrayList<String> picList = new ArrayList<>();
    /**
     * 显示选择的照片
     */
    private ListView lv_picture_description;
    /**
     * 完成按钮
     */
    private TextView tv_top_right;
    /**
     * 保存照片描述的List集合
     */
    private List<String> picDescriptionList;
    /**
     * 标题描写
     */
    private EditText search_question;
    private static final int REQUEST_CODE_MAP = 8;
    /**
     * 主题数据集合
     */
    private List<LoopBaseData> list;
    //判断是主题下拉框还是地区下拉框
    private int themeOrArea = 1;
    private String themeCid;
    private String areaCid;
    /**
     * 选择位置
     */
    private TextView tv_show_your_location;
    private int picSuccessCount = 0;
    protected static final int getData_Success = 0;
    protected static final int getData_FAILURE = 1;
    int status = 0;
    private TextProgressDialog dialog;
    private ImageView iv_top_back;
    private ScrollView sll;
    private String dataNum;
    private LoopArticleData articleDetail;
    private List<String> changePicList;
    private JsonUtils jsonUtil = JsonUtils.getInstance();
    //修改文章进来是否重新选择图片
    private boolean isChangePic = false;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case getData_Success:
                    if (1 == status && picSuccessCount == picList.size()) {
                        dialog.dismissDialog();
                        Toast.makeText(EasyTackPhotoActivity.this, R.string.article_publish_success, Toast.LENGTH_SHORT).show();
                        //TODO 发布完成后在此跳转
                        Intent intent = new Intent(EasyTackPhotoActivity.this, LoopArticleActivity.class);
                        intent.putExtra("articleId", dataNum);
                        intent.putExtra("TAG", TAG);
                        startActivity(intent);
                    } else {
                        dialog.dismissDialog();
                        Toast.makeText(EasyTackPhotoActivity.this, R.string.article_publish_failure, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case getData_FAILURE:
                    dialog.dismissDialog();
                    Toast.makeText(EasyTackPhotoActivity.this, R.string.article_publish_failure, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_tackphoto);

        picList = this.getIntent().getStringArrayListExtra("pictureMessage");
//        articleDetail = (LoopArticleData)getIntent().getSerializableExtra("articleDetail");
        articleDetail = jsonUtil.fromJSON(LoopArticleData.class, getIntent().getStringExtra("articleDetail"));
        initView();


        //判断如果文章详情信息不为空就是修改文章的图片反之为新文章图片
//        iv_cancel.setVisibility(View.GONE);
        tv_cancel.setVisibility(View.VISIBLE);

        ViewAction();
    }

    private void ViewAction() {
        tv_cancel.setOnClickListener(this);
        tv_top_right.setOnClickListener(this);
        iv_top_back.setOnClickListener(this);
        tv_show_your_location.setOnClickListener(this);

    }

    private void initView() {

        LinearLayout rootLayout = (LinearLayout) findViewById(R.id.root);
        if (isKITKAT) {
            if (navigationBarHeight <= 0) {
                rootLayout.setPadding(0, statusBarHeight, 0, 0);
            } else {
                rootLayout.setPadding(0, statusBarHeight, 0, navigationBarHeight);
            }
        }

        picDescriptionList = new ArrayList<>();
        iv_top_back = (ImageView) findViewById(R.id.iv_top_back);
        iv_top_back.setVisibility(View.GONE);
        tv_cancel = (TextView) findViewById(R.id.tv_top_cancel);
        lv_picture_description = (ListView) findViewById(R.id.lv_picture_description);
        tv_top_right = (TextView) findViewById(R.id.tv_top_right);
        search_question = (EditText) findViewById(R.id.search_question);
        tv_show_your_location = (TextView) findViewById(R.id.tv_show_your_location);
        sll = (ScrollView) findViewById(R.id.sll);
        sll.smoothScrollTo(0, 0);

        if (articleDetail != null) {
            changeArticleFullData();
        } else {
            lv_picture_description.setAdapter(new EasyTackPhotoAdapter(this, picList, "0"));
            Utils.setListViewHeightBasedOnChildren(lv_picture_description);
        }
    }

    private void changeArticleFullData() {
        search_question.setText(articleDetail.getaTitle());
        tv_show_your_location.setText(articleDetail.getaAddr());
        changePicList = jsonUtil.fromJSON(new TypeToken<ArrayList<String>>() {
        }.getType(), articleDetail.getaImgList());
        List<String> changeContentList = jsonUtil.fromJSON(new TypeToken<ArrayList<String>>() {
        }.getType(), articleDetail.getaContent());
        lv_picture_description.setAdapter(new EasyTackPhotoAdapter(this, changePicList, changeContentList, "1"));
        Utils.setListViewHeightBasedOnChildren(lv_picture_description);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == 9) {
            if (articleDetail != null) {
                isChangePic = true;
            }
            picList = data.getStringArrayListExtra("pictureMessage");
            lv_picture_description.setAdapter(new EasyTackPhotoAdapter(this, picList, "0"));
            Utils.setListViewHeightBasedOnChildren(lv_picture_description);
        } else if (data != null && requestCode == REQUEST_CODE_MAP) {
            double latitude = data.getDoubleExtra("latitude", 0);
            double longitude = data.getDoubleExtra("longitude", 0);
            String locationAddress = data.getStringExtra("address");

            DeBugLog.i(TAG, "locationAddress:" + locationAddress);
            DeBugLog.i(TAG, "latitude:" + latitude);
            DeBugLog.i(TAG, "longitude:" + longitude);

            if (locationAddress != null && !locationAddress.equals("")) {
                tv_show_your_location.setText(locationAddress);
            } else {
                String st = getResources().getString(R.string.unable_to_get_location);
                Toast.makeText(this, st, Toast.LENGTH_SHORT).show();
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
        }  else if (mId == R.id.tv_show_your_location) {
            startActivityForResult(new Intent(EasyTackPhotoActivity.this, BaiduMapActivity.class), REQUEST_CODE_MAP);
        } else if (mId == R.id.iv_top_back) {
            finish();
        }
    }
    private void loadDate() {
        ListView list = (ListView) findViewById(R.id.lv_picture_description);//获得listview
        for (int i = 0; i < list.getChildCount() - 1; i++) {
            LinearLayout layout = (LinearLayout) list.getChildAt(i);// 获得子item的layout
            EditText et = (EditText) layout.findViewById(R.id.et_item_description);// 从layout中获得控件,根据其id
            // EditText et = (EditText) layout.getChildAt(1)//或者根据Y位置,在这我假设TextView在前，EditText在后
            picDescriptionList.add(et.getText().toString());
        }


        //访问网络相关
        dialog.showDialog();
        String str = SuiuuInfo.ReadVerification(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter("type", 1 + "");
        params.addBodyParameter(HttpServicePath.key, str);
        params.addBodyParameter("title", search_question.getText().toString().trim());
        params.addBodyParameter("addrId", areaCid);
        params.addBodyParameter("circleId", themeCid);
        params.addBodyParameter("content", jsonUtil.toJSON(picDescriptionList));
        params.addBodyParameter("addr", tv_show_your_location.getText().toString().trim());
        List<String> picNameList = new ArrayList<>();
        for (String string : picList) {
            updateDate(string);
            String substring = string.substring(string.lastIndexOf("/"));
            picNameList.add(AppConstant.IMG_FROM_SUIUU + "suiuu_content" + substring);
        }
        params.addBodyParameter("imgList", jsonUtil.toJSON(picNameList));
        params.addBodyParameter("img", picNameList.get(0));
        SuHttpRequest suHttpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.createLoop, new CreateLoopCallBack());
        suHttpRequest.setParams(params);
        suHttpRequest.requestNetworkData();
    }


    private void changeLoadDate() {
        String str = SuiuuInfo.ReadVerification(this);
        RequestParams params = new RequestParams();
        ListView list = (ListView) findViewById(R.id.lv_picture_description);//获得listview
        for (int i = 0; i < list.getChildCount() - 1; i++) {
            LinearLayout layout = (LinearLayout) list.getChildAt(i);// 获得子item的layout
            EditText et = (EditText) layout.findViewById(R.id.et_item_description);// 从layout中获得控件,根据其id
            // EditText et = (EditText) layout.getChildAt(1)//或者根据Y位置,在这我假设TextView在前，EditText在后
            picDescriptionList.add(et.getText().toString());
        }
        if (TextUtils.isEmpty(search_question.getText().toString().trim())) {
            Toast.makeText(this, R.string.title_is_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        dialog.showDialog();
        params.addBodyParameter(HttpServicePath.key, str);
        params.addBodyParameter("articleId", articleDetail.getArticleId());
        params.addBodyParameter("title", search_question.getText().toString().trim());
        params.addBodyParameter("content", jsonUtil.toJSON(picDescriptionList));
        params.addBodyParameter("addr", tv_show_your_location.getText().toString().trim());

        List<String> picNameList = new ArrayList<>();
        if (isChangePic) {
            for (String string : picList) {
                updateDate(string);
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



    private void updateDate(final String path) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String type = path.substring(path.lastIndexOf("/"));
                String name = type.substring(type.lastIndexOf(".") + 1);
                String newPath = null;
                try {
                    newPath = CompressImageUtil.compressImage(path, name, 50);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                OSSFile bigFile = ossService.getOssFile(bucket, "suiuu_content" + type);
                try {
                    bigFile.setUploadFilePath(newPath, name);
                    bigFile.ResumableUploadInBackground(new SaveCallback() {

                        @Override
                        public void onSuccess(String objectKey) {
                            picSuccessCount += 1;
                            if (picSuccessCount == picList.size()) {
                                handler.sendEmptyMessage(getData_Success);
                            }

                        }

                        @Override
                        public void onProgress(String objectKey, int byteCount, int totalSize) {
                            DeBugLog.i(TAG, "[onProgress] - current upload " + objectKey + " bytes: " + byteCount + " in total: " + totalSize);
                        }

                        @Override
                        public void onFailure(String objectKey, OSSException ossException) {
                            handler.sendEmptyMessage(getData_FAILURE);
                            DeBugLog.i(TAG, "[onFailure] - upload " + objectKey + " failed!\n" + ossException.toString());
                            ossException.printStackTrace();
//                    ossException.getException().printStackTrace();
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

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
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            handler.sendEmptyMessage(getData_FAILURE);
            DeBugLog.i(TAG, "请求失败------------------------------------" + s);
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
                } else {
                    handler.sendEmptyMessage(getData_FAILURE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                handler.sendEmptyMessage(getData_FAILURE);
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            handler.sendEmptyMessage(getData_FAILURE);
            DeBugLog.e(TAG, "发布文章失败:" + s);
        }
    }


}
