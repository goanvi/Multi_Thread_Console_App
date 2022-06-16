package view.command.commands;

import controller.DAOManager;
import controller.IdManager;
import model.StudyGroup;
import request.Request;
import response.Response;
import view.command.AbstractCommand;
import view.command.exceptions.ExecuteCommandException;
import view.exceptions.IncorrectInputException;

public class RemoveById extends AbstractCommand {

    public RemoveById() {
        super("remove_by_id", "удаляет элемент из коллекции по его id");
    }

    @Override
    public Response execute(Request request, DAOManager manager) {
        try {
            int id = Integer.parseInt(request.getArgument().trim());
            StudyGroup studyGroup = manager.getStudyGroupDAO().getById(Integer.toString(id));
            if (request.getOwnerId()!=studyGroup.getOwnerId()) throw new IllegalArgumentException();
            if (!IdManager.containsStudyGroupID(id)) throw new IncorrectInputException();
            manager.getStudyGroupDAO().deleteById(Integer.toString(id));
            IdManager.removeStudyGroupID(id);
            return new Response(true, "Элемент успешно удален!");
        } catch (NumberFormatException exception) {
            return new Response(false, "Значением поля должно являться число!");
        } catch (IllegalArgumentException e){
            return new Response(false,"Вы не можете удалить этот элемент");
        } catch (IncorrectInputException exception) {
            return new Response(false, "Такого id не существует!");
        } catch (ExecuteCommandException e){
            return new Response(false,"Непредвиденная ошибка");
        }
    }

    public String getMessage() {
        return "remove_by_id  - Удаляет элемент из коллекции по его id";
    }
}
