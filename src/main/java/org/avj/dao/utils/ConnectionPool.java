package org.avj.dao.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class ConnectionPool {
    private String dataBaseUrl;
    private String userName;
    private String password;

    private int maxPoolSize = 10;
    private int connNum = 0;

    private static final String SQL_VERIFYCONN="select_1";

    Stack<Connection> freePool = new Stack<>();
    Set<Connection> occupedPool = new HashSet<>();

    public ConnectionPool(String dataBaseUrl, String userName, String password, int maxPoolSize) {
        this.dataBaseUrl = dataBaseUrl;
        this.userName = userName;
        this.password = password;
        this.maxPoolSize = maxPoolSize;
    }

    public synchronized Connection getConnection() throws SQLException {
        Connection conn = null;
        if(isFull()){
            throw new SQLException("The connection pool is full");
        }
        conn = getConnectionFromPool();
        if (conn == null){
            conn = createNewConnectionForOccupedPool();
        }
        conn = makeAvailable(conn);
        return conn;
    }

    public synchronized void returnConnection(Connection conn) throws SQLException {
        if (conn == null){
            throw new NullPointerException();
        }
        if (!occupedPool.remove(conn)){
            throw new SQLException("The connection is returned alredy or it isn`t for this pool");
        }
        freePool.push(conn);
    }

    private synchronized boolean isFull(){
        return ((freePool.size()==0) && (connNum>=maxPoolSize));
    }

    private Connection createNewConnectionForOccupedPool() throws SQLException {
        Connection conn = createNewConnection();
        connNum++;
        occupedPool.add(conn);
        return conn;
    }

    private Connection createNewConnection() throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection(dataBaseUrl,userName,password);
        return conn;
    }

    private Connection getConnectionFromPool(){
        Connection conn = null;
        if(freePool.size()>0){
            conn = freePool.pop();
            occupedPool.add(conn);
        }
        return conn;
    }

    private Connection makeAvailable(Connection conn) throws SQLException {
        if(isConnectionAvailable(conn)){
            return conn;
        }
        occupedPool.remove(conn);
        connNum--;
        conn.close();

        conn = createNewConnection();
        occupedPool.add(conn);
        connNum++;
        return conn;
    }

    private boolean isConnectionAvailable(Connection conn){
        try(Statement statement = conn.createStatement()){
            statement.executeQuery(SQL_VERIFYCONN);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
