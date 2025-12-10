package edu.upc.backend.database;

import javax.naming.NameNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface Session {
/// El orm debe ser puramente agnostico
    void save(Object entity);
    void update(Object object);
    //void update(Class theClasss, HashMap<String, Object> paramsSearch, HashMap<String, Object> paramsUpdate); // existe la funcion de arriba...
    Object get(Class theclass, int id);
    void close() throws SQLException;
    void delete(Object object) throws NameNotFoundException;

    List<Object> findAll(Class theClass);
    List<Object> findAll(Class theClass, HashMap params);
    int findId(Object object);
    int findId(String query, HashMap params);

    List<Object> query(String query, Class theClass, HashMap params);
    //List<Object> queryMasterFunction(String action, Class theClass, HashMap<String, Object> params);
}
