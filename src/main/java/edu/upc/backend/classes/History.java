package edu.upc.backend.classes;

/**
 * @brief Score history records.
 */
public class History {
    public int getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    int userId;
    int score;

    public History() {}
    public  History(int userId, int score) { setUserId(userId); setScore(score);}

    @Override
    public String toString() {
        return String.format("User: %d, Score: %d", userId, score);
    }
}
