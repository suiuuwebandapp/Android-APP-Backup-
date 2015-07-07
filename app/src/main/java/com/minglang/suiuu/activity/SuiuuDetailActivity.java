package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.utils.HttpServicePath;

/**
 * 项目名称：Suiuu
 * 类描述：suiuu的详细列表
 * 创建人：Administrator
 * 创建时间：2015/5/4 19:13
 * 修改人：Administrator
 * 修改时间：2015/5/4 19:13
 * 修改备注：
 */
@SuppressWarnings("deprecation")
public class SuiuuDetailActivity extends BaseActivity {

//    private static final String TAG = SuiuuDetailActivity.class.getSimpleName();
//
//    private TextView tv_nickName;
//    private TextView tv_selfSign;
//    private TextView tv_title;
//    private TextView tv_content;
//    private JsonUtils jsonUtil = JsonUtils.getInstance();
//    private SuiuuDetailForInfo detailInfo;
//    private CircleImageView suiuu_details_user_head_image;
//    private TextView tv_service_time;
//    private TextView tv_suiuu_travel_time;
//    private TextView tv_atMost_people;
//    private TextView tv_price;
//    private TextView suiuu_details_praise;
//    private SuiuuDetailForData suiuuDetailData;
//    private TextView suiuu_details_collection;
//
//    /**
//     * 点赞Id
//     */
//    private String attentionId;
//
//    /**
//     * 收藏ID
//     */
//    private String collectionId;
//
//    private TextProgressDialog dialog;
//
//    //判断取消的是点赞还是收藏
//    private boolean isPraise;
//
//    private TextView suiuu_details_comments;
    private String tripId;
    private WebView suiuu_detail_web_view;
    private ProgressDialog pd = null;
//    private DisplayImageOptions options;
//    private List<SuiuuDetailForPicList> picList;
//    private LinearLayout dots_ll;
//    private LinearLayout top_news_viewpager;
//    private List<View> dotLists;
//    private TextView tv_choice;
//    private ImageView suiuu_details_back;
//    private BootstrapButton bb_consult;
//    private BootstrapButton bb_schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suiuu_details_activity);
        tripId = this.getIntent().getStringExtra("tripId");

        initView();
//        loadDate(tripId);
//        viewAction();
    }

//    private void viewAction() {
//        suiuu_details_praise.setOnClickListener(new MyOnclick());
//        suiuu_details_collection.setOnClickListener(new MyOnclick());
//        suiuu_details_comments.setOnClickListener(new MyOnclick());
//        suiuu_details_user_head_image.setOnClickListener(new MyOnclick());
//        suiuu_details_back.setOnClickListener(new MyOnclick());
//        bb_consult.setOnClickListener(new MyOnclick());
//        bb_schedule.setOnClickListener(new MyOnclick());
//    }
//
    private void initView() {
        pd = new ProgressDialog(this);
        pd.setMessage("玩命加载中.....");
        suiuu_detail_web_view = (WebView) findViewById(R.id.suiuu_detail_web_view);
        suiuu_detail_web_view.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
                pd.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
                pd.dismiss();
            }

        });
        suiuu_detail_web_view.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                super.onProgressChanged(view, newProgress);
                pd.setMessage("已经加载" + newProgress + "%");
                if(newProgress >= 70) {
                    pd.setMessage("已经加载" + 100 + "%");
                    pd.dismiss();
                }
            }

        });

        // 得到webview的设置
        WebSettings settings = suiuu_detail_web_view.getSettings();
        // webview支持缩放
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        //让webview支持js
        settings.setJavaScriptEnabled(true);


        suiuu_detail_web_view.addJavascriptInterface(new Object() {
            public void demoTest(String msg) {
                Toast.makeText(SuiuuDetailActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        }, "webViewDemo");
        String url = HttpServicePath.RootPath+"/wechat-trip/info?tripId="+tripId;
        suiuu_detail_web_view.loadUrl(url);
//        dialog = new TextProgressDialog(this);
//
//        tv_nickName = (TextView) findViewById(R.id.tv_nickname);
//        tv_selfSign = (TextView) findViewById(R.id.tv_self_sign);
//        tv_title = (TextView) findViewById(R.id.tv_title);
//        tv_content = (TextView) findViewById(R.id.tv_content);
//
//        suiuu_details_user_head_image = (CircleImageView) findViewById(R.id.suiuu_details_user_head_image);
//
//        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_suiuu_image)
//                .showImageForEmptyUri(R.drawable.default_suiuu_image).showImageOnFail(R.drawable.default_suiuu_image)
//                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
//                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
//
//        tv_service_time = (TextView) findViewById(R.id.tv_service_time);
//        tv_suiuu_travel_time = (TextView) findViewById(R.id.tv_suiuu_travel_time);
//        tv_atMost_people = (TextView) findViewById(R.id.tv_atmost_people);
//        tv_price = (TextView) findViewById(R.id.tv_price);
//        suiuu_details_praise = (TextView) findViewById(R.id.suiuu_details_praise);
//        suiuu_details_collection = (TextView) findViewById(R.id.suiuu_details_collection);
//        suiuu_details_comments = (TextView) findViewById(R.id.suiuu_details_comments);
//        dots_ll = (LinearLayout) findViewById(R.id.dots_ll);
//        top_news_viewpager = (LinearLayout) findViewById(R.id.top_news_viewpager);
//        tv_choice = (TextView) findViewById(R.id.tv_choiced);
//        suiuu_details_back = (ImageView) findViewById(R.id.suiuu_details_back);
//
//        bb_consult = (BootstrapButton) findViewById(R.id.bb_consult);
//        bb_schedule = (BootstrapButton) findViewById(R.id.bb_schedule);
    }
//
//    //填充数据
//    public void fullOfData() {
//        tv_choice.setText(detailInfo.getTravelTime() + "人选择过");
//        tv_title.setText(detailInfo.getTitle());
//        tv_content.setText(detailInfo.getInfo());
//        tv_nickName.setText(suiuuDetailData.getUserInfo().getNickname());
//
//        if (TextUtils.isEmpty(suiuuDetailData.getUserInfo().getIntro())) {
//            tv_selfSign.setText("该用户没有个性签名");
//        } else {
//            tv_selfSign.setText(suiuuDetailData.getUserInfo().getIntro());
//        }
//
//        ImageLoader.getInstance().displayImage(suiuuDetailData.getUserInfo().getHeadImg(), suiuu_details_user_head_image, options);
//        tv_service_time.setText("服务时间:     " + detailInfo.getStartTime() + "-" + detailInfo.getEndTime());
//        tv_suiuu_travel_time.setText("随游时长:     " + detailInfo.getTravelTime() + "个小时");
//
//        if (detailInfo.getMaxUserCount() == null) {
//            tv_atMost_people.setText("最多人数:     0 人");
//        } else {
//            tv_atMost_people.setText("最多人数:     " + detailInfo.getMaxUserCount() + "人");
//        }
//
//        tv_price.setText("￥:  " + detailInfo.getBasePrice());
//
//        //判断是否显示点赞
//        if (suiuuDetailData.getPraise().size() >= 1) {
//            attentionId = suiuuDetailData.getPraise().get(0).getAttentionId();
//        }
//
//        if (!TextUtils.isEmpty(attentionId)) {
//            suiuu_details_praise.setTextColor(getResources().getColor(R.color.text_select_true));
//            suiuu_details_praise.setCompoundDrawables(DrawableUtils.setBounds(getResources()
//                    .getDrawable(R.drawable.icon_praise_red)), null, null, null);
//        }
//
//        if (suiuuDetailData.getAttention().size() >= 1) {
//            collectionId = suiuuDetailData.getAttention().get(0).getAttentionId();
//        }
//
//        if (!TextUtils.isEmpty(collectionId)) {
//            suiuu_details_collection.setTextColor(getResources().getColor(R.color.text_select_true));
//            suiuu_details_collection.setCompoundDrawables(DrawableUtils.setBounds(getResources()
//                    .getDrawable(R.drawable.icon_collection_yellow)), null, null, null);
//        }
//
//        picList = suiuuDetailData.getPicList();
//
//        if (picList.size() >= 1) {
//            setViewPager();
//        }
//
//
//    }
//
//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    private void setViewPager() {
//        initDot(picList.size());
//        RollViewPager mViewPager = new RollViewPager(this, dotLists, new RollViewPager.PagerClickCallBack() {
//            @Override
//            public void pagerClick(int position) {
////				Intent intent = new Intent(ct,DetaiAct.class);
////				ct.startActivity(intent);
//                Toast.makeText(SuiuuDetailActivity.this, "xxxxxx", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        //设置viewpager的宽高
////        LinearLayout.LayoutParams layoutParams = new LayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
////                LayoutParams.WRAP_CONTENT));
//
//        List<String> imageUrlLists = new ArrayList<>();
//        for (SuiuuDetailForPicList picDetail : picList) {
//            imageUrlLists.add(picDetail.getUrl());
//        }
//
//        // 把图片的url地址传进去
//        mViewPager.setImageUrlList(imageUrlLists);
//        // 把title的url地址传进去
////        mViewPager.setTitleList(top_news_title, titleLists);
//        // 定义一个viewpage可以跳动的方法
//        mViewPager.startRoll();
//
//        /**
//         * 把viewpager加入到布局里面
//         */
//        top_news_viewpager.removeAllViews();
//        top_news_viewpager.addView(mViewPager);
//    }
//
//    //访问网络
//    private void loadDate(String tripId) {
//        dialog.showDialog();
//        RequestParams params = new RequestParams();
//        params.addBodyParameter("trId", tripId);
//        params.addBodyParameter(HttpServicePath.key, verification);
//        Log.i("suiuu", "vertification=" + verification);
//        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
//                HttpServicePath.getSuiuuItemInfo, new SuiuuItemInfoCallBack());
//        httpRequest.setParams(params);
//        httpRequest.requestNetworkData();
//    }
//
//    /**
//     * 请求数据网络接口回调
//     */
//    class SuiuuItemInfoCallBack extends RequestCallBack<String> {
//
//        @Override
//        public void onSuccess(ResponseInfo<String> responseInfo) {
//            try {
//                JSONObject json = new JSONObject(responseInfo.result);
//                String status = json.getString("status");
//                if ("1".equals(status)) {
//                    dialog.dismissDialog();
//                    SuiuuDataDetail detail = jsonUtil.fromJSON(SuiuuDataDetail.class, responseInfo.result);
//                    suiuuDetailData = detail.getData();
//                    detailInfo = detail.getData().getInfo();
//                    fullOfData();
//                } else if ("-3".equals(status)) {
//                    Toast.makeText(getApplicationContext(), "登录信息过期,请重新登录", Toast.LENGTH_SHORT).show();
//                    AppUtils.intentLogin(getApplicationContext());
//                    SuiuuDetailActivity.this.finish();
//                } else {
//                    dialog.dismissDialog();
//                    Toast.makeText(SuiuuDetailActivity.this, "数据请求失败，请稍候再试！", Toast.LENGTH_SHORT).show();
//                }
//            } catch (Exception e) {
//                dialog.dismissDialog();
//                Toast.makeText(SuiuuDetailActivity.this, "数据请求失败，请稍候再试！", Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onFailure(HttpException error, String msg) {
//            dialog.dismissDialog();
//            Toast.makeText(SuiuuDetailActivity.this, "数据请求失败，请稍候再试！", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    /**
//     * 点赞接口
//     */
//    private void addPraiseRequest() {
//        RequestParams params = new RequestParams();
//        params.addBodyParameter("travelId", detailInfo.getTripId());
//        params.addBodyParameter(HttpServicePath.key, verification);
//        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
//                HttpServicePath.travelAddPraise, new addPraiseRequestCallBack());
//        httpRequest.setParams(params);
//        httpRequest.requestNetworkData();
//    }
//
//    /**
//     * 收藏随游接口
//     */
//    private void addCollection() {
//        RequestParams params = new RequestParams();
//        params.addBodyParameter("travelId", detailInfo.getTripId());
//        params.addBodyParameter(HttpServicePath.key, verification);
//        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
//                HttpServicePath.travelAddCollection, new addCollectionCallback());
//        httpRequest.setParams(params);
//        httpRequest.requestNetworkData();
//    }
//
//    /**
//     * 点赞,收藏取消接口
//     *
//     * @param cancelId 取消Id
//     */
//    private void collectionArticleCancel(String cancelId) {
//        RequestParams params = new RequestParams();
//        params.addBodyParameter("attentionId", cancelId);
//        params.addBodyParameter(HttpServicePath.key, verification);
//        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
//                HttpServicePath.CollectionArticleCancelPath, new CollectionArticleCancelRequestCallback());
//        httpRequest.setParams(params);
//        httpRequest.requestNetworkData();
//    }
//
//    /**
//     * 点赞回调接口
//     */
//    class addPraiseRequestCallBack extends RequestCallBack<String> {
//
//        @Override
//        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
//            try {
//                JSONObject json = new JSONObject(stringResponseInfo.result);
//                String status = json.getString("status");
//                String data = json.getString("data");
//                if ("1".equals(status)) {
//                    attentionId = data;
//                    suiuu_details_praise.setTextColor(getResources().getColor(R.color.text_select_true));
//                    suiuu_details_praise.setCompoundDrawables(DrawableUtils.setBounds(getResources()
//                            .getDrawable(R.drawable.icon_praise_red)), null, null, null);
//                    Toast.makeText(SuiuuDetailActivity.this, "点赞成功", Toast.LENGTH_SHORT).show();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                Toast.makeText(SuiuuDetailActivity.this, "点赞失败，请稍候再试！", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        @Override
//        public void onFailure(HttpException e, String s) {
//            DeBugLog.e(TAG, "点赞失败:" + e.getMessage());
//            Toast.makeText(SuiuuDetailActivity.this, "点赞失败，请稍候再试！", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    /**
//     * 取消点赞收藏回调接口
//     */
//    class CollectionArticleCancelRequestCallback extends RequestCallBack<String> {
//
//        @Override
//        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
//            try {
//                JSONObject json = new JSONObject(stringResponseInfo.result);
//                String status = json.getString("status");
//                String data = json.getString("data");
//                if ("1".equals(status) && "success".equals(data)) {
//                    if (isPraise) {
//                        attentionId = null;
//                        suiuu_details_praise.setTextColor(getResources().getColor(R.color.white));
//                        suiuu_details_praise.setCompoundDrawables(setImgDrawTextPosition(R.drawable.icon_praise_white), null, null, null);
//                        Toast.makeText(SuiuuDetailActivity.this, "取消点赞成功", Toast.LENGTH_SHORT).show();
//                    } else {
//                        collectionId = null;
//                        suiuu_details_collection.setTextColor(getResources().getColor(R.color.white));
//                        suiuu_details_collection.setCompoundDrawables(setImgDrawTextPosition(R.drawable.icon_collection_white), null, null, null);
//                        Toast.makeText(SuiuuDetailActivity.this, "取消收藏成功", Toast.LENGTH_SHORT).show();
//
//                    }
//                } else {
//                    Toast.makeText(SuiuuDetailActivity.this, "网络错误,请稍候再试", Toast.LENGTH_SHORT).show();
//                }
//            } catch (JSONException e) {
//                Toast.makeText(SuiuuDetailActivity.this, "网络错误,请稍候再试", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//
//        @Override
//        public void onFailure(HttpException e, String s) {
//            Toast.makeText(SuiuuDetailActivity.this, "网络错误,请稍候再试", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    /**
//     * 收藏文章回调接口
//     */
//    class addCollectionCallback extends RequestCallBack<String> {
//
//        @Override
//        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
//            try {
//                JSONObject json = new JSONObject(stringResponseInfo.result);
//                String status = json.getString("status");
//                String data = json.getString("data");
//                if ("1".equals(status)) {
//                    collectionId = data;
//                    suiuu_details_collection.setTextColor(getResources().getColor(R.color.text_select_true));
//                    suiuu_details_collection.setCompoundDrawables(setImgDrawTextPosition(R.drawable.icon_collection_yellow), null, null, null);
//                    Toast.makeText(SuiuuDetailActivity.this, "收藏文章成功", Toast.LENGTH_SHORT).show();
//                }
//            } catch (Exception e) {
//                Toast.makeText(SuiuuDetailActivity.this, "收藏失败，请稍候再试！", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//
//        @Override
//        public void onFailure(HttpException e, String s) {
//            DeBugLog.i("suiuu", s);
//            Toast.makeText(SuiuuDetailActivity.this, "网络异常，请稍候再试！", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public Drawable setImgDrawTextPosition(int img) {
//        Drawable drawable = this.getResources().getDrawable(img);
//        // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
//        if (drawable != null) {
//            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//        }
//        return drawable;
//    }
//
//    class MyOnclick implements View.OnClickListener {
//
//        @Override
//        public void onClick(View v) {
//            int vId = v.getId();
//            switch (vId) {
//                case R.id.suiuu_details_praise:
//                    if (TextUtils.isEmpty(attentionId)) {
//                        //为空添加点赞
//                        addPraiseRequest();
//                    } else {
//                        isPraise = true;
//                        //取消点赞
//                        collectionArticleCancel(attentionId);
//                    }
//                    break;
//                case R.id.suiuu_details_collection:
//                    if (TextUtils.isEmpty(collectionId)) {
//                        addCollection();
//                    } else {
//                        isPraise = false;
//                        //取消点赞
//                        collectionArticleCancel(collectionId);
//                    }
//                    break;
//                case R.id.suiuu_details_comments:
//                    Intent intent = new Intent(SuiuuDetailActivity.this, CommentsActivity.class);
//                    intent.putExtra("tripId", tripId);
//                    startActivity(intent);
//                    break;
//                case R.id.suiuu_details_user_head_image:
//                    Intent intentOtherUser = new Intent(SuiuuDetailActivity.this, OtherUserActivity.class);
//                    intentOtherUser.putExtra("userSign", suiuuDetailData.getUserInfo().getUserSign());
//                    startActivity(intentOtherUser);
//                    break;
//                case R.id.suiuu_details_back:
//                    finish();
//                    break;
//                //点击咨询按钮
//                case R.id.bb_consult:
//                    if (isExistUser(suiuuDetailData.getUserInfo().getUserId())) {
//                        //当前用户在数据库中已经存在;
//                    } else {
//                        addUser();
//                        Log.i("suiuu", "不存在了");
//                    }
//                    Intent intentConsult = new Intent(SuiuuDetailActivity.this, ChatActivity.class);
//                    intentConsult.putExtra("userId", suiuuDetailData.getUserInfo().getUserId());
//                    startActivity(intentConsult);
//                    break;
//                case R.id.bb_schedule:
//                    Intent intentSchedule = new Intent(SuiuuDetailActivity.this, SuiuuOrderActivity.class);
//                    intentSchedule.putExtra("titleInfo",detailInfo.getTitle());
//                    intentSchedule.putExtra("titleImg",detailInfo.getTitleImg());
//                    intentSchedule.putExtra("price",detailInfo.getBasePrice());
//                    intentSchedule.putExtra("tripId",detailInfo.getTripId());
//                    startActivity(intentSchedule);
//
//                    break;
//
//                default:
//                    break;
//            }
//        }
//    }
//
//    /**
//     * 动态初始化点
//     */
//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    private void initDot(int size) {
//        Utils util = Utils.newInstance();
//        dotLists = new ArrayList<>();
//        dots_ll.removeAllViews();
//        LayoutParams params;
//        for (int i = 0; i < size; i++) {
//            View pointView = new View(this);
//            params = new LayoutParams(util.dip2px(15, this), util.dip2px(15, this));
//            params.leftMargin = util.dip2px(10, this);
//            pointView.setLayoutParams(params);
//            pointView.setEnabled(false);
//            pointView.setBackgroundResource(R.drawable.ic_launcher);
//            if (i == 0) {
//                pointView.setBackgroundResource(R.drawable.choiced_press);
//            } else {
//                pointView.setBackgroundResource(R.drawable.choiced_normal);
//            }
//            dotLists.add(pointView);
//            dots_ll.addView(pointView);
//        }
//    }
//    /**
//     * 添加一条记录
//     */
//    public void addUser() {
//        UserDbHelper helper = new UserDbHelper(this);
//        //得到可读可写数据库
//        SQLiteDatabase db = helper.getReadableDatabase();
//        //执行sql语句
//        db.execSQL("insert into user (userid,nikename,titleimg) values (?,?,?)", new Object[]{suiuuDetailData.getUserInfo().getUserId(), suiuuDetailData.getUserInfo().getNickname(), suiuuDetailData.getUserInfo().getHeadImg()});
//        db.close();
//        Log.i("suiuu", "添加user成功");
//    }

//    public boolean isExistUser(String userId) {
//        boolean isexist = false;
//        UserDbHelper helper = new UserDbHelper(this);
//        //得到可读数据库
//        SQLiteDatabase db = helper.getReadableDatabase();
//        //得到数据库查询的结果集的游标（指针）
//        Cursor cursor = db.rawQuery("select * from user where userid = ? ", new String[]{userId});
//        while (cursor.moveToNext()) {
//            isexist = true;
//        }
//        cursor.close();
//        db.close();
//        return isexist;
//    }
}
