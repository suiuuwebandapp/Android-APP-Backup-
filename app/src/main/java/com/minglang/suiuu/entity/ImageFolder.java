package com.minglang.suiuu.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ImageFolder implements Serializable {
    /**
     * 图片的文件夹路径
     */
    private String dir;

    /**
     * 第一张图片的路径
     */
    private String firstImagePath;

    /**
     * 文件夹的名称
     */
    private String name;

    public List<ImageItem> images = new ArrayList<>();

    /**
     * 图片的数量
     */
    private int count;

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
        int lastIndexOf = this.dir.lastIndexOf("/");
        this.name = this.dir.substring(lastIndexOf);
    }

    public String getFirstImagePath() {
        return firstImagePath;
    }

    public void setFirstImagePath(String firstImagePath) {
        this.firstImagePath = firstImagePath;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ImageFolder{" +
                "dir='" + dir + '\'' +
                ", firstImagePath='" + firstImagePath + '\'' +
                ", name='" + name + '\'' +
                ", images=" + images +
                ", count=" + count +
                '}';
    }

}
