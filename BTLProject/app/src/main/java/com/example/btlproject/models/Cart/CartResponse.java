package com.example.btlproject.models.Cart;

import java.util.List;

public class CartResponse {
    private int statusCode;
    private boolean isSuccess;
    private List<String> errorMessages;
    private Result result;

    public static class Result {
        private int id;
        private String applicationUserId;
        private String stripePaymentIntentId;
        private String clientSecret;
        private List<CartItem> cartItems;
        private double cartTotal;
        private int itemsTotal;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getApplicationUserId() {
            return applicationUserId;
        }

        public void setApplicationUserId(String applicationUserId) {
            this.applicationUserId = applicationUserId;
        }

        public String getStripePaymentIntentId() {
            return stripePaymentIntentId;
        }

        public void setStripePaymentIntentId(String stripePaymentIntentId) {
            this.stripePaymentIntentId = stripePaymentIntentId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        public List<CartItem> getCartItems() {
            return cartItems;
        }

        public void setCartItems(List<CartItem> cartItems) {
            this.cartItems = cartItems;
        }

        public double getCartTotal() {
            return cartTotal;
        }

        public void setCartTotal(double cartTotal) {
            this.cartTotal = cartTotal;
        }

        public int getItemsTotal() {
            return itemsTotal;
        }

        public void setItemsTotal(int itemsTotal) {
            this.itemsTotal = itemsTotal;
        }
    }

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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

}
