package edu.upc.backend.database;

import javax.naming.NameNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface Session {
    void save(Object entity);
    void close() throws SQLException;
    //Object get(Class theClass, int ID) throws NameNotFoundException;
    //void update(Object object) throws NameNotFoundException;
    void delete(Object object) throws NameNotFoundException;
    List<Object> findAll(Class theClass);
    //List<Object> findAll(Class theClass, HashMap params);
    List<Object> query(String query, Class theClass, HashMap params);
    List<Object> queryMasterFunction(String action, Class theClass, HashMap<String, Object> params);
    Object update(Class theClasss, HashMap<String, Object> paramsSearch, HashMap<String, Object> paramsUpdate);
    List<Object> get(Class theclass, HashMap<String, Object> paramsSearch);
}
