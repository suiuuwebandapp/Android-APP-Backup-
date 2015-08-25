package com.minglang.suiuu.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.swipelistview.SwipeListView;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.lasque.tusdk.core.TuSdk;
import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.impl.activity.TuFragment;
import org.lasque.tusdk.impl.components.TuEditMultipleComponent;
import org.lasque.tusdk.impl.components.base.TuSdkComponent;
import org.lasque.tusdk.impl.components.base.TuSdkHelperComponent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * 随问和随记公用的gridview的Adapter
 */
public class EasyTackPhotoAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    private Activity activity;
    private String type;
    private ImageLoader imageLoader;

    private DisplayImageOptions options;
    private List<String> changeContentList;

    private SwipeListView swipeListView;
    private int tempPosition;
    BitmapUtils bitmapUtils;

    /**
     * 组件帮助方法
     */
    // see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/base/TuSdkHelperComponent.html
    public TuSdkHelperComponent componentHelper;

    public EasyTackPhotoAdapter(Context context, List<String> list, List<String> changeContentList, String type) {
        super();
        this.context = context;
        this.list = list;
        activity = (Activity) context;
        this.type = type;
        this.changeContentList = changeContentList;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));

        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.loading)
                .showImageForEmptyUri(R.drawable.loading)
                .showImageOnFail(R.drawable.loading_error)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.NONE_SAFE).bitmapConfig(Bitmap.Config.RGB_565).build();
        this.componentHelper = new TuSdkHelperComponent(activity);
        bitmapUtils = new BitmapUtils(context);
    }

    public EasyTackPhotoAdapter(Context context, List<String> list, String type) {
        super();
        this.context = context;
        this.list = list;
        activity = (Activity) context;
        this.type = type;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));

        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_suiuu_image)
                .showImageForEmptyUri(R.drawable.default_suiuu_image)
                .showImageOnFail(R.drawable.default_suiuu_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.NONE_SAFE).bitmapConfig(Bitmap.Config.RGB_565).build();
        this.componentHelper = new TuSdkHelperComponent(activity);
        bitmapUtils = new BitmapUtils(context);
    }

    public void onDateChange(List<String> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public void setSwipeListView(SwipeListView swipeListView) {
        this.swipeListView = swipeListView;
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_easy_tack_photo, position);
        convertView = holder.getConvertView();

        ImageView pictureView = holder.getView(R.id.item_tack_photo);
        EditText picDescriptionView = holder.getView(R.id.item_tack_description);
        Button picRemoveView = holder.getView(R.id.item_tack_remove);


        if ("1".equals(type)) {
            picDescriptionView.setText(changeContentList.get(position));
            imageLoader.displayImage(list.get(position), pictureView, options);
        } else {
            bitmapUtils.display(pictureView, list.get(position));
        }
        picDescriptionView.setHint(R.string.picture_description);
        pictureView.setOnClickListener(new pictureViewClick(position));
        picRemoveView.setOnClickListener(new picRemoveViewClick(position, this));
        return convertView;
    }

    private class pictureViewClick implements View.OnClickListener {

        private int position;

        private pictureViewClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            tempPosition = position;
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(list.get(position));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap  = BitmapFactory.decodeStream(fis);
                TuSdkResult s = new TuSdkResult();
                openEditMultiple(bitmap,s,null,null);

        }
    }

    private class picRemoveViewClick implements View.OnClickListener {

        private int position;
        private EasyTackPhotoAdapter easyTackPhotoAdapter;
        private picRemoveViewClick(int position, EasyTackPhotoAdapter easyTackPhotoAdapter) {
            this.position = position;
            this.easyTackPhotoAdapter = easyTackPhotoAdapter;
        }

        @Override
        public void onClick(View v) {
            if (list.size() <= 1) {
                Toast.makeText(context, "至少选择一张图片", Toast.LENGTH_SHORT).show();
            } else {
                list.remove(position);
                swipeListView.closeOpenedItems();
                setListViewHeight(swipeListView);
            }
        }
    }

    // 重新计算子listview的高度
    public void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)) + 10;
        listView.setLayoutParams(params);
    }
    /**
     * 开启多功能图片编辑
     *
     * @param result
     * @param error
     * @param lastFragment
     */
    private void openEditMultiple(Bitmap bitmap,TuSdkResult result, Error error,
                                  TuFragment lastFragment)
    {
        if (result == null || error != null) return;
        // 组件委托
        TuSdkComponent.TuSdkComponentDelegate delegate = new TuSdkComponent.TuSdkComponentDelegate()
        {
            @Override
            public void onComponentFinished(TuSdkResult result, Error error,
                                            TuFragment lastFragment)
            {
                String path = result.imageSqlInfo.path;
                if(!TextUtils.isEmpty(path)) {
                    resetPic(path);
                }
            }
        };

        // 组件选项配置
        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/TuEditMultipleComponent.html
        TuEditMultipleComponent component = null;

        if (lastFragment == null)
        {
            component = TuSdk.editMultipleCommponent(
                    this.componentHelper.activity(), delegate);
        }
        else
        {
            component = TuSdk.editMultipleCommponent(lastFragment, delegate);
        }
        // 设置图片
        result.image = bitmap;
        component.setImage(result.image)
                // 设置系统照片
                .setImageSqlInfo(result.imageSqlInfo)
                        // 设置临时文件
                .setTempFilePath(result.imageFile)
                        // 在组件执行完成后自动关闭组件
                .setAutoDismissWhenCompleted(true)
                        // 开启组件
                .showComponent();
    }
    public void resetPic(String newPath) {
        list.set(tempPosition,newPath);
        this.notifyDataSetChanged();
//        FrameLayout item = (FrameLayout)this.getItem(tempPosition);
//        ImageView viewById = (ImageView ) item.findViewById(R.id.item_tack_info_layout).findViewById(R.id.item_tack_photo);
//        bitmapUtils.display(viewById, newPath);
    }
}