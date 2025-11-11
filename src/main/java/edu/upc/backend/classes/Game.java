package edu.upc.backend.classes;

public class Game {
    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    String playerId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Item getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(Item currentItem) {
        this.currentItem = currentItem;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    String id;
    int score;
    Item currentItem;
    Level currentLevel;

    public Game(){}
    public Game(String id, String playerId, int score, Item currentItem, Level currentLevel)
    {
        setId(id);
        setScore(score);
        setCurrentItem(currentItem);
        setCurrentLevel(currentLevel);
        setPlayerId(playerId);
    }
}
