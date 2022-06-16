package view.command.commands;

import controller.DAOManager;
import controller.IdManager;
import model.Exceptions.IncorrectNameEnumException;
import model.Semester;
import model.StudyGroup;
import request.Request;
import response.Response;
import view.command.AbstractCommand;
import view.command.exceptions.ExecuteCommandException;
import view.exceptions.IncorrectInputException;

import java.util.Optional;
import java.util.TreeSet;

public class RemoveAnyBySemesterEnum extends AbstractCommand {

    public RemoveAnyBySemesterEnum() {
        super("remove_any_by_semester_enum {Три, Пять, Семь}", "удаляет из коллекции один элемент," +
                " значение поля semesterEnum которого эквивалентно заданному");
    }

    @Override
    public Response execute(Request request, DAOManager manager) {
        try {
            Semester semester = Semester.equals(request.getArgument().trim());
            TreeSet<StudyGroup> groups = manager.getStudyGroupDAO().getByOwner(request.getOwnerId());//переделать
            Optional<StudyGroup> output = groups.stream().filter((group) -> group.getSemesterEnum().equals(semester)).findAny();
            StudyGroup group = output.orElse(null);
            if (group == null) throw new IncorrectInputException();
            manager.getStudyGroupDAO().deleteById(Integer.toString(group.getID()));
            IdManager.removeStudyGroupID(group.getID());
            return new Response(true, "Элемент успешно удален!");
//        } catch (EmptyCollectionException exception) {
//            return new Response(true, "Коллекция пуста!");//Не уверен, что так должно быть. Пока что считаю, что пустая коллекция не повод выбрасывать ошибку выполнения
        } catch (IncorrectNameEnumException exception) {
            return new Response(false, "Семестр обучения введен неверно!");
        } catch (IncorrectInputException e) {
            return new Response(false, "Объекта с таким семестром обучения не найдено!");
        } catch (ExecuteCommandException e){
            return new Response(false,"Непредвиденная ошибка");
        }
    }

    public String getMessage() {
        return "remove_any_by_semester_enum semesterEnum - Удаляет из коллекции один элемент," +
                " значение поля semesterEnum которого эквивалентно заданному";
    }
}
