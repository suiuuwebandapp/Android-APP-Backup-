package com.minglang.suiuu.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;

import java.io.IOException;

public class DrawableUtils {

    public static Drawable setBounds(Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }

    public static Drawable setBounds(Context context, int drawableId) {
        Drawable drawable = context.getResources().getDrawable(drawableId);
        return setBounds(drawable);
    }

    /**
     * 修正图片偏转
     *
     * @param bitmap 偏转的图片
     * @param degree 偏转的角度
     * @return 修正后的图片
     */
    public static Bitmap roateBitmap(Bitmap bitmap, int degree) {
        if (bitmap != null) {
            if (degree == 0) {
                return bitmap;
            }
            Matrix matrix = new Matrix();
            matrix.postRotate(degree);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } else {
            return null;
        }
    }

    /**
     * 得到照片偏转角度
     *
     * @param path 照片路径
     * @return 照片偏转角度
     */
    public static int getPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

}