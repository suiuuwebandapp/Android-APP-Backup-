package com.minglang.suiuu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.OSSService;
import com.alibaba.sdk.android.oss.OSSServiceProvider;
import com.alibaba.sdk.android.oss.callback.SaveCallback;
import com.alibaba.sdk.android.oss.model.OSSException;
import com.alibaba.sdk.android.oss.storage.OSSBucket;
import com.alibaba.sdk.android.oss.storage.OSSFile;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.EasyTackPhotoAdapter;
import com.minglang.suiuu.chat.activity.BaiduMapActivity;
import com.minglang.suiuu.customview.mProgressDialog;
import com.minglang.suiuu.entity.LoopBase;
import com.minglang.suiuu.entity.LoopBaseData;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtil;
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
public class EasyTackPhotoActivity extends Activity implements View.OnClickListener {

    private static final String TAG = EasyTackPhotoActivity.class.getSimpleName();

    private static OSSService ossService = OSSServiceProvider.getService();
    private static OSSBucket bucket = ossService.getOssBucket("suiuu");
    //    private ImageView iv_cancel;
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
    private PopupWindow popupWindow;
    /**
     * PopupWindow中的ListView
     */
    private ListView listView;
    /**
     * 选择主题
     */
    private TextView tv_theme_choice;
    /**
     * 选择地区
     */
    private TextView tv_area_choice;
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
    private mProgressDialog dialog;
    private ImageView iv_top_back;
    private ScrollView sll;
    private String dataNum;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case getData_Success:
                    if (1 == status && picSuccessCount == picList.size()) {
                        dialog.dismissDialog();
                        Toast.makeText(EasyTackPhotoActivity.this, R.string.article_publish_success, Toast.LENGTH_SHORT).show();
                        //TODO 发布完成后在此跳转
                        Intent intent = new Intent(EasyTackPhotoActivity.this,LoopArticleActivity.class);
                        intent.putExtra("articleId",dataNum);
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

        initView();
        lv_picture_description.setAdapter(new EasyTackPhotoAdapter(this, picList));
        Utils.setListViewHeightBasedOnChildren(lv_picture_description);
//        iv_cancel.setVisibility(View.GONE);
        tv_cancel.setVisibility(View.VISIBLE);

        initPopupWindow();

        ViewAction();
    }

    private void ViewAction() {
        tv_cancel.setOnClickListener(this);
        tv_top_right.setOnClickListener(this);
        iv_top_back.setOnClickListener(this);
        tv_show_your_location.setOnClickListener(this);
        tv_theme_choice.setOnClickListener(this);
        tv_area_choice.setOnClickListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (themeOrArea == 1) {
                    tv_theme_choice.setText(list.get(position).getcName());
                    themeCid = list.get(position).getcId();
                } else {
                    tv_area_choice.setText(list.get(position).getcName());
                    areaCid = list.get(position).getcId();

                }
                popupWindow.dismiss();
            }
        });
    }

    private void initView() {
        iv_top_back = (ImageView) findViewById(R.id.iv_top_back);
        iv_top_back.setVisibility(View.GONE);
        tv_cancel = (TextView) findViewById(R.id.tv_top_cancel);
        lv_picture_description = (ListView) findViewById(R.id.lv_picture_description);
        tv_top_right = (TextView) findViewById(R.id.tv_top_right);
        tv_theme_choice = (TextView) findViewById(R.id.tv_theme_choice);
        tv_area_choice = (TextView) findViewById(R.id.tv_area_choice);
        search_question = (EditText) findViewById(R.id.search_question);
        tv_show_your_location = (TextView) findViewById(R.id.tv_show_your_location);
        sll = (ScrollView) findViewById(R.id.sll);
        sll.smoothScrollTo(0, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == 9) {
            picList = data.getStringArrayListExtra("pictureMessage");
        } else if (data != null && requestCode == REQUEST_CODE_MAP) {
            double latitude = data.getDoubleExtra("latitude", 0);
            double longitude = data.getDoubleExtra("longitude", 0);
            String locationAddress = data.getStringExtra("address");
            Log.i(TAG, locationAddress + "logcation");
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
            loadDate();
        } else if (mId == R.id.tv_theme_choice) {
            loadThemeDate();
        } else if (mId == R.id.tv_area_choice) {
            loadAreaDate();
        } else if (mId == R.id.tv_show_your_location) {
            startActivityForResult(new Intent(EasyTackPhotoActivity.this, BaiduMapActivity.class), REQUEST_CODE_MAP);
        } else if (mId == R.id.iv_top_back) {
            finish();
        }
    }

    //访问主题的数据
    public void loadThemeDate() {
        String str = SuiuuInfo.ReadVerification(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, str);
        params.addBodyParameter("type", "1");
        SuHttpRequest suHttpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.LoopDataPath, new ThemeRequestCallback());
        suHttpRequest.setParams(params);
        suHttpRequest.requestNetworkData();
    }

    /**
     * 主题请求回调接口
     */
    class ThemeRequestCallback extends RequestCallBack<String> {
        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String str = responseInfo.result;
            LoopBase loopBase;
            loopBase = JsonUtil.getInstance().fromJSON(LoopBase.class, str);
            if (loopBase != null) {
                if (Integer.parseInt(loopBase.getStatus()) == 1) {
                    list = loopBase.getData().getData();
                    for (LoopBaseData date : list) {
                        Log.i(TAG, date.toString());
                    }
                    listView.setAdapter(new MyListAdapter(EasyTackPhotoActivity.this, list));
                    themeOrArea = 1;

                    popupWindow.showAsDropDown(tv_theme_choice,10,10);
                } else {
                    Toast.makeText(EasyTackPhotoActivity.this, "数据获取失败，请重试！", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(HttpException error, String msg) {
            Log.i("suiuu",msg);
            Toast.makeText(EasyTackPhotoActivity.this, "数据获取失败，请重试！", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadAreaDate() {
        String str = SuiuuInfo.ReadVerification(this);

        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, str);
        params.addBodyParameter("type", "2");

        SuHttpRequest suHttpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.LoopDataPath, new AreaRequestCallback());
        suHttpRequest.setParams(params);
        suHttpRequest.requestNetworkData();
    }

    /**
     * 地区回调接口
     */
    class AreaRequestCallback extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String str = responseInfo.result;
            LoopBase loopBase = JsonUtil.getInstance().fromJSON(LoopBase.class, str);
            if (loopBase != null) {
                if (Integer.parseInt(loopBase.getStatus()) == 1) {
                    list = loopBase.getData().getData();
                    listView.setAdapter(new MyListAdapter(EasyTackPhotoActivity.this, list));
                    themeOrArea = 2;
                    popupWindow.showAsDropDown(tv_area_choice, 0, 10);
                } else {
                    Toast.makeText(EasyTackPhotoActivity.this, "数据获取失败，请重试！", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(HttpException error, String msg) {
            Toast.makeText(EasyTackPhotoActivity.this, "数据获取失败，请重试！", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadDate() {
        picDescriptionList = new ArrayList<>();
        ListView list = (ListView) findViewById(R.id.lv_picture_description);//获得listview
        for (int i = 0; i < list.getChildCount() - 1; i++) {
            LinearLayout layout = (LinearLayout) list.getChildAt(i);// 获得子item的layout
            EditText et = (EditText) layout.findViewById(R.id.et_item_description);// 从layout中获得控件,根据其id
            // EditText et = (EditText) layout.getChildAt(1)//或者根据Y位置,在这我假设TextView在前，EditText在后
            picDescriptionList.add(et.getText().toString());
            Log.i(TAG, "the text of " + i + "'s EditText：----------->" + et.getText());
        }
        if (TextUtils.isEmpty(search_question.getText().toString().trim())) {
            Toast.makeText(this, R.string.title_is_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(tv_theme_choice.getText().toString().trim())) {
            Toast.makeText(this, R.string.theme_is_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(tv_area_choice.getText().toString().trim())) {
            Toast.makeText(this, R.string.area_is_empty, Toast.LENGTH_SHORT).show();
            return;
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
        params.addBodyParameter("content", JsonUtil.getInstance().toJSON(picDescriptionList));
        params.addBodyParameter("addr", tv_show_your_location.getText().toString().trim());
        List<String> picNameList = new ArrayList<>();
        for (String string : picList) {
            updateDate(string);
            String substring = string.substring(string.lastIndexOf("/"));
            picNameList.add(substring);
        }
        params.addBodyParameter("imgList", JsonUtil.getInstance().toJSON(picNameList));
        params.addBodyParameter("img", picNameList.get(0));
        SuHttpRequest suHttpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.createLoop, new CreateLoopCallBack());
        suHttpRequest.setParams(params);
        suHttpRequest.requestNetworkData();
    }

    //初始化popupWindow
    public void initPopupWindow() {
        dialog = new mProgressDialog(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.pop_select_list, null);
        listView = (ListView) view.findViewById(R.id.pictureSelectList);
//        listView.setBackgroundResource(R.drawable.listview_background);
        //自适配长、框设置
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.listview_background));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        popupWindow.update();
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
    }

    private void updateDate(String path) {
        String type = path.substring(path.lastIndexOf("/"));
        String name = type.substring(type.lastIndexOf(".") + 1);
        OSSFile bigFile = ossService.getOssFile(bucket, "suiuu_content" + type);
        try {
            bigFile.setUploadFilePath(path, name);
            bigFile.ResumableUploadInBackground(new SaveCallback() {

                @Override
                public void onSuccess(String objectKey) {
                    Log.i(TAG, "success upload");
                    picSuccessCount += 1;
                    if (picSuccessCount == picList.size()) {
                        handler.sendEmptyMessage(getData_Success);
                    }

                }

                @Override
                public void onProgress(String objectKey, int byteCount, int totalSize) {
                    Log.i(TAG, "[onProgress] - current upload " + objectKey + " bytes: " + byteCount + " in total: " + totalSize);
                }

                @Override
                public void onFailure(String objectKey, OSSException ossException) {
                    handler.sendEmptyMessage(getData_FAILURE);
                    Log.i(TAG, "[onFailure] - upload " + objectKey + " failed!\n" + ossException.toString());
                    ossException.printStackTrace();
//                    ossException.getException().printStackTrace();
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发布圈子文章回调接口
     */
    class CreateLoopCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            String result = stringResponseInfo.result;
            Log.i("suiuu",result+"--------------");
            try {
                JSONObject json = new JSONObject(result);
                status = (int) json.get("status");
                dataNum = (String)json.get("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            handler.sendEmptyMessage(getData_FAILURE);
            Log.i(TAG, "请求失败------------------------------------" + s);
        }
    }

}
