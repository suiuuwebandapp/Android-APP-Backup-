package com.minglang.suiuu.entity;


public class TripGallery {
    private String headUrl;
    private String locationDistance;
    private String loveNumber;
    private String tripGalleryName;
    private String tripGalleryTag;
    private String headPotrait;

    @Override
    public String toString() {
        return "TripGallery{" +
                "headUrl='" + headUrl + '\'' +
                ", locationDistance='" + locationDistance + '\'' +
                ", loveNumber='" + loveNumber + '\'' +
                ", tripGalleryName='" + tripGalleryName + '\'' +
                ", tripGalleryTag='" + tripGalleryTag + '\'' +
                ", headPotrait='" + headPotrait + '\'' +
                '}';
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getLocationDistance() {
        return locationDistance;
    }

    public void setLocationDistance(String locationDistance) {
        this.locationDistance = locationDistance;
    }

    public String getLoveNumber() {
        return loveNumber;
    }

    public void setLoveNumber(String loveNumber) {
        this.loveNumber = loveNumber;
    }

    public String getTripGalleryName() {
        return tripGalleryName;
    }

    public void setTripGalleryName(String tripGalleryName) {
        this.tripGalleryName = tripGalleryName;
    }

    public String getTripGalleryTag() {
        return tripGalleryTag;
    }

    public void setTripGalleryTag(String tripGalleryTag) {
        this.tripGalleryTag = tripGalleryTag;
    }

    public String getHeadPotrait() {
        return headPotrait;
    }

    public void setHeadPotrait(String headPotrait) {
        this.headPotrait = headPotrait;
    }
}
