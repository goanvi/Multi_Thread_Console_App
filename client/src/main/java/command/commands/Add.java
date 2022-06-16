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

public class Add extends AbstractCommand {
    private Asker asker;
    private Communicate communicate;

    public Add(Asker asker, Communicate communicate) {
        super("add", "Добавляет новый элемент в коллекцию");
        this.asker = asker;
        this.communicate = communicate;
    }

    @Override
    public boolean execute(String argument) throws IncorrectScriptException {
        Request request = null;
        try {
            if (argument.isEmpty()) {
                StudyGroupDTO groupDTO = new StudyGroupDTO(
                        asker.askName(),
                        asker.askCoordinates(),
                        asker.askStudentsCount(),
                        asker.askAverageMark(),
                        asker.askFromOfEducation(),
                        asker.askSemester(),
                        asker.askPerson());
                request = new Request(groupDTO, "add", null, Authenticator.getOwnerId());
                communicate.send(request);
                Response response = communicate.get();
                ConsoleClient.println("\n" + response.getText());
                return response.getAnswer();
            } else throw new WrongCommandInputException();
        } catch (WrongCommandInputException exception) {
            ConsoleClient.printError("Команда " + getName() + " введена с ошибкой: " +
                    "команда не должна содержать символы после своего названия!");
            if (Asker.getFileMode()) throw new IncorrectScriptException();
        }
        return false;
    }

    public String getMessage() {
        return "addCommand {element} - Добавляет новый элемент в коллекцию";
    }
}
