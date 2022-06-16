package controller;

import request.Request;
import response.Response;
import view.command.AbstractCommand;
import view.command.commands.*;

import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    Map<String, AbstractCommand> commandMap = new HashMap<>();

    public CommandManager() {
        commandMap.put("add", new Add());
        commandMap.put("clear", new Clear());
        commandMap.put("filter_less_than_students_count", new FilterLessThanStudentsCount());
        commandMap.put("help", new Help(commandMap));
        commandMap.put("history", new History());
        commandMap.put("exit", new ClientExit());
        commandMap.put("info", new Info());
        commandMap.put("remove_any_by_semester_enum", new RemoveAnyBySemesterEnum());
        commandMap.put("remove_by_id", new RemoveById());
        commandMap.put("remove_greater", new RemoveGreater());
        commandMap.put("remove_lower", new RemoveLower());
        commandMap.put("show", new Show());
        commandMap.put("sum_of_students_count", new SumOfStudentsCount());
        commandMap.put("update_id", new UpdateId());
    }

    public Response callCommand(Request request, DAOManager manager) throws SocketException {
        if (request == null) throw new SocketException("Client is disconnected");
        Response response = commandMap.get(request.getName()).execute(request,manager);
        if (response.getAnswer()) History.addToHistory(request.getName());
        return response;
    }

    public Map<String, AbstractCommand> getCommands() {
        return commandMap;
    }
}
