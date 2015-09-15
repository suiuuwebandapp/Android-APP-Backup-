package com.minglang.suiuu.entity;

/**
 * Created by Administrator on 2015/9/15.
 * 私聊数据实体类
 */
public class PrivateChat {

    /**
     * type : say
     * sender_id : a4c1406ff4cc382389f19bf6ec3e55c1
     * sender_name : dorlen
     * sender_HeadImg : http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg
     * receive_id : dc03956692f43d209b6b513bc1d627d6
     * content : 55
     * time : 2015-09-15 09:27:04
     * session_key : c3fa009a4105c1624353e7bc20af03e1
     * to_client_id : a4c1406ff4cc382389f19bf6ec3e55c1
     */

    private String type;
    private String sender_id;
    private String sender_name;
    private String sender_HeadImg;
    private String receive_id;
    private String content;
    private String time;
    private String session_key;
    private String to_client_id;

    public void setType(String type) {
        this.type = type;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public void setSender_HeadImg(String sender_HeadImg) {
        this.sender_HeadImg = sender_HeadImg;
    }

    public void setReceive_id(String receive_id) {
        this.receive_id = receive_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public void setTo_client_id(String to_client_id) {
        this.to_client_id = to_client_id;
    }

    public String getType() {
        return type;
    }

    public String getSender_id() {
        return sender_id;
    }

    public String getSender_name() {
        return sender_name;
    }

    public String getSender_HeadImg() {
        return sender_HeadImg;
    }

    public String getReceive_id() {
        return receive_id;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public String getSession_key() {
        return session_key;
    }

    public String getTo_client_id() {
        return to_client_id;
    }

}