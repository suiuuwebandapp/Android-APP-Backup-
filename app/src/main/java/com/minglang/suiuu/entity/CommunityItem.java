package com.minglang.suiuu.entity;

import java.util.List;

/**
 * 问题详情数据实体类
 * <p/>
 * Created by Administrator on 2015/7/20.
 */
public class CommunityItem {

    /**
     * data : {"question":[{"qTitle":"测试03","qCityId":"15","attentionNumber":"4","headImg":"http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg","qId":"3","pvNumber":"42","qInviteAskUser":"085963dc0af031709b032725e3ef18f5","qAddr":"地址03","qUserSign":"085963dc0af031709b032725e3ef18f5","qCreateTime":"2015-07-13 18:10:56","qContent":"内容03","qCountryId":"7","qTag":"1"}],"answer":[{"headImg":"http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg","nickname":"✨yao","aContent":"回答内容01","aUserSign":"085963dc0af031709b032725e3ef18f5","aCreateTime":"2015-07-13 14:59:34","aId":"1","qId":"3"},{"headImg":"http://image.suiuu.com/suiuu_head/20150804085358_14724.png","nickname":"3dorlen","aContent":"02","aUserSign":"a4c1406ff4cc382389f19bf6ec3e55c1","aCreateTime":"2015-07-13 15:32:21","aId":"2","qId":"3"},{"headImg":"http://image.suiuu.com/suiuu_head/20150804085358_14724.png","nickname":"3dorlen","aContent":"qq","aUserSign":"a4c1406ff4cc382389f19bf6ec3e55c1","aCreateTime":"2015-07-13 18:14:45","aId":"3","qId":"3"},{"headImg":"http://image.suiuu.com/suiuu_head/20150804085358_14724.png","nickname":"3dorlen","aContent":"test","aUserSign":"a4c1406ff4cc382389f19bf6ec3e55c1","aCreateTime":"2015-07-22 17:16:05","aId":"4","qId":"3"},{"headImg":"http://image.suiuu.com/suiuu_head/20150804085358_14724.png","nickname":"3dorlen","aContent":"回答测试","aUserSign":"a4c1406ff4cc382389f19bf6ec3e55c1","aCreateTime":"2015-08-10 16:41:35","aId":"8","qId":"3"},{"headImg":"http://image.suiuu.com/suiuu_head/20150804085358_14724.png","nickname":"3dorlen","aContent":"回答测试","aUserSign":"a4c1406ff4cc382389f19bf6ec3e55c1","aCreateTime":"2015-08-10 16:42:37","aId":"9","qId":"3"}],"attention":[{"addTime":"2015-07-23 10:44:21","userSign":"a4c1406ff4cc382389f19bf6ec3e55c1","relativeId":"3","attentionId":"196","relativeType":"13","status":"1"}]}
     * message :
     * status : 1
     * token : 221eaa518eb2bdf2e35e6b0d28411ffc
     */
    private CommunityItemData data;
    private String message;
    private int status;
    private String token;

    public void setData(CommunityItemData data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public CommunityItemData getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public String getToken() {
        return token;
    }

    public static class CommunityItemData {
        /**
         * question : [{"qTitle":"测试03","qCityId":"15","attentionNumber":"4","headImg":"http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg","qId":"3","pvNumber":"42","qInviteAskUser":"085963dc0af031709b032725e3ef18f5","qAddr":"地址03","qUserSign":"085963dc0af031709b032725e3ef18f5","qCreateTime":"2015-07-13 18:10:56","qContent":"内容03","qCountryId":"7","qTag":"1"}]
         * answer : [{"headImg":"http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg","nickname":"✨yao","aContent":"回答内容01","aUserSign":"085963dc0af031709b032725e3ef18f5","aCreateTime":"2015-07-13 14:59:34","aId":"1","qId":"3"},{"headImg":"http://image.suiuu.com/suiuu_head/20150804085358_14724.png","nickname":"3dorlen","aContent":"02","aUserSign":"a4c1406ff4cc382389f19bf6ec3e55c1","aCreateTime":"2015-07-13 15:32:21","aId":"2","qId":"3"},{"headImg":"http://image.suiuu.com/suiuu_head/20150804085358_14724.png","nickname":"3dorlen","aContent":"qq","aUserSign":"a4c1406ff4cc382389f19bf6ec3e55c1","aCreateTime":"2015-07-13 18:14:45","aId":"3","qId":"3"},{"headImg":"http://image.suiuu.com/suiuu_head/20150804085358_14724.png","nickname":"3dorlen","aContent":"test","aUserSign":"a4c1406ff4cc382389f19bf6ec3e55c1","aCreateTime":"2015-07-22 17:16:05","aId":"4","qId":"3"},{"headImg":"http://image.suiuu.com/suiuu_head/20150804085358_14724.png","nickname":"3dorlen","aContent":"回答测试","aUserSign":"a4c1406ff4cc382389f19bf6ec3e55c1","aCreateTime":"2015-08-10 16:41:35","aId":"8","qId":"3"},{"headImg":"http://image.suiuu.com/suiuu_head/20150804085358_14724.png","nickname":"3dorlen","aContent":"回答测试","aUserSign":"a4c1406ff4cc382389f19bf6ec3e55c1","aCreateTime":"2015-08-10 16:42:37","aId":"9","qId":"3"}]
         * attention : [{"addTime":"2015-07-23 10:44:21","userSign":"a4c1406ff4cc382389f19bf6ec3e55c1","relativeId":"3","attentionId":"196","relativeType":"13","status":"1"}]
         */
        private List<QuestionEntity> question;
        private List<AnswerEntity> answer;
        private List<AttentionEntity> attention;

        public void setQuestion(List<QuestionEntity> question) {
            this.question = question;
        }

        public void setAnswer(List<AnswerEntity> answer) {
            this.answer = answer;
        }

        public void setAttention(List<AttentionEntity> attention) {
            this.attention = attention;
        }

        public List<QuestionEntity> getQuestion() {
            return question;
        }

        public List<AnswerEntity> getAnswer() {
            return answer;
        }

        public List<AttentionEntity> getAttention() {
            return attention;
        }

        public static class QuestionEntity {
            /**
             * qTitle : 测试03
             * qCityId : 15
             * attentionNumber : 4
             * headImg : http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg
             * qId : 3
             * pvNumber : 42
             * qInviteAskUser : 085963dc0af031709b032725e3ef18f5
             * qAddr : 地址03
             * qUserSign : 085963dc0af031709b032725e3ef18f5
             * qCreateTime : 2015-07-13 18:10:56
             * qContent : 内容03
             * qCountryId : 7
             * qTag : 1
             */
            private String qTitle;
            private String qCityId;
            private String attentionNumber;
            private String headImg;
            private String qId;
            private String pvNumber;
            private String qInviteAskUser;
            private String qAddr;
            private String qUserSign;
            private String qCreateTime;
            private String qContent;
            private String qCountryId;
            private String qTag;

            public void setQTitle(String qTitle) {
                this.qTitle = qTitle;
            }

            public void setQCityId(String qCityId) {
                this.qCityId = qCityId;
            }

            public void setAttentionNumber(String attentionNumber) {
                this.attentionNumber = attentionNumber;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public void setQId(String qId) {
                this.qId = qId;
            }

            public void setPvNumber(String pvNumber) {
                this.pvNumber = pvNumber;
            }

            public void setQInviteAskUser(String qInviteAskUser) {
                this.qInviteAskUser = qInviteAskUser;
            }

            public void setQAddr(String qAddr) {
                this.qAddr = qAddr;
            }

            public void setQUserSign(String qUserSign) {
                this.qUserSign = qUserSign;
            }

            public void setQCreateTime(String qCreateTime) {
                this.qCreateTime = qCreateTime;
            }

            public void setQContent(String qContent) {
                this.qContent = qContent;
            }

            public void setQCountryId(String qCountryId) {
                this.qCountryId = qCountryId;
            }

            public void setQTag(String qTag) {
                this.qTag = qTag;
            }

            public String getQTitle() {
                return qTitle;
            }

            public String getQCityId() {
                return qCityId;
            }

            public String getAttentionNumber() {
                return attentionNumber;
            }

            public String getHeadImg() {
                return headImg;
            }

            public String getQId() {
                return qId;
            }

            public String getPvNumber() {
                return pvNumber;
            }

            public String getQInviteAskUser() {
                return qInviteAskUser;
            }

            public String getQAddr() {
                return qAddr;
            }

            public String getQUserSign() {
                return qUserSign;
            }

            public String getQCreateTime() {
                return qCreateTime;
            }

            public String getQContent() {
                return qContent;
            }

            public String getQCountryId() {
                return qCountryId;
            }

            public String getQTag() {
                return qTag;
            }
        }

        public static class AnswerEntity {
            /**
             * headImg : http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg
             * nickname : ✨yao
             * aContent : 回答内容01
             * aUserSign : 085963dc0af031709b032725e3ef18f5
             * aCreateTime : 2015-07-13 14:59:34
             * aId : 1
             * qId : 3
             */
            private String headImg;
            private String nickname;
            private String aContent;
            private String aUserSign;
            private String aCreateTime;
            private String aId;
            private String qId;

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setAContent(String aContent) {
                this.aContent = aContent;
            }

            public void setAUserSign(String aUserSign) {
                this.aUserSign = aUserSign;
            }

            public void setACreateTime(String aCreateTime) {
                this.aCreateTime = aCreateTime;
            }

            public void setAId(String aId) {
                this.aId = aId;
            }

            public void setQId(String qId) {
                this.qId = qId;
            }

            public String getHeadImg() {
                return headImg;
            }

            public String getNickname() {
                return nickname;
            }

            public String getAContent() {
                return aContent;
            }

            public String getAUserSign() {
                return aUserSign;
            }

            public String getACreateTime() {
                return aCreateTime;
            }

            public String getAId() {
                return aId;
            }

            public String getQId() {
                return qId;
            }
        }

        public static class AttentionEntity {
            /**
             * addTime : 2015-07-23 10:44:21
             * userSign : a4c1406ff4cc382389f19bf6ec3e55c1
             * relativeId : 3
             * attentionId : 196
             * relativeType : 13
             * status : 1
             */
            private String addTime;
            private String userSign;
            private String relativeId;
            private String attentionId;
            private String relativeType;
            private String status;

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public void setUserSign(String userSign) {
                this.userSign = userSign;
            }

            public void setRelativeId(String relativeId) {
                this.relativeId = relativeId;
            }

            public void setAttentionId(String attentionId) {
                this.attentionId = attentionId;
            }

            public void setRelativeType(String relativeType) {
                this.relativeType = relativeType;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getAddTime() {
                return addTime;
            }

            public String getUserSign() {
                return userSign;
            }

            public String getRelativeId() {
                return relativeId;
            }

            public String getAttentionId() {
                return attentionId;
            }

            public String getRelativeType() {
                return relativeType;
            }

            public String getStatus() {
                return status;
            }
        }
    }

}