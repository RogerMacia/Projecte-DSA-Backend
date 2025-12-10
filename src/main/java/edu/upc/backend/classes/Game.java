package edu.upc.backend.classes;

public class Game {



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }



    int id;
    int score;

    public Game(){}
    public Game(int id , int score)
    {
        setId(id);
        setScore(score);
    }
}
