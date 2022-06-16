package view.command;

import controller.CollectionManager;
import controller.FileWorker;
import response.Response;
import server.Connect;

public class ServerExit {
    CollectionManager collectionManager;
    FileWorker fileWorker;

    public ServerExit() {
    }

    public Response execute() {
        try {
            if (Connect.getSocket() != null) Connect.close();
            System.exit(0);
            return null;

        } catch (SecurityException exception) {
            System.out.println("Ошибка прав доступа к файлу!");
            return new Response(false, "Ошибка прав доступа к файлу!");
        }
    }
}
