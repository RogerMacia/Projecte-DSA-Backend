package edu.upc.backend.database;
import edu.upc.backend.classes.*;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class PlayerDAO implements IPlayerDAO {
    private static  PlayerDAO instance;

    public static PlayerDAO getInstance() {
        if (instance == null) {
            instance = new PlayerDAO();
        }
        return instance;
    }
    
    private PlayerDAO() {

    }

    private static final Logger logger = Logger.getLogger(PlayerDAO.class);

    public int addPlayer(Player player) throws SQLException {
        int id = -1;
        Session session = null;
        //User input = new User(username,name,email,password);
        try{
            session = new SessionBuilder().build();
            session.save(player);
            id = player.getPlayerId();
        }
        catch (Exception e) {
                logger.error(e.getMessage());
                e.printStackTrace();
        }
        finally {
            session.close();
        }

        return id;
    }

    public Player getPlayer(int userID) {
        Session session = null;
        Player player = null;
        try {
            session = new SessionBuilder().build();
            player = (Player) session.get(Player.class, userID);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        finally {
            if (session != null) {
                try {
                    session.close();
                } catch (SQLException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return player;
    }

    public void updatePlayer(Player player) {
        Session session = null;
        try {
            session = new SessionBuilder().build();
            session.update(player);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (SQLException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    public void deletePlayer(int userID) {
        Session session = null;
        Player player;
        try {
            session = new SessionBuilder().build();
            player = (Player) session.get(Player.class, userID);
            if (player != null) {
                session.delete(player);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (SQLException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Player> getPlayers() {
        Session session = null;
        List<Player> playerList = null;
        try {
            session = new SessionBuilder().build();
            playerList = (List<Player>) (List<?>) session.findAll(Player.class);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (SQLException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return playerList;
    }

}
