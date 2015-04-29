package com.minglang.suiuu.entity;

/**
 * Created by LZY on 2015/4/13 0013.
 */
public class ImageItem {

    public String path;

    public ImageItem(String p) {
        this.path = p;
    }

    @Override
    public String toString() {
        return "ImageItem{" +
                "path='" + path + '\'' +
                '}';
    }
}
