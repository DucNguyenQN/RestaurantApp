package com.example.btlproject.models.Booking;

public class BookingRequest {
    private String Name;
    private String PhoneNumber;
    private String BookingDate;
    private int NumberOfGuests;
    private String SpecialRequest;

    public BookingRequest(String name, String phoneNumber, String bookingDate, int numberOfGuests, String specialRequest) {
        Name = name;
        PhoneNumber = phoneNumber;
        BookingDate = bookingDate;
        NumberOfGuests = numberOfGuests;
        SpecialRequest = specialRequest;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getBookingDate() {
        return BookingDate;
    }

    public void setBookingDate(String bookingDate) {
        BookingDate = bookingDate;
    }

    public int getNumberOfGuests() {
        return NumberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        NumberOfGuests = numberOfGuests;
    }

    public String getSpecialRequest() {
        return SpecialRequest;
    }

    public void setSpecialRequest(String specialRequest) {
        SpecialRequest = specialRequest;
    }
}
