package edu.upc.backend.database.util;

//import jdk.internal.classfile.impl.Util;

import edu.upc.backend.services.EETACBROSMannagerSystemService;
import org.apache.log4j.Logger;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.stream.Stream;

public class QueryHelper {

    public static String createQueryINSERT(Object entity) {

        StringBuffer sb = new StringBuffer("INSERT INTO ");
        sb.append(entity.getClass().getSimpleName()).append(" ");
        sb.append("(");

        String [] fields = ObjectHelper.getFields(entity);

        //sb.append("ID");
        /*
        for (String field: fields) {
            sb.append(", ").append(field);
        }
        */
        sb.append(fields[0]);
        for(int i = 1; i < fields.length; i++)
        {
            sb.append(", ").append(fields[i]);
        }

        sb.append(") VALUES (?");

        for (int i = 1; i < fields.length; i++) {
            sb.append(", ?");
        }

        sb.append(")");

        return sb.toString();
    }

    public static String createQuerySELECT(Object entity) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM ").append(entity.getClass().getSimpleName());
        sb.append(" WHERE ID = ?");

        return sb.toString();
    }

    public static String createQueryUPDATE(Object entity) throws NoSuchFieldException, IllegalAccessException {
        StringBuffer sb = new StringBuffer();
        String [] fields = ObjectHelper.getFields(entity);
        Stream<String> buffer = Arrays.stream(fields).filter(x->!x.equals("id"));
        fields = buffer.toArray(String[]::new);
        sb.append("UPDATE ").append(entity.getClass().getSimpleName());
        sb.append(" SET ");

        String first = fields[0];
        sb.append(first).append("=?");

        for(int i = 1; i < fields.length; i++)
        {
            sb.append(",").append(fields[i]).append("=?");
        }

        sb.append(" WHERE ID = ?");

        return sb.toString();
    }

    public static String createQueryDELETE(Object entity)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("DELETE FROM ").append(entity.getClass().getSimpleName())
                .append(" WHERE id = ?");
        return  sb.toString();
    }

    public static String createQuerySELECTID(Object entity) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ID FROM ").append(entity.getClass().getSimpleName())
                .append(" WHERE ");
        String [] fields = ObjectHelper.getFields(entity);

        String first = fields[0];
        Object value1 = ObjectHelper.getter(entity,first);
        sb.append(first).append("=");

        Utils.valueParser(sb,value1);


        for(int i = 1; i < fields.length; i++)
        {
            String f = fields[i];
            Object value = ObjectHelper.getter(entity,f);
            sb.append(",").append(f).append("=");

            Utils.valueParser(sb,value);
            /*
            // switch no funciona con Classes a este nivel de lenguaje...
            if(value.getClass() == String.class)
                sb.append("'").append(value).append("'");
            else if (value.getClass().isPrimitive()) sb.append(value.toString()); // creo que es asi
            else if(value.getClass() == java.lang.Double.class) sb.append(Utils.checkDecimal((double)value)); // por alguna razon esto funciona
            else throw new ClassNotFoundException(String.format("Class %s not found.",value.getClass()));
            */

        }
        return sb.toString();
    }

    public static String createQyerySELECTSOME(Class theClass)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM ").append(theClass.getSimpleName())
                .append(" WHERE ");
        String [] fields = ObjectHelper.getFields(theClass);

        String first = fields[0];
        sb.append(first).append(" = ?");

        for(int i = 1; i < fields.length; i++)
        {
            sb.append(",").append(fields[i]).append(" = ?");
        }

        return sb.toString();
    }

    public static String createQuerySelectAll(Class theClass)
    {
        String res = "SELECT * FROM %s";
        return String.format(res,theClass.getSimpleName());
    }

    // Todas las operaciones CRUD implementadas


    //region Tablas relacionales

    /**
     * a = one
     * b = many
     * */
    public static String createQueryINSERTOneToMany(Object a, Integer aID, Object b)
    {
        //StringBuffer sb = new StringBuffer();

        String aParam = String.format("%sID",a.getClass().getSimpleName());

        //region insert

        StringBuffer sb = new StringBuffer("INSERT INTO ");
        sb.append(b.getClass().getSimpleName()).append(" ");
        sb.append("(");

        String [] fields = ObjectHelper.getFields(b);

        sb.append("ID").append(", ").append(aParam);
        for (String field: fields) {
            sb.append(", ").append(field);
        }

        sb.append(") VALUES (?").append(", " + aID.toString());

        for (String field: fields) {
            sb.append(", ?");
        }

        sb.append(")");



        //endregion insert

        return sb.toString();

    }

    /**
     * Insert a which is bound with an existing b.
     * */
    public static String createQueryINSERTOneToOne(Object a, Object b) throws NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        String bParam = String.format("%sID",b.getClass().getSimpleName());

        //region insert
        StringBuffer sb = new StringBuffer("INSERT INTO ");
        sb.append(a.getClass().getSimpleName()).append(" ");
        sb.append("(");

        String [] fields = ObjectHelper.getFields(a);

        sb.append("ID").append(", ").append(bParam);
        for (String field: fields) {
            sb.append(", ").append(field);
        }

        sb.append(") VALUES (?").append(", (" + QueryHelper.createQuerySELECTID(b) + ")");

        for (String field: fields) {
            sb.append(", ?");
        }

        sb.append(")");

        return sb.toString();
        //endregion insert
    }

    //endregion Tablas relacionales

}
