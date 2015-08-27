package com.minglang.suiuu.activity;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;
import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.customview.photodraweeview.CircleIndicator;
import com.minglang.suiuu.customview.photodraweeview.MultiTouchViewPager;
import com.minglang.suiuu.customview.photodraweeview.OnPhotoTapListener;
import com.minglang.suiuu.customview.photodraweeview.OnViewTapListener;
import com.minglang.suiuu.customview.photodraweeview.PhotoDraweeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：展示大图片
 * 创建人：Administrator
 * 创建时间：2015/8/25 17:25
 * 修改人：Administrator
 * 修改时间：2015/8/25 17:25
 * 修改备注：
 */
public class ShowBigPictureActivity extends BaseActivity{
    @Bind(R.id.ci_indicator)
    CircleIndicator indicator;
    @Bind(R.id.mv_view_pager)
    MultiTouchViewPager viewPager;
    private List<String> picList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_big_picture);
        ButterKnife.bind(this);
        picList = this.getIntent().getStringArrayListExtra("picList");
        int position = this.getIntent().getIntExtra("position",0);
        viewPager.setAdapter(new DraweePagerAdapter());
        indicator.setViewPager(viewPager);
        viewPager.setCurrentItem(position);

    }

    public class DraweePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return picList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup viewGroup, int position) {
            final PhotoDraweeView photoDraweeView = new PhotoDraweeView(viewGroup.getContext());
            PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
            controller.setUri(Uri.parse(picList.get(position)));
            controller.setOldController(photoDraweeView.getController());
            controller.getImageRequest();
            controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
                @Override
                public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                    super.onFinalImageSet(id, imageInfo, animatable);
                    if (imageInfo == null) {
                        return;
                    }
                    photoDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
                }
            });
            photoDraweeView.setController(controller.build());
            photoDraweeView.setOnPhotoTapListener(new OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                   finish();
                }
            });
            photoDraweeView.setOnViewTapListener(new OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                }
            });

            photoDraweeView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override public boolean onLongClick(View v) {
                    return true;
                }
            });

            try {
                viewGroup.addView(photoDraweeView, ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return photoDraweeView;
        }
    }

}
