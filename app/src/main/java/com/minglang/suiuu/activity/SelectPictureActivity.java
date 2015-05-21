package com.minglang.suiuu.activity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.BaseHolderAdapter;
import com.minglang.suiuu.adapter.SelectPictureAdapter;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.entity.ImageFolder;
import com.minglang.suiuu.entity.ImageItem;
import com.minglang.suiuu.popupwindow.BasePopupWindowForListView;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SelectPictureActivity extends BaseActivity {

    private static final String TAG = SelectPictureActivity.class.getSimpleName();

    /**
     * 最多选择图片的个数
     */
    private static int MAX_NUM = 9;

    private static final int OPEN_CAMERA = 100;

    private DisplayImageOptions options;

    private ContentResolver mContentResolver;

    private ImageFolder imageAll, currentImageFolder;

    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private Map<String, Integer> tmpDir = new HashMap<>();
    private List<ImageFolder> mDirPaths = new ArrayList<>();

    /**
     * 已选择的图片
     */
    private ArrayList<String> selectedPicture = new ArrayList<>();

    private Context context = this;

    /**
     * 返回键
     */
    private ImageView back;

    /**
     * 完成
     */
    private TextView complete;

    /**
     * 选择图片
     */
    private GridView selectImage;

    /**
     * 所有图片
     */
    private TextView allPicture;

    private SelectPictureAdapter selectPictureAdapter;

    private ListPictureDirPopupWindow listPictureDirPopupWindow;
    private static final int result = 9;
    private int state = 0;

    public SelectPictureActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_picture);
        state = this.getIntent().getIntExtra("state", 0);
        initView();

        initPopupWindow();

        ObtainThumbnail();

        ViewAction();

        selectPictureAdapter = new SelectPictureAdapter(context, currentImageFolder.images,
                currentImageFolder, selectedPicture, complete);
        selectImage.setAdapter(selectPictureAdapter);


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

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (1 == state) {
                    if (selectedPicture.size() < 1) {
                        Toast.makeText(SelectPictureActivity.this, R.string.choice_picture, Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(SelectPictureActivity.this, EasyTackPhotoActivity.class);
                        intent.putStringArrayListExtra("pictureMessage", selectedPicture);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Intent intent = new Intent();
                    intent.putStringArrayListExtra("pictureMessage", selectedPicture);
                    setResult(result, intent);
                    finish();
                }

            }
        });

        allPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listPictureDirPopupWindow.setAnimationStyle(R.style.anim_popup_dir);
                listPictureDirPopupWindow.showAsDropDown(allPicture, 0, 0);
                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = .3f;
                getWindow().setAttributes(lp);
            }
        });

        selectImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    openCamera();
                }
            }
        });
    }

    /**
     * 获取缩略图
     */
    private void ObtainThumbnail() {
        Cursor mCursor = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.ImageColumns.DATA}, "", null,
                MediaStore.MediaColumns.DATE_ADDED + " DESC");

        if (mCursor.moveToFirst()) {
            int _date = mCursor.getColumnIndex(MediaStore.Images.Media.DATA);
            do {
                // 获取图片的路径
                String path = mCursor.getString(_date);

                imageAll.images.add(new ImageItem(path));

                // 获取该图片的父路径名
                File parentFile = new File(path).getParentFile();

                if (parentFile == null) {
                    continue;
                }

                ImageFolder imageFolder;

                String dirPath = parentFile.getAbsolutePath();

                if (!tmpDir.containsKey(dirPath)) {
                    // 初始化imageFolder
                    imageFolder = new ImageFolder();
                    imageFolder.setDir(dirPath);
                    imageFolder.setFirstImagePath(path);
                    mDirPaths.add(imageFolder);
                    tmpDir.put(dirPath, mDirPaths.indexOf(imageFolder));
                } else {
                    imageFolder = mDirPaths.get(tmpDir.get(dirPath));
                }

                imageFolder.images.add(new ImageItem(path));

            } while (mCursor.moveToNext());
            mCursor.close();

            for (int i = 0; i < mDirPaths.size(); i++) {
                ImageFolder folder = mDirPaths.get(i);
                DeBugLog.d(TAG, i + "-----" + folder.getName() + "---" + folder.images.size());
            }
            tmpDir = null;
        }

    }


    /**
     * 使用相机拍照
     */
    protected void openCamera() {
        if (selectedPicture.size() + 1 > MAX_NUM) {
            Toast.makeText(context, "最多选择" + MAX_NUM + "张", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageUri = getOutputMediaFileUri();
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, OPEN_CAMERA);
    }

    private String cameraPath = null;

    /**
     * 用于拍照时获取输出的Uri
     */
    protected Uri getOutputMediaFileUri() {
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Night");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                DeBugLog.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault()).format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        cameraPath = mediaFile.getAbsolutePath();
        return Uri.fromFile(mediaFile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && cameraPath != null) {
            selectedPicture.add(cameraPath);
        }
    }

    /**
     * 初始化PopupWindow
     */
    private void initPopupWindow() {

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(R.layout.check_img_list_dir, null);

        listPictureDirPopupWindow = new ListPictureDirPopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                screenHeight / 10 * 7, true, mDirPaths);
    }

    /**
     * 初始化方法
     */
    private void initView() {
        RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.selectPicture_root);
        if (isKITKAT) {
            if (navigationBarHeight <= 0) {
                rootLayout.setPadding(0, statusBarHeight, 0, 0);
            } else {
                rootLayout.setPadding(0, statusBarHeight, 0, navigationBarHeight);
            }
        }

        mContentResolver = getContentResolver();
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_head_image)
                .showImageForEmptyUri(R.drawable.default_head_image).showImageOnFail(R.drawable.default_head_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();

        imageAll = new ImageFolder();
        imageAll.setDir("/所有图片");
        currentImageFolder = imageAll;
        mDirPaths.add(imageAll);

        back = (ImageView) findViewById(R.id.iv_top_back);
        complete = (TextView) findViewById(R.id.tv_top_right);

        selectImage = (GridView) findViewById(R.id.selectPicture);
        allPicture = (TextView) findViewById(R.id.selectPictureAll);

    }

    class ListPictureDirPopupWindow extends BasePopupWindowForListView<ImageFolder> {

        private ListView mListDir;

        public ListPictureDirPopupWindow(View contentView, int width, int height,boolean focusable, List<ImageFolder> data) {
            super(contentView, width, height, focusable, data);
        }

        @Override
        protected void beforeInitWeNeedSomeParams(Object... params) {

        }

        @Override
        public void initViews() {
            mListDir = (ListView) findViewById(R.id.id_list_dir);
            mListDir.setAdapter(new BaseHolderAdapter<ImageFolder>(context, mData, R.layout.item_list_dir) {
                @Override
                public void convert(ViewHolder helper, ImageFolder item, long position) {

                    ImageView imageView = helper.getView(R.id.id_dir_item_image);
                    TextView title = helper.getView(R.id.id_dir_item_name);
                    TextView count = helper.getView(R.id.id_dir_item_count);
                    ImageView choose = helper.getView(R.id.choose);

                    imageLoader.displayImage("file://" + item.getFirstImagePath(), imageView, options);
                    title.setText(item.getName());
                    count.setText(item.images.size() + "张");
                    choose.setVisibility(currentImageFolder == item ? View.VISIBLE : View.GONE);
                }
            });
        }

        @Override
        public void initEvents() {
            mListDir.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    currentImageFolder = mDirPaths.get(position);
                    ListPictureDirPopupWindow.this.dismiss();
                    selectPictureAdapter.setImageFolder(currentImageFolder);
                    allPicture.setText(currentImageFolder.getName());

                    WindowManager.LayoutParams lp = getWindow().getAttributes();
                    lp.alpha = 1f;
                    getWindow().setAttributes(lp);

                }
            });
        }

        @Override
        public void init() {

        }

        @Override
        public void dismiss() {
            super.dismiss();
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            if (lp.alpha != 1f) {
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        }
    }

}
