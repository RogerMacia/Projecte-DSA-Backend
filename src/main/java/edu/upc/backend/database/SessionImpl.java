package edu.upc.backend.database;

import edu.upc.backend.classes.Item;
import edu.upc.backend.classes.UserItem;
import edu.upc.backend.database.util.*;
import org.apache.log4j.Logger;


import javax.naming.NameNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Stream;

// ORM cortesia de https://github.com/wenjie-c/DAO

public class SessionImpl implements Session {
    private final Connection conn;

    //region magia borras
    //HashMap<Integer,Object> _cache;
    Logger log = Logger.getLogger(SessionImpl.class);
    //endregion magia borras

    public SessionImpl(Connection conn) {
        this.conn = conn;
        //_cache = new HashMap<>();
    }

    public void save(Object entity) {

        String insertQuery = QueryHelper.createQueryINSERT(entity);

        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(insertQuery);
            //pstm.setObject(1, 0);
            int i = 1;

            for (String field: ObjectHelper.getFields(entity)) {
                pstm.setObject(i++, ObjectHelper.getter(entity, field));
            }

            pstm.executeQuery();

        }
        catch (Exception e) {
            log.error(insertQuery);
            e.printStackTrace();
        }

    }

    public Object update(Class theClass, HashMap<String, Object> paramsSearch, HashMap<String, Object> paramsUpdate) {
        PreparedStatement pstm = null;
        String query = QueryHelper.createUPDATEMASTERFUNCTION(theClass, paramsSearch, paramsUpdate);
        try {
            pstm = conn.prepareStatement(query);
            int i = 1;

            // The order of keys is not guaranteed, but we're following the same order as query creation.
            String[] updateKeys = paramsUpdate.keySet().toArray(new String[0]);
            for (String key : updateKeys) {
                pstm.setObject(i++, paramsUpdate.get(key));
            }

            String[] searchKeys = paramsSearch.keySet().toArray(new String[0]);
            for (String key : searchKeys) {
                pstm.setObject(i++, paramsSearch.get(key));
            }

            int affectedRows = pstm.executeUpdate();
            return affectedRows;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Object> get(Class theClass, HashMap<String, Object> paramsSearch) {
        PreparedStatement pstm = null;
        String query = QueryHelper.createSELECTMASTERFUNCTION(theClass, paramsSearch);
        List<Object> results = new LinkedList<>();
        try {
            pstm = conn.prepareStatement(query);

            if (paramsSearch != null && !paramsSearch.isEmpty()) {
                // The order of keys is not guaranteed, but we're following the same order as query creation in QueryHelper.
                String[] searchKeys = paramsSearch.keySet().toArray(new String[0]);
                for (int i = 0; i < searchKeys.length; i++) {
                    pstm.setObject(i + 1, paramsSearch.get(searchKeys[i]));
                }
            }

            ResultSet rs = pstm.executeQuery();
            String[] fields = ObjectHelper.getFields(theClass);

            while (rs.next()) {
                Object instance = theClass.getConstructor().newInstance();
                for (int i = 0; i < fields.length; i++) {
                    ObjectHelper.setter(instance, fields[i], rs.getObject(i + 1));
                }
                results.add(instance);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return results;
    }

    public void delete(Object object) {
        PreparedStatement pstm = null;
        PreparedStatement pstm2 = null;
        try
        {
            //region buscar id
            //Integer id = Utils.getKeyByValue(_cache,object);
            Integer id = (Integer) ObjectHelper.getter(object,"id");
            //if(id < 0) throw new NameNotFoundException("Id not found.");
            //endregion buscar id

            String query2 = QueryHelper.createQueryDELETE(object);
            pstm2 = conn.prepareStatement(query2);
            //log.info("La id es " + id);
            pstm2.setObject(1,id);
            pstm2.executeQuery();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public List<Object> findAll(Class theClass) {
        PreparedStatement pstm = null;
        String query = QueryHelper.createQuerySelectAll(theClass);
        LinkedList<Object> res = new LinkedList<>();
        String[] fields = ObjectHelper.getFields(theClass);

        try {
            pstm = conn.prepareStatement(query);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Object buffer = theClass.getConstructor().newInstance();
                for (int i = 0; i < fields.length; i++) {
                    ObjectHelper.setter(buffer, fields[i], rs.getObject(i + 1));
                }
                res.add(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    public void close() throws SQLException {
        conn.close();
    }

    /*public Object get(Class theClass, int ID) throws NameNotFoundException {

        *//*
        Object res = _cache.get(ID);
        res = ObjectHelper.getter()


        if(res != null) return res;
        *//*
        PreparedStatement pstm = null;
        Object res = null;

        try{
            res = theClass.getConstructor().newInstance();
            String query = QueryHelper.createQuerySELECT(res);
            pstm = conn.prepareStatement(query);

            pstm.setObject(1,ID);

            ResultSet rs = pstm.executeQuery();

            if(!rs.next()) throw new NameNotFoundException("Objeto con id " + ID + " no fue encontrado."); // next una vez, creo

            String[] f =  ObjectHelper.getFields(res);
            for(int i = 0; i < f.length; i++)
            {
                ObjectHelper.setter(res,f[i],rs.getObject(i+1));
            }

            //_cache.put(ID,res);
        }
        catch (SQLException | NoSuchFieldException | IllegalAccessException | NoSuchMethodException |
               InvocationTargetException | InstantiationException e)
        {
            e.printStackTrace(); // errores importantes
        }

        return res;
    }*/

    /*public void update(Object object) throws NameNotFoundException {
        PreparedStatement pstm = null;
        //Integer id = Utils.getKeyByValue(_cache,object);
        //Object old = _cache.get(id);

        String[] fields = ObjectHelper.getFields(object);
        Stream<String> buffer = Arrays.stream(fields).filter(x->!x.equals("id")); // solucion chapucera
        fields = buffer.toArray(String[]::new);
        String query = "";
        PreparedStatement pstm2 = null;
        try {
            Integer id = (Integer) ObjectHelper.getter(object,"id");
            if(id < 0) throw new NameNotFoundException("Id not found! Unknown object.");
            *//*
            //region buscar id
            String query = QueryHelper.createQuerySELECTID(object);
            pstm = conn.prepareStatement(query);
            ResultSet rs = pstm.executeQuery();
            if(!rs.next()) throw new NameNotFoundException("ID no encontrado");

            int id = rs.getInt(1);
            //endregion buscar id
            *//*
            query = QueryHelper.createQueryUPDATE(object);
            pstm2 = conn.prepareStatement(query);


            for(int i = 0; i < fields.length;i++)
            {

                pstm2.setObject(i+1,ObjectHelper.getter(object,fields[i]));
            }
            pstm2.setObject(fields.length+1,id);

            pstm2.executeQuery();



        }
        catch (SQLException | NoSuchFieldException | IllegalAccessException | NoSuchMethodException |
               InvocationTargetException e)
        {
            log.error(query);
            e.printStackTrace();
        }
    }*/

    /* WENJIE
    public List<Object> findAll(Class theClass) {
        PreparedStatement pstm = null;
        String query = QueryHelper.createQuerySelectAll(theClass);
        LinkedList<Object> res = new LinkedList<>();
        String[] fields = ObjectHelper.getFields(theClass);

        try{

            pstm = conn.prepareStatement(query);

            ResultSet rs = pstm.executeQuery();

            while(rs.next())
            {
                Object buffer = theClass.getConstructor().newInstance();
                for(int i = 0; i < fields.length; i++)
                {
                    ObjectHelper.setter(buffer,fields[i],rs.getObject(i+1));
                }
                //_cache.put(rs.getInt(1),buffer);

                res.add(buffer);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return res;
    }
    */

<<<<<<< HEAD
    // S'HAURIA D'ELIMINAR AQUESTA FUNCIÓ
    /*public List<Item> getItemlist() {
        PreparedStatement pstm = null;
        String query = QueryHelper.createQuerySelectAll(Item.class);
        LinkedList<Item> res = new LinkedList<>();
        String[] fields = ObjectHelper.getFields(Item.class);

        try {
            pstm = conn.prepareStatement(query);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Item buffer = Item.class.getConstructor().newInstance();
                for (int i = 0; i < fields.length; i++) {
                    ObjectHelper.setter(buffer, fields[i], rs.getObject(i + 1));
                }
                res.add(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }*/
=======
>>>>>>> 6e3699013d9a115113aadaf783cdc66f45c1346d

    public List<Object> findAll(Class theClass, HashMap params) {
        PreparedStatement pstm = null;
        String query = QueryHelper.createQuerySelectAll(theClass);
        LinkedList<Object> res = new LinkedList<>();
        String[] fields = ObjectHelper.getFields(theClass);

        try {

            String[] ordered = Utils.computeOrder(query,params.keySet());

            pstm = conn.prepareStatement(query);

            for(int i = 0; i < ordered.length; i++) pstm.setObject(i+1,params.get(ordered[i]));

            //pstm.setObject(1,);

            ResultSet rs = pstm.executeQuery();

            while(rs.next())
            {
                Object buffer = theClass.getConstructor().newInstance();
                for(int i = 0; i < fields.length; i++)
                {
                    ObjectHelper.setter(buffer,fields[i],rs.getObject(i+2));
                }
                //_cache.put(rs.getInt(1),buffer);

                res.add(buffer);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return res;
    }

    public int findId(Object object)
    {
        PreparedStatement pstm = null;
        int res = -1;

        try {

            String query = QueryHelper.createQuerySELECTID(object);
            pstm = conn.prepareStatement(query);
            ResultSet rs = pstm.executeQuery();
            if(!rs.next()) return res;
            res = rs.getInt(1);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return res;
    }

    // Hasmap debe set <string,string>, el primero siendo el nombre de la propiedad y el segundo el valor en si.
    public List<Object> query(String query, Class theClass, HashMap params) {
        PreparedStatement pstm = null;
        LinkedList<Object> res = new LinkedList<>();
        try
        {

            pstm = conn.prepareStatement(query);
            Set<String> keys = params.keySet();
            String[] ordered = Utils.computeOrder(query,keys);

            String[] fields = ObjectHelper.getFields(theClass);


            for(int i = 0; i < ordered.length; i++)
            {
                pstm.setObject(i+1, params.get(ordered[i])); // <---
            }

            ResultSet rs = pstm.executeQuery();
            while(rs.next())
            {
                Object buffer = theClass.getConstructor().newInstance();
                for(int x = 0; x < fields.length; x++)
                {
                    ObjectHelper.setter(buffer,fields[x],rs.getObject(x+1)); // el primero
                }
                res.add(buffer);
            }

            return res;

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public List<Object> queryMasterFunction(String action, Class theClass, HashMap<String, Object> params ) {
        PreparedStatement pstm = null;
        LinkedList<Object> res = new LinkedList<>();
        try
        {
            String query = QueryHelper.createQueryMasterFunction(action,theClass, params);
            pstm = conn.prepareStatement(query);
            Set<String> keys = params.keySet();
            String[] ordered = Utils.computeOrder(query,keys);

            String[] fields = ObjectHelper.getFields(theClass);


            for(int i = 0; i < ordered.length; i++)
            {
                pstm.setObject(i+1, params.get(ordered[i])); // <---
            }

            ResultSet rs = pstm.executeQuery();
            while(rs.next())
            {
                Object buffer = theClass.getConstructor().newInstance();
                for(int x = 0; x < fields.length; x++)
                {
                    ObjectHelper.setter(buffer,fields[x],rs.getObject(x+1)); // el primero
                }
                res.add(buffer);
            }

            return res; // Aquí funciona correctament

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
