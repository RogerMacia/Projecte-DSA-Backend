package edu.upc.backend.database.util;

import edu.upc.backend.database.util.*;

import java.util.*;

public class Utils {
    // https://www.baeldung.com/java-string-uppercase-first-letter , que es chatgpt?
    public static String CapitalizeFirst(String input)
    {
        String output = input.substring(0, 1).toUpperCase() + input.substring(1);
        return output;
    }
    //https://www.access-programmers.co.uk/forums/threads/decimal-points-only-if-it-has-decimal-values-no-decimal-if-it-is-a-full-number.315747/
    public static String checkDecimal(double dec)
    {
        if((int)dec == dec)
        {
            return Integer.toString((int)dec);
        }
        else
        {
            return Double.toString(dec);
        }
    }

    public static void valueParser(StringBuffer sb, Object value) throws ClassNotFoundException {
        // switch no funciona con Classes a este nivel de lenguaje...
        if(value.getClass() == String.class)
            sb.append("'").append(value).append("'");
        else if (value.getClass().isPrimitive()) sb.append(value.toString()); // creo que es asi
        else if(value.getClass() == Double.class) sb.append(Utils.checkDecimal((double)value)); // por alguna razon esto funciona
        else throw new ClassNotFoundException(String.format("Class %s not found.",value.getClass()));
    }


    public static String[] computeOrder(String input, Set<String> sub)
    {
        LinkedList<Pair<String,Integer>> priority = new LinkedList<>(); // como una cola de prioridad basado en una lista desordenada
        LinkedList<String> res = new LinkedList<>(); // ya es una lista ordenada

        for(String line : sub)
        {
            int index = input.indexOf(line);
            if(index > -1) priority.add(new Pair<String,Integer>(line, index));
        }

        if(priority.size() == 0) return res.toArray(new String[0]);

        //Pair<String,Integer> pivot = null;
        // insertion sort, https://gist.github.com/wenjie-c/89e850b66f5d3f18ef625b54b9e3761f
        int i = 1;

        while(i < priority.size())
        {
            int j = i;
            while(j > 0 && priority.get(j - 1).getSecond() > priority.get(j).getSecond())
            {
                Collections.swap(priority,j,j-1);
                j--;
            }
            i++;
        }

        for(int x = 0; x < priority.size(); x++)
        {
            res.addLast(priority.get(x).getFirst());
        }

        return res.toArray(new String[0]);
    }

    public static String[] computeOrder(String input, String[] sub)
    {
        LinkedList<Pair<String,Integer>> priority = new LinkedList<>(); // como una cola de prioridad basado en una lista desordenada
        LinkedList<String> res = new LinkedList<>(); // ya es una lista ordenada

        for(String line : sub)
        {
            int index = input.indexOf(line);
            if(index > -1) priority.add(new Pair<String,Integer>(line, index));
        }

        if(priority.size() == 0) return res.toArray(new String[0]);

        //Pair<String,Integer> pivot = null;
        // insertion sort, https://gist.github.com/wenjie-c/89e850b66f5d3f18ef625b54b9e3761f
        int i = 1;

        while(i < priority.size())
        {
            int j = i;
            while(j > 0 && priority.get(j - 1).getSecond() > priority.get(j).getSecond())
            {
                Collections.swap(priority,j,j-1);
                j--;
            }
            i++;
        }

        for(int x = 0; x < priority.size(); x++)
        {
            res.addLast(priority.get(x).getFirst());
        }

        return res.toArray(new String[0]);
    }

    // Source - https://stackoverflow.com/a
// Posted by Vitalii Fedorenko, modified by community. See post 'Timeline' for change history
// Retrieved 2025-11-23, License - CC BY-SA 4.0

    public static <E> Integer getKeyByValue(Map<Integer, E> map, E value) {
        for (Map.Entry<Integer, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return -1;
    }

    public static String formatUrl(ResourceManager rm)
    {
        String url = String.format("jdbc:mariadb://%s:%s/%s?user=%s&password=%s",
                rm.get("address"),
                rm.get("port"),
                rm.get("db"),
                rm.get("user"),
                rm.get("password")
        );

        return url;
    }


}
