package view.command.commands;

import controller.DAOManager;
import dto.StudyGroupDTO;
import model.*;
import model.Exceptions.IncorrectNameEnumException;
import request.Request;
import response.Response;
import view.command.AbstractCommand;
import view.command.exceptions.ExecuteCommandException;

public class Add extends AbstractCommand {

    public Add() {
        super("add", "добавляет новый элемент в коллекцию");
    }

    @Override
    public Response execute(Request request, DAOManager manager) {
        try {
            StudyGroupDTO groupDTO = (StudyGroupDTO) request.getDto();
            if (groupDTO.getGroupAdmin().getWeight() == 0) {
                StudyGroup group = new StudyGroup(request.getOwnerId(),
                        groupDTO.getName(),
                        new Coordinates(groupDTO.getCoordinates().getX(), groupDTO.getCoordinates().getY()),
                        groupDTO.getStudentsCount(),
                        groupDTO.getAverageMark(),
                        FormOfEducation.convert(groupDTO.getFormOfEducation().getName()),
                        Semester.equals(groupDTO.getSemesterEnum().getName()),
                        null);
                manager.getStudyGroupDAO().create(group);
            } else {
                StudyGroup group = new StudyGroup(request.getOwnerId(),
                        groupDTO.getName(),
                        new Coordinates(groupDTO.getCoordinates().getX(), groupDTO.getCoordinates().getY()),
                        groupDTO.getStudentsCount(),
                        groupDTO.getAverageMark(),
                        FormOfEducation.convert(groupDTO.getFormOfEducation().getName()),
                        Semester.equals(groupDTO.getSemesterEnum().getName()),
                        new Person(groupDTO.getGroupAdmin().getName(), groupDTO.getGroupAdmin().getBirthday(), groupDTO.getGroupAdmin().getWeight()));
                manager.getStudyGroupDAO().create(group);
            }
            return new Response(true, "Группа успешно создана!");
        } catch (IncorrectNameEnumException e) {
            return new Response(false, "Данные введены некорректно!");
        } catch (ExecuteCommandException e){
            return new Response(false,"Непредвиденная ошибка");
        }
    }

    public String getMessage() {
        return "addCommand {element} - Добавляет новый элемент в коллекцию";
    }
}
