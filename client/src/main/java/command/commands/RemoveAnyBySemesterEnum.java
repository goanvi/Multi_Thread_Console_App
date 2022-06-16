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
import utility.exceptions.IncorrectNameEnumException;

import java.util.NoSuchElementException;
import java.util.regex.Pattern;

public class RemoveAnyBySemesterEnum extends AbstractCommand {
    Communicate communicate;

    public RemoveAnyBySemesterEnum(Communicate communicate) {
        super("remove_any_by_semester_enum {Три, Пять, Семь}", "Удаляет из коллекции один элемент," +
                " значение поля semesterEnum которого эквивалентно заданному");
        this.communicate = communicate;
    }

    @Override
    public boolean execute(String argument) throws IncorrectScriptException {
        Request request = null;
        try {
            if (!argument.isEmpty()) {
                if (Pattern.matches("(три)|(пять)|(семь)", argument.trim())) System.out.println("работает");
                else System.out.println("не работает");
                switch (argument.trim()) {
                    case "три":
                    case "пять":
                    case "семь":
                        request = new Request(null, "remove_any_by_semester_enum", argument, Authenticator.getOwnerId());
                        break;
                    default:
                        throw new IncorrectNameEnumException();
                }
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
        } catch (IncorrectNameEnumException e) {
            ConsoleClient.printError("Некорректный ввод аргумента!");
        }
        return false;
    }

    public String getMessage() {
        return "remove_any_by_semester_enum semesterEnum - Удаляет из коллекции один элемент," +
                " значение поля semesterEnum которого эквивалентно заданному";
    }
}
