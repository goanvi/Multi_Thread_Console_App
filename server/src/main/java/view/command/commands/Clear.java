package view.command.commands;

import controller.DAOManager;
import request.Request;
import response.Response;
import view.command.AbstractCommand;
import view.command.exceptions.ExecuteCommandException;

public class Clear extends AbstractCommand {

    public Clear() {
        super("clear", "очищает коллекцию");
    }

    @Override
    public Response execute(Request request, DAOManager manager) {
        try {
            manager.getStudyGroupDAO().deleteByOwner(Integer.toString(request.getOwnerId()));
        } catch (ExecuteCommandException e){
            return new Response(false,"Непредвиденная ошибка");
        }
        return new Response(true, "Очистка коллекции успешно проведена!");
    }

    public String getMessage() {
        return "clear - Очищает коллекцию";
    }
}
