package view.command.commands;

import controller.DAOManager;
import request.Request;
import response.Response;
import view.command.AbstractCommand;

import java.util.LinkedList;

public class History extends AbstractCommand {
    static private final int HISTORY_BUFFER_SIZE = 11;
    static LinkedList<String> buf = new LinkedList<>();

    public History() {
        super("history", "выводит последние 11 команд (без их аргументов)");
    }

    public static void addToHistory(String command) {
        if (buf.size() > HISTORY_BUFFER_SIZE) {
            buf.addFirst(command);
            buf.removeLast();
        } else buf.addFirst(command);
    }

    @Override
    public Response execute(Request request, DAOManager manager) {
        StringBuilder stringBuilder = new StringBuilder();
        if (buf.isEmpty()) return new Response(true, "Еще не было реализованно ни одной команды!");
        buf.forEach((command) -> stringBuilder.append(command).append("\n"));
        return new Response(true, stringBuilder + "\n" + "История команд успешно выведена!");
    }

    public String getMessage() {
        return "history - Выводит последние 11 команд (без их аргументов)";
    }
}
