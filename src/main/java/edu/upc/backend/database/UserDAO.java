package edu.upc.backend.database;

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
        //User input = new User(username,name,email,password);

        try{
            session = new SessionBuilder().build();
            //session.save(input);
            /*HashMap<String,Object> params = new HashMap<>();
            params.put("username",user.getUsername());
            params.put("name",user.getName());
            params.put("email",user.getEmail());
            params.put("password",user.getPassword());
            params.put("coins",user.getCoins());*/
            session.save(user);
            //session.queryMasterFunction("INSERT",User.class,params);
            id = user.getId();
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


    public User getUserById(int userID) throws Exception {
        User res = null;
        Session session = null;

        try{
            session = new SessionBuilder().build();
            HashMap<String,Object> paramsSearch = new HashMap<>();
            paramsSearch.put("id",userID);
            List<Object> objectList = session.get(User.class,paramsSearch);
            res = (User) objectList.get(0);
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
    
    public void updateUser(User user) throws Exception {
        Session session = null;
        try{
            session = new SessionBuilder().build();
            HashMap<String,Object> paramsSearch = new HashMap<>();
            paramsSearch.put("id",user.getId());
            HashMap<String,Object> paramsUpdate = new HashMap<>();
            paramsUpdate.put("name",user.getName());
            paramsUpdate.put("email",user.getEmail());
            paramsUpdate.put("password",user.getPassword());
            paramsUpdate.put("coins",user.getCoins());
            session.update(User.class,paramsSearch,paramsUpdate);
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
            HashMap<String,Object> paramsSearch = new HashMap<>();
            paramsSearch.put("id",userID);
            User buffer = (User) session.get(User.class,paramsSearch);
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
            e.printStackTrace();
        }
        finally {
                session.close();
        }
        return userList;
    }

    public User getUserByUsername(String username) throws SQLException {
        Session session = null;
        User res = null;
        HashMap<String,Object> params = new HashMap<>();
        params.put("username",username);
        try{
            session = new SessionBuilder().build();
            //res = (User) session.query("SELECT * FROM user WHERE username = ?",User.class,params).get(0);
            List<Object> objectList = session.get(User.class,params);
            res = (User) objectList.get(0);
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
