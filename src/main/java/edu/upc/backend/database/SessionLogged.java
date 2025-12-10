package edu.upc.backend.database;

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
    public Object get(Class theClass, int id){
        //Object res = null;
        try
        {
            log.info("Selecting object: " + theClass.getName());
            return _session.get(theClass, id);
        }
        catch (Exception e)
        {
            log.error("Error: " + e.getMessage());
        }
        return null;
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
    public List<Object> query(String query, Class theClass, HashMap params) {
        try
        {
            log.info(String.format("Custom query \" %s ",query ));
            List<Object> res =  _session.query(query,theClass,params);
            //log.info(String.format("%d objects were found.", res.size()));
            return res;
        }
        catch (Exception e)
        {
            log.warn("Error: " + e.getMessage());
        }
        return null;
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
    public int findId(Object object) {
        try
        {
            log.info("Findind id for " + object.toString());
            int res = _session.findId(object);
            log.info(String.format("%d was found"));
            return res;
        }
        catch (Exception e)
        {
            log.warn("Error: " + e.getMessage());
        }
        return -1;
    }

    @Override
    public int findId(String query, HashMap params) {
        try
        {
            log.info("Executing custom query: " + query);
            int res = _session.findId(query,params);
            log.info(String.format("id: %d was found",res));
            return res;
        }
        catch (Exception e)
        {
            log.warn("Error: " + e.getMessage());
        }
        return -1;
    }


}
