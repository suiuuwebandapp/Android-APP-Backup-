package com.minglang.suiuu.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
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
import com.minglang.suiuu.adapter.ShowGVPictureAdapter;
import com.minglang.suiuu.chat.activity.BaiduMapActivity;
import com.minglang.suiuu.chat.activity.ShowBigImage;
import com.minglang.suiuu.entity.LoopBase;
import com.minglang.suiuu.entity.LoopBaseData;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtil;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2015/4/23.
 * 随问和随记的页面
 */

public class AskQuestionActivity extends Activity implements View.OnClickListener {

    private static final String TAG = AskQuestionActivity.class.getSimpleName();

    private GridView gv_show_picture;
    private ArrayList<String> listPicture;
    //返回按钮
    private ImageView iv_top_back;
    private EditText et_search_question;
    private EditText et_question_description;
    private TextView tv_show_your_location;
    private TextView tv_theme_choice;
    private TextView tv_area_choice;
    private static final int REQUEST_CODE_MAP = 8;
    private TextView tv_top_right;
    private static OSSService ossService = OSSServiceProvider.getService();
    private static OSSBucket bucket = ossService.getOssBucket("suiuu");
    private Dialog dialog;
    private PopupWindow popupWindow;
    private ListView listView;
    private int record = 0;
    private int picSuccessCount = 0;
    protected static final int getData_Success = 0;
    protected static final int getData_FAILURE = 1;
    /**
     * 主题数据集合
     */
    private List<LoopBaseData> list;
    //判断是主题下拉框还是地区下拉框
    private int themeOrArea = 1;
    private String themeCid;
    private String areaCid;
    private int status = 0;
    private String dataNum;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case getData_Success:
                    if (1 == status && picSuccessCount == listPicture.size()) {
                        dialog.dismiss();
                        Toast.makeText(AskQuestionActivity.this, R.string.article_publish_success, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AskQuestionActivity.this,LoopArticleActivity.class);
                        intent.putExtra("articleId",dataNum);
                        intent.putExtra("TAG",TAG);
                        startActivity(intent);
                    } else {
                        dialog.dismiss();
                        Toast.makeText(AskQuestionActivity.this, R.string.article_publish_failure, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case getData_FAILURE:
                    dialog.dismiss();
                    Toast.makeText(AskQuestionActivity.this, R.string.article_publish_failure, Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_ask_question);
        record = getIntent().getIntExtra("record", 0);
        initView();

        ViewAction();
    }

    private void ViewAction() {
        gv_show_picture.setAdapter(new ShowGVPictureAdapter(this, listPicture));
        tv_top_right.setOnClickListener(this);
        iv_top_back.setOnClickListener(this);
        tv_show_your_location.setOnClickListener(this);
        tv_theme_choice.setOnClickListener(this);
        tv_area_choice.setOnClickListener(this);
        gv_show_picture.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == listPicture.size()) {
                    Intent intent = new Intent(AskQuestionActivity.this, SelectPictureActivity.class);
                    startActivityForResult(intent, 1);
                } else {
                    Intent showPicture = new Intent(AskQuestionActivity.this, ShowBigImage.class);
                    showPicture.putExtra("path", listPicture.get(position));
                    startActivity(showPicture);
                }
            }
        });
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

    private void updateDate(String path) {
        String type = path.substring(path.lastIndexOf("/"));
        String name = type.substring(type.lastIndexOf(".") + 1);
        OSSFile bigFile = ossService.getOssFile(bucket, "suiuu_content" + type);
        try {
            bigFile.setUploadFilePath(path, name);
            bigFile.ResumableUploadInBackground(new SaveCallback() {

                @Override
                public void onSuccess(String objectKey) {
                    picSuccessCount += 1;
                    if (picSuccessCount == listPicture.size()) {
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
                    Log.e(TAG, "[onFailure] - upload " + objectKey + " failed!\n" + ossException.toString());
                    ossException.printStackTrace();
                    ossException.getException().printStackTrace();
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化方法
     */
    public void initView() {

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_bar);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        listPicture = new ArrayList<>();

        tv_top_right = (TextView) findViewById(R.id.tv_top_right);
        gv_show_picture = (GridView) findViewById(R.id.gv_show_picture);
        iv_top_back = (ImageView) findViewById(R.id.iv_top_back);
        et_search_question = (EditText) findViewById(R.id.search_question);
        et_question_description = (EditText) findViewById(R.id.et_question_description);
        tv_show_your_location = (TextView) findViewById(R.id.tv_show_your_location);
        tv_theme_choice = (TextView) findViewById(R.id.tv_theme_choice);
        tv_area_choice = (TextView) findViewById(R.id.tv_area_choice);

        initPopupWindow();

        if (record == 1) {
            et_search_question.setHint(R.string.image_theme);
            et_question_description.setHint(R.string.activity_description);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == 9) {
            listPicture = data.getStringArrayListExtra("pictureMessage");
            gv_show_picture.setAdapter(new ShowGVPictureAdapter(this, listPicture));
        } else if (data != null && requestCode == REQUEST_CODE_MAP) {
            double latitude = data.getDoubleExtra("latitude", 0);
            double longitude = data.getDoubleExtra("longitude", 0);
            String locationAddress = data.getStringExtra("address");
            Log.i("suiuu", locationAddress + "logcation");
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
        //右边完成按钮
        if (mId == R.id.tv_top_right) {
            publish();
        } else if (mId == R.id.tv_theme_choice) {
            loadThemeDate();
        } else if (mId == R.id.iv_top_back) {
            finish();
        } else if (mId == R.id.tv_show_your_location) {
            startActivityForResult(new Intent(AskQuestionActivity.this, BaiduMapActivity.class), REQUEST_CODE_MAP);
        } else if (mId == R.id.tv_area_choice) {
            loadAreaDate();
        }
    }

    //点击发布按钮
    private void publish() {
        String str = SuiuuInformation.ReadVerification(this);
        RequestParams params = new RequestParams();
        if (TextUtils.isEmpty(et_search_question.getText().toString().trim())) {
            if (record == 1) {
                Toast.makeText(this, R.string.title_is_empty, Toast.LENGTH_SHORT).show();
                return;
            } else {
                Toast.makeText(this, R.string.your_ask_is_empty, Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if ("".equals(tv_theme_choice.getText().toString().trim())) {
            Toast.makeText(this, R.string.theme_is_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(tv_area_choice.getText().toString().trim())) {
            Toast.makeText(this, R.string.area_is_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(et_question_description.getText().toString().trim())) {
            if (record == 1) {
                Toast.makeText(this, R.string.activity_description_is_empty, Toast.LENGTH_SHORT).show();
                return;
            } else {
                Toast.makeText(this, R.string.ask_description_is_empty, Toast.LENGTH_SHORT).show();
                return;
            }
        }
        dialog.show();
        if (record == 1) {
            params.addBodyParameter("type", String.valueOf(3));
        } else {
            params.addBodyParameter("type", String.valueOf(2));
        }
        params.addBodyParameter(HttpServicePath.key, str);
        params.addBodyParameter("title", et_search_question.getText().toString().trim());
        params.addBodyParameter("addrId", areaCid);
        params.addBodyParameter("circleId", themeCid);
        params.addBodyParameter("content", et_question_description.getText().toString().trim());
        params.addBodyParameter("addr", tv_show_your_location.getText().toString().trim());
        List<String> picNameList = new ArrayList<>();
        for (String string : listPicture) {
            updateDate(string);
            String substring = string.substring(string.lastIndexOf("/"));
            picNameList.add(substring);
        }
        params.addBodyParameter("imgList", JsonUtil.getInstance().toJSON(picNameList));
        SuHttpRequest suHttpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.createLoop, new CreateLoopCallBack());
        suHttpRequest.setParams(params);
        suHttpRequest.requestNetworkData();

    }

    private void loadAreaDate() {
        String str = SuiuuInformation.ReadVerification(this);

        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, str);
        params.addBodyParameter("type", "2");

        SuHttpRequest suHttpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.LoopDataPath, new AreaRequestCallback());
        suHttpRequest.setParams(params);
        suHttpRequest.requestNetworkData();
    }

    /**
     * 初始化popupWindow
     */
    public void initPopupWindow() {
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

    //访问主题的数据
    public void loadThemeDate() {
        String str = SuiuuInformation.ReadVerification(this);
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
                    listView.setAdapter(new MyListAdapter(AskQuestionActivity.this, list));
                    themeOrArea = 1;
                    popupWindow.showAsDropDown(tv_theme_choice, 0, 10);
                } else {
                    Toast.makeText(AskQuestionActivity.this, "数据获取失败，请重试！", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(HttpException error, String msg) {
            Log.e(TAG, "主题数据请求失败:" + msg);
            Toast.makeText(AskQuestionActivity.this, "数据获取失败，请重试！", Toast.LENGTH_SHORT).show();
        }
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
                    listView.setAdapter(new MyListAdapter(AskQuestionActivity.this, list));
                    themeOrArea = 2;
                    popupWindow.showAsDropDown(tv_area_choice, 0, 10);
                } else {
                    Toast.makeText(AskQuestionActivity.this, "数据获取失败，请重试！", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(HttpException error, String msg) {
            Log.e(TAG, "地区数据请求失败:" + msg);
            Toast.makeText(AskQuestionActivity.this, "数据获取失败，请重试！", Toast.LENGTH_SHORT).show();
        }
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
                dataNum = (String)json.get("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            handler.sendEmptyMessage(getData_FAILURE);
            Log.e(TAG, "发布文章失败:" + s);
        }
    }
}

class MyListAdapter extends BaseAdapter {

    private static final String TAG = MyListAdapter.class.getSimpleName();

    private List<LoopBaseData> list;
    private Context context;

    public MyListAdapter(Context context, List<LoopBaseData> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder")
        View view = View.inflate(context, R.layout.adapter_simple_string, null);
        TextView tv_theme = (TextView) view.findViewById(R.id.tv_theme);
        tv_theme.setText(list.get(position).getcName());
        Log.i(TAG, list.get(position).getcName() + "----------");
        return view;
    }

}

