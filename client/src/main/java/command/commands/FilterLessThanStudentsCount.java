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

import java.util.NoSuchElementException;

public class FilterLessThanStudentsCount extends AbstractCommand {
    Communicate communicate;

    public FilterLessThanStudentsCount(Communicate communicate) {
        super("filter_less_than_students_count", "Выводит элементы, значение поля studentsCount" +
                " которых меньше заданного");
        this.communicate = communicate;
    }

    @Override
    public boolean execute(String argument) throws IncorrectScriptException {
        Request request = null;
        try {
            if (!argument.isEmpty()) {
                int input = Integer.parseInt(argument.trim());
                request = new Request(null, "filter_less_than_students_count", Integer.toString(input), Authenticator.getOwnerId());
                communicate.send(request);
                Response response = communicate.get();
                ConsoleClient.println("\n" + response.getText());
                return response.getAnswer();
            } else throw new WrongCommandInputException();
        } catch (WrongCommandInputException exception) {
            ConsoleClient.printError("Команда " + getName() + " введена с ошибкой: " +
                    "команда не должна содержать символы после своего названия!");
            if (Asker.getFileMode()) throw new IncorrectScriptException();
        } catch (NumberFormatException exception) {
            ConsoleClient.printError("Значением поля должно являться число!");
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
        return "filter_less_than_students_count studentsCount - Выводит элементы, значение поля studentsCount которых меньше заданного";
    }
}
