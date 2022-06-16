package view.command.commands;

import controller.DAOManager;
import model.StudyGroup;
import request.Request;
import response.Response;
import view.command.AbstractCommand;
import view.command.exceptions.ExecuteCommandException;

import java.util.TreeSet;

public class SumOfStudentsCount extends AbstractCommand {

    public SumOfStudentsCount() {
        super("sum_of_students_count", "выводит сумму значений поля studentsCount для всех элементов коллекции");
    }

    @Override
    public Response execute(Request request, DAOManager manager) {
        String out;
        try {
            TreeSet<StudyGroup> groups = manager.getStudyGroupDAO().getByOwner(request.getOwnerId());//Peredelat'
            long sum = groups.stream().mapToLong(StudyGroup::getStudentsCount).sum();
            out = "Общее количество студентов: " + sum + "\n" +
                    "Количество студентов успешно выведено!";
            return new Response(true, out);
//        } catch (EmptyCollectionException exception) {
//            return new Response(true, "Коллекция пуста!");//Не уверен, что так должно быть.
//            // Пока что считаю, что пустая коллекция не повод выбрасывать ошибку выполнения
        }catch (ExecuteCommandException e){
            return new Response(false,"Непредвиденная ошибка");
        }
    }

    public String getMessage() {
        return "sum_of_students_count - Выводит сумму значений поля studentsCount для всех элементов коллекции";
    }
}
