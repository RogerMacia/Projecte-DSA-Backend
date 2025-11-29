package edu.upc.backend.database.util;

import edu.upc.backend.classes.User;
import edu.upc.backend.classes.Item;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

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
        String capital = Utils.CapitalizeFirst(property);
        String m_name = String.format("set%s", capital);

        // Get the declared field to determine its type
        Field field = object.getClass().getDeclaredField(property);
        Class<?> fieldType = field.getType();

        // Handle primitive types by mapping them to their wrapper classes for reflection
        Class<?> setterParamType = fieldType;
        if (fieldType.isPrimitive()) {
            if (fieldType == int.class) setterParamType = Integer.class;
            else if (fieldType == boolean.class) setterParamType = Boolean.class;
            else if (fieldType == long.class) setterParamType = Long.class;
            else if (fieldType == double.class) setterParamType = Double.class;
            else if (fieldType == float.class) setterParamType = Float.class;
            else if (fieldType == byte.class) setterParamType = Byte.class;
            else if (fieldType == short.class) setterParamType = Short.class;
            else if (fieldType == char.class) setterParamType = Character.class;
        }

        // Find the setter method
        Method m_function = object.getClass().getMethod(m_name, setterParamType);

        // Perform type conversion if necessary
        Object valueToSet = value;
        if (value != null && !setterParamType.isInstance(value)) {
            if (setterParamType == Integer.class && value instanceof String) {
                valueToSet = Integer.parseInt((String) value);
            }
            // Add more conversions here if needed (e.g., String to Double, etc.)
        }

        m_function.invoke(object, valueToSet);
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
