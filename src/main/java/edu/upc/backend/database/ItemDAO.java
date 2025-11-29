package edu.upc.backend.database;

import edu.upc.backend.classes.Item;
import edu.upc.backend.classes.User;
import org.apache.log4j.Logger;

import java.sql.SQLException;
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
        List<Item> res = null;
        Session session = null;
        try{

            session = new SessionBuilder().build();
            res = session.findAll(Item.class);
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
