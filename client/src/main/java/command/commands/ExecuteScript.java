package command.commands;

import command.AbstractCommand;
import command.exceptions.ScriptLoopingException;
import command.exceptions.WrongCommandInputException;
import console.ConsoleClient;
import exceptions.IncorrectScriptException;
import utility.Asker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ExecuteScript extends AbstractCommand {
    private ConsoleClient consoleClient;

    public ExecuteScript(ConsoleClient consoleClient) {
        super("Execute_script", "Считывает и исполняет скрипт из указанного файла");
        this.consoleClient = consoleClient;
    }

    @Override
    public boolean execute(String argument) throws IncorrectScriptException {
        String input;
        File file;
        try {
            if (!argument.isEmpty()) {
                file = new File(argument.trim());
                if (consoleClient.getFiles().contains(file.getAbsolutePath())) throw new ScriptLoopingException();
                consoleClient.getFiles().add(file.getAbsolutePath());
                consoleClient.fileMode(new Scanner(file));
                ConsoleClient.println("Скрипт из файла " + consoleClient.getFiles().getLast() + " успешно выполнен!");
                consoleClient.getFiles().removeLast();
                return true;
            } else throw new WrongCommandInputException();
        } catch (FileNotFoundException exception) {
            ConsoleClient.printError("Файл не найден!");
            if (Asker.getFileMode()) throw new IncorrectScriptException();
        } catch (ScriptLoopingException exception) {
            ConsoleClient.printError("Зацикливание скрипта!");
            if (Asker.getFileMode()) throw new IncorrectScriptException();
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
        return "execute_script file_name - Считает и исполнит скрипт из указанного файла";
    }
}
