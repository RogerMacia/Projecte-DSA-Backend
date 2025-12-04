package edu.upc.backend;

import edu.upc.backend.classes.*;
import edu.upc.backend.database.ItemDAO;
import edu.upc.backend.database.UserDAO;
import edu.upc.backend.database.UserItemDAO;
import edu.upc.backend.exceptions.IncorrectPasswordException;
import edu.upc.backend.exceptions.InsufficientMoneyException;
import edu.upc.backend.exceptions.UserNotFoundException;
import org.apache.log4j.Logger;

import java.sql.SQLException;
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
    public void registerUser(User user) throws SQLException {
        UserDAO _users = UserDAO.getInstance();

        User userExists = _users.getUserByUsername(user.getUsername());

        if (userExists != null) {
            log.error("User " + user.getUsername() + " already exists");
            throw new SQLException("User already exists");
        }
        else {
            try {
                _users.registerUser(user);
            }
            catch (Exception e) {
                log.error("Error: " + e.getMessage() );
            }
        }
    }

    public User logIn(User user) throws SQLException {

        UserDAO _users = UserDAO.getInstance();
        User userExists = _users.getUserByUsername(user.getUsername());
        String password = user.getPassword();

        if (userExists == null) {
            log.error("User " + user.getUsername() + " not found");
            throw new UserNotFoundException();
        }
        else {
            log.info("User found");
            if (password.equals(userExists.getPassword())) {
                log.info("User with correct credentials");
                return userExists;
            } else {
                log.error("Incorrect password");
                throw new IncorrectPasswordException();
            }
        }
    }

    public void registerPurchase(BuyRequest buyRequest) throws Exception {
        UserDAO userDAO = UserDAO.getInstance();

        User user = userDAO.getUserById(buyRequest.getUserId());
        if (user == null) {
            throw new UserNotFoundException();
        }

        int totalCost = 0;
        for (Item item : buyRequest.getItems()) {
            totalCost += item.getPrice() * item.getQuantity();
        }

        if (user.getCoins() < totalCost) {
            throw new InsufficientMoneyException();
        }

        user.setCoins((user.getCoins() - totalCost));
        userDAO.updateUser(user);

        for (Item item : buyRequest.getItems()) {
            UserItemDAO userItemDAO = UserItemDAO.getInstance();
            UserItem userItem = userItemDAO.getUserItem(user.getId(), item.getId());
            if (userItem != null) {
                userItem.setQuantity(userItem.getQuantity() + item.getQuantity());
                userItemDAO.updateUserItem(userItem);
            } else {
                userItem = new UserItem(user.getId(), item.getId(), item.getQuantity());
                userItemDAO.addUserItem(userItem);
            }
        }
    }

    @Override
    public void addPlayer(Player player) {

    }

    @Override
    public  List<User> getUsersListDatabase() {
        UserDAO _users = UserDAO.getInstance();
        List<User> list = null;
        try {
            list = _users.getUsers();
        }
        catch (Exception e) {
            log.error("Error: " + e.getMessage());
        }
        return list;
    }

    public List<Item> getItemList() {
        ItemDAO _itemsInstance = ItemDAO.getInstance();
        List<Item> items = null;
        try {
            items = _itemsInstance.getItemlist();
        }
        catch (Exception e)
        {
            log.error("Error: " + e.getMessage() );
        }
        return  items;
    }

    @Override
    public PlayerList getPlayerList() {
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        UserDAO _users = UserDAO.getInstance();
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
        UserDAO _users = UserDAO.getInstance();
        User res = null;
        try{
            res = _users.getUserById(userId);
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
