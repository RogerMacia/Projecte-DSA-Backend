package edu.upc.backend.database;

import edu.upc.backend.classes.UserItem;
import org.apache.log4j.Logger;

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

    public void addUserItem(UserItem userItem) {
        Session session = null;
        try {
            session = new SessionBuilder().build();
            session.save(userItem);
        }
        catch (Exception ex) {
            logger.error(ex);
        }
    }

    public UserItem getUserItem(int userId, int itemId) {
        Session session = null;
        UserItem useritem = null;
        try {
            session = new SessionBuilder().build();
            HashMap<String,Object> params = new HashMap<>();
            params.put("userId",userId);
            params.put("itemId",itemId);
            //List<Object> objectList = session.queryMasterFunction("SELECT",UserItem.class,params);
            List<Object> objectList = session.get(UserItem.class, params);
            useritem = (UserItem) objectList.get(0);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return useritem;
    }

    public UserItem updateUserItem(UserItem userItem) throws Exception{
        Session session = null;
        try {
            session = new SessionBuilder().build();
            HashMap<String,Object> paramsSearch = new HashMap<>();
            paramsSearch.put("userId",userItem.getUserId());
            paramsSearch.put("itemId",userItem.getItemId());
            HashMap<String,Object> paramsUpdate = new HashMap<>();
            paramsUpdate.put("quantity",userItem.getQuantity());
            int affectedRows = (int) session.update(UserItem.class,paramsSearch,paramsUpdate);
            if (affectedRows > 0) {
                return userItem;
            }
            return null;
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return  null;
        }
        finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
