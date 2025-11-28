package edu.upc.backend.classes;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {
    int score;
    List<Item> items;

    public Player() {
        this.items = new ArrayList<>();
    }
    public Player(int playerId, int hp, double speed, float X, float Y, int score)
    {
        setPlayerId(playerId);
        setHp(hp);
        setSpeed(speed);
        setX(X);
        setY(Y);
        this.score = score;
        this.items = new ArrayList<>();
    }


    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }


    public int getPlayerId() {
        return id;
    }

    public void setPlayerId(int id) {
        this.id = id;
    }

    public List<Item>  getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    int id;
    int hp;
    double speed;
}
