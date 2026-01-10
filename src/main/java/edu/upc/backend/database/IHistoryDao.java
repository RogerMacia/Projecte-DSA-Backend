package edu.upc.backend.database;

import edu.upc.backend.classes.Game;
import edu.upc.backend.classes.User;

import java.sql.SQLException;
import java.util.List;

/**
 * @brief User's points history. These points are immutable, in other words, records when you finish a game.
 */
public interface IHistoryDao {
    public void save(User user) throws SQLException;
    public List<Integer> getAllPointsByUser(int userId) throws SQLException;
}
