package edu.upc.backend.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {

    public static final String DB_NAME = "projecte_dsa_db";
    public static final String DB_HOST = "localhost";

    /*// localhost Production
    public static final String DB_USER = "root";
    public static final String DB_PASS = "mariadb";
    */

    /*// Enviroment Production 1
    public static final String DB_USER = "root";
    public static final String DB_PASS = "Mazinger72";
    */

    // Enviroment Production 2
    //public static final String DB_USER = "userDSA";
    public static final String DB_USER = "root";
    public static final String DB_PASS = "mariadb";

    public static final String DB_PORT = "3306";

    public static String getDb() {
        return DB_NAME;
    }

    public static String getDbHost(){
        return DB_HOST;
    }

    public static String getDbUser() {
        return DB_USER;
    }

    public static String getDbPasswd() {
        return DB_PASS;
    }

    public static  String getDbPort() {
        return DB_PORT;
    }

    public static Connection getConnection() throws SQLException {
        String db = DBUtils.getDb();
        String host = DBUtils.getDbHost();
        String port = DBUtils.getDbPort();
        String user = DBUtils.getDbUser();
        String pass = DBUtils.getDbPasswd();


        Connection connection = DriverManager.getConnection("jdbc:mariadb://"+host+":"+port+"/"+
                db+"?user="+user+"&password="+pass);

        return connection;
    }

    //region ID management

    private static int userID = 0;

    public static int retrieveUserID()
    {
        return userID++;
    }

    //endregion ID management

}