package command.commands;

import client.Client;
import client.Communicate;
import command.AbstractCommand;
import command.exceptions.WrongCommandInputException;
import console.ConsoleClient;
import exceptions.IncorrectScriptException;
import request.Request;
import response.Response;
import utility.Asker;
import utility.Authenticator;

public class Exit extends AbstractCommand {
    Communicate communicate;

    public Exit(Communicate communicate) {
        super("Exit", "Завершает программу (без сохранения в файл)");
        this.communicate = communicate;
    }

    @Override
    public boolean execute(String argument) throws IncorrectScriptException {
        Request request = null;
        try {
            if (argument.isEmpty()) {
                request = new Request(null, "exit", null, Authenticator.getOwnerId());
                communicate.send(request);
                Response response = communicate.get();
                if (response.getAnswer()) {
                    Client.close();
                    System.exit(0);
                } else {
                    ConsoleClient.println("\n" + response.getText() + "\n");
                    return false;
                }
                return true;
            } else throw new WrongCommandInputException();

        } catch (WrongCommandInputException exception) {
            ConsoleClient.printError("Команда " + getName() + " введена с ошибкой: " +
                    "команда не должна содержать символы после своего названия!");
            if (Asker.getFileMode()) throw new IncorrectScriptException();
        }
        return false;
    }

    public String getMessage() {
        return "exit - Завершает программу (без сохранения в файл)";
    }
}
