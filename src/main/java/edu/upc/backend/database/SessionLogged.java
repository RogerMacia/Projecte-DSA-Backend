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

        try {
            _session.save(entity);
        }
        catch (Exception e)
        {
            log.error("Error: " + e.getMessage());
        }

    }

    @Override
    public void update(Object object) {
        try
        {
            _session.update(object);
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
            _session.close();
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
            _session.delete(object);
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
    public Object findLast(Class theClass) {
        Object res = null;
        try
        {
            res = _session.findLast(theClass);
        }
        catch (Exception e)
        {
            log.warn("Error: " + e.getMessage());
        }
        return res;
    }

    @Override
    public List<Object> findAll(Class theClass) {
        try
        {
            List<Object> res = _session.findAll(theClass);
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
            List<Object> res = _session.findAll(theClass,params);
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
            int res = _session.findId(object);
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
            int res = _session.findId(query,params);
            return res;
        }
        catch (Exception e)
        {
            log.warn("Error: " + e.getMessage());
        }
        return -1;
    }


}
