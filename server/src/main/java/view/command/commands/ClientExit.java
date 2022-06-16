package view.command.commands;

import controller.DAOManager;
import request.Request;
import response.Response;
import view.command.AbstractCommand;

public class ClientExit extends AbstractCommand {
    public ClientExit() {
        super("exit", "завершает программу (без сохранения в файл)");
    }

    @Override
    public Response execute(Request request, DAOManager manager) {
        return new Response(true, "exit");
    }

    public String getMessage() {
        return "exit - Завершает программу (без сохранения в файл)";
    }
}
