package view.command.commands;

import controller.DAOManager;
import controller.IdManager;
import dto.StudyGroupDTO;
import model.*;
import model.Exceptions.IncorrectNameEnumException;
import request.Request;
import response.Response;
import view.command.AbstractCommand;
import view.command.exceptions.ExecuteCommandException;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class RemoveGreater extends AbstractCommand {

    public RemoveGreater() {
        super("remove_greater", "удалить из коллекции все элементы, превышающие заданный");
    }

    @Override
    public Response execute(Request request, DAOManager manager) {
        StudyGroup studyGroup;
        try {
            StudyGroupDTO groupDTO = (StudyGroupDTO) request.getDto();
            if (groupDTO.getGroupAdmin().getWeight() == 0) {
                studyGroup = new StudyGroup(
                        groupDTO.getName(),
                        new Coordinates(groupDTO.getCoordinates().getX(), groupDTO.getCoordinates().getY()),
                        groupDTO.getStudentsCount(),
                        groupDTO.getAverageMark(),
                        FormOfEducation.convert(groupDTO.getFormOfEducation().getName()),
                        Semester.equals(groupDTO.getSemesterEnum().getName()),
                        null);
            } else {
                studyGroup = new StudyGroup(
                        groupDTO.getName(),
                        new Coordinates(groupDTO.getCoordinates().getX(), groupDTO.getCoordinates().getY()),
                        groupDTO.getStudentsCount(),
                        groupDTO.getAverageMark(),
                        FormOfEducation.convert(groupDTO.getFormOfEducation().getName()),
                        Semester.equals(groupDTO.getSemesterEnum().getName()),
                        new Person(groupDTO.getGroupAdmin().getName(), groupDTO.getGroupAdmin().getBirthday(), groupDTO.getGroupAdmin().getWeight()));
            }
            TreeSet<StudyGroup> groups = manager.getStudyGroupDAO().getByOwner(request.getOwnerId());//Переделать
            Set<StudyGroup> removeGroups = groups.stream().filter((group) -> group.compareTo(studyGroup) > 0).collect(Collectors.toSet());
            removeGroups.forEach((group) -> {
                try {
                    manager.getStudyGroupDAO().deleteById(Integer.toString(group.getID()));
                } catch (view.command.exceptions.ExecuteCommandException e) {
                    e.printStackTrace();
                }
                IdManager.removeStudyGroupID(group.getID());
            });
            return new Response(true, "Все элементы больше заданного удалены!");
//        } catch (EmptyCollectionException exception) {
//            return new Response(true, "Коллекция пуста!");//Не уверен, что так должно быть. Пока что считаю, что пустая коллекция не повод выбрасывать ошибку выполнения
        } catch (IncorrectNameEnumException e) {
            return new Response(false, "Данные введены некорректно!");
        } catch (ExecuteCommandException e){
            return new Response(false,"Непредвиденная ошибка");
        }
    }

    public String getMessage() {
        return "remove_any_by_semester_enum semesterEnum - Удалит из коллекции один элемент, значение поля semesterEnum которого эквивалентно заданному";
    }
}
