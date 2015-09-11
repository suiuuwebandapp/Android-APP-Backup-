package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/9/11.
 * 私信列表数据实体类
 */
public class PrivateLetter {

    /**
     * status : 1
     * data : [{"nickname":"★RòdG﹌","headImg":"http://q.qlogo.cn/qqapp/101206430/954E9D7AEE616B1FEE21953A138F2B79/100","sessionId":"1","sessionKey":"5942d384b13d825e05741a343704ba20","userId":"a4c1406ff4cc382389f19bf6ec3e55c1","relateId":"a0390caca8e533a3b16a2f1f8d762766","lastConcatTime":"2015-07-13 17:06:23","lastContentInfo":"6","isRead":"1"}]
     * message :
     * token : 2729d6594017f62936f40f5e891713f1
     */

    private int status;
    private String message;
    private String token;
    private List<PrivateLetterData> data;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setData(List<PrivateLetterData> data) {
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

    public List<PrivateLetterData> getData() {
        return data;
    }

    public static class PrivateLetterData {
        /**
         * nickname : ★RòdG﹌
         * headImg : http://q.qlogo.cn/qqapp/101206430/954E9D7AEE616B1FEE21953A138F2B79/100
         * sessionId : 1
         * sessionKey : 5942d384b13d825e05741a343704ba20
         * userId : a4c1406ff4cc382389f19bf6ec3e55c1
         * relateId : a0390caca8e533a3b16a2f1f8d762766
         * lastConcatTime : 2015-07-13 17:06:23
         * lastContentInfo : 6
         * isRead : 1
         */

        private String nickname;
        private String headImg;
        private String sessionId;
        private String sessionKey;
        private String userId;
        private String relateId;
        private String lastConcatTime;
        private String lastContentInfo;
        private String isRead;

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public void setSessionKey(String sessionKey) {
            this.sessionKey = sessionKey;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public void setRelateId(String relateId) {
            this.relateId = relateId;
        }

        public void setLastConcatTime(String lastConcatTime) {
            this.lastConcatTime = lastConcatTime;
        }

        public void setLastContentInfo(String lastContentInfo) {
            this.lastContentInfo = lastContentInfo;
        }

        public void setIsRead(String isRead) {
            this.isRead = isRead;
        }

        public String getNickname() {
            return nickname;
        }

        public String getHeadImg() {
            return headImg;
        }

        public String getSessionId() {
            return sessionId;
        }

        public String getSessionKey() {
            return sessionKey;
        }

        public String getUserId() {
            return userId;
        }

        public String getRelateId() {
            return relateId;
        }

        public String getLastConcatTime() {
            return lastConcatTime;
        }

        public String getLastContentInfo() {
            return lastContentInfo;
        }

        public String getIsRead() {
            return isRead;
        }
    }
}