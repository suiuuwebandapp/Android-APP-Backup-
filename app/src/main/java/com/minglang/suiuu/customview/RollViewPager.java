package com.minglang.suiuu.customview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lidroid.xutils.BitmapUtils;
import com.minglang.suiuu.R;
import com.minglang.suiuu.chat.activity.ShowBigImage;

import java.util.List;


public class RollViewPager extends ViewPager {

    public RollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RollViewPager(Context context) {
        super(context);
    }

    /**
     * 计算图片所占用的内存： 长 * 宽 * 图片字节数； eg ： 800 * 480 * 4 = 图片== 位图 Config.ARGB_8888
     * = 32/8=4； Config.ARGB_4444 = 16/8=2 Config.RGB_565 =2 Config.ALPHA_8 = 1
     */

    /**
     * 初始化有几个点
     */
    private Context ct;
    private List<View> dotLists;

    public interface PagerClickCallBack {
        void pagerClick(int position);
    }

    public PagerClickCallBack pagerClickCallBack;

    public RollViewPager(Context ct, List<View> dotLists, PagerClickCallBack pagerClickCallBack) {
        super(ct);
        this.ct = ct;
        this.dotLists = dotLists;
        bitmapUtils = new BitmapUtils(ct);
        bitmapUtils.configDefaultBitmapConfig(Config.ARGB_4444);
        viewPagerTask = new ViewPagerTask();
        this.pagerClickCallBack = pagerClickCallBack;
    }

    private class ViewPagerTask implements Runnable {
        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageUrlLists.size();
            handler.obtainMessage().sendToTarget();
        }
    }

    /**
     * 目的：希望我们的代码复用性强：我们必须自定义一个可以滚动的viewpager 1 把需要展示的具体的url地址传进来。 2
     * 把具体需要展示的标题传进来 3 把展示标题的textview传进来。 4 把具体跳动的点数传进来
     *
     */
    /**
     * 初始化ImageUrl的集合
     *
     * @param imageUrlLists
     */
    private List<String> imageUrlLists;

    public void setImageUrlList(List<String> imageUrlLists) {
        this.imageUrlLists = imageUrlLists;
    }


    private TextView top_news_title;
    private List<String> titleLists;
    private BitmapUtils bitmapUtils;

    /**
     * 初始化标题和TextView
     */
    public void setTitleList(TextView top_news_title, List<String> titleLists) {
        this.top_news_title = top_news_title;
        this.titleLists = titleLists;
        if (top_news_title != null && titleLists != null
                && titleLists.size() > 0) {
            top_news_title.setText(titleLists.get(0));
        }
    }

    private boolean has_startRoll = false;

    /**
     * viewpager滚动的方法
     */
    public void startRoll() {
        if (!has_startRoll) {
            has_startRoll = true;

            RollViewPager.this
                    .setOnPageChangeListener(new MyOnPageChangeListener());

            ViewPagerAdapter adater = new ViewPagerAdapter();
            RollViewPager.this.setAdapter(adater);

        }
        handler.postDelayed(viewPagerTask, 4000);
    }

    private int currentItem = 0;
    private int oldPosition = 0;

    private class MyOnPageChangeListener implements OnPageChangeListener {
        boolean isAutoPlay = false;

        @Override
        public void onPageSelected(int arg0) {
            currentItem = arg0;

            if (titleLists != null && titleLists.size() > 0
                    && top_news_title != null) {
                top_news_title.setText(titleLists.get(arg0));
            }

            if (dotLists != null && dotLists.size() > 0) {
                dotLists.get(arg0).setBackgroundResource(R.drawable.choiced_press);
                dotLists.get(oldPosition).setBackgroundResource(
                        R.drawable.choiced_normal);
            }
            oldPosition = arg0;
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            switch (arg0) {
                case 1:// 手势滑动，空闲中
                    isAutoPlay = false;
                    break;
                case 2:// 界面切换中
                    isAutoPlay = true;
                    break;
                case 0:// 滑动结束，即切换完毕或者加载完毕
                    // 当前为最后一张，此时从右向左滑，则切换到第一张
                    if (RollViewPager.this.getCurrentItem() == RollViewPager.this.getAdapter().getCount() - 1 && !isAutoPlay) {
                        RollViewPager.this.setCurrentItem(0);
                    }
                    // 当前为第一张，此时从左向右滑，则切换到最后一张
                    else if (RollViewPager.this.getCurrentItem() == 0 && !isAutoPlay) {
                        RollViewPager.this.setCurrentItem(RollViewPager.this.getAdapter().getCount() - 1);
                    }
                    break;
            }

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            RollViewPager.this.setCurrentItem(currentItem);
            startRoll();
            return false;
        }
    });
    private ViewPagerTask viewPagerTask;


    /**
     * 当view销毁的时候调用的方法
     */
    @Override
    protected void onDetachedFromWindow() {
        handler.removeCallbacksAndMessages(null);
        super.onDetachedFromWindow();
    }

    public class ViewPagerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            (container).removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            View view = View.inflate(ct, R.layout.viewpager_item, null);
            SimpleDraweeView image = (SimpleDraweeView) view.findViewById(R.id.image);
            Uri uri = Uri.parse(imageUrlLists.get(position));
            image.setImageURI(uri);
            (container).addView(view);
            image.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent showPic = new Intent(ct, ShowBigImage.class);
                    showPic.putExtra("remotepath", imageUrlLists.get(position));
                    showPic.putExtra("isHuanXin", false);
                    ct.startActivity(showPic);
                }
            });
            return view;
        }

        @Override
        public int getCount() {
            return imageUrlLists.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }

}
