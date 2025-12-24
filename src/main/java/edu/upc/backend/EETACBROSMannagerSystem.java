package edu.upc.backend;

import edu.upc.backend.classes.*;
import edu.upc.backend.exceptions.InsufficientMoneyException;
import edu.upc.backend.exceptions.UserNotFoundException;
import edu.upc.backend.exceptions.UserOrPasswordInvalidException;
import edu.upc.backend.exceptions.UsernameAlreadyExistsException;

import java.sql.SQLException;
import java.util.List;

public interface EETACBROSMannagerSystem {

    public void registerUser(User user) throws SQLException, UsernameAlreadyExistsException;
    public List<User> getUsersListDatabase();
    public List<Item> getItemList();
    public User logIn (User user) throws SQLException, UserOrPasswordInvalidException;
    void clear();
    void registerPurchase(BuyRequest buyrequest) throws UserNotFoundException, InsufficientMoneyException, Exception;
    List<Item> getUserItems(int userId);
    User updateUserData(User user) throws Exception;

    void deleteUserData(int id) throws Exception;


    public void addPlayer(Player player);

    public PlayerList getPlayerList();
    public User getUserByUsername(String username);
    public Player getPlayerById(int id);
    public User getUserById(int userId);

    public Player getPlayerByUserId(int userId) throws Exception;
    public Game getGameByUserId(int userId) throws SQLException;
    public List<Faq> getAllFaqs() throws SQLException;
    public RankingResponse getRanking(int userId) throws UserNotFoundException;


}