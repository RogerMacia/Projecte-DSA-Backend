package edu.upc.backend.classes;

public class Game {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    private int level;

    public Game() {}
    public Game(int id, int level)
    {
        setId(id);
        setLevel(level);
    }

}
