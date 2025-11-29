package edu.upc.backend;

import edu.upc.backend.classes.*;

import java.util.HashMap;
import java.util.List;

public interface EETACBROSMannagerSystem {

    public User logIn (String username, String password);
    public void logOut(int userId);
    public UserList getConnectedUsers();

    // public void createGame(int playerId);

    // public Game findGame(int playerId);
    // public void updateGame(Game game);
    // public void removeGame(int playerId);

    void clear();

    // DAO Methods
    void save(Object entity);
    void close();
    Object get(Class<?> theClass, Object ID);
    void update(Object object);
    void delete(Object object);
    User registerUser(User user);

    // void managePurchase(BuyRequest request);

    List<Item> findAll(Class<Item> itemClass);
}
