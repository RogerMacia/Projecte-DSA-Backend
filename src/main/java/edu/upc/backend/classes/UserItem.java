package edu.upc.backend.classes;

public class UserItem {
    private int userId;
    private int itemId;
    private int quantity;

    public UserItem() {
    }

    public UserItem(int userId, int itemId, int quantity) {
        this.userId = userId;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    // Getters
    public int getUserId() {
        return userId;
    }

    public int getItemId() {
        return itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    // Setters
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Optional: Utility methods
    @Override
    public String toString() {
        return "UserItem{" +
                "userId=" + userId +
                ", itemId=" + itemId +
                ", quantity=" + quantity +
                '}';
    }
}