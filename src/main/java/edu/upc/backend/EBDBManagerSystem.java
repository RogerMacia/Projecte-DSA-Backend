package edu.upc.backend;

import edu.upc.backend.classes.*;
import edu.upc.backend.database.UserDao;
import org.apache.log4j.Logger;

import java.util.List;

// Eetac bros Database manager
public class EBDBManagerSystem implements EETACBROSMannagerSystem {

    private static final Logger log = Logger.getLogger(EBDBManagerSystem.class);
    private static EBDBManagerSystem _instance;

    private EBDBManagerSystem() {}

    public static EBDBManagerSystem getInstance()
    {
        if(_instance == null) _instance = new EBDBManagerSystem();
        return _instance;
    }

    @Override
    public void addUser(User user) {
        UserDao _users = UserDao.getInstance();
        try{
            _users.addUser(user);
        }
        catch (Exception e)
        {
            log.error("Error: " + e.getMessage() );
        }

    }

    @Override
    public void addPlayer(Player player) {

    }

    @Override
    public UsersList getUsersList() {
        return null;
    }

    @Override
    public List<Item> getItemList() {
        return null;
    }

    @Override
    public PlayerList getPlayerList() {
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    @Override
    public Player getPlayerById(int id) {
        return null;
    }

    @Override
    public User getUserById(int userId) {
        return null;
    }

    @Override
    public Item getItemById(Integer id) {
        return null;
    }

    @Override
    public void logIn(String username, String password) {


    }

    @Override
    public void createGame(int playerId) {

    }

    @Override
    public Game findGame(int playerId) {
        return null;
    }

    @Override
    public void updateGame(Game game) {

    }

    @Override
    public void removeGame(int playerId) {

    }

    @Override
    public void clear() {

    }
}
