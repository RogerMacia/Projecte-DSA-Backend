package edu.upc.backend.database;

import edu.upc.backend.classes.Item;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;

// Es un decorador
public class SessionLogged implements Session{

    Logger log = Logger.getLogger(SessionLogged.class);
    Session _session = null;

    public SessionLogged(Session session)
    {
        _session = session;
    }

    @Override
    public void save(Object entity) {

        log.info("Saving object: " + entity.getClass().getName());
        try {
            _session.save(entity);
        }
        catch (Exception e)
        {
            log.error("Error: " + e.getMessage());
        }

        log.info("Object successfully saved.");
    }

    @Override
    public void close(){
        try
        {
            log.info("Trying to close the session...");
            _session.close();
            log.info("Session closed.");
        }
        catch (Exception e)
        {
            log.error("Error: " + e.getMessage());
        }
    }

    @Override
    public Object get(Class theClass, int ID){
        //Object res = null;
        try
        {
            log.info("Selecting object: " + theClass.getName() + " with ID: " + Integer.toString(ID));
            return _session.get(theClass,ID);
        }
        catch (Exception e)
        {
            log.error("Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Object object) {
        try
        {
            log.info("Updating object: " + object.getClass().getName());
            _session.update(object);
            log.info("Object successfully updated.");
        }
        catch (Exception e)
        {
            log.error("Error: " + e.getMessage());
        }
    }

    @Override
    public void delete(Object object) {
        try
        {
            log.info("Deleting object: " + object.getClass().getName());
            _session.delete(object);
            log.info("Object successfully deleted.");
        }
        catch (Exception e)
        {
            log.error("Error: " + e.getMessage());
        }
    }

    @Override
    public List<Object> findAll(Class theClass) {
        try
        {
            log.info("Selecting all " + theClass.getName());
            List<Object> res = _session.findAll(theClass);
            log.info(String.format("%d objects were found.", res.size()));
            return res;
        }
        catch (Exception e)
        {
            log.error("Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Object> findAll(Class theClass, HashMap params) {
        try
        {
            log.info(String.format("Selecting all %s with params: %s",theClass.getName(), params.toString()));
            List<Object> res = _session.findAll(theClass,params);
            log.info(String.format("%d objects were found.", res.size()));
            return res;
        }
        catch (Exception e)
        {
            log.warn("Error: " + e.getMessage());
        }
        return null;
    }


    @Override
    public List<Object> query(String query, Class theClass, HashMap params) {
        try
        {
            log.info(String.format("Custom query \" %s \" for the object %s",query ,theClass.getName()));
            List<Object> res =  _session.query(query,theClass,params);
            log.info(String.format("%d objects were found.", res.size()));
            return res;
        }
        catch (Exception e)
        {
            log.warn("Error: " + e.getMessage());
        }
        return null;
    }

}
