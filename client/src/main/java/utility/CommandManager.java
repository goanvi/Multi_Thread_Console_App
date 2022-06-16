package utility;

import command.AbstractCommand;
import command.exceptions.ExecuteCommandException;
import command.exceptions.WrongCommandInputException;
import console.ConsoleClient;
import exceptions.IncorrectScriptException;

import java.util.Map;

public class CommandManager {
    Map<String, AbstractCommand> commands;

    public CommandManager(Map<String, AbstractCommand> commands) {
        this.commands = commands;
    }

    public void callCommand(String command, String argument) throws IncorrectScriptException {
        boolean answer;
        try {
            int count = 0;
            for (Map.Entry<String, AbstractCommand> commandMap : commands.entrySet()) {
                if (commandMap.getKey().equalsIgnoreCase(command)) {
                    count += 1;
                    answer = commandMap.getValue().execute(argument);
                    if (!answer) throw new ExecuteCommandException();
                }
            }
            if (count == 0) throw new WrongCommandInputException();

        } catch (WrongCommandInputException exception) {
            ConsoleClient.printError("Некорректный ввод команды!");
            if (Asker.getFileMode()) throw new IncorrectScriptException();
        } catch (ExecuteCommandException exception) {
            ConsoleClient.printError(command + ": ошибка в выполнении команды!");
            if (Asker.getFileMode()) throw new IncorrectScriptException();
        }
    }

    public Map<String, AbstractCommand> getCommands() {
        return commands;
    }
}
