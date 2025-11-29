package edu.upc.backend.database;

import edu.upc.backend.classes.User;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class UserDAO implements IUserDAO{

    private static UserDAO _instance;
    public static UserDAO getInstance()
    {
        if(_instance == null) _instance = new UserDAO();
        return _instance;
    }

    private UserDAO()
    {}

    private static final Logger log = Logger.getLogger(UserDAO.class);

    @Override
    public int addUser(String name, String password, String username, String email) throws Exception {
        return registerUser(new User(username,name,email,password));
    }

    public int registerUser(User input) throws SQLException {
        int id = -1;
        Session session = null;
        //User input = new User(username,name,email,password);

        try{

            session = new SessionBuilder().build();
            session.save(input);
            id = input.getId();
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return id;
    }

    @Override
    public User getUser(int userID) throws Exception {
        User res = null;
        Session session = null;

        try{
            session = new SessionBuilder().build();
            res = (User) session.get(User.class,userID);
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return  res;
    }

    @Override
    public void updateUser(int userID, String name, String password, String username, String email) throws Exception {

        Session session = null;
        User input = new User(username, name, email,password);
        input.setId(userID);

        try{
            session = new SessionBuilder().build();
             session.update(input);
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }

    @Override
    public void deleteUser(int userID) throws Exception {

        Session session = null;

        try{
            session = new SessionBuilder().build();
            User buffer = (User) session.get(User.class, userID);
            session.delete(buffer);
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }

    @Override
    public List<User> getUsers() throws Exception {
        List<User> res = null;
        Session session = null;
        try{

            session = new SessionBuilder().build();
            res = session.findAll(User.class);
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return res;
    }

    public User getUserByUsername(String username) throws SQLException {
        Session session = null;
        User res = null;
        HashMap<String,Object> params = new HashMap<>();
        params.put("username",username);
        try{
            session = new SessionBuilder().build();
            res = (User) session.query("SELECT * FROM user WHERE username = ?",User.class,params).get(0);
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return res;
    }
}
