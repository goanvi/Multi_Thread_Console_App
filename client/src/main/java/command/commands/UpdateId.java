package command.commands;

import client.Communicate;
import command.AbstractCommand;
import command.exceptions.WrongCommandInputException;
import console.ConsoleClient;
import dto.StudyGroupDTO;
import exceptions.IncorrectScriptException;
import request.Request;
import response.Response;
import utility.Asker;
import utility.Authenticator;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class UpdateId extends AbstractCommand {
    Asker asker;
    ConsoleClient consoleClient;
    Communicate communicate;
    String[] parameters;

    public UpdateId(Asker asker, Communicate communicate, ConsoleClient consoleClient) {
        super("update_id", "Обновляет значение элемента коллекции, id которого равен заданному");
        this.asker = asker;
        this.consoleClient = consoleClient;
        this.communicate = communicate;
    }

    private boolean setNewParameters(StudyGroupDTO studyGroup) throws IncorrectScriptException {
        try {
            ConsoleClient.println("Какие параметры группы вы хотите изменить?\n" +
                    "Имя\n" +
                    "Координаты\n" +
                    "Количество студентов\n" +
                    "Средняя оценка\n" +
                    "Форма обучения\n" +
                    "Семестр\n" +
                    "Админ группы");
            ConsoleClient.println("Запишите все изменяемые параметры в строчку через запятую");
            parameters = consoleClient.readLine().split(",");
            return asker.changeParameters(parameters, studyGroup);
        } catch (NoSuchElementException exception) {
            ConsoleClient.printError("Значение поля не распознано!");
            if (Asker.getFileMode()) throw new IncorrectScriptException();
        } catch (IllegalStateException exception) {
            ConsoleClient.printError("Непредвиденная ошибка!");
            System.exit(0);
        }
        return false;
    }

    @Override
    public boolean execute(String argument) throws IncorrectScriptException {
        Request request = null;
        try {
            if (!argument.isEmpty()) {
                StudyGroupDTO groupDTO = new StudyGroupDTO();
                while (true) {
                    if (setNewParameters(groupDTO)) {
                        break;
                    }
                }
                String out = Arrays.toString(parameters).replaceAll("[\\[\\]]", "");
                request = new Request(groupDTO, "update_id", argument + "," + out, Authenticator.getOwnerId());
                communicate.send(request);
                Response response = communicate.get();
                ConsoleClient.println("\n" + response.getText());
                return response.getAnswer();
            } else throw new WrongCommandInputException();
        } catch (WrongCommandInputException exception) {
            ConsoleClient.printError("Команда " + getName() + " введена с ошибкой: " +
                    "команда не должна содержать символы после своего названия!");
            if (Asker.getFileMode()) throw new IncorrectScriptException();
        } catch (NoSuchElementException exception) {
            ConsoleClient.printError("Значение поля не распознано!");
            if (Asker.getFileMode()) throw new IncorrectScriptException();
        } catch (IllegalStateException exception) {
            ConsoleClient.printError("Непредвиденная ошибка!");
            System.exit(0);
        }
        return false;
    }

    public String getMessage() {
        return "update id {element} - Обновляет значение элемента коллекции, id которого равен заданному";
    }
}
