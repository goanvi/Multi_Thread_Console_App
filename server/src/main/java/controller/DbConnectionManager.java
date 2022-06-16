package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

public class DbConnectionManager {
    Queue<Connection> connectionQueue = new LinkedList<>();
    private final int CONNECTION_POOL_SIZE = 10;

    public DbConnectionManager(){
        while (connectionQueue.size() < CONNECTION_POOL_SIZE){
            connectionQueue.add(connect());
        }

    }

    private Connection connect(){
        String url = "jdbc:postgresql://pg:5432/studs";
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, "s336497", "ybi556");
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Connection ERROR");
            return null;
        }
    }

    public int size(){
        return connectionQueue.size();
    }

    public Connection getConnection(){
        return connectionQueue.poll();
    }

    public void addCollection(Connection connection){
        connectionQueue.add(connection);
    }
}
