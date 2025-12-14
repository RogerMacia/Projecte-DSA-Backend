package edu.upc.backend.database;

import edu.upc.backend.classes.Game;

import java.sql.SQLException;
import java.util.*;

public interface IGameDAO {
    public int add(int userId, int playerId) throws SQLException;
    public Game getbyUserId(int userId) throws SQLException;
    public List<Game> getAll() throws SQLException;
    public void update(Game game) throws SQLException;
    public void delete(Game game) throws SQLException;
}
