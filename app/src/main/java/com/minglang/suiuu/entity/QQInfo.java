package com.minglang.suiuu.entity;

/**
 * Created by Administrator on 2015/4/17.
 * <p/>
 * 使用QQ登录时使用，用于保存相关信息<br/>
 * <p/>
 * 包括用户昵称、用户头像地址、性别
 */
public class QQInfo {

    private String NickName;

    private String ImagePath;

    private String Gender;

    public boolean isNUll() {
        if (NickName != null) {
            if (ImagePath != null) {
                if (Gender != null) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    @Override
    public String toString() {
        return "QQInfo{" +
                "NickName='" + NickName + '\'' +
                ", ImagePath='" + ImagePath + '\'' +
                ", Gender='" + Gender + '\'' +
                '}';
    }
}
