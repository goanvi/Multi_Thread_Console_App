package server;

import controller.*;
import dto.UserDTO;
import request.Request;
import response.Response;
import view.command.ServerExit;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.nio.channels.SelectionKey.OP_READ;
import static java.nio.channels.SelectionKey.OP_WRITE;

public class Server {
    CommandManager commandManager;
    ServerSocketChannel serverSocketChannel;
    ExecutorService handler;
    ExecutorService executor;
    ExecutorService sandler;
    DbConnectionManager connectionManager;
    Set<SocketChannel> socketSet = new HashSet<>();

    public Server() {
        this.commandManager = new CommandManager();
        Connect.open();
        Connect.start();
        System.out.println("Порт: " + Connect.getPort());
        this.serverSocketChannel = Connect.getServer();
        this.handler = Executors.newCachedThreadPool();
        this.executor = Executors.newFixedThreadPool(10);
        this.sandler = Executors.newFixedThreadPool(10);
        this.connectionManager = new DbConnectionManager();
    }

    public void start() {
        initScript();
        serverThread();
        try {
            Selector selector = Selector.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isValid()) {
                        if (key.isAcceptable()) {
                            SocketChannel sock = serverSocketChannel.accept();
                            if (connectionManager.size() == 0) {
                                socketSet.add(sock);
                            } else {
                                Connection connection = connectionManager.getConnection();
                                UserManager.addConnection(sock, connection);
                                UserManager.addDao(sock, new DAOManager(connection));
                            }
                            sock.configureBlocking(false);
                            sock.register(key.selector(), OP_READ);
                        }
                        if (key.isReadable()) {
                            SocketChannel client = (SocketChannel) key.channel();
                            if (socketSet.contains(client)) {
                                if (connectionManager.size() != 0) {
                                    Connection connection = connectionManager.getConnection();
                                    UserManager.addConnection(client, connection);
                                    UserManager.addDao(client, new DAOManager(connection));
                                    socketSet.remove(client);
                                } else {
                                    iterator.remove();
                                    continue;
                                }
                            }
                            Request request;
                            try {
                                Future<Request> future = handler.submit(new RequestHandler(key));
                                request = future.get();
                            } catch (ExecutionException e) {
                                key.cancel();
                                Connection connection = UserManager.getConnection(client);
                                connectionManager.addCollection(connection);
                                UserManager.removeConnection(client);
                                UserManager.removeDao(client);
                                UserManager.removeResponse(client);
                                break;
                            }
                            Future<Response> responseFuture = executor.submit(() -> {
                                Authentication authentication = new Authentication(UserManager.getDAOManager(client).getUserDAO());
                                if (request.getName().equalsIgnoreCase("login")) {
                                    Request<UserDTO> request1 = request;
                                    return authentication.login(request1.getDto());
                                } else if (request.getName().equalsIgnoreCase("register")) {
                                    Request<UserDTO> request1 = request;
                                    return authentication.register(request1.getDto());
                                } else return commandManager.callCommand(request, UserManager.getDAOManager(client));
                            });
                            Response response = responseFuture.get();
                            UserManager.addResponse(client, response);
                            if (key.isValid()) {
                                client.configureBlocking(false);
                                client.register(key.selector(), OP_WRITE);
                            } else {
                                key.cancel();
                            }
                        }
                        if (key.isWritable()) {
                            SocketChannel client = (SocketChannel) key.channel();
                            sandler.execute(new ResponseSandler(key, connectionManager));
                            if (key.isValid()) {
                                client.configureBlocking(false);
                                client.register(key.selector(), OP_READ);
                            }
                        }
                        iterator.remove();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initScript() {
        Connection connection = connectionManager.getConnection();
        String sql = "CREATE TYPE FormOfEducation AS ENUM ('Дистанционно','Очно','Вечер');\n" +
                "\n" +
                "CREATE TYPE  Semester AS ENUM ('Три','Пять','Семь') ;"
                + "CREATE TABLE IF NOT EXISTS Users" +
                "(" +
                "    id       SERIAL PRIMARY KEY UNIQUE,\n" +
                "    login    VARCHAR(100) NOT NULL,\n" +
                "    password VARCHAR(100) NOT NULL,\n" +
                "    salt     VARCHAR(100) NOT NULL\n" +
                ");" +
                "CREATE TABLE IF NOT EXISTS Persons\n" +
                "(\n" +
                "    id         SERIAL PRIMARY KEY UNIQUE,\n" +
                "    name       VARCHAR(100) NOT NULL,\n" +
                "    birthday   TIMESTAMP,\n" +
                "    weight     FLOAT CHECK ( weight > 0) NOT NULL ,\n" +
                "    passportID VARCHAR(33) NOT NULL\n" +
                ");" +
                "CREATE TABLE IF NOT EXISTS Coordinates\n" +
                "(\n" +
                "    id    SERIAL PRIMARY KEY UNIQUE,\n" +
                "    X     INT          NOT NULL CHECK (X < 812),\n" +
                "    Y     INT          NOT NULL\n" +
                ");" +
                "CREATE TABLE IF NOT EXISTS StudyGroups\n" +
                "(\n" +
                "    id              SERIAL PRIMARY KEY UNIQUE,\n" +
                "    ownerId         INT REFERENCES users (id)                  NOT NULL,\n" +
                "    name            VARCHAR(100)                               NOT NULL,\n" +
                "    coordinates     INT REFERENCES coordinates (id)            NOT NULL,\n" +
                "    creationDate    DATE                                       NOT NULL,\n" +
                "    studentsCount   BIGINT CHECK ( studentsCount > 0 )         NOT NULL,\n" +
                "    averageMark     DOUBLE PRECISION CHECK ( averageMark > 0 ) NOT NULL,\n" +
                "    formOfEducation FormOfEducation                            NOT NULL,\n" +
                "    semester        Semester                                   NOT NULL,\n" +
                "    person          INT REFERENCES persons (id)\n" +
                ");";
        try {
            Statement st = connection.createStatement();
            st.execute(sql);
        } catch (SQLException ignored) {
        }
        connectionManager.addCollection(connection);

    }

    private void serverThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String input = System.console().readLine().trim();
                    if (input.equalsIgnoreCase("exit")) {
                        new ServerExit().execute();

                    }
                }
            }
        }).start();
    }

}
