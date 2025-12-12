package edu.upc.backend.database;

import edu.upc.backend.classes.Game;
import java.util.*;

public interface IGameDAO {
    public void add(Game game);
    public Game getbyUserId(int userId);
    public List<Game> getAll();
    public void update();
    public void delete();
}
