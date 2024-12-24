package com.example.btlproject.models.Cart;

public class CartRequest {
    private String userId;
    private int menuItemId;
    private int updateQuantity;

    public CartRequest(String userId, int menuItemId, int updateQuantity) {
        this.userId = userId;
        this.menuItemId = menuItemId;
        this.updateQuantity = updateQuantity;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public int getQuantity() {
        return updateQuantity;
    }

    public void setQuantity(int quantity) {
        this.updateQuantity = quantity;
    }
}
