package edu.upc.backend.classes;

import java.util.ArrayList;
import java.util.List;

public class Player {



    public Player() {}
    public Player(int playerId, int hp, Integer speed, float X, float Y)
    {
        setId(playerId);
        setHp(hp);
        setSpeed(speed);
        setX(X);
        setY(Y);
    }


    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }


    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public float getX() {
        return X;
    }

    public void setX(Float x) {
        X = x;
    }

    public float getY() {
        return Y;
    }

    public void setY(Float y) {
        Y = y;
    }



    int id;
    int hp;
    int speed;
    float X, Y;


    @Override
    public String toString() {
        return String.format(
                "Player: id=%d; hp=%d; speed=%d; X=%f; Y=%f",
                id,
                hp,
                speed,
                X,
                Y
        );
    }
}
