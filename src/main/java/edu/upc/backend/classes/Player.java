package edu.upc.backend.classes;

public class Player extends Entity
{


    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }


    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    String playerId;
    int hp;
    double speed;


    public Player() {}
    public Player(String playerId, int hp, double speed, float X, float Y)
    {
        setPlayerId(playerId);
        setHp(hp);
        setSpeed(speed);
        setX(X);
        setY(Y);
    }
}
