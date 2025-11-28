package edu.upc.backend.database;

import edu.upc.backend.database.util.*;
import org.apache.log4j.Logger;
import edu.upc.backend.classes.User; // Import the User class

import javax.naming.NameNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Stream;


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
            int i = 1; // Parameter index for PreparedStatement

            for (String field: ObjectHelper.getFields(entity)) {
                // Special handling for User class and "items" attribute
                // If the entity is a User and the field is "items", we skip setting the parameter
                // because QueryHelper.createQueryINSERT already inserted "NULL" directly into the SQL.
                if (entity instanceof User && field.equals("items")) {
                    // Do increment 'i' and set parameter for "items" field VARCHAR NULL
                    pstm.setObject(i++, "NULL");
                    continue;
                }
                pstm.setObject(i++, ObjectHelper.getter(entity, field));
            }

            pstm.executeQuery();

        } catch (Exception e) {
            log.error(insertQuery);
            e.printStackTrace();
        }

    }

    public void close() throws SQLException {
        conn.close();
    }

    public Object get(Class theClass, int ID) throws NameNotFoundException {

        /*
        Object res = _cache.get(ID);
        res = ObjectHelper.getter()


        if(res != null) return res;
        */
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
    }

    public void update(Object object) throws NameNotFoundException {
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
            /*
            //region buscar id
            String query = QueryHelper.createQuerySELECTID(object);
            pstm = conn.prepareStatement(query);
            ResultSet rs = pstm.executeQuery();
            if(!rs.next()) throw new NameNotFoundException("ID no encontrado");

            int id = rs.getInt(1);
            //endregion buscar id
            */
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

    public List<Object> findAll(Class theClass, HashMap params) {
        PreparedStatement pstm = null;
        String query = QueryHelper.createQuerySelectAll(theClass);
        LinkedList<Object> res = new LinkedList<>();
        String[] fields = ObjectHelper.getFields(theClass);

        try{

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
                pstm.setObject(i+1, params.get(ordered[i]));
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

}
