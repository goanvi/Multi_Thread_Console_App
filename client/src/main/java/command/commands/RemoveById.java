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

public class RemoveById extends AbstractCommand {
    Communicate communicate;

    public RemoveById(Communicate communicate) {
        super("remove_by_id", "Удаляет элемент из коллекции по его id");
        this.communicate = communicate;
    }

    @Override
    public boolean execute(String argument) throws IncorrectScriptException {
        Request request = null;
        try {
            if (!argument.isEmpty()) {
                request = new Request(null, "remove_by_id", argument, Authenticator.getOwnerId());
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
        return "remove_by_id  - Удаляет элемент из коллекции по его id";
    }
}
