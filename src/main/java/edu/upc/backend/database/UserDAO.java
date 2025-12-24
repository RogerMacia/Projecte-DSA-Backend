package edu.upc.backend.database;

import edu.upc.backend.classes.Game;
import edu.upc.backend.classes.User;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDAO implements IUserDAO{

    private static UserDAO _instance;
    public static UserDAO getInstance()
    {
        if(_instance == null) _instance = new UserDAO();
        return _instance;
    }

    private UserDAO() {

    }

    private static final Logger log = Logger.getLogger(UserDAO.class);

    /*
    @Override
    public int addUser(String name, String password, String username, String email) throws Exception {
        return registerUser(new User(username,name,email,password));
    }

     */

    public int registerUser(User user) throws SQLException {
        int id = -1;
        Session session = null;
        try{
            session = new SessionBuilder().build();
            session.save(user);
            /*
            List<Object> users = session.findAll(Game.class);
            id = ((User) users.get(users.size() - 1)).getId();
            */
            id = ((User)session.findLast(User.class)).getId();
        }
        catch (Exception e) // Catch other exceptions
        {
            log.error("Unexpected error registering user in DAO: " + e.getMessage());
            throw new SQLException("Unexpected error during user registration", e); // Wrap and rethrow
        }
        finally {
            if (session != null) {
                session.close();
            }
        }

        return id;
    }


    public User getUserById(int userID) throws Exception {
        User res = null;
        Session session = null;

        try{
            session = new SessionBuilder().build();
            res = (User)session.get(User.class,userID);

        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            throw e; // Rethrow the exception
        }
        finally {
            if (session != null) {
                session.close();
            }
        }
        return  res;
    }
    
    public void updateUser(User user) throws Exception {
        Session session = null;

        try{
            session = new SessionBuilder().build();
            session.update(user);
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            throw e; // Rethrow the exception
        }
        finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void deleteUser(int id) throws Exception {

        Session session = null;
        User user = null;
        try{
            session = new SessionBuilder().build();

            user = (User)session.get(User.class,id);
            if (user != null) {
                session.delete(user);
            }
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            throw e; // Rethrow the exception
        }
        finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getUsers() throws Exception {
        Session session = null;
        List<User> userList = new ArrayList<>();
        try{
            session = new SessionBuilder().build();
            List<Object> objectList = session.findAll(User.class);
            for(Object o : objectList) {
                userList.add((User) o);
            }
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            throw e; // Rethrow the exception
        }
        finally {
            if (session != null) {
                session.close();
            }
        }
        return userList;
    }

    public User getUserByUsername(String username) throws SQLException {
        Session session = null;
        User res = null;
        String customQuery = "SELECT * FROM User WHERE username=?";
        HashMap<String,Object> params = new HashMap<>();
        params.put("username",username);
        try{
            session = new SessionBuilder().build();
            //List<Object> objectList = session.findAll(User.class,params);
            List<Object> objectList = session.query(customQuery,User.class,params);
            if (!objectList.isEmpty()) {
                res = (User) objectList.get(0);
            }
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            throw new SQLException("Error retrieving user by username", e); // Wrap and rethrow
        }
        finally {
            if (session != null) {
                session.close();
            }
        }
        return res;
    }
}
