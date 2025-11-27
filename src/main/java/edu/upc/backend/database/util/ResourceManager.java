package edu.upc.backend.database.util;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceManager
{
    private static ResourceManager _instance;
    final static Logger log = Logger.getLogger(ResourceManager.class);
    private HashMap<String,String> _vars;

    private ResourceManager()
    {
        _vars = new HashMap<>();
        //default values
        _vars.put("address","localhost");
        _vars.put("user","root");
        _vars.put("port","3306");
        _vars.put("db","example");
        _vars.put("password","1234");
    }

    static public ResourceManager getInstance()
    {
        if(_instance == null)
        {
            _instance = new ResourceManager();
        }
        return _instance;
    }

    public void setVars(String lang)
    {
        ResourceBundle bundle = ResourceBundle.getBundle("var",new Locale(lang));

        if(bundle.containsKey("address"))
        {
            String address = bundle.getString("address");
            log.info("Using address: " + address);
            _vars.put("address",address);
        }
        if(bundle.containsKey("user"))
        {
            String user = bundle.getString("user");
            log.info("Using user: " + user);
            _vars.put("user",user);
        }
        if(bundle.containsKey("port"))
        {
            String port = bundle.getString("port");
            log.info("Using port: " + port);
            _vars.put("port",port);
        }
        if(bundle.containsKey("db"))
        {
            String db = bundle.getString("db");
            log.info("Using db: " + db);
            _vars.put("db",db);
        }
        if(bundle.containsKey("password"))
        {
            String password = bundle.getString("password");
            log.info("Using password: " + password);
            _vars.put("password",password);
        }
    }

    public String get(String var)
    {
        return _vars.get(var);
    }
}
