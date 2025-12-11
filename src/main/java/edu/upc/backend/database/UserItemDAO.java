package edu.upc.backend.database;

import edu.upc.backend.classes.UserItem;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserItemDAO implements IUserItemDAO{
    private static UserItemDAO _instance;

    public static UserItemDAO getInstance() {
        if(_instance == null) _instance = new UserItemDAO();
        return _instance;
    }

    private UserItemDAO() {

    }

    private static final Logger logger = Logger.getLogger(UserItemDAO.class);

    public void addUserItem(UserItem userItem) throws SQLException {
        Session session = null;
        String customQuery = "SELECT quantity FROM UserItem WHERE userId=? AND itemId=?";
        HashMap<String,Object> params = new HashMap<>();
        params.put("userId",userItem.getUserId());
        params.put("itemId",userItem.getItemId());
        try {
            session = new SessionBuilder().build();
            int quantity = session.findId(customQuery,params); // Tambien sirve para buscar cualquier entero
            if (quantity < 1) // not found
                session.save(userItem);
            else
            {
                UserItem buffer = new UserItem(userItem.getUserId(),userItem.getItemId(),quantity + userItem.getQuantity());
                String customQuery2 = "UPDATE UserItem SET quantity=? WHERE userId=? AND itemId=?";
                HashMap<String,Object> params2 = new HashMap<>();
                params2.put("quantity",buffer.getQuantity());
                params2.put("userId",buffer.getUserId());
                params2.put("itemId",buffer.getItemId());
                session.query(customQuery2,null,params2);
            }



        }
        catch (Exception ex) {
            logger.error(ex);
        }
        finally {
            session.close();
        }
    }

    public List<UserItem> getUserItems(int userId) throws SQLException {
        Session session = null;
        String customQuery = "SELECT * FROM UserItem WHERE userId=?";
        List<UserItem> useritemList = null;
        HashMap<String,Object> params = new HashMap<>();
        params.put("userId",userId);
        try {
            session = new SessionBuilder().build();
            //List<Object> objectList = session.findAll(UserItem.class, params);
            List<Object> objectList = session.query(customQuery,UserItem.class,params);
            useritemList = new ArrayList<>();
            for(Object o : objectList) {
                useritemList.add((UserItem) o);
            }

        }
        catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return useritemList;
    }

    public void updateUserItem(UserItem userItem) throws Exception{
        Session session = null;
        try {
            session = new SessionBuilder().build();
            session.update(userItem);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
