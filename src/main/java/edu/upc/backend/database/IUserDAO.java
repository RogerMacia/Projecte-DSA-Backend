package edu.upc.backend.database;

import edu.upc.backend.classes.Player;
import edu.upc.backend.classes.User;

import java.util.List;

public interface IUserDAO {

//public int addUser(String name, String password, String username, String email) throws Exception;
public User getUserById(int userID) throws Exception;
public void updateUser(User user) throws Exception;
public void deleteUser(int id) throws Exception;
public List<User> getUsers() throws Exception;
    public List<User> getTopUsers() throws Exception;
}
