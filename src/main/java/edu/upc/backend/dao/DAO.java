package edu.upc.backend.dao;

import java.util.HashMap;
import java.util.List;

public interface DAO {
    void save(Object entity);
    void close();
    Object get(Class<?> theClass, Object ID);
    void update(Object object);
    void delete(Object object);
    List<Object> findAll(Class<?> theClass);
    List<Object> findAll(Class<?> theClass, HashMap<String, Object> params);
    List<Object> query(String query, Class<?> theClass, HashMap<String, Object> params);
}
