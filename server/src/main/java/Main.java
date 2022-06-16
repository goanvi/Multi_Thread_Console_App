import server.Server;

public class Main {

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
//        FileWorker fileWorker = new FileWorker(System.getenv("LABA5"));
//        CollectionManager collectionManager = new CollectionManager(System.getenv("LABA5"));
//        Map<String, AbstractCommand> commandMap = new HashMap<>();
//        commandMap.put("add", new Add());
//        commandMap.put("clear", new Clear());
//        commandMap.put("filter_less_than_students_count", new FilterLessThanStudentsCount());
//        commandMap.put("help", new Help(commandMap));
//        commandMap.put("history", new History());
//        commandMap.put("exit", new ClientExit());
//        commandMap.put("info", new Info());
//        commandMap.put("remove_any_by_semester_enum", new RemoveAnyBySemesterEnum());
//        commandMap.put("remove_by_id", new RemoveById());
//        commandMap.put("remove_greater", new RemoveGreater());
//        commandMap.put("remove_lower", new RemoveLower());
//        commandMap.put("show", new Show());
//        commandMap.put("sum_of_students_count", new SumOfStudentsCount());
//        commandMap.put("update_id", new UpdateId());
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    String input = System.console().readLine().trim();
//                    if (input.equalsIgnoreCase("save")) new Save(collectionManager, fileWorker).execute(null);
//                    if (input.equalsIgnoreCase("exit")) {
//                        new ServerExit(collectionManager, fileWorker).execute(null);
//
//                    }
//                }
//            }
//        }).start();
//        CommandManager commandManager = new CommandManager();
//        Connect.open();
//        Connect.start();
//        ServerSocketChannel serverSocketChannel = Connect.getServer();
//        ExecutorService handler = Executors.newCachedThreadPool();
//        ExecutorService executor = Executors.newFixedThreadPool(10);
//        ExecutorService sandler = Executors.newFixedThreadPool(10);
//        DbConnectionManager connectionManager = new DbConnectionManager();
//        Set<SocketChannel> socketSet = new HashSet<>();
//        try {
//            Selector selector = Selector.open();
//            serverSocketChannel.configureBlocking(false);
//            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
//            while (true) {
//                selector.select();
//                Set<SelectionKey> keys = selector.selectedKeys();
//                Iterator<SelectionKey> iterator = keys.iterator();
//                while (iterator.hasNext()) {
//                    SelectionKey key = iterator.next();
//                    if (key.isValid()) {
//                        if (key.isAcceptable()) {
//                            SocketChannel sock = serverSocketChannel.accept();
//                            if (connectionManager.size() == 0) {
//                                socketSet.add(sock);
//                            } else {
//                                Connection connection = connectionManager.getConnection();
//                                UserManager.addConnection(sock, connection);
//                                UserManager.addDao(sock, new DAOManager(connection));
//                            }
//                            sock.configureBlocking(false);
//                            sock.register(key.selector(), OP_READ);
//                        }
//                        if (key.isReadable()) {
//                            SocketChannel client = (SocketChannel) key.channel();
//                            if (socketSet.contains(client)) {
//                                if (connectionManager.size()!=0){
//                                Connection connection = connectionManager.getConnection();
//                                UserManager.addConnection(client, connection);
//                                UserManager.addDao(client, new DAOManager(connection));
//                                socketSet.remove(client);
//                                }else{
//                                    iterator.remove();
//                                    continue;
//                                }
//                            }
//                            Request request;
//                            try {
//                                Future<Request> future = handler.submit(new RequestHandler(key));
//                                request = future.get();
//                            } catch (ExecutionException e) {
//                                key.cancel();
//                                Connection connection = UserManager.getConnection(client);
//                                connectionManager.addCollection(connection);
//                                UserManager.removeConnection(client);
//                                UserManager.removeDao(client);
//                                UserManager.removeResponse(client);
//                                break;
//                            }
//                            Future<Response> responseFuture = executor.submit(() -> {
//                                Authentication authentication = new Authentication(UserManager.getDAOManager(client).getUserDAO());
//                                if (request.getName().equalsIgnoreCase("login")) {
//                                    Request<UserDTO> request1 = request;
//                                    return authentication.login(request1.getDto());
//                                } else if (request.getName().equalsIgnoreCase("register")) {
//                                    Request<UserDTO> request1 = request;
//                                    return authentication.register(request1.getDto());
//                                } else return commandManager.callCommand(request, UserManager.getDAOManager(client));
//                            });
//                            Response response = responseFuture.get();
//                            UserManager.addResponse(client, response);
//                            if (key.isValid()) {
//                                client.configureBlocking(false);
//                                client.register(key.selector(), OP_WRITE);
//                            } else {
//                                System.out.println("xui");
//                                key.cancel();
//                            }
//                        }
//                        if (key.isWritable()) {
//                            SocketChannel client = (SocketChannel) key.channel();
//                            sandler.execute(new ResponseSandler(key, connectionManager));
//                            if (key.isValid()) {
//                                client.configureBlocking(false);
//                                client.register(key.selector(), OP_READ);
//                            }
//                        }
//                        iterator.remove();
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }


}