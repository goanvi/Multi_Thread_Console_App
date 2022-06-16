package server;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Connect {
    static SocketChannel socket;
    static ServerSocketChannel server;
    static int port = 5576;
    static InetSocketAddress inetSocketAddress;

    public Connect() {
    }

    public static void open() {
        try {
            server = ServerSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void start() {
        try {
            inetSocketAddress = new InetSocketAddress(port);
            server.bind(inetSocketAddress);
        } catch (BindException exception) {
            System.out.println("Порт занят");
            while (true) {
                try {
                    System.out.print("Введите порт:");
                    setPort(Integer.parseInt(System.console().readLine()));
                    break;
                } catch (NumberFormatException ex) {
                    System.out.println("Введите число!");
                }
            }
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void connect() {
        try {
            socket = server.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SocketChannel getSocket() {
        return socket;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        Connect.port = port;
    }

    public static ServerSocketChannel getServer() {
        return server;
    }

    public static InetSocketAddress getInetSocketAddress() {
        return inetSocketAddress;
    }
}
