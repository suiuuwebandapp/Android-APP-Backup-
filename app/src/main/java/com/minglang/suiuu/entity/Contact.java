package com.minglang.suiuu.entity;

/**
 * Created by Administrator on 2015/4/3.
 */
public class Contact {

    private String ContactTitle;

    private String ContactInfo;

    public String getContactInfo() {
        return ContactInfo;
    }

    public void setContactInfo(String contactInfo) {
        ContactInfo = contactInfo;
    }

    public String getContactTitle() {
        return ContactTitle;
    }

    public void setContactTitle(String contactTitle) {
        ContactTitle = contactTitle;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "ContactTitle='" + ContactTitle + '\'' +
                ", ContactInfo='" + ContactInfo + '\'' +
                '}';
    }
}
