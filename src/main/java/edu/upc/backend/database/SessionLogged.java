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
    public List<Object> get(Class theClass, HashMap<String, Object> paramsSearch){
        //Object res = null;
        try
        {
            log.info("Selecting object: " + theClass.getName());
            return _session.get(theClass, paramsSearch);
        }
        catch (Exception e)
        {
            log.error("Error: " + e.getMessage());
        }
        return null;
    }

//    public Object getUserItem(Class theClass, ){
//        //Object res = null;
//        try
//        {
//            log.info("Selecting object: " + theClass.getName() + " with ID: " + Integer.toString(itemId));
//            return _session.query(theClass,);
//        }
//        catch (Exception e)
//        {
//            log.error(e.getMessage());
//        }
//        return null;
//    }

    /*@Override
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
    }*/

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

    /*@Override
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
    }*/


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

    public List<Object> queryMasterFunction(String query, Class theClass, HashMap params) {
        try
        {
            log.info(String.format("Custom query \" %s \" for the object %s",query ,theClass.getName()));
            List<Object> res =  _session.queryMasterFunction(query,theClass,params);
            log.info(String.format("%d objects were found.", res.size()));
            return res;
        }
        catch (Exception e)
        {
            log.warn("Error: " + e.getMessage());
        }
        return null;
    }

    public Object update(Class theClasss, HashMap<String, Object> paramsSearch, HashMap<String, Object> paramsUpdate) {
        try
        {
            log.info("Updating object: " + theClasss.getClass().getName());
            Object res = _session.update(theClasss, paramsSearch, paramsUpdate);
            log.info("Object successfully updated.");
            return res;
        }
        catch (Exception e)
        {
            log.error("Error: " + e.getMessage());
        }
        return null;
    }

}
