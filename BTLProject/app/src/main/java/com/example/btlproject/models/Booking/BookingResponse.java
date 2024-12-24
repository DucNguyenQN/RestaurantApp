package com.example.btlproject.models.Booking;

import java.util.List;

public class BookingResponse {
    private int statusCode;
    private boolean isSuccess;
    private List<String> errorMessages;
    private BookingResult result;

    // Getter và Setter
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public BookingResult getResult() {
        return result;
    }

    public void setResult(BookingResult result) {
        this.result = result;
    }
    public class BookingResult {
        private int id;
        private String name;
        private String phoneNumber;
        private String bookingDate; // Hoặc sử dụng Date nếu cần
        private int numberOfGuests;
        private String specialRequest;
        private String dateCreated; // Hoặc sử dụng Date nếu cần
        private String bookingStatus;
        private String applicationUserId;

        // Getter và Setter
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getBookingDate() {
            return bookingDate;
        }

        public void setBookingDate(String bookingDate) {
            this.bookingDate = bookingDate;
        }

        public int getNumberOfGuests() {
            return numberOfGuests;
        }

        public void setNumberOfGuests(int numberOfGuests) {
            this.numberOfGuests = numberOfGuests;
        }

        public String getSpecialRequest() {
            return specialRequest;
        }

        public void setSpecialRequest(String specialRequest) {
            this.specialRequest = specialRequest;
        }

        public String getDateCreated() {
            return dateCreated;
        }

        public void setDateCreated(String dateCreated) {
            this.dateCreated = dateCreated;
        }

        public String getBookingStatus() {
            return bookingStatus;
        }

        public void setBookingStatus(String bookingStatus) {
            this.bookingStatus = bookingStatus;
        }

        public String getApplicationUserId() {
            return applicationUserId;
        }

        public void setApplicationUser_Id(String applicationUserId) {
            this.applicationUserId = applicationUserId;
        }
    }
}
