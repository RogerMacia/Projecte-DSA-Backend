package edu.upc.backend.database;

import edu.upc.backend.classes.Item;
import edu.upc.backend.classes.User;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemDAO implements IItemDAO {
    private static ItemDAO _instance;
    public static ItemDAO getInstance()
    {
        if(_instance == null) _instance = new ItemDAO();
        return _instance;
    }

    private ItemDAO()
    {}

    private static final Logger log = Logger.getLogger(ItemDAO.class);

    @Override
    public List<Item> getItemlist() throws Exception {
        List<Item> itemList = new  ArrayList<>();
        Session session = null;
        try{

            session = new SessionBuilder().build();
            List<Object> objectList = session.findAll(Item.class);
            for(Object o : objectList) {
                itemList.add((Item) o);
            }
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        finally {
            if (session != null) {
                session.close();
            }
        }
        return itemList;
    }

    public Item getItemById(int itemId) throws SQLException {
        Session session = null;
        try {
            session = new SessionBuilder().build();
            HashMap<String,Object> paramsSerch = new HashMap<>();
            paramsSerch.put("id",itemId);
            List<Object> objects = session.get(Item.class,paramsSerch);
            if (!objects.isEmpty()) {
                return (Item) objects.get(0);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }
}
