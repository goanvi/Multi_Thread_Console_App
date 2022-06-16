package view.command;

import controller.DAOManager;
import request.Request;
import response.Response;

public interface Command {
    String getDescription();

    String getName();

    Response execute(Request request, DAOManager manager);
}
