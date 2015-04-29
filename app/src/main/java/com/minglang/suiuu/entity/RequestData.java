package com.minglang.suiuu.entity;

/**
 * 第三方相关信息实体类
 * <p/>
 * Created by Administrator on 2015/4/25.
 */
public class RequestData {

    public String ID;

    public String NickName;

    public String Gender;

    public String ImagePath;

    public String Type;

    public RequestData() {

    }

    public RequestData(String ID, String NickName, String Gender, String ImagePath, String Type) {
        this.ID = ID;
        this.NickName = NickName;
        this.Gender = Gender;
        this.ImagePath = ImagePath;
        this.Type = Type;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    @Override
    public String toString() {
        return "RequestData{" +
                "ID='" + ID + '\'' +
                ", NickName='" + NickName + '\'' +
                ", Gender='" + Gender + '\'' +
                ", ImagePath='" + ImagePath + '\'' +
                ", Type='" + Type + '\'' +
                '}';
    }
}
