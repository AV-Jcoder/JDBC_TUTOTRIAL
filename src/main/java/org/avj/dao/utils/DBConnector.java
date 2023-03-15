package org.avj.dao.utils;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class DBConnector {

    private static ConnectionPool pool;

    public static Connection getConnection() {
        try {
            return getConnectionWithLocalProperties();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Connection getConnectionWithLocalProperties() throws SQLException {
        ResourceBundle bundle = ResourceBundle.getBundle("postgresql");
        String url = bundle.getString("URL");
        String login = bundle.getString("LOGIN");
        String pass = bundle.getString("PASSWORD");
        Connection con = DriverManager.getConnection(url,login,pass);
        return con;
    }

    private static Connection getConnectionFromRemoteProperties() throws SQLException {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("src/main/java/resources/other.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String url = properties.getProperty("URL");
        String login = properties.getProperty("LOGIN");
        String pass = properties.getProperty("PASSWORD");
        Connection con = DriverManager.getConnection(url,login,pass);
        return con;
    }

    private static Connection getConnectionFromPool() throws SQLException {
        ResourceBundle bundle = ResourceBundle.getBundle("postgres");
        String url = bundle.getString("URL");
        String login = bundle.getString("LOGIN");
        String pass = bundle.getString("PASSWORD");
        pool = new ConnectionPool(url,login,pass,10);
        return pool.getConnection();
    }


}
