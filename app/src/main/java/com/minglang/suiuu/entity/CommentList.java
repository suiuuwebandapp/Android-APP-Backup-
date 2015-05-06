package com.minglang.suiuu.entity;

import java.util.List;

/**
 * 项目名称：Suiuu
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/5/6 17:48
 * 修改人：Administrator
 * 修改时间：2015/5/6 17:48
 * 修改备注：
 */
public class CommentList {
    private String status;
    private CommentListData data;
    private String message;
    private String token;


    @Override
    public String toString() {
        return "CommentList{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CommentListData getData() {
        return data;
    }

    public void setData(CommentListData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public class CommentListData{
        private List<LoopArticleCommentList> data;
        private SuiuuDataForMessage msg;

        public List<LoopArticleCommentList> getData() {
            return data;
        }

        public void setData(List<LoopArticleCommentList> data) {
            this.data = data;
        }

        public SuiuuDataForMessage getMsg() {
            return msg;
        }

        public void setMsg(SuiuuDataForMessage msg) {
            this.msg = msg;
        }
    }
}
