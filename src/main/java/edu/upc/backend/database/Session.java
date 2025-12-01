package edu.upc.backend.database;

import edu.upc.backend.classes.Item;

import javax.naming.NameNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/// El orm debe ser puramente agnostico

public interface Session<E> {
    void save(Object entity);
    void close() throws SQLException;
    Object get(Class theClass, int ID) throws NameNotFoundException;
    void update(Object object) throws NameNotFoundException;
    void delete(Object object) throws NameNotFoundException;
    List<Object> findAll(Class theClass);
    List<Object> findAll(Class theClass, HashMap params);
    List<Object> query(String query, Class theClass, HashMap params);
}
