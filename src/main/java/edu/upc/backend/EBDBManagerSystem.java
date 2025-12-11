package edu.upc.backend;

import edu.upc.backend.classes.*;
import edu.upc.backend.database.*;
import edu.upc.backend.exceptions.*;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
    public void registerUser(User user) throws SQLException, UsernameAlreadyExistsException {
        UserDAO _users = UserDAO.getInstance();

        User userExists = _users.getUserByUsername(user.getUsername());

        if (userExists != null) {
            log.error("User " + user.getUsername() + " already exists");
            throw new UsernameAlreadyExistsException("Username is not available");
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

    @Override
    public User logIn(User user) throws UserOrPasswordInvalidException {

        UserDAO _users = UserDAO.getInstance();
        User userExists = null;
        try {
            userExists = _users.getUserByUsername(user.getUsername());
        } catch (SQLException e) {
            log.error("Database error during login: " + e.getMessage());
            // Re-throw as a generic runtime exception or a custom LoginFailedException if needed
            throw new RuntimeException("Login failed due to a database error.", e);
        }
        String password = user.getPassword();

        if (userExists == null) {
            log.error("User " + user.getUsername() + " not found");
            throw new UserOrPasswordInvalidException("Invalid username or password.");
        }
        else {
            log.info("User found");
            if (password.equals(userExists.getPassword())) {
                log.info("User with correct credentials");
                return userExists;
            } else {
                log.error("Incorrect password");
                throw new UserOrPasswordInvalidException("Invalid username or password.");
            }
        }
    }

    @Override
    public void registerPurchase(BuyRequest buyRequest) throws UserNotFoundException, InsufficientMoneyException, Exception {
        UserDAO userDAO = UserDAO.getInstance();

        User user = userDAO.getUserById(buyRequest.getUserId());
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        int totalCost = 0;
        for (Item item : buyRequest.getItems()) {
            totalCost += item.getPrice() * item.getQuantity();
        }

        if (user.getCoins() < totalCost) {
            throw new InsufficientMoneyException("Insufficient money to complete the purchase.");
        }

        user.setCoins((user.getCoins() - totalCost));
        userDAO.updateUser(user);

        for (Item item : buyRequest.getItems()) {
            UserItemDAO userItemDAO = UserItemDAO.getInstance();
            userItemDAO.addUserItem(new UserItem(user.getId(),item.getId(),item.getQuantity()));
        }


    }

    public User updateUserData(User user) throws Exception {
        UserDAO _users = UserDAO.getInstance();
        User userExists = null;

        userExists = _users.getUserById(user.getId());
        if (userExists == null){
            throw new UserNotFoundException("User not found");
        }
        try {
            userExists.setCoins(userExists.getCoins());
            userExists.setName(user.getName());
            userExists.setEmail(user.getEmail());
            userExists.setPassword(user.getPassword());
            userExists.setUsername(user.getUsername());
            _users.updateUser(userExists);
        }
        catch (SQLException e) {
            log.error("Database error during updating: " + e.getMessage());
        }
        return userExists;
    }

    public void deleteUserData(int id) throws Exception {
        UserDAO _users = UserDAO.getInstance();
        User userExists = _users.getUserById(id);
        if (userExists == null){
            throw new UserNotFoundException("User not found");
        }
        try {
            _users.deleteUser(id);
        }
        catch (SQLException e) {
            log.error("Database error during deleting: " + e.getMessage());
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

    public List<Item> getUserItems(int userId){
        UserItemDAO _userItemInstance = UserItemDAO.getInstance();
        List<Item> items = null;
        HashMap<String,Object> paramsSearch = new HashMap<>();
        //paramsSearch.put("userId",userId);
        try {
            List<UserItem> userItems = _userItemInstance.getUserItems(userId);
            items = new ArrayList<>();
            for (UserItem userItem : userItems) {
                ItemDAO _itemsInstance = ItemDAO.getInstance();
                Item item = _itemsInstance.getItemById(userItem.getItemId());
                item.setQuantity(userItem.getQuantity());
                items.add(item);
            }
        }
        catch (Exception e)
        {
            log.error("Error: " + e.getMessage() );
        }
        return  items;
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
