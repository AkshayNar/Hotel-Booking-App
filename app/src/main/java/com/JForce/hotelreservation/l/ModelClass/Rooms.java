package com.JForce.hotelreservation.l.ModelClass;

public class Rooms {

    private String RTitle, RImage, RCapacity, ROriginalPrice, RDiscountPrice,RDescription, Rid, Roffer, Status, LastUpdated, RType;

    public Rooms() {
    }


    public Rooms(String RTitle, String RImage, String RCapacity, String ROriginalPrice, String RDiscountPrice, String RDescription, String rid, String roffer, String status, String lastUpdated, String RType) {
        this.RTitle = RTitle;
        this.RImage = RImage;
        this.RCapacity = RCapacity;
        this.ROriginalPrice = ROriginalPrice;
        this.RDiscountPrice = RDiscountPrice;
        this.RDescription = RDescription;
        Rid = rid;
        Roffer = roffer;
        Status = status;
        LastUpdated = lastUpdated;
        this.RType = RType;
    }


    public String getRTitle() {
        return RTitle;
    }

    public void setRTitle(String RTitle) {
        this.RTitle = RTitle;
    }

    public String getRImage() {
        return RImage;
    }

    public void setRImage(String RImage) {
        this.RImage = RImage;
    }

    public String getRCapacity() {
        return RCapacity;
    }

    public void setRCapacity(String RCapacity) {
        this.RCapacity = RCapacity;
    }

    public String getROriginalPrice() {
        return ROriginalPrice;
    }

    public void setROriginalPrice(String ROriginalPrice) {
        this.ROriginalPrice = ROriginalPrice;
    }

    public String getRDiscountPrice() {
        return RDiscountPrice;
    }

    public void setRDiscountPrice(String RDiscountPrice) {
        this.RDiscountPrice = RDiscountPrice;
    }

    public String getRDescription() {
        return RDescription;
    }

    public void setRDescription(String RDescription) {
        this.RDescription = RDescription;
    }

    public String getRid() {
        return Rid;
    }

    public void setRid(String rid) {
        Rid = rid;
    }

    public String getRoffer() {
        return Roffer;
    }

    public void setRoffer(String roffer) {
        Roffer = roffer;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getLastUpdated() {
        return LastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        LastUpdated = lastUpdated;
    }

    public String getRType() {
        return RType;
    }

    public void setRType(String RType) {
        this.RType = RType;
    }
}
