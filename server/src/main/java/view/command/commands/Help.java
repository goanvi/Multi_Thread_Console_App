package view.command.commands;

import controller.DAOManager;
import request.Request;
import response.Response;
import view.command.AbstractCommand;

import java.util.Map;


public class Help extends AbstractCommand {
    private Map<String, AbstractCommand> commandMap;

    public Help( Map<String, AbstractCommand> commandMap) {
        super("help", "выводит информацию по доступным командам");
        this.commandMap = commandMap;
    }

    @Override
    public Response execute(Request request, DAOManager manager) {
        StringBuilder stringBuilder = new StringBuilder();
        commandMap.forEach((key, value) -> stringBuilder.append(key).append("  -  ").append(value.getDescription()).append("\n"));
        return new Response(true, stringBuilder + "\n" + "Справка по командам успешно выведена");
    }

    public String getMessage() {
        return "help - Выводит информацию по доступным командам";
    }
}

