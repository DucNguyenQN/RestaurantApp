package com.example.btlproject.models.Delivery;

public class UserDeliveryInfo {
    private String applicationUserId;
    private DeliveryInfo deliveryInfo;

    public UserDeliveryInfo(String applicationUserId, DeliveryInfo deliveryInfo) {
        this.applicationUserId = applicationUserId;
        this.deliveryInfo = deliveryInfo;
    }

    // Getters and setters
    public String getApplicationUserId() {
        return applicationUserId;
    }

    public void setApplicationUserId(String applicationUserId) {
        this.applicationUserId = applicationUserId;
    }

    public DeliveryInfo getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }
}
