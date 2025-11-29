package edu.upc.backend;

import edu.upc.backend.classes.*;
import edu.upc.backend.database.*;
import edu.upc.backend.database.util.QueryHelper;
import edu.upc.backend.exceptions.*;
import org.apache.log4j.Logger;

import javax.naming.NameNotFoundException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public class EETACBROSMannagerSystemImpl implements EETACBROSMannagerSystem {
    final static Logger logger = Logger.getLogger(EETACBROSMannagerSystemImpl.class);

    private static EETACBROSMannagerSystemImpl instance;
    private static GameList _games;
    private final Session<Object> session;
    private UserList connectedUsers;

    private EETACBROSMannagerSystemImpl() {
        _games = new GameList();
        this.session = new SessionBuilder().build();
        this.connectedUsers = new UserList();
        logger.info("Constructor EETACbROSManagerSystemImpl inicialitzat");
    }

    public static EETACBROSMannagerSystemImpl getInstance() {
        logger.info("Inici getInstance()");
        if (instance == null) {
            instance = new EETACBROSMannagerSystemImpl();
            logger.warn("Instancia creada");
        }
        logger.info("Fi getInstance() -> " + instance);
        return instance;
    }
    
    public User logIn(String username, String password) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", username);
        //List<User> users = session.findAll(User.class, params).stream().map(obj -> (User) obj).collect(Collectors.toList());
        // List<User> users = session.findAll(User.class).stream().map(obj -> (User) obj).collect(Collectors.toList());

        User user = (User) session.findObject(User.class, params);
        if (user == null) {
            logger.error("User " + username + " not found");
            throw new UserNotFoundException();
        }
        
        if (password.equals(user.getPassword())) {
            logger.info("User with correct credentials");
            this.connectedUsers.addUser(user);
        }
        else {
            logger.error("Incorrect password");
            throw new IncorrectPasswordException();
        }
        return user;
    }

    public User registerUser(User user) {
        String username = user.getUsername();
        String name = user.getName();
        String password = user.getPassword();
        String email = user.getEmail();

        logger.info("Registering user: " + username);
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", username);
        List<User> existingUsers = session.findAll(User.class, params).stream().map(obj -> (User) obj).collect(Collectors.toList());

        if (!existingUsers.isEmpty()) {
            logger.error("User with username " + username + " already exists.");
            throw new UserAlreadyExistsException();
        }

        User newUser = new User(username, name, password, email);
        session.save(newUser);
        logger.info("User " + username + " registered successfully.");
        return newUser;
    }

    public List<Object> findAll(Class theClass){
        return session.findAll(theClass);
    }

    public void logOut(int userId) {
        User user = this.connectedUsers.getUserById(userId);
        if (user != null) {
            this.connectedUsers.getUserslist().remove(user);
            logger.info("User with id " + userId + " logged out.");
        }
        else {
            logger.warn("User with id " + userId + " not found in connected users list.");
        }
    }

    public UserList getConnectedUsers() {
        return this.connectedUsers;
    }

    @Override
    public void clear() {
        logger.info("Inici clear()");
        this.connectedUsers = new UserList();
        logger.info("Fi clear() -> Llista de usuaris buidada");
    }

    @Override
    public void save(Object entity) {
        session.save(entity);
    }

    @Override
    public void close() {
        try {
            session.close();
        }
        catch (SQLException e) {
            logger.error("Error closing session: " + e.getMessage());
        }
    }

    @Override
    public Object get(Class<?> theClass, Object ID) {
        try {
            return session.get(theClass, (Integer) ID);
        }
        catch (NameNotFoundException e) {
            logger.error("Error getting object: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void update(Object object) {
        try {
            session.update(object);
        }
        catch (NameNotFoundException e) {
            logger.error("Error updating object: " + e.getMessage());
        }
    }

    @Override
    public void delete(Object object) {
        try {
            session.delete(object);
        }
        catch (NameNotFoundException e) {
            logger.error("Error deleting object: " + e.getMessage());
        }
    }

    /*public void managePurchase(BuyRequest request) {
        int userId = request.getUserId();
        try {
            User user = (User) session.get(User.class, userId);

            if (user == null) {
                logger.error("User with ID " + userId + " not found for purchase.");
                // throw new UserNotFoundException("User with ID " + userId + " not found.");
            }

            int totalCost = 0;
            // Map to store canonical items to avoid re-fetching for each instance
            HashMap<Integer, Item> canonicalItemsInPurchase = new HashMap<>();

            for (Item itemInRequest : request.getItems()) { // 'itemInRequest' is an Item from the BuyRequest
                Item canonicalItem = (Item) session.get(Item.class, itemInRequest.getId()); // Fetch the actual Item from DB
                if (canonicalItem == null) {
                    logger.error("Item with ID " + itemInRequest.getId() + " not found for purchase.");
                    // throw new ProductNotFoundException("Item with ID " + itemInRequest.getId() + " not found."); // Reusing ProductNotFoundException for item
                }
                // Store the canonical item if not already stored
                if (!canonicalItemsInPurchase.containsKey(itemInRequest.getId())) {
                    canonicalItemsInPurchase.put(itemInRequest.getId(), canonicalItem);
                }
                totalCost += canonicalItem.getPrice(); // Each item in request.getItems() represents one unit
            }

            if (user.getCoins() >= totalCost) { // Use getCoins()
                user.setCoins(user.getCoins() - totalCost); // Use setCoins()

                // Ensure user's item list is initialized
                if (user.getItems() == null) {
                    user.setItems(new ArrayList<>());
                }
                // The update method in this class already handles NameNotFoundException,
                // so no need for a try-catch block here.
                session.update(user);
                logger.info("Purchase successful for user " + user.getUsername() + ". Total cost: " + totalCost);
            } else {
                logger.warn("Purchase failed for user " + user.getUsername() + ": not enough money. Required: " + totalCost + ", Available: " + user.getCoins());
                // throw new NotEnoughMoneyException();
            }
        }
        catch (Exception ex) {
            logger.error("Purchase failed: " + ex.getMessage());
        }
    }

    public void deleteItemFromUser(int userId, List<Item> itemsToDelete) {
        try {
            User user = (User) session.get(User.class, userId);
            if (user == null) {
                logger.error("User with ID " + userId + " not found for deleting items.");
                //throw new UserNotFoundException("User with ID " + userId + " not found.");
            }

            if (user.getItems() == null || user.getItems().isEmpty()) {
                logger.warn("User " + user.getUsername() + " does not have any items to delete.");
                return;
            }

            List<Item> userItems = user.getItems();
            int itemsRemovedCount = 0;

            for (Item itemToDeleteRequest : itemsToDelete) {
                boolean removed = false;
                // Iterate through the user's actual items to find and remove one instance
                for (int i = 0; i < userItems.size(); i++) {
                    if (userItems.get(i).getId() == itemToDeleteRequest.getId()) {
                        userItems.remove(i);
                        itemsRemovedCount++;
                        removed = true;
                        logger.info("Removed one instance of item with ID " + itemToDeleteRequest.getId() + " from user " + user.getUsername() + "'s inventory.");
                        break; // Remove only one instance per request item
                    }
                }
                if (!removed) {
                    logger.warn("User " + user.getUsername() + " does not possess item with ID " + itemToDeleteRequest.getId() + " (or not enough instances).");
                }
            }

            if (itemsRemovedCount > 0) {
                session.update(user);
                logger.info(itemsRemovedCount + " items successfully deleted from user " + user.getUsername() + "'s inventory.");
            } else {
                logger.info("No items were deleted from user " + user.getUsername() + "'s inventory.");
            }
        }
    catch (Exception ex) {
           logger.info(" Failed to remove items");
        }
    }*/

    /*
    //region games
    @Override
    public void createGame(int playerId)
    {
        _games.create(playerId);
        logger.info(String.format("A new game for player with %d has been created", playerId));
    }

    public Game findGame(int playerId)
    {
        Game res = _games.find(playerId);
        if(res == null) logger.warn(String.format("Player with id %d has already a game.",playerId));
        return res;
    }

    public void removeGame(int playerId)
    {
        _games.remove(playerId);
    }

    public void updateGame(Game game)
    {
        Game l_game = _games.find(game.getPlayerId());
        if(l_game == null)
        {
            logger.warn("Game not found.");
            return;
        }
        l_game.setScore(game.getScore());
    }

     */

}