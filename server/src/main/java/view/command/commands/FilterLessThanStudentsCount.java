package view.command.commands;

import controller.DAOManager;
import model.StudyGroup;
import request.Request;
import response.Response;
import view.command.AbstractCommand;
import view.command.exceptions.ExecuteCommandException;
import view.utility.Formatter;

import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class FilterLessThanStudentsCount extends AbstractCommand {

    public FilterLessThanStudentsCount() {
        super("filter_less_than_students_count", "выводит элементы, значение поля studentsCount" +
                " которых меньше заданного");
    }

    @Override
    public Response execute(Request request, DAOManager manager) {
        long studCount;
        StringBuilder strOut = new StringBuilder();
        try {
            studCount = Long.parseLong(request.getArgument().trim());
            TreeSet<StudyGroup> output = manager.getStudyGroupDAO().getByOwner(request.getOwnerId());//gjghfdbnm
            if (output.isEmpty()) return new Response(true, "Во всех группах количество человек больше");
            else {
                List<StudyGroup> out = output.stream().filter(group -> group.getStudentsCount() < studCount).collect(Collectors.toList());
                out.forEach((group) -> strOut.append(Formatter.format(group)).append("\n"));
                return new Response(true, strOut + "Элементы коллекции успешно выведены!");
            }
//        } catch (EmptyCollectionException exception) {
//            return new Response(true, "Коллекция пуста!");//Не уверен, что так должно быть. Пока что считаю, что пустая коллекция не повод выбрасывать ошибку выполнения
        } catch (NumberFormatException exception) {
            return new Response(false, "Значением поля должно являться число!");
        } catch (ExecuteCommandException e){
            return new Response(false,"Непредвиденная ошибка");
        }
    }

    public String getMessage() {
        return "filter_less_than_students_count studentsCount - Выводит элементы, значение поля studentsCount которых меньше заданного";
    }
}
