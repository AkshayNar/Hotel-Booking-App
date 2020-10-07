package com.JForce.hotelreservation.l.ModelClass;

public class Bookings {

    private String RTitle, nameOfGuest, GuestPhoneNumber, CheckInDate, BookingDate, RDiscountPrice, ROriginalPrice, RImage, NumberOfGuests, RType;

    public Bookings() {
    }

    public Bookings(String RTitle, String nameOfGuest, String guestPhoneNumber, String checkInDate, String bookingDate, String RDiscountPrice, String ROriginalPrice, String RImage, String numberOfGuests, String RType) {
        this.RTitle = RTitle;
        this.nameOfGuest = nameOfGuest;
        GuestPhoneNumber = guestPhoneNumber;
        CheckInDate = checkInDate;
        BookingDate = bookingDate;
        this.RDiscountPrice = RDiscountPrice;
        this.ROriginalPrice = ROriginalPrice;
        this.RImage = RImage;
        NumberOfGuests = numberOfGuests;
        this.RType = RType;
    }


    public String getRTitle() {
        return RTitle;
    }

    public void setRTitle(String RTitle) {
        this.RTitle = RTitle;
    }

    public String getNameOfGuest() {
        return nameOfGuest;
    }

    public void setNameOfGuest(String nameOfGuest) {
        this.nameOfGuest = nameOfGuest;
    }

    public String getGuestPhoneNumber() {
        return GuestPhoneNumber;
    }

    public void setGuestPhoneNumber(String guestPhoneNumber) {
        GuestPhoneNumber = guestPhoneNumber;
    }

    public String getCheckInDate() {
        return CheckInDate;
    }

    public void setCheckInDate(String checkInDate) {
        CheckInDate = checkInDate;
    }

    public String getBookingDate() {
        return BookingDate;
    }

    public void setBookingDate(String bookingDate) {
        BookingDate = bookingDate;
    }

    public String getRDiscountPrice() {
        return RDiscountPrice;
    }

    public void setRDiscountPrice(String RDiscountPrice) {
        this.RDiscountPrice = RDiscountPrice;
    }

    public String getROriginalPrice() {
        return ROriginalPrice;
    }

    public void setROriginalPrice(String ROriginalPrice) {
        this.ROriginalPrice = ROriginalPrice;
    }

    public String getRImage() {
        return RImage;
    }

    public void setRImage(String RImage) {
        this.RImage = RImage;
    }

    public String getNumberOfGuests() {
        return NumberOfGuests;
    }

    public void setNumberOfGuests(String numberOfGuests) {
        NumberOfGuests = numberOfGuests;
    }

    public String getRType() {
        return RType;
    }

    public void setRType(String RType) {
        this.RType = RType;
    }
}
