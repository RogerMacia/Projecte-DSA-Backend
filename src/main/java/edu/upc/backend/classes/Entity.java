package edu.upc.backend.classes;

import java.security.PublicKey;

public abstract class Entity {
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public float getX() {
        return X;
    }

    public void setX(float x) {
        X = x;
    }

    public float getY() {
        return Y;
    }

    public void setY(float y) {
        Y = y;
    }

    int hp;
    float X, Y;

    public Entity() {}
    public Entity(int hp, int x, int y)
    {
        setHp(hp);
        setX(x);
        setY(y);
    }

}
