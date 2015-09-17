package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/9/15.
 * 私聊数据实体类
 */
public class PrivateChat {

    /**
     * status : 1
     * data : [{"messageId":"1","sessionkey":"5942d384b13d825e05741a343704ba20","receiveId":"a4c1406ff4cc382389f19bf6ec3e55c1","senderId":"a0390caca8e533a3b16a2f1f8d762766","url":null,"content":"测试 一下\n2","sendTime":"2015-07-13 17:02:48","readTime":"2015-07-13 17:04:34","isRead":"1","isShield":"0"},{"messageId":"2","sessionkey":"5942d384b13d825e05741a343704ba20","receiveId":"a4c1406ff4cc382389f19bf6ec3e55c1","senderId":"a0390caca8e533a3b16a2f1f8d762766","url":null,"content":"3","sendTime":"2015-07-13 17:03:23","readTime":"2015-07-13 17:04:34","isRead":"1","isShield":"0"},{"messageId":"3","sessionkey":"5942d384b13d825e05741a343704ba20","receiveId":"a0390caca8e533a3b16a2f1f8d762766","senderId":"a4c1406ff4cc382389f19bf6ec3e55c1","url":null,"content":"4","sendTime":"2015-07-13 17:04:38","readTime":"2015-07-13 17:04:57","isRead":"1","isShield":"0"},{"messageId":"4","sessionkey":"5942d384b13d825e05741a343704ba20","receiveId":"a4c1406ff4cc382389f19bf6ec3e55c1","senderId":"a0390caca8e533a3b16a2f1f8d762766","url":null,"content":"5","sendTime":"2015-07-13 17:05:00","readTime":"2015-07-13 17:06:01","isRead":"1","isShield":"0"},{"messageId":"5","sessionkey":"5942d384b13d825e05741a343704ba20","receiveId":"a4c1406ff4cc382389f19bf6ec3e55c1","senderId":"a0390caca8e533a3b16a2f1f8d762766","url":null,"content":"6","sendTime":"2015-07-13 17:06:23","readTime":"2015-07-13 17:07:14","isRead":"1","isShield":"0"}]
     * message :
     * token : 4a68c697eec1b537215f36bd7670fee1
     */

    private int status;
    private String message;
    private String token;
    private List<PrivateChatData> data;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setData(List<PrivateChatData> data) {
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

    public List<PrivateChatData> getData() {
        return data;
    }

    public static class PrivateChatData {
        /**
         * messageId : 1
         * sessionkey : 5942d384b13d825e05741a343704ba20
         * receiveId : a4c1406ff4cc382389f19bf6ec3e55c1
         * senderId : a0390caca8e533a3b16a2f1f8d762766
         * url : null
         * content : 测试 一下
         * sendTime : 2015-07-13 17:02:48
         * readTime : 2015-07-13 17:04:34
         * isRead : 1
         * isShield : 0
         */

        private String messageId;
        private String sessionkey;
        private String receiveId;
        private String senderId;
        private Object url;
        private String content;
        private String sendTime;
        private String readTime;
        private String isRead;
        private String isShield;

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }

        public void setSessionkey(String sessionkey) {
            this.sessionkey = sessionkey;
        }

        public void setReceiveId(String receiveId) {
            this.receiveId = receiveId;
        }

        public void setSenderId(String senderId) {
            this.senderId = senderId;
        }

        public void setUrl(Object url) {
            this.url = url;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }

        public void setReadTime(String readTime) {
            this.readTime = readTime;
        }

        public void setIsRead(String isRead) {
            this.isRead = isRead;
        }

        public void setIsShield(String isShield) {
            this.isShield = isShield;
        }

        public String getMessageId() {
            return messageId;
        }

        public String getSessionkey() {
            return sessionkey;
        }

        public String getReceiveId() {
            return receiveId;
        }

        public String getSenderId() {
            return senderId;
        }

        public Object getUrl() {
            return url;
        }

        public String getContent() {
            return content;
        }

        public String getSendTime() {
            return sendTime;
        }

        public String getReadTime() {
            return readTime;
        }

        public String getIsRead() {
            return isRead;
        }

        public String getIsShield() {
            return isShield;
        }

    }

}