package org.avj;

import org.avj.dao.utils.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class TestPool {
    public static void main(String[] args) {

        ResourceBundle resource = ResourceBundle.getBundle("database");
        String url = resource.getString("URL");
        String login = resource.getString("LOGIN");
        String pass = resource.getString("PASSWORD");

        Connection conn = null;
        ConnectionPool pool = new ConnectionPool(url,login,pass,2);
        try{
            conn = pool.getConnection();
            try(Statement st = conn.createStatement()){
                ResultSet resultSet = st.executeQuery("SELECT * FROM employee");
                System.out.println("tables below");
                while (resultSet.next()){
                    System.out.print(" | ");
                    System.out.print(resultSet.getString(1));
                    System.out.print(" | ");
                    System.out.print(resultSet.getString(2));
                    System.out.print(" | ");
                    System.out.print(resultSet.getString(3));
                    System.out.print(" | ");
                    System.out.print(resultSet.getString(4));
                    System.out.print(" | ");
                    System.out.print(resultSet.getString(5));
                    System.out.print(" | ");
                    System.out.print(resultSet.getString(6));
                    System.out.print(" | ");
                    System.out.println(" ");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try{
                conn.close();
                pool.returnConnection(conn);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
