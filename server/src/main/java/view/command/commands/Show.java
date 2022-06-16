package view.command.commands;

import controller.DAOManager;
import model.StudyGroup;
import request.Request;
import response.Response;
import view.command.AbstractCommand;
import view.command.exceptions.ExecuteCommandException;
import view.utility.Formatter;

import java.util.TreeSet;

public class Show extends AbstractCommand {

    public Show() {
        super("show", "выводит в стандартный поток вывода все элементы коллекции в строковом представлении");
    }

    @Override
    public Response execute(Request request, DAOManager manager) {
        StringBuilder stringBuilder = new StringBuilder();
        TreeSet<StudyGroup> studyGroups = null;
        try {
            studyGroups = manager.getStudyGroupDAO().getAll("");
        } catch (ExecuteCommandException e){
            return new Response(false,"Непредвиденная ошибка");
        }

        if (studyGroups.isEmpty()) {
            return new Response(true, "Коллекция пуста!");
        } else {
            studyGroups.forEach((group) -> stringBuilder.append(Formatter.format(group)).append("\n"));
        }
        return new Response(true, stringBuilder + "Коллекция успешно выведена!");
    }

    public String getMessage() {
        return "show - Выводит в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
