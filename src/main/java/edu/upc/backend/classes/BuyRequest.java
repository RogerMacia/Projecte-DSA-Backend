package edu.upc.backend.classes;

import java.util.ArrayList;
import java.util.List;

public class BuyRequest {
    private int userId;
    private List<Item> items;

    public BuyRequest(){
        this.items = new ArrayList<>();
    }

    public BuyRequest(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getTotalItems() {
        int total = 0;
        for (Item item : this.items) {
            total += item.getQuantity();
        }
        return total;
    }

}

