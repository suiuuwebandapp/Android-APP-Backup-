package com.minglang.suiuu.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AppUtils {

    private static final String TAG = AppUtils.class.getSimpleName();

    private static AppUtils appUtils;

    public static AppUtils newInstance() {
        if (appUtils == null) {
            appUtils = new AppUtils();
        }
        return appUtils;
    }

    /**
     * dp转像素
     *
     * @param dpValue dp数值
     * @return 对应像素值
     */
    public static int dip2px(float dpValue, Context context) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 像素转dp
     *
     * @param pxValue 像素数值
     * @return 对应dp值
     */
    public static int px2dip(float pxValue, Context context) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 选择一张图片 图片类型，这里是image/*，当然也可以设置限制 如：image/jpeg等
     *
     * @param activity Activity
     */
    @SuppressLint("InlinedApi")
    public static void selectPicture(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            // 由于startActivityForResult()的第二个参数"requestCode"为常量，
            // 个人喜好把常量用一个类全部装起来，不知道各位大神对这种做法有异议没？
            activity.startActivityForResult(intent, AppConstant.KITKAT_LESS);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            // 由于Intent.ACTION_OPEN_DOCUMENT的版本是4.4以上的内容
            // 所以注意这个方法的最上面添加了@SuppressLint("InlinedApi")
            // 如果客户使用的不是4.4以上的版本，因为前面有判断，所以根本不会走else，
            // 也就不会出现任何因为这句代码引发的错误
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            activity.startActivityForResult(intent, AppConstant.KITKAT_ABOVE);
        }
    }

    /**
     * 裁剪图片
     *
     * @param activity Activity
     * @param uri      图片的Uri
     */
    public static void cropPicture(Activity activity, Uri uri) {
        Intent innerIntent = new Intent("com.android.camera.action.CROP");
        innerIntent.setDataAndType(uri, "image/*");
        innerIntent.putExtra("crop", "true");// 才能出剪辑的小方框，不然没有剪辑功能，只能选取图片
        innerIntent.putExtra("aspectX", 1); // 放大缩小比例的X
        innerIntent.putExtra("aspectY", 1);// 放大缩小比例的X 这里的比例为： 1:1
        innerIntent.putExtra("outputX", 200); // 这个是限制输出图片大小
        innerIntent.putExtra("outputY", 200);
        innerIntent.putExtra("return-data", true);
        innerIntent.putExtra("scale", true);
        activity.startActivityForResult(innerIntent, AppConstant.INTENT_CROP);
    }

    /**
     * 下面几个方法来自于Stack Overflow,虽然来自大神，但大神的代码也不是就那样？ 看不懂的地方挨个百度。
     * -----------------------割------------------------- Get a file path from a
     * Uri. This will get the the path for Storage Access Framework Documents,
     * as well as the _data field for the MediaStore and other file-based
     * ContentProviders.
     *
     * @param context The context.
     * @param uri     The Uri to query.
     */
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else {
                if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    switch (type) {
                        case "image":
                            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                            break;
                        case "video":
                            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                            break;
                        case "audio":
                            contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                            break;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{split[1]};

                    return getDataColumn(context, contentUri, selection,
                            selectionArgs);
                }
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * Scroll 套 listView 重新计算listView高度
     *
     * @param listView 要计算的ListView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
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
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        params.height += 5;//if without this statement,the listview will be a little short
        listView.setLayoutParams(params);
    }

    /**
     * 根据两点经纬度计算两点之间的距离  返回的是千米
     *
     * @param lng1 点1经度
     * @param lat1 点1纬度
     * @param lng2 点2经度
     * @param lat2 点2纬度
     * @return 相距距离
     */
    public static double distanceByLngLat(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = lat1 * Math.PI / 180;
        double radLat2 = lat2 * Math.PI / 180;
        double a = radLat1 - radLat2;
        double b = lng1 * Math.PI / 180 - lng2 * Math.PI / 180;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * 6378137.0;// 取WGS84标准参考椭球中的地球长半径(单位:m)
        s = Math.round(s * 10000) / 10000;
        return Math.round((int) s / 100d) / 10d;
    }

    /**
     * 判断网络是否连接
     *
     * @param context 上下文对象
     * @return 网络是否连接
     */
    public static boolean isNetworkConnect(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 比较日期前后
     *
     * @param beforeDate 前一个日期
     * @param afterDate  后一个日期
     * @return 结果值
     */
    public static int compareDate(String beforeDate, String afterDate) {
        DateFormat df = new SimpleDateFormat("yyyy:MM:dd", Locale.getDefault());
        try {
            Date dt1 = df.parse(beforeDate);
            Date dt2 = df.parse(afterDate);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据生日计算年龄
     *
     * @param birthday 生日
     * @return 年龄
     */
    public static String calculateAge(String birthday) {
        L.i(TAG, "birthday:" + birthday);
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            Date date = format.parse(birthday);
            L.i(TAG, "Date:" + date.toString());

            Calendar calendar = Calendar.getInstance();
            int yearNow = calendar.get(Calendar.YEAR);

            calendar.setTime(date);
            int yearBirth = calendar.get(Calendar.YEAR);

            L.i(TAG, "yearNow:" + yearNow + ",yearBirth:" + yearBirth);
            if (yearNow > yearBirth) {
                return String.valueOf(yearNow - yearBirth);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static int getStatusBarHeight(Activity activity) {
        int result = 0;
        Resources res = activity.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }

}