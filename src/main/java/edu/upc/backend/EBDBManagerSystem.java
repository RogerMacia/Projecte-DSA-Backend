package edu.upc.backend;

import edu.upc.backend.classes.*;
import edu.upc.backend.database.UserDao;
import edu.upc.backend.exceptions.IncorrectPasswordException;
import edu.upc.backend.exceptions.UserNotFoundException;
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
        UserDao _users = UserDao.getInstance();
        UsersList res = null;
        try {
            List<User> list = _users.getUsers();
            res = new UsersList(list);
        }
        catch (Exception e)
        {
            log.error("Error: " + e.getMessage() );
        }
        return  res;
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
        UserDao _users = UserDao.getInstance();
        User res = null;
        try{
            res = _users.getUserByUsername(username);
        }
        catch (Exception e)
        {
            log.error("Error: " + e.getMessage() );
        }
        return  res;
    }

    @Override
    public Player getPlayerById(int id) {
        return null;
    }

    @Override
    public User getUserById(int userId) {
        UserDao _users = UserDao.getInstance();
        User res = null;
        try{
            res = _users.getUser(userId);
        }
        catch (Exception e)
        {
            log.error("Error: " + e.getMessage() );
        }
        return  res;
    }

    @Override
    public Item getItemById(Integer id) {
        return null;
    }

    @Override
    public void logIn(String username, String password) {
        User u = getUserByUsername(username);
        if (u == null) {
            log.error("User " + username + " not found");
            throw new UserNotFoundException();
        }
        else {
            log.info("User found");
            if (password.equals(u.getPassword())) {
                log.info("User with correct credentials");
            }
            else {
                log.error("Incorrect password");
                throw new IncorrectPasswordException();
            }
        }

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
