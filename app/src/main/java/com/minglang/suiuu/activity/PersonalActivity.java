package com.minglang.suiuu.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.PersonalPostAdapter;
import com.minglang.suiuu.adapter.PersonalShowPageAdapter;
import com.minglang.suiuu.adapter.PersonalSuiuuAdapter;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.OtherUser;
import com.minglang.suiuu.entity.OtherUserDataArticle;
import com.minglang.suiuu.entity.OtherUserDataInfo;
import com.minglang.suiuu.entity.TravelList;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtil;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人信息
 */
public class PersonalActivity extends Activity {

    private static final String TAG = PersonalActivity.class.getSimpleName();

    private static final String USERSIGNKEY = "userSign";

    /**
     * 返回
     */
    private ImageView back;

    private ImageView headLayoutBackground;

    /**
     * 头像
     */
    private CircleImageView headImageView;

    /**
     * 用户昵称
     */
    private TextView userName;

    /**
     * 位置
     */
    private TextView location;

    /**
     * 签名
     */
    private TextView signature;

    private TextView praise;

    private ViewPager personalShowPage;

    private GridView suiuuGrid, postGrid;

    private ImageLoader imageLoader;

    private String userSign, verification;

    private ProgressDialog dialog;

    /**
     * 用户随游数据适配器
     */
    private PersonalSuiuuAdapter personalSuiuuAdapter;
    /**
     * 用户帖子数据适配器
     */
    private PersonalPostAdapter personalPostAdapter;

    /**
     * 用户随游列表
     */
    private List<TravelList> travelList;

    /**
     * 用户帖子列表
     */
    private List<OtherUserDataArticle> articleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        initView();

        ViewAction();

        getData();
    }

    /**
     * 将数据绑定到View
     *
     * @param str Json字符串
     */
    private void userData2View(String str) {
        Log.i(TAG, "个人主页数据:" + str);
        if (!TextUtils.isEmpty(str)) {
            try {
                OtherUser user = JsonUtil.getInstance().fromJSON(OtherUser.class, str);
                OtherUserDataInfo info = user.getData().getUser();

                String name = info.getNickname();
                if (!TextUtils.isEmpty(name)) {
                    userName.setText(name);
                }

                String intro = info.getIntro();
                if (!TextUtils.isEmpty(intro)) {
                    signature.setText(intro);
                }

                String praiseNumber = user.getData().getFansNumb();
                if (!TextUtils.isEmpty(praiseNumber)) {
                    praise.setText(praiseNumber);
                } else {
                    praise.setText("0");
                }

                String strHeadImage = info.getHeadImg();
                if (!TextUtils.isEmpty(strHeadImage)) {
                    imageLoader.displayImage(strHeadImage, headImageView);
                }

                //随游列表
                travelList = user.getData().getTravelList();
                if (travelList != null && travelList.size() > 0) {
                    personalSuiuuAdapter.setList(travelList);
                }
                //贴子列表
                articleList = user.getData().getArticleList();
                if (articleList != null && articleList.size() > 0) {
                    personalPostAdapter.setList(articleList);
                }
            } catch (Exception e) {
                Log.e(TAG, "数据解析异常:" + e.getMessage());
                Toast.makeText(this, getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
        }

    }

    private void getData() {

        if (dialog != null) {
            dialog.show();
        }

        getPersonalInfo4Service();
    }

    /**
     * 网络请求
     */
    private void getPersonalInfo4Service() {
        RequestParams params = new RequestParams();
        params.addBodyParameter(USERSIGNKEY, userSign);
        params.addBodyParameter(HttpServicePath.key, verification);

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.userInformationPath, new PersonalInfoCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 控件动作
     */
    private void ViewAction() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        personalShowPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        suiuuGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TravelList travel = travelList.get(position);
                String tripId = travel.getTripId();
                Intent intent = new Intent(PersonalActivity.this, SuiuuDetailActivity.class);
                intent.putExtra("tripId", tripId);
                startActivity(intent);
            }
        });

        postGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OtherUserDataArticle article = articleList.get(position);
                String articleId = article.getArticleId();
                Intent intent = new Intent(PersonalActivity.this, LoopArticleActivity.class);
                intent.putExtra("articleId", articleId);
                intent.putExtra("TAG", TAG);
                startActivity(intent);
            }
        });

    }

    /**
     * 初始化方法
     */
    private void initView() {

        dialog = new ProgressDialog(this);
        dialog.setMessage(getResources().getString(R.string.load_wait));
        dialog.setCanceledOnTouchOutside(false);

        back = (ImageView) findViewById(R.id.personalBack);
        headLayoutBackground = (ImageView) findViewById(R.id.personalHeadLayoutImage);
        headImageView = (CircleImageView) findViewById(R.id.personalHeadImage);
        userName = (TextView) findViewById(R.id.personalName);
        location = (TextView) findViewById(R.id.personalLocation);
        signature = (TextView) findViewById(R.id.personalSignature);
        praise = (TextView) findViewById(R.id.personalCollection);

        personalShowPage = (ViewPager) findViewById(R.id.personalShowPage);

        imageLoader = ImageLoader.getInstance();
        if (!imageLoader.isInited()) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        }

        userSign = SuiuuInfo.ReadUserSign(this);
        verification = SuiuuInfo.ReadVerification(this);

        Log.i(TAG, "userSign:" + userSign);
        Log.i(TAG, "verification:" + verification);

        suiuuGrid = new GridView(this);
        suiuuGrid.setHorizontalSpacing(10);
        suiuuGrid.setVerticalSpacing(10);
        suiuuGrid.setNumColumns(2);

        postGrid = new GridView(this);
        postGrid.setHorizontalSpacing(10);
        postGrid.setVerticalSpacing(10);
        postGrid.setNumColumns(2);

        personalSuiuuAdapter = new PersonalSuiuuAdapter(this);
        suiuuGrid.setAdapter(personalSuiuuAdapter);

        personalPostAdapter = new PersonalPostAdapter(this);
        postGrid.setAdapter(personalPostAdapter);

        List<View> viewList = new ArrayList<>();
        viewList.add(suiuuGrid);
        viewList.add(postGrid);

        PersonalShowPageAdapter showPageAdapter = new PersonalShowPageAdapter(this, viewList);
        personalShowPage.setAdapter(showPageAdapter);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private class PersonalInfoCallBack extends RequestCallBack<String> {


        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            String str = responseInfo.result;
            userData2View(str);

        }

        @Override
        public void onFailure(HttpException e, String s) {
            Log.e(TAG, "数据请求失败:" + s);

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            Toast.makeText(PersonalActivity.this, getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();

        }
    }

}
