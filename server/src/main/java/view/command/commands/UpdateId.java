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
import view.command.exceptions.WrongCommandInputException;
import view.exceptions.IncorrectInputException;

import java.util.ArrayList;
import java.util.Arrays;

public class UpdateId extends AbstractCommand {
    public UpdateId() {
        super("update_id", "обновляет значение элемента коллекции, id которого равен заданному");
    }

    @Override
    public Response execute(Request request1, DAOManager manager) {
        try {
            Request<StudyGroupDTO> request = request1;
            String[] params = request.getArgument().split(",");
            Integer inputInt = Integer.parseInt(params[0].trim());
            if (!IdManager.containsStudyGroupID(inputInt)) throw new WrongCommandInputException();
            StudyGroup studyGroup = manager.getStudyGroupDAO().getById(Integer.toString(inputInt));
            ArrayList<String> parameters = new ArrayList<>(Arrays.asList(params));
            parameters.remove(0);
            for (String parameter : parameters) {
                switch (parameter.toLowerCase().trim()) {
                    case "имя":
                        studyGroup.setName(request.getDto().getName());
                        break;
                    case "координаты":
                        studyGroup.setCoordinates(new Coordinates(request.getDto().getCoordinates().getX(),
                                request.getDto().getCoordinates().getY()));
                        break;
                    case "количество студентов":
                        studyGroup.setStudentsCount(request.getDto().getStudentsCount());
                        break;
                    case "средняя оценка":
                        studyGroup.setAverageMark(request.getDto().getAverageMark());
                        break;
                    case "форма обучения":
                        studyGroup.setFormOfEducation(FormOfEducation.convert(request.getDto().getFormOfEducation().getName()));
                        break;
                    case "семестр":
                        studyGroup.setSemesterEnum(Semester.equals(request.getDto().getSemesterEnum().getName()));
                        break;
                    case "админ группы":
                        if (request.getDto().getGroupAdmin().getWeight() == 0) studyGroup.setGroupAdmin(null);
                        else studyGroup.setGroupAdmin(
                                new Person(
                                        request.getDto().getGroupAdmin().getName(),
                                        request.getDto().getGroupAdmin().getBirthday(),
                                        request.getDto().getGroupAdmin().getWeight()));
                        break;
                    default:
                        throw new IncorrectInputException();
                }
            }
            manager.getStudyGroupDAO().update(studyGroup,Integer.toString(inputInt));
            return new Response(true, "Параметры элемента успешно изменены!");
//        } catch (EmptyCollectionException exception) {
//            return new Response(true, "Коллекция пуста!");//Не уверен, что так должно быть. Пока что считаю, что пустая коллекция не повод выбрасывать ошибку выполнения
        } catch (IncorrectInputException exception) {
            return new Response(false, "Введенные данные некорректны!");
        }catch (WrongCommandInputException e){
            return new Response(false,"Элемента с таким id не существует");
        } catch (IncorrectNameEnumException e) {
            return new Response(false, "Данные введены некорректно!");
        }catch (ExecuteCommandException e){
            return new Response(false,"Непредвиденная ошибка");
        }
    }

    public String getMessage() {
        return "update id {element} - Обновляет значение элемента коллекции, id которого равен заданному";
    }
}
