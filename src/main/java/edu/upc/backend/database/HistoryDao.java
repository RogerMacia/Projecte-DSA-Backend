package edu.upc.backend.database;

import edu.upc.backend.classes.Game;
import edu.upc.backend.classes.History;
import edu.upc.backend.classes.User;
import edu.upc.backend.database.util.Utils;
import io.swagger.models.auth.In;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class HistoryDao implements IHistoryDao{

    static private HistoryDao _instance;
    static public HistoryDao getInstance()
    {
        if(_instance == null) _instance = new HistoryDao();
        return _instance;
    }

    @Override
    public void save(User user) throws SQLException {
        Session session = null;
        String query = "INSERT INTO History (userId, score) VALUES (?, ?)";
        HashMap<String,Integer> params = new HashMap<>();
        params.put("userId", user.getId());
        params.put("score",user.getScore());
        try
        {
            session = new SessionBuilder().build();
            session.query(query,null, params);
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
    public List<Integer> getAllPointsByUser(int userId) throws SQLException {
        Session session = null;
        String query = "SELECT * FROM History WHERE userId=?";
        HashMap<String,Integer> params = new HashMap<>();
        params.put("userUd",userId);
        List<Integer> res = new LinkedList<Integer>();

        try
        {
            session = new SessionBuilder().build();
            List<History> buffer = Utils.<History>castList(session.findAll(History.class, params));
            if(!buffer.isEmpty()) res = buffer.stream().map(x->x.getScore()).collect(Collectors.toList()); // me gusta mas linq de dot net.

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
}
