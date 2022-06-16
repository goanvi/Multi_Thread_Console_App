package command.commands;

import client.Communicate;
import command.AbstractCommand;
import command.exceptions.WrongCommandInputException;
import console.ConsoleClient;
import exceptions.IncorrectScriptException;
import request.Request;
import response.Response;
import utility.Asker;
import utility.Authenticator;

public class SumOfStudentsCount extends AbstractCommand {
    Communicate communicate;

    public SumOfStudentsCount(Communicate communicate) {
        super("sum_of_students_count", "Выводит сумму значений поля studentsCount для всех элементов коллекции");
        this.communicate = communicate;
    }

    @Override
    public boolean execute(String argument) throws IncorrectScriptException {
        Request request = null;
        try {
            if (argument.isEmpty()) {
                request = new Request(null, "sum_of_students_count", null, Authenticator.getOwnerId());
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
        return "sum_of_students_count - Выводит сумму значений поля studentsCount для всех элементов коллекции";
    }
}
