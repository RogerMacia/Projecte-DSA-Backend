package edu.upc.backend.database;

import edu.upc.backend.classes.Player;
import edu.upc.backend.classes.User;
import edu.upc.backend.database.*;
import edu.upc.backend.database.util.ObjectHelper;
import edu.upc.backend.database.util.Utils;
import org.apache.log4j.Logger;

import javax.print.attribute.standard.Finishings;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerDAO implements IPlayerDAO{

    private static final Logger log = Logger.getLogger(PlayerDAO.class);
    private static PlayerDAO _instance;
    public static PlayerDAO getInstance()
    {
        if(_instance == null) _instance = new PlayerDAO();
        return _instance;
    }
    private PlayerDAO() {}


    @Override
    public int addPlayer(int userId) throws Exception {
        log.info("\u001B[32mAccessing Database: addPlayer\u001B[0m");
        Player player = new Player(0,100,10,0,0);
        int playerId = -1;
        Session session = null;
        try{
            session = new SessionBuilder().build();
            session.save(player);

            /*
            List<Object> players = session.findAll(Player.class);
            playerId = ((Player) players.get(players.size() - 1)).getId();
            */
            playerId = ((Player)session.findLast(Player.class)).getId();

        }
        catch (Exception e)
        {
            log.error(e.getMessage());
        }
        finally {
            session.close();
        }
        return playerId;
    }

    @Override
    public Player getPlayer(int playerId) throws Exception {
        log.info("\u001B[32mAccessing Database: getPlayer\u001B[0m");
        Player player = null;
        Session session;
        //String customQuery = "SELECT playerId FROM Core WHERE userId=?";
        try{
            session = new SessionBuilder().build();
            player = (Player) session.get(Player.class,playerId);

        }
        catch (Exception e)
        {
            log.error(e.getMessage());
        }
        return player;
    }

    @Override
    public Player getPlayerbyUserId(int userId) throws Exception {
        log.info("\u001B[32mAccessing Database: getPlayerbyUserId\u001B[0m");
        Session session = null;
        String customQuery = "SELECT playerId FROM Core WHERE userId=?";
        Player res = null;
        try{
            session = new SessionBuilder().build();
            HashMap<String,Object> params = new HashMap<>();
            params.put("userId",userId);
            int playerId = session.findId(customQuery,params);
            res = (Player) session.get(Player.class,playerId);
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
        finally {
            session.close();
        }

        return res;
    }

    @Override
    public void updatePlayer(Player player) throws Exception {
        log.info("\u001B[32mAccessing Database: updatePlayer\u001B[0m");
        Session session = null;

        try{
            session = new SessionBuilder().build();
            session.update(player);
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
        finally {
            session.close();
        }

    }

    @Override
    public void deletePlayer(Player player) throws Exception {
        log.info("\u001B[32mAccessing Database: deletePlayer\u001B[0m");
        Session session = null;
        int playerId = player.getId();
        String customQuery = "DELETE FROM Core WHERE playerId=?";
        try{
            session = new SessionBuilder().build();
            HashMap<String,Object> params = new HashMap<>();
            params.put("playerId",playerId);
            session.query(customQuery,null,params);
            session.delete(player);
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
        finally {
            session.close();
        }
    }

    @Override
    public List<Player> getPlayers() throws Exception {
        log.info("\u001B[32mAccessing Database: getPlayers\u001B[0m");
        List<Object> res = null;
        Session session = null;
        try {
            session = new SessionBuilder().build();
            res = session.findAll(Player.class);
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
        }
        finally {
            session.close();
        }

        return res.stream().map(object -> (Player) object).collect(Collectors.toList());
    }


}
