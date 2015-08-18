package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/8/18.
 * 邀请回答的用户数据实体类
 */
public class InvitationAnswer {

    /**
     * data : {"sysUser":{"birthday":"1991-02-14","travelCount":"0","lon":"","cityId":"342","countryId":"336","lastLoginIp":null,"password":null,"balance":null,"school":"","intro":"一座城市，一些岁月，和一个人。","nickname":"dorlen","email":null,"lat":"","info":"来自西南的小姑娘","profession":"业余导游","registerIp":null,"headImg":"http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg","registerTime":null,"sex":"0","userId":null,"version":null,"isPublisher":"1","lastLoginTime":null,"areaCode":null,"phone":null,"userSign":"a4c1406ff4cc382389f19bf6ec3e55c1","hobby":"","status":null},"inviteUser":[{"userSign":"a4c1406ff4cc382389f19bf6ec3e55c1","nickname":"dorlen"},{"userSign":"085963dc0af031709b032725e3ef18f5","nickname":"✨yao"}]}
     * message :
     * status : 1
     * token : 8e13fee85d46b3f2aa4949d18839cb4d
     */
    private InvitationAnswerData data;
    private String message;
    private int status;
    private String token;

    public void setData(InvitationAnswerData data) {
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

    public InvitationAnswerData getData() {
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

    public static class InvitationAnswerData {
        /**
         * sysUser : {"birthday":"1991-02-14","travelCount":"0","lon":"","cityId":"342","countryId":"336","lastLoginIp":null,"password":null,"balance":null,"school":"","intro":"一座城市，一些岁月，和一个人。","nickname":"dorlen","email":null,"lat":"","info":"来自西南的小姑娘","profession":"业余导游","registerIp":null,"headImg":"http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg","registerTime":null,"sex":"0","userId":null,"version":null,"isPublisher":"1","lastLoginTime":null,"areaCode":null,"phone":null,"userSign":"a4c1406ff4cc382389f19bf6ec3e55c1","hobby":"","status":null}
         * inviteUser : [{"userSign":"a4c1406ff4cc382389f19bf6ec3e55c1","nickname":"dorlen"},{"userSign":"085963dc0af031709b032725e3ef18f5","nickname":"✨yao"}]
         */
        private SysUserEntity sysUser;
        private List<InviteUserEntity> inviteUser;

        public void setSysUser(SysUserEntity sysUser) {
            this.sysUser = sysUser;
        }

        public void setInviteUser(List<InviteUserEntity> inviteUser) {
            this.inviteUser = inviteUser;
        }

        public SysUserEntity getSysUser() {
            return sysUser;
        }

        public List<InviteUserEntity> getInviteUser() {
            return inviteUser;
        }

        public static class SysUserEntity {
            /**
             * birthday : 1991-02-14
             * travelCount : 0
             * lon :
             * cityId : 342
             * countryId : 336
             * lastLoginIp : null
             * password : null
             * balance : null
             * school :
             * intro : 一座城市，一些岁月，和一个人。
             * nickname : dorlen
             * email : null
             * lat :
             * info : 来自西南的小姑娘
             * profession : 业余导游
             * registerIp : null
             * headImg : http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg
             * registerTime : null
             * sex : 0
             * userId : null
             * version : null
             * isPublisher : 1
             * lastLoginTime : null
             * areaCode : null
             * phone : null
             * userSign : a4c1406ff4cc382389f19bf6ec3e55c1
             * hobby :
             * status : null
             */
            private String birthday;
            private String travelCount;
            private String lon;
            private String cityId;
            private String countryId;
            private String lastLoginIp;
            private String password;
            private String balance;
            private String school;
            private String intro;
            private String nickname;
            private String email;
            private String lat;
            private String info;
            private String profession;
            private String registerIp;
            private String headImg;
            private String registerTime;
            private String sex;
            private String userId;
            private String version;
            private String isPublisher;
            private String lastLoginTime;
            private String areaCode;
            private String phone;
            private String userSign;
            private String hobby;
            private String status;

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public void setTravelCount(String travelCount) {
                this.travelCount = travelCount;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public void setCityId(String cityId) {
                this.cityId = cityId;
            }

            public void setCountryId(String countryId) {
                this.countryId = countryId;
            }

            public void setLastLoginIp(String lastLoginIp) {
                this.lastLoginIp = lastLoginIp;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public void setSchool(String school) {
                this.school = school;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public void setProfession(String profession) {
                this.profession = profession;
            }

            public void setRegisterIp(String registerIp) {
                this.registerIp = registerIp;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public void setRegisterTime(String registerTime) {
                this.registerTime = registerTime;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public void setIsPublisher(String isPublisher) {
                this.isPublisher = isPublisher;
            }

            public void setLastLoginTime(String lastLoginTime) {
                this.lastLoginTime = lastLoginTime;
            }

            public void setAreaCode(String areaCode) {
                this.areaCode = areaCode;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public void setUserSign(String userSign) {
                this.userSign = userSign;
            }

            public void setHobby(String hobby) {
                this.hobby = hobby;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getBirthday() {
                return birthday;
            }

            public String getTravelCount() {
                return travelCount;
            }

            public String getLon() {
                return lon;
            }

            public String getCityId() {
                return cityId;
            }

            public String getCountryId() {
                return countryId;
            }

            public String getLastLoginIp() {
                return lastLoginIp;
            }

            public String getPassword() {
                return password;
            }

            public String getBalance() {
                return balance;
            }

            public String getSchool() {
                return school;
            }

            public String getIntro() {
                return intro;
            }

            public String getNickname() {
                return nickname;
            }

            public String getEmail() {
                return email;
            }

            public String getLat() {
                return lat;
            }

            public String getInfo() {
                return info;
            }

            public String getProfession() {
                return profession;
            }

            public String getRegisterIp() {
                return registerIp;
            }

            public String getHeadImg() {
                return headImg;
            }

            public String getRegisterTime() {
                return registerTime;
            }

            public String getSex() {
                return sex;
            }

            public String getUserId() {
                return userId;
            }

            public String getVersion() {
                return version;
            }

            public String getIsPublisher() {
                return isPublisher;
            }

            public String getLastLoginTime() {
                return lastLoginTime;
            }

            public String getAreaCode() {
                return areaCode;
            }

            public String getPhone() {
                return phone;
            }

            public String getUserSign() {
                return userSign;
            }

            public String getHobby() {
                return hobby;
            }

            public String getStatus() {
                return status;
            }
        }

        public static class InviteUserEntity {
            /**
             * userSign : a4c1406ff4cc382389f19bf6ec3e55c1
             * nickname : dorlen
             * headImg  :
             */
            private String userSign;
            private String nickname;
            private String headImg;

            public void setUserSign(String userSign) {
                this.userSign = userSign;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public String getUserSign() {
                return userSign;
            }

            public String getNickname() {
                return nickname;
            }

            public String getHeadImg() {
                return headImg;
            }
        }

    }

}