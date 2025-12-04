package edu.upc.backend;

import edu.upc.backend.classes.*;

import java.sql.SQLException;
import java.util.List;

public interface EETACBROSMannagerSystem {


    //public void addUser(User user);
    public void registerUser(User user) throws SQLException;
    public void addPlayer(Player player);
    public List<User> getUsersListDatabase();
    public List<Item> getItemList();
    public PlayerList getPlayerList();
    public User getUserByUsername(String username);
    public Player getPlayerById(int id);
    public User getUserById(int userId);
    public Item getItemById(Integer id);
    // public void logIn (String username, String password);
    public User logIn (User user) throws SQLException;

    public void createGame(int playerId);

    public Game findGame(int playerId);
    public void updateGame(Game game);
    public void removeGame(int playerId);

    void clear();

    void registerPurchase(BuyRequest buyrequest) throws Exception;
}