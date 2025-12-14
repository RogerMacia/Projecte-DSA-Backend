package edu.upc.backend.database;

import edu.upc.backend.classes.Game;
import edu.upc.backend.classes.Player;
import edu.upc.backend.database.util.ObjectHelper;
import edu.upc.backend.database.util.Utils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class GameDAO implements IGameDAO{

    static private GameDAO _instance;
    static public GameDAO getInstance()
    {
        if(_instance == null) _instance = new GameDAO();
        return _instance;
    }

    @Override
    public int add(int userId, int playerId) throws SQLException {
        Session session = null;
        Game game = new Game(0,1);
        int gameId = -1;
        try{
            session = new SessionBuilder().build();
            session.save(game);

            List<Object> games = session.findAll(Game.class);
            gameId = ((Game) games.get(games.size() - 1)).getId();

            HashMap<String,Object> params = new HashMap<>();
            params.put("userId",userId);
            params.put("playerId",playerId);
            params.put("gameId",gameId);
            session.query("INSERT INTO CORE (userId, playerId, gameId) VALUES (?, ?, ?)",null,params);


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return gameId;
    }

    @Override
    public Game getbyUserId(int userId) throws SQLException {
        Session session = null;
        Game res = null;
        String customQuery = "SELECT gameId FROM Core WHERE userId=?";
        try{
            session = new SessionBuilder().build();
            HashMap<String,Object> params = new HashMap<>();
            params.put("userId",userId);
            int gameId = session.findId("",params);
            res = (Game )session.get(Game.class, gameId);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return res;
    }

    @Override
    public List<Game> getAll() throws SQLException {
        Session session = null;
        List<Game> games = null;

        try{
            session = new SessionBuilder().build();
            games = Utils.<Game>castList(session.findAll(Game.class));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return games;
    }

    @Override
    public void update(Game game) throws SQLException {
        Session session = null;
        try{
            session = new SessionBuilder().build();
            session.update(game);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }

    @Override
    public void delete(Game game) throws SQLException {
        Session session = null;
        try{
            session = new SessionBuilder().build();
            session.delete(game);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }
}
