package edu.upc.backend.database.util;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ObjectHelper {
    public static String[] getFields(Object entity) {

        Class theClass = entity.getClass();

        return getFields(theClass);

    }

    //refactorizado
    public static String[] getFields(Class theClass)
    {
        Field[] fields = theClass.getDeclaredFields();

        String[] sFields = new String[fields.length];
        int i=0;

        for (Field f: fields) sFields[i++]=f.getName();
        return sFields;
    }


    public static void setter(Object object, String property, Object value) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // Method // invoke
        // https://medium.com/@ossamakharbaq4/demystifying-java-reflection-a-guide-to-dynamic-code-capabilities-part-1-3225cfebbd61

        String capital = Utils.CapitalizeFirst(property);
        String m_name = String.format("set%s",capital);
        Method m_function = object.getClass().getMethod(m_name,value.getClass());
        m_function.invoke(object,value);

    }

    public static Object getter(Object object, String property) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // Method // invoke

        //Field f = object.getClass().getDeclaredField(property);
        String capital = Utils.CapitalizeFirst(property);
        String m_name = String.format("get%s",capital);
        Method m_function = object.getClass().getDeclaredMethod(m_name);
        return m_function.invoke(object);
    }

}
