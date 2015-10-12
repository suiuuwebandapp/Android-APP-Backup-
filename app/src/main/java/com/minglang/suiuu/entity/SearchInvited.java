package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/10/12.
 * <p/>
 * 搜索得到的可邀请用户的数据实体类
 */
public class SearchInvited {

    /**
     * status : 1
     * data : [{"nickname":"pasde ","headImg":"http://image.suiuu.com/suiuu_default/default_g2.png","userSign":"bafa09ac5d05309b86fc724f0c8eb2ea"},{"nickname":"asdxx1023","headImg":"http://static.qyer.com/data/avatar/002/03/89/55_avatar_big.jpg?v=1390894698 ","userSign":"58e1cbcb50693dc38b9ac9b6226bd33c"}]
     * message :
     * token : 743d674dd538283b4997674cc2866096
     */

    private int status;
    private String message;
    private String token;
    private List<SearchInvitedData> data;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setData(List<SearchInvitedData> data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public List<SearchInvitedData> getData() {
        return data;
    }

    public static class SearchInvitedData {
        /**
         * nickname : pasde
         * headImg : http://image.suiuu.com/suiuu_default/default_g2.png
         * userSign : bafa09ac5d05309b86fc724f0c8eb2ea
         */

        private String nickname;
        private String headImg;
        private String userSign;

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public void setUserSign(String userSign) {
            this.userSign = userSign;
        }

        public String getNickname() {
            return nickname;
        }

        public String getHeadImg() {
            return headImg;
        }

        public String getUserSign() {
            return userSign;
        }
    }

}