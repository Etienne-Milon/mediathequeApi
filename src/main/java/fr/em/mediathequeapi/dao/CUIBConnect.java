package fr.em.mediathequeapi.dao;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.Connection;

public class CUIBConnect {

    private static Connection connexion;
//    private static String SERVER_NAME = "database";
    private static String SERVER_NAME = "127.0.0.1";

//    private static int PORT_NUMBER = 1433;
    private static int PORT_NUMBER = 1404;
    private static String DATABASE_NAME = "CUIB";
    private static String USER = "sa";
    private static String PASSWORD = "azerty@private123";

    private CUIBConnect() {
    }

    public static Connection getInstance() {
        if (connexion == null) {
            try {
                SQLServerDataSource ds = new SQLServerDataSource();
                ds.setServerName(SERVER_NAME);
                ds.setPortNumber(PORT_NUMBER);
                ds.setDatabaseName(DATABASE_NAME);
                ds.setIntegratedSecurity(false);
                ds.setEncrypt(false);
                ds.setUser(USER);
                ds.setPassword(PASSWORD);
                connexion = ds.getConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connexion;
    }
}
