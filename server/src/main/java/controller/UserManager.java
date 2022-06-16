package controller;

import response.Response;

import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static Map<SocketChannel, Response> responseMap = new HashMap<>();
    private static Map<SocketChannel, Connection> connectionMap = new HashMap<>();
    private static Map<SocketChannel,DAOManager> daoMap = new HashMap<>();

    public static Response getResponse(SocketChannel channel){
        return responseMap.get(channel);
    }

    public static Connection getConnection(SocketChannel channel){
        return connectionMap.get(channel);
    }

    public static DAOManager getDAOManager(SocketChannel channel){
        return daoMap.get(channel);
    }

    public static void addResponse (SocketChannel socketChannel, Response response){
        responseMap.put(socketChannel,response);
    }

    public static void addConnection (SocketChannel socketChannel, Connection connection){
        connectionMap.put(socketChannel,connection);
    }

    public static void addDao (SocketChannel socketChannel,DAOManager manager){
        daoMap.put(socketChannel,manager);
    }

    public static void removeConnection (SocketChannel socketChannel){
        connectionMap.remove(socketChannel);
    }

    public static void removeResponse (SocketChannel socketChannel){
        responseMap.remove(socketChannel);
    }

    public static void removeDao (SocketChannel socketChannel){
        daoMap.remove(socketChannel);
    }
}
