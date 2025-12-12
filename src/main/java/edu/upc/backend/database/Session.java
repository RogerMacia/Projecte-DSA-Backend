package edu.upc.backend.database;

import javax.naming.NameNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface Session {
/// El orm debe ser puramente agnostico
    /**
     * Save current entity
     * @param entity
     */
    void save(Object entity);

    /**
     * Updates the current object
     * @param object
     */
    void update(Object object);


    //void update(Class theClasss, HashMap<String, Object> paramsSearch, HashMap<String, Object> paramsUpdate); // existe la funcion de arriba...

    /**
     * Get the entity from theclass table with that id.
     * @param theclass
     * @param id Id of a specific instance of theclass.
     * @return
     */
    Object get(Class theclass, int id);

    /**
     * Closes the database.
     * @throws SQLException
     */
    void close() throws SQLException;

    /**
     * Deletes the object.
     * @param object
     * @throws NameNotFoundException
     */
    void delete(Object object) throws NameNotFoundException;

    /**
     * Select all from theclass table.
     * @param theClass
     * @return
     */
    List<Object> findAll(Class theClass);

    /**
     * Select all from theclass table with specific params.
     * @param theClass
     * @param params The specific params.
     * @return
     */
    List<Object> findAll(Class theClass, HashMap params);

    /**
     * Finds the id of the specific object.
     * @param object The object created with specific parameters locally for search purpose only.
     * @return
     */
    int findId(Object object);

    /**
     * Finds the id or any specific integer by a custom query and specific parameters.
     * @param query The custom query.
     * @param params The specific parameters.
     * @return Any Integer parameter from the specific table.
     */
    int findId(String query, HashMap params);

    /**
     * Get all the entities from a custom query providing specific parameters.
     * @param query The custom query
     * @param theClass
     * @param params The specific parameters.
     * @return
     */
    List<Object> query(String query, Class theClass, HashMap params);
    //List<Object> queryMasterFunction(String action, Class theClass, HashMap<String, Object> params);
}
