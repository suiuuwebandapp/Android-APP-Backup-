package com.minglang.suiuu.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.minglang.suiuu.utils.imageloader.ImageLoaderUtils;

public class ViewHolder {

    private final SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    private ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mPosition = position;
        this.mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        // setTag
        mConvertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param context     上下文对象
     * @param convertView item的根View
     * @param parent      父View
     * @param layoutId    布局ID
     * @param position    位置
     * @return VieHolder对象
     */
    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder(context, parent, layoutId, position);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
        }
        return holder;
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId item内view的ID
     * @return 返回View实例
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId 控件ID
     * @param text   文本内容
     * @return ViewHolder实例
     */
    public ViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId     控件ID
     * @param drawableId 资源ID
     * @return ViewHolder实例
     */
    public ViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId 控件ID
     * @param bm     Bitmap
     * @return ViewHolder实例
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId 控件ID
     * @param url    图片网络地址
     * @return ViewHolder实例
     */
    public ViewHolder setImageByUrl(int viewId, String url) {
        ImageLoaderUtils.getInstance(3, ImageLoaderUtils.Type.LIFO).loadImage(url,
                (ImageView) getView(viewId));
        return this;
    }

    public int getPosition() {
        return mPosition;
    }

}
