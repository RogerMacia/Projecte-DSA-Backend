package edu.upc.backend.classes;

import java.util.ArrayList;
import java.util.List;

public class BuyRequest {
    private int playerId;
    private List<Item> items;

    public BuyRequest(){
        this.items = new ArrayList<>();
    }

    public BuyRequest(int playerId) {
        this.playerId = playerId;
    }

    public int getUserId() {
        return playerId;
    }

    public void setUserId(int playerId) {
        this.playerId = playerId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}

