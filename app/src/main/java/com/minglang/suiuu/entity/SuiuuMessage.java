package com.minglang.suiuu.entity;

import java.util.List;

/**
 * 消息数据实体类
 * <p/>
 * Created by Administrator on 2015/5/3.
 */
public class SuiuuMessage {

    public String status;

    public SuiuuMessageBase data;

    public String message;

    public String token;

    public String getStatus() {
        return status;
    }

    public SuiuuMessageBase getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "SuiuuMessage{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public class SuiuuMessageBase {

        public List<SuiuuMessageData> data;

        public SuiuuMessageMsg msg;

        public List<SuiuuMessageData> getData() {
            return data;
        }

        public SuiuuMessageMsg getMsg() {
            return msg;
        }

        @Override
        public String toString() {
            return "SuiuuMessageBase{" +
                    "data=" + data +
                    ", msg=" + msg +
                    '}';
        }

        public class SuiuuMessageData {

            public String relativeId;

            public String relativeType;

            public String remindId;

            public String headImg;

            public String nickname;

            public String createUserSign;

            public String getRelativeId() {
                return relativeId;
            }

            public String rType;

            public String getRelativeType() {
                return relativeType;
            }

            public String getRemindId() {
                return remindId;
            }

            public String getHeadImg() {
                return headImg;
            }

            public String getNickname() {
                return nickname;
            }

            public String getCreateUserSign() {
                return createUserSign;
            }

            public String getRtype() {
                return rType;
            }

            @Override
            public String toString() {
                return "SuiuuMessageData{" +
                        "relativeId='" + relativeId + '\'' +
                        ", relativeType='" + relativeType + '\'' +
                        ", remindId='" + remindId + '\'' +
                        ", headImg='" + headImg + '\'' +
                        ", nickname='" + nickname + '\'' +
                        ", createUserSign='" + createUserSign + '\'' +
                        ", rType='" + rType + '\'' +
                        '}';
            }
        }

        public class SuiuuMessageMsg {

            public String currentPage;

            public String startRow;

            public String pageSize;

            public String sortName;

            public String sortType;

            public String totalCount;

            public boolean showAll;

            public String getCurrentPage() {
                return currentPage;
            }

            public String getStartRow() {
                return startRow;
            }

            public String getPageSize() {
                return pageSize;
            }

            public String getSortName() {
                return sortName;
            }

            public String getSortType() {
                return sortType;
            }

            public String getTotalCount() {
                return totalCount;
            }

            public boolean isShowAll() {
                return showAll;
            }

            @Override
            public String toString() {
                return "SuiuuMessageMsg{" +
                        "currentPage='" + currentPage + '\'' +
                        ", startRow='" + startRow + '\'' +
                        ", pageSize='" + pageSize + '\'' +
                        ", sortName='" + sortName + '\'' +
                        ", sortType='" + sortType + '\'' +
                        ", totalCount='" + totalCount + '\'' +
                        ", showAll=" + showAll +
                        '}';
            }
        }

    }

}