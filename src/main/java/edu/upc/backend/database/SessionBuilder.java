package edu.upc.backend.database;

import edu.upc.backend.util.DBUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SessionBuilder {

    final static Logger log = Logger.getLogger(SessionBuilder.class);
    private String _host;
    private  String _user;
    private String _password;
    private String _database;
    private String _port;

    public SessionBuilder()
    {
        _host = DBUtils.getDbHost();
        _user = DBUtils.getDbUser();
        _password = DBUtils.getDbPasswd();
        _database = DBUtils.getDb();
        _port = DBUtils.getDbPort();
    }

    public SessionBuilder setUser(String user) {_user = user; return this;}
    public SessionBuilder setPassword(String password) {_password = password; return this;}
    public SessionBuilder setDatabase(String database) {_database = database; return this;}
    public SessionBuilder setPort(String port) {_port = port; return this;}
    public SessionBuilder setHost(String host) {_host = host; return this;}

    public Session build()
    {
        Connection conn = null;
        String url = String.format("jdbc:mariadb://%s:%s/%s?user=%s&password=%s",
                _host,
                _port,
                _database,
                _user,
                _password
        );

        try{
            conn = DriverManager.getConnection(url);

        } catch (SQLException ex) {
            // handle any errors
            log.error("SQLException: " + ex.getMessage());
            log.error("SQLState: " + ex.getSQLState());
            log.error("VendorError: " + ex.getErrorCode());
        }


        Session res = new SessionImpl(conn);
        return res;
    }

}
