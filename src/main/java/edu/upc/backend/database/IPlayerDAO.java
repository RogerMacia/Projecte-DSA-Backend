package edu.upc.backend.database;

import edu.upc.backend.classes.User;

import java.util.List;

public interface IPlayerDAO
{
    public int addPlayer(String name, String password, String username, String email) throws Exception;
    public User getPlayer(int userID) throws Exception;
    public void updatePlayer(int userID, String name, String password, String username, String email) throws Exception;
    public void deletePlayer(int userID) throws Exception;
    public List<User> getPlayers() throws Exception;

}
