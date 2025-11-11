package edu.upc.backend.classes;

public abstract class Enemy extends Entity
{
    public Enemy() {}
    public Enemy(int hp, int x, int y) {
        setHp(hp);
        setX(x);
        setY(y);
    }
}
